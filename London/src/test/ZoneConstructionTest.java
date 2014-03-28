package test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.ZoneConstruction;

public class ZoneConstructionTest {
	
	@Test
	public void TestGetNbPiles(){
		ZoneConstruction p = new ZoneConstruction();
		assertEquals(0, p.getNbPiles());
		p.addPile();
		assertEquals(1, p.getNbPiles());
		p.addPile();
		p.addPile();
		p.addPile();
		assertEquals(4, p.getNbPiles());
	}
	
	@Test
	public void testAjoutePileAvecCarte(){
		ZoneConstruction p = new ZoneConstruction();
		Carte c = new Carte(1,"nom","A",2,"Rouge",null);
		p.addPile(c);
		assertEquals(1, p.getCartesPile(0).get(0).getId_carte());
		Carte c1 = new Carte(2,"nom","A",2,"Rouge",null);
		p.ajouterCarte(0, c1);
		assertEquals(2, p.getCartesPile(0).get(1).getId_carte());
		Carte c3 = new Carte(3,"nom","A",2,"Rouge",null);
		p.addPile(c3);
		assertEquals(3, p.getCartesPile(1).get(0).getId_carte());
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
	public void testNbCartesParPileEx(){
		ZoneConstruction p = new ZoneConstruction();
		p.getCartesPile(1);				
	}
	
	@Test
	public void testNbCartesParPile(){
		ZoneConstruction p = new ZoneConstruction();
		Carte c = new Carte(1,"nom","A",2,"Rouge",null);
		p.addPile(c);
		assertEquals(1, p.getNbCartesPile(0));
		Carte c1 = new Carte(2,"nom","A",2,"Rouge",null);
		p.ajouterCarte(0, c1);
		assertEquals(2, p.getNbCartesPile(0));
		Carte c3 = new Carte(3,"nom","A",2,"Rouge",null);
		p.addPile(c3);
		assertEquals(1, p.getNbCartesPile(1));
	}

}
