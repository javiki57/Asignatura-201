public class Nov20 {
    /*
    Dado un array ordenado, se desea calcular el múmero de veces que aparece el valor x, siendo x uno de los valores
    presentes en el array.
     */
    static int contador(int[] a, int x, int left, int right){

        if (left > right) {
            return 0; // No se encontró el valor
        }

        int mid = left + (right - left) / 2;

        if (a[mid] == x) {
            // Si encontramos el valor en la posición 'mid', contamos esta ocurrencia y exploramos ambas mitades
            return 1 + contador(a, x, left, mid - 1) + contador(a, x, mid + 1, right);
        } else if (a[mid] < x) {
            // Si el valor en 'mid' es menor que 'x', buscamos en la mitad derecha
            return contador(a, x, mid + 1, right);
        } else {
            // Si el valor en 'mid' es mayor que 'x', buscamos en la mitad izquierda
            return contador(a, x, left, mid - 1);
        }
    }

    public static void main(String[] args) {
        int[] a = {-10, -5,3, 3, 3, 4, 7, 9, 11, 12};

        int res = contador(a, 3, 0, a.length-1);

        System.out.println("Número de veces que aparece: " + res);

    }

}
