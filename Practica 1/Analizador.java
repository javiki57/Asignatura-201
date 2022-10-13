import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Analizador {
	
	private static long v1[];
	private static long v2[];
	/* 
	 * NOTA IMPORTANTE
	 * 
	 * Esta clase se proporciona solamente para ilustrar el formato de
	 * salida que deberia tener la solucion a este ejericio.
	 * Esta clase debe modificarse completamente para cumplir 
	 * mínimamente los requisitos de esta practica.
	 * Notese que ni siquiera esta completa......
	 */
	
	public static String masCercano(double ratio) {
			if (ratio < 1.5) {                      // aprox 1.0
				return "1";	
			} else if (1 <= ratio && ratio < 3.0) { // aprox 2.0
				return "LOG";	
			} else if (1 <= ratio && ratio < 3.0) { // aprox 2.0
				return "N";
			} else if (1 <= ratio && ratio < 3.0) { // aprox 2.0
				return "NLOG";
			} else if (3 <= ratio && ratio < 6.0) { // aprox 4.0
				return "N2";
			} else if (6 <= ratio && ratio <12.0) { // aprox 8.0
				return "N3";
			} else if (6 <= ratio && ratio < 10.0) { // aprox 8.0
				return "2N";
			} else { 								 // otras
				return "NF";
			}
	}

	private static final long[] n = {1,2,3,4,5,6,7,8,9,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,
			100,300,500,700,1000,3000,5000,7000,10000,30000,50000,70000,
			100000,200000,300000,500000,700000,800000,850000,900000,950000,
			1000000,1500000,2000000,2500000,3000000,3500000,5000000,7000000};  //vector para medir tiempos con complejidades pequeñas


	private static final long[] n2 = {1,2,3,4,5,6,7,8,9,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,
			100,300,500,700,1000,3000,5000,7000}; //vector para medir tiempos para diferenciar N2 y NLOGN

	private static double[] tiempos = {};

	public static boolean esGrande(){ //ignora los primeros valores
		double media1=0;
		double media2=0;
		boolean esNF=false;
		Temporizador t = new Temporizador();

		int tam=12;

		for(int i = 0;i<tam;i++){ //bucle que mide los 10 primeros tiempos porque si es una complejidad muy grande con el vector n no acabaría nunca, en
			t.iniciar();  // cambio con los 10 primeros valores, que ademas son pequeños, nos puede dar una respuesta sobre si será una complejidad muy grande o pequeña
			Algoritmo.f(n[i]);
			t.parar();
			tiempos[i]=t.tiempoPasado();


			if(i!=0) {
				if ((t.tiempoPasado() / tiempos[i - 1]) > 10) { //si la pendiente de los tiempos es
					i = tam;								// mayor que 10 será de complejidad NF
					esNF = true;
				}
			}

			t.reiniciar();
		}

		if(!esNF) {

			for (int i = 2; i <= (tam / 2); i++) { //suma los 5 primeros tiempos
				media1 += tiempos[i];			// y los divide entre los 5 últimos
			}
			for (int i = ((tam / 2) + 1); i < tam; i++) {
				media2 += tiempos[i];
			}


			double Mmedia = media2 / media1;

			if ((media2 / media1) < 1.7) {  //si supera 1.7 será de complejidad mayor a n
				return false;
			} else {
				if (Mmedia < 4) { //segun como sea la media sera de una complejidad u otra

					for (int i = 0; i < n2.length; i++) {
						t.iniciar();
						Algoritmo.f(n2[i]);
						t.parar();
						tiempos[i] = t.tiempoPasado();
						t.reiniciar();
					}

					double mediaN=media(tiempos);
					if(mediaN<65000){
						System.out.println("NLOGN");
					}else{
						System.out.println("N2");
					}

				} else if (Mmedia < 8) {
					System.out.print("N3");
				} else {
					System.out.print("2N");
				}

				return true;
			}

		}
		System.out.print("NF");
		return true;
	}

	public static double media(double V[]){  //ignora el primer valor debido a que siempre es muy alto
		double media=0;
		for(int i = 1;i<V.length;i++){
			media+=V[i];
		}
		return media/(V.length-1);

	}

	// uno-media -> 380<
	// logn-media -> 1200<
	// n-media -> 100000<
	// nlogn-media -> resto

	// n2-esGrande -> 1.7>
	// nlogn-esGrande -> 1.7>
	// n3-esGrande -> 3,5>
	// 2n-esGrande -> 8>
	// nf-esGrande -> no termina

	public static void main(String arg[]) {


		tiempos=new double[63];
		double media=0;
		Temporizador t = new Temporizador();

		if(!esGrande()) { //si no es grande entonces la complejidad seá menor o igual a n

			for (int i = 0; i < n.length; i++) {
				t.iniciar();
				Algoritmo.f(n[i]);
				t.parar();
				tiempos[i] = t.tiempoPasado();
				t.reiniciar();
			}


			media=media(tiempos); //se hace la media de los tiempos y se clasifican segun su valor
			if(media<180){
				System.out.print("1");
			}else if(media<10000){
				System.out.print("LOGN");
			}else{
				System.out.print("N");
			}
		}

	}
}
