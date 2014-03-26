package test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.m1miage.london.classes.Carte;

public class CarteTest {

	@Test
	public void testToString() {
		// modif des test à faire
		Carte c = new Carte(1,"nom","A",2,"Rouge",1,1,1,"toto",1,true,1,1,1,1);
		String affichage = "id : 1\nnom : nom\nperiode : A\n";		
		assertEquals(affichage, c.toString());
	}

}
