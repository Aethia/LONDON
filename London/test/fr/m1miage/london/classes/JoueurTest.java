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
	
	@Test
	
	public void testEmpruntNegative(){
		Joueur j = new Joueur(1,"toto",Color.black);
		assertEquals("Montant incorrect \n", j.emprunter(-1));
	}
	@Test
	
	public void testEmpruntZero(){
		Joueur j = new Joueur(1,"toto",Color.black);
		assertEquals("Montant incorrect \n", j.emprunter(0));
	}	@Test
	
	public void testEmpruntMultiple(){
		Joueur j = new Joueur(1,"toto",Color.black);
		j.emprunter(10);
		assertEquals(15, j.getArgent());
	}	@Test
	
	public void testEmpruntPasMultiple(){
		Joueur j = new Joueur(1,"toto",Color.black);
		assertEquals("Montant incorrect \n", j.emprunter(1));
	}	@Test
	
	public void testEmpruntHorsLimite(){
		Joueur j = new Joueur(1,"toto",Color.black);
		assertEquals("Montant incorrect \n", j.emprunter(110));
	}	@Test
	
	public void testEmpruntLimite(){
		Joueur j = new Joueur(1,"toto",Color.black);
		j.emprunter(50);
		assertEquals(55, j.getArgent());
		assertEquals("Montant incorrect \n", j.emprunter(60));
	}
}
