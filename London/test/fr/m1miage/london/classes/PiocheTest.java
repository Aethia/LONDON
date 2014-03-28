package fr.m1miage.london.classes;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Pioche;

public class PiocheTest {

	@Test
	/*
	 * méthode de test de la qte de carte dans la pioche
	 */
	public void testNbCartes(){
		Pioche p = new Pioche();
		assertEquals(0, p.getNbCartes());
		p.ajouterCarte(new Carte(1,"nom","A",2,"Rouge",null));
		assertEquals(1, p.getNbCartes());
		p.ajouterCarte(new Carte(1,"nom","A",2,"Rouge",null));
		p.ajouterCarte(new Carte(1,"nom","A",2,"Rouge",null));
		assertEquals(3, p.getNbCartes());	
	}
	
	@Test
	/*
	 * méthode de test d'ajout de carte dans la main
	 */
	public void testAdd(){
		Pioche p = new Pioche();
		p.ajouterCarte(new Carte(1,"nom","A",2,"Rouge",null));
		assertEquals(1, p.getCartes().get(0).getId_carte());
	}
	
	@Test
	/*
	 * méthode de test de vidage de la pioche
	 */
	public void testViderPioche(){
		Pioche p = new Pioche();
		p.ajouterCarte(new Carte(1,"nom","A",2,"Rouge",null));
		p.ajouterCarte(new Carte(1,"nom","A",2,"Rouge",null));
		p.ajouterCarte(new Carte(1,"nom","A",2,"Rouge",null));
		p.ajouterCarte(new Carte(1,"nom","A",2,"Rouge",null));
		p.viderPioche();
		assertEquals(0, p.getNbCartes());	
	}
	
	@Test
	/*
	 * méthode de test du tirage de la carte au sommet de la pioche
	 */
	public void testTirerCarte(){
		Pioche p = new Pioche();
		Carte c = new Carte(1,"nom","A",2,"Rouge",null);
		p.ajouterCarte(c);
		p.ajouterCarte(new Carte(2,"nom","A",2,"Rouge",null));
		p.ajouterCarte(new Carte(3,"nom","A",2,"Rouge",null));
		p.ajouterCarte(new Carte(4,"nom","A",2,"Rouge",null));
		Carte c1 = p.tirerUneCarte();
		assertEquals(c, c1);

	}	

}
