package fr.m1miage.london.sound;
//package fr.m1miage.london.sound;



public class SoundPlayer {
	public static Boolean musique = false;
	
	public static void jouerSon(String son) {
		Thread t1 = new Thread(new Sound("ressources/Sounds/"+son));
		t1.start();
	}


}
