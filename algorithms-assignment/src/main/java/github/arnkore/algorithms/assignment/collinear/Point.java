/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/
package github.arnkore.algorithms.assignment.collinear;

import java.util.Comparator;

import edu.princeton.cs.algs4.stdlib.StdDraw;

public class Point implements Comparable<Point> {
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder(); // compare points by slope
    private final int x; // x coordinate
    private final int y; // y coordinate
    
    private class SlopeOrder implements Comparator<Point> {
		public int compare(Point o1, Point o2) {
			double slopeToP1 = Point.this.slopeTo(o1);
			double slopeToP2 = Point.this.slopeTo(o2);
			if (slopeToP1 < slopeToP2) return -1;
			else if (slopeToP1 > slopeToP2) return 1;
			else return 0;
		}
    }

    /**
     * create the point (x, y)
     * @param x
     * @param y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * plot this point to standard drawing
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * draw line between this point and that point to standard drawing
     * @param that
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * slope between this point and that point
     * @param that
     * @return
     */
    public double slopeTo(Point that) {
    	if (this.x == that.x) return this.y == that.y ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    	if (this.y == that.y) return (this.y - that.y) / Math.abs((this.x - that.x));
    	return (this.y - that.y) / (double)(this.x - that.x);
    }

    /**
     * is this point lexicographically smaller than that one?
     * comparing y-coordinates and breaking ties by x-coordinates
     */
    public int compareTo(Point that) {
    	if (this.y < that.y) return -1;
    	if (this.y > that.y) return 1;
    	if (this.x < that.x) return -1;
    	if (this.x > that.x) return 1;
    	return 0;
    }

    /**
     * return string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
