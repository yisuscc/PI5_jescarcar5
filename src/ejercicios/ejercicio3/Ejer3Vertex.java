package ejercicios.ejercicio3;

import java.util.List;

import us.lsi.graphs.virtual.VirtualVertex;

public record Ejer3Vertex(Integer zIndex, List<Integer> days,List<List<Integer>> distribution) implements VirtualVertex<Ejer3Vertex, Ejer3Edge, Integer>{
	//TODO OF
		//TODO goal
	//TODO goalHasSolution
	//TODO INItial
	//TODO GreedyEdge
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
