import java.util.Arrays;

public class Septiembre2021 {

    /*
    ESPECIFICACIÓN:
    Sea A un array [1...n] de enteros que contiene números positivos y negativos.
    Especificar una función lógica que indique si hay alguna posición del array cuyo valor tenga
    diferente signo al de las posiciones "anterior" y "siguiente".

    DyV:
    Sea A un array [1...n] de enteros. Diseñar un algoritmo DyV para reorganizarlo de manera que
    primero aparezcan los números negativos y luego los positivos, en ambos casos respetando el
    orden original dentro de cada grupo. Por ejemplo, si A={9,-3,5,-2,-8,-6,1,3}, tras aplicar
    el algoritmo tendríamos A={-3,-2,-8,-6,9,5,1,3}.

    Calcula la complejidad con el Teorema Maestro.
     */


    public static void reorganizarArray(int[] a){
        DyV(a,0,a.length-1);
    }

    public static void DyV(int[] a, int l, int r){
        if(l < r){
            int m = (l+r)/2;
            DyV(a,l,m);
            DyV(a,m+1,r);
            combinar(a,l,m,r);
        }
    }

    public static void combinar(int[] a, int l, int m, int r){
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] negativos = new int[n1];
        int[] positivos = new int[n2];

        // Copia los elementos correspondientes a los subarreglos negativos y positivos
        for (int i = 0; i < n1; i++) {
            negativos[i] = a[l + i];
        }
        for (int j = 0; j < n2; j++) {
            positivos[j] = a[m + 1 + j];
        }

        int i = 0, j = 0, k = l;

        while (i < n1 && negativos[i] < 0) {
            a[k++] = negativos[i++];
        }

        while (j < n2 && positivos[j] < 0) {
            a[k++] = positivos[j++];
        }

        // Copia los elementos restantes de los subarreglos
        while (i < n1) {
            a[k++] = negativos[i++];
        }
        while (j < n2) {
            a[k++] = positivos[j++];
        }
    }


    public static void main(String[] args) {
        int[] A = {9, -3, 5, -2, -8, -6, 1, 3};
        reorganizarArray(A);
        System.out.println(Arrays.toString(A));
    }

}
