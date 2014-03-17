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
		//On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try
		{
			//On crée un nouveau document JDOM avec en argument le fichier XML
			document = sxb.build(new File("ressources/Objects/cartes.xml"));
		}
		catch(Exception e){}
		//On initialise un nouvel élément racine avec l'élément racine du document.
		racine = document.getRootElement();

		List<Element> listeCartes = racine.getChildren("carte");

		//On crée un Iterator sur notre liste
		Iterator<?> i = listeCartes.iterator();
		while(i.hasNext())
		{
			//On recrée l'Element courant à chaque tour de boucle afin de
			//pouvoir utiliser les méthodes propres aux Element comme :
			//sélectionner un nœud fils, modifier du texte, etc...
			Element courant = (Element)i.next();
			Integer id = Integer.parseInt(courant.getChild("id").getText());
			String nom = courant.getChild("nom").getText();
			Integer prix = Integer.parseInt(courant.getChild("prix").getText());
			String couleur = courant.getChild("couleur").getText();

			String periode = courant.getChild("periode").getText();
			String image = courant.getChild("image").getText();

			Integer pointsVictoire = 0;
			if(courant.getChild("ptsVictoire") != null){
				pointsVictoire = Integer.parseInt(courant.getChild("ptsVictoire").getText());
			}

			Integer idEffet = 0;
			if(courant.getChild("id_Effet") != null){
				idEffet = Integer.parseInt(courant.getChild("id_Effet").getText());
			}
			
			Integer idEffetActivation = 0;
			if(courant.getChild("id_EffActivation")!=null){
				idEffetActivation = Integer.parseInt(courant.getChild("id_EffActivation").getText());
			}
			
			
			Integer id_CoutActivation = 0;
			if(courant.getChild("id_CoutActivation")!=null){
				id_CoutActivation = Integer.parseInt(courant.getChild("id_CoutActivation").getText());
			}
			boolean aRetourner = false;
			if(courant.getChild("aRetourner")!=null){
				aRetourner = Boolean.parseBoolean(courant.getChild("aRetourner").getText());
			}
			
			
			Integer argentActivation = 0;
			if(courant.getChild("argentActivation")!=null){
				argentActivation = Integer.parseInt(courant.getChild("argentActivation").getText());
			}
			Integer ptsVictActivation = 0;
			if(courant.getChild("ptsVictActivation")!=null){
				ptsVictActivation = Integer.parseInt(courant.getChild("ptsVictActivation").getText());
			}
			Integer ptsPauvretePerdus = 0;
			if(courant.getChild("ptsPauvretePerdus")!=null){
				ptsPauvretePerdus = Integer.parseInt(courant.getChild("ptsPauvretePerdus").getText());
			}
			Integer ptsPauvreteGagnes = 0;
			if(courant.getChild("ptsPauvreteGagnes")!=null){
				ptsPauvreteGagnes = Integer.parseInt(courant.getChild("ptsPauvreteGagnes").getText());
			}
			
		
			cartes.add(new Carte(id, nom, periode, prix, couleur, pointsVictoire, idEffet
					, idEffetActivation, image, id_CoutActivation, aRetourner, argentActivation,
					ptsVictActivation, ptsPauvretePerdus, ptsPauvreteGagnes));
		}

		return cartes;

	}
}
