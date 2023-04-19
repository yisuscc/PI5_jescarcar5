package ejercicios.ejercicio1.PDManual;

public class CafePD {
	public static record SpCafe(Integer a, Integer weight)implements Comparable<SpCafe>{
		public static SpCafe of(Integer a, Integer weight) {
			return new SpCafe(a, weight);
		}

		@Override
		public int compareTo(SpCafe o) {
			
			return this.weight().compareTo(o.weight());
		}
		
		
	}
}
