package fr.m1miage.london.classes;

public class Quartier {
	int id;
	String nom;
	int prix;
	int point_victoire;
	int nb_carte_a_piocher;
	// m�tro possible sur le quartier ?
	Boolean metro;
	// y'a-t-il un m�tro sur le quartier ?
	Boolean metro_actif;
	// on peut investir ?
	Boolean investir_possible;
	// le joueur propri�taire du quartier
	Joueur priprietaire;
}
