package ejercicios.tests;

import java.util.List;
import java.util.function.Predicate;


import _datos.DatosEjercicio3;
import _soluciones.solEjercicios.SolucionEjercicio3;
import _utils.GraphsPI5;
import _utils.TestsPI5;

import ejercicios.ejercicio3.Ejer3Edge;
import ejercicios.ejercicio3.Ejer3Vertex;

public class TestEjercicio3 {

	public static void main(String[] args) {
		List.of(1, 2,3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio3DatosEntrada", num_test, DatosEjercicio3::iniDatos);
			Ejer3Vertex v_inicial = Ejer3Vertex.initial();
			Predicate<Ejer3Vertex> es_terminal = Ejer3Vertex.goal();
			var gp = TestsPI5.testGreedy(GraphsPI5.greedyEjer3Graph(v_inicial, es_terminal));
			TestsPI5.toConsole("Voraz", gp, SolucionEjercicio3::of);
			
			var path = TestsPI5.testAStar(GraphsPI5.ejer3Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("A*", path, SolucionEjercicio3::of);

			path = TestsPI5.testPDR(GraphsPI5.ejer3Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("PDR", path, SolucionEjercicio3::of);

			path = TestsPI5.testBT(GraphsPI5.ejer3Graph(v_inicial, es_terminal),gp);
			TestsPI5.toConsole("BT", path, SolucionEjercicio3::of);

			TestsPI5.line("*");

		});


	}

}
