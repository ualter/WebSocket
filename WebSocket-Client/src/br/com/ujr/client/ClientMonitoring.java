package br.com.ujr.client;

import br.com.ujr.client.gui.GaugeFrame;



public class ClientMonitoring {
	
	public static void main(String... args) {
		GaugeFrame gaugeFrame = new GaugeFrame();
		gaugeFrame.setVisible(true);
		
		JavaWebSocketClient javaWebSocketClient = new JavaWebSocketClient();
		javaWebSocketClient.addListener(gaugeFrame);
		javaWebSocketClient.start();
	}
}
