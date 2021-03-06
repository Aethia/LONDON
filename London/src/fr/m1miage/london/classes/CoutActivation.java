package fr.m1miage.london.classes;

import java.io.Serializable;

/**
 * Classe CoutActivation qui stocke toutes les informations nécessaires pour l'activation de la carte
 */

public class CoutActivation implements Serializable {
    /**
     * le type d'activation de la carte (1:argent, 2:carte d'une couleur précise, 3:carte n'importe quelle couleur)
     */
	private int typeActiv; 
    /**
     * si le type activation est 1,le montant est renseigné
     */
	private int livresAPayer;
    /**
     * si le type activation est 2,la couleur est renseigné
     */
	private String couleurADefausser;
    /**
     * si on doit retourné la carte une fois activé le boolean est à true, false sinon
     */
	private boolean aRetourner;
	
	public CoutActivation(){
		
	}
	
	
	public CoutActivation(int typeActiv, int livresAPayer,
			String couleurADefausser) {
		super();
		this.typeActiv = typeActiv;
		this.livresAPayer = livresAPayer;
		this.couleurADefausser = couleurADefausser;
	}



	public int getTypeActiv() {
		return typeActiv;
	}
	public void setTypeActiv(int typeActiv) {
		this.typeActiv = typeActiv;
	}
	public int getLivresAPayer() {
		return livresAPayer;
	}
	public void setLivresAPayer(int livresAPayer) {
		this.livresAPayer = livresAPayer;
	}
	public String getCouleurADefausser() {
		return couleurADefausser;
	}
	public void setCouleurADefausser(String couleurADefausser) {
		this.couleurADefausser = couleurADefausser;
	}


	public boolean isaRetourner() {
		return aRetourner;
	}


	public void setaRetourner(boolean aRetourner) {
		this.aRetourner = aRetourner;
	}


	@Override
	public String toString() {
		StringBuilder msg = new StringBuilder();
		msg.append("CoutActivation :");
		switch(typeActiv){
		case 0 : msg.append(" Aucun ");
		break;
		case 1 : msg.append(" Montant en Livres ( ").append(livresAPayer).append("� )");
		break;
		case 2 : msg.append(" Carte de couleur unique ( ").append(couleurADefausser).append(" )");
		break;
		case 3 : msg.append(" N'importe quelle couleur de carte ");
		break;
		}
		return msg.toString();
	}
	
	
	
}
