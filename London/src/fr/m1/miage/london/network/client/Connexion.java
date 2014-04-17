package fr.m1.miage.london.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connexion implements Runnable {

	private Socket socket = null;
	public static Thread t2;
	public static String login = null, pass = null, message1 = null, message2 = null, message3 = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private boolean connect = false;
	
	public Connexion(Socket s,String client){
		this.login = client;
		socket = s;
	}
	
	public void run() {
		
		try {
			
		out = new PrintWriter(socket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));		
		
		out.println(login);
		out.flush();
		
		
		if(in.readLine().equals("connecte")){
			
		System.out.println("Je suis connect� "); 
		connect = true;
		  }
		else {
			System.err.println("Impossible de se connecter au serveuyr"); 
		  }
		
	
			
			t2 = new Thread(new Chat_ClientServeur(socket,login));
			t2.start();
		
		} catch (IOException e) {
			
			System.err.println("Le serveur ne r�pond plus ");
		}
	}

}