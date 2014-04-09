package fr.m1miage.london;

import java.util.Scanner;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import fr.m1miage.london.db.CartesManager;
import fr.m1miage.london.ui.LondonGame;
import fr.m1miage.london.ui.Prefs;



public class London {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Choix du lancement de LONDON :");
		System.out.println("\t 1 : Console");
		System.out.println("\t 2 : Graphique");
		int nb=0;
		boolean err = true;
		Scanner sc = new Scanner(System.in);
		while(err){
			if(sc.hasNextInt()){
				nb = sc.nextInt();
				if(nb==1 || nb==2){
					err=false;
				}
			}else{
				sc.next();
			}
		}
		switch(nb){
		case 1 : 
			Partie partie = new Partie();
			partie.init();
			partie.lancerJeu();
			break;
		case 2 :  
			LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
			cfg.title = "LondonGame";
			cfg.width = Prefs.LARGEUR_FENETRE;
			cfg.height = Prefs.HAUTEUR_FENETRE;

			new LwjglApplication(new LondonGame(), cfg);
			break;
		default : ;
		break;
		}

		sc.close();
	}

}
