/*
    Bidirectional Search Solver
    Busqueda Bidireccional
*/
package puzzle8.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.SortedSet;


public class BSSolver implements Solver {
    
    public BSSolver(){
        //TODO
    }

    public ArrayList<Direction> solve(Board begin, Board end, int limit){
        LinkedList<Board> aVisitarB = new LinkedList<Board>(); //A visitar Begin
        LinkedList<Board> aVisitarE = new LinkedList<Board>(); //A visitar End
        SortedSet<Board> visitadosB = new TreeSet<Board>(); //Conjunto de los visitados Begin
        SortedSet<Board> visitadosE = new TreeSet<Board>(); //Conjunto de los visitados End
        SortedSet<Board> auxSet = new TreeSet<Board>();
        ArrayList<Board> auxArray = null;
        ArrayList<Direction> movBE = null;
        ArrayList<Direction> movEB = null;
        Board b = null;
        //Agregando los nodos iniciales
        aVisitarB.add(begin);
        aVisitarE.add(end);
        while(true){
            //Calculando la interseccion entre visitadosB y visitadosE
            auxSet.clear();
            auxSet.addAll(visitadosB);
            auxSet.retainAll(visitadosE);
            if(auxSet.size()>0) break;
            //Si aun no se encuentran las busquedas
            //Para la busqueda B->E
            //Sacar el tope
            b = aVisitarB.removeFirst();
            //Expandir el nodo
            auxArray = b.expand();
            //Agregando cada elemento a la cola de expancion si no esta
            for(int i=0; i<auxArray.size(); i++){
                if(visitadosB.contains(auxArray.get(i))) continue;
                aVisitarB.add(auxArray.get(i));
            }
            //Para la busqueda E->B
            //Sacar el tope
            b = aVisitarE.removeFirst();
            //Expandir el nodo
            auxArray = b.expand();
            //Agregando cada elemento a la cola de expancion si no esta
            for(int i=0; i<auxArray.size(); i++){
                if(visitadosE.contains(auxArray.get(i))) continue;
                aVisitarE.add(auxArray.get(i));
            }
            //for(Board bAux: auxArray){
            //    if(visitadosE.contains(bAux)) continue;
            //    aVisitarE.add(bAux);
            //}
        }
        //Formando la lista a regresar
        //Tomando los movimientos de B a X
        movBE = auxSet.first().getHistory();
        //Tomando los movimientos de E a X
        auxSet.clear();
        auxSet.addAll(visitadosE);
        auxSet.retainAll(visitadosB);
        movEB = auxSet.first().getHistory();
        //Eliminando el ultimo movimiento de E a B
        movEB.remove(movEB.size()-1);
        //Agregando todos los movimientos en una sola lista
        for(int i=movEB.size()-1; i>=0; i--){
            if(movEB.get(i)==Direction.LEFT){
                movBE.add(Direction.RIGHT);
            }else if(movEB.get(i)==Direction.RIGHT){
                movBE.add(Direction.LEFT);
            }else if(movEB.get(i)==Direction.UP){
                movBE.add(Direction.DOWN);
            }else if(movEB.get(i)==Direction.DOWN){
                movBE.add(Direction.UP);
            }
        }
        return movBE;
    }
}
