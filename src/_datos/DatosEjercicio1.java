package _datos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import _datos.DatosEjercicio1.tipoCafe;
import us.lsi.common.Files2;

public class DatosEjercicio1 {
// un tipo  variedad cafe y otro tipo cafe
	public static record tipoCafe(Integer id, String nombre, Integer peso) {
		//realmente el atributo id sobra 
		// ya que vale lo mismo que la posición en la lista
		public static tipoCafe create(String linea, Integer id) {
		String[] datos = linea.split(":");//C01: kgdisponibles=35;
		String nom = datos[0].trim();
		String aux = datos[1].trim().replace("kgdisponibles=", " ").replace(';', ' ').trim();
		Integer peso = Integer.valueOf(aux);
		return new tipoCafe(id, nom, peso);
		}
	}

	public static record variedadCafe(Integer id, String nombre, Double beneficio, Map<String, Double> composicion) {
		public static variedadCafe create( String linea ,Integer id) {
			//P01 -> beneficio=60; comp=(C01:0.5),(C03:0.4),(C07:0.1);
			String[] partes = linea.split(";");
			//nomYBen  P01 -> beneficio=60
			String[] nomYBen = partes[0].split("->");
			String nom = nomYBen[0].trim();
			String aux = nomYBen[1].replace("beneficio=", " ");
			Double ben = Double.valueOf(aux.trim());
			// comp=(C01:0.5),(C03:0.4),(C07:0.1);
			Map<String,Double> comp = new HashMap<>();
			String[] claveYValorSucio =  partes[1].replace("comp=", " ").replace(';', ' ').trim().split(",");
			for(String cYv: claveYValorSucio) {
				//(C01:0.5)
				String [] claveYvalor = cYv.replace("(", "").replace(")", "").trim().split(":");
			 String clave = claveYvalor[0].trim();
			 Double valor = Double.valueOf(claveYvalor[1].trim());
			comp.put(clave, valor);
			}
			return new variedadCafe(id, nom, ben, comp);
		}
	}
	private static List<tipoCafe> cafe;
	private static List<variedadCafe> variedades;
	public static void iniDatos(String fichero) {
		Integer contCaf = -1;
		Integer contVar = -1;
		List<String> ls =  Files2.linesFromFile(fichero);
		
		//el index de café lo podemos omitir ya que empieza siempre en cero
		Integer indexVariedades = ls.indexOf("// VARIEDADES");
		Integer fin = ls.size();
		List<String> lsCafe = List.copyOf(ls.subList(1, indexVariedades)); 
		List<String> lsVariedad = List.copyOf(ls.subList(indexVariedades+1, fin));
		cafe = lsCafe.stream().map(s-> tipoCafe.create(s, contCaf+1)).toList();
		variedades = lsVariedad.stream().map(s-> variedadCafe.create(s,contVar+1)).toList();
		
	}

public static tipoCafe getCafeNom(String nom) {
	return cafe.stream().filter(c->c.nombre().equals(nom)).findFirst().get();
}
	public static String cafeId(Integer id) {
		return cafe.get(id).nombre();
	}

	public static String variedadId(Integer id) {
		return variedades.get(id).nombre();
	}

	// nint tipos cafe
	public static Integer getTiposCafe() {
		return cafe.size();
	}

	// int var cafe
	public static Integer getVarCafe() {
		return variedades.size();
	}

	// cantidad
	public static List<tipoCafe> getCafes() {
		return cafe;
	}

	public static List<variedadCafe> getVariedades() {
		return variedades;
	}
private static void testLectura() {
	iniDatos("ficheros/ejercicios/Ejercicio1DatosEntrada1.txt");
	System.out.println("Estas son los tipos de cafe"+ cafe);
	System.out.println("Estas son las variedades"+ variedades);
	
}
	public static void main(String[] args) {
		testLectura();

		

	}

}
