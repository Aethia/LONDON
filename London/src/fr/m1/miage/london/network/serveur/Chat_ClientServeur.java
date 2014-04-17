package fr.m1.miage.london.network.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Chat_ClientServeur implements Runnable {

	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String login;
	private Thread t3, t4;
	
	
	public Chat_ClientServeur(Socket s, String log){
		socket = s;
		login = log;
	}
	public void run() {
		
		try {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream());
		
		
		Thread t3 = new Thread(new Reception(in,login));
		t3.start();
		Emission e = new Emission(out);
		Serveur.lesClients.add(e);
		Thread t4 = new Thread(e);
		t4.start();
		
		} catch (IOException e) {
			System.err.println(login +"s'est d�connect� ");
		}
}
}
