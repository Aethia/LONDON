package fr.m1miage.london.classes;

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
		Joueur j = new Joueur(1,"toto",Color.black);
		Carte c = new Carte(1,"nom","A",2,"Rouge",null);
		Carte c1 =new Carte(2,"nom","A",2,"Rouge",null);
		j.ajouterCarteMain(c);
		j.ajouterCarteMain(c1);
		assertNotSame(j.getMainDuJoueur(), j.getMainDuJoueurCopie());
	}

}
