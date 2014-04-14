package fr.m1.miage.london.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AccepterClient implements Runnable{
	private ServerSocket socketserver;
	   private Socket socket;
	   private int nbrclient = 1;
		public AccepterClient(ServerSocket s){
			socketserver = s;
		}
		
		public void run() {

	        try {
	        	while(true){
			  socket = socketserver.accept(); 
	                  System.out.println("Le client numéro "+nbrclient+ " est connecté !");
	                  nbrclient++;
	                  socket.close();
	        	}
	        
	        } catch (IOException e) {
				e.printStackTrace();
			}
		}
}
