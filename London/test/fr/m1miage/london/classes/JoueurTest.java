
package fr.m1miage.london.classes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.m1miage.london.GestionErreurs;

public class JoueurTest {

	private Joueur j;
	private Carte c,c1,c2;

	
	@Before
	public void setUp() throws Exception{
		
		j = new Joueur(1,"toto",Color.black);
		c = new Carte(1,"nom","A",2,"Brun",null);
		c1 =new Carte(2,"nom","A",2,"Brun",null);
		c2 =new Carte(3,"nom","A",2,"Brun",null);
		j.ajouterCarteMain(c);
		j.ajouterCarteMain(c1);
	}
	

	@Test
	/*
	 * Test sur le getMain qui doit retourner une copie de la main et non la main en elle m�me
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
	
	public void testEmpruntNegative(){
		Joueur j = new Joueur(1,"toto",Color.black);
		assertEquals(GestionErreurs.MONTANT_INCORRECT, j.emprunter(-1));
	}
	@Test
	
	public void testEmpruntZero(){
		Joueur j = new Joueur(1,"toto",Color.black);
		assertEquals(GestionErreurs.MONTANT_INCORRECT, j.emprunter(0));
	}	@Test
	
	public void testEmpruntMultiple(){
		Joueur j = new Joueur(1,"toto",Color.black);
		j.emprunter(10);
		assertEquals(15, j.getArgent());
	}
	
	@Test
	public void testConstruire(){
		Plateau p = new Plateau();
		p.setEtalage(new Etalage());
		p.init();
		j.getZone_construction().addPile();
		assertEquals(GestionErreurs.NONE, j.construire(c, c1, 1,p.getEtalage()));
	}
	
	@Test
	public void testNotConstruire(){
		Plateau p = new Plateau();
		p.setEtalage(new Etalage());
		p.init();
		Carte c3 = new Carte(3, "nom2", "A", 1, "Rose", null);
		Carte c4 = new Carte(4, "nom4", "A", 7, "Brun", null);
		Carte c5 = new Carte(5, "nom5", "A", 7, "Brun", null);
		j.ajouterCarteMain(c3);
		j.ajouterCarteMain(c4);
		j.getZone_construction().addPile();
		assertEquals(GestionErreurs.INCORRECT_CARTE_DEFAUSSE, j.construire(c, c3, 1,p.getEtalage()));
		assertEquals(GestionErreurs.NOT_ENOUGH_MONEY, j.construire(c4, c, 1,p.getEtalage()));
		assertEquals(GestionErreurs.INCORRECT_CARTE, j.construire(c5, c, 1,p.getEtalage()));
	}
	
	@Test
	
	public void testEmpruntPasMultiple(){
		Joueur j = new Joueur(1,"toto",Color.black);
		assertEquals(GestionErreurs.MONTANT_INCORRECT, j.emprunter(1));
	}	@Test
	
	public void testEmpruntHorsLimite(){
		Joueur j = new Joueur(1,"toto",Color.black);
		assertEquals(GestionErreurs.MONTANT_INCORRECT, j.emprunter(110));
	}	@Test
	
	public void testEmpruntLimite(){
		Joueur j = new Joueur(1,"toto",Color.black);
		j.emprunter(50);
		assertEquals(55, j.getArgent());
		assertEquals(GestionErreurs.MAX_EMPRUNT, j.emprunter(60));
	}
	
	@Test
	public void testEmpruntAvecEffetCarte72(){
		Joueur j = new Joueur(1,"toto",Color.black);
		Carte carte = new Carte(72, "nom1", "A", 1, "Rose", null);
		j.getZone_construction().addPile(carte);
		j.emprunter(50);
		assertEquals(65, j.getArgent());
		assertEquals(50, j.getMontantEmprunts());
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

	
	@Test
	public void testInvestirArgent(){
		Joueur j = new Joueur(1,"toto",Color.black);
		Plateau pl = new Plateau();
		Pioche p = new Pioche();
		pl.init();
		p.init();
		assertEquals(GestionErreurs.NOT_ENOUGH_MONEY,j.invest(1,pl,p));
	}
	
	@Test
	public void testInvestirQuartierInexistant(){
		Joueur j = new Joueur(1,"toto",Color.black);
		Plateau pl = new Plateau();
		Pioche p = new Pioche();
		pl.init();
		p.init();
		assertEquals(GestionErreurs.INCORRECT_NUMBER,j.invest(30,pl,p));
	}
	
	@Test
	public void testInvestirQuartierIndispo(){
		Joueur j = new Joueur(1,"toto",Color.black);
		Plateau pl = new Plateau();
		Pioche p = new Pioche();
		pl.init();
		p.init();
		assertEquals(GestionErreurs.DISABLED_QUARTIER,j.invest(10,pl,p));
	}
	
	@Test
	public void testInvestirQuartierOk(){
		Joueur j = new Joueur(1,"toto",Color.black);
		Plateau pl = new Plateau();
		Pioche p = new Pioche();
		pl.init();
		p.init();
		assertEquals(GestionErreurs.NONE,j.invest(2,pl,p));
	}
	
	@Test
	public void testRestaurerVilleOk(){
		Joueur j = new Joueur(1,"toto",Color.black);
		ZoneConstruction zc = j.getZone_construction();
		zc.addPile(c);
		zc.addPile(c1);
		zc.addPile(c2);
		
		// le cout de la carte c
		CoutActivation cout = new CoutActivation();
		cout.setTypeActiv(0);
		c.setcoutActivation(cout);
		
		// le cout de la carte c1
		CoutActivation cout2 = new CoutActivation();
		cout2.setTypeActiv(1);
		cout2.setLivresAPayer(2);
		c1.setcoutActivation(cout2);
		
		// le cout de la carte c2
		CoutActivation cout3 = new CoutActivation();
		cout3.setTypeActiv(1);
		cout3.setLivresAPayer(2);
		c2.setcoutActivation(cout3);
		
		// les cartes que je veux a	ctiver
		List<Integer> cartes = new ArrayList<Integer>();
		cartes.add(c.getId_carte());	
		cartes.add(c1.getId_carte());	
		assertEquals(GestionErreurs.NONE,j.restaurerVille(cartes));
		assertEquals(2,TraderClassRestaurerVille.getCoutEnLivres());

	}
	
	@Test
	public void testRestaurerVilleOk2(){
		Joueur j = new Joueur(1,"toto",Color.black);
		ZoneConstruction zc = j.getZone_construction();
		zc.addPile(c);
		zc.addPile(c1);
		zc.addPile(c2);
		
		// le cout de la carte c
		CoutActivation cout = new CoutActivation();
		cout.setTypeActiv(0);
		c.setcoutActivation(cout);
		
		// le cout de la carte c1
		CoutActivation cout2 = new CoutActivation();
		cout2.setTypeActiv(1);
		cout2.setLivresAPayer(2);
		c1.setcoutActivation(cout2);
		
		// le cout de la carte c2
		CoutActivation cout3 = new CoutActivation();
		cout3.setTypeActiv(2);
		cout3.setCouleurADefausser("Bleu");
		c2.setcoutActivation(cout3);
		
	
		// les cartes que je veux a	ctiver
		List<Integer> cartes = new ArrayList<Integer>();
		cartes.add(c.getId_carte());	
		cartes.add(c1.getId_carte());	
		cartes.add(c2.getId_carte());
		assertEquals(GestionErreurs.NONE,j.restaurerVille(cartes));
		assertEquals(1,TraderClassRestaurerVille.getNbCartesBleues());
		assertEquals(2,TraderClassRestaurerVille.getCoutEnLivres());
		
		

	}
	
	@Test
	public void testRestaurerVilleOk3(){
		Joueur j = new Joueur(1,"toto",Color.black);
		ZoneConstruction zc = j.getZone_construction();
		zc.addPile(c);
		zc.ajouterCarte(0, c1);;
		zc.addPile(c2);
		
		// le cout de la carte c
		CoutActivation cout = new CoutActivation();
		cout.setTypeActiv(0);
		c.setcoutActivation(cout);
		
		// le cout de la carte c1
		CoutActivation cout2 = new CoutActivation();
		cout2.setTypeActiv(1);
		cout2.setLivresAPayer(2);
		c1.setcoutActivation(cout2);
		
		// le cout de la carte c2
		CoutActivation cout3 = new CoutActivation();
		cout3.setTypeActiv(2);
		cout3.setCouleurADefausser("Bleu");
		c2.setcoutActivation(cout3);
		
	
		// les cartes que je veux a	ctiver
		List<Integer> cartes = new ArrayList<Integer>();	
		cartes.add(c1.getId_carte());	
		cartes.add(c2.getId_carte());
		assertEquals(GestionErreurs.NONE,j.restaurerVille(cartes));
		assertEquals(1,TraderClassRestaurerVille.getNbCartesBleues());
		assertEquals(2,TraderClassRestaurerVille.getCoutEnLivres());
		
		

	}

	@Test
	public void testRestaurerVilleErreur(){
		Joueur j = new Joueur(1,"toto",Color.black);
		ZoneConstruction zc = j.getZone_construction();
		zc.addPile(c);
		zc.addPile(c1);
		List<Integer> cartes = new ArrayList<Integer>();
		cartes.add(c2.getId_carte());
		assertEquals(GestionErreurs.INCORRECT_NUMBER,j.restaurerVille(cartes));
	}	
	
	@Test
	public void testPayerRestauration(){

		Carte c3 =new Carte(4,"nom","A",2,"Brun",null);
		Carte c4 =new Carte(5,"nom","A",2,"Rose",null);
		Carte c5 =new Carte(6,"nom","A",2,"Gris",null);
		j.ajouterCarteMain(c3);
		j.ajouterCarteMain(c4);
		j.ajouterCarteMain(c5);
		
		ArrayList<Carte> cartes = new ArrayList<Carte>();
		cartes.add(c3);
		
		TraderClassRestaurerVille.addNbCartesGrises();
		
		assertEquals(GestionErreurs.NONE, j.payerRestaurationVille(cartes));
	}
	
}

