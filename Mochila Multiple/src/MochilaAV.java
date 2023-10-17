
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * 
 * @author ***** Indicar aqui el autor de la practica *******
 *
 */
public class MochilaAV extends Mochila {

	public SolucionMochila resolver(ProblemaMochila pm) {
		SolucionMochila sm=null;
		pm.items.sort(new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				double densidad1 = ((double)o1.valor) / ((double)o1.peso);
				double densidad2 = ((double)o2.valor) / ((double)o2.peso);

				if(densidad1 > densidad2){
					return -1;

				}else if(densidad1 < densidad2){
					return 1;

				}else{
					return 0;
				}
			}
		});

		Iterator<Item> it = pm.items.iterator();
		int p = 0, v = 0;
		int[] sol = new int[pm.items.size()];

		while(it.hasNext()){
			Item item = it.next();

			while((sol[item.index] < item.unidades) && (pm.getPesoMaximo() - p - item.peso >= 0)){
				sol[item.index]++;
				p += item.peso;
				v+= item.valor;
			}
		}

		return (new SolucionMochila(sol,p,v));
	}
}
