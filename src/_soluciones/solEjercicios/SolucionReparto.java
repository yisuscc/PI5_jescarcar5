package _soluciones.solEjercicios;

import java.util.List;

import java.util.stream.IntStream;


import org.jgrapht.GraphPath;

import _datos.DatosEjercicio4;
import ejercicios.ejercicio4.RepartoEdge;
import ejercicios.ejercicio4.RepartoVertex;
import us.lsi.common.List2;

public class SolucionReparto implements Comparable<SolucionReparto> {
	List<Integer> solucion;
	List<Integer> path;
	Double beneficio;
	public static SolucionReparto of(List<Integer> ls) {
		return new SolucionReparto(ls);
	}
	public static SolucionReparto of(GraphPath<RepartoVertex,RepartoEdge>path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionReparto res = of(ls);
		res.path = ls;
		return res;
	}
	private SolucionReparto(List<Integer>values) {
		//List<Integer> v = List2.addFirst(values,0);
		Integer kms= 0;
		Double ben= IntStream.range(1, DatosEjercicio4.getN()).mapToDouble(i->DatosEjercicio4.getBeneficioCliente(i)).sum();
		Integer vertS = 0;
		Double pen = 0.;
		for(int i=0; i<DatosEjercicio4.getN();i++) {
			Integer n = DatosEjercicio4.getN();
			Integer j = i+1%n;
			pen+=(n-i) *DatosEjercicio4.getPesoArista(i, j);
			kms += DatosEjercicio4.getPesoArista(i, j).intValue();
		}
		beneficio = ben-pen;
		solucion = List2.addLast(values, 0);
		
	}
	@Override
	public String toString() {
		String res = "Ruta optima: " +solucion+"\r\n"+"Benenficio:"+beneficio;
		
		return res;
	}
	@Override
	public int compareTo(SolucionReparto s) {
		return this.beneficio.compareTo(s.beneficio);
	}
	
	
}
