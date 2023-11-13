package Homework.lab1.lab2;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class tema2 {

	public static void main(String args[]) throws IOException {
		JFrame frame = new JFrame("(Self)Messaging App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		
		JTextField inputField = new JTextField("Something");
		inputField.setBounds(10, 400, 500, 30);
		frame.add(inputField);

		JTextArea outputArea = new JTextArea();
		outputArea.setBounds(10,100,580,300);
		frame.add(outputArea);
		
		JButton button = new JButton("Send");
		button.setBounds(500, 400, 80, 30);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Sender(outputArea).start();
				
			}
		});
		frame.add(button);
		
		frame.setLayout(null);
		frame.setVisible(true);
		
		new Reciever("Serve", inputField).start();
	}
}



