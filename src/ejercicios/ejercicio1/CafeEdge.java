package ejercicios.ejercicio1;

import ejemplos.ejemplo1.MulticonjuntoVertex;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CafeEdge(CafeVertex source, CafeVertex target, Integer action, Double weight) implements SimpleEdgeAction<CafeVertex, Integer> {
// TODO el metodo of y el cambio del action a integer integer
	//implements SimpleEdgeAction<MulticonjuntoVertex,Integer> 
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}


}
