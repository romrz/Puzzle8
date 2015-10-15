package puzzle8.gui;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import puzzle8.entity.*;

/**
 * Esta clase implementa solamente la funcionalidad del juego 8-Puzzle
 * Representa el tablero del juego, en el cual se pueden mover 
 * las piezas para completar el objetivo.
 * 
 * Las opciones externas al juego y la inteligencia artificial se
 * implementan en clases externas
 */

public class Puzzle8Game extends Canvas implements Runnable, KeyListener {

    // Tamano de cada cuadrito
    private int tileSize = 128;
    // Tamano del tablero
    private int size;

    // Tablero que se muestra en la aplicacion
    // y al cual se le aplican los movimientos
    private Board board;
    // Tablero al que se quiere llegar
    private Board objective;
    // Indica se ya se llego al objetivo
    private boolean objectiveReached = false;

    // Animacion de las transiciones
    // Posicion del cuadrito que se esta animando en el tablero
    private int animTileX = -1;
    private int animTileY = -1;
    // Posicion inicial en pixeles
    private int x1;
    private int y1;
    // Posicion actual en pixeles
    private float currentX = 0;
    private float currentY = 0;
    // Rapidez de la animacion
    private float speed = 1000f;
    // Velocidad de la animacion
    private float velX;
    private float velY;

    private double deltaTime = 0;
    
    // Almacena la direccion en la que se tiene que mover el espacio vacio
    // Solo se mueve al inicio de cada frame. Por lo tanto, si llega
    // una peticion de movimiento mediante el metodo moveBlank almacena la
    // direccion aqui y espera hasta el inicio del frame para ejecutar el
    // movimiento.
    private Direction direction;

    //
    private int marginH = 50;
    private int marginV = 50;

    private Color lineColor = new Color(0, 0, 0, 120);
    
    public Puzzle8Game(Board board, Board objective, int size) {
        this.board = board;
        this.objective = objective;
	this.size = size;

        addKeyListener(this);
        setFocusable(true);
        setSize(new Dimension(size * tileSize + marginH, size * tileSize+marginV));
    }

    public void start() {
        new Thread(this, "Game").start();
    }
    
    public void setBoard(Board board) {
	this.board = board;
    }

    public void setObjectiveBoard(Board ob) {
	this.objective = ob;
    }

    public Board getBoard() {
        return board;
    }

    public Board getObjectiveBoard() {
        return objective;
    }
    
    private void drawTile(Graphics2D g, int value, int x, int y, int size) {
        Color color = new Color(0, 51, 204, 255);
        int w = 5;
        if(objectiveReached)
            color = Color.GREEN.darker().darker();

        Color light = color.brighter();
        Color dark = color.darker();

        g.setColor(light);
        g.fillRect(x, y, size, size);
        g.setColor(dark);
        g.fillRect(x + w, y + w, size - w, size - w);
        g.setColor(color);
        g.fillRect(x + w, y + w, size - 2*w, size -2*w);

        g.setColor(Color.white);
        g.drawString("" + value, x + size/2, y + size/2);
    }

    // NOTE:
    // Por el momento, los eventos se detectan en esta misma clase
    // Pero creo que lo mejor seria implementarla en una clase externa
    // Y tal vez, que solo se pueda tener ya sea el movimienton manual o el
    // movimiento realizado por AI. Para que cuando se este ejecutando
    // la AI no se permita movaerlo manualmente.
    public void keyPressed(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){
        Direction dir;

        // Obtiene en que direccion se va a mover
        // el espacio vacio
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dir = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                dir = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                dir = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                dir = Direction.RIGHT;
                break;
            default:
                return;
        }

        moveBlank(dir);
    }

    public void moveBlank(Direction dir) {
        direction = dir;
    }

    /**
     * Se llama cada inicio del frame, si existe un movimiento que realizar
     */
    private void move() {
        animTileX = board.getBlankX();
        animTileY = board.getBlankY();

        if(!board.move(direction)) {
            animTileX = animTileY = -1;
            direction = null;
            return;
        }
        board.clearHistory();

        x1 = board.getBlankX() * tileSize;
        y1 = board.getBlankY() * tileSize;

        currentX = currentY = 0;
        
        switch(direction) {
            case UP:
                velX = 0;
                velY = speed;
                break;
            case DOWN:
                velX = 0;
                velY = -speed;
                break;
            case LEFT:
                velX = speed;
                velY = 0;
                break;
            case RIGHT:
                velX = -speed;
                velY = 0;
                break;
        }

        objectiveReached = board.equals(objective);
        direction = null;
    }
    
    public void run() {
	long previous = System.currentTimeMillis();
	long elapsed, current, sleepTime;
	long frameTime = 1000 / 20; // 20 FPS
	
        // Game Loop
        while(true) {
	    current = System.currentTimeMillis();
	    elapsed = current - previous;
	    previous = current;
	    deltaTime = elapsed / 1000.0;
	    //	    System.out.println("Elapsed Time: " + elapsed);
	    
            // Realiza el movimiento si existe alguno
            if(direction != null) move();
            render();

	    sleepTime = frameTime - (System.currentTimeMillis() - current);
	    if(sleepTime < 0) sleepTime = 0;
	    try { Thread.sleep(sleepTime); }
	    catch(InterruptedException e) {}
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());

        // Dibuja el tablero
        Color boardColor = new Color(51, 51, 255, 255);
        int outerMargin = 20;
        g.setColor(boardColor);
        g.fillRect(marginH/2-outerMargin, marginV/2-outerMargin,
                        size*tileSize+2*outerMargin, size*tileSize+2*outerMargin
                        );
        g.setColor(lineColor);
        g.drawRect(marginH/2-outerMargin, marginV/2-outerMargin,
                        size*tileSize+2*outerMargin, size*tileSize+2*outerMargin
                        );
        g.setColor(boardColor.darker().darker());
        g.fillRect(marginH/2, marginV/2, size * tileSize, size * tileSize);
        g.setColor(lineColor);
        g.drawRect(marginH/2, marginV/2, size * tileSize, size * tileSize);

        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                byte value = board.getValue(x, y);

                // Anima el movimiento del cuadrito, si existe alguno que se
                // este moviendo
                if(x == animTileX && y == animTileY) {
                    drawTile(g,
                             value,
                             x1 + (int)currentX + marginH / 2,
                             y1 + (int)currentY + marginV / 2,
                             tileSize);
                    currentX += velX * deltaTime;
                    currentY += velY * deltaTime;
                    if(Math.sqrt(currentX*currentX + currentY*currentY) > tileSize)
                        animTileX = animTileY = -1;
                }
                else if(value != 0){
                    drawTile(g, value,
                             x * tileSize + marginH / 2,
                             y * tileSize + marginV / 2,
                             tileSize);
                }
            }
        }
        
        g.dispose();
        bs.show();
    }

}
