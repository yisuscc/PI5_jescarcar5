package ejemplos.ejemplo1.manual;

import java.util.List;
import java.util.stream.IntStream;

import _datos.DatosMulticonjunto;
import us.lsi.common.List2;

public record MulticonjuntoProblem(Integer index, Integer remaining) {
	public static MulticonjuntoProblem initial() {
		return of(0, DatosMulticonjunto.getSuma());
	}

	private static MulticonjuntoProblem of(Integer i, Integer suma) {
		// TODO Auto-generated method stub
		return new MulticonjuntoProblem(i, suma);
	}

	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if (index < DatosMulticonjunto.getNumElementos()) {
			Integer value = DatosMulticonjunto.getElemento(index);
			Integer options = remaining / value;
			if (index == DatosMulticonjunto.getNumElementos() - 1) {
				if (remaining % value == 0) {
					alternativas = List2.of(remaining / value);
				} else {
					alternativas = List2.of(0);
				}
			} else {
				alternativas = List2.rangeList(0, options + 1);
			}
		}
		return alternativas;

	}
	public MulticonjuntoProblem neighbor(Integer a) {
		return of(index+1, remaining-a*DatosMulticonjunto.getElemento(index));
	}
	public Double heuristic() {
		Double res = 0.;
		if(remaining>0) {
			Integer max = IntStream.range(index, DatosMulticonjunto.getNumElementos()).
					map(i-> DatosMulticonjunto.getElemento(i)).filter(e-> e<=remaining).max().orElse(0);
			if(max>0) {
				Integer r = remaining / max;
				res = (double) (remaining% max== 0?r:r+1);
			}
		}
		return res;
	}

}
