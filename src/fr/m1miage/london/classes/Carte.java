package fr.m1miage.london.classes;

public class Carte {
	private String nom;
	private String periode;
	
	public Carte(){
	}
	
	public Carte(String nom, String periode){
		this.nom = nom;
		this.periode = periode;
	}
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}

	@Override
	public String toString() {
		return "Carte [nom=" + nom + ", periode=" + periode + "]";
	}
	
	
	
}
