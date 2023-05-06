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

public record RepartoVertex(Integer index,Integer cliente, Set<Integer> pendientes,List<Integer> visitados, Integer kms)
implements VirtualVertex<RepartoVertex, RepartoEdge, Integer> {
	//  Es inPair o integer? Creo que se puede hacero por los dos casos
	//el tsp de ejemplo iene con intpair
	//pero para facilitarmae la vida voy a intentarlo primero con Integer
//  of 
	public static RepartoVertex of(Integer i,Integer c, Set<Integer> p, List<Integer> v, Integer k) {
		return new RepartoVertex(i,c,p, v, k);
	}
	
// return initial
	public static RepartoVertex initial() {
		Set<Integer>p =  Set2.range(1, DatosEjercicio4.getN());
		return of(1,0,p,List2.of(0),0);
		
	}
// goal
	public static Predicate<RepartoVertex>goal(){
	
		return v->  v.index.equals(DatosEjercicio4.getN());
	}
// GOal Has Solution
	public  static Predicate<RepartoVertex>goalHasSolution(){
		return v-> v.pendientes().isEmpty() && DatosEjercicio4.getN().equals(v.visitados.size());
	}
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		Integer n = DatosEjercicio4.getN();// para que el numero se corresponda con el numero de vertices del grafo 
		
		// Notas, pendientes no incluye al vertice actual 
		
		Set<Integer> vecinos = DatosEjercicio4.getVecinos(this.cliente());
		Set<Integer> sAux = Set2.intersection(this.pendientes(),vecinos );
		if(index<n&& !sAux.isEmpty()) {
			
		if(index == n-1 ){// caso  penultimo vertice
			//el vertice destino tiene que tener arista con el almacen 
			alternativas= sAux.stream().filter(i-> DatosEjercicio4.existeArista(i, 0)).toList();
			//si no existiera creo que devolvería una lista vacía 
		}else {//caso resto de vertices
			alternativas = List2.ofCollection(sAux);
		
		}
			
		}
		return alternativas;
	}

	@Override
	public RepartoVertex neighbor(Integer a) {
		Set<Integer> p = Set2.difference(this.pendientes(), Set.of(a));
		List<Integer> ls =List2.addLast(visitados(), a);
		
		Integer k =  this.kms()+DatosEjercicio4.getPesoArista(cliente(), a).intValue();
		return of(index+1, a, p, ls, k);
	}

	@Override
	public RepartoEdge edge(Integer a) {
		
		return RepartoEdge.of(this,neighbor(a), a);
	}
	// GreedyEdge
	public RepartoEdge greedyEdge() {

		Integer c =  this.cliente;
		// primero un comparador
		Comparator<Integer> cmp = Comparator.comparing(f-> DatosEjercicio4.getBeneficioCliente(f)-DatosEjercicio4.getPesoArista(c, f));
		Integer max = Set2.intersection(pendientes(), DatosEjercicio4.getVecinos(cliente)).stream().max(cmp).get();
		 return edge(max);
	}




}
