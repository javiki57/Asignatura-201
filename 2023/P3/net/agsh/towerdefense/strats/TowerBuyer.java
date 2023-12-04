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

        int n = towers.size();
        float[][] T = new float[n + 1][(int) (money + 1)];

        for (int i = 1; i <= n; i++) {
            Tower currentTower = towers.get(i - 1);
            float towerCost = currentTower.getCost();
            float towerValue = getTowerValue(currentTower);

            for (int j = 0; j <= money; j++) {
                T[i][j] = T[i - 1][j];

                if (j >= towerCost) {
                    // Si se puede comprar, se considera la compra de la torreta
                    T[i][j] = Math.max(T[i][j], T[i - 1][(int) (j - towerCost)] + towerValue);
                }
            }
        }

        // Reconstrucción de las torres seleccionadas en base a T
        ArrayList<Integer> selected = new ArrayList<>();
        int i = n;
        int j = (int) money;
        while (i > 0 && j > 0) {
            if (T[i][j] != T[i - 1][j]) {
                // Torreta seleccionada
                selected.add(i - 1);
                j -= towers.get(i - 1).getCost();
            }
            i--;
        }

        // hacer un reverse para tener el orden adecuado
        ArrayList<Integer> reversedSelected = new ArrayList<>();
        for (int k = selected.size() - 1; k >= 0; k--) {
            reversedSelected.add(selected.get(k));
        }

        return reversedSelected;
    }

    public static float getTowerValue(Tower tower) {
        return   tower.getDispersion() / 3 * tower.getRange() + (2 * tower.getDamage() / tower.getRange()) ;
    }
}
