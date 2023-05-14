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

	private static void  bt_search() {
		if(estado.esSolucion()) {
			Double valorObtenido = estado.acumulado;
			if(valorObtenido > mejorValor) {
				mejorValor = valorObtenido;
				soluciones.add(estado.getSolucion());
			}
		}else if(!estado.esTerminal()) {//siempre ycuando no haya terminado estudio las alternativas de cada vertice
			for(Integer a: estado.alternativas()) {
				if(estado.cota(a)>= mejorValor) {
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
