package puzzle8.entity;

import java.util.Comparator;

public class BoardComparator implements Comparator<Board> {

    public int compare(Board b1, Board b2) {
        int diff = b1.getPathCost() - b2.getPathCost();
        if(diff < 0) return -1;
        else if(diff == 0) return 0;
        else return 1;
    }
    
}
