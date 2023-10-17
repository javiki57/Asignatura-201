public class Septiembre2021 {

    /*
    Sea M una matriz de números naturales de tamaño m x n. Deseamos determinar si existe
    o no un camino desde la posición (1,1) a la posición (m,n) teniendo en cuenta en todo
    momento que si estamos en la posición (i,j) sólo podemos desplazarnos a las posiciones
    (i+M_ij, j) o (i, j+M_ij).
    Diseñar un algoritmo de vuelta atrás para el problema descrito.
     */

    public static boolean canReachDestination(int[][] M, int m, int n) {

        if(m > M.length || n > M[0].length || m<0 || n<0) throw new RuntimeException("Valores de m y n incorrectos.");

        boolean[][] visited = new boolean[m][n];

        // Inicialmente, marcamos la posición (1,1) como visitada
        visited[0][0] = true;

        return backtrack(0, 0, m, n, M, visited);
    }

    private static boolean backtrack(int i, int j, int m, int n, int[][] M, boolean[][] visited) {
        if (i == m - 1 && j == n - 1) {
            // Hemos llegado a la posición de destino (m, n)
            return true;
        }

        int move = M[i][j];

        //nos movemos hacia arriba.
        if(j + move < n && !visited[i][j+move]){
            visited[i][j+move] = true;

            if(backtrack(i, j+move, m, n, M, visited)){
                return true;
            }

            //Retrocede si no es el camino correcto.
            visited[i][j+move] = false;
        }

        //nos movemos hacia abajo
        if(i + move < m && !visited[i+move][j]){
            visited[i+move][j] = true;

            if(backtrack(i+move, j, m, n , M, visited)){
                return true;
            }
            visited[i+move][j] = false;
        }

        //Si no podemos avanzar hacia la derecha o hacia abajo, estamos en un bloqueo.
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 2, 0, 0},
                {1, 0, 1, 0, 1},
                {1, 2, 0, 1, 1},
                {2, 1, 0, 2, 2},
                {1, 1, 2, 2, 0}
        };

        int m = 5;
        int n = 5;

        boolean result = canReachDestination(matrix, n, m);

        if (result) {
            System.out.println("Sí, existe un camino desde (1,1) a ("+m+","+n+").");
        } else {
            System.out.println("No existe un camino desde (1,1) a ("+m+","+n+").");
        }
    }

}
