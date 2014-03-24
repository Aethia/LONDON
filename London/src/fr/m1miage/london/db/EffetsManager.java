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
		//On cr�e une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			//On cr�e un nouveau document JDOM avec en argument le fichier XML
			document = sxb.build("ressources/Objects/effets.xml");
		}
		catch(Exception e){}
		//On initialise un nouvel �l�ment racine avec l'�l�ment racine du document.
		racine = document.getRootElement();

		List<Element> listeEffets = racine.getChildren("effet");

		//On cr�e un Iterator sur notre liste
		Iterator<?> i = listeEffets.iterator();
		while(i.hasNext())
		{
			//On recr�e l'Element courant � chaque tour de boucle afin de
			//pouvoir utiliser les m�thodes propres aux Element comme :
			//s�lectionner un n�ud fils, modifier du texte, etc...
			Element courant = (Element)i.next();
			Integer id = Integer.parseInt(courant.getChild("id").getText());
			String nom = courant.getChild("texte").getText();
			Integer type = Integer.parseInt(courant.getChild("type").getText());
			
			
			effets.put(id, new Effet(id,nom,type));
		}

		return effets;

	}
}
