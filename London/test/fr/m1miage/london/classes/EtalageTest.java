package fr.m1miage.london.classes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EtalageTest {

	@Test
	public void testAjouterCarte(){
		Etalage e = new Etalage();
		Carte c = new Carte(1,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c);
		assertEquals(1, e.getRangee2().get(0).getId_carte());
		Carte c1 = new Carte(2,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c1);
		assertEquals(2, e.getRangee2().get(1).getId_carte());
		Carte c2 = new Carte(3,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c2);
		assertEquals(3, e.getRangee2().get(2).getId_carte());
		Carte c3 = new Carte(4,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c3);
		assertEquals(4, e.getRangee2().get(3).getId_carte());
		Carte c4 = new Carte(5,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c4);
		Carte c5 = new Carte(6,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c5);
		assertEquals(6, e.getRangee2().get(0).getId_carte());
		assertEquals(1, e.getRangee1().get(0).getId_carte());	
	}
	
	@Test
	public void testAffichage() {
	
	}
	
	@Test
	public void testVidageEtalage(){
		Etalage e = new Etalage();
		Carte c = new Carte(1,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c);
		Carte c1 = new Carte(2,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c1);
		Carte c2 = new Carte(3,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c2);
		Carte c3 = new Carte(4,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c3);
		Carte c4 = new Carte(5,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c4);
		Carte c5 = new Carte(6,"nom","A",2,"Rouge",null);
		e.ajouterCarte(c5);
		e.viderEtalage();
		String tmp = "1e rangee de l'�talage\n-------------\n2e rangee de l'�talage\n";
		assertEquals(tmp, tmp.toString());
	}

}
