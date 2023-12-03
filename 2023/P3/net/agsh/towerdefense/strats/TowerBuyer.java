/*
Sustituir este comentario por una explicación de la formula o procedimiento empleado para determinar el valor de una
torreta.
*/

package net.agsh.towerdefense.strats;

import net.agsh.towerdefense.Tower;

import java.util.ArrayList;

public class TowerBuyer {


    public static ArrayList<Integer> buyTowers(ArrayList<Tower> towers, float money) {
        // This is just a (bad) example. Replace ALL of this with your own code.
        // The ArrayList<Integer> returned is a list of the indices of the towers you want to buy.
        // For example, if you want to buy the first and third towers, return [0, 2].
        // The selected towers must be affordable, and the total cost must be less than or equal to money.
        // The indices should be given in the order that the towers are given in the original ArrayList<Tower> towers.

        // Create an ArrayList<Integer> to store the indices of the towers you want to buy.


//
//        // Loop through the towers.
//        for (int i = 0; i < towers.size(); i++) {
//            // If the tower is affordable, buy it.
//            if(money >= towers.get(i).getCost()) {
//                // Subtract the cost of the tower from money.
//                money -= towers.get(i).getCost();
//                // Add the index of the tower to selected.
//                selected.add(i);
//            }
//        }

        ArrayList<Integer> selected = new ArrayList<>();
        float [][] T = new float[towers.size()+1][(int) money + 1];
        int n = towers.size();

        for(int i=0; i<=n; i++){

            for(int j=0; j<= (int) money; j++){
                T[i][j] = -1;
            }
        }

        buyTowersDinamic(towers, (int) money, n, T);

        selected.add((int) T[n][(int) money]);
        return selected;
    }

    private static void buyTowersDinamic(ArrayList<Tower> towers, int c, int n, float[][] T){

        if(c == 0){
            T[n][c] = 0;

        }else if (n == 1){

            if(c < towers.get(0).getCost()){
                T[0][c] = 0;

            }else{
                T[0][c] = getTowerValue(towers.get(0));
            }

        }else if(n > 0){

            if(T[n-1][c] == -1){
                buyTowersDinamic(towers, c,n - 1, T);
            }

            if (c - towers.get(n - 1).getCost() >= 0 && T[n][(int) (c - towers.get(n - 1).getCost())] == -1){
                buyTowersDinamic(towers, (int) (c - towers.get(n).getCost()), n-1, T);
            }
            T[n][c] = Math.max(T[n-1][c],T[n-1][c] + getTowerValue(towers.get(n)));
        }

    }

    public static float getTowerValue(Tower tower){

        return tower.getRange() + 2 * tower.getDispersion();
    }
}
