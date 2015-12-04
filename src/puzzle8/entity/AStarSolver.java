/*
	Best First Search Solver using A*

        Romario Ramirez Calderon
*/
package puzzle8.entity;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarSolver implements Solver {

    private int expandedNodes = 0;  // Numero de nodos expandidos
    private String filename = "output.txt";
    private PrintWriter writer;
    
    public AStarSolver(){
        try {
        writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        } catch(Exception e) {
            System.out.println("Error al crear el archivo.");
        }
    }

    /**
     * Encuentra la solucion y regresa una lista de los movimientos que
     * se deben hacer para llegar a la solucion
     */
    public ArrayList<Direction> solve(Board begin, Board end, int limit){
        Board found = null;
        ArrayList<Direction> sequence = new ArrayList<Direction>();

        ArrayList<Board> explored = new ArrayList<Board>();
        PriorityQueue<Board> priorityQueue = new PriorityQueue(20, new BoardComparator());
        
        begin.childNum = 0;
        begin.childCount = 0;
        begin.setHeuristic(tilesOutOfPlaceHeuristic(begin, end));
        priorityQueue.add(begin);

        // ejecuta el algoritmo mientras haya nodos por procesar
        while(priorityQueue.size() != 0) {
            // Obtiene el nodo con el menor costo estimado (f = g + h)
            Board node = priorityQueue.poll();

            printExpandedNode(node);  // Imprime el nodo en el archivo

            // Si llega a la solucíon, regresa la secuencia de movimientos
            if(node.equals(end)) {
                sequence = node.getHistory();
                break;
            }

            // Agrega el nodo a los explorados para que no se expanda de nuevo
            explored.add(node);

            // Obtiene los hijos del nodo
            ArrayList<Board> children = node.expand();

            writer.println("-- Hijos --");  // Imprime en el archivo

            // Procesa cada nodo hijo
            for(Board child : children) {
                // Calcula la heuristica del nodo hijo
                child.setHeuristic(tilesOutOfPlaceHeuristic(child, end));

                //Si no se ha visitado ya el nodo, lo agrega a la cola de prioridad
                if(!explored.contains(child) && !priorityQueue.contains(child)) {
                    priorityQueue.add(child);
                    expandedNodes++;
                    printChildNode(child);
                }
            }

            writer.println("-- --");
            writer.println();
        }
        
        // Imprime la solucion en la consola y la regresa
        System.out.println("Result: " + sequence);

        writer.close();
        
        return sequence;
    }

    public int getExpandedNodesCount() {
        return expandedNodes;
    }

    /**
     * Heuristica del numero de piezas fuera de su lugar
     */
    public int tilesOutOfPlaceHeuristic(Board board, Board target) {
        int h = 0;
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                if(board.getValue(i, j) != target.getValue(i , j))
                    h++;
            }
        }
        return h;
    }

    private void printExpandedNode(Board node) {
        writer.println("Expandiendo Nodo: " + node.childNum);
        printNode(node);
        writer.println(node.getPathCost() + " = " + node.getNodeCost() + " + " + node.getHeuristic());
        writer.println();

    }

    private void printChildNode(Board node) {
        writer.println("Nodo hijo: " + node.childNum);
        printNode(node);
        writer.println(node.getPathCost() + " = " + node.getNodeCost() + " + " + node.getHeuristic());
    }

    private void printNode(Board node) {
        for (int i = 0; i < node.getSize(); i++) {
            for (int j = 0; j < node.getSize(); j++) {
                writer.print(node.getValue(j, i) + " ");
            }
            writer.println();
        }
    }
    
}
