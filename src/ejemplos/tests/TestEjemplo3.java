package ejemplos.tests;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosAlumnos;
import _datos.DatosMulticonjunto;
import _soluciones.SolucionAlumnos;

import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejemplos.ejemplo3.AlumnosVertex;


public class TestEjemplo3 {

	public static void main(String[] args) {
		List.of(1, 2, 3).forEach(num_test -> {
			TestsPI5.iniTest("Ejemplo3DatosEntrada", num_test, DatosAlumnos::iniDatos);

			// TODO Defina un m. factoria para el vertice inicial
			AlumnosVertex v_inicial = AlumnosVertex.initial();
			// TODO Defina un m. static para los vertices finales
			Predicate<AlumnosVertex> es_terminal = AlumnosVertex.goal();

			var gp = TestsPI5.testGreedy(GraphsPI5.greedyAcademyGraph(v_inicial, es_terminal));
			TestsPI5.toConsole("Voraz", gp, SolucionAlumnos::of);

			var path = TestsPI5.testAStar(GraphsPI5.academyGraph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("A*", path, SolucionAlumnos::of);

			path = TestsPI5.testPDR(GraphsPI5.academyGraph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("PDR", path, SolucionAlumnos::of);

			path = TestsPI5.testBT(GraphsPI5.academyGraph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("BT", path, SolucionAlumnos::of);

			TestsPI5.line("*");

		});

	}

}
