
public class Analizador {

	// Utilizaremos n1 para medir las complejidades pequenas
	private static final long [] n1 = {1,2,3,4,5,6,7,8,9,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,
			300,500,700,1000,3000,5000,7000,10000,30000,50000,70000,
			100000,200000,300000,500000,700000,800000,850000,900000,950000,
			1000000,1500000,2000000,2500000,3000000,3500000,5000000,7000000};

	// Con este vector diferenciaremos tiempos de complejidades como N2 y NLOGN
	private static final long [] n2 = {1,2,3,4,5,6,7,8,9,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,
			300,500,700,1000,3000,5000,7000};

	private static double [] tiempo = {};


	//Procedimiento para calcular la media.
	public static double media(double a[]){
		double media=0.0;

		for(int i=1;i<a.length;i++){ //cogemos a partir del segundo valor para mas exactitud
			media+=a[i];
		}

		media=media/a.length-1;

		return media;
	}

	public static boolean costeAlto(){
		boolean esGrande=true;
		double suma1 = 0.0;
		double suma2 = 0.0;
		double ratio= 0.0;

		/*
		Sumamos los 5 primeros tiempos y los dividimos entre los 5 ultimos
		para establecer un ratio.
		 */
		if(!esNF()){

			for(int i=1;i<=5;i++){
				suma1+=tiempo[i];
			}

			for(int i=10;i>5;i--){
				suma2+=tiempo[i];
			}

			ratio = suma2/suma1;

			// Si supera este ratio, quiere decir que la complejidad es superior a N
			if(ratio<1.7) return esGrande=false;

			else{
				if(ratio<4){
					Temporizador t = new Temporizador();

					for(int i=0;i<n2.length;i++){
						t.iniciar();
						Algoritmo.f(n2[i]);
						t.parar();
						tiempo[i] = t.tiempoPasado();
						t.reiniciar();
					}

					double media=media(tiempo);

					if(media<65000){

						System.out.println("NLOGN");
					
                                        }else{
						System.out.println("N2");

					}
                                        
				}else if(ratio<8){

					System.out.println("N3");

				}else{

					System.out.println("2N");
				}

				return esGrande;
			}
		}else{

			System.out.println("NF");
		}

		return esGrande;
	}

	/*
	En este metodo vamos a coger algunos valores del array n1 para tener una
	idea a priori de la complejidad con valores pequenos, ya que, en otro caso,
	si fuese muy grande los tiempos serian muy grandes. A su vez, tambien
	comprobamos si es NF.
	 */
	public static boolean esNF(){
		boolean NF=false;
		Temporizador t = new Temporizador();

		for(int i=0;i<12;i++){
			t.iniciar();
			Algoritmo.f(n1[i]);
			t.parar();
			tiempo[i]=t.tiempoPasado();

			if(i!=0){
				//Tras hacer pruebas, si la division de los tiempos es mayor que 10, la complejidad es NF
				if((t.tiempoPasado()/tiempo[i-1])>10){
					i = 12;
					NF=true;
				}
			}
			t.reiniciar();
		}
		return NF;
	}

	/*	Vamos a dividir las complejidades en dos partes, pequenas y grandes,
		de tal forma que usaremos unos valores mas altos para complejidades altas
		y unos valores mas pequenos para complejidades mas pequenas.
	*/

	public static void main(String arg[]) {

		tiempo = new double[80];
		double media = 0.0;
		Temporizador t = new Temporizador();

		if(!costeAlto()){

			for(int i=0;i<n1.length;i++){
				t.iniciar();
				Algoritmo.f(n1[i]);
				t.parar();
				tiempo[i] = t.tiempoPasado();
				t.reiniciar();
			}

			//Aqui manejamos las complejidades menores o iguales que N
			media = media(tiempo);

			if(media<180){

				System.out.println("1");

			}else if(media<10000){

				System.out.println("LOGN");

			}else{
				System.out.println("N");
			}
		}

	}
}
