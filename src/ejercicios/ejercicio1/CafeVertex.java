package ejercicios.ejercicio1;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosEjercicio1;
import _datos.DatosMulticonjunto;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CafeVertex(Integer index, List<Integer> remaining)implements VirtualVertex<CafeVertex, CafeEdge, Integer> {
	//el of
	public static CafeVertex of(Integer index, List<Integer> remaining) {
		return new CafeVertex(index, remaining);
	}
	// el INitial 
	public static  CafeVertex initial() {
		List<Integer> ls = DatosEjercicio1.
				getCafes().
				stream().map(c-> c.peso()).toList();
		return of(0,ls);
	}

	//TODO el GOal
	public static Predicate<CafeVertex> goal(){
		// si no furula probad con == 
		return v-> v.index().equals(DatosEjercicio1.getNumVarCafe());
	}

	//TODO El HasSolution
	public static Predicate<CafeVertex> goalHasSolution(){
		//TODO cambiar 
		/*
		 * sería solución si los elementos del remaining son
		 * mayores o iguale que 0 
		 * 
		 */
		return goal();
	}

	private static Boolean todosMayorIgualQueCero(List<Integer> ls) {
		Boolean res = true;
		for(Integer i: ls) {
			if(i<0) {
				res = false;
				break;
			}
		}
		return res;
	}
	private static  Integer limiteVarCafe(Integer index, List<Integer> rem) {
		/*Dados un remaining y un index devuelve 
		 * cual es el maxímo numeno de Kilos que se puede coger 
		 * de cada variedad
		 */
		
		List<Integer> rem2 = List2.copy(rem);
		Map<String, Double> comp  = DatosEjercicio1.getVariCafe().get(index).composicion();// si el map fuese initeger integer
		//seria mas sencillo
		Double min= comp.entrySet().stream().
				mapToDouble(es->rem.get(Integer.valueOf(es.getKey().replace('C',' '))-1)/es.getValue()).
				min().orElse(0);

		return min.intValue();
	}
	@Override
	public List<Integer> actions() {
		// TODO Auto-generated method stub
		List<Integer> alternativas = List2.empty();
		if(index<DatosEjercicio1.getNumVarCafe()) {

		}

		return alternativas;
	}

	@Override
	public CafeVertex neighbor(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CafeEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

	//	@Override
	//	public boolean equals(Object obj) {
	//		// TODO Auto-generated method stub
	//		return false;
	//	}
	//
	//	@Override
	//	public int hashCode() {
	//		// TODO Auto-generated method stub
	//		return 0;
	//	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
