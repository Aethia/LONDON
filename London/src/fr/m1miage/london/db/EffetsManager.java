package fr.m1miage.london.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Effet;

public class EffetsManager {
	static Document document;
	static Element racine;
	
	public static Map<Integer, Effet> getEffets(){
		Map<Integer, Effet> effets = new HashMap<Integer, Effet>();
		//source : http://cynober.developpez.com/tutoriel/java/xml/jdom/
		//On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			//On crée un nouveau document JDOM avec en argument le fichier XML
			document = sxb.build("ressources/Objects/effets.xml");
		}
		catch(Exception e){}
		//On initialise un nouvel élément racine avec l'élément racine du document.
		racine = document.getRootElement();

		List<Element> listeEffets = racine.getChildren("effet");

		//On crée un Iterator sur notre liste
		Iterator<?> i = listeEffets.iterator();
		while(i.hasNext())
		{
			//On recrée l'Element courant à chaque tour de boucle afin de
			//pouvoir utiliser les méthodes propres aux Element comme :
			//sélectionner un nœud fils, modifier du texte, etc...
			Element courant = (Element)i.next();
			Integer id = Integer.parseInt(courant.getChild("id").getText());
			String nom = courant.getChild("texte").getText();
			Integer type = Integer.parseInt(courant.getChild("type").getText());
			
			
			effets.put(id, new Effet(id,nom,type));
		}

		return effets;

	}
}
