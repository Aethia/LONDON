package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.awt.Color;

import org.junit.Test;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Pioche;
import fr.m1miage.london.classes.Plateau;

public class JoueurTest {

	@Test
	/*
	 * Test sur le getMain qui doit retourner une copie de la main et non la main en elle m�me
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
	public void testInvestirArgent(){
		Joueur j = new Joueur(1,"toto",Color.black);
		Plateau pl = new Plateau();
		Pioche p = new Pioche();
		pl.init();
		p.init();
		assertEquals("Désolé vous ne pouvez pas investir dans ce quartier !",j.invest(1,pl,p));
	}
	
	@Test
	public void testInvestirQuartier(){
		Joueur j = new Joueur(1,"toto",Color.black);
		Plateau pl = new Plateau();
		Pioche p = new Pioche();
		pl.init();
		p.init();
		
		
	}
	

}
