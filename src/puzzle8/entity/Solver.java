package puzzle8.entity;

import java.util.ArrayList;

public interface Solver{
	public ArrayList<Direction> solve(Board begin, Board end, int limit);
}
