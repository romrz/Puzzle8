package puzzle8.gui;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import puzzle8.entity.*;

public class Puzzle8 implements Runnable {

    private JFrame window;
    private Puzzle8Game puzzle;

    private JPanel optionsPanel;
    private JButton solveBtn;
    private JButton stopBtn;
    private JButton scrambleBtn;
    private ButtonGroup radioGroup;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private JRadioButton radio3;
    private JRadioButton radio4;
    private JTextField limit;
    private JTextField sizeField;

    private JLabel info = new JLabel();
    private JLabel steps = new JLabel();

    private int size = 3;
    private int depthLimit;
    private boolean stopMoving = false;
    private Solver solver;
    
    public Puzzle8() {
        puzzle = new Puzzle8Game(createBoard(size), createBoard(size), size);
    }

    public Board createBoard(int size) {
	byte b[][] = new byte[size][size];

	byte k = 0;
	for(int i = 0; i < size; i++)
	    for(int j = 0; j < size; j++)
		b[j][i] = k++;

	return new Board(size, b);
	
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

        radio1.setSelected(true);
        
        radioGroup = new ButtonGroup();
        radioGroup.add(radio1);
        radioGroup.add(radio2);
        radioGroup.add(radio3);
        radioGroup.add(radio4);

        limit = new JTextField("100");
	limit.setMaximumSize(new Dimension(
	    Integer.MAX_VALUE, limit.getPreferredSize().height + 10));

        solveBtn = new JButton("Resolver");
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

                    depthLimit = Integer.parseInt(limit.getText());

                    new Thread(Puzzle8.this, "Ejecucion de la solucion").start();
		}

            });

        stopBtn = new JButton("Detener");
        stopBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                    stopMoving = true;
		}
            });

        
        scrambleBtn = new JButton("Revolver");
        scrambleBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                    scramble();
		}
            });

        
        optionsPanel = new JPanel();
        //        optionsPanel.setBackground(Color.BLUE);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.add(new JLabel("INSTRUCCIONES:"));
        optionsPanel.add(new JLabel("  1. Desordenar el tablero."));
        optionsPanel.add(new JLabel("       Ya sea con el teclado o con el boton Revolver"));
        optionsPanel.add(new JLabel("  2. Elegir el algortitmo de busqueda"));
        optionsPanel.add(new JLabel("  3. Oprimir el boton Resolver"));
        optionsPanel.add(new JLabel("  4. Presione el boton Detener para detener la animacion"));
        optionsPanel.add(new JLabel("  "));
        optionsPanel.add(new JLabel("Opciones para resolver el 8-Puzzle"));
        optionsPanel.add(new JLabel("Selecciona el algoritmo"));
        optionsPanel.add(radio1);
        optionsPanel.add(radio2);
        optionsPanel.add(radio3);
        optionsPanel.add(radio4);
        optionsPanel.add(new JLabel("  "));
        optionsPanel.add(new JLabel("Introduce la profundidad maxima"));
        optionsPanel.add(new JLabel("con la que trabajara el algoritmo"));
        optionsPanel.add(limit);
        optionsPanel.add(new JLabel(" "));
        optionsPanel.add(scrambleBtn);
        optionsPanel.add(solveBtn);
        optionsPanel.add(stopBtn);
        optionsPanel.add(new JLabel(" "));
        optionsPanel.add(info);
        optionsPanel.add(steps);

        window = new JFrame("Algoritmos para resolver el 8 Puzzle");
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

    /* Revuelve el tablero */
    public void scramble() {
        Random r = new Random();
        int steps = r.nextInt(10) + 10;
        Direction dir = Direction.LEFT;
        for(int i = 0; i < steps; i++) {
            switch(r.nextInt(4)) {
            case 0:
                dir = Direction.LEFT;
                break;
            case 1:
                dir = Direction.RIGHT;
                break;
            case 2:
                dir = Direction.UP;
                break;
            case 3:
                dir = Direction.DOWN;
                break;
            }
            puzzle.getBoard().move(dir);
        }
        puzzle.getBoard().clearHistory();
        puzzle.checkObjectiveReached();
    }
    
    public void solve() {
        steps.setText("Resolviendo...");
        optionsPanel.repaint();
	ArrayList<Direction> sequence = solver.solve(
            (Board)puzzle.getBoard().clone(),
	    (Board)puzzle.getObjectiveBoard().clone(),
	    depthLimit);
        steps.setText("Resuelto. Pasos para llegar a la solucion: " + sequence.size());
        optionsPanel.repaint();

        stopMoving = false;
        if(sequence.isEmpty()) return;
        for(Direction dir : sequence) {
            puzzle.moveBlank(dir);

            if(stopMoving) break;
            try { Thread.sleep(500); }
            catch(InterruptedException e) {}
        }
    }

    public void run() {
        solve();
    }

}
