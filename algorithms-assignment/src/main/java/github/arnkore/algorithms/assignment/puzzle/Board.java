package github.arnkore.algorithms.assignment.puzzle;

import edu.princeton.cs.algs4.algs.Queue;
import edu.princeton.cs.algs4.stdlib.In;

/**
 * board in a search Node
 * @author arnkore
 *
 */
public class Board {
	private int[][] blocks;
	private int N;
	
	/**
	 * construct a board from an N-by-N array of blocks
	 * (where blocks[i][j] = block in row i, column j)
	 * @param blocks
	 */
    public Board(int[][] blocks) {
    	this.N = blocks.length;
    	this.blocks = new int[N][N];
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			this.blocks[i][j] = blocks[i][j];
    		}
    	}
    }
    
    /**
     * board dimension N
     * @return
     */
    public int dimension() {
    	return N;
    }
    
    /**
     * number of blocks out of place
     * @return
     */
    public int hamming() {
    	int num = 0;
    	
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (blocks[i][j] != 0) {
    				if (row(blocks[i][j]) != i || column(blocks[i][j]) != j) {
    					num++;
    				}
    			}
    		}
    	}
    	
    	return num;
    }
    
    /**
     * sum of Manhattan distances between blocks and goal
     * @return
     */
    public int manhattan() {
    	int distance = 0;
    	
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (blocks[i][j] != 0) {
    				distance += Math.abs((row(blocks[i][j]) - i)) + Math.abs((column(blocks[i][j]) - j));
    			}
    		}
    	}
    	
    	return distance;
    }
    
    /**
     * is this board the goal board?
     * @return
     */
    public boolean isGoal() {
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (i == N - 1 && j == N - 1) {
    				if (blocks[i][j] != 0) return false;
    			} else {
    				if (blocks[i][j] != i * N + j + 1) return false;
    			}
    		}
    	}
    	
    	return true;
    }
    
    /**
     * a board that is obtained by exchanging two adjacent blocks in the same row
     * @return
     */
    public Board twin() {
    	int[][] copyBlocks = copyBlocks();
    	
    	if (copyBlocks[0][0] != 0 && copyBlocks[0][1] != 0) {
    		swap(copyBlocks, 0, 0, 0, 1);
    	} else {
    		swap(copyBlocks, 1, 0, 1, 1);
    	}
    	
    	return new Board(copyBlocks);
    }
    
	/**
     * does this board equal y?
     */
    public boolean equals(Object y) {
    	if (this == y) return true;
    	if (y == null) return false;
    	if (y.getClass() != this.getClass()) return false;
    	
    	Board other = (Board)y;
    	if (N != other.N) return false;
    	
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (this.blocks[i][j] != other.blocks[i][j]) return false;
    		}
    	}
    	
    	return true;
    }
    
    /**
     * all neighboring boards
     * @return
     */
    public Iterable<Board> neighbors() {
    	Queue<Board> queue = new Queue<Board>();
    	int blankRow = N - 1, blankCol = N - 1;
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (blocks[i][j] == 0) {
    				blankRow = i;
    				blankCol = j;
    			}
    		}
    	}
    	
    	for (int dirt = Direction.UP.ordinal(); dirt <= Direction.LEFT.ordinal(); dirt++) {
    		int row = blankRow, col = blankCol;
    		if (dirt == Direction.UP.ordinal()) row--;
    		else if (dirt == Direction.RIGHT.ordinal()) col++;
    		else if (dirt == Direction.DOWN.ordinal()) row++;
    		else if (dirt == Direction.LEFT.ordinal()) col--;
    		
    		if (row >= 0 && row < N && col >= 0 && col < N) {
    			int[][] copyBlocks = copyBlocks();
    			swap(copyBlocks, blankRow, blankCol, row, col);
    			queue.enqueue(new Board(copyBlocks));
    		}
    	}
    	
    	return queue;
    }
    
    /**
     * string representation of this board (in the output format specified below)
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(N + "\n");
    	
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			sb.append(String.format("%2d ", blocks[i][j]));
    		}
    		sb.append("\n");
    	}
    	
    	return sb.toString();
    }
    
    private enum Direction {
    	UP,
    	RIGHT,
    	DOWN,
    	LEFT
    }
    
    private int[][] copyBlocks() {
    	int[][] copyBlocks = new int[N][N];
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			copyBlocks[i][j] = blocks[i][j];
    		}
    	}
    	
    	return copyBlocks;
    }
    
    private void swap(int[][] blocks, int i1, int j1, int i2, int j2) {
    	int tmp = blocks[i1][j1];
    	blocks[i1][j1] = blocks[i2][j2];
    	blocks[i2][j2] = tmp;
	}
    
    private int row(int grid) {
    	if (grid <= 0 || grid >= N * N) {
    		throw new IllegalArgumentException();
    	}
    	
    	return (grid - 1) / N;
    }
    
    private int column(int grid) {
    	if (grid <= 0 || grid >= N * N) {
    		throw new IllegalArgumentException();
    	}
    	
    	return (grid - 1) % N;
    }

    /**
     * unit tests
     * @param args
     */
    public static void main(String[] args) {
    	In in = new In(args[0]);
    	int N = in.readInt();
    	int[][] blocks = new int[N][N];
    	
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			blocks[i][j] = in.readInt();
    		}
    	}
    	
    	Board board = new Board(blocks);
    	System.out.println(board.isGoal());
    	System.out.println(board.dimension());
    	System.out.println(board.hamming());
    	System.out.println(board.manhattan());
    	System.out.println(board);
    	System.out.println(board.twin());
    	
    	for (Board nb : board.neighbors()) {
    		System.out.println(nb);
    	}
    }
}