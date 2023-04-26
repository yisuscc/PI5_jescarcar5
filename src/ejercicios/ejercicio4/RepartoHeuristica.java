package ejercicios.ejercicio4;

import java.util.Set;
import java.util.function.Predicate;

import ejercicios.ejercicio1.CafeVertex;
import us.lsi.common.Set2;

public class RepartoHeuristica {
	
	public static Double heuristic(RepartoVertex v1 , Predicate<RepartoVertex> goal, RepartoVertex v2) {
		Set<Integer> r = Set2.copy(v1.pendientes());
		return 0.;
	}
}
