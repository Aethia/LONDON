package fr.m1miage.london.classes;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Quartier;

public class QuartierTest {

	@Test
	public void testGetQuartiersAdjacents() {
		Quartier q1 = new Quartier();
		q1.setId(1);

		Quartier q2 = new Quartier();
		q2.setId(2);
		Set<Quartier> quartAdj = new HashSet<Quartier>();
		
		Quartier q3 = new Quartier();
		q3.setId(3);
		
		quartAdj.add(q1);
		quartAdj.add(q3);
		q2.setQuartiersAdjacents(quartAdj);
		
		assertEquals(quartAdj, q2.getQuartiersAdjacents());	

	}


	@Test
	public void testIsAuSudTamise() {
		Quartier q1 = new Quartier();
		q1.setId(1);
		q1.setAuSudTamise(true);

		Quartier q2 = new Quartier();
		q2.setId(2);
		Set<Quartier> quartAdj = new HashSet<Quartier>();
		
		Quartier q3 = new Quartier();
		q3.setId(3);
		
		quartAdj.add(q1);
		quartAdj.add(q3);
		
		q2.setQuartiersAdjacents(quartAdj);
		assertEquals(true, q1.isAuSudTamise());	
		assertEquals(false, q2.isAuSudTamise());	
		assertEquals(false, q3.isAuSudTamise());	
		
	}
	
	@Test
	public void testInvestitQuartier(){
		Joueur j = new Joueur(1,"toto",Color.black);
		Set<Quartier> quartAdj = new HashSet<Quartier>();
		Set<Quartier> quartAdj2 = new HashSet<Quartier>();
		Quartier q1 = new Quartier();
		q1.setId(1);
		q1.setInvestir_possible(true);
		q1.setAuSudTamise(true);
		// est ce que je peux investir ce quartier ?
		

		Quartier q2 = new Quartier();
		q2.setId(2);
		q2.setInvestir_possible(false);
		
		
		Quartier q3 = new Quartier();
		q3.setInvestir_possible(false);
		q3.setId(3);
		
		// q1 et q3 adjacent à q2
		quartAdj.add(q1);
		quartAdj.add(q3);
		q2.setQuartiersAdjacents(quartAdj);
		// q2 adjacent à q1
		quartAdj2.add(q2);
		q1.setQuartiersAdjacents(quartAdj2);
		// q2 adjacent à q3
		q3.setQuartiersAdjacents(quartAdj2);
		
		assertEquals(false, q3.isInvestir_possible());
		assertEquals(false, q2.isInvestir_possible());	
		assertEquals(true, q1.isInvestir_possible());
		q1.investirQuartier(j);
		assertEquals(false, q3.isInvestir_possible());
		assertEquals(true, q2.isInvestir_possible());	
		assertEquals(false, q1.isInvestir_possible());
		q2.investirQuartier(j);
		assertEquals(true, q3.isInvestir_possible());
		assertEquals(false, q2.isInvestir_possible());	
		assertEquals(false, q1.isInvestir_possible());
		q3.investirQuartier(j);
		assertEquals(false, q3.isInvestir_possible());
		assertEquals(false, q2.isInvestir_possible());	
		assertEquals(false, q1.isInvestir_possible());
	
		
		
	}

}
