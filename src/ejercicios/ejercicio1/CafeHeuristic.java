package ejercicios.ejercicio1;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio2;
import us.lsi.common.List2;



public class CafeHeuristic {
 /*
  * 	// Se explica en practicas.
	public static Double heuristic(MulticonjuntoVertex v1, Predicate<MulticonjuntoVertex> goal,
			MulticonjuntoVertex v2) {
		
		Double res = 0.;
		if(v1.remaining()>0) {
			Integer max = IntStream.range(v1.index(), DatosMulticonjunto.getNumElementos())
					.map(i -> DatosMulticonjunto.getElemento(i))
					.filter(e -> e<=v1.remaining()).max().orElse(0);
			if(max>0) {
				Integer r = v1.remaining()/max;
				res += v1.remaining()%max==0? r: r+1;				
			} else 
				res += 100;
		} else if(v1.remaining()<0)
			res += 100;
		
		return res; 
	}
  */
	public static Double heuristic(CafeVertex v1, Predicate<CafeVertex> goal, CafeVertex v2) {
		return pesoVoraz(v1);
	}
	
	public static Double heuristicV2(CafeVertex v1, Predicate<CafeVertex> goal, CafeVertex v2) {
		
		
//		// 1 quito las variedades que  con la cantidad deisponible no pueda crear al menos un Kg
		ToDoubleFunction<Integer> funk = i-> {
			return CafeVertex.limiteVarCafe(i,v1.remaining())*DatosEjercicio1.getBeneficioVar(i);
		};
		// 2 lloro 
		// 3 de las variedades restantes calculo cuanto puedo coger  y lo multiplico por el beneficio
		
		// 4 me quedo con el maximo y si no cero patatero 
		
		return IntStream.range(0, DatosEjercicio1.getNumVarCafe()).
				boxed().mapToDouble(funk).max().orElse(0);
	}
	public static  Double pesoVoraz( CafeVertex v1) {
		Double peso = 0.;
		Integer i = v1.index();
		
		List<Integer> rem = v1.remaining();
		while(i< DatosEjercicio1.getNumVarCafe()) {
			Integer a = CafeVertex.limiteVarCafe(i, rem);
			rem = CafeVertex.decrementor(rem, a, i);
			peso += a*DatosEjercicio1.getBeneficioVar(i);
			i++;
			
		}
		return peso;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
