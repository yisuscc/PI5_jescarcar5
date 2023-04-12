package ejercicios.ejercicio1;


import _datos.DatosEjercicio1;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CafeEdge(CafeVertex source, CafeVertex target, Integer action, Double weight) implements SimpleEdgeAction<CafeVertex, Integer> {

	public static CafeEdge of(CafeVertex s, CafeVertex t, Integer a) {
		
		return new  CafeEdge(s, t, a,a*DatosEjercicio1.getBeneficioVar(s.index()));
	}
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}


}
