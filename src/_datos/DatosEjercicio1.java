package _datos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import _datos.DatosEjercicio1.Cafe;
import us.lsi.common.Files2;

public class DatosEjercicio1 {
	// un tipo variedad cafe y otro tipo cafe
	public static record Cafe(String nombre, Integer peso) {

		public static Cafe create(String linea) {
			String[] datos = linea.split(":");// C01: kgdisponibles=35;
			String nom = datos[0].trim();
			String aux = datos[1].trim().replace("kgdisponibles=", " ").replace(';', ' ').trim();
			Integer peso = Integer.valueOf(aux);
			return new Cafe(nom, peso);
		}
	}

	public static record VarCafe(String nombre, Double beneficio, Map<String, Double> composicion) {
		public static VarCafe create(String linea) {
			// P01 -> beneficio=60; comp=(C01:0.5),(C03:0.4),(C07:0.1);
			String[] partes = linea.split(";");
			// nomYBen P01 -> beneficio=60
			String[] nomYBen = partes[0].split("->");
			String nom = nomYBen[0].trim();
			String aux = nomYBen[1].replace("beneficio=", " ");
			Double ben = Double.valueOf(aux.trim());
			// comp=(C01:0.5),(C03:0.4),(C07:0.1);
			Map<String, Double> comp = new HashMap<>();
			String[] claveYValorSucio = partes[1].replace("comp=", " ").replace(';', ' ').trim().split(",");
			for (String cYv : claveYValorSucio) {
				// (C01:0.5)
				String[] claveYvalor = cYv.replace("(", "").replace(")", "").trim().split(":");
				String clave = claveYvalor[0].trim();
				Double valor = Double.valueOf(claveYvalor[1].trim());
				comp.put(clave, valor);
			}
			return new VarCafe(nom, ben, comp);
		}
	}

	private static List<Cafe> cafes;
	private static List<VarCafe> variedades;

	public static void iniDatos(String fichero) {

		List<String> ls = Files2.linesFromFile(fichero);

		// el index de café lo podemos omitir ya que empieza siempre en cero
		Integer indexVariedades = ls.indexOf("// VARIEDADES");
		Integer fin = ls.size();
		List<String> lsCafe = List.copyOf(ls.subList(1, indexVariedades));
		List<String> lsVariedad = List.copyOf(ls.subList(indexVariedades + 1, fin));
		cafes = lsCafe.stream().map(s -> Cafe.create(s)).toList();
		variedades = lsVariedad.stream().map(s -> VarCafe.create(s)).toList();

	}

	public static List<Cafe> getCafes() {
		return cafes;
	}

	public static Cafe getCafeNom(String nom) {
		return cafes.stream().filter(c -> c.nombre().equals(nom)).findFirst().get();
	}

	public static String getCafe(Integer index) {
		return cafes.get(index).nombre();
	}

	public static List<VarCafe> getVariCafe() {
		return variedades;
	}

	public static String getNombreVarCafe(Integer index) {
		return variedades.get(index).nombre();
	}

	public static VarCafe getVarCafe(Integer index) { // no es necesario
		return variedades.get(index);
	}

	// nint tipos cafe
	public static Integer getTiposCafe() { // n
		return cafes.size();
	}

	// int var cafe
	public static Integer getNumVarCafe() { // m
		return variedades.size();
	}

	public static Integer getCafeDisp(Integer tipoCafe) {// entra j
		return cafes.get(tipoCafe).peso();
	}

	public static Double getBeneficioVar(Integer tipoVar) {// entra i
		return variedades.get(tipoVar).beneficio();
	}

	public static Double getPorcentajeCafeVar(Integer tipoCafe, Integer tipoVar) {
		String nomCafe = getCafe(tipoCafe);
		return variedades.get(tipoVar).composicion.getOrDefault(nomCafe, 0.0);
	}

	// // cantidad
	// public static List<Cafe> getCafes() {
	// return cafe;
	// }
	//
	// public static List<VarCafe> getVariedades() {
	// return variedades;
	// }
	private static void testLectura() {
		iniDatos("ficheros/ejercicios/Ejercicio1DatosEntrada1.txt");
		System.out.println("Estas son los tipos de cafe" + cafes);
		System.out.println("Estas son las variedades" + variedades);

	}

	public static void main(String[] args) {
		testLectura();

	}

}
