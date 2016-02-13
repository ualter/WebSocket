package br.com.ujr.server.app;

import org.jboss.logging.Logger;

import com.sun.grizzly.tcp.Request;
import com.sun.grizzly.websockets.WebSocket;
import com.sun.grizzly.websockets.WebSocketApplication;

/**
 * WebSocket servidor que recebe as requisi��es WebSockets conectados 
 */
public class WebSocketServer extends WebSocketApplication {

	private Logger log = Logger.getLogger(WebSocketServer.class);
	
	/**
	 * Define se a requisi��o deve ser aceita(true) ou n�o(false) como uma conex�o WebSocket 
	 */
	@Override
	public boolean isApplicationRequest(Request request) {
		return true;
	}

	/**
	 * Recebe e reenvia (echo) as mensagens enviadas pelos WebSockets conectados
	 */
	@Override
	public void onMessage(WebSocket socket, String text) {
		/**
		 * Itera��o sobre todos os WebSockets conectados e reenviando a mensagem recebida
		 */
		for (WebSocket ws : this.getWebSockets()) {
			ws.send("[ECHO from Server] " + text);
		}
		log.info("Mensagem recebida e reenviada " + text);
	}
	
	/**
	 * Envia mensagem para os WebSockets conectados 
	 */
	public void enviarMensagem(String msg) {
		/**
		 * Itera��o sobre todos os WebSockets conectados para envio de mensagem
		 */
		for (WebSocket ws : this.getWebSockets()) {
			ws.send(msg);
		}
		log.info("Mensagem enviada " + msg);
	}
}
