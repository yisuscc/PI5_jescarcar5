package ejercicios.tests.manual;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import _datos.DatosEjercicio3;
import _soluciones.solEjercicios.SolucionEjercicio3;
import _utils.TestsPI5;
import ejercicios.ejercicio3.manual.Ejer3BT;
import us.lsi.common.String2;

public class TestEj3Manual {
/*
 * 
 */
	public static void main(String[] args) {
	
		List.of(1, 2,3).forEach(num_test -> {
			DatosEjercicio3.iniDatos("ficheros/Ejercicio3DatosEntrada" + num_test + ".txt");
			Ejer3BT.search();
			Function<SolucionEjercicio3,Integer>funk  = v-> v.getCalidad();
		    SolucionEjercicio3 sr=  Ejer3BT.getSoluciones().stream().max(Comparator.comparing(funk)).get();
		    String2.toConsole("Solucion obtenida: %s\n", sr);
			TestsPI5.line("*");
		});
	}

}
