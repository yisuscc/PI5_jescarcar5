package ejercicios.ejercicio1.PDManual;

import java.util.List;
import java.util.Map;

import _datos.DatosEjercicio1;
import us.lsi.common.List2;

public record CafeProblem(Integer index, List<Integer> cafeRestante) {
//TODO OF
	public static CafeProblem of(Integer index, List<Integer> cafeRestante) {
		//TODO un exception checker
		return new CafeProblem(index, cafeRestante);
	}
	//TODO vecino
	public CafeProblem vecino(Integer a) {
		return null;
	}
	
	//TODO GreedyAction
	public Integer greedyInteger() {
		return null; 
	
	}
	//TODO acciones
	public List<Integer> acciones(){
		List<Integer> alternativas = List2.empty();
		if(index<DatosEjercicio1.getNumVarCafe()) {//TODO No se si le hace falta algo más
			alternativas = List2.rangeList(0, limiteVarCafeMan(index, cafeRestante)+1);
		}

		return alternativas;
	}
	private static Integer limiteVarCafeMan(Integer index2, List<Integer> cafeRestante2) {
		// TODO Auto-generated method stub
		List<Integer> rem2 = List2.copy(cafeRestante2);
		Map<String, Double> comp  = DatosEjercicio1.getVariCafe().get(index2).composicion();// si el map fuese initeger integer
		//seria mas sencillo
		Double min= comp.entrySet().stream().
				mapToDouble(es->rem2.get(Integer.valueOf(es.getKey().replace('C',' ').trim())-1)/es.getValue()).
				min().orElse(0);

		return min.intValue();
	}
	private static List<Integer> decrementorMan(List<Integer>cafeRestante2, Integer a, Integer ind ){
		// a diferencia del de cafe vertex este lo voy a hacer con stream
		List<Integer> ls= List2.copy(cafeRestante2);
		Integer in = -1;
		List<Integer> res =  ls.stream().map(r->( r- a*DatosEjercicio1.getPorcentajeCafeVar(, in)) )
	}
}
