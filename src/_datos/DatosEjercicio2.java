package _datos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import us.lsi.common.Comparator2;
import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public class DatosEjercicio2 {
//Max_Centros = 1
	// {1,2,3,4}:10.0:0
	// {1,4}:3.0:0
	public static record Curso(Set<Integer> tematica, Double precio, Integer centro) {
		public static Curso create(String linea) {
			String[] datos = linea.split(":");
			Set<Integer> cur = new HashSet<>();
			String[] d = datos[0].replace("}", "").replace("{", "").split(",");
			for (String i : d) {
				cur.add(Integer.valueOf(i.trim()));
			}
			Double mat = Double.valueOf(datos[1].trim());
			Integer cent = Integer.valueOf(datos[2].trim());
			return new Curso(cur, mat, cent);

		}
	}

	private static Integer maxCentros;
	private static List<Curso> cursos;
	private static List<Integer> tematicas;

	/*
	 * creo que temeaticas realmente deberia ser parte de los getters no parte del
	 * los atributos
	 * 
	 * 
	 */
	public static void iniDatos(String fichero) {
		List<String> lsFich = Files2.linesFromFile(fichero);
		maxCentros = Integer.valueOf(lsFich.get(0).replace("Max_Centros = ", "").trim());
		List<Curso> temp = new ArrayList<>();
		Set<Integer> setTemas = Set2.empty();

		for (int i = 1; i < lsFich.size(); i++) {// se puede hacer con stream skip
			Curso aux = Curso.create(lsFich.get(i));
			temp.add(aux);
			setTemas.addAll(aux.tematica());
		}
		cursos = temp;
		List<Integer> lsAux = List2.ofCollection(setTemas);
		lsAux.sort(Comparator.naturalOrder());
		tematicas = lsAux;
	}

	public static Integer getMaxCentros() {
		return maxCentros;
	}

	public static List<Curso> getCursos() {// se puede eliminar
		return cursos;
	}

	public static List<Integer> getTematicas() {
		return tematicas;
	}

	public static Integer getNumCursos() {
		return cursos.size();
	}

	public static Integer getNumTematicas() {
		return tematicas.size();
	}

	public static Integer getNumCentros() {
		// cuidao con los overflows
		Long lo = cursos.stream().map(l -> l.centro()).distinct().count();

		return lo.intValue();
	}

	public static Set<Integer> getTematicasCursos(Integer i) {
		return cursos.get(i).tematica();
	}

	public static Double getPrecioCurso(Integer i) {
		return cursos.get(i).precio();

	}

	public static Integer getCentroCurso(Integer i) {
		return cursos.get(i).centro();
	}

	private static void test() {
		iniDatos("ficheros/ejercicios/Ejercicio2DatosEntrada1.txt");
		System.out.println("el maximo de centros es: " + maxCentros);
		System.out.println("Los cursos son " + cursos);
	}

	public static void main(String[] args) {
		test();
	}

}
