
import java.util.ArrayList;
/**
 *
 * @author ***** Jose A. Onieva *******
 *
 */
public class MochilaPD extends Mochila {

	public SolucionMochila resolver(ProblemaMochila pm) {
		SolucionMochila sm=null;
		SolucionMochila[][] F = new SolucionMochila[pm.getItems().size()+1][pm.getPesoMaximo()+1];

		for(int i=0;i<=pm.size();i++){
			for(int j=0;j<=pm.getPesoMaximo();j++){

				if(i==0 || j==0){
					F[i][j] = new SolucionMochila(new int[pm.getItems().size()], 0, 0);

				}else if(j < pm.getPeso(i-1)){
					SolucionMochila s = F[i-1][j];
					F[i][j] = new SolucionMochila(
							(ArrayList<Integer>) s.getSolucion().clone(),
							s.getSumaPesos(),
							s.getSumaValores());

				}else{
					SolucionMochila s = F[i-1][j];
					int max = 0;

					for(int k=1;k<=pm.getUnidad(i-1);k++){

						if(j >= k * pm.getPeso(i-1)){
							SolucionMochila aux = F[i-1][j-k*pm.getPeso(i-1)];

							if(s.getSumaValores() < aux.getSumaValores() + k*pm.getValor(i-1)){
								s = aux;
								max = k;
							}
						}
					}

					ArrayList<Integer> sol = (ArrayList<Integer>) s.getSolucion().clone();
					sol.set(i-1,max);
					F[i][j] = new SolucionMochila(
							sol,
							s.getSumaPesos() + max * pm.getPeso(i-1),
							s.getSumaValores() + max * pm.getValor(i-1));

				}
			}
		}

		sm = F[pm.getItems().size()][pm.getPesoMaximo()];
		return sm;
	}
}
