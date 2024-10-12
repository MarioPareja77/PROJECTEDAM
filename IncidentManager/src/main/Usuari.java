package main;

import java.util.HashMap;

public class Usuari {
	
	HashMap<Integer, Usuari> userMap = new HashMap<>();
	
	private int id;
	private String nom;
	
	public Usuari(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}
	
	public void afegirUsuari(int userNumber, Usuari user) {	
		userMap.put(userNumber, user);	
	}
	
	public Usuari obtenirUsuari(int userNumber) {	
		return userMap.get(userNumber);	
	}
	
	@Override
	public String toString() {
		return "Usuari[id=" + id + ", nom=" + nom + "]";
	}

}
