package ejemplos.ejemplo2;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosSubconjuntos;
import _datos.DatosSubconjuntos.Subconjunto;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record SubconjuntosVertex(Integer index, Set<Integer> remaining)
		implements VirtualVertex<SubconjuntosVertex, SubconjuntosEdge, Integer> {

	public static SubconjuntosVertex of(Integer i, Set<Integer> rest) {
		return new SubconjuntosVertex(i, rest);
	}

	// TODO Consulte las clases GraphsPI5 y TestPI5

	public static Predicate<SubconjuntosVertex> goal() {
		// recuerda la diferencia entre objetos y priimitivos
		// para el equals y ==

		return v -> v.index() == DatosSubconjuntos.getNumSubconjuntos();
	}

	public static Predicate<SubconjuntosVertex> goalHasSolution() {
		return v -> v.remaining().isEmpty();
	}

	public static SubconjuntosVertex initial() {

		return of(0, Set2.copy(DatosSubconjuntos.getUniverso()));
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();

		if (index < DatosSubconjuntos.getNumSubconjuntos()) {
			if (remaining().isEmpty()) {
				alternativas = List2.of(0);
			} else {
				Set<Integer> rest = Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
				if (rest.equals(remaining)) {
					alternativas = List2.of(0);
				} else if (index == DatosSubconjuntos.getNumSubconjuntos() - 1) {
					alternativas = rest.isEmpty() ? List2.of(1) : List2.of(0);
				} else {
					alternativas = List2.of(0, 1);
				}
			}
		}

		return alternativas;
	}

	@Override
	public SubconjuntosVertex neighbor(Integer a) {
		Set<Integer> rest = a == 0 ? Set2.copy(remaining)
				: Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
		return of(index + 1, rest);
	}

	@Override
	public SubconjuntosEdge edge(Integer a) {
		return SubconjuntosEdge.of(this, neighbor(a), a);
	}

	// Se explica en practicas.
	public SubconjuntosEdge greedyEdge() {
		Set<Integer> rest = Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
		return rest.equals(remaining) ? edge(0) : edge(1);
	}

}
