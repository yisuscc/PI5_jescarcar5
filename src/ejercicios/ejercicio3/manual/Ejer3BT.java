package ejercicios.ejercicio3.manual;

import java.util.Set;

import _soluciones.solEjercicios.SolucionEjercicio3;
import us.lsi.common.Set2;

public class Ejer3BT {

	private static Double mejorValor;
	private static Ejer3State estado;
	private static Set<SolucionEjercicio3> soluciones;

	public static void search() {
		soluciones = Set2.newTreeSet();// por que rtreeeset y no hash set?
		mejorValor = Double.MIN_VALUE;// maximizando;
		estado = Ejer3State.initial();
		bt_search();
	}

	private static void bt_search() {
		// el objetivo es generar un
		if (estado.esSolucion()) {
			Double valorObtenido = estado.acumulado;
			if (valorObtenido > mejorValor) { // Estamos minimizando
				mejorValor = valorObtenido;
				soluciones.add(estado.getSolucion());
			}
		} else if (!estado.esTerminal()) {
			// siempre y cuando no haya termicado estudio las alternativas de cada estado
			for (Integer a : estado.alternativas()) {
//				me interesa ir por esa rama
//				depende si la cota  es mayosd que , entonces se porque estoy maximizando
				if (estado.cota(a) >= mejorValor) { // Estamos maximizando
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}
	public static Set<SolucionEjercicio3> getSoluciones(){
		return soluciones;
	}

}
