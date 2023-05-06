package ejercicios.ejercicio3;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record Ejer3Edge(Ejer3Vertex source, Ejer3Vertex target, Integer action, Double weight)
		implements SimpleEdgeAction<Ejer3Vertex, Integer> {
	//como va a ser de tipo last y no de tipo sum, 
	//el peso va a ser 1
	public static Ejer3Edge of(Ejer3Vertex s, Ejer3Vertex t, Integer a) {
		return new Ejer3Edge(s, t, a, 1.);
	}

}
