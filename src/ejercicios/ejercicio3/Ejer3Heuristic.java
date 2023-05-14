package ejercicios.ejercicio3;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosEjercicio3;

public class Ejer3Heuristic {
	public static Double heuristic(Ejer3Vertex v1, Predicate<Ejer3Vertex> goal, Ejer3Vertex v2) {
		/*
		 * Hesta heuristica itiera sobre el trabajo siguente al vertice no el actual
		 * 
		 */
		Integer trabAct = v1.zIndex() % DatosEjercicio3.getNumTrabajos()  ;
		//NOTA: no se si empezar por el actual o el actiual +1
		// filtramos las que diractamente no sean posibles,
//	 luegode laos piosibles obtenenmos tiodass las calidades,
		// hacemos el sum de todas las calidades (es optimista)
		return IntStream.range(trabAct, DatosEjercicio3.getNumTrabajos()).
		boxed().filter(j->esPosible(v1.getDiasDispEsp(), trabAct)).mapToDouble(j-> DatosEjercicio3.getCalidadTrabajo(j)).sum();
		
	}

	private static Boolean esPosible(List<Integer> lsEsp, Integer trabajo) {
		Boolean res = true;

		// se pude hacer coon stream
		// pèrro bueno, yo lo hago así
		for (int q = 0; q < lsEsp.size(); q++) {
			if (!res) {
				break;
			}
			res = lsEsp.get(q) >= DatosEjercicio3.getDiasNecesariosEsp(trabajo, q);
		}

		return res;
	}

}
