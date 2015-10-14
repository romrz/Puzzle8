package puzzle8.testing;

import java.util.ArrayList;
import puzzle8.gui.*;
import puzzle8.entity.*;

public class Tester{

	public static void main(String[] args) {
            
	    //    Puzzle8 puzzle = new Puzzle8();
	    //            puzzle.start();
	    
	    Board start =  new Board(3, new byte[][] {
		    {0, 3, 6}, {1, 4, 7}, {2, 5, 8}});

	    ArrayList<Direction> sequence = new ArrayList<Direction>();
	    sequence.add(Direction.RIGHT);
	    	    sequence.add(Direction.RIGHT);
	    sequence.add(Direction.DOWN);
	    sequence.add(Direction.DOWN);
	    sequence.add(Direction.LEFT);
	    sequence.add(Direction.LEFT);
	    /*	    sequence.add(Direction.RIGHT);
	    sequence.add(Direction.RIGHT);
	    sequence.add(Direction.UP);
	    sequence.add(Direction.UP);
	    sequence.add(Direction.LEFT);
	    sequence.add(Direction.LEFT);*/

	    for(Direction d : sequence) {
		ArrayList<Board> s = start.expand();
		System.out.println("Posibles expanciones");
		for(Board b : s){
		    System.out.println("---- "+b.getHistory());
		}
		start.move(d);
		
		System.out.println("Movimiento: "+ d);
		System.out.println();
	    }

            
	}
}
