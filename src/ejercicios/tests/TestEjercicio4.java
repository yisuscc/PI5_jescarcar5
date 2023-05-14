package ejercicios.tests;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio4;
import _soluciones.solEjercicios.SolucionCafe;
import _soluciones.solEjercicios.SolucionReparto;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio1.CafeVertex;
import ejercicios.ejercicio4.RepartoVertex;

public class TestEjercicio4 {

	public static void main(String[] args) {
		System.out.println("Si el resultado no es correcto,pon el kmsMedio de la heuristica a 1.");
		List.of(1, 2).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio4DatosEntrada", num_test, DatosEjercicio4::iniDatos);
			RepartoVertex v_inicial = RepartoVertex.initial();
			Predicate<RepartoVertex> es_terminal = RepartoVertex.goal();
			var gp = TestsPI5.testGreedy(GraphsPI5.greedyRepartoGraph(v_inicial, es_terminal));
			TestsPI5.toConsole("Voraz", gp, SolucionReparto::of);

		var path = TestsPI5.testAStar(GraphsPI5.repartoGraph(v_inicial, es_terminal), gp);
		TestsPI5.toConsole("A*", path, SolucionReparto::of);

		path = TestsPI5.testPDR(GraphsPI5.repartoGraph(v_inicial, es_terminal), gp);
		TestsPI5.toConsole("PDR", path, SolucionReparto::of);

		path = TestsPI5.testBT(GraphsPI5.repartoGraph(v_inicial, es_terminal), gp);
		TestsPI5.toConsole("BT", path, SolucionReparto::of);

		TestsPI5.line("*");

		});

	}


	}


