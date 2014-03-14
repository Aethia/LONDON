package fr.m1miage.london.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import fr.m1miage.london.classes.Carte;

public class CartesManager {
	static Document document;
	static Element racine;

	public CartesManager(){

	}

	public static List<Carte> getCartes(){
		List<Carte> cartes = new ArrayList<Carte>();
		//source : http://cynober.developpez.com/tutoriel/java/xml/jdom/
		//On cr�e une instance de SAXBuilder
	      SAXBuilder sxb = new SAXBuilder();
	      try
	      {
	         //On cr�e un nouveau document JDOM avec en argument le fichier XML
	         document = sxb.build(new File("ressources/Objects/cartes.xml"));
	      }
	      catch(Exception e){}
	      //On initialise un nouvel �l�ment racine avec l'�l�ment racine du document.
	      racine = document.getRootElement();

	      List<Element> listeCartes = racine.getChildren("carte");

	      //On cr�e un Iterator sur notre liste
	      Iterator<?> i = listeCartes.iterator();
	      while(i.hasNext())
	      {
	         //On recr�e l'Element courant � chaque tour de boucle afin de
	         //pouvoir utiliser les m�thodes propres aux Element comme :
	         //s�lectionner un n�ud fils, modifier du texte, etc...
	         Element courant = (Element)i.next();
	         
	         String nom = courant.getChild("nom").getText();
	         String periode = courant.getChild("periode").getText();
	         cartes.add(new Carte(nom, periode));
	      }
	      
	      return cartes;
		
	}
}
