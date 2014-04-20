package fr.m1.miage.london.network.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class Chat_ClientServeur implements Runnable {

	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private String login;
	private Thread t3, t4;
	
	
	public Chat_ClientServeur(Socket s, String log){
		socket = s;
		login = log;
	}
	
	
	public void run() {
		
		try {
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		
		
		Thread t3 = new Thread(new Reception(in,login));
		t3.start();
		Emission e = new Emission(out,login);
		Serveur.lesClients.add(e);
		Thread t4 = new Thread(e);
		t4.start();
		
		} catch (IOException e) {
			System.err.println(login +"s'est d�connect� ");
		}
}
}
