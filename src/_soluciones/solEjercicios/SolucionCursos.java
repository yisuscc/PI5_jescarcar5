package _soluciones.solEjercicios;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio2;
import _datos.DatosEjercicio2.Curso;
import ejercicios.ejercicio2.CursoEdge;
import ejercicios.ejercicio2.CursoVertex;

public class SolucionCursos implements Comparable<SolucionCursos>{

	public static SolucionCursos of(List<Integer>  values) {
		return new SolucionCursos(values);
	}
	public static SolucionCursos of(GraphPath<CursoVertex, CursoEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionCursos res = of(ls);
		res.path= ls;
		return res;
	}
	
	private  Double coste;
	private  Set<String> cursos;
	private List<Integer> path;
	
	private SolucionCursos (List<Integer> values) {
		Double cost = 0.0;
		Set<String> cur = new HashSet<>();
		
		for(int i = 0; i<values.size(); i++) {
			if(values.get(i)==1) {
				Curso cursoI = DatosEjercicio2.getCursos().get(i);
				cost += cursoI.precio();
				cur.add("S"+i);
			}
		}
		this.cursos = cur;
		this.coste = cost;
	}
	@Override
public String toString() {
	return "Cursos elegidos: "+this.cursos +"\r\n"+"Coste total: "+this.coste+"\r\n";
}
	@Override
	public int compareTo(SolucionCursos s) {
		return coste.compareTo(s.coste);
	}
}
