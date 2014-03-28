package fr.m1miage.london.classes;

public class Effet {
	private int idEffet;
	private String nomEffet;
	private int type; //1 = passif, 2 = actif
	
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
		StringBuilder msg= new StringBuilder(); //"Effet \n\t";
				msg.append("id : ").append(idEffet).append(" - texte : ").append(nomEffet).append(" de type ");
		if(type==1){
			msg.append("passif.\n");
		}else{
			msg.append("actif.\n");
		}
		
		return msg.toString();
	}

	
	
}
