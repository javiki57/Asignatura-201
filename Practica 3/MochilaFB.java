
/**
 * 
 * @author ***** Jose A. Onieva ******* Asumimos que: a) Todos los items tienen
 *         un valor >=1 b) W>0
 */

public class MochilaFB extends Mochila {

	
	public SolucionMochila resolver(ProblemaMochila pm) {
		SolucionMochila sm=null;
		int[] peso    	  = pm.getPesos();
		int[] valor       = pm.getValores();
		int[] items	  	  = pm.getUnidades();
		int permutaciones = 1;

		for(int i=0; i<pm.size();i++){
			permutaciones *= pm.getUnidad(i)+1;
		}

		for(int i=0; i<permutaciones;i++){
			int actual = i, pActual = 0, vActual = 0;
			int[] a = new int[items.length];

			for(int j=0; j<items.length;j++){
				a[j] = actual % (items[j] + 1);
				pActual += peso[j] * a[j];
				vActual += valor[j] * a[j];
				actual /= items[j]+1;

			}

			if(pm.getPesoMaximo() >= pActual){
				if(sm == null || sm.getSumaValores() < vActual){
					sm = new SolucionMochila(a,pActual,vActual);
				}
			}
		}


		return sm;	}

}
