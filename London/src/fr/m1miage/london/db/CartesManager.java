package fr.m1miage.london.db;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.CoutActivation;
import fr.m1miage.london.classes.Effet;

public class CartesManager {
	static Document document;
	static Element racine;

	public CartesManager(){

	}

	public static Set<Carte> getCartes(){
		/* Avant de charger les cartes, charger les effets. => objets Effet dans Carte*/
		Map<Integer,Effet> effets = EffetsManager.getEffets();

		Set<Carte> cartes = new HashSet<Carte>();
		//source : http://cynober.developpez.com/tutoriel/java/xml/jdom/
		//On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			//On crée un nouveau document JDOM avec en argument le fichier XML
			document = sxb.build("ressources/Objects/cartes.xml");
		}
		catch(Exception e){}
		//On initialise un nouvel élément racine avec l'élément racine du document.
		racine = document.getRootElement();

		List<Element> listeCartes = racine.getChildren("carte");

		//On crée un Iterator sur notre liste
		Iterator<?> i = listeCartes.iterator();
		while(i.hasNext())
		{
			Element courant = (Element)i.next();
			//elements obligatoires : id, nom, prix, couleur, periode, image
			Integer id = Integer.parseInt(courant.getChild("id").getText());
			String nom = courant.getChild("nom").getText();
			Integer prix = Integer.parseInt(courant.getChild("prix").getText());
			String couleur = courant.getChild("couleur").getText();
			String periode = courant.getChild("periode").getText();
			String image = courant.getChild("image").getText();

			Carte carte = new Carte(id,nom,periode,prix,couleur,image);

			/*--------------------   elements non obligatoires --------------------*/
			
			if(courant.getChild("ptsVictoire") != null){
				carte.setPointsVictoire(Integer.parseInt(courant.getChild("ptsVictoire").getText()));
			}
			/* plusieurs effets, on va recuperer l'id et le chercher dans la hashmap effets */
			List<Element> listeEffets = courant.getChildren("id_EffActivation");
			for(Element eft:listeEffets){
				int e = Integer.parseInt(eft.getText());
				if(effets.containsKey(e)==true){
					if(effets.get(e).getType()==1){
						carte.setEffet_passif(effets.get(e));
					}else{
						carte.setEffet_actif(effets.get(e));
					}
				}else{
					System.err.println("Problème dans le chargement de la carte, l'effet " + e +" n'existe pas.");
				}
			}

			/*s'il existe une balise activation, on va chercher d'autres elts*/
			if(courant.getChild("Activation")!=null){
				Element activ = courant.getChild("Activation");
				/*-------------- s'il y a un cout d'activation --------------*/
				if(activ.getChild("CoutActivation")!=null){
					Element coutActiv = activ.getChild("CoutActivation");
					CoutActivation coutActivationObject= new CoutActivation();
					if(coutActiv.getChild("id_Activation")!=null){
						coutActivationObject.setTypeActiv(Integer.parseInt(coutActiv.getChild("id_Activation").getText()));
					}
					if(coutActiv.getChild("MontantActivation")!=null){
						coutActivationObject.setLivresAPayer(Integer.parseInt(coutActiv.getChild("MontantActivation").getText()));
					}
					if(coutActiv.getChild("CouleurActivation")!=null){
						coutActivationObject.setCouleurADefausser(coutActiv.getChild("CouleurActivation").getText());
					}
					if(coutActiv.getChild("aRetourner")!=null){
						if(coutActiv.getChild("aRetourner").getText().equals("true")){
							coutActivationObject.setaRetourner(true);
						}else{
							coutActivationObject.setaRetourner(false);
						}
						
					}
					
				}

				/*------------- s'il y a un gain apres l'activation -----------*/
				if(activ.getChild("GainActivation")!=null){
					Element gainActiv = activ.getChild("GainActivation");
					if(gainActiv.getChild("ptsVictActivation")!=null){
						carte.setPtsVictActivation(Integer.parseInt(gainActiv.getChild("ptsVictActivation").getText()));
					}
					if(gainActiv.getChild("ptsPauvretePerdus")!=null){
						carte.setPtsPauvretePerdus(Integer.parseInt(gainActiv.getChild("ptsPauvretePerdus").getText()));
					}
					if(gainActiv.getChild("ptsPauvreteGagnes")!=null){
						carte.setPtsPauvreteGagnes(Integer.parseInt(gainActiv.getChild("ptsPauvreteGagnes").getText()));
					}
					if(gainActiv.getChild("MontantGagne")!=null){
						carte.setArgentActivation(Integer.parseInt(gainActiv.getChild("MontantGagne").getText()));
					}
					
				}
			}

			cartes.add(carte);
		}

		return cartes;

	}
}
