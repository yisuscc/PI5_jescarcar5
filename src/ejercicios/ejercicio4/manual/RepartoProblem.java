package ejercicios.ejercicio4.manual;

import java.util.List;
import java.util.Set;

import _datos.DatosEjercicio4;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record RepartoProblem(Integer index, Integer cliente, Set<Integer> pendientes, List<Integer> visitados,
		Integer kms) {
//of 
	public static RepartoProblem of(Integer i, Integer c, Set<Integer> p, List<Integer> v, Integer k) {
		return new RepartoProblem(i, c, p, v, k);
	}

	// initial
	public static RepartoProblem initial() {
		Set<Integer> p = Set2.range(1, DatosEjercicio4.getN());
		return of(1, 0, p, List2.of(0), 0);

	}

	// actions
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		Integer n = DatosEjercicio4.getN();// para que el numero se corresponda con el numero de vertices del grafo

		// Notas, pendientes no incluye al vertice actual

		Set<Integer> vecinos = DatosEjercicio4.getVecinos(this.cliente());
		Set<Integer> sAux = Set2.intersection(this.pendientes(), vecinos);
		if (index < n && !sAux.isEmpty()) {

			if (index == n - 1) {// caso penultimo vertice
				// el vertice destino tiene que tener arista con el almacen
				alternativas = sAux.stream().filter(i -> DatosEjercicio4.existeArista(i, 0)).toList();
				// si no existiera creo que devolvería una lista vacía
			} else {// caso resto de vertices
				alternativas = List2.ofCollection(sAux);
			}
		}
		return alternativas;
	}

	// vecino
	public RepartoProblem neighbor(Integer a) {
		Set<Integer> p = Set2.difference(this.pendientes(), Set.of(a));
		List<Integer> ls = List2.addLast(visitados(), a);
		Integer k = this.kms() + DatosEjercicio4.getPesoArista(cliente(), a).intValue();
		return of(index + 1, a, p, ls, k);
	}
	// heuristica
	public Double heuristic() { //TODO Hacerl a heuristica
		return 0.;
	}

}
