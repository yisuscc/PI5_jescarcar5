package ejercicios.ejercicio2.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import _datos.DatosEjercicio2;
import _soluciones.solEjercicios.SolucionCursos;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class CursoPDR {
	public static record SpCurso(Integer a, Double weight) implements Comparable<SpCurso> {
		public static SpCurso of(Integer a, Double w) {
			return new SpCurso(a, w);

		}

		@Override
		public int compareTo(SpCurso o) {

			return this.weight().compareTo(o.weight());
		}

	}

	public static Double bestValue;// problema de minimizacíon
	public static CursoProblem start;
	public static Map<CursoProblem, SpCurso> memory;

	public static SolucionCursos search() {
		memory = Map2.empty();
		bestValue = Double.MAX_VALUE;
		pdr_search(CursoProblem.initial(), 0., memory);
		return getSolucion();
	}
	private static SpCurso pdr_search(CursoProblem prob, Double acumulado, Map<CursoProblem, SpCurso> memoria) {
		SpCurso res = null;
		Boolean esTerminal= prob.index().equals(DatosEjercicio2.getNumCursos());
		Boolean esSolucion = prob.esSol();
		//esta en memoria? 
		if(memory.containsKey(prob)) {
			res = memory.get(prob);
		}else if(esSolucion&&esTerminal) {//ya no hay mas subproblemas
			res = SpCurso.of(null,0.);
			memory.put(prob, res);
			if(acumulado < bestValue) {
				bestValue = acumulado;
			}
		}else {
			//se analizasn todas las posibles soluciones
			List<SpCurso> soluciones = List2.empty();
			for(Integer action : prob.actions()) {
				Double cota = acotar(acumulado,prob,action);
				if(cota> bestValue) {//se descarta
					res = null;
				}else {
					//obtengo lasolucion del vecino tomando un camino
					CursoProblem vecino = prob.neighbor(action);
					Double acum= acumulado +action*DatosEjercicio2.getPrecioCurso(prob.index());
					SpCurso  s = pdr_search(vecino, acum, memory);
					if(s!= null) {
						SpCurso amp = SpCurso.of(action,s.weight+action*DatosEjercicio2.getPrecioCurso(prob.index()));
						soluciones.add(amp);
					}
				}
			}
			res= soluciones.stream().min(Comparator.naturalOrder()).orElse(null);
			if(res!= null)
				memory.put(prob, res);
		}
		
		return res;
	}
	

	private static Double acotar(Double acum, CursoProblem p, Integer a) {
		
		return acum + a*DatosEjercicio2.getPrecioCurso(p.index())+p.heuristic();
	}
	public static SolucionCursos getSolucion() {
		List<Integer> acciones = List2.empty();
		CursoProblem prob = CursoProblem.initial();
		SpCurso spCurso = memory.get(prob);
		while(spCurso !=null && spCurso.a != null) {
			CursoProblem old = prob;
			acciones.add(spCurso.a);
			prob = old.neighbor(spCurso.a);
			spCurso = memory.get(prob);
		}
		return SolucionCursos.of(acciones);
	}

}
