package ejercicios.ejercicio3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.IntStream;


import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Trabajo;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;


public record Ejer3Vertex(Integer zIndex, List<Integer> days, List<List<Integer>> distribution)
		implements VirtualVertex<Ejer3Vertex, Ejer3Edge, Integer> {
	// Of
	public static Ejer3Vertex of(Integer z, List<Integer> da, List<List<Integer>> dis) {
		return new Ejer3Vertex(z, da, dis);
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

// INItial
	public static Ejer3Vertex initial() {
		return of(0, iniDays(), iniDistribution());
	}

	// goal
	public static Predicate<Ejer3Vertex> goal() {
		return v -> v.zIndex() == (DatosEjercicio3.getNumInvestigadores() * DatosEjercicio3.getNumTrabajos());
	}
	//  goalHasSolution
	public static Predicate<Ejer3Vertex> goalHasSolution(){
		//es solucion si todos los elementos de days son iguales o mayores que cero
		//(es decir que no se ha superado la capacidad de cada trabajador)
		// y si cada elemento de la distribución es 0 o igual al iniDistrib
		
		return v-> todosMayorIgualQueCero(v.days()) ; //TODO Cambiar 
		
	}
	private static Boolean todosMayorIgualQueCero(List<Integer> ls) {
		Boolean res = true;
		for (Integer i : ls) {
			if (i < 0) {
				res = false;
				break;
			}
		}
		return res;
	}
	private static Boolean distribACeroOInicial(List<List<Integer>> dis) {
		/*
		 * Es para evitar cojer investigadores de mas cuando no van a poder terminar el trabajo
		 */
		Boolean res = true;
		List<List<Integer>> ls  = iniDistribution();
		for(int g = 0; g<dis.size();g++) {
			Integer o = g;
			res = dis.get(g).stream().allMatch(e-> e.equals(0) || dis.equals(ls.get(o)));
			if(!res) {
				break;
			}
		}
		
		
		return res;
	}
	
	public Ejer3Edge greedyEdge() {
		Integer j = zIndex()%DatosEjercicio3.getNumTrabajos();
		Integer i= zIndex()/DatosEjercicio3.getNumTrabajos();
		Integer esp= DatosEjercicio3.getEspecialidadTrabajador(i);
		Integer min= Math.min(days.get(i),distribution().get(j).get(esp) );
		return esPosible()?edge(min):edge(0);
		
	}
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		Integer z = (DatosEjercicio3.getNumTrabajos()*DatosEjercicio3.getNumInvestigadores());
		Integer j = zIndex()%DatosEjercicio3.getNumTrabajos();
		Integer i= zIndex()/DatosEjercicio3.getNumTrabajos();
		
		
		if(zIndex<z) {
			Integer esp= DatosEjercicio3.getEspecialidadTrabajador(i);
			Integer dia = days.get(i);
			Integer di = distribution().get(j).get(esp);
			Integer min = Math.min(dia, di);
			
		
			alternativas = esPosible()?IntStream.range(0, min+1).boxed().toList():List2.of(0);
		
		}
		return alternativas;
		
	}
	private  Boolean esPosible() {
		//determina si es posible que se haga el trabajo con lios dias restantes que queden
		Boolean res = true;
		
		List<Integer> diasEspRestantes = this.getDiasDispEsp();
		Integer inv = zIndex()/DatosEjercicio3.getNumTrabajos();
		Integer trab = zIndex()%DatosEjercicio3.getNumTrabajos();
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
	public Boolean esPosibleHeuristic(Integer trab) {
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

	@Override
	public Ejer3Vertex neighbor(Integer a) {
		// primero decrementamos el valor correspondiente en days
		List<Integer> dias= this.decrementor( a);
		List<List<Integer>> dist = decrementorDobleLista(distribution, a, zIndex());
		
		
		
		//luego en la lista del trabajos
		
		return of(zIndex+1, dias, dist);
	}
	private List<Integer> decrementor( Integer a){
		Integer ind = zIndex();
		List<Integer> rem = List2.copy(days());
		List<Integer> res = List2.copy(rem);
		Integer inv = ind/DatosEjercicio3.getNumTrabajos();
		
		Integer r = rem.get(inv)-a;
		res.set(inv, r);
		
		
		return res;
	}
	public static List<List<Integer>>decrementorDobleLista(List<List<Integer>> rem, Integer a, Integer ind) {
		Integer inv = ind/DatosEjercicio3.getNumTrabajos();
		Integer trab = ind%DatosEjercicio3.getNumTrabajos();
		Integer esp= DatosEjercicio3.getEspecialidadTrabajador(inv);
		List<List<Integer>>res = List2.copy(rem);
		Integer inAux= rem.get(trab).get(esp)-a;
		List<Integer>lsAux= List2.copy(rem.get(trab));
		lsAux.set(esp, inAux);
		res.set(trab, lsAux);
		
		return res;
	}
public  Double calidadObtenida() {
	/// sirve tanto para sum como para last
	//deberia ser integer, pero como pretendia qusarlo como tipo last, lo puse de tipo Double
	Double res = 0.;
	List<List<Integer>> dist = this.distribution();
	for(int i = 0; i<dist.size();i++) {
		
		if(dist.get(i).stream().allMatch(e-> e.equals(0))) {
			res+= DatosEjercicio3.getCalidadTrabajo(i);
		}
	}
	return res;
}
public List<Integer>getDiasDispEsp(){
	List<Integer> res = List2.ofTam(0, DatosEjercicio3.getNumEspecialidades());
	for(int h = 0; h<days().size();h++) {
		Integer esp = DatosEjercicio3.getEspecialidadTrabajador(h);
		Integer sum = res.get(esp)+days().get(h);
		res.set(esp,sum );
	}
	return res;
}
private static Boolean todosIgualACero(List<Integer> ls) {
	Boolean res = true;
	for (Integer i : ls) {
		if (i != 0) {
			res = false;
			break;
		}
	}
	return res;
}
	@Override
	public Ejer3Edge edge(Integer a) {
		
		return Ejer3Edge.of(this, neighbor(a), a);
	}



}
