package fr.m1miage.london.db;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import fr.m1miage.london.classes.Quartier;

public class QuartiersManager {
	static Document document;
	static Element racine;

	public static Map<Integer, Quartier> getQuartiers(){
		Map<Integer, Quartier> quartiers = new HashMap<Integer, Quartier>();
		
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			document = sxb.build("ressources/Objects/quartiers.xml");
		}
		catch(Exception e){}
		//On initialise un nouvel élément racine avec l'élément racine du document.
		racine = document.getRootElement();

		List<Element> listeQuartiers = racine.getChildren("quartier");
		
		for(Element q: listeQuartiers){
			Integer id = Integer.parseInt(q.getChild("id").getText());
			Quartier quartier;
			if(quartiers.containsKey(id)){ //s'il existe deja dans la liste on le recupere 
				 quartier = quartiers.get(id);
			}else{
				quartier= new Quartier();
				quartiers.put(id, quartier);
			}
			quartier.setId(id);
			quartier.setNom(q.getChild("nom").getText());
			if(q.getChildText("PossibleInvest").equals("true")){
				quartier.setInvestir_possible(true);
			}else{
				quartier.setInvestir_possible(false);
			}
			quartier.setPrix(Integer.parseInt(q.getChildText("prix")));
			quartier.setNb_carte_a_piocher(Integer.parseInt(q.getChildText("nbCarteAPiocher")));
			quartier.setPoint_victoire(Integer.parseInt(q.getChildText("ptsVictoire")));
			
			//on recupere tous les quartiers adjacents
			List<Element> qAdjacents = q.getChildren("QuartierAdja");
			Set<Quartier> listeAdja = new HashSet<Quartier>();
			for(Element qa : qAdjacents){ //parcourir la liste des quartiers adjacents
				if(!quartiers.containsKey(Integer.parseInt(qa.getText()))){ //s'il n'existe pas on l'ajoute
					quartiers.put(Integer.parseInt(qa.getText()), new Quartier());
				}
				listeAdja.add(quartiers.get(Integer.parseInt(qa.getText())));				
			}
			quartier.setQuartiersAdjacents(listeAdja);
			
			if(q.getChildText("SudTamise").equals("true")){
				quartier.setAuSudTamise(true);
			}else{
				quartier.setAuSudTamise(false);
			}
			
			if(q.getChildText("PossibleMetro").equals("true")){
				quartier.setMetro(true);
			}else{
				quartier.setMetro(false);
			}
		}
		
		return quartiers;
		
	}
}
