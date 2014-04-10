package fr.m1miage.london.classes;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.m1miage.london.Partie;

public class PartieTest implements Serializable{

	@Test
	public void testSauvegarde() throws IOException, ClassNotFoundException {
		Partie partie = new Partie();
		partie.setPlateau(new Plateau());
		partie.getPlateau().init();
		Joueur j = new Joueur(1,"toto",Color.black);
		Joueur j2 = new Joueur(2,"titi",Color.red);
		List<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(j);
		joueurs.add(j2);
		Carte c = new Carte(1,"carte1","A",2,"Brun",null);
		Carte c1 =new Carte(2,"carte2","A",2,"Brun",null);
		Carte c2 = new Carte(3,"carte3","A",2,"Bleu",null);
		Carte c3 =new Carte(4,"carte4","A",2,"Bleu",null);
		boolean actionEffectuee=false;
		j.ajouterCarteMain(c);
		j.ajouterCarteMain(c1);
		j2.ajouterCarteMain(c3);
		j2.ajouterCarteMain(c2);
		j.construire(c, c1, 0);
		partie.setListeJoueurs(joueurs);
		
		FileOutputStream out = new FileOutputStream("save.txt");
		ObjectOutputStream s = new ObjectOutputStream(out);
		s.writeObject(partie);
		//s.writeObject(this.plateau.getEtalage());
		for(Joueur i:joueurs){
			s.writeObject(i.getMainDuJoueur());
		}
		System.out.println("Objet sérialisé");
		s.close();
		
		//Partie partie2 = new Partie();
		FileInputStream in=new FileInputStream("save.txt");
		ObjectInputStream s2 = new ObjectInputStream(in);
		Partie partie2 = (Partie) s2.readObject();
		
//		Etalage etalage = (Etalage)s.readObject();
//		Plateau.setEtalage(etalage);
		assertEquals(joueurs, partie2.getListeJoueurs());
	}

}
