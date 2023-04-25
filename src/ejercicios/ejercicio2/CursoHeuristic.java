package ejercicios.ejercicio2;

import java.util.List;
import java.util.Set;

import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

import _datos.DatosEjercicio2;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public class CursoHeuristic {

	public static Double heuristic(CursoVertex v1, Predicate<CursoVertex> goal, CursoVertex v2) {
		// version voraz de la heuristica
		//return pesoVoraz(v1);
	return altHeur(v1, goal, v2);
	}

	// la heuristica de este problema es literalmente el problema del ejempo 2
	//que estoy atontao 
	private static Double altHeur(CursoVertex v1, Predicate<CursoVertex> goal, CursoVertex v2) {
		/*
		 * return v1.remaining().isEmpty() ? 0. : //
				IntStream.range(v1.index(), DatosSubconjuntos.getNumSubconjuntos())
						.filter(i -> !List2.intersection(v1.remaining(), DatosSubconjuntos.getElementos(i)).isEmpty())
						.mapToDouble(i -> DatosSubconjuntos.getPeso(i)).min().orElse(100.);
		 */
		//creo que en este caso el  n de centros se puede ignorar 
		return v1.remaining().isEmpty()?0.: IntStream.range(v1.index(), DatosEjercicio2.getNumCursos()).
				filter(i->!List2.intersection(v1.remaining(),DatosEjercicio2.getTematicasCursos(i)).isEmpty()).
				filter(i-> Set2.union(v1.centers(), Set2.of(DatosEjercicio2.getCentroCurso(i))).size()<= DatosEjercicio2.getMaxCentros() ).
				mapToDouble(i-> DatosEjercicio2.getPrecioCurso(i)).min().orElse(100.);
	}
	private static Double pesoVoraz(CursoVertex v1) {
		/*
		 * Set<Integer> rem = Set2.difference(remaining,
		 * DatosEjercicio2.getTematicasCursos(index)); Set<Integer> cent =
		 * Set2.union(centers(), Set2.of(DatosEjercicio2.getCentroCurso(index)));
		 * Boolean cond = !rem.equals(remaining) && cent.size()<=
		 * DatosEjercicio2.getMaxCentros(); return cond?edge(1):edge(0);
		 */
		Double peso = 0.;
		Integer i = v1.index();
		Set<Integer> rmnng = Set2.copy(v1.remaining());
		Set<Integer> cntrs = Set2.copy(v1.centers());
		while (i < DatosEjercicio2.getNumCursos() && cntrs.size() <= DatosEjercicio2.getMaxCentros()) {
			Set<Integer> rem = Set2.difference(rmnng, DatosEjercicio2.getTematicasCursos(i));
			Set<Integer> cent = Set2.union(cntrs, Set2.of(DatosEjercicio2.getCentroCurso(i)));
			Boolean cond = !rem.equals(rmnng) && cent.size() <= DatosEjercicio2.getMaxCentros();
			if (cond) {
				rmnng = rem;
				cntrs = cent;
				peso += DatosEjercicio2.getPrecioCurso(i);
			}
			i++;
		}

		return peso;
	}

	public static Double heuristicV2(CursoVertex v1, Predicate<CursoVertex> goal, CursoVertex v2) {
		// Da muchos problemas, pero la dejo por si acaso

		/*
		 * return v1.remaining().isEmpty()? 0.: // IntStream.range(v1.index(),
		 * DatosSubconjuntos.getNumSubconjuntos()) .filter(i ->
		 * !List2.intersection(v1.remaining(),
		 * DatosSubconjuntos.getElementos(i)).isEmpty()) .mapToDouble(i ->
		 * DatosSubconjuntos.getPeso(i)).min().orElse(100.);
		 * 
		 */
		Predicate<Integer> prdct = i -> {
			// elimina aquellos numeros cuya adición cambiaria el n de centros a peor
			Set<Integer> s = Set2.copy(v1.centers());
			s.add(DatosEjercicio2.getCentroCurso(i));
			return s.size() <= DatosEjercicio2.getMaxCentros();
		};
		ToDoubleFunction<Integer> funk = f -> {
			// calcula la diferencias en tematicas que se pueden quitar
			Set<Integer> tem = Set2.copy(v1.remaining());
			tem.removeAll(DatosEjercicio2.getTematicasCursos(f));
			return (double) tem.size();

		};

		// crea la secuencia de nnumeros

		return v1.remaining().isEmpty() ? 0.
				: IntStream.range(v1.index(), DatosEjercicio2.getNumCursos()).boxed().filter(prdct)
						.mapToDouble((ToDoubleFunction<? super Integer>) funk).min().orElse(1000);

	}
}
