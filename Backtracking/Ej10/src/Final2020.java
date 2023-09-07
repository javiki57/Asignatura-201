import java.util.ArrayList;

public class Final2020 {

    /*
    Encontrar los k enteros positivos (que pueden estar repetidos o no) {x_1,x_2,...,x_k} tales que
    dado otro positivo N minimicen la suma desde i=1 hasta k de (x_i)^2 y cumplan que la suma desde
    i = 1 hasta k de los x_i = N. Se pide resolver el problema implementando un algoritmo de Vuelta Atrás.
    b) Construir el árbol de expansión para un N=6, k=3.
     */


    public static ArrayList<Integer> backtrack(int k, int N, int i, int suma, int[] bestSum, ArrayList<Integer> sol){
        if(esSolucion(i,k,suma,N,sol)){
            int sumacuadrados = 0;

            for(int sum : sol){
                sumacuadrados += sum * sum;
            }

            if(sumacuadrados < bestSum[0]){
                bestSum[0] = sumacuadrados;
                return new ArrayList<>(sol);
            }

        }

        for (int candidato = 1; candidato <= N; candidato++) {
            if (suma + candidato <= N) {
                sol.add(candidato);
                // Asignar el resultado de la llamada recursiva a bestSolution
                ArrayList<Integer> bestSolution = backtrack(k, N, i + 1, suma + candidato, bestSum, sol);
                sol.remove(sol.size() - 1);
                // Devolver bestSolution
                return bestSolution;
            }
        }

        return sol;
    }

    private static boolean esSolucion(int i, int k, int suma, int N, ArrayList<Integer> sol){
        return i == k && suma == N;
    }

    public static void main(String[] args) {
        int N = 10; // Cambia este valor según tus necesidades
        int k = 3;  // Cambia este valor según tus necesidades
        int max = N; // Cambia este valor según tus necesidades
        int[] bestSum = { Integer.MAX_VALUE };
        ArrayList<Integer> bestSolution = new ArrayList<>();

        // Pasar el parámetro max y el arreglo bestSum a la función backtrack
        bestSolution = backtrack(k, max, 0, 0, bestSum, bestSolution);

        if (bestSolution.isEmpty()) {
            System.out.println("No se encontró solución.");
        } else {
            System.out.println("Mejor solución: " + bestSolution);
            int sumOfSquares = 0;
            for (int num : bestSolution) {
                sumOfSquares += num * num;
            }
            System.out.println("Suma de cuadrados mínima: " + sumOfSquares);
        }
    }

}
