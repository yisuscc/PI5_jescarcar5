package ejercicios.tests;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosEjercicio2;
import _soluciones.solEjercicios.SolucionCursos;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio2.CursoVertex;

public class TestEjercicio2 {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio2DatosEntrada", num_test, DatosEjercicio2::iniDatos);
			CursoVertex v_inicial = CursoVertex.initial();
			Predicate<CursoVertex> es_terminal = CursoVertex.goal();
			var gp = TestsPI5.testGreedy(GraphsPI5.greedyCursoGraph(v_inicial, es_terminal));
			TestsPI5.toConsole("Voraz", gp, SolucionCursos::of);
			
			var path = TestsPI5.testAStar(GraphsPI5.cursoGraph(v_inicial, es_terminal),gp);
			TestsPI5.toConsole("A*", path, SolucionCursos::of);
			
			path = TestsPI5.testPDR(GraphsPI5.cursoGraph(v_inicial, es_terminal),gp);
			TestsPI5.toConsole("PDR", path, SolucionCursos::of);
			
			path = TestsPI5.testBT(GraphsPI5.cursoGraph(v_inicial, es_terminal),gp);
			TestsPI5.toConsole("BT", path, SolucionCursos::of);
			
			
			TestsPI5.line("*");

		});

	}

}
