package fr.m1miage.london.classes;

public class Effet {
	private int idEffet;
	private String nomEffet;
	private int type;
	
	public Effet(){
		
	}
	
	public Effet(int idEffet, String nomEffet, int type){
		this.idEffet = idEffet;
		this.nomEffet = nomEffet;
		this.type = type;
	}

	public int getIdEffet() {
		return idEffet;
	}

	public String getNomEffet() {
		return nomEffet;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		String msg="Effet \n\t";
				msg+="id : "+ idEffet + " - texte : " + nomEffet + " de type ";
		if(type==1){
			msg+="passif.\n";
		}else{
			msg+="actif.\n";
		}
		
		return msg;
	}

	
	
}
