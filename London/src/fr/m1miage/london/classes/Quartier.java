package fr.m1miage.london.classes;

public class Quartier {
	int id;
	String nom;
	int prix;
	int point_victoire;
	int nb_carte_a_piocher;
	// métro possible sur le quartier ?
	Boolean metro;
	// y'a-t-il un métro sur le quartier ?
	Boolean metro_actif;
	// on peut investir ?
	Boolean investir_possible;
	// le joueur propriétaire du quartier
	Joueur priprietaire;
}
