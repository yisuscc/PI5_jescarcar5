package ejemplos.ejemplo2.manual;


import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import _datos.DatosSubconjuntos;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record SubconjuntosProblem(Integer index, Set<Integer> remaining) { // es el "vertice"
	
	public static SubconjuntosProblem initial() {
		return of(0, Set2.copy(DatosSubconjuntos.getUniverso()));
	}

	public static SubconjuntosProblem of(Integer i, Set<Integer> rest) {
		return new SubconjuntosProblem(i, rest);
	}
	
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if(index < DatosSubconjuntos.getNumSubconjuntos()) {
			if(remaining.isEmpty())// ya he cubierto todos los elementos del universo 
				alternativas = List2.of(0);
			else {
				Set<Integer> rest = Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
				if(rest.isEmpty()) {//me interesa coger ese subconjunto porque el remaining me lo deja listo,vacío
					alternativas = List2.of(1);
				} else if(rest.equals(remaining)) {
						alternativas = List2.of(0);
				} else {
					alternativas = List.of(1,0);
				}
			}
		}
		return alternativas;
	}
	
	public SubconjuntosProblem neighbor(Integer a) {
		// si a =, no cojo el subconjunto, dejo el remaining igual(le hago la copia)
		// si a = 1, cojo es subconjunto y me quedo con la diferencai
		// lo que me queda por cubri
		Set<Integer> rest = a==0? Set2.copy(remaining):
			Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
		return of(index+1, rest);
	}
	
	// Lo mas optimista: si todavia quedan por cubrir, el de menor peso de los que quedan lo hara
	public Double heuristic() {
		// slo mas optimista: si todavía quedan por cubrir, el de menor peso lo hara 
		return remaining.isEmpty()? 0.: 
			IntStream.range(index, DatosSubconjuntos.getNumSubconjuntos())
			.filter(i -> !List2.intersection(remaining, DatosSubconjuntos.getElementos(i)).isEmpty())
			//Penalizacion estilo genetico con el orElse
			.mapToDouble(i -> DatosSubconjuntos.getPeso(i)).min().orElse(100.);
	}

}
