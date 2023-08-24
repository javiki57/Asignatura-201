import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final boolean[][] t = {{false,true,false,true},
                                    {false, true, true, false},
                                    {true, true, false, true}};
    private static final int n = t.length;
    private static final int m = t[0].length;

    private static void asigTrabajos(int[] sol, List<int[]> todas, int i){
        if(i == n){
            todas.add(sol.clone());

        }else{
            int j=0;
            while(j < m){

                if(esFactible(sol, i, j)){
                    sol[i] = j;
                    asigTrabajos(sol, todas,i+1);
                }
                j++;
            }
        }
    }

    private static boolean esFactible(int[] sol, int i, int j){
        return t[i][j] && !contiene(sol,i,j);
    }

    private static boolean contiene(int[] sol, int i, int j){
        int k = 0;

        while(k < i && sol[k] != j){
            k++;
        }
        return k < i;
    }

    private static void verSol(int[] s){
        System.out.println("-------");
        for(int i=0;i<n;i++){
            System.out.println("Operario "+ (i+1)+ ":" + " Trabajo "+ (s[i]+1));
        }
    }

    public static void main(String[] args) {
        int[] sol = new int[n];
        List<int[]> todas = new ArrayList<>();
        int i=0;
        asigTrabajos(sol,todas,i);
        System.out.println("Soluciones encontradas: ");

        for(int[] s:todas){
            verSol(s);
        }
    }
}