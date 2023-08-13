public class pico {
    /*
    Sea un array de un número de enteros estrictamente creciente seguido por una secuencia estrictamente decreciente,
    construye una función (utilizando la técnica de divide y vencerás) int pico(int[]a) que devuelva el valor del array
    donde pasa de ser creciente a decreciente.
     */

    static int fpico(int[] a){
        int l=0;
        int r = a.length-1;
        return dyv(a, l, r);
    }

    static int dyv(int[] a, int l, int r){

        if(l == r){
            return a[l];
        }

        int m = l + (r - l) /2;

        if(a[m] < a[m+1]){
            return dyv(a, m+1,r);

        }else{
            return dyv(a,l,m);
        }
    }

    public static void main(String[] args) {
        int[] fila = {1, 3, 5, 7, 10, 8, 6, 4}; // Ejemplo de fila
        int lowestPosition = fpico(fila);

        System.out.println("Posición pico: " + lowestPosition);
    }

}
