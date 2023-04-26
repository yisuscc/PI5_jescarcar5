package _datos;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.common.Set2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class DatosEjercicio4 {


	public static record Cliente2(Integer idCliente, Double beneficio) {//para que no se confunda con el cliente del lsi 
		public static Cliente2 create(String[] aux) {
			// 0,0.
			// String[] aux = linea.split(",");
			return new Cliente2(Integer.valueOf(aux[0].trim()), Double.valueOf(aux[1].trim()));
		}
	}

	public static record Arista(Integer idCliente1, Integer idCliente2, Double kms) {
		public static Arista create(String[] aux) {
			// 0,1,1.0
			// String[] aux = linea.split(",");
			return new Arista(Integer.valueOf(aux[0].trim()), Integer.valueOf(aux[1].trim()),
					Double.valueOf(aux[1].trim()));
		}
		public static Arista of(Integer idCliente1, Integer idCliente2, Double k) {
			return new Arista(idCliente1, idCliente2, k);
		}
	}

	private static IntegerVertexGraphView<Cliente2, Arista> intGrafo;// a diferencia de la pi4 lo voy a hacer con 
	private static Graph<Cliente2, Arista> grafo;// pongo el grafo normal por si acaso
	private static Integer n;

	public static void iniDatos(String fichero) {//TODO Igrafo integer o grafo normal 
		SimpleWeightedGraph<Cliente2, Arista> gra = GraphsReader.newGraph(fichero, Cliente2::create, Arista::create,Graphs2::simpleWeightedGraph, Arista::kms);
		//hacemos el grafo completo
		for(Cliente2 c1:gra.vertexSet()) {
			Set<Cliente2>s = Set2.difference(gra.vertexSet(), Set.of(c1));
			for(Cliente2 c2 : s) {
				if(!gra.containsEdge(c2, c1)) {
					gra.addEdge(c1,c2,Arista.of(c1.idCliente(), c2.idCliente(),1000.));
					
				}
			}
		}
		intGrafo = IntegerVertexGraphView.of(gra);
		
		grafo = gra;
		n = gra.vertexSet().size();

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
