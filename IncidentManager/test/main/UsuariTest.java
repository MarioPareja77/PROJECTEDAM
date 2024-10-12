package main;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import main.Usuari;

class UsuariTest {

	@Test
	void creacioUsuari() {
		Usuari user = new Usuari(001, "Emma");
		user.afegirUsuari(1, user);
		Usuari resultat = user.obtenirUsuari(1);
		assertEquals(user, resultat);
		
		user.userMap.entrySet().stream() 
		    .forEach(entry -> System.out.println("Clau d'usuari: " + entry.getKey() + ": " + entry.getValue().toString()));
		
	}
	

}
