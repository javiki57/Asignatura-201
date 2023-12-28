/*
Sustituir este comentario por una explicación de la formula o procedimiento empleado para determinar el valor de una
torreta.
*/

package net.agsh.towerdefense.strats;

import net.agsh.towerdefense.Tower;

import java.util.ArrayList;

public class TowerBuyer {

    public static ArrayList<Integer> buyTowers(ArrayList<Tower> towers, float money) {

        ArrayList<Integer> currentSelection = new ArrayList<>();
        ArrayList<Integer> bestSelection = new ArrayList<>();
        float[] bestValue = new float[]{Float.MIN_VALUE};

        buyTowersBacktracking(towers, money, 0, currentSelection, bestSelection, bestValue);

        return bestSelection;
    }

    private static void buyTowersBacktracking(ArrayList<Tower> towers, float remainingMoney, int currentIndex,
                                              ArrayList<Integer> currentSelection, ArrayList<Integer> bestSelection,
                                              float[] bestValue) {
        if (currentIndex == towers.size()) {
            float totalValue = calculateTotalValue(towers, currentSelection);
            if (totalValue > bestValue[0] && totalCost(towers, currentSelection) <= remainingMoney) {
                bestValue[0] = totalValue;
                bestSelection.clear();
                bestSelection.addAll(currentSelection);
            }
            return;
        }

        // Incluir la torreta actual en la selección
        currentSelection.add(currentIndex);
        buyTowersBacktracking(towers, remainingMoney - towers.get(currentIndex).getCost(),
                currentIndex + 1, currentSelection, bestSelection, bestValue);
        currentSelection.remove(currentSelection.size() - 1);  // Deshacer la inclusión

        // No incluir la torreta actual en la selección
        buyTowersBacktracking(towers, remainingMoney, currentIndex + 1, currentSelection, bestSelection, bestValue);
    }

    private static float calculateTotalValue(ArrayList<Tower> towers, ArrayList<Integer> selection) {
        float totalValue = 0.0f;
        for (int idx : selection) {
            totalValue += getTowerValue(towers.get(idx));
        }
        return totalValue;
    }

    private static float totalCost(ArrayList<Tower> towers, ArrayList<Integer> selection) {
        float totalCost = 0.0f;
        for (int idx : selection) {
            totalCost += towers.get(idx).getCost();
        }
        return totalCost;
    }

    public static float getTowerValue(Tower tower) {
        return tower.getCost() / 2 * tower.getRadius() + tower.getDispersion() / 3 * tower.getRange() + (tower.getDamage() / tower.getRange());
    }

}
