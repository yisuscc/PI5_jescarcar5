package ejercicios.ejercicio3;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record Ejer3Edge(Ejer3Vertex source,Ejer3Vertex target , Integer action,  Double weight)implements SimpleEdgeAction<Ejer3Vertex, Integer> {

}
