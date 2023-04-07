package _datos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;

public class DatosEjercicio2 {
//Max_Centros = 1
	//{1,2,3,4}:10.0:0
	//{1,4}:3.0:0
public static record Cursos(Set<Integer> tematica, Double matricula, Integer centro) {
		public static Cursos create(String linea) {
			String [] datos = linea.split(":");
			Set<Integer> cur = new HashSet<>();
			String[]d = datos[0].replace("}","").replace("{","").split(",");
			for(String i: d) {
				cur.add(Integer.valueOf(i.trim()));
			}
			Double mat= Double.valueOf(datos[1].trim());
			Integer cent = Integer.valueOf(datos[2].trim());
			return new Cursos(cur, mat, cent);
			
		}
	}
	private static Integer maxCentros ;
	private static List<Cursos> todoCursos;
	public static void iniDatos(String fichero) {
		List<String> lsFich = Files2.linesFromFile(fichero);
		maxCentros = Integer.valueOf(lsFich.get(0).replace("Max_Centros = ", "").trim());
		List<Cursos> temp = new ArrayList<>();
		for(int i = 1; i < lsFich.size();i++) {// se puede hacer con stream skip
			
			temp.add(Cursos.create(lsFich.get(i)));
		}
		todoCursos = temp;
	}
	public static Integer getMaxCentros() {
		return maxCentros;
	}
	public static List<Cursos> getCursos(){
		return todoCursos;
	}
	private static void test() {
		iniDatos("ficheros/ejercicios/Ejercicio2DatosEntrada1.txt");
		System.out.println("el maximo de centros es: " + maxCentros);
		System.out.println("Los cursos son "+ todoCursos);
	}
	public static void main(String[] args) {
		test();
	}

}
