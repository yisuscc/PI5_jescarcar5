package _utils;

import java.util.function.Predicate;

import ejemplos.ejemplo1.MulticonjuntoEdge;
import ejemplos.ejemplo1.MulticonjuntoHeuristic;
import ejemplos.ejemplo1.MulticonjuntoVertex;
import ejemplos.ejemplo2.SubconjuntosEdge;
import ejemplos.ejemplo2.SubconjuntosHeuristic;
import ejemplos.ejemplo2.SubconjuntosVertex;
import ejemplos.ejemplo3.AlumnosEdge;
import ejemplos.ejemplo3.AlumnosHeuristic;
import ejemplos.ejemplo3.AlumnosVertex;
import ejercicios.ejercicio1.CafeEdge;
import ejercicios.ejercicio1.CafeHeuristic;
import ejercicios.ejercicio1.CafeVertex;
import ejercicios.ejercicio2.CursoEdge;
import ejercicios.ejercicio2.CursoHeuristic;
import ejercicios.ejercicio2.CursoVertex;
import ejercicios.ejercicio3.Ejer3Edge;
import ejercicios.ejercicio3.Ejer3Heuristic;
import ejercicios.ejercicio3.Ejer3Vertex;
import ejercicios.ejercicio4.RepartoEdge;
import ejercicios.ejercicio4.RepartoHeuristica;
import ejercicios.ejercicio4.RepartoVertex;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

// Clase Factoria para crear los grafos de los ejemplos y ejercicios
public class GraphsPI5 {

	// Ejemplo1: Grafo NO Greedy
	public static EGraph<MulticonjuntoVertex, MulticonjuntoEdge> multisetGraph(MulticonjuntoVertex v_inicial,
			Predicate<MulticonjuntoVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				.goalHasSolution(MulticonjuntoVertex.goalHasSolution()).heuristic(MulticonjuntoHeuristic::heuristic)
				.build();
	}

	// Ejemplo1: Grafo Greedy
	public static EGraph<MulticonjuntoVertex, MulticonjuntoEdge> greedyMultisetGraph(MulticonjuntoVertex v_inicial,
			Predicate<MulticonjuntoVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				.greedyEdge(MulticonjuntoVertex::greedyEdge).goalHasSolution(MulticonjuntoVertex.goalHasSolution())
				.heuristic(MulticonjuntoHeuristic::heuristic).build();
	}

	// Ejemplo2: Grafo NO Greedy
	public static EGraph<SubconjuntosVertex, SubconjuntosEdge> subsetGraph(SubconjuntosVertex v_inicial,
			Predicate<SubconjuntosVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				.goalHasSolution(SubconjuntosVertex.goalHasSolution()).heuristic(SubconjuntosHeuristic::heuristic)
				.build();
	}

	// Ejemplo2: Grafo Greedy
	public static EGraph<SubconjuntosVertex, SubconjuntosEdge> greedySubsetGraph(SubconjuntosVertex v_inicial,
			Predicate<SubconjuntosVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min).greedyEdge(SubconjuntosVertex::greedyEdge)
				.goalHasSolution(SubconjuntosVertex.goalHasSolution()).heuristic(SubconjuntosHeuristic::heuristic)
				.build();
	}

	// Ejemplo3: Grafo NO Greedy
	public static EGraph<AlumnosVertex, AlumnosEdge> academyGraph(AlumnosVertex v_inicial,
			Predicate<AlumnosVertex> es_terminal) {
		
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min).greedyEdge(AlumnosVertex::greedyEdge)
				.goalHasSolution(AlumnosVertex.goalHasSolution()).heuristic(AlumnosHeuristic::heuristic)
				.build();
	}

	// Ejemplo3: Grafo Greedy
	public static EGraph<AlumnosVertex, AlumnosEdge> greedyAcademyGraph(AlumnosVertex v_inicial,
			Predicate<AlumnosVertex> es_terminal) {
		
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min).greedyEdge(AlumnosVertex::greedyEdge)
				.goalHasSolution(AlumnosVertex.goalHasSolution()).heuristic(AlumnosHeuristic::heuristic)
				.build();
	}

	// Ejercicio 1 . Grafo no greedy
	public static EGraph<CafeVertex, CafeEdge> coffeeGraph(CafeVertex v_inicial, Predicate<CafeVertex> es_terminal) {

		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				.goalHasSolution(CafeVertex.goalHasSolution()).heuristic(CafeHeuristic::heuristic).build();
	}

	// Ejercicio 1: Grafo Greedy
	public static EGraph<CafeVertex, CafeEdge> greedyCoffeeGraph(CafeVertex v_inicial,
			Predicate<CafeVertex> es_terminal) {

		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max).greedyEdge(CafeVertex::greedyEdge)
				.build();
	}

	// Ejercicio 2: Grafo no greedy
	public static EGraph<CursoVertex, CursoEdge> cursoGraph(CursoVertex v_inicial, Predicate<CursoVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min).goalHasSolution(es_terminal)
				.heuristic(CursoHeuristic::heuristic).build();
	}

	// Ejercicio 2: Grafo greedy
	public static EGraph<CursoVertex, CursoEdge> greedyCursoGraph(CursoVertex v_inicial,
			Predicate<CursoVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min ).greedyEdge(CursoVertex::greedyEdge)
				.heuristic(CursoHeuristic::heuristic).build();
	}
	
	//Ejercio 3: Grafo no greedy
	public static EGraph<Ejer3Vertex, Ejer3Edge>ejer3Graph(Ejer3Vertex v_inicial,Predicate<Ejer3Vertex>es_terminal){
		return  EGraph.virtual(v_inicial,es_terminal,PathType.Last,Type.Max).vertexWeight(v->v.calidadObtenida()).
				goalHasSolution(es_terminal).heuristic(Ejer3Heuristic::heuristic).build();
	}
	//Ejercicio 4: Grafo no greedy
	public static EGraph<RepartoVertex, RepartoEdge>repartoGraph(RepartoVertex v_inicial,Predicate<RepartoVertex>es_terminal){
		return EGraph.virtual(v_inicial,es_terminal,PathType.Sum,Type.Max).goalHasSolution(es_terminal).heuristic(RepartoHeuristica::heuristic).build();
	}
	//Ejercicio 4 grafo greedy 
	public static EGraph<RepartoVertex, RepartoEdge>greedyRepartoGraph(RepartoVertex v_inicial,Predicate<RepartoVertex>es_terminal){
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum,Type.Max).greedyEdge(RepartoVertex::greedyEdge).build();
	}

}
