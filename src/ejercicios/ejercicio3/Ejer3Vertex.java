package ejercicios.ejercicio3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Trabajo;
import us.lsi.graphs.virtual.VirtualVertex;

public record Ejer3Vertex(Integer zIndex, List<Integer> days, List<List<Integer>> distribution)
		implements VirtualVertex<Ejer3Vertex, Ejer3Edge, Integer> {
	// Of
	public static Ejer3Vertex of(Integer z, List<Integer> da, List<List<Integer>> dis) {
		return new Ejer3Vertex(z, da, dis);
	}

	private static List<List<Integer>> iniDistribution() {
		List<List<Integer>> res = new ArrayList<>();
		for (Trabajo t : DatosEjercicio3.getTrabajos()) {
			Map<Integer, Integer> map = t.espDias();
			Comparator<Entry<Integer, Integer>> cmp = (o1, o2) -> o1.getKey().compareTo(o2.getKey());
			List<Integer> ls = map.entrySet().stream().sorted(cmp).map(e -> e.getValue()).toList();
			res.add(ls);
		}
		return res;
	}

	private static List<Integer> iniDays() {
		return DatosEjercicio3.getInvestigadores().stream().map(i -> i.capacidad()).toList();
	}

// INItial
	public static Ejer3Vertex initial() {
		return of(0, iniDays(), iniDistribution());
	}

	// goal
	public static Predicate<Ejer3Vertex> goal() {
		return v -> v.zIndex() == (DatosEjercicio3.getNumInvestigadores() * DatosEjercicio3.getNumTrabajos());
	}
	// TODO goalHasSolution

	// TODO GreedyEdge
	@Override
	public List<Integer> actions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ejer3Vertex neighbor(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ejer3Edge edge(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
