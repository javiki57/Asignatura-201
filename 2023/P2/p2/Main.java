import net.agsh.towerdefense.*;

import java.util.ArrayList;

/*
Starting round: 0
Total enemies: 1
Number of available towers: 18
Money available: 209.52121
Bought towers: 16
Total cost: 198.18323 (money left: 11.337982)
n	    NoSort	InsertionSort	MergeSort	QuickSort
496	    0,0346	   0,1037        0,0532      0,0989
946	    0,0633	   0,3940	     0,0912	     0,2208
1540	0,1034	   1,1574	     0,1534	     0,4165
2278	0,1493	   2,4839	     0,2209	     0,7513
3160	0,2086	   5,2842	     0,3199	     1,1947

La razón por la que NoSort es el más eficiente en términos de tiempo por iteración es
porque simplemente busca los "count" nodos más pequeños en la lista original, sin realizar
ningún proceso de ordenamiento, lo cual es más rápido en comparación con los algoritmos de
ordenamiento que involucran comparaciones y movimientos de elementos.

No obstante, hay que tener en cuenta que la eficiencia de un algoritmo depende del contexto,
por lo que, mientras que en este caso si es el más eficiente no tiene por qué serlo en otro
distinto. En situaciones donde la lista debe estar completamente ordenada, los algoritmos de
ordenación como MergeSort y QuickSort pueden ser más apropiados.

 */
public class Main {
    public static void main(String[] args) {
        // initialize game and map
        Game g = Game.getInstance();
        g.init(0);
        Config config = new Config();

        System.out.print("n\tNoSort\tInsertionSort\tMergeSort\tQuickSort\n");

        for(float scale = 0.5f ; scale < 1.5 ; scale += 0.2f) {
            Map map = new Map(new Point2D(config.get(Config.Parameter.MAP_SIZE_X) * scale,
                    config.get(Config.Parameter.MAP_SIZE_Y) * scale),
                    config.get(Config.Parameter.MAP_GRID_SPACE));
            map.init();

            // assign values to nodes and print map
            boolean printMap = false;
            MapNode center = map.getNodes()[map.getNodes().length / 2][map.getNodes()[0].length / 2];
            for (int i = 0; i < map.getNodes().length; i++) {
                for (int j = 0; j < map.getNodes()[i].length; j++) {
                    if (map.getNodes()[i][j].isWalkable()) {
                        if(printMap) {
                            System.out.print("   ");
                        }
                    } else {
                        float distanceToCenter = center.getPosition().distance(map.getNodes()[i][j].getPosition());
                        map.getNodes()[i][j].setValue(0, distanceToCenter);
                        if(printMap) {
                            System.out.printf("%2.0f ", distanceToCenter / 10f);
                        }
                    }
                }
                if(printMap) {
                    System.out.println();
                }
            }

            // select best nodes for towers
            Point2D size = g.getMap().getSize();
            float separation = map.getSeparation();
            int numberOfTowers = (int) (g.getParam(Config.Parameter.TOWER_DENSITY) * size.x * size.y / (separation * separation));
            ArrayList<MapNode> best = selectBestNodes(map.getNodesList(), numberOfTowers);
        }
    }

    private static ArrayList<MapNode> deepCopy(ArrayList<MapNode> nodes) {
        ArrayList<MapNode> copy = new ArrayList<>();
        for(MapNode node : nodes) {
            copy.add(new MapNode(node.getPosition(), node.isWalkable(), node.getValues()));
        }
        return copy;
    }

    private static boolean NodeListEquals(ArrayList<MapNode> a, ArrayList<MapNode> b, int valueIndex) {
        if(a.size() != b.size()) {
            return false;
        }
        for(int i=0; i<a.size(); i++) {
            if(a.get(i).getValue(valueIndex) != b.get(i).getValue(valueIndex)) {
                return false;
            }
        }
        return true;
    }

    private static ArrayList<MapNode>  selectBestNodes(ArrayList<MapNode> nodesList, int count) {
        int n = nodesList.size();
        long maxTime = 1000;

        System.out.print(n+"\t");

        // ========================== No sort ==========================
        int iterations = 0;
        Chronometer c = new Chronometer();
        ArrayList<MapNode> bestNoSort;
        do {
            c.pause();
            ArrayList<MapNode> nodesListCopy = deepCopy(nodesList);
            c.resume();
            bestNoSort = selectBestNodesNoSort(nodesListCopy, count);
            iterations++;
        } while(c.getElapsedTime() < maxTime);
        float timePerIteration = (float) c.getElapsedTime() / iterations;
        System.out.printf("%.4f\t", timePerIteration);

        // ========================== Insertion sort ==========================
        iterations = 0;
        c = new Chronometer();
        ArrayList<MapNode> bestInsertionSort;
        do {
            c.pause();
            ArrayList<MapNode> nodesListCopy = deepCopy(nodesList);
            c.resume();
            bestInsertionSort = selectBestNodesInsertionSort(nodesListCopy, count);
            iterations++;
        } while(c.getElapsedTime() < maxTime);
        timePerIteration = (float) c.getElapsedTime() / iterations;
        System.out.printf("%.4f\t", timePerIteration);

        if(!NodeListEquals(bestNoSort, bestInsertionSort, 0)) {
            System.out.println("ERROR");
        }

        // ========================== Merge sort ==========================
        iterations = 0;
        c = new Chronometer();
        ArrayList<MapNode> bestMergeSort;
        do {
            c.pause();
            ArrayList<MapNode> nodesListCopy = deepCopy(nodesList);
            c.resume();
            bestMergeSort = selectBestNodesMergeSort(nodesListCopy, count);
            iterations++;
        } while(c.getElapsedTime() < maxTime);
        timePerIteration = (float) c.getElapsedTime() / iterations;
        System.out.printf("%.4f\t", timePerIteration);

        if(!NodeListEquals(bestNoSort, bestMergeSort, 0)) {
            System.out.println("ERROR");
        }

        // ========================== Quick sort ==========================
        iterations = 0;
        c = new Chronometer();
        ArrayList<MapNode> bestQuickSort;
        do {
            c.pause();
            ArrayList<MapNode> nodesListCopy = deepCopy(nodesList);
            c.resume();
            bestQuickSort = selectBestNodesQuickSort(nodesListCopy, count);
            iterations++;
        } while(c.getElapsedTime() < maxTime);
        timePerIteration = (float) c.getElapsedTime() / iterations;
        System.out.printf("%.4f\t", timePerIteration);

        if(!NodeListEquals(bestNoSort, bestQuickSort, 0)) {
            System.out.println("ERROR");
        }

        System.out.println();

        return bestNoSort;
    }


    private static ArrayList<MapNode> selectBestNodesNoSort(ArrayList<MapNode> nodesList, int count) {
        // TODO: Return "count" nodes with the lowest value in the index 0 WITHOUT ORDERING the list. Given a node
        // in the list, the value of the node is accessed with node.getValue(0).

        //lista para almacenar los count mejores
        ArrayList<MapNode> mejores = new ArrayList<>();

        //Si count es mayor o igual que la lista, devolvemos la lista
        if(count >= nodesList.size()){
            return nodesList;
        }

        //Iteramos count veces para encontrar los mejores valores.  >
        for(int i=0; i < count ; i++){
            MapNode minNode = nodesList.get(0); // Inicializamos con el primer nodo como mínimo
            for(MapNode node : nodesList){
                if(node.getValue(0) < minNode.getValue(0)){
                    minNode = node;
                }
            }
            mejores.add(minNode);
            nodesList.remove(minNode);
        }

        return mejores;
    }


    private static ArrayList<MapNode> selectBestNodesInsertionSort(ArrayList<MapNode> nodesList, int count) {
        // TODO: Implement insertion sort according to the values of nodes in the index 0. Given a node in the list, the value
        // of the node is accessed with node.getValue(0). The list is sorted in ascending order.
        // Return the "count" first nodes in the list.

        //Algoritmo por inserción, empezando por el segundo elemento.
         for(int i=1; i<nodesList.size(); i++){
            MapNode actual = nodesList.get(i);
            float valoractual = actual.getValue(0);
            int j = i-1;

            //Mover los mayores que el actual hacia la derecha  <
            while(j >= 0 && nodesList.get(j).getValue(0) > valoractual){
                nodesList.set(j+1, nodesList.get(j));
                j--;
            }

            //Colocamos el valor actual en su posición correcta
            nodesList.set(j+1,actual);
        }
        // Crear una nueva lista para almacenar los "count" primeros nodos ordenados
        ArrayList<MapNode> bestNodes = new ArrayList<>();
        for(int i=0; i<count && i<nodesList.size(); i++){
            bestNodes.add(nodesList.get(i));
        }

        return bestNodes;
    }

    private static ArrayList<MapNode> selectBestNodesMergeSort(ArrayList<MapNode> nodesList, int count) {
        // TODO: Implement merge sort according to the values of nodes in the index 0. Given a node in the list, the value
        // of the node is accessed with node.getValue(0). The list is sorted in ascending order.
        // Return the "count" first nodes in the list.
        //

        //Comprobamos que la lista está vacía o con un elemento
        if(nodesList.size() <= 1){
            return nodesList;
        }

        //Dividimos la lista en dos partes para ordenar
        int medio = nodesList.size() / 2;
        ArrayList<MapNode> izq = new ArrayList<>(nodesList.subList(0,medio));
        ArrayList<MapNode> der = new ArrayList<>(nodesList.subList(medio, nodesList.size()));

        //Recursión en las dos mitades
        izq = selectBestNodesMergeSort(izq,count);
        der = selectBestNodesMergeSort(der,count);

        //combinación de ambas mitades >=
        ArrayList<MapNode> result = new ArrayList<>();
        int i=0, j=0;
        while(i < izq.size() && j < der.size()){
            if(izq.get(i).getValue(0) <= der.get(j).getValue(0)){
                result.add(izq.get(i));
                i++;
            } else {
                result.add(der.get(j));
                j++;
            }
        }

        //Se agregan los elementos restantes, si existen
        while(i < izq.size()){
            result.add(izq.get(i));
            i++;
        }

        while(j < der.size()){
            result.add(der.get(j));
            j++;
        }

        //Cogemos los count elementos
        if(result.size() > count){
            result = new ArrayList<>(result.subList(0,count));
        }

        return result;
    }

    private static ArrayList<MapNode> selectBestNodesQuickSort(ArrayList<MapNode> nodesList, int count) {
        // TODO: Implement quick sort according to the values of nodes in the index 0. Given a node in the list, the value
        // of the node is accessed with node.getValue(0). The list is sorted in ascending order.
        // Return the "count" first nodes in the list.

        quickSort(nodesList, 0, nodesList.size() - 1);

        //Devolvemos los count elementos
        if (nodesList.size() > count) {
            nodesList = new ArrayList<>(nodesList.subList(0, count));
        }

        return nodesList;
    }

    private static void quickSort(ArrayList<MapNode> nodesList, int izq, int der){

        if(izq < der){
            //Se elige y se coloca el pivote en la posición correcta
            int pivote = particion(nodesList, izq, der);

            //Se ordenan las sublistas de manera recursiva
            quickSort(nodesList, izq, pivote -1);
            quickSort(nodesList, pivote +1, der);
        }
    }

    private static int particion(ArrayList<MapNode> nodesList, int izq, int der){
        // Elegimos el pivote (en este caso, el último elemento de la sub-lista)
        float pivote = nodesList.get(der).getValue(0);
        int i = izq - 1;

        for(int j = izq; j<der ; j++){
            // Si el elemento actual es menor o igual al pivote, lo intercambiamos con el elemento en el índice i+1 =>
            if(nodesList.get(j).getValue(0) <= pivote){
                i++;
                cambio(nodesList, i, j);
            }
        }

        //Colocamos el pivote en la posición correcta
        cambio(nodesList, i+1, der);

        return i+1;
    }

    private static void cambio(ArrayList<MapNode> nodesList, int i, int j){
        MapNode temp = nodesList.get(i);
        nodesList.set(i, nodesList.get(j));
        nodesList.set(j, temp);
    }
}
