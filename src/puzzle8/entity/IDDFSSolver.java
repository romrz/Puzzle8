/*
	Iterative Deepening Depth-First Search Solver
	Busqueda Primero en Profundidad Iterativa

        Romario Ramirez Calderon
*/
package puzzle8.entity;

import java.util.ArrayList;

public class IDDFSSolver implements Solver {
	
    public IDDFSSolver(){

    }

    /**
     * Encuentra la solucion y regresa una lista de los movimientos que
     * se deben hacer para llegar a la solucion
     */
    public ArrayList<Direction> solve(Board begin, Board end, int limit){
        Board found = null;
        ArrayList<Direction> sequence = new ArrayList<Direction>();

        // Comienza la busqueda por profundidad iterada iterada
        for(int i = 0; i < limit; i++) {
            // Inicia una busqueda de profundidad limitada
            found = DLS(begin, end, i);
            // Se detiene si ya encontro la solucion
            if(found != null) {
                sequence = found.getHistory();
                break;
            }
        }

        // Imprime la solucion en la consola y la regresa
        System.out.println("Result: " + sequence);
        return sequence;
    }

    /**
     * Depth Limit Search - Busqueda por profundidad limitada
     */
    private Board DLS(Board start, Board goal, int depth) {
        Board found = null;
        // Paso base: Si start es la solucion y esta en el ultimo nivel, regresa
        if((depth == 0) && start.equals(goal)) {
            return start;
        }
        // Si el nivel aun no es el ultimo, se obtienen los nodos hijos
        // y se vuelve a llamar la funcion en ellos
        else if(depth > 0) {
            // Obtiene los nodos hijos
            // Todas las posibles configuraciones del tablero que se pueden
            // hacer a partir de la actual
            ArrayList<Board> children = start.expand();

            // Para cada hijo vuelve a hacer lo mismo
            for(Board child : children) {
                found = DLS(child, goal, depth - 1);
                if(found != null)
                    return found;
            }
        }
        return null;
    }
}
