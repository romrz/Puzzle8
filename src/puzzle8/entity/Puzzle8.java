package puzzle8.entity;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Puzzle8 {

    private JFrame window;
    private Puzzle8Game puzzle;

    private Solver solver;
    
    public Puzzle8() {
        Board start =  new Board(3, new byte[][] {
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}});
        Board objective = new Board(3, new byte[][] {
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}});

        puzzle = new Puzzle8Game(start, objective);
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }

    public void start() {
        window = new JFrame("8 Puzzle");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(puzzle);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setIgnoreRepaint(true);
        window.setResizable(false);
        window.setVisible(true);

        puzzle.start();
    }

    public void solve() {
        //        ArrayList<Direction> sequence = solver.solve(
        //  puzzle.getBoard(), puzzle.getObjectiveBoard());

        // Prueba de una secuancia de movimientos
        try { Thread.sleep(1000); }
        catch(InterruptedException e) {}
        
        ArrayList<Direction> sequence = new ArrayList<Direction>();
        sequence.add(Direction.RIGHT);
        sequence.add(Direction.RIGHT);
        sequence.add(Direction.DOWN);
        sequence.add(Direction.DOWN);
        sequence.add(Direction.LEFT);
        sequence.add(Direction.LEFT);
        sequence.add(Direction.RIGHT);
        sequence.add(Direction.RIGHT);
        sequence.add(Direction.UP);
        sequence.add(Direction.UP);
        sequence.add(Direction.LEFT);
        sequence.add(Direction.LEFT);

        for(Direction dir : sequence) {
            puzzle.moveBlank(dir);
            
            try { Thread.sleep(1000); }
            catch(InterruptedException e) {}
        }        
    }

}
