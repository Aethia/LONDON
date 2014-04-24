package fr.m1miage.london;


public enum GestionErreurs {
	NONE ("Action validée"),
	NOT_ENOUGH_MONEY ("Vous n'avez pas assez d'argent pour effectuer cette action"),
	DISABLED_QUARTIER("Ce quartier n'est pas disponible pour investir"),
	INCORRECT_NUMBER("Ce numéro n'est pas disponible"),
	INCORRECT_CARTE_DEFAUSSE("Vous ne pouvez pas defausser cette carte"),
	INCORRECT_CARTE("Ce numéro de carte n'est pas disponible"),
	CARTE_NON_TROUVEE("Impossible de trouver cette carte"),
	CARTE_NON_ACTIVABLE("Impossible d'activer cette carte"),
	CARTE_DEFAUSSE_MANQUE("Vous devez choisir une carte à defausser"),
	CARTE_DEFAUSSE_COULEUR("Vous devez choisir une couleur compatible pour la carte à défausser"),
	DEFAUSSE_INDISPO("Aucune carte à defausser"),
	MAX_EMPRUNT("Vous avez atteint la limite pour emprunter (100£)"),
	MONTANT_INCORRECT("Montant incorrect, veuillez inserer une valeur entiere multiple de 10 inférieure à 100"),
	NOT_ENOUGH_CARD("Il n'y a plus de carte � piocher"),
	NON_CONSTRUCTIBLE_CARD("Cette carte n'est pas constructible"),
	NON_ACTIVABLE_CARD("Cette carte n'est pas activable"),
	NO_ROSE_CARD("Vous n'avez pas de carte rose"),
	NO_BRUN_CARD("Vous n'avez pas de carte brune"),
	NO_BLEU_CARD("Vous n'avez pas de carte bleue"),
	NO_GRIS_CARD("Vous n'avez pas de carte grise"),
	NONEXISTANT_PLAYER("Ce joueur n'existe pas"),
	WRONG_PLAYER("Vous ne pouvez pas réaliser cette action sur vous même"),
	NOT_ENOUGH_PAUPERS("Vous n'avez pas assez de point de pauvreté"),
	PIOCHE_VIDE("Il n'y a plus de cartes dans la pioche")
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
