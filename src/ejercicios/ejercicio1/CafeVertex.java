package ejercicios.ejercicio1;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import _datos.DatosEjercicio1;
import _datos.DatosMulticonjunto;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CafeVertex(Integer index, List<Integer> remaining)implements VirtualVertex<CafeVertex, CafeEdge, Integer> {
	//consisdero que el remaining debería ser una lista de double
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
		return v-> todosMayorIgualQueCero(v.remaining());
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
	public static  Integer limiteVarCafe(Integer index, List<Integer> rem) {
		/*Dados un remaining y un index devuelve 
		 * cual es el maxímo numeno de Kilos que se puede coger 
		 * de cada variedad
		 */
		
		List<Integer> rem2 = List2.copy(rem);
		Map<String, Double> comp  = DatosEjercicio1.getVariCafe().get(index).composicion();// si el map fuese initeger integer
		//seria mas sencillo
		Double min= comp.entrySet().stream().
				mapToDouble(es->rem.get(Integer.valueOf(es.getKey().replace('C',' ').trim())-1)/es.getValue()).
				min().orElse(0);

		return min.intValue();
	}
	@Override
	public List<Integer> actions() {
	
		List<Integer> alternativas = List2.empty();
		if(index<DatosEjercicio1.getNumVarCafe()) {//TODO No se si le hace falta algo más
			alternativas = List2.rangeList(0, limiteVarCafe(index, remaining)+1);

		}

		return alternativas;
	}
public static  List<Integer> decrementor(List<Integer> rem, Integer a, Integer ind){
	List<Integer> ls = List2.copy(rem);
	List<Integer> res = List2.empty();
	for(int p = 0; p<rem.size();p++) {
		Double r =ls.get(p)-a* DatosEjercicio1.getPorcentajeCafeVar(p, ind) ;
		res.add(p, r.intValue());
	}
	return ls;
}
	@Override
	public CafeVertex neighbor(Integer a) {
	
	
		return of(index+1,decrementor(remaining, a, index));
	}

	@Override
	public CafeEdge edge(Integer a) {
		
		return CafeEdge.of(this, neighbor(a),a);
	}
	//TODO GREEDY EDGE
	public CafeEdge greedyEdge() {
		//return existeMejorArista()? edge(0): edge(remaining/DatosMulticonjunto.getElemento(index));
		//return existeMejorArista()?edge(0):edge(limiteVarCafe(index, remaining));
		return edge(limiteVarCafe(index, remaining));
	}
	public Boolean existeMejorArista() {
/*
 * 	Integer max = IntStream.range(index+1, DatosMulticonjunto.getNumElementos())
				.map(i -> DatosMulticonjunto.getElemento(i))
				.filter(e -> remaining%e==0).max().orElse(0);
		return max > DatosMulticonjunto.getElemento(index);
 */
		Double max = Stream.iterate(0, i-> i<DatosEjercicio1.getNumVarCafe(), i-> i+1).
				mapToDouble(i->DatosEjercicio1.getBeneficioVar(i) * limiteVarCafe(i, remaining)).max().orElse(0);
		
			return max >(DatosEjercicio1.getBeneficioVar(index)* limiteVarCafe(index, remaining));

		
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
