package fr.m1.miage.london.network.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import fr.m1.miage.london.network.Action;
import fr.m1miage.london.classes.Joueur;



public class Emission implements Runnable{

	private ObjectOutputStream  out;
	private Joueur joueur = null;
	private String message = null;
	private Scanner sc = null;
	
	public Emission(ObjectOutputStream  out, Joueur joueur) {
		this.joueur = joueur;
		this.out = out;
		
	}

	
	public void run() {
		System.out.println("thread emission du client lancé.");
	}



	public void sendMessageString(String message) {
		try {
			Action a = new Action();
			a.setType(2);
			a.setText(message);
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
