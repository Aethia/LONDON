package fr.m1miage.london;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Regles {
	public final static int PTPAUVRETE = 5;
	public final static int ARGENT = 20;
	public final static int NBMAXCARTES=9;
	public final static int NBCARTESDEPART=6;
	public final static int NBMAXJOUEURS=5;
	public final static int EMPRUNTMAX = 100;
	public static List<Point> listePoints = new ArrayList<Point>();
	public final static Point q0= new Point(0,0);
	public final static Point q1= new Point(1000,423);
	public final static Point q3= new Point(882, 350);
	public final static Point q8= new Point(1077, 135);
	public final static Point q7= new Point(969, 143);
	public final static Point q11= new Point(1127, 376);
	public final static Point q10= new Point(1223, 403);
	public final static Point q9= new Point(1091, 249);
	public final static Point q12= new Point(1209, 486);
	public final static Point q14= new Point(950, 412);
	public final static Point q13= new Point(1054, 406);
	public final static Point q16= new Point(674, 523);
	public final static Point q15= new Point(864, 416);
	public final static Point q2= new Point(1071, 342);
	public final static Point q6= new Point(896, 296);
	public final static Point q4= new Point(846, 245);
	public final static Point q20= new Point(739, 215);
	public final static Point q19= new Point(699, 264);
	public final static Point q17= new Point(713, 382);
	public final static Point q18= new Point(662, 344);
	public final static Point q5= new Point(807, 138);
	
	
	public Regles(){
		listePoints.add(q0);
		listePoints.add(q1);
		listePoints.add(q2);
		listePoints.add(q3);
		listePoints.add(q4);
		listePoints.add(q5);
		listePoints.add(q6);
		listePoints.add(q7);
		listePoints.add(q8);
		listePoints.add(q9);
		listePoints.add(q10);
		listePoints.add(q11);
		listePoints.add(q12);
		listePoints.add(q13);
		listePoints.add(q14);
		listePoints.add(q15);
		listePoints.add(q16);
		listePoints.add(q17);
		listePoints.add(q18);
		listePoints.add(q19);
		listePoints.add(q20);
			
	}
	
}

