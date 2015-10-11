package puzzle8.entity;

public class Board{

	private int n;
	private byte matrix[][];

	public Board(int n){
		if(n<8) n=8;
		this.matrix = new byte[n][n];
	}

	public Board(int n, byte matrix[][]){
		this.n = n;
		this.matrix = matrix;
	}

	//Hace un movimiento en el tablero actual y regresa un
	//valor booleano indicando si tuvo exito la operacion
	public boolean move(Direction dir){
		//TODO
		return false;
	}
}