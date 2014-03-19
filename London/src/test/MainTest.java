package test;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Main;

public class MainTest {

	@Test
	/*
	 * méthode de test d'ajout de carte dans la main
	 */
	public void testAdd(){
		Main m = new Main();
		Carte c = new Carte(1,"nom","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		m.ajouterCarte(c);
		assertEquals(1, m.getLesCartes().get(0).getId_carte());
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
	/*
	 * test de suppression avec un indice incorrect
	 */
	public void testSupprimerParIndiceException(){
		Main m = new Main();
		m.supprimerCarteParIndice(-1);
		m.supprimerCarteParIndice(0);
		Carte c1 = new Carte(1,"nom1","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		m.ajouterCarte(c1);
		m.supprimerCarteParIndice(1);		
	}
	
	@Test
	/*
	 * test de suppression d'une carte dans la main par les indice
	 */
	public void testsupprimerParIndice(){
		Main m = new Main();
		Carte c1 = new Carte(1,"nom1","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		m.ajouterCarte(c1);
		m.supprimerCarteParIndice(0);
		assertEquals(0, m.getNb_cartes());
	}
	
	@Test
	/*
	 * simulation de fin de tour avec trop de cartes
	 */
	public void testFinDeTour(){
		Main m = new Main();
		for(int i=0;i<11;i++)
			m.ajouterCarte(new Carte((i+1),"nom","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1));
		Scanner lectureClavier = new Scanner(System.in);
		int id;
		while (m.VerifierQteCarteFinDeTour() == false) {
			System.out.println("Vous possédez trop de cartes pour pouvoir finir votre tour :");
			System.out.println(m);
			id = lectureClavier.nextInt();
			if (!m.supprimerCarteParId(id)){
				System.out.println("Carte introuvable!");
			}	
		}
		
	}
	
	@Test
	/*
	 * test de suppression d'une carte dans la main par les id
	 */
	public void testSupprimerParId(){
		Main m = new Main();
		Carte c1 = new Carte(1,"nom1","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		m.ajouterCarte(c1);
		assertEquals(true, m.supprimerCarteParId(1));
		assertEquals(false, m.supprimerCarteParId(2));
	}
	
	@Test
	/*
	 * test du calcul du nb de cartes dans la main
	 */
	public void testNbCartes(){
		Main m = new Main();
		assertEquals(0, m.getNb_cartes());
		Carte c = new Carte(1,"nom","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		Carte c1 = new Carte(1,"nom1","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		m.ajouterCarte(c);
		m.ajouterCarte(c1);
		assertEquals(2, m.getNb_cartes());
	}
	
	@Test
	/*
	 * test de l'affichage de la main
	 */
	public void testToString() {
		Carte c = new Carte(1,"nom","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		Carte c1 = new Carte(1,"nom1","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		Carte c2 = new Carte(1,"nom2","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		Main m = new Main();
		m.ajouterCarte(c);
		m.ajouterCarte(c1);
		m.ajouterCarte(c2);
		String tmp = "id : 1\nnom : nom\nperiode : A\n----------\nid : 1\nnom : nom1\nperiode : A\n----------\nid : 1\nnom : nom2\nperiode : A\n----------\n";
		assertEquals(tmp, m.toString());
	}
	
	@Test
	/*
	 * test de la méthode pour savoir si le joueur peut finir son tour ou non
	 */
	public void testQteCarteFinDeTour(){
		Main m = new Main();
		for(int i=0;i<9;i++)
			m.ajouterCarte(new Carte((i+1),"nom","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1));
		assertEquals(true, m.VerifierQteCarteFinDeTour());
		m.ajouterCarte(new Carte(10,"nom","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1));
		assertEquals(false, m.VerifierQteCarteFinDeTour());
	}

}
