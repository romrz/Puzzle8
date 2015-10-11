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

	public boolean left(){
		//TODO
		return false;
	}

	public boolean right(){
		//TODO
		return false;
	}

	public boolean up(){
		//TODO
		return false;
	}

	public boolean down(){
		//TODO
		return false;
	}
}
