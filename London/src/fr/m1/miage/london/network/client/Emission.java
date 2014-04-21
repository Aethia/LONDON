package fr.m1.miage.london.network.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;



public class Emission implements Runnable{

	private ObjectOutputStream  out;
	private String login = null, message = null;
	private Scanner sc = null;
	
	public Emission(ObjectOutputStream  out, String login) {
		this.login = login;
		this.out = out;
		
	}

	
	public void run() {
		System.out.println("thread emission du client lancé.");
	}



	public void sendMessage(String message) {
		try {
			out.writeObject(message); 
			out.flush();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
	
	
}
