/*
El pensamiento para desarrollo de la función getNodeValue ha sido dar un mayor valor a las celdas
más cercanas al centro del mapa y menos a las más lejanas. Para ello, se ha hecho una función
matemática que calcula la distancia de la celda pasada por parámetro al centro del mapa, aproximadamente.
Después, se le asigna un valor a la celda inversamente proporcional a la distancia al centro.
Además, las torretas que se van a colocar han sido previamente ordenadas descendentemente según su
radio, por lo que primeramente se añadirán las torretas de mayor radio.
*/

package net.agsh.towerdefense.strats;

import net.agsh.towerdefense.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TowerPlacer {

    public static float getNodeValue(MapNode node, Map map) {

        // Obtener las coordenadas del mapa
        float mapWidth = map.getSize().x;
        float mapHeight = map.getSize().y;

        // Obtener las coordenadas de la celda
        float coordenadaX = node.getPosition().x;
        float coordenadaY = node.getPosition().y;

        // Calcular la distancia al centro del mapa
        float distanceToEdges = Math.min(Math.min(coordenadaX, mapWidth - coordenadaX), Math.min(coordenadaY, mapHeight - coordenadaY));
        // Asignar un valor inversamente proporcional a la distancia al centro
        float value = 1.0f / (distanceToEdges  + 1);

        return value;
    }


    public static boolean collide(Point2D entity1Position, float entity1Radius,
                                  Point2D entity2Position, float entity2Radius) {

        // Calcula la distancia entre los centros de las entidades
        double distance = entity1Position.distance(entity2Position);

        // Compara la distancia con la suma de los radios para verificar la superposición
        return distance <= (entity1Radius + entity2Radius);

    }

    public static ArrayList<Tower> placeTowers(ArrayList<Tower> towers, Map map) {

        // Get nodes the enemies can walk on.
        ArrayList<MapNode> walkableNodes = map.getWalkableNodes();

        // Get obstacles.
        ArrayList<Obstacle> obstacles = map.getObstacles();



        // Se crea la lista donde se almacenan las soluciones
        ArrayList<Tower> placedTowers = new ArrayList<>();

        //Se ordenan las torres de mayor a menor radio
        List<Tower> sortedTowers = towers.stream()
                .sorted(Comparator.comparingDouble(Tower::getRadius).reversed())
                .collect(Collectors.toList());

        // Obtener los nodos del mapa ordenados de mayor a menor valor, según lo definido en getNodeValue
        List<MapNode> sortedNodes = new ArrayList<>(map.getNodesList().stream()
                .filter(node -> !walkableNodes.contains(node))  // Excluir nodos correspondientes al camino
                .sorted(Comparator.comparingDouble(node -> getNodeValue(node, map)))
                .toList());

        //mientras que no sea solución o candidatos != vacío
        while(!sortedTowers.isEmpty() && !sortedNodes.isEmpty()){

            // Extraer el primer candidato (nodo con mayor valor)
            MapNode candidateNode = sortedNodes.remove(0);
            Point2D candidatePosition = candidateNode.getPosition();
            Tower tower = sortedTowers.remove(0);

            if (factible(tower, candidatePosition, placedTowers, obstacles, map)) {
                // Agregar la torre a la lista de torres colocadas
                tower.setPosition(candidatePosition);
                placedTowers.add(tower);

            }else{
                sortedTowers.add(tower);
            }
        }

        return placedTowers;

    }


   private static boolean factible(Tower tower, Point2D candidatePosition, ArrayList<Tower> placedTowers, ArrayList<Obstacle> obstacles, Map map) {
       // Obtener la posición y radio de la torre candidata
       float radius = tower.getRadius();

       // Verificar si la torre queda completamente dentro de los límites del terreno de juego
       boolean fueraLimites = limits(candidatePosition, radius, map);
       if (fueraLimites) {
           return false;
       }

       // Verificar colisiones con obstáculos
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
          // float distanceToNode = candidatePosition.distance(walkableNode.getPosition());
          // if (distanceToNode < radius) {
          //     return false; // Colisión con un camino
           if(collide(candidatePosition, radius, walkableNode.getPosition(),Game.getInstance().getParam(Config.Parameter.ENEMY_RADIUS_MAX))){
               return false;
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
