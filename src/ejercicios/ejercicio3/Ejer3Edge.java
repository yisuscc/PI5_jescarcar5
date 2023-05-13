package ejercicios.ejercicio3;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record Ejer3Edge(Ejer3Vertex source, Ejer3Vertex target, Integer action, Double weight)
		implements SimpleEdgeAction<Ejer3Vertex, Integer> {
	//como va a ser de tipo last y no de tipo sum, 
	
	/*
	 * Para convertirlo a tipo sum,
	 *  usar el metodo de calidad obtenida, si y averiguar las diferencias de calidades obtenidas en dichos vertices 
	 *  
	 */
	//el peso va a ser 1
	public static Ejer3Edge of(Ejer3Vertex s, Ejer3Vertex t, Integer a) {
		Double w = (s.calidadObtenida()!=t.calidadObtenida())?Math.abs(s.calidadObtenida()-t.calidadObtenida()):1.;
		return new Ejer3Edge(s, t, a, w);
	}

}
