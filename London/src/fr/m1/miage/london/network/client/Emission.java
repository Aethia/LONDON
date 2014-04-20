package fr.m1.miage.london.network.client;

import java.io.PrintWriter;
import java.util.Scanner;



public class Emission implements Runnable{

	private PrintWriter out;
	private String login = null, message = null;
	private Scanner sc = null;
	
	public Emission(PrintWriter out, String login) {
		this.login = login;
		this.out = out;
		
	}

	
	public void run() {
		System.out.println("thread emission du client lancé.");
	}



	public void sendMessage(String message) {
		out.println(message);
	    out.flush();	
	}
	
	
}
