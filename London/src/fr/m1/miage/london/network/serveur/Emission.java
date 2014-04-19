package fr.m1.miage.london.network.serveur;

import java.io.PrintWriter;
import java.util.Scanner;


public class Emission implements Runnable {

	private PrintWriter out;
	private String message = null;
	private Scanner sc = null;
	private String login;
	
	public Emission(PrintWriter out,String login) {
		this.out = out;
		this.login = login;
	}

	
	public void run() {
		
		 
	}
	
	public String getLogin(){
		return login;
	}
	
	
	public void sendMessage(String msg){
		out.println(msg);
	    out.flush();	
	}
	
	
}
