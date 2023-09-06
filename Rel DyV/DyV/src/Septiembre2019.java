public class Septiembre2019 {

    /*
    Sea A un array [1..n] de enteros ordenados ascendentemente y en el que puede haber elementos
    repetidos. Sea 'e' un valor entero incluido en A. Especificar pre y post condición de una función
    que devuelva el índice de la primera aparición de un valor e. Por ejemplo, si A={2,3,5,5,5,5,7,9}
    y e = 5 el algoritmo debería de devolver 3. Diseña un algoritmo de Divide y Vencerás para este problema.
    Calcular la complejidad del algoritmo en el peor caso usando el Teorema Maestro.
     */

    public static int firstOccurrence(int[] a, int e, int l, int r){

        if(l >= r){
            return -1;
        }

        int m = l + (r - l)/2;

        if(a[m] == e){
            if(a[m-1] != e){
                return m+1;
            }else{
                return firstOccurrence(a,e,l,m);
            }

        }else if(a[m] < e){
            return firstOccurrence(a,e,m+1,r);

        }else{
            return firstOccurrence(a,e,l,m);
        }

    }
    /*
    La ecuación de recurrencia en el peor de los casos sería T(n)=T(n/2)+O(1),
    utilizando el teorema maestro : 1 = 2^0 -> T(n) € O(log n)
     */

    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 5, 7, 9, 9};
        int e = 9;
        int result = firstOccurrence(arr, e, 0, arr.length - 1);

        if (result != -1) {
            System.out.println("El valor " + e + " se encuentra en el índice " + result);
        } else {
            System.out.println("El valor " + e + " no se encuentra en el array.");
        }
    }

}
