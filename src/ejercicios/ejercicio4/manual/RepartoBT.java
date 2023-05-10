package ejercicios.ejercicio4.manual;

import java.util.Set;

import _soluciones.solEjercicios.SolucionReparto;
import us.lsi.common.Set2;

public class RepartoBT {

	private static Double mejorValor;
	private static RepartoState estado;
	private static Set<SolucionReparto> soluciones;

	public static void search() {
		 soluciones = Set2.newTreeSet();
		 mejorValor = Double.MIN_VALUE; //maximizabndo
		 estado = RepartoState.initial();
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
	public static Set<SolucionReparto>getSoluciones(){
		return soluciones;
	}

}
