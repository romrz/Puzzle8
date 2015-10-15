package puzzle8.gui;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import puzzle8.entity.*;

public class Puzzle8 {

    private JFrame window;
    private Puzzle8Game puzzle;

    private JPanel optionsPanel;
    private JButton solveBtn;
    private ButtonGroup radioGroup;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private JRadioButton radio3;
    private JRadioButton radio4;

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

        radio1 = new JRadioButton("Busqueda en anchura");
        radio2 = new JRadioButton("Busqueda en profundidad");
        radio3 = new JRadioButton("Busqueda en profundidad iterativa");
        radio4 = new JRadioButton("Busqueda bidireccional");

        radio1.setActionCommand("a");
        radio2.setActionCommand("p");
        radio3.setActionCommand("pi");
        radio4.setActionCommand("b");
        
        radioGroup = new ButtonGroup();
        radioGroup.add(radio1);
        radioGroup.add(radio2);
        radioGroup.add(radio3);
        radioGroup.add(radio4);
        
        solveBtn = new JButton("Resolver");
	solveBtn.setActionCommand("Resolver");
        solveBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    String option = radioGroup.getSelection().getActionCommand();
		    System.out.print("El metodo de busqueda seleccionado es: ");
		    System.out.println(option);

		    if(option.equals("a"))
			solver = new BFSSolver();
		    else if(option.equals("p"))
			solver = new DFSSolver();
		    else if(option.equals("pi"))
			solver = new IDDFSSolver();
		    else if(option.equals("b"))
			solver = new BSSolver();

		    solve();
		}

            });
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.add(new JLabel("Opciones para resolver el 8-Puzzle"));
        optionsPanel.add(new JLabel("Selecciona el algoritmo"));
        optionsPanel.add(radio1);
        optionsPanel.add(radio2);
        optionsPanel.add(radio3);
        optionsPanel.add(radio4);
        optionsPanel.add(solveBtn);
        
        window = new JFrame("8 Puzzle");
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(puzzle, BorderLayout.WEST);
        window.add(optionsPanel, BorderLayout.EAST);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setResizable(false);
	//	window.setIgnoreRepaint(true);
        window.setVisible(true);

        puzzle.start();
    }

    public void solve() {
	ArrayList<Direction> sequence = solver.solve(
            (Board)puzzle.getBoard().clone(),
	    (Board)puzzle.getObjectiveBoard().clone(),
	    1000); // de limite aunque no se utilice 

        // Prueba de una secuancia de movimientos
	/*	ArrayList<Direction> sequence = new ArrayList<Direction>();
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
	*/
        
        if(sequence.isEmpty()) return;
        for(Direction dir : sequence) {
            puzzle.moveBlank(dir);
            
            try { Thread.sleep(500); }
            catch(InterruptedException e) {}
        }
	

    }

}
