package ejercicios.ejercicio4;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import _datos.DatosEjercicio4;
import ejercicios.ejercicio1.CafeVertex;
import us.lsi.common.Set2;

public class RepartoHeuristica {
	
	public static Double heuristic(RepartoVertex v1 , Predicate<RepartoVertex> goal, RepartoVertex v2) {
		Double res =0.;
		Set<Integer> r = Set2.copy(v1.pendientes());
		Function<Integer,Double> funk= i -> DatosEjercicio4.getBeneficioCliente(i);
		List<Integer> clientes = r.stream().sorted(Comparator.comparing(funk).reversed()).toList();
		 Double kmsMedio= DatosEjercicio4.getMediaKms();//1.; si no funciona ponerlo a 1.
		 //se puede utilizar para averiguar la media DatosEjerrcicio4.getMediaKms
		for(Integer c: clientes) {
			res += DatosEjercicio4.getBeneficioCliente(c)-clientes.indexOf(c)*kmsMedio;
		}
		return res;
	}
//	public static Double pesoVoraz(RepartoVertex v1 , Predicate<RepartoVertex> goal, RepartoVertex v2) {
//		Double peso = 0.;
//		Set<Integer> pendientes = v1.pendientes();
//		Integer c = v1.cliente();
//		Integer kms = v1.kms();
//	
//	
//		while(!pendientes.isEmpty()) {
//			Function<Integer, Double> funk = f-> DatosEjercicio4.getBeneficioCliente(f)-DatosEjercicio4.getPesoArista(c, f);
//			Comparator<Integer> cmp = Comparator.comparing(funk);
//			Integer max = pendientes.stream().max(cmp).get();
//			pendientes.remove(max);
//			kms += DatosEjercicio4.getPesoArista(c, max).intValue();
//			Double ben = DatosEjercicio4.getBeneficioCliente(max);
//			peso += ben -kms;
//			c = max;
//		}
//		return peso;
//	}
}
