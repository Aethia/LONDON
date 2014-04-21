package fr.m1.miage.london.network.serveur;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import fr.m1.miage.london.network.Action;


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
	
	
	public void sendMessageString(String msg){
		try {
			Action a = new Action();
			a.setType(2);
			a.setText(msg);
			out.writeObject(a); 
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	
	public void sendObject(int type, Object o){
		try {
			Action a = new Action();
			a.setType(type);
			a.setObject(o);
			out.writeObject(a); 
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	
	
}
