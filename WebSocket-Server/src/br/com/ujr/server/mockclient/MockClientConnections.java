package br.com.ujr.server.mockclient;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import org.jboss.logging.Logger;


/**
 * Simulador de conex�es de usu�rios
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
	
	// Comp�e o par�metro que ser� enviado ao servidor 
	private String formatQuery(int number) {
		try {
			return String.format("mensagem=%s", URLEncoder.encode(String.valueOf(number),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.warn(e);
		}
		return "9999";
	}
	
	// Disparo da requisi��o do usu�rio para o servidor
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
	 * C�digo de execu��o da Thread: disparo das requisi��es do usu�rio ao servidor 
	 * Enviando como par�metro a quantidade simulada de transa��es em execu��o pelo servidor
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
	
	// Utilit�rio para simular intervalo
	private void pause(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
