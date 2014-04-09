package test;

import static org.junit.Assert.*;

import org.junit.Before;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Plateau;

public class JoueurTest {
	
	private Joueur j;
	private Carte c;
	private Carte c1;
	
	@Before
	public void setUp() throws Exception{
		
		j = new Joueur(1,"toto",Color.black);
		c = new Carte(1,"nom","A",2,"Brun",null);
		c1 =new Carte(2,"nom","A",2,"Brun",null);
		j.ajouterCarteMain(c);
		j.ajouterCarteMain(c1);
	}
	

	@Test
	/*
	 * Test sur le getMain qui doit retourner une copie de la main et non la main en elle même
	 */
	public void testGetMain(){
		
		assertNotSame(j.getMainDuJoueur(), j.getMainDuJoueurCopie());
	}
	
	@Test
	public void testGetCartesCouleur(){
		Carte c3 = new Carte(3, "nom2", "A", 1, "Rose", null);
		List<Carte> cartes = new ArrayList<Carte>();
		j.ajouterCarteMain(c3);
		cartes.add(c1);
		assertEquals(cartes, j.getCartesCouleur(c));
	}
	
	@Test
	public void testGetNotCartesCouleur(){
		Carte c3 = new Carte(3, "nom2", "A", 1, "Rose", null);
		List<Carte> cartes = new ArrayList<Carte>();
		j.ajouterCarteMain(c3);
		cartes.add(c1);
		assertEquals(new ArrayList<Carte>(), j.getCartesCouleur(c3));
	}
	
	@Test
	public void testConstruire(){
		Plateau p = new Plateau();
		p.init();
		j.getZone_construction().addPile();
		assertEquals("Construction terminée !", j.construire(c, c1, 1));
	}
	
	@Test
	public void testNotConstruire(){
		Plateau p = new Plateau();
		p.init();
		Carte c3 = new Carte(3, "nom2", "A", 1, "Rose", null);
		Carte c4 = new Carte(4, "nom4", "A", 7, "Brun", null);
		Carte c5 = new Carte(5, "nom5", "A", 7, "Brun", null);
		j.ajouterCarteMain(c3);
		j.ajouterCarteMain(c4);
		j.getZone_construction().addPile();
		assertEquals("Carte à défausser n'existe pas", j.construire(c, c3, 1));
		assertEquals("Argent insuffisant", j.construire(c4, c, 1));
		assertEquals("Carte à poser n'existe pas", j.construire(c5, c, 1));
	}
	
	@Test
	public void testVerifPresenceCarte(){
		List<Carte> cartes = new ArrayList<Carte>();
		cartes.add(c);
		assertEquals(true, j.verifPresenceCarte(c, cartes));
	}
	
	@Test
	public void testVerifAbsenceCarte(){
		List<Carte> cartes = new ArrayList<Carte>();
		cartes.add(c);
		assertNotEquals(true, j.verifPresenceCarte(c1, cartes));
	}

}
