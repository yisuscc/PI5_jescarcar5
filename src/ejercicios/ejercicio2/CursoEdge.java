package ejercicios.ejercicio2;

import _datos.DatosEjercicio2;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CursoEdge(CursoVertex source, CursoVertex target, Integer action, Double weight)
		implements SimpleEdgeAction<CursoVertex, Integer> {
	public static CursoEdge of(CursoVertex s, CursoVertex t, Integer a) {
		Double w = DatosEjercicio2.getPrecioCurso(s.index()) * a;
		return new CursoEdge(s, t, a, w);
	}
}
