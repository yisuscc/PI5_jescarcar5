package ejercicios.ejercicio3.manual;

import java.util.List;

import _datos.DatosEjercicio3;
import _soluciones.solEjercicios.SolucionEjercicio3;
import us.lsi.common.List2;

public class Ejer3State {
	Ejer3Problem actual;
	Double acumulado;
	List<Integer> acciones;
	List<Ejer3Problem>anteriores;
	//constructorr privado
	private Ejer3State(Ejer3Problem p, Double a, List<Integer>ls1,List<Ejer3Problem>ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
		
	}
	
	//el of
	public static Ejer3State of(Ejer3Problem p, Double a, List<Integer>ls1,List<Ejer3Problem>ls2) {
		return new Ejer3State(p, a, ls1, ls2);
	}
	
	//initial
	public static Ejer3State initial() {
		return of(Ejer3Problem.initial(),0.,List2.empty(),List2.empty());
	}
	
	//foward 
	public void forward(Integer a) {
		//actualizamos el acumulado
		acumulado += Math.abs((actual.neighbor(a).calidadObtenida()-actual.calidadObtenida()));
		//añadimos el paso xDD
		acciones.add(a);
		//añadimos el subproblema
		anteriores.add(actual);
		//obtenemos el siguiente estado
		actual = actual.neighbor(a);
	}
	
	//back 
	public void back() {
		//obtengo la última acción por la que he pasado
		int last = acciones.size()-1;	
		//obtengo el ultimo subproblema por el que he pasado
		var probAnt = anteriores.get(last);
		//desgahoo el acumulado
		acumulado -= Math.abs(actual.calidadObtenida()-probAnt.calidadObtenida());
		acciones.remove(last);
		anteriores.remove(last);
		//obtengo el estado anterior
		actual = probAnt;
	}
	
	//alternativa 
	public  List<Integer> alternativas(){
		return actual.actions();
	}
	
	//cota
	public Double cota(Integer a) {
		Double dif = Math.abs(actual.calidadObtenida()-actual.neighbor(a).calidadObtenida());
		return acumulado +actual.neighbor(a).heuristic();
		
	}
	
	
	//esSolucion
	public Boolean esSolucion() {
		return actual.isSolution();
	}
	
	//esTreminal
	public Boolean esTerminal() {
		return actual.zIndex().equals(DatosEjercicio3.getNumInvestigadores()*DatosEjercicio3.getNumTrabajos());
	}
	
	//getSolucion
	public SolucionEjercicio3 getSolucion() {
		return SolucionEjercicio3.of(acciones);
	}
}
