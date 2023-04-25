package ejercicios.ejercicio2.manual;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import _datos.DatosEjercicio2;
import _datos.DatosEjercicio2.Curso;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record CursoProblem(Integer index, Set<Integer> remaining, Set<Integer> centers) {

	// of
	public static CursoProblem of(Integer i, Set<Integer> r, Set<Integer> c) {
		return new CursoProblem(i, r, c);
	}
	// INITIAL

	public static CursoProblem initial() {

		return of(0, Set2.copy(DatosEjercicio2.getTematicas()), Set2.empty());
	}
	// TODO Rehacer el actions

	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		
		if (index < DatosEjercicio2.getNumCursos()) {
			// comprobar si reduce las tematicas restantes y si los centros no superan los
			// maximos
			Curso cAux = DatosEjercicio2.getCursos().get(index);
			Integer cntr = cAux.centro();
			Set<Integer> tmtcs = cAux.tematica();
			Set<Integer> rmnng = Set2.copy(remaining);
			Set<Integer> cntrs = Set2.union(centers, Set2.of(cntr));
			Set<Integer> rest = Set2.difference(rmnng, tmtcs);
		

			if (!rest.equals(rmnng) && cntrs.size() <= DatosEjercicio2.getMaxCentros() && !rmnng.isEmpty()) {
				alternativas = List2.of(0, 1);
			} else {
				alternativas = List2.of(0);
			}

		}
		return alternativas;
	}

	// neighbor
	public CursoProblem neighbor(Integer a) {
		Set<Integer> t = Set2.copy(remaining());
		Set<Integer> c = Set2.copy(centers());
		if(a!=0) {
			t = Set2.difference(remaining(), DatosEjercicio2.getTematicasCursos(index));
			c = Set2.union(centers(), Set2.of(DatosEjercicio2.getCentroCurso(index)));
		}
		return of(index + 1, t, c);
	}

	// La heuristica
	public Double heuristic() {
		// me baso en la del ejemplo 2
		return remaining()
				.isEmpty()
						? 0.
						: IntStream
								.range(index(),
										DatosEjercicio2.getNumCursos())
								.filter(i -> !List2.intersection(remaining(), DatosEjercicio2.getTematicasCursos(i))
										.isEmpty())
								.filter(i -> Set2.union(centers(), Set2.of(DatosEjercicio2.getCentroCurso(i)))
										.size() <= DatosEjercicio2.getMaxCentros())
								.mapToDouble(i -> DatosEjercicio2.getPrecioCurso(i)).min().orElse(100.);
	}

	// greedy Integer
	public Integer greedyInteger() {
		// este caso es biniario asýi que es facil
		// se coge si no supera el maximo de centro y si el el remaining se reduce
		Set<Integer> t = Set2.difference(remaining(), DatosEjercicio2.getTematicasCursos(index));
		Set<Integer> c = Set2.union(centers(), Set2.of(DatosEjercicio2.getCentroCurso(index)));
		Boolean cond = t.size() < remaining().size() && c.size() <= DatosEjercicio2.getMaxCentros();
		return cond ? 1 : 0;
	}

	// esSol
	public Boolean esSol() {
		return centers().size() <= DatosEjercicio2.getMaxCentros() && remaining().isEmpty();
	}

}
