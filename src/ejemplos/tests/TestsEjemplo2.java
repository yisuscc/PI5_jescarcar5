package ejemplos.tests;

import java.util.List;
import java.util.function.Predicate;
import _datos.DatosSubconjuntos;
import _soluciones.SolucionSubconjuntos;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejemplos.ejemplo2.SubconjuntosVertex;

public class TestsEjemplo2 {

	public static void main(String[] args) {
		List.of(1,2).forEach(num_test -> {
			TestsPI5.iniTest("Ejemplo2DatosEntrada", num_test, DatosSubconjuntos::iniDatos);
			
			// TODO Defina un m. factoria para el vertice inicial
			SubconjuntosVertex v_inicial = SubconjuntosVertex.initial();
			// TODO Defina un m. static para los vertices finales 
			Predicate<SubconjuntosVertex> es_terminal = SubconjuntosVertex.goal();
			
			var gp = TestsPI5.testGreedy(GraphsPI5.greedySubsetGraph(v_inicial, es_terminal));
			TestsPI5.toConsole("Voraz", gp, SolucionSubconjuntos::of);
			
			var path = TestsPI5.testAStar(GraphsPI5.subsetGraph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("A*", path, SolucionSubconjuntos::of);
			
			path = TestsPI5.testPDR(GraphsPI5.subsetGraph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("PDR", path, SolucionSubconjuntos::of);
			
			path = TestsPI5.testBT(GraphsPI5.subsetGraph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("BT", path, SolucionSubconjuntos::of);
			
			TestsPI5.line("*");

		});
	}

}
