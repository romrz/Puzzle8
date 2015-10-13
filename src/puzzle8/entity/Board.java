package puzzle8.entity;

import java.util.Arrays;

public class Board{

    private int n;
    private byte matrix[][];

    // Posicion del espacio vacio
    private int blankX;
    private int blankY;

    public Board(int n){
        if(n<8) n=8;
        this.matrix = new byte[n][n];
    }

    public Board(int n, byte matrix[][]){
        this.n = n;
        this.matrix = matrix;
        findBlankPosition();
    }

    /**
     * Calcula la posicion del espacio vacio.
     * El espacio vacio se representa por un cero.
     */
    private void findBlankPosition(){
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                if(matrix[x][y] == 0) {
                    blankX = x;
                    blankY = y;
                    return;
                }
            }
        }
    }

    public int getBlankX() {
        return blankX;
    }

    public int getBlankY() {
        return blankY;
    }
    
    public byte getValue(int x, int y){
        return matrix[x][y];
    }

    //Hace un movimiento en el tablero actual y regresa un
    //valor booleano indicando si tuvo exito la operacion
    public boolean move(Direction dir){
        int newX = blankX;
        int newY = blankY;

        // Calcula la nueva posicion del espacio vacio
        switch(dir) {
            case UP:
                newY--;
                System.out.println("UP"); // Debug
                break;
            case DOWN:
                newY++;
                System.out.println("DOWN"); // Debug
                break;
            case LEFT:
                newX--;
                System.out.println("LEFT");  // Debug
                break;
            case RIGHT:
                newX++;
                System.out.println("RIGHT");  // Debug
                break;
            default:
                return false;
        }

        // Solo lo mueve si se encuentra dentro de los limites
        // del tablero
        if(newX >= 0 && newX < n && newY >= 0 && newY < n) {
            matrix[blankX][blankY] = matrix[newX][newY];
            matrix[newX][newY] = 0;

            blankX = newX;
            blankY = newY;
            
            return true;
        }
        
        return false;
    }

    public boolean equals(Board board) {
        boolean result = true;
        for(int i = 0; i < n; i++)
            result = result && Arrays.equals(matrix[i], board.matrix[i]);
        return result;
    }
}
