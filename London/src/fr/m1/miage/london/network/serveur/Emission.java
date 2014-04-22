package fr.m1.miage.london.network.serveur;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import fr.m1.miage.london.network.Action;
import fr.m1miage.london.classes.Joueur;


public class Emission implements Runnable {

	private ObjectOutputStream out;
	private String message = null;
	private Scanner sc = null;
	private Joueur joueur;
	
	public Emission(ObjectOutputStream out,Joueur joueur) {
		this.out = out;
		this.joueur = joueur;
	}

	
	public void run() {
		
		 
	}
	
	public Joueur getJoueur(){
		return joueur;
	}
	
	
	public void sendMessageString(String msg){
		try {
			Action a = new Action();
			a.setType(2);
			a.setText(msg);
			out.writeObject(a); 
			out.flush();
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
			out.reset();
			out.flush();
			out.reset();
			
			a = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void sendObjectPartie(Object o){
		try {
			Action a = new Action();
			a.setType(5);
			a.setObject(o);
			a.setText("lol samarchpa");

			out.writeObject(a); 
			out.reset();
			out.flush();
			out.reset();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	
	
}
