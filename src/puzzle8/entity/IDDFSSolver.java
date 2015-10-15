/*
	Iterative Deepening Depth-First Search Solver
	Busqueda Primero en Profundidad Iterativa
*/
package puzzle8.entity;

import java.util.ArrayList;

public class IDDFSSolver implements Solver {
	
    public IDDFSSolver(){
        //TODO
    }

    public ArrayList<Direction> solve(Board begin, Board end, int limit){
        Board found = null;
        ArrayList<Direction> sequence = new ArrayList<Direction>();
        
        for(int i = 0; i < limit; i++) {
            found = DLS(begin, end, i);
            if(found != null) {
                sequence = found.getHistory();
                break;
            }
        }
        
        System.out.println("Result: " + sequence);
        return sequence;
    }

    // Depth Limit Search
    private Board DLS(Board start, Board goal, int depth) {
        Board found = null;
        if((depth == 0) && start.equals(goal)) {
            return start;
        }
        else if(depth > 0) {
            ArrayList<Board> children = start.expand();
            for(Board child : children) {
                found = DLS(child, goal, depth - 1);
                if(found != null)
                    return found;
            }
        }
        return null;
    }
}
