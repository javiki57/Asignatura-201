public class Parcial23 {

    public static int posicion(int[] a, int x, int izq, int der){
        if(izq >= der){
            return -1;

        }else{
            int m = izq + (der - izq) / 2;

            if(a[m] == x){
                return m;

            }else if(a[m+1] == x){
                return m+1;

            }else if(a[m-1] == x){
                return m-1;

            }else if(a[m] < x){
                return posicion(a,x,m+1,der);
            }else{
                return posicion(a,x,izq,m-1);
            }
        }
    }

    public static void main(String[] args) {
        int[] a = new int[] {2, 1, 3, 5, 4, 7, 6, 9, 8};
        int[] b = new int[] {2, 1, 3, 5, 4, 5, 6, 8, 9};

        System.out.println("Posición del a: " + posicion(a,9,0,a.length-1));
        System.out.println("Posición del b: " + posicion(b,5,0,a.length-1));
    }
}
