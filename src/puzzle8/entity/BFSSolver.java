/*
	Breadth First Search Solver
	Busqueda Primero en Anchura
*/
package puzzle8.entity;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class BFSSolver implements Solver {
    

	public BFSSolver(){
		//TODO
	}

	public ArrayList<Direction> solve(Board begin, Board end, int limit){
               ArrayList<Board> Fila=new ArrayList<Board>();
               Fila.add(begin);

            while(true){

               if(Fila.get(0).getHistory().size()==limit){
                   System.out.println("Se ha llegado al limite marcado");
                   break;
               }
               if(end.equals(Fila.get(0))){
                break;
               }else{
               Fila.addAll(Fila.get(0).expand());          
               Fila.remove(0);
               }
            }
               return Fila.get(0).getHistory();
	}
}
