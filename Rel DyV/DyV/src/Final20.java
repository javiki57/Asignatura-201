public class Final20 {
    /*
    Sea un array A ordenado de tamaño n, que contiene todos los elementos 1,2,... n-1. Se desea encontrar el
    único elemento que está repetido.
    a) Diseña un algoritmo de Divide y Vencerás de complejidad O(log n)
    b) Expresa la complejidad mediante una ecuación de recurrencia y determina su orden de complejidad mediante el
    Teorema Maestro.
     */

    static int findRepeated(int[] a, int l, int r){
        // Caso base: si el array tiene solo dos elementos, se devuelve el mayor como el repetido
        if (l >= r) {
            return a[r];
        }

        // Se calcula el índice del elemento medio del array
        int m = (l + r) / 2;

        // Se compara el elemento medio con su índice
        if (a[m] == m + 1) {
            // Si son iguales, el elemento repetido está en la segunda mitad del array
            return findRepeated(a, m + 1, r);
        } else {
            // Si son distintos, el elemento repetido está en la primera mitad del array
            return findRepeated(a, l, m);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 5, 6, 7};

        int repeated = findRepeated(arr, 0, arr.length - 1);
        System.out.println("El número repetido es: " + repeated); // Debería mostrar 6
    }
}
