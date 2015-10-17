/*
	Breadth First Search Solver
	Busqueda Primero en Anchura
      
        Miguel Angel Reynoso Morales.
*/
package puzzle8.entity;

import java.util.ArrayList;

public class BFSSolver implements Solver {
    //En la búsqueda en anchura se incrementa el uso de la memoria de una manera exponencial.
    //por lo que la solución no debe encontrarse a una gran profundidad.
    //la ventaja es que encuentra la solución más corta.

	public BFSSolver(){
		//constructor.
	}

	public ArrayList<Direction> solve(Board begin, Board end, int limit){
              //Recibe en estado inicial del tablero de puzzle por medio de begin, así como el
              //estado objetivo del tablero.
              //limit indica el máximo nivel de profundidad en el cual se puede buscar.

               ArrayList<Board> Cola=new ArrayList<Board>();  //se inicializa la cola.
               ArrayList<Direction> sequence = new ArrayList<Direction>(); //variable vacía para retornar en caso de llegar al limite
               Cola.add(begin);//Se agrega a la cola el estado inicial del tablero.

            while(true){

               if(Cola.get(0).getHistory().size()==limit){ //Si el tamaño del historial del primer nodo en la cola es igual al limite, rompe el ciclo
                                                           //el historial contiene todos los movimientos realizados por lo que su longitud indica el
                                                           //nivel en el que se encuntra.
                   System.out.println("Se ha llegado al limite marcado");
                   return sequence;// regresa vacio
               }
               if(end.equals(Cola.get(0))){  //compara el nodo que se encuentra al frente de la cola con el objetivo si es igual se rompe el ciclo y
                                             // se retorna el historial del nodo.
                break;
               }else{          //Si no son iguales expande y agrega los hijos del nodo a la cola y es removido del frente de la cola.
               Cola.addAll(Cola.get(0).expand());          
               Cola.remove(0);
               }
            }
               return Cola.get(0).getHistory();//retorna el historial del nodo solución.
	}
}
