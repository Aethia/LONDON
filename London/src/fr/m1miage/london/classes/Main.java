package fr.m1miage.london.classes;

import java.util.ArrayList;
import java.util.List;

public class Main implements Cloneable{
	// les cartes qui constituent la main
	private List<Carte> lesCartes = new ArrayList<Carte>(); 
	
	public Main(){
	}
	
	// ajouter une carte dans la main
	public void ajouterCarte(Carte c){
		lesCartes.add(c);
	}
	
	// suppression d'une carte dans la main par l'indice dans la main
	public void supprimerCarteParIndice(int indice){
		try {
			lesCartes.remove(indice);
		}
		catch (IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("erreur lors de la suppression");
		}
	}
	
	// suppression d'une carte dans la main par l'id de carte
	public Boolean supprimerCarteParId(int idCarte){
		for (Carte c : lesCartes) {
			if (c.getId_carte() == idCarte) {
				lesCartes.remove(c);
				return true;
			}
		}
		return false;
	}
	
	/*
	 * retourner (une copie) des cartes de la main
	 */
	public List<Carte> getLesCartes() {
		return new ArrayList<Carte>(lesCartes) ;
	}

	/*
	 * retourner le nb de cartes dans la main
	 */
	public int getNb_cartes() {
		return lesCartes.size();
	}

	@Override
	public String toString() {
		String tmp = "";
		for(Carte c : lesCartes) {
			tmp += c.toString();
			tmp += "----------\n";
		}
		return tmp;
	}
	
	public void ajouterCartes(List<Carte> cartes){
		lesCartes.addAll(cartes);
	}

	/*
	 * v�rifier si le joueur peut ou non finir son tour
	 */
	public Boolean VerifierQteCarteFinDeTour(){
		if (this.getNb_cartes() <= 9)
			return true;
		else
			return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 * impl�mentation de l'interface Clonable
	 */
	public Main clone() {
	    Main main = null;
	    try {
	    	main = (Main) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	cnse.printStackTrace(System.err);
	    }
	    // on renvoie le clone
	    return main;
	}
	
	public Carte choisirCarte(int idCarte){
		for (Carte c : lesCartes) {
			if (c.getId_carte() == idCarte) {
				return c;
			}
		}
		return null;
		
	}
	
	//on v�rifie que la carte choisie par la joueur existe dans sa main
	public boolean verifPresenceCarte(Carte carte, boolean couleur){
		boolean presence=false;
		if(couleur == false){
			for(Carte c:lesCartes){
				if(c.getId_carte() == carte.getId_carte()){
					System.out.println("La carte existe dans la main");
					presence = true;
				}
			}
		}
		else{
			for(Carte c:this.getCartesCouleur(carte)){
				if(c.getId_carte() == carte.getId_carte()){
					System.out.println("La carte de m�me couleur existe");
					presence = true;
				}
			}
		}
		return presence;
	}
	
	//affiche les cartes de la m�me couleur qu'une carte choisie
	public List<Carte> getCartesCouleur(Carte c){
		String couleur = c.getCouleur();
		List<Carte> cartesCouleur = new ArrayList<Carte>();
		for(Carte i:lesCartes){
			if(i.getCouleur().compareTo(couleur)==0 && i!=c){
				cartesCouleur.add(c);
			}
		}
		return cartesCouleur;
	}
	
	

	
}
