package ejercicios.ejercicio4;


import us.lsi.common.IntPair;

import us.lsi.graphs.virtual.SimpleEdgeAction;

//TravelVertex source, TravelVertex target, IntPair action, Double weight
public record RepartoEdge(RepartoVertex source, RepartoVertex target,IntPair action, Double weight )implements SimpleEdgeAction<RepartoVertex, IntPair> {
// Hacer el metodo of
}
