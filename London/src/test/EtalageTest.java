package test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Etalage;

public class EtalageTest {

	@Test
	public void testAjouterCarte(){
		Etalage e = new Etalage(5);
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
		Etalage e = new Etalage(5);
		String tmp = "1e rangee de l'étalage\n-------------\n2e rangee de l'étalage\n";
		assertEquals(tmp, tmp.toString());
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
		tmp = "1e rangee de l'étalage \nid : 1\nnom : nom\nperiode : A\nid : 2\nnom : nom\nperiode : A\nid : 3\nnom : nom\nperiode : A\n";
		tmp += "id : 4\nnom : nom\nperiode : A\nid : 5\nnom : nom\nperiode : A\n-------------\n2e rangee de l'étalage \nid : 6\n";
		tmp += "nom : nom\nperiode : A\n";
		assertEquals(tmp, e.toString());		
	}
	
	@Test
	public void testVidageEtalage(){
		Etalage e = new Etalage(5);
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
		String tmp = "1e rangee de l'étalage\n-------------\n2e rangee de l'étalage\n";
		assertEquals(tmp, tmp.toString());
	}

}
