package ejercicios.ejercicio4;

import java.util.List;
import java.util.Set;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.VirtualVertex;

public record RepartoVertex(Integer cliente, Set<Integer> pendientes,List<Integer> visitados, Integer kms)
implements VirtualVertex<RepartoVertex, RepartoEdge, IntPair> {// TODO Es inPair o integer?
//TODO  of 
	
//TODO initial
//TODO goal
//TODO GOal Has Solution
	@Override
	public List<IntPair> actions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepartoVertex neighbor(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepartoEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}
	//TODO GreedyEdge
	




}
