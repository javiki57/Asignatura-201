package analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Analyzer implements Runnable {
    Algorithm algorithm;
    long maxExecutionTime;
    String complexity = null;

    // N range
    public static long FROM_NF      = 2,    TO_NF       = 9;
    public static long FROM_2N      = 2,    TO_2N       = 20;
    public static long FROM_N3      = 2,    TO_N3       = 42;
    public static long FROM_N2      = 20,   TO_N2       = 250;
    public static long FROM_NLOGN   = 20,   TO_NLOGN    = 800;
    public static long FROM_N       = 20,   TO_N        = 120000;
    public static long FROM_LOGN    = 20,   TO_LOGN     = 500000;

    // Thresholds
    //public static long THRESHOLD_NF = 1000;
    public static long THRESHOLD_2N = 300;
    public static long THRESHOLD_N3 = 300;
    public static long THRESHOLD_N2 = 50;
    public static long THRESHOLD_NLOGN = 45;
    public static long THRESHOLD_N = 300;
    public static long THRESHOLD_LOGN = 1;

    // Reliability scales with the number of iterations
    public static int ITERATIONS = 10;

    public Analyzer(Algorithm algorithm, long maxExecutionTime) {
        this.algorithm = algorithm;
        this.maxExecutionTime = maxExecutionTime;
    }

    public String getComplexity() {
        return complexity;
    }

    @Override
    public void run() {
        complexity = findComplexityOf(algorithm, maxExecutionTime);
    }

    static String findComplexityOf(Algorithm algorithm, long maxExecutionTime) {
        // Modify the content of this method in order to find the complexity of the given algorithm.
        // You can delete any of the following instructions if you don't need them. You can also
        // add new instructions or new methods, but you cannot modify the signature of this method
        // nor the existing methods.
        /*
        Chronometer chrono = new Chronometer();
        chrono.pause();
        int n = 10;
        algorithm.init(n);
        chrono.resume();
        algorithm.run();
        long time = chrono.getElapsedTime();
        String complexity = "1";
        if(time > 0.1) {
            complexity = "log(n)";
        }
        return complexity;

         */

        double ratio = 0.0;
        String complexity;
/*
        complexity = "NF";
        ratio = getMinRatio(algorithm, FROM_NF, TO_NF);
        if (ratio > THRESHOLD_NF) {
            return complexity;
        }

 */
        complexity = "2^n";
        ratio = getMinRatio(algorithm, FROM_2N, TO_2N, maxExecutionTime);
        if (ratio > THRESHOLD_2N) {
            return complexity;
        }

        complexity = "n^3";
        ratio = getMinRatio(algorithm, FROM_N3, TO_N3, maxExecutionTime);
        if (ratio > THRESHOLD_N3) {
            return complexity;
        }

        complexity = "n^2";
        ratio = getMinRatio(algorithm, FROM_N2, TO_N2, maxExecutionTime);
        if (ratio > THRESHOLD_N2) {
            return complexity;
        }

        complexity = "n*log(n)";
        ratio = getMinRatio(algorithm, FROM_NLOGN, TO_NLOGN, maxExecutionTime);
        if (ratio > THRESHOLD_NLOGN) {
            return complexity;
        }

        complexity = "n";
        ratio = getMinRatio(algorithm, FROM_N, TO_N, maxExecutionTime);
        if (ratio > THRESHOLD_N) {
            return complexity;
        }

        complexity = "log(n)";
        ratio = getMinRatio(algorithm, FROM_LOGN, TO_LOGN, maxExecutionTime);
        if (ratio > THRESHOLD_LOGN) {
            return complexity;
        }
        else {
            complexity = "1";
            return complexity;
        }
    }


    public static long ejecucion(Algorithm a, long n, long maxExecutionTime) {

        Chronometer chrono = new Chronometer();
        //chrono.pause();
        a.init(n);
        //chrono.resume();
        a.run();
        chrono.stop();
        if (chrono.getElapsedTime() > maxExecutionTime) {

            return maxExecutionTime;
        }

        return chrono.getElapsedTime();
    }

    public static double getMinRatio(Algorithm a, final long n1, final long n2, long maxExecutionTime) {
        long min1 = Long.MAX_VALUE;
        long min2 = Long.MAX_VALUE;

        for (int i = 0; i < ITERATIONS; i++) {
            long ejecucion1 = ejecucion(a, n1, maxExecutionTime); // Mide el tiempo de ejecución para n1.
            long ejecucion2 = ejecucion(a, n2, maxExecutionTime); // Mide el tiempo de ejecución para n2.

            min1 = Math.min(min1, ejecucion1) <= 0 ? min1 : Math.min(min1, ejecucion1);  // Mide el tiempo de ejecución para n1.
            min2 = Math.min(min2, ejecucion2) <= 0 ? min2 : Math.min(min2, ejecucion2);  // Mide el tiempo de ejecución para n2.


            if (ejecucion1 > maxExecutionTime || ejecucion2 > maxExecutionTime) {
                // Detener la ejecución o tomar medidas necesarias
                break;
            }
        }

        double ratio = (double) min2 / min1; // Calcula el ratio entre los tiempos de ejecución.

        // Prevenir infinito
        ratio = (ratio > Integer.MAX_VALUE) ? getMinRatio(a, n1, n2, maxExecutionTime) : ratio;


        return ratio;
    }
}
