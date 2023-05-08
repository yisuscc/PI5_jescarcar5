package ejemplos.ejemplo2.manual;

import java.util.List;

import _datos.DatosSubconjuntos;
import _soluciones.SolucionSubconjuntos;

import us.lsi.common.List2;

public class SubconjuntosState {// miodela unicamente las variazas entre estados

	SubconjuntosProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<SubconjuntosProblem> anteriores;
	
	private SubconjuntosState(SubconjuntosProblem p, Double a, 
		List<Integer> ls1, List<SubconjuntosProblem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}

	public static SubconjuntosState initial() {
		SubconjuntosProblem pi = SubconjuntosProblem.initial();
		return of(pi, 0., List2.empty(), List2.empty());
	}

	public static SubconjuntosState of(SubconjuntosProblem prob, Double acum, List<Integer> lsa,
			List<SubconjuntosProblem> lsp) {
		return new SubconjuntosState(prob, acum, lsa, lsp);
	}

	public void forward(Integer a) {		
		//hacia delante
		// aumenta el acumulado
		acumulado += a * DatosSubconjuntos.getPeso(actual.index());
		//añado el paso xd
		acciones.add(a);
		//añado el subproblema
		anteriores.add(actual);
		//obtengo el siguiente estado 
		actual = actual.neighbor(a);
	}

	public void back() {
		//hacia atras
		// obtengo la última acción por la que he pasado 
	
		int last = acciones.size() - 1; //el último problema 
		//obtengo el ultimo subproblema por el que he pasado 
		var prob_ant = anteriores.get(last);
		// deshgo el acumulado
		acumulado -= acciones.get(last) * DatosSubconjuntos.getPeso(prob_ant.index());
		acciones.remove(last);
		anteriores.remove(last);
		// obtengo el estado anterior
		actual = prob_ant;
	}

	public List<Integer> alternativas() { // se modelan en el propio problema 
		return actual.actions();
	}

	public Double cota(Integer a) {
		Double weight = a > 0 ? DatosSubconjuntos.getPeso(actual.index()) : 0.;
		return acumulado + weight + actual.neighbor(a).heuristic();
	}

	public Boolean esSolucion() {
		return actual.remaining().isEmpty();
	}

	public boolean esTerminal() {
		return actual.index() == DatosSubconjuntos.getNumSubconjuntos();
	}

	public SolucionSubconjuntos getSolucion() {
		return SolucionSubconjuntos.of(acciones);
	}

}
