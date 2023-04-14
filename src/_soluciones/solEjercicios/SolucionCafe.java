package _soluciones.solEjercicios;

import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio1.VarCafe;
import ejercicios.ejercicio1.CafeEdge;
import ejercicios.ejercicio1.CafeVertex;
import us.lsi.common.List2;

public class SolucionCafe {
	
	private Double beneficio;
	private List<Integer>  solucion;
	private List<Integer> path;
	
	
private  SolucionCafe(List<Integer> values) {
	Double benTotal= 0.0;
	List<Integer> ls = List2.empty();
	for(int i = 0; i<values.size();i++) {
	VarCafe vari = DatosEjercicio1.getVariCafe().get(i);
	Double  ben=  vari.beneficio();
	benTotal += ben*values.get(i);
	ls.add(values.get(i));
	
	}
	this.beneficio = benTotal;
	this.solucion = ls;
}
	
public SolucionCafe of(List<Integer> ls) {
	return new SolucionCafe(ls);
}

public SolucionCafe of(GraphPath<CafeVertex, CafeEdge> path ) {

	List<Integer> ls = path.getEdgeList().stream().map(e->e.action()).toList();
	SolucionCafe res = of(ls);
	res.path =ls;
	return  res ;
}
@Override
public String toString() { //PI4
	String aux = " Variedades seleccionadas: \r\n ";
	// System.lineSeparator()
	List<Integer> sol = this.solucion;
	for(int j=0; j<sol.size(); j++) {
		Integer val = sol.get(j);
		if(val>0) {
			
			aux += DatosEjercicio1.getVariCafe().get(j).nombre() +": "+ val +"\r\n";
		}

	}
	aux += "Beneficio: " +beneficio;
//	return aux;
	return path==null? aux: String.format("%s\nPath de la solucion: %s", aux, path);
}

}
