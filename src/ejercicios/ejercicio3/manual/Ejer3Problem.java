package ejercicios.ejercicio3.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Trabajo;
import us.lsi.common.List2;

public record Ejer3Problem(Integer zIndex, List<Integer> days, List<List<Integer>> distribution) {
//of 
	public static Ejer3Problem of(Integer z, List<Integer> d, List<List<Integer>> dist) {
		return new Ejer3Problem(z, d, dist);
	}

//initial
	public static Ejer3Problem initial() {
		return of(0, iniDays(), iniDistribution());
	}

	private static List<List<Integer>> iniDistribution() {
		List<List<Integer>> res = new ArrayList<>();
		for (Trabajo t : DatosEjercicio3.getTrabajos()) {
			Map<Integer, Integer> map = t.espDias();
			Comparator<Entry<Integer, Integer>> cmp = (o1, o2) -> o1.getKey().compareTo(o2.getKey());
			List<Integer> ls = map.entrySet().stream().sorted(cmp).map(e -> e.getValue()).toList();
			res.add(ls);
		}
		return res;
	}

	private static List<Integer> iniDays() {
		return DatosEjercicio3.getInvestigadores().stream().map(i -> i.capacidad()).toList();
	}

//actions
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		Integer z = (DatosEjercicio3.getNumTrabajos() * DatosEjercicio3.getNumInvestigadores());
		Integer j = zIndex() % DatosEjercicio3.getNumTrabajos();
		Integer i = zIndex() / DatosEjercicio3.getNumTrabajos();

		if (zIndex < z) {
			Integer esp = DatosEjercicio3.getEspecialidadTrabajador(i);
			Integer dia = days.get(i);
			Integer di = distribution().get(j).get(esp);
			Integer min = Math.min(dia, di);
			Boolean cond = esPosible();
			alternativas =  cond? IntStream.range(0, min + 1).boxed().toList() : List2.of(0);

		}
		
		return alternativas;

	}

	private Boolean esPosible() {
		// determina si es posible que se haga el trabajo con lios dias restantes que
		// queden
		Boolean res = true;

		List<Integer> diasEspRestantes = this.getDiasDispEsp();
		Integer inv = zIndex() / DatosEjercicio3.getNumTrabajos();
		Integer trab = zIndex() % DatosEjercicio3.getNumTrabajos();
		Integer esp = DatosEjercicio3.getEspecialidadTrabajador(inv);
		List<Integer> diasNecesariosTrabajo = this.distribution().get(trab);
		for (int q = 0; q < diasEspRestantes.size(); q++) {

			res = diasEspRestantes.get(q) >= diasNecesariosTrabajo.get(q);
			if (!res) {
				break;
			}
		}

		return res;
	}

	public List<Integer> getDiasDispEsp() {
		List<Integer> res = List2.ofTam(0, DatosEjercicio3.getNumEspecialidades());
		for (int h = 0; h < days().size(); h++) {
			Integer esp = DatosEjercicio3.getEspecialidadTrabajador(h);
			Integer sum = res.get(esp) + days().get(h);
			res.set(esp, sum);
		}
		return res;
	}

//vecino
	public Ejer3Problem neighbor(Integer a) {
		List<Integer> dias = this.decrementor(a);
		List<List<Integer>> dist = decrementorDobleLista(distribution, a, zIndex());

		// luego en la lista del trabajos

		return of(zIndex + 1, dias, dist);
	}

	private List<Integer> decrementor(Integer a) {
		Integer ind = zIndex();
		List<Integer> rem = List2.copy(days());
		List<Integer> res = List2.copy(rem);
		Integer inv = ind / DatosEjercicio3.getNumTrabajos();

		Integer r = rem.get(inv) - a;
		res.set(inv, r);

		return res;
	}

	public Double calidadObtenida() {
		/// sirve tanto para sum como para last (por eso es doble en lugar de integer
		Double res = 0.;
		List<List<Integer>> dist = this.distribution();
		for (int i = 0; i < dist.size(); i++) {

			if (dist.get(i).stream().allMatch(e -> e.equals(0))) {
				res += DatosEjercicio3.getCalidadTrabajo(i);
			}
		}
		return res;
	}

	public static List<List<Integer>> decrementorDobleLista(List<List<Integer>> rem, Integer a, Integer ind) {
		Integer inv = ind / DatosEjercicio3.getNumTrabajos();
		Integer trab = ind % DatosEjercicio3.getNumTrabajos();
		Integer esp = DatosEjercicio3.getEspecialidadTrabajador(inv);
		List<List<Integer>> res = List2.copy(rem);
		Integer inAux = rem.get(trab).get(esp) - a;
		List<Integer> lsAux = List2.copy(rem.get(trab));
		lsAux.set(esp, inAux);
		res.set(trab, lsAux);

		return res;
	}

//heuristica
	public Double heuristic() {
		Integer trabAct = zIndex() % DatosEjercicio3.getNumTrabajos();
		return IntStream.range(trabAct, DatosEjercicio3.getNumTrabajos()).boxed().filter(j -> esPosibleHeuristic(j))
			.mapToDouble(j -> DatosEjercicio3.getCalidadTrabajo(j)).sum();	
	}

	private Boolean esPosibleHeuristic(Integer trab) {
		Boolean res = true;

		List<Integer> diasEspRestantes = this.getDiasDispEsp();
		List<Integer> diasNecesariosTrabajo = this.distribution().get(trab);
		for (int q = 0; q < diasEspRestantes.size(); q++) {

			res = diasEspRestantes.get(q) >= diasNecesariosTrabajo.get(q);
			if (!res) {
				break;
			}
		}

		return res;

	}
	public Boolean isTerminal() {
		return zIndex() == (DatosEjercicio3.getNumInvestigadores() * DatosEjercicio3.getNumTrabajos());
	}

	public Boolean isSolution() {
		
//		creo que lal soluciomn realmente es que sea terminal y que la suma de las calidades sea mayo r que cero 
		return isTerminal();
	}

}
