package ejercicios.tests;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosEjercicio1;
import _soluciones.SolucionMulticonjunto;
import _soluciones.solEjercicios.SolucionCafe;
import _utils.GraphsPI5;
import _utils.TestsPI5;

import ejercicios.ejercicio1.CafeVertex;

public class TestEjercicio1 {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio1DatosEntrada", num_test, DatosEjercicio1::iniDatos);
			CafeVertex v_inicial = CafeVertex.initial();
			Predicate<CafeVertex> es_terminal = CafeVertex.goal();
			var gp = TestsPI5.testGreedy(GraphsPI5.greedyCoffeeGraph(v_inicial, es_terminal));
			TestsPI5.toConsole("Voraz", gp, SolucionCafe::of);
			
			var path = TestsPI5.testAStar(GraphsPI5.coffeeGraph(v_inicial, es_terminal),gp);
			TestsPI5.toConsole("A*", path, SolucionCafe::of);
			
			path = TestsPI5.testPDR(GraphsPI5.coffeeGraph(v_inicial, es_terminal),gp);
			TestsPI5.toConsole("PDR", path, SolucionCafe::of);
			
			path = TestsPI5.testBT(GraphsPI5.coffeeGraph(v_inicial, es_terminal),gp);
			TestsPI5.toConsole("BT", path, SolucionCafe::of);
			
			
			TestsPI5.line("*");

		});
		

	}

}
