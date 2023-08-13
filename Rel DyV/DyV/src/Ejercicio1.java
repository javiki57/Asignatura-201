public class Ejercicio1 {

    static int buscar(int[] a){
        int left = 0;
        int right = a.length - 1;

        while(left <= right){
            int m = left + (right - left) / 2;

            if(a[m] == m+1){
                return m+1;
            }else if(a[m] < m+1){
                left = m + 1;
            }else{
                right = m - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] a = {-10, -5, 3, 4, 7, 9, 11, 12};

        int res = buscar(a);
        if (res != -1) {

            System.out.println("Número i tal que V[i] = i: " + res);
        } else {
            System.out.println("No se encontró ningún número i tal que V[i] = i.");
        }
    }
}
