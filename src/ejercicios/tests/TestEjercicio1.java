package ejercicios.tests;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosEjercicio1;
import _datos.DatosMulticonjunto;
import _soluciones.SolucionMulticonjunto;
import _soluciones.solEjercicios.SolucionCafe;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejemplos.ejemplo1.MulticonjuntoVertex;
import ejercicios.ejercicio1.CafeVertex;

public class TestEjercicio1 {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest("ejercicios/Ejercicio1DatosEntrada", num_test, DatosEjercicio1::iniDatos);
			
			// TODO Defina un m. factoria para el vertice inicial
			CafeVertex v_inicial = CafeVertex.initial();
			// TODO Defina un m. static para los vertices finales 
			Predicate<CafeVertex> es_terminal = CafeVertex.goal();
			
			var gp = TestsPI5.testGreedy(GraphsPI5.greedyCoffeeGraph(v_inicial, es_terminal));
//			TestsPI5.toConsole("Voraz", gp, SolucionCafe::of);
			//TestsPI5.toConsole("Voraz", gp, SolucionCafe::of)
			
			var path = TestsPI5.testAStar(GraphsPI5.multisetGraph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("A*", path, SolucionMulticonjunto::of);
			
			path = TestsPI5.testPDR(GraphsPI5.multisetGraph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("PDR", path, SolucionMulticonjunto::of);
			
			path = TestsPI5.testBT(GraphsPI5.multisetGraph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("BT", path, SolucionMulticonjunto::of);
			
			TestsPI5.line("*");

		});
		

	}

}
