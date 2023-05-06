package _datos;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.common.Set2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.views.CompleteGraphView;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class DatosEjercicio4 {


	public static record Cliente2(Integer idCliente, Double beneficio) {//para que no se confunda con el cliente del lsi 
		public static Cliente2 create(String[] aux) {
			// 0,0.
			// String[] aux = linea.split(",");
			return new Cliente2(Integer.valueOf(aux[0].trim()), Double.valueOf(aux[1].trim()));
		}
	}

	public static record Arista(Double kms, int id) {
		private static int  cont = 0;
		
		public static Arista create(String[] aux) {
			// 0,1,1.0
			// String[] aux = linea.split(",");
			int id = cont;
			cont++;
			return new Arista(Double.valueOf(aux[2].trim()),id);
		}
		public static Arista of(Double k) {
			int id = cont;
			cont++;
			return new Arista(k,id);
		}
	}

	private static IntegerVertexGraphView<Cliente2, Arista> intGrafo;// a diferencia de la pi4 lo voy a hacer con 
	private static Graph<Cliente2, Arista> grafo;// pongo el grafo normal por si acaso
	private static Integer n;

	public static void iniDatos(String fichero) {
		SimpleWeightedGraph<Cliente2, Arista> gra = GraphsReader.newGraph(fichero, Cliente2::create, Arista::create,Graphs2::simpleWeightedGraph, Arista::kms);
		//hacemos el grafo completo
		Double pesoMax = gra.edgeSet().stream().mapToDouble(m->m.kms()).sum()*1000;
		Graph<Cliente2, Arista> gra2 = CompleteGraphView.of(gra, ()-> Arista.of(pesoMax));
		//Graph<Cliente2, Arista> gra2 = gra;
		intGrafo = IntegerVertexGraphView.of(gra2);
		
		grafo = gra2;
		n = gra2.vertexSet().size();

	}
	
	// n el numero de vertices 
	public static Integer  getN() {
		return n;
	}
	// E las aristas del grafo
	public static Set<Arista> getAristas() {
		return grafo.edgeSet();
	}
	public static Set<SimpleEdge<Integer>> getIntAristas() {
		return intGrafo.edgeSet();
	}
	public static Set<Integer>getVecinos(Integer i){
		return Graphs.neighborSetOf(intGrafo,i);
	}
	
	
	public static Cliente2 getOrigen() {
		return intGrafo.getVertex(0);
	}
	public static Integer getIntOrigen() {
		return 0;
	}
	// wij double peso de la arista 
	public static Double getPesoArista(Integer i, Integer j) {
		return intGrafo.getEdgeWeight(i, j); 
	}
	// bi double beneficio del cliente ubicado en el vertice
	public static Double getBeneficioCliente(Integer i) {
		return intGrafo.getVertex(i).beneficio();
	}
	public static Boolean existeArista(Integer i, Integer j) {
		return intGrafo.containsEdge(i, j);
	}
	public static void test() {
		String fichero = "ficheros/Ejercicio4DatosEntrada1.txt";
		iniDatos(fichero);
		System.out.println(grafo);
	}
	public static void main(String[] args) {
	test();
	System.out.println("El vertice vecino es:"+intGrafo.getVertex(0));
	System.out.println("Vecinos del vertice 0:" + getVecinos(0));
	GraphColors.toDot(intGrafo, "grafos/grafo.gv");
	}

}
