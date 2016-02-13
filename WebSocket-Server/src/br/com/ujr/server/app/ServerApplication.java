package br.com.ujr.server.app;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.sun.grizzly.websockets.WebSocketEngine;

/**
 * Servlet Servidor que recebe as requisi��es dos usu�rios da aplica��o 
 */
@WebServlet(name="ServerApplication", urlPatterns = {"/ws"})
public class ServerApplication extends HttpServlet {

	private Logger log = Logger.getLogger(ServerApplication.class);
	private WebSocketServer webSocketServer = new WebSocketServer();

	/**
	 * Registrando a aplica��o WebSocketServer para receber as requisi��es 
	 * de promo��o da conex�o para protocolo WebSocket. 
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		WebSocketEngine.getEngine().register(webSocketServer);
		log.info("WebSocketServer registrado com sucesso!");
	}

	/**
	 * Eliminando do registro a aplica��o WebSocketServer
	 */
	@Override
	public void destroy() {
		WebSocketEngine.getEngine().unregister(webSocketServer);
		log.info("WebSocketServer eliminado do registro com sucesso!");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Notificando as conex�es WebSocket: enviando mensagem recebida para os WebSockets conectados
		webSocketServer.enviarMensagem(req.getParameter("mensagem"));
	}
	
	private static final long serialVersionUID = 1L;
	
}
