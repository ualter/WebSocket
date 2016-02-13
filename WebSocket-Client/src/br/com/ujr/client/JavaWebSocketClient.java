package br.com.ujr.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.grizzly.websockets.WebSocketClient;
import com.sun.grizzly.websockets.WebSocketListener;

/**
 * Cliente em Java para conexão com servidor WebSocket
 */
public class JavaWebSocketClient extends Thread implements Runnable {
	
	private String serverUrl;
	private boolean exit = false;
	private WebSocketClient webSocketClient;
	private List<WebSocketListener> listeners;
	
	public JavaWebSocketClient() {
		// Endereço do servidor WebSocket
		this.serverUrl = "ws://localhost:8080/WebSocket-Server/ws";
		this.listeners = new ArrayList<WebSocketListener>();
	}
	
	public void addListener(WebSocketListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Envio de mensagem para o servidor
	 */
	public void enviarMensagem(String msg) {
		this.webSocketClient.send(msg);
	}
	
	/**
	 * Execução da Thread, estabelecimento da conexão
	 */
	public void run() {
		try {
			/* 
			 * Criação do objeto de conexão informando o objeto de retorno
			 * (listener callback) das mensagens enviadas para a este cliente     
			 */
			this.webSocketClient = new WebSocketClient(this.serverUrl);
			// Adicionando os ouvintes interessados nas mensagens recebidas por este cliente java WebSocket 
			for(WebSocketListener l : this.listeners) {
				this.webSocketClient.add(l);
			}
			// Inicia a conexão
			this.webSocketClient.connect();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		while(!exit){
			Thread.yield();
		}
	}
	
	public void setExit(boolean exit) {
		this.exit = exit;
	}

}
