package ejercicios.ejercicio1.PDManual;

import java.util.List;
import java.util.Map;

import _datos.DatosEjercicio1;
import us.lsi.common.List2;

public  record CafeProblem(Integer index, List<Integer> cafeRestante) {

	public static CafeProblem of(Integer index, List<Integer> cafeRestante) {

		return new CafeProblem(index, cafeRestante);
	}
	public static CafeProblem initial() {
		List<Integer> ls = DatosEjercicio1.getCafes().stream().map(c -> c.peso()).toList();
		return of(0,ls);
	}

	public CafeProblem vecino(Integer a) {

		return of(index + 1, decrementorMan(cafeRestante, a, index));
	}

	public Integer greedyInteger() {
		return limiteVarCafeMan(index, cafeRestante);

	}

	public List<Integer> acciones() {
		List<Integer> alternativas = List2.empty();
		if (index < DatosEjercicio1.getNumVarCafe()) {// TODO No se si le hace falta algo más
			alternativas = List2.rangeList(0, limiteVarCafeMan(index, cafeRestante) + 1);
		}

		return alternativas;
	}

	private static Integer limiteVarCafeMan(Integer index2, List<Integer> cafeRestante2) {

		List<Integer> rem2 = List2.copy(cafeRestante2);
		Map<String, Double> comp = DatosEjercicio1.getVariCafe().get(index2).composicion();// si el map fuese initeger
																							// integer
		// seria mas sencillo
		Double min = comp.entrySet().stream()
				.mapToDouble(es -> rem2.get(Integer.valueOf(es.getKey().replace('C', ' ').trim()) - 1) / es.getValue())
				.min().orElse(0);

		return min.intValue();
	}

	private static List<Integer> decrementorMan(List<Integer> cafeRestante2, Integer a, Integer ind) {
		// a diferencia del de cafe vertex este lo voy a hacer con stream
		//no es idoneo hacerlo con stream 
		
		List<Integer> ls = List2.copy(cafeRestante2);
		Integer in = -1;
		
		List<Integer> res = List2.empty();
		for (int p = 0; p < ls.size(); p++) {
			Double r = ls.get(p) - a * DatosEjercicio1.getPorcentajeCafeVar(p, ind);
			res.add(p, r.intValue());
		}
		return res;

	}
	public Double heuristic() {
		
		//heuristica basada en la solucion voraz
		Double res = 0.;
		List<Integer>ls = List2.copy(cafeRestante);
		Integer i = index();
		while(i<DatosEjercicio1.getNumVarCafe()) {
			Integer a = limiteVarCafeMan(i, ls);
			ls= decrementorMan(ls, a, i);
			res += a*DatosEjercicio1.getBeneficioVar(i);
			i++;
		}
		return res;
	}
	public  Boolean esSol() {
		Boolean res = true;
		List<Integer>ls = List2.copy(this.cafeRestante());
		Integer i  = 0;
		while(res && i<DatosEjercicio1.getNumVarCafe()) {
			res = ls.get(i)>=0;
			i++;
		}
		return res;
	}
}
