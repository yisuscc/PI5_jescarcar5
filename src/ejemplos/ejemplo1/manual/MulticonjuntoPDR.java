package ejemplos.ejemplo1.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import _datos.DatosMulticonjunto;
import _soluciones.SolucionMulticonjunto;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class MulticonjuntoPDR {

	public static record Spm(Integer a, Integer weight) implements Comparable<Spm> {// Muy importante el metodo
																					// comparable
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}

		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}

	public static Map<MulticonjuntoProblem, Spm> memory;
	public static Integer mejorValor;

	public static SolucionMulticonjunto search() {
		memory = Map2.empty();
		mejorValor = Integer.MAX_VALUE; // Estamos minimizando

		pdr_search(MulticonjuntoProblem.initial(), 0, memory);
		return getSolucion();
	}

	private static Spm pdr_search(MulticonjuntoProblem prob, Integer acumulado,
			Map<MulticonjuntoProblem, Spm> memoria) {

		Spm res = null;
		// devuelve true cuando ya no queden mas nodos(fin de la rama)
		Boolean esTerminal = prob.index().equals(DatosMulticonjunto.getNumElementos());
		// devuelve true si tiene solucion
		Boolean esSolucion = prob.remaining().equals(0);
		// ¿El problema ya ha sido resuelto?
		if (memory.containsKey(prob)) {
			res = memory.get(prob);

		} else if (esTerminal && esSolucion) {// Caso base
			res = Spm.of(null, 0); // ya no hay mas subproblemas
			memory.put(prob, res);// lo guardo igualmente
			if (acumulado < mejorValor) { // Estamos minimizando
				mejorValor = acumulado;
			}
		} else {
			// voy a analizar todas las posibles soluciones 
			//(tomando distintas alternativas) desde mi problema inicial
			List<Spm> soluciones = List2.empty();
			for (Integer action : prob.actions()) {
				//miro si me interesa descartar o no la rama
				Double cota = acotar(acumulado, prob, action);
				if (cota > mejorValor) { // la descarto
					res = null; // continue;
				} else {
					//obtengo la solución del vecino tomando un camino(accíon)
					MulticonjuntoProblem vecino = prob.neighbor(action);
					//Acción = peso
					//Vecino = nuevo vertice tomando una  accion
//					acumulado  = lo que llevo acunulado mas esa accion 
					// arrastro la memoria 
					
					Spm s = pdr_search(vecino, acumulado + action, memory);
					if (s != null) {
						//si da una solucion valida, la instanciamos de forma estaitca 
						// para  guardarla en la lista
						// de soluciones parciales
						Spm amp = Spm.of(action, s.weight() + action);
						soluciones.add(amp);
					}

				}
			}
			// Estamos minimizando
			// de todas las soluciones, quedate comn la mas pequeña 
			// solucion mas pequeña  = solución con menor peso
			res = soluciones.stream().min(Comparator.naturalOrder()).orElse(null);
			if (res != null)
				// guardo las solución para ese subproblema 
				memory.put(prob, res);
		}

		return res;
	}

	private static Double acotar(Integer acum, MulticonjuntoProblem p, Integer a) {
		// LO que llevo + lo que actualizo tomando a + lo que me quedaría(heuristica)
		return acum + a + p.neighbor(a).heuristic();
	}

	public static SolucionMulticonjunto getSolucion() {
		// la solución del problema original no es mas que una serie de pasos
		//serie de pasos  = lista de acciones 
		List<Integer> acciones = List2.empty();
//		/obengo el problema inicial 
		MulticonjuntoProblem prob = MulticonjuntoProblem.initial();
		//Obtengo la solución del problema inicial 
		Spm spm = memory.get(prob);
		while (spm != null && spm.a != null) {
			//recreo los pasos 
			MulticonjuntoProblem old = prob;
			//guardo la accion dek probema actual(solución))
			acciones.add(spm.a);
			//actualizo el problema 
			// actiualizar el problema= obtener el subproblema dado a 
			prob = old.neighbor(spm.a);
			spm = memory.get(prob);
		}
		return SolucionMulticonjunto.of(acciones);
	}

}
