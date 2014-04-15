package fr.m1miage.london;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Etalage;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Plateau;
import fr.m1miage.london.classes.Quartier;
import fr.m1miage.london.classes.ZoneConstruction;

public class PartieTest implements Serializable{

	private Partie p;
	private Joueur j;
	private Joueur j1;
	private Carte c, c1, c2, c3;
	private List<Joueur> listeJoueurs;
	private Quartier q1;
	private ZoneConstruction zc;

	@Before
	public void setUp() throws Exception{

		listeJoueurs = new ArrayList<Joueur>();
		j = new Joueur(1,"toto",Color.black);
		j1 = new Joueur(2,"titi",Color.red);
		listeJoueurs.add(j);
		listeJoueurs.add(j1);
		p = new Partie(listeJoueurs, 2);
		c = new Carte(1,"nom","A",2,"Brun",null);
		c1 =new Carte(2,"nom","A",2,"Brun",null);
		c2 = new Carte(3,"nom","A",2,"Bleu",null);
		c3 =new Carte(4,"nom","A",2,"Bleu",null);
		q1 = new Quartier();	
		zc = new ZoneConstruction();
	}
	
	@Test
	public void testSauvegarde() throws IOException, ClassNotFoundException {
		Partie partie = new Partie();
		partie.setPlateau(new Plateau());
		partie.getPlateau().setEtalage(new Etalage());
		partie.getPlateau().init();
		List<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(j);
		joueurs.add(j1);
		boolean actionEffectuee=false;
		j.ajouterCarteMain(c);
		j.ajouterCarteMain(c1);
		j1.ajouterCarteMain(c3);
		j1.ajouterCarteMain(c2);
		j.construire(c, c1, 0, partie.getPlateau().getEtalage());
		partie.setListeJoueurs(joueurs);
		partie.sauvegarder();
		
		Partie partie2 = new Partie();
		partie2.chargerPartie();
		System.out.println(partie2.getListeJoueurs().toString());
		System.out.println(partie.getListeJoueurs().toString());
		assertEquals(joueurs.toString(), partie2.getListeJoueurs().toString());
	}




	/*
	 * testCalculPointsVictoire
	 */
	@Test
	public void testCalculPointsVictoireRembourseEmprunt() {
		j.setAddPoint_victoire(10);
		j.emprunter(10);

		p.calculPointsVictoire();
		assertEquals(10, j.getPoint_victoire());
	}

	@Test
	public void testCalculPointsVictoireRemboursePasEmprunt() {
		j.setAddPoint_victoire(10);
		j.emprunter(10);
		j.setArgent(10);

		p.calculPointsVictoire();
		assertEquals(6, j.getPoint_victoire());
	}

	@Test
	public void testCalculPointsVictoireCarteEnMain() {
		//on a 6 cartes en main au d�part

		j.ajouterCarteMain(c);
		j.ajouterCarteMain(c1);
		j.ajouterCarteMain(c2);
		j.ajouterCarteMain(c3);
		p.calculPointsVictoire();
		assertEquals(9, j.getPoint_pauvrete());
	}

	@Test
	public void testCalculPointsVictoireArgentMultiple() {
		j.setAddPoint_victoire(10);
		j.setAddArgent(4);
		p.calculPointsVictoire();
		assertEquals(13, j.getPoint_victoire());
	}

	@Test
	public void testCalculPointsVictoireArgentPasMultiple() {
		j.setAddPoint_victoire(10);
		//j.setArgent(10);
		p.calculPointsVictoire();
		assertEquals(11, j.getPoint_victoire());
	}

	@Test
	public void testCalculPointsVictoireArrondissement() {
		q1.setId(1);
		q1.setProprietaireQuartier(j);
		q1.setPoint_victoire(10);

		Plateau plateau = new Plateau();
		plateau.getQuartiers().put(1, q1);
		p.setPlateau(plateau);

		p.calculPointsVictoire();
		assertEquals(11, j.getPoint_victoire());
	}

	@Test
	public void testCalculPointsVictoireArrondissementMetro() {

		q1.setId(1);
		q1.setProprietaireQuartier(j);
		q1.setPoint_victoire(10);
		q1.setMetro_pose(true);
		
		Plateau plateau = new Plateau();
		plateau.getQuartiers().put(1, q1);
		p.setPlateau(plateau);
		
		p.calculPointsVictoire();
		assertEquals(13, j.getPoint_victoire());
	}
	@Test
	public void testCalculPointsVictoireZoneConctruct() {
		c.setPointsVictoire(5);
		c1.setPointsVictoire(10);
		c2.setPointsVictoire(20);
		j.setZoneConstruction(zc);
		zc.addPile(c);
		zc.addPile(c1);
		zc.ajouterCarte(1, c2);

		p.calculPointsVictoire();
		assertEquals(36, j.getPoint_victoire());
	}
	/*
	 * TestCalculGagnant
	 */
	@Test
	public void testCalculGagnantDefaussePauvre() {

		j.setAddPoint_pauvrete(20);
		j1.setAddPoint_pauvrete(30);
		p.calculGagnant();
		assertEquals(10, j1.getPoint_pauvrete());
	}

	@Test
	public void testCalculGagnantTableauPoints() {
		j1.setAddPoint_victoire(20);

		j.setAddPoint_pauvrete(20);
		j1.setAddPoint_pauvrete(35);
		p.calculGagnant();
		assertEquals(5, j1.getPoint_victoire());
	}
	@Test
	public void testCalculGagnantEgaliteVictoire() {
		j.setAddPoint_victoire(20);
		j1.setAddPoint_victoire(20);
		//j.setAddPoint_pauvrete(5);
		//j1.setAddPoint_pauvrete(5);
		
		q1.setProprietaireQuartier(j1);
		Plateau plateau = new Plateau();
		plateau.getQuartiers().put(1, q1);
		p.setPlateau(plateau);
		
		p.calculPointsVictoire();
		p.calculGagnant();
		assertEquals(j1, listeJoueurs.get(0));
	}
}

