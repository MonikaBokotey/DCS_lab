package Homework.lab1.lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

class Sender extends Thread{
	
	JTextArea outputArea;
	public Sender(JTextArea outputArea) {
		this.outputArea=outputArea;
	}
	
	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket();
			byte[] buf = new byte[256];
			InetAddress adress = InetAddress.getByName("localhost");
			DatagramPacket packet = new DatagramPacket(buf, buf.length, adress, 4445);
			socket.send(packet);
			 
			packet= new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			
			String received = new String(packet.getData());
			outputArea.append("\n>"+received);
			
			socket.close();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}