
import java.util.*;

public class Septiembre2019 {

    /*
    Sean s_1,...,S_m una colección de m conjuntos, donde S_i está contenido en {1,---,n}, 1<=i<=m. Se dice que
    C está contenido en {1,...,n} es un impactador de S_1,...,S_m si, y sólo si, C intersección S_i es distinto
    del conjunto vacío para todo S_i, 1<=i<=m.
    a) Implementar un algoritmo de vuelta atrás para determinar si existe un impactador C de tamaño |C|<=k, para
    cierto k y una cierta colección de conjuntos S1,...,Sm.
    b) Construir el árbol de búsqueda para k = 2 y conjuntos {1,2},{1,3},{1,5},{2,3,4},{4,6}.
     */

    public static boolean isImpactor(Set<Integer> C, List<Set<Integer>> S) {
        for (Set<Integer> Si : S) {
            if (C.isEmpty() || Collections.disjoint(C, Si)) {
                return false;
            }
        }
        return true;
    }

    public static boolean backtrack(Set<Integer> C, List<Set<Integer>> S, int k) {
        if (C.size() <= k) {
            if (isImpactor(C, S)) {
                return true;
            }
        }

        if (k == 0) {
            return false;
        }

        for (int i = 1; i <= S.get(0).size(); i++) {
            C.add(i);
            if (backtrack(C, S, k - 1)) {
                return true;
            }
            C.remove(i);
        }

        return false;
    }

    public static void main(String[] args) {
        List<Set<Integer>> S = new ArrayList<>();
        // Agrega tus conjuntos S1, S2, ..., Sm a la lista S.
        Set<Integer> S1 = new HashSet<>();
        S1.add(1);
        S1.add(2);
        S1.add(3);
        S.add(S1);

        Set<Integer> S2 = new HashSet<>();
        S2.add(2);
        S2.add(3);
        S2.add(4);
        S.add(S2);

        int n = S.get(0).size(); // Tamaño del conjunto universal {1, ..., n}

        int k = 1; // Inicializa k
        Set<Integer> C = new HashSet<>();

        while (k <= n) {
            if (backtrack(C, S, k)) {
                System.out.println("Se encontró un impactador de tamaño " + k + ": " + C);
                break;
            }
            k++;
        }

        if (k > n) {
            System.out.println("No se encontró un impactador de tamaño " + k);
        }
    }
}

