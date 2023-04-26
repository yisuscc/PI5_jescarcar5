package ejercicios.ejercicio4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import _datos.DatosEjercicio4;

import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record RepartoVertex(Integer cliente, Set<Integer> pendientes,List<Integer> visitados, Integer kms)
implements VirtualVertex<RepartoVertex, RepartoEdge, Integer> {
	//  Es inPair o integer? Creo que se puede hacero por los dos casos
	//el tsp de ejemplo iene con intpair
	//pero para facilitarmae la vida voy a intentarlo primero con Integer
//  of 
	public static RepartoVertex of(Integer c, Set<Integer> p, List<Integer> v, Integer k) {
		return new RepartoVertex(c, p, v, k);
	}
	
// return initial
	public static RepartoVertex initial() {
		Set<Integer>p =  Set2.range(1, DatosEjercicio4.getN()+1);
		return of(0,p,List2.of(0),0);
		
	}
// goal
	public static Predicate<RepartoVertex>goal(){
		// es n o n-1?
		return v->  v.cliente.equals(DatosEjercicio4.getN());
	}
// GOal Has Solution
	public  static Predicate<RepartoVertex>goalHasSolution(){
		return v-> v.pendientes().isEmpty();
	}
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		Integer n = DatosEjercicio4.getN();
		if(cliente()<n) {
			Set<Integer>vecinos = DatosEjercicio4.getVecinos(cliente);
			// caso estamos en el almacesn  cliente 0
			if(cliente().equals(0)) {
				alternativas = new ArrayList<>(vecinos);
			}else if(cliente().equals(n-1)) {//caso index-1
				//en este caso hay que comprobar que el ultimo vertice tenga arista con 0
				Set<Integer> diff = Set2.difference(vecinos, visitados());
			alternativas=  diff.isEmpty()?List2.empty(): diff.stream().filter(p-> DatosEjercicio4.getVecinos(p).contains(0)).toList();
			}else {
				Set<Integer> diff = Set2.difference(vecinos, visitados());
				alternativas = new ArrayList<>(diff);
			}
		}

		return alternativas;
	}

	@Override
	public RepartoVertex neighbor(Integer a) {
		Set<Integer> p = Set2.difference(this.pendientes(), Set.of(a));
		List<Integer> ls =List2.addLast(visitados(), a);
		
		Integer vertV= List2.last(this.visitados);
		Integer k =  this.kms()+DatosEjercicio4.getPesoArista(vertV, a).intValue();
		return of(cliente()+1, p, ls, k);
	}

	@Override
	public RepartoEdge edge(Integer a) {
		
		return RepartoEdge.of(this,neighbor(a), a);
	}
	// GreedyEdge
	public RepartoEdge greedyEdge() {
		Set<Integer>vecinos = DatosEjercicio4.getVecinos(cliente);
		Set<Integer> diff = Set2.difference(vecinos, visitados());
		Integer v = List2.last(visitados());
		Function<Integer, Double>f = i-> DatosEjercicio4.getBeneficioCliente(i)-DatosEjercicio4.getPesoArista(i, v);
		return edge(diff.stream().max(Comparator.comparing(f)).orElse(null));
	}




}
