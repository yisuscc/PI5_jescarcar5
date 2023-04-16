package ejercicios.ejercicio1;

import java.util.function.Predicate;
import java.util.stream.IntStream;

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
		//TODO
		Double res = 0.;
//		// 1 quito las variedades que  con la cantidad deisponible no pueda crear al menos un Kg
		// 2 lloro 
		// 3 de las variedades restantes calculo cuanto puedo coger 
		// 4 me quedo con el maximo y si no cero patatero 
		
		return res;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
