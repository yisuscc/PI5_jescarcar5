package ejercicios.ejercicio1.PDManual;

import java.util.Comparator;

import java.util.List;
import java.util.Map;

import _datos.DatosEjercicio1;
import _soluciones.solEjercicios.SolucionCafe;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class CafePDR {
	public static record SpCafe(Integer a, Double weight) implements Comparable<SpCafe> {
		public static SpCafe of(Integer a, Double weight) {
			return new SpCafe(a, weight);
		}

		@Override
		public int compareTo(SpCafe o) {

			return this.weight().compareTo(o.weight());
		}

	}
	public static Double maxValue = Double.MIN_VALUE;
	public static CafeProblem start;
	public static Map<CafeProblem, SpCafe> memory;
	
	public static SolucionCafe search() {
		memory = Map2.empty();
		maxValue = Double.MIN_VALUE;
		pdr_search(CafeProblem.initial(),0.,memory);
		return getSolucion();
		
	}

	private static SpCafe pdr_search(CafeProblem prob, Double acumulado, Map<CafeProblem, SpCafe> memoria) {
		SpCafe res = null;
		// devuelve true cuando ya no queden mas nodos(fin de la rama)
		Boolean esTerminal= prob.index().equals(DatosEjercicio1.getNumVarCafe());
		// devuelve true si tiene solucion
		Boolean esSolucion= prob.esSol();
		//comprobamos si el problema ha sido resuelto
		if(memory.containsKey(prob)) {
			res = memory.get(prob);
		}else if(esTerminal&&esSolucion){//caso base y no hay mas subproblemas
			res = SpCafe.of(null,0.);
			memory.put(prob, res);
			if(acumulado> maxValue) {
				maxValue = acumulado;
			}
		}else {//todavia quedan subproblemas
			//voy a analizar todas las posibles sololuciones parciales
			//tomando distintas alternativas desde mi problema inicial
			List<SpCafe> soluciones = List2.empty();
			for(Integer action: prob.acciones()) {
				//Miro si me interesa o no la rama
				Double cota = acotar(acumulado,prob,action);
				if(cota<maxValue) {
					//descartamos porque estamos maximizando;
					res = null;
				}else {
					//obtengo la solucion del vecino  tomando un camino
					CafeProblem vecino = prob.vecino(action);
					// Acción = peso
					// Vecino = nuevo vertice tomando una accion
//					acumulado  = lo que llevo acunulado mas esa accion 
					// arrastro la memoria
					//TODO Peso acum 
					Double pesoAcum = acumulado+ action*DatosEjercicio1.getBeneficioVar(prob.index());
					SpCafe s = pdr_search(vecino,pesoAcum , memory);
					if(s!= null) {
						// si da una solucion valida, la instanciamos de forma estaitca
						// para guardarla en la lista
						// de soluciones parciales
						SpCafe amp = SpCafe.of(action, 
								s.weight()+action*DatosEjercicio1.getBeneficioVar(prob.index()));
						soluciones.add(amp);
					}
				}
			}
			res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if(res!= null) {
				memory.put(prob, res);
			}
			
		}
		
		
		return res;
	}

	private static Double acotar(Double acum, CafeProblem p, Integer a) {
		return acum+a*DatosEjercicio1.getBeneficioVar(p.index())+p.vecino(a).heuristic();
		
	}
	public static SolucionCafe getSolucion() {
		List<Integer> acciones = List2.empty();
		//Problema inicial
		CafeProblem prob = CafeProblem.initial();
		//obtenemos la solucion del problema inicial
		SpCafe spCafe = memory.get(prob);
		while(spCafe != null && spCafe.a != null ) {
			//recreamos los pasos
			CafeProblem old= prob;
			acciones.add(spCafe.a);
			prob = old.vecino(spCafe.a);
			spCafe = memory.get(prob);
		}
		return SolucionCafe.of(acciones);
	}
}
