package fr.m1miage.london.ui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.Color;

import fr.m1miage.london.ui.graphics.AreaColorRect;

public class Prefs {

	public static final int HAUTEUR_FENETRE = 800;
	public static final int LARGEUR_FENETRE = 1400;
	public static final String REPERTOIRE = "ressources/Images/";
	public static final String REPERTOIRE_CARTES = "ressources/Images/Cartes/";
	public static final String REPERTOIRE_QUARTIERS = "ressources/Images/Quartiers/";
	public static final String REPERTOIRE_EMPRUNTS = "ressources/Images/Boutons/Emprunts/";
	public static final String REPERTOIRE_ACTIONS = "ressources/Images/Boutons/Actions/";
	public static final String REPERTOIRE_BOUTONS = "ressources/Images/Boutons/";
	public static final String REPERTOIRE_ICONES = "ressources/Images/Icones/";
	

	public static final int HAUTEUR_CARTE = 300;
	public static final int LARGEUR_CARTE = 200;
	
	public static List<Point> listePoints = new ArrayList<Point>();
	public final static Point q0= new Point(0,0);
	public final static Point q1= new Point(1010,435);
	public final static Point q3= new Point(882, 350);
	public final static Point q8= new Point(1077, 555);
	public final static Point q7= new Point(969, 543);
	public final static Point q11= new Point(1127, 300);
	public final static Point q10= new Point(1223, 280);
	public final static Point q9= new Point(1091, 449);
	public final static Point q12= new Point(1209, 150);
	public final static Point q14= new Point(940, 280);
	public final static Point q13= new Point(1054, 285);
	public final static Point q16= new Point(674, 180);
	public final static Point q15= new Point(864, 280);
	public final static Point q2= new Point(1071, 355);
	public final static Point q6= new Point(896, 296);
	public final static Point q4= new Point(846, 440);
	public final static Point q20= new Point(745, 470);
	public final static Point q19= new Point(699, 430);
	public final static Point q17= new Point(713, 305);
	public final static Point q18= new Point(662, 344);
	public final static Point q5= new Point(790, 548);
	
	
	public Prefs(){
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
	
	public static Color conversionCouleur(java.awt.Color c){
		System.out.println("huahuahu"+ (float)c.getRed()/255);
		return new Color((float)c.getRed()/255,(float)c.getGreen()/255,(float)c.getBlue()/255,1);
	}
}
