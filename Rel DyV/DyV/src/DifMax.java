public class DifMax {

    /*
    Problema: Máximo Subvector con Diferencia Máxima

    Dado un vector de números enteros, tu tarea es encontrar dos elementos adyacentes en el vector que tengan la diferencia máxima entre ellos.
    Es decir, debes encontrar el par de elementos A[i] y A[i+1] tal que A[i+1] - A[i] sea lo más grande posible.
    Debes devolver la posición i en la que se encuentra el primer elemento del par.

    Implementa una función llamada maxDifferenceIndex que tome como entrada un vector de números enteros A y devuelva el índice i que corresponde
    al primer elemento del par con la diferencia máxima.
     */

    public static int maxDifferenceIndex(int[] A) {
        // Llamamos a la función recursiva con los límites del vector
        return maxDifferenceIndex(A, 0, A.length - 1);
    }

    // Función recursiva que devuelve el índice del primer elemento del par con la diferencia máxima en un subvector A[izq..der]
    public static int maxDifferenceIndex(int[] A, int izq, int der) {
        // Caso base: si el subvector tiene un solo elemento, no hay ningún par posible y devolvemos -1
        if (izq == der) {
            return -1;
        }
        // Caso recursivo: dividimos el subvector en dos mitades
        int med = (izq + der) / 2;

        // Resolvemos el problema para cada mitad, obteniendo dos posibles índices i1 e i2
        int i1 = maxDifferenceIndex(A, izq, med);
        int i2 = maxDifferenceIndex(A, med + 1, der);

        // Calculamos la diferencia entre el elemento medio y el siguiente, obteniendo un tercer posible índice i3
        int i3 = med;
        int dif3 = A[med + 1] - A[med];

        // Comparamos las diferencias de los tres posibles índices y devolvemos el que tenga la mayor
        if (i1 == -1 || dif3 > A[i1 + 1] - A[i1]) {
            // Si i1 no existe o dif3 es mayor que la diferencia de i1, comparamos dif3 con la diferencia de i2

            if (i2 == -1 || dif3 > A[i2 + 1] - A[i2]) {
                // Si i2 no existe o dif3 es mayor que la diferencia de i2, devolvemos i3
                return i3;

            } else {
                // Si dif3 es menor o igual que la diferencia de i2, devolvemos i2
                return i2;
            }
        } else {

            // Si dif3 es menor o igual que la diferencia de i1, comparamos la diferencia de i1 con la diferencia de i2
            if (i2 == -1 || A[i1 + 1] - A[i1] > A[i2 + 1] - A[i2]) {
                // Si i2 no existe o la diferencia de i1 es mayor que la diferencia de i2, devolvemos i1
                return i1;
            } else {
                // Si la diferencia de i1 es menor o igual que la diferencia de i2, devolvemos i2
                return i2;
            }
        }
    }

    public static void main(String[] args) {
        int[] A1 = {4, 2, 9, 7, 5, 8, 11};
        int result1 = maxDifferenceIndex(A1);
        System.out.println("Max difference index for A1: " + result1); // Debería mostrar 2

        int[] A2 = {1, 5, 3, 9, 12};
        int result2 = maxDifferenceIndex(A2);
        System.out.println("Max difference index for A2: " + result2); // Debería mostrar 1
    }

}
