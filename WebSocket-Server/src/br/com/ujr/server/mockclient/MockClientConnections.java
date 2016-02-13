package br.com.ujr.server.mockclient;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import org.jboss.logging.Logger;


/**
 * Simulador de conexões de usuários
 */
public class MockClientConnections extends Thread implements Runnable {
		
	private Logger log = Logger.getLogger(MockClientConnections.class);
	private URL                serverAddress;
	private HttpURLConnection  connection;
	private String server;
	private int numberOfConnections = 100;
	private boolean stop = false;
	
	public MockClientConnections() {
		this.server = "http://localhost:8080/WebSocket-Server/ws?";
	}
	
	// Compõe o parâmetro que será enviado ao servidor 
	private String formatQuery(int number) {
		try {
			return String.format("mensagem=%s", URLEncoder.encode(String.valueOf(number),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.warn(e);
		}
		return "9999";
	}
	
	// Disparo da requisição do usuário para o servidor
	private void fireRequestToServer(String query) {
		try {
			this.serverAddress = new URL(this.server + query);
			this.connection    = (HttpURLConnection) this.serverAddress.openConnection();
			this.connection.getInputStream();
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/** 
	 * Código de execução da Thread: disparo das requisições do usuário ao servidor 
	 * Enviando como parâmetro a quantidade simulada de transações em execução pelo servidor
	 */
	@Override
	public void run() {
		Random rnd = new Random();
		for (int i = 0; (i < this.numberOfConnections && !this.stop); i++) {
			this.fireRequestToServer( formatQuery(rnd.nextInt(1000)) );
			// Intervalo 1seg
			this.pause(1000);
		}
	}
	
	public void setStop(boolean stop) {
		this.fireRequestToServer( formatQuery(0) );
		this.stop = stop;
	}
	
	// Utilitário para simular intervalo
	private void pause(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
