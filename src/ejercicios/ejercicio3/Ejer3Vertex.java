package ejercicios.ejercicio3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Trabajo;
import _datos.DatosEjercicio4;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;
import us.lsi.math.Math2;

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
		
		return v-> todosMayorIgualQueCero(v.days()); //TODO Cambiar 
		
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
	// TODO GreedyEdge
	public Ejer3Edge greedyEdge() {
		Integer j = zIndex()%DatosEjercicio3.getNumTrabajos();
		Integer i= zIndex()/DatosEjercicio3.getNumTrabajos();
		Integer esp= DatosEjercicio3.getEspecialidadTrabajador(i);
		Integer min= Math.min(days.get(i),distribution().get(j).get(esp) );
		return edge(min);
		
	}
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		Integer z = (DatosEjercicio3.getNumTrabajos()*DatosEjercicio3.getNumInvestigadores());
		Integer j = zIndex()%DatosEjercicio3.getNumTrabajos();
		Integer i= zIndex()/DatosEjercicio3.getNumTrabajos();
		
		
		if(zIndex<z) {
			Integer esp= DatosEjercicio3.getEspecialidadTrabajador(i);
			Integer min= Math.min(days.get(i),distribution().get(j).get(esp));
			alternativas = List2.rangeList(0,min+1);
		}
		return alternativas;
		
	}

	@Override
	public Ejer3Vertex neighbor(Integer a) {
		// primero decrementamos el valor correspondiente en days
		List<Integer> dias= decrementor(days, a, zIndex());
		List<List<Integer>> dist = decrementorDobleLista(distribution, a, zIndex());
		
		
		
		//luego en la lista del trabajos
		
		return of(zIndex+1, dias, dist);
	}
	public static List<Integer> decrementor(List<Integer> rem, Integer a, Integer ind){
		
	
		List<Integer> res = List2.copy(rem);
		Integer inv = ind/DatosEjercicio3.getNumTrabajos();
		Integer esp= DatosEjercicio3.getEspecialidadTrabajador(inv);
		Integer r = rem.get(esp)-a;
		res.set(esp, r);
		
		
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
