package ejercicios.ejercicio2;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

import _datos.DatosEjercicio2;
import us.lsi.common.Set2;

public class CursoHeuristic {

	public static Double heuristic(CursoVertex v1, Predicate<CursoVertex> goal, CursoVertex v2) {
		//TODO
		/*		return v1.remaining().isEmpty()? 0.:  // 
			IntStream.range(v1.index(), DatosSubconjuntos.getNumSubconjuntos())
			.filter(i -> !List2.intersection(v1.remaining(), 
					DatosSubconjuntos.getElementos(i)).isEmpty())
			.mapToDouble(i -> DatosSubconjuntos.getPeso(i)).min().orElse(100.);
		 * 
		 */
		Predicate<Integer> prdct= i -> {
			//elimina aquellos numeros cuya adición cambiaria el n de centros a peor 
			Set<Integer> s = Set2.copy(v1.centers());
			s.add(DatosEjercicio2.getCentroCurso(i));
			return s.size()<= DatosEjercicio2.getMaxCentros();
		};
	ToDoubleFunction<Integer> funk = f->{
			// calcula la diferencias en tematicas que se pueden quitar 
			Set<Integer>tem = Set2.copy(v1.remaining());
			tem.removeAll(DatosEjercicio2.getTematicasCursos(f));
			return (double) tem.size();
			
		};
		
		//crea la secuencia de nnumeros 
		
		// Uso stream en lugar de int stream porque si no 
		
		 return v1.remaining().isEmpty()? 0.:IntStream.range(v1.index(), DatosEjercicio2.getNumCursos()).
				 boxed().filter(prdct).
				 mapToDouble((ToDoubleFunction<? super Integer>) funk).min().orElse(1000);
	}
}
