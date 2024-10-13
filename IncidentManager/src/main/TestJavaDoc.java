package main;

/**
 * Classe que prova la creació de documentació amb javadoc
 * @author Xavier 
 * @version 1.0
 */

public class TestJavaDoc {
	
	/**
	 *  Default constructor
	 */
	public TestJavaDoc() {
		
	}
	
	/**
	 * Calcula el factorial de n.
	 * n! = n * (n-1) * (n-2) * (n-3) *...* 1
	 * @param n és el número al que es calcularà el factorial
	 * @return n! es el resultat del factorial de n
	 */
	
	public static double factorial(double n) {
		if (n == 0) {
			return 1;
		} else {
			double resultat = n * factorial(n-1);
			return resultat;
		}
	}

}
