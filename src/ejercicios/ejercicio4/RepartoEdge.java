package ejercicios.ejercicio4;


import _datos.DatosEjercicio4;
import us.lsi.common.IntPair;

import us.lsi.graphs.virtual.SimpleEdgeAction;

//TravelVertex source, TravelVertex target, IntPair action, Double weight
public record RepartoEdge(RepartoVertex source, RepartoVertex target,Integer action, Double weight )implements SimpleEdgeAction<RepartoVertex, Integer> {
// Hacer el metodo of
	public static RepartoEdge of(RepartoVertex s, RepartoVertex t,Integer a) {
		// averiguamos el beneficio
		Integer vertS = s.visitados().get(s.visitados().size()-1);
		Integer vertT = a; 
		Double b = DatosEjercicio4.getBeneficioCliente(a);
		//2 averiguamos la penalización
		Double pen = s.kms()+ DatosEjercicio4.getPesoArista(vertS, vertT);
		//3 calculamos el peso(no tiene en cuenta el coste de la gasolina
		//ni el coste de vuelta al almacén)
		Double w = b-pen;
		return new RepartoEdge(s, t, a, w);
	}
}
