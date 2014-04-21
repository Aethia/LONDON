package fr.m1miage.london.classes;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.Partie;

public class EffetTest {
	
	private Joueur j, j1;
	private Carte c, c1, c2;
	private ZoneConstruction zc;
	private Pioche pioche;
	private Partie p;
	private Effet effet;
	private List<Joueur> listeJoueurs;
	private Quartier q1, q2, q3;
	private Plateau plateau;
	
	private Set<Quartier> quartiersAdjacents1;
	private Set<Quartier> quartiersAdjacents2;
	
	@Before
	public void setUp() throws Exception{                                             
		p = new Partie();
		effet = new Effet();
		zc = new ZoneConstruction();
		pioche = new Pioche();
		//p.setPioche(pioche);
		j = new Joueur(1,"toto",Color.black);
		j1 = new Joueur(2,"titi",Color.red);
		j.setZoneConstruction(zc);
		c = new Carte(1,"nom","A",2,"Brun",null);
		c1 =new Carte(2,"nom","A",2,"Bleu",null);
		c2 =new Carte(3,"nom","A",2,"Bleu",null);
		q1 = new Quartier();
		q2 = new Quartier();
		q3 = new Quartier();
		plateau = new Plateau();
		listeJoueurs = new ArrayList<Joueur>();
		listeJoueurs.add(j);
		listeJoueurs.add(j1);
		p = new Partie(listeJoueurs, 2);
		
		p.setPlateau(plateau);
	}
	
	@Test
	public void testPrendreDeuxCartes() {
		pioche.ajouterCarte(c);
		pioche.ajouterCarte(c1);
		pioche.ajouterCarte(c2);
		
		effet.prendreDeuxCartes(pioche, j);
		assertEquals(2, j.getNb_cartes());
	}
	
	@Test
	public void testPrendreDeuxCartesPiocheVide() {
		
		effet.prendreDeuxCartes(pioche, j);
		assertEquals(0, j.getNb_cartes());
	}

	@Test
	public void testPVPourCartesNonBrune() {
		zc.addPile(c);
		zc.addPile(c1);
		zc.addPile(c2);
		
		effet.pVPourCartesNonBrune(j);
		assertEquals(2, j.getPoint_victoire());
		
	}
	
	@Test
	public void testPVPourCartesNonBrunePiocheVide() {
		
		effet.pVPourCartesNonBrune(j);
		assertEquals(0, j.getPoint_victoire());
		
	}
	
	@Test
	public void testPVPourCartesBrune() {
		zc.addPile(c);
		zc.addPile(c1);
		zc.addPile(c2);
		
		effet.pVPourCartesBrune(j);
		assertEquals(1, j.getPoint_victoire());
		
	}
	
	@Test
	public void testPVPourCartesBruneAucune() {
		zc.addPile(c1);
		zc.addPile(c2);
		
		effet.pVPourCartesBrune(j);
		assertEquals(0, j.getPoint_victoire());
		
	}
	
	@Test
	public void testArgentQuartiersNordFaux() {
		q1.setId(1);
		q1.setProprietaireQuartier(j);
		q1.setAuSudTamise(true);
		plateau.getQuartiers().put(1, q1);
		
		effet.argentQuartiersNord(plateau, j);
		assertEquals(5, j.getArgent());
		
	}
	
	@Test
	public void testArgentQuartiersNordVrai() {
		q1.setId(1);
		q1.setProprietaireQuartier(j);
		q1.setAuSudTamise(false);
		plateau.getQuartiers().put(1, q1);
		
		effet.argentQuartiersNord(plateau, j);
		assertEquals(7, j.getArgent());
		
	}

	@Test
	public void testArgentQuartiersSudFaux() {
		q1.setId(1);
		q1.setProprietaireQuartier(j);
		q1.setAuSudTamise(false);
		plateau.getQuartiers().put(1, q1);
		
		effet.argentQuartiersSud(plateau, j);
		assertEquals(5, j.getArgent());
		
	}
	
	@Test
	public void testArgentQuartiersSudVrai() {
		q1.setId(1);
		q1.setProprietaireQuartier(j);
		q1.setAuSudTamise(true);
		plateau.getQuartiers().put(1, q1);
		
		effet.argentQuartiersSud(plateau, j);
		assertEquals(7, j.getArgent());
		
	}
	@Test
	public void testArgentQuartiersOccupes(){
		q1.setId(1);
		q1.setProprietaireQuartier(j);
		q2.setId(2);
		q2.setProprietaireQuartier(j);
		plateau.getQuartiers().put(1, q1);
		plateau.getQuartiers().put(2, q2);
		
		effet.argentQuartiersOccupes(plateau, j);
		assertEquals(7, j.getArgent());
	}
	
	@Test
	public void testArgentQuartiersOccupesAucun(){
		
		effet.argentQuartiersOccupes(plateau, j);
		assertEquals(5, j.getArgent());
	}
	
	@Test
	public void testArgentQuartiersAdjacentsTamise(){
		q1.setId(1);
		q1.setProprietaireQuartier(j);
		q1.setAuSudTamise(false);
		q2.setId(2);
		q2.setAuSudTamise(false);
		q3.setId(3);
		q3.setProprietaireQuartier(j);
		q3.setAuSudTamise(true);
		
		quartiersAdjacents1 = new HashSet<Quartier>();
		quartiersAdjacents1.add(q3);
		q1.setQuartiersAdjacents(quartiersAdjacents1);
		
		quartiersAdjacents2 = new HashSet<Quartier>();
		quartiersAdjacents2.add(q1);
		q3.setQuartiersAdjacents(quartiersAdjacents2);
		
		plateau.getQuartiers().put(1, q1);
		plateau.getQuartiers().put(2, q2);
		plateau.getQuartiers().put(2, q3);
		
		effet.argentQuartiersAdjacentsTamise(plateau, j);
		assertEquals(9, j.getArgent());
	}	
	
	@Test
	public void testArgentQuartiersAdjacentsTamiseAucunAdjacent(){
		q1.setId(1);
		q1.setProprietaireQuartier(j);
		q1.setAuSudTamise(false);
		q2.setId(2);
		q2.setProprietaireQuartier(j);
		q2.setAuSudTamise(false);
		plateau.getQuartiers().put(1, q1);
		plateau.getQuartiers().put(2, q2);
		
		effet.argentQuartiersAdjacentsTamise(plateau, j);
		assertEquals(5, j.getArgent());
	}
	
	@Test
	public void testArgentQuartiersAdjacentsTamisePossedeAucun(){
		
		effet.argentQuartiersAdjacentsTamise(plateau, j);
		assertEquals(5, j.getArgent());
	}
	
	@Test
	public void testDonneUnDeVosPPEnleve(){
		effet.donneUnDeVosPP(2, p, j, 2);
		assertEquals(4, j.getPoint_pauvrete());
	}
	
	@Test
	public void testDonneUnDeVosPPRecoit(){
		effet.donneUnDeVosPP(2, p, j, 2);
		assertEquals(6, j1.getPoint_pauvrete());
	}
	
	@Test
	public void testDonneUnDeVosPPInexistant(){
		assertEquals(GestionErreurs.NONEXISTANT_PLAYER, effet.donneUnDeVosPP(5, p, j, 2));
	}
	
	@Test
	public void testDonneUnDeVosPPSoiMeme(){
		assertEquals(GestionErreurs.WRONG_PLAYER, effet.donneUnDeVosPP(1, p, j, 2));
	}
	
	@Test
	public void testDonneUnDeVosPPPasDePauvre(){
		j.setAddPoint_pauvrete(-5);
		assertEquals(GestionErreurs.NOT_ENOUGH_PAUPERS, effet.donneUnDeVosPP(1, p, j, 2));
	}


}
