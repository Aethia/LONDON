package fr.m1.miage.london.network.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Chat_ClientServeur implements Runnable {

	private Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private Scanner sc;
	private Thread t3, t4;
	private String login;
	
	public Chat_ClientServeur(Socket s,String login){
		this.login = login;
		socket = s;
	}
	
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			sc = new Scanner(System.in);
			
			Emission e1 = new Emission(out,login);
			Sender.e = e1;
			Thread t4 = new Thread(e1);
			t4.start();
			Thread t3 = new Thread(new Reception(in,login));
			t3.start();
		
		   
		    
		} catch (IOException e) {
			System.err.println("Le serveur distant s'est d�connect� !");
		}
	}

}