package ejercicios.ejercicio1;

import java.util.List;

import us.lsi.graphs.virtual.VirtualVertex;

public record CafeVertex(Integer index, List<Integer> remaining)implements VirtualVertex<CafeVertex, CafeEdge, Integer> {
//TODO el of

	// TODO el INitial 
	
//TODO el GOal
	@Override
	public List<Integer> actions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CafeVertex neighbor(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CafeEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public boolean equals(Object obj) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int hashCode() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
