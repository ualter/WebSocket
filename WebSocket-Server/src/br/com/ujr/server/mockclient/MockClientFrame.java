package br.com.ujr.server.mockclient;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MockClientFrame extends JFrame implements ActionListener {
	
	public static void main(String[] args) {
		MockClientFrame m = new MockClientFrame();
		m.setVisible(true);
	}
	
	public MockClientFrame() {
		this.setBounds(100, 680, 300, 200);
		this.setTitle("MockClient Connections");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.btnStart.setActionCommand("start");
		this.btnStart.addActionListener(this);
		this.btnStop.setActionCommand("stop");
		this.btnStop.addActionListener(this);
		this.btnStop.setEnabled(false);
		this.add(this.btnStart);
		this.add(this.btnStop);
	}
	
	private MockClientConnections mockClientConnections;
	private JButton btnStart = new JButton("Start");
	private JButton btnStop = new JButton("Stop");
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if ( "start".equals(event.getActionCommand()) ) {
			this.btnStart.setEnabled(false);
			this.btnStop.setEnabled(true);
			this.mockClientConnections = new MockClientConnections();
			this.mockClientConnections.start();
		} else
		if ( "stop".equals(event.getActionCommand()) ) {
			this.btnStart.setEnabled(true);
			this.btnStop.setEnabled(false);
			this.mockClientConnections.setStop(true);
		}
	}
	
	private static final long serialVersionUID = 1L;

}
