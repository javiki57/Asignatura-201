package analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*

BinaryExponentiation	log(n)	OK	4546
BinarySearch	log(n)	OK	101
Exception in thread "Thread-2" java.lang.OutOfMemoryError: Java heap space
	at algorithms.BipartiteGraphBruteForce.init(BipartiteGraphBruteForce.java:16)
	at analyzer.Analyzer.ejecucion(Analyzer.java:165)
	at analyzer.Analyzer.getMinRatio(Analyzer.java:179)
	at analyzer.Analyzer.findComplexityOf(Analyzer.java:144)
	at analyzer.Analyzer.run(Analyzer.java:91)
	at java.base/java.lang.Thread.run(Thread.java:833)
BipartiteGraphBruteForce	ERROR	FAIL (2^n)	2182
BruteForceKnapsack: Timeout! (2^n)
BubleSort	n^2	OK	719
CatalanNumber	n^2	OK	154
Constant	1	OK	6026
Cubic: Timeout! (n^3)
EuclideanDistance	1	OK	28
Exponential: Timeout! (2^n)
FastExponentiation	1	FAIL (log(n))	34
FibonacciIterative	n^2	FAIL (n)	1272
FibonacciRecursive: Timeout! (2^n)
Floyd	n^2	FAIL (n^3)	5660
HeapSort: Timeout! (n*log(n))
Linear: Timeout! (n)
Linearithmic	n^3	FAIL (n*log(n))	2455
LinearSearch	1	FAIL (n)	5630
LinearSearchConstant	1	OK	1008
Logarithmic	log(n)	OK	548
Exception in thread "Thread-20" java.lang.OutOfMemoryError: Java heap space
	at algorithms.MatrixMultiplication.reset(MatrixMultiplication.java:23)
	at algorithms.MatrixMultiplication.init(MatrixMultiplication.java:18)
	at analyzer.Analyzer.ejecucion(Analyzer.java:165)
	at analyzer.Analyzer.getMinRatio(Analyzer.java:179)
	at analyzer.Analyzer.findComplexityOf(Analyzer.java:144)
	at analyzer.Analyzer.run(Analyzer.java:91)
	at java.base/java.lang.Thread.run(Thread.java:833)
MatrixMultiplication	ERROR	FAIL (n^3)	6287
MatrixMultiplicationConstant	1	OK	100
MergeSort	n	FAIL (n*log(n))	2567
MinMax	n	OK	3770
Quadratic: Timeout! (n^2)
SelectionSort	n^2	OK	833
Skyline	n^3	FAIL (n*log(n))	449
Warshall	n^3	OK	412
12
 */

public class Analyzer implements Runnable {
    Algorithm algorithm;
    long maxExecutionTime;
    String complexity = null;

    // N range
    public static long FROM_2N      = 5,    TO_2N       = 10;
    public static long FROM_N3      = 10,    TO_N3       = 60;
    public static long FROM_N2      = 20,   TO_N2       = 250;
    public static long FROM_NLOGN   = 20,   TO_NLOGN    = 800;
    public static long FROM_N       = 20,   TO_N        = 120000;
    public static long FROM_LOGN    = 20,   TO_LOGN     = 500000;

    // Thresholds

    public static long THRESHOLD_2N = 200;
    public static long THRESHOLD_N3 = 10;
    public static long THRESHOLD_N2 = 10;
    public static long THRESHOLD_NLOGN = 45;
    public static long THRESHOLD_N = 200;
    public static long THRESHOLD_LOGN = 1;

    // Reliability scales with the number of iterations
    public static int ITERATIONS = 8;

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


    public static long ejecucion(Algorithm a, long n) {

        Chronometer chrono = new Chronometer();
        //chrono.pause();
        a.init(n);
        //chrono.resume();
        a.run();
        //chrono.stop();

        return chrono.getElapsedTime();
    }

    public static double getMinRatio(Algorithm a, final long n1, final long n2, long maxExecutionTime) {
        long min1 = Long.MAX_VALUE;
        long min2 = Long.MAX_VALUE;

        for (int i = 0; i < ITERATIONS; i++) {
            long ejecucion1 = ejecucion(a, n1); // Mide el tiempo de ejecución para n1.
            long ejecucion2 = ejecucion(a, n2); // Mide el tiempo de ejecución para n2.

            min1 = Math.min(min1, ejecucion1) <= 0 ? min1 : Math.min(min1, ejecucion1);  // Mide el tiempo de ejecución para n1.
            min2 = Math.min(min2, ejecucion2) <= 0 ? min2 : Math.min(min2, ejecucion2);  // Mide el tiempo de ejecución para n2.

        }

        double ratio = (double) min2 / min1; // Calcula el ratio entre los tiempos de ejecución.

        // Prevenir infinito
        ratio = (ratio > Integer.MAX_VALUE) ? getMinRatio(a, n1, n2, maxExecutionTime) : ratio;


        return ratio;
    }
}
