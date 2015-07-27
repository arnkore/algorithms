package github.arnkore.algorithms.assignment.puzzle;

import java.util.Comparator;

import edu.princeton.cs.algs4.algs.MinPQ;
import edu.princeton.cs.algs4.algs.Stack;
import edu.princeton.cs.algs4.stdlib.In;
import edu.princeton.cs.algs4.stdlib.StdOut;

/**
 * 8-puzzle problem solver
 * @author arnkore
 *
 */
public class Solver {
	private SearchNode goal; // goal searchNode
	private final Comparator<SearchNode> MAHATTAN_COMPARATOR = new ManhattanComparator();
	
	private class ManhattanComparator implements Comparator<SearchNode>  {
		public int compare(SearchNode v, SearchNode w) {
			int cmp = (v.board.manhattan() + v.moves) - (w.board.manhattan() + w.moves);
			if (cmp < 0) return -1;
			else if (cmp > 0) return 1;
			else return 0;
		}
	}
	
	private class SearchNode {
		private SearchNode prev;
		private Board board;
		private int moves;
		
		public SearchNode(SearchNode prev, Board board, int moves) {
			this.prev = prev;
			this.board = board;
			this.moves = moves;
		}
	}
	
	/**
	 * find a solution to the initial board (using the A* algorithm)
	 * @param initial
	 */
    public Solver(Board initial) {
    	if (initial == null) {
    		throw new NullPointerException("illegal argument");
    	}
    	MinPQ<SearchNode> origPQ = new MinPQ<SearchNode>(MAHATTAN_COMPARATOR);
    	origPQ.insert(new SearchNode(null, initial, 0));
    	MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>(MAHATTAN_COMPARATOR);
    	twinPQ.insert(new SearchNode(null, initial.twin(), 0));
    	
    	while (true) {
    		SearchNode searchNode1 = move(origPQ);
    		if (searchNode1.board.isGoal()) {
    			goal = searchNode1;
    			break;
    		}
    		
    		SearchNode searchNode2 = move(twinPQ);
    		if (searchNode2.board.isGoal()) {
    			break;
    		}
    	}
    }
    
    private SearchNode move(MinPQ<SearchNode> pq) {
    	SearchNode cur = pq.delMin();
    	
		for (Board neighborBoard : cur.board.neighbors()) {
			if (cur.prev == null || !neighborBoard.equals(cur.prev.board)) {
				pq.insert(new SearchNode(cur, neighborBoard, cur.moves + 1));
			}
		}
    	
    	return cur;
	}

	/**
     * is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
    	return goal != null;
    }
    
    /**
     * min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves() {
    	return goal != null ? goal.moves : -1;
    }
    
    /**
     * sequence of boards in a shortest solution; null if unsolvable
     * @return
     */
    public Iterable<Board> solution() {
    	if (goal == null) {
    		return null;
    	}
    	
    	Stack<Board> stack = new Stack<Board>();
    	for(SearchNode searchNode = goal; searchNode != null; searchNode = searchNode.prev) {
    		stack.push(searchNode.board);
    	}
    	
    	return stack;
    }
    
    /**
     * solve a slider puzzle (given below)
     * @param args
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}