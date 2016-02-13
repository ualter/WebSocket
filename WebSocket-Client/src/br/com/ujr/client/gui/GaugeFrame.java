package br.com.ujr.client.gui;

import javax.swing.JFrame;

import com.sun.grizzly.websockets.DataFrame;
import com.sun.grizzly.websockets.WebSocket;
import com.sun.grizzly.websockets.WebSocketListener;

public class GaugeFrame extends JFrame implements WebSocketListener {
	
	final GaugePanel gaugePanel = new GaugePanel(600,600);
	
	public GaugeFrame() {
		this.setTitle("GaugeFrame - Monitoração Servidor - Cliente Java WebSocket");
		this.setDefaultCloseOperation(3);
		this.setContentPane(gaugePanel);
		this.setBounds(50,50,600,630);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setValue(int number) {
		this.gaugePanel.setValue(number);
	}
	public int getCurrentValue() {
		return this.gaugePanel.getCurrentValue();
	}
	public void moveTo(int value) {
		this.gaugePanel.moveTo(value);
	}
	
	@Override
	public void onClose(WebSocket socket, DataFrame data) {
	}
	@Override
	public void onConnect(WebSocket socket) {
	}
	@Override
	public void onFragment(WebSocket socket, String data, boolean arg2) {
	}
	@Override
	public void onFragment(WebSocket socket, byte[] data, boolean arg2) {
	}
	@Override
	public void onMessage(WebSocket socket, String data) {
		/* 
		 * Recebe a quantidade de requisições em execução no servidor e
		 * representa no velocimetro na interface gráfica do usuário 
		 */
		this.moveTo(Integer.valueOf(data));
	}
	@Override
	public void onMessage(WebSocket socket, byte[] data) {
	}
	@Override
	public void onPing(WebSocket socket, byte[] data) {
	}
	@Override
	public void onPong(WebSocket socket, byte[] data) {
	}
	
	private static final long serialVersionUID = 1L;
	
}
