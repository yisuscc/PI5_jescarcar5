package ejercicios.ejercicio4.manual;

import java.util.List;

import _datos.DatosEjercicio4;
import _soluciones.solEjercicios.SolucionEjercicio3;
import _soluciones.solEjercicios.SolucionReparto;
import ejemplos.ejemplo2.manual.SubconjuntosProblem;
import ejemplos.ejemplo2.manual.SubconjuntosState;
import us.lsi.common.List2;

public class RepartoState {
/*
 * 	SubconjuntosProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<SubconjuntosProblem> anteriores;
 */
	RepartoProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<RepartoProblem>anteriores;
	
	//constructor privado
	private RepartoState(RepartoProblem p, Double a, List<Integer >ls1, List<RepartoProblem>ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores= ls2;
	}
	//el of
	public static RepartoState of(RepartoProblem p, Double a, List<Integer >ls1, List<RepartoProblem>ls2) {
		return new RepartoState(p, a, ls1, ls2);
	}
	//initial
	public static  RepartoState initial() {
		RepartoProblem ini = RepartoProblem.initial();
		return of(ini,0.,List2.empty(),List2.empty());
	}
	//foward 
	public void forward(Integer a) {
		//aumentamos el acumulado
		Double pen  = actual.kms()+ DatosEjercicio4.getPesoArista(a, actual.cliente());
		if(actual.index()==DatosEjercicio4.getN()-1) {
			pen += DatosEjercicio4.getPesoArista(a, 0);
		}
		acumulado += DatosEjercicio4.getBeneficioCliente(a)-pen;
		//añadimos el paso xd
		acciones.add(a);
		//añado el subproblemas
		anteriores.add(actual);
		//obtengo el siguiente resultado 
		actual = actual.neighbor(a);
		
	}
	//back
	public void back() {//TODO REVISar
		int last = acciones.size()-1;
		var prob_ant = anteriores.get(last);//obtengo el ultimo problema
		Integer p = acciones.get(last);
		//descacemos el acumulado
		Double pen  = prob_ant.kms()+ DatosEjercicio4.getPesoArista(p, prob_ant.cliente());
		if(prob_ant.index()==DatosEjercicio4.getN()-1) {
			pen += DatosEjercicio4.getPesoArista(p, 0);
		}
		acumulado -= DatosEjercicio4.getBeneficioCliente(p)-pen;
		acciones.remove(last);
		anteriores.remove(last);
		//obtengo el estado anterior
		actual = prob_ant;
		
		
		//al decrementar el acumuulado tener cuidado si el vertice es terminal index es igual a n
	}
	//alternativas
	public  List<Integer> alternativas(){
		return actual.actions();
	}
	//TODO cota
	public Double cota(Integer a) {
	//	return Double.MAX_VALUE;//para forzar a que lo coja
		Integer c = this.actual.cliente();
		Integer km = this.actual.kms();
		Double ben = DatosEjercicio4.getBeneficioCliente(a)-DatosEjercicio4.getPesoArista(c, a)-km;
		return acumulado+km+ actual.neighbor(a).heuristic();
	}
	
	//essolucion
	public  Boolean esSolucion() {
		return actual.pendientes().isEmpty();
	}
	//es terminal
	public Boolean esTerminal() {
		return actual.index().equals(DatosEjercicio4.getN());
	}
	//getSolucion
	public SolucionReparto getSolucion() {
		return SolucionReparto.of(acciones);
	}
}
