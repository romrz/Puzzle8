/*
	Depth First Search Solver
	Busqueda Primero en Profundidad

        Ricardo Hernandez Ramirez
*/
package puzzle8.entity;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.SortedSet;


public class DFSSolver implements Solver {
	SortedSet<Board> visitados = new TreeSet<Board>();//Conjunto de configuraciones ya visitadas

	public DFSSolver(){
		//TODO
	}

	public ArrayList<Direction> solve(Board begin, Board end, int limit){
		
		ArrayList<Direction> camino = new ArrayList<Direction>();

        Board encontrado = DL(begin, end, limit);
        if(encontrado != null)
            camino = encontrado.getHistory();
        
        System.out.println("Result: " + camino);
        return camino;
	}

	private Board DL(Board inicio, Board fin, int limite){ //hace la busqueda en base a un limite de profundidad
		
		visitados.add(inicio);//añado el nodo raiz

		if (inicio.equals(fin))
			return inicio;
		else if(limite > 0){
			ArrayList<Board> hijos = inicio.expand(); //expando el nodo inicio
			for(Board hijo : hijos){
				if(visitados.contains(hijo))//Si el nodo actual es uno de los ya visitados no lo expando para no repetir
					hijo = null;
				else{
					Board encontrado = DL(hijo,fin,limite-1);//aplico recursividad
				 	if(encontrado != null)
                    	return encontrado;
                }                   
			}
		}
		return null;
	}
}
