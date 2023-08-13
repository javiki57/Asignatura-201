public class Final20 {
    /*
    Sea un array A ordenado de tamaño n, que contiene todos los elementos 1,2,... n-1. Se desea encontrar el
    único elemento que está repetido.
    Diseña un algoritmo de Divide y Vencerás de complejidad O(log n)
     */

    static int findRepeated(int[] a, int l, int r){
        if(l >= r){
            return Integer.MIN_VALUE;
        }

        int m = l + (r - l) / 2;
        if(a[m] == a[m+1] || a[m-1] == a[m]){
            return a[m];

        }else{
            int izq = findRepeated(a,l,m);
            int der = findRepeated(a,m+1,r);
            return Math.max(izq,der);
        }

    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 3, 4, 5, 6, 7};

        int repeated = findRepeated(arr, 1, arr.length - 1);
        System.out.println("El número repetido es: " + repeated); // Debería mostrar 4
    }
}
