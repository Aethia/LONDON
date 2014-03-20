package test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;

public class JoueurTest {

	@Test
	/*
	 * Test sur le getMain qui doit retourner une copie de la main et non la main en elle même
	 */
	public void testGetMain(){
		Joueur j = new Joueur(1,"toto",Color.black,0,0);
		Carte c = new Carte(1,"pauvre","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		Carte c1 = new Carte(2,"usine","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		j.ajouterCarteMain(c);
		j.ajouterCarteMain(c1);
		assertNotSame(j.getMainDuJoueur(), j.getMainDuJoueurCopie());
	}

}
