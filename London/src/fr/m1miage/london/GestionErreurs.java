package fr.m1miage.london;


public enum GestionErreurs {
	NONE ("Action validée"),
	NOT_ENOUGH_MONEY ("Vous n'avez pas assez d'argent pour effectuer cette action"),
	DISABLED_QUARTIER("Ce quartier n'est pas disponible pour investir"),
	INCORRECT_NUMBER("Ce numéro n'est pas disponible"),
	INCORRECT_CARTE_DEFAUSSE("Ce numéro de carte à défausser n'est pas disponible"),
	INCORRECT_CARTE("Ce numéro de carte n'est pas disponible"),
	DEFAUSSE_INDISPO("Aucune carte à defausser"),
	MAX_EMPRUNT("Vous avez atteint la limite pour emprunter (100£)"),
	MONTANT_INCORRECT("Montant incorrect, veuillez inserer une valeur entiere multiple de 10 inférieure à 100"),
	;
	
private String name = "";
	
	GestionErreurs(String ename){
		this.name= ename;
	}
	
	public void getMsgError(){
       System.out.println(name);
	  }
	public String getMsgErrorString(){
        return name;
	  }

}
