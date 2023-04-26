package ejercicios.ejercicio2;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.alg.scoring.AlphaCentrality;

import _datos.DatosEjercicio2;
import _datos.DatosSubconjuntos;
import _datos.DatosEjercicio2.Curso;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CursoVertex(Integer index, Set<Integer> remaining, Set<Integer> centers)
		implements VirtualVertex<CursoVertex, CursoEdge, Integer> {

	// of
	public static CursoVertex of(Integer i, Set<Integer> r, Set<Integer> c) {
		return new CursoVertex(i, r, c);
	}

	// INitial
	public static CursoVertex initial() {
		Set<Integer> sT = Set2.copy(DatosEjercicio2.getTematicas());
		return of(0, sT, Set2.empty());
	}

	// goal
	public static Predicate<CursoVertex> goal() {
		return v -> v.index() == DatosEjercicio2.getNumCursos();
	}

	// goalHasSolution
	public static Predicate<CursoVertex> goalHasSolution() {
		return v -> v.remaining().isEmpty() && v.centers.size() <= DatosEjercicio2.getMaxCentros();
	}

	@Override
	public List<Integer> actions() {//TODO Rehacer
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

			if (!rest.equals(rmnng) && cntrs.size() <= DatosEjercicio2.getMaxCentros()&& !rmnng.isEmpty()) {
				alternativas = List2.of(0, 1);
			} else {
				alternativas = List2.of(0);
			}

		}
		return alternativas;
	}

	@Override
	public CursoVertex neighbor(Integer a) {
		// Caso si a es igual a 1 caso si a es igual a cero
		Set<Integer> rem = Set2.copy(remaining());
		Set<Integer> cent = Set2.copy(centers());
		if (a != 0) {
			rem = Set2.difference(remaining, DatosEjercicio2.getTematicasCursos(index));
			cent.add(DatosEjercicio2.getCentroCurso(index));
		}
		CursoVertex res = CursoVertex.of(index + 1, rem, cent);

		return res;
	}

	@Override
	public CursoEdge edge(Integer a) {

		return CursoEdge.of(this, neighbor(a), a);
	}

	public CursoEdge greedyEdge() {
		Set<Integer> rem = Set2.difference(remaining, DatosEjercicio2.getTematicasCursos(index));
		Set<Integer> cent = Set2.union(centers(), Set2.of(DatosEjercicio2.getCentroCurso(index)));
		//Boolean cond = !rem.equals(remaining) && cent.size() <= DatosEjercicio2.getMaxCentros()  ;
		Boolean cond = rem.size()< remaining().size() && cent.size() <= DatosEjercicio2.getMaxCentros()  ;
		return cond ? edge(1) : edge(0);
	}

	

}
