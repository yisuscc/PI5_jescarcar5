package _datos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.basictypes.Test;
import us.lsi.common.Files2;

public class DatosEjercicio3 {
//INV1: capacidad=10; especialidad=0;
//T01 -> calidad=7; reparto=(0:2),(1:0),(2:5),(3:0);
public static record Investigador (String nombre, Integer capacidad, Integer especialidad) {
	public static Investigador create(String linea) {
		String []datos = linea.split(";");
		String[] aux= datos[0].split(":");
		String nom =  aux[0].trim();
		Integer cap = Integer.valueOf(aux[1].replace("capacidad=", "").trim());
		Integer esp = Integer.valueOf(datos[1].replace("especialidad=", "").trim());
		return new Investigador(nom, cap, esp);
	}
}
public static record Trabajo(String nombre, Integer calidad, Map<Integer,Integer>espDias) {
	public static Trabajo create(String linea) {
		String []datos = linea.split(";");
		String[] aux = datos[0].split("->");
		String nom =  aux[0].trim();
		Integer cal = Integer.valueOf(aux[1].replace("calidad=", "").trim());
		Map<Integer , Integer> eD = new HashMap<>();
		String[] aux1 = datos[1].replace("reparto=", "").trim().split(",");
		for( String a: aux1) {
			String[]cYv = a.replace("(", "").replace(")", "").split(":");
			eD.put(Integer.valueOf(cYv[0].trim()),  Integer.valueOf(cYv[1].trim()));
		}
		return new Trabajo(nom, cal, eD);
		
	}
}
	private static List<Investigador> investigadores;
	private static List<Trabajo>trabajos;
	
	public static List<Investigador> getInvestigadores(){
		return investigadores;
	}
	public static List<Trabajo> getTrabajos() {
		return trabajos;
	}
	public static Integer getDiaTrabajo(Integer trab) {
		return trabajos.get(trab).espDias().values().stream().mapToInt(i->i).sum();
	}
	public static Integer getDiaTrabajoInv(Integer trab, Integer inv) {
		Integer in = investigadores.get(inv).especialidad();
		return trabajos.get(trab).espDias.get(in);
		
	}
	public static Set<Investigador> getTrabajadoresConXesp(Integer x) {
		return investigadores.stream().filter(i-> i.especialidad().equals(x)).collect(Collectors.toSet());
	}
	public static Boolean getTrabajadorEsp(Integer i, Integer k) {
//		Copiado del Ejrecicio 3 ple	
		return investigadores.get(i).especialidad().equals(k);
	}
	
 
	public static void iniData(String fichero) {
		List<String> lsF  = Files2.linesFromFile(fichero);
		 Integer indexTrabajos = lsF.indexOf("// TRABAJOS");
		 List<Investigador> inv= List.copyOf(lsF.subList(1, indexTrabajos)).stream().map(s-> Investigador.create(s)).toList();
		 List<Trabajo> trab = List.copyOf(lsF.subList(indexTrabajos+1, lsF.size())).stream().map(s-> Trabajo.create(s)).toList();
		 trabajos = trab;
		 investigadores = inv;
	}
	
	
	public static void test( String fichero) {
		
			iniData(fichero);
		 System.out.println("Estos son los trabajos"+ getTrabajos());
		 System.out.println("Estos son los inv" + getInvestigadores());
		 
	}
	public static void main(String[] args) {
	//	 test("ficheros/ejercicios/Ejercicio3DatosEntrada1.txt");

		}
	
}

	


