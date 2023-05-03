package ejercicios.ejercicio4;

import java.util.List;
import java.util.Set;

public record IntPairRepartoEdge(Integer index,Integer cliente, Set<Integer> pendientes,List<Integer> visitados, Integer km) {

}
