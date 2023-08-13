public class Algoritmo {

    public static synchronized double f(double n) {
        for(int i = 0; i < n; i++) {
            f(n-1);
        }

        return n;
    }
}
