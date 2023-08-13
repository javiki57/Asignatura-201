public class Final20 {
    /*
    Sea un array A ordenado de tamaño n, que contiene todos los elementos 1,2,... n-1. Se desea encontrar el
    único elemento que está repetido.
    a) Diseña un algoritmo de Divide y Vencerás de complejidad O(log n)
    b) Expresa la complejidad mediante una ecuación de recurrencia y determina su orden de complejidad mediante el
    Teorema Maestro.
     */

    static int findRepeated(int[] a, int l, int r){
        if (l >= r) {
            return -1; // No se encontró el número repetido
        }

        int m = l + (r - l) / 2;

        if (a[m] == a[m-1] || a[m] ==a[m+1]) {
            return a[m]; // Se encontró el índice del número repetido

        } else if (a[m] < m + a[0]) {
            // El número repetido está en la mitad izquierda
            return findRepeated(a, l, m - 1);
        } else {
            // El número repetido está en la mitad derecha
            return findRepeated(a, m + 1, r);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 6, 7};

        int repeated = findRepeated(arr, 0, arr.length - 1);
        System.out.println("El número repetido es: " + repeated); // Debería mostrar 6
    }
}
