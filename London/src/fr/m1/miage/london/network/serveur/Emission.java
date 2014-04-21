package fr.m1.miage.london.network.serveur;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;


public class Emission implements Runnable {

	private ObjectOutputStream out;
	private String message = null;
	private Scanner sc = null;
	private String login;
	
	public Emission(ObjectOutputStream out,String login) {
		this.out = out;
		this.login = login;
	}

	
	public void run() {
		
		 
	}
	
	public String getLogin(){
		return login;
	}
	
	
	public void sendMessage(String msg){
		try {
			out.writeObject(msg); 
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
	}
	
	
}
