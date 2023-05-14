package ejercicios.tests.manual;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import _datos.DatosEjercicio4;
import _soluciones.solEjercicios.SolucionEjercicio3;
import _soluciones.solEjercicios.SolucionReparto;
import _utils.TestsPI5;
import ejercicios.ejercicio3.manual.Ejer3BT;
import ejercicios.ejercicio4.manual.RepartoBT;
import us.lsi.common.String2;

public class TestEj4Manual {

	public static void main(String[] args) {
		System.out.println("Si el resultado no es correcto,pon el kmsMedio de la heuristica a 1.");
		System.out.println("o bien pon la cota a Double.MaxValue");
		List.of(1, 2).forEach(num_test -> {
			DatosEjercicio4.iniDatos("ficheros/Ejercicio4DatosEntrada" + num_test + ".txt");
			RepartoBT.search();
			Function<SolucionReparto,Double>funk  = v-> v.getBeneficio();
		    SolucionReparto sr=  	RepartoBT.getSoluciones().stream().max(Comparator.comparing(funk)).get();
		    String2.toConsole("Solucion obtenida: %s\n", sr);
			TestsPI5.line("*");
		});
	}

}
