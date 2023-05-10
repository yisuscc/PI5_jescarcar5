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
	Double kms;
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
		//TODO REHACER 
		//Double ben = values.stream().mapToDouble(i-> DatosEjercicio4.getBeneficioCliente(i)).sum();
		//values = [x,x,x,x,x,x]
		List<Integer>v = List2.addFirst(values,0);
		v = List2.addLast(v, 0);
		// v = [0,x,x,x,x,x,x,0]
		Double kmetros = 0.;
		Double ben = 0.;
		Double k= 0.;
		for(Integer i = 0;i<v.size(); i++ ) {
		Integer j = i+1;
		if(j== v.size())
			break;
		Integer v1 = v.get(i);
		Integer v2 = v.get(j);
		k = DatosEjercicio4.getPesoArista(v1, v2);
		kmetros += DatosEjercicio4.getPesoArista(v1, v2);
		ben+= DatosEjercicio4.getBeneficioCliente(v2)-kmetros;
		} //si la distancia es incorrecta comentad la linea siguiente
		kmetros-= k;
		kms= kmetros;
		beneficio = ben;
		solucion = v;
		
		
	}
	public Double getBeneficio() {
		return beneficio;
	}
	@Override
	public String toString() {
		String res = "Ruta optima: " +solucion+"\r\n"+"Beneficio:"+beneficio+"\r\n"+"Kms: "+kms+"\r\n"+"La distancia en kilometros puede ser imprecisa(ver linea 48)";
		
		return res;
	}
	@Override
	public int compareTo(SolucionReparto s) {
		return this.beneficio.compareTo(s.beneficio);
	}
	
	
}
