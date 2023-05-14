package _soluciones.solEjercicios;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio3;
import ejercicios.ejercicio3.Ejer3Edge;
import ejercicios.ejercicio3.Ejer3Vertex;
import us.lsi.common.List2;

public class SolucionEjercicio3 implements Comparable<SolucionEjercicio3> {
	private List<Integer> sol;
	private Integer calidad;
	private List<Integer> path;

	public static SolucionEjercicio3 of(List<Integer> values) {
		return new SolucionEjercicio3(values);
	}

	public static SolucionEjercicio3 of(GraphPath<Ejer3Vertex, Ejer3Edge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionEjercicio3 res = of(ls);
		res.path = ls;
		return res;
	}

	private SolucionEjercicio3(List<Integer> value) {
		Integer calidadTotal = 0;
		Integer nTrabajos = DatosEjercicio3.getTrabajos().size();
		Integer nInv = DatosEjercicio3.getInvestigadores().size();
		for (int k = 0; k < nTrabajos; k++) { 
			Integer dT = 0;
			Integer trab = k;
			for (int l = k; l < value.size(); l = l + nTrabajos) {
				dT += value.get(l);
			}
			Integer diasTotalTrab =  IntStream.
					range(0, DatosEjercicio3.getNumEspecialidades()).map(e-> DatosEjercicio3.getDiasNecesariosEsp(trab, e)).sum();
			if (dT ==diasTotalTrab) {
				calidadTotal += DatosEjercicio3.getTrabajos().get(k).calidad();

			}
		}
		sol = value;
		calidad = calidadTotal;
	}
	public Integer getCalidad() {
		return calidad;
	}

	@Override
	public String toString() {
		String s = "Reparto obtenido: \r\n";
		Integer nTrabajos = DatosEjercicio3.getTrabajos().size();
		Integer nInv = DatosEjercicio3.getInvestigadores().size();
		Integer inicio = 0;
		Integer fin = nTrabajos;
		for (int i = 0; i < nInv; i++) {
			List<Integer> solu = List2.copy(sol.subList(inicio, fin));
			s += "INV" + (i + 1) + ": " + solu + "\r\n";
			inicio += nTrabajos;
			fin += nTrabajos;
		}
		s += "Suma de las calidades: " + calidad;
		return s;
	}

	@Override
	public int compareTo(SolucionEjercicio3 s) {
		return calidad.compareTo(s.calidad);
	}

}
