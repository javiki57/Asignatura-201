public class Parcial1 {
    public static int encuentraInicioDyV(int[] a){
        return encuentra(a,0,a.length-1);
    }

    public static int encuentra(int[] a, int l, int r){

        if(l == r){
            return l;
        }

        int m = l + (r - l) / 2;

        if(a[m] > a[r]){
            return encuentra(a, m+1, r);

        }else{
            return encuentra(a,l,m);
        }

    }

    public static void main(String[] args) {
        int[] fila = {234,235,267,438,23,63,78}; // Ejemplo de fila
        int lowestPosition = encuentraInicioDyV(fila);

        System.out.println("Posición de la persona con el ticket más bajo: " + lowestPosition);
    }

}
