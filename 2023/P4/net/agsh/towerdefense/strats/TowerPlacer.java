/*
Sustituir este comentario por una explicación de la formula o procedimiento empleado para determinar el valor de una
celda (MapNode).
*/

package net.agsh.towerdefense.strats;

import net.agsh.towerdefense.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TowerPlacer {

    public static float getNodeValue(MapNode node, Map map) {

        ArrayList<MapNode> walkableNodes = map.getWalkableNodes();

        // Calcular la distancia media desde el nodo actual a los caminos
        double totalDistance = 0.0;
        for (MapNode walkableNode : walkableNodes) {
            totalDistance += node.getPosition().distance(walkableNode.getPosition());
        }

        // Calcular la distancia media
        double averageDistance = totalDistance / walkableNodes.size();

        // Obtener el radio máximo de los enemigos (por ejemplo, 10 unidades)
        float maxEnemyRadius = 10.0f;

        // Calcular el valor de la celda del terreno basado en la inversa de la distancia media a los caminos
        // Cuanto menor sea la distancia media, mayor será el valor
        float calculatedValue = 1.0f / (float) (averageDistance + maxEnemyRadius);

        // Aumentar el valor para las celdas cercanas a los caminos
        float proximityBonus = 2.0f; // Ajusta según sea necesario
        calculatedValue += proximityBonus / (float) node.getPosition().distance(getClosestWalkableNode(node, walkableNodes).getPosition());

        return calculatedValue;
    }

    // Método auxiliar para obtener el nodo caminable más cercano a un nodo dado
    private static MapNode getClosestWalkableNode(MapNode node, ArrayList<MapNode> walkableNodes) {
        return walkableNodes.stream()
                .min(Comparator.comparingDouble(walkableNode -> node.getPosition().distance(walkableNode.getPosition())))
                .orElse(null);
    }

    public static boolean collide(Point2D entity1Position, float entity1Radius,
                                  Point2D entity2Position, float entity2Radius) {

        // Calcula la distancia entre los centros de las entidades
        double distance = entity1Position.distance(entity2Position);

        // Compara la distancia con la suma de los radios para verificar la superposición
        return distance <= (entity1Radius + entity2Radius);

    }

    public static ArrayList<Tower> placeTowers(ArrayList<Tower> towers, Map map) {

        // Get map size.
//        float mapWidth = map.getSize().x;
//        float mapHeight = map.getSize().y;


        // Get parameters from the game.
//        float maxEnemyRadius = Game.getInstance().getParam(Config.Parameter.ENEMY_RADIUS_MAX);
//        float maxRange = Game.getInstance().getParam(Config.Parameter.TOWER_RANGE_MAX);

        // Get nodes the enemies can walk on.
        ArrayList<MapNode> walkableNodes = map.getWalkableNodes();

        // Get obstacles.
        ArrayList<Obstacle> obstacles = map.getObstacles();

//        // Get the grid of map nodes.
//        MapNode[][] grid = map.getNodes();
//
//        // Get the distance between two nodes.
//        MapNode n1 = grid[0][0];
//        MapNode n2 = grid[0][1];
//        float distance = n1.getPosition().distance(n2.getPosition());


        // Each node has up to Config.Parameter.MAP_NODE_VALUES values. Default is 5.
        // You can *optionally* use these values to store information about the nodes.
        // Set the second value of node n1.
//        n1.setValue(2, getNodeValue(n1, map));
//
//        // Get the second value of node n1.
//        float value = n1.getValue(2);

        // Se crea la lista donde se almacenan las soluciones
        ArrayList<Tower> placedTowers = new ArrayList<>();

        // Obtener los nodos del mapa ordenados de mayor a menor valor, según lo definido en getNodeValue
        List<MapNode> sortedNodes = new ArrayList<>(map.getNodesList().stream()
                .filter(node -> !walkableNodes.contains(node))  // Excluir nodos correspondientes al camino
                .sorted(Comparator.comparingDouble(node -> getNodeValue(node, map)))
                .toList());

        //mientras que no sea solución o candidatos != vacío
        while(!towers.isEmpty() && !sortedNodes.isEmpty()){

            // Extraer el primer candidato (nodo con mayor valor)
            MapNode candidateNode = sortedNodes.remove(0);
            Point2D candidatePosition = candidateNode.getPosition();
            Tower tower = towers.remove(0);

            if (factible(tower, candidatePosition, placedTowers, obstacles, map)) {
                // Agregar la torre a la lista de torres colocadas
                tower.setPosition(candidatePosition);
                placedTowers.add(tower);

            }

        }

        return placedTowers;

    }


   private static boolean factible(Tower tower, Point2D candidatePosition, ArrayList<Tower> placedTowers, ArrayList<Obstacle> obstacles, Map map) {
       // Obtener la posición y radio de la torre candidata
       float radius = tower.getRadius();

       // Verificar si la torre queda completamente dentro de los límites del terreno de juego
       boolean dentroLimites = limits(candidatePosition, radius, map);
       if (!dentroLimites) {
           return false;
       }

       // Verificar colisiones con obstáculos y otras torres
       for (Obstacle o : obstacles) {
           if (collide(candidatePosition, radius, o.getPosition(), o.getRadius())) {
               return false; // Colisión con un obstáculo
           }
       }

       // Verificar colisiones con otras torres
       for (Tower placedTower : placedTowers) {
           if (collide(candidatePosition, radius, placedTower.getPosition(), placedTower.getRadius())) {
               return false; // Colisión con otra torre
           }
       }

       // Verificar que no se coloque sobre los caminos
       for (MapNode walkableNode : map.getWalkableNodes()) {
           float distanceToNode = candidatePosition.distance(walkableNode.getPosition());
           if (distanceToNode < radius) {
               return false; // Colisión con un camino
           }
       }

       // Todas las condiciones se cumplen, la torre es factible
       return true;
   }

   private static boolean limits(Point2D pos, float radio, Map map){
       float mapWidth = map.getSize().x;
       float mapHeight = map.getSize().y;

       float x = pos.x;
       float y = pos.y;

       // Verificar si el círculo se sale de los límites en el eje X
       if (x - radio < 0 || x + radio > mapWidth) {
           return true;  // El círculo se sale en el eje X
       }

       // Verificar si el círculo se sale de los límites en el eje Y
       if (y - radio < 0 || y + radio > mapHeight) {
           return true;  // El círculo se sale en el eje Y
       }

       // El círculo no se sale de los límites
       return false;

   }

}
