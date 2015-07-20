package github.arnkore.algorithms.assignment.collinear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.algs.Quick;
import edu.princeton.cs.algs4.stdlib.In;
import edu.princeton.cs.algs4.stdlib.StdDraw;

public class Fast {
	private static final int MIN_POINTS_IN_LINE = 4;
	private Map<Double, List<Point>> map = new HashMap<Double, List<Point>>();
	
	public static void main(String[] args) {
		Fast fast = new Fast();
		fast.draw(args[0]);
	}
	
	private void draw(String inputFile) {
		In in = new In(inputFile);
		int N = in.readInt();
		Point[] points = new Point[N];
		Point[] cpoints = new Point[N];
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		for (int i = 0; i < N; i++) {
			points[i] = new Point(in.readInt(), in.readInt());
			points[i].draw();
		}
		
		for (int i = 0; i < N - 3; i++) {
			refreshCopyPoints(points, cpoints, i, N - 1);
			Arrays.sort(cpoints, i + 1, N, cpoints[i].SLOPE_ORDER);
			
			Point originPoint = cpoints[i];
			List<Point> pList = null;
			double lastSlope = Double.NEGATIVE_INFINITY;
			for (int j = i + 1; j <= N - 1; j++) {
				Point currentPoint = cpoints[j];
				double slope = originPoint.slopeTo(currentPoint);
				
				if (lastSlope != slope) { // when this slope isn't equal to last slope, check whether pList can be segment line. 
					if (pList != null && pList.size() >= MIN_POINTS_IN_LINE) {
						outputSegmentLine(originPoint, pList, lastSlope);
					}
					
					pList = new ArrayList<Point>();
					pList.add(originPoint);
				} 
				
				pList.add(currentPoint);
				lastSlope = slope;
				
				if (j == N - 1) { // when loop ended, check whether pList can be segment line.
					if (pList != null && pList.size() >= MIN_POINTS_IN_LINE) {
						outputSegmentLine(originPoint, pList, lastSlope);
					}
				}
			}
		}
	}
	
	/**
	 * copy specified region points back to temporary point array
	 * @param points
	 * @param copyPoints
	 * @param low
	 * @param high
	 */
	private void refreshCopyPoints(Point[] points, Point[] copyPoints, int low, int high) {
		for (int i = low; i <= high; i++) {
			copyPoints[i] = points[i];
		}
	}
	
	/**
	 * output and draw the specified segment line
	 * @param origin: origin point
	 * @param plist
	 * @param pSlope
	 */
	private void outputSegmentLine(Point origin, List<Point> plist, double pSlope) {
		boolean contains = false;
		List<Point> ps = null;
		
		if (map.containsKey(pSlope)) {
			ps = map.get(pSlope);
			for (Point p : ps) {
				if (origin.slopeTo(p) == pSlope) {
					contains = true;
					break;
				}
			}
		} else {
			ps = new ArrayList<Point>();
			map.put(pSlope, ps);
		}
		
		if (!contains) {
			ps.add(origin);
			Point[] outpoints = new Point[plist.size()];
			plist.toArray(outpoints);
			Quick.sort(outpoints);
			
			for (int k = 0; k < outpoints.length; k++) {
				if (k == outpoints.length - 1) {
					System.out.println(outpoints[k]);
					outpoints[0].drawTo(outpoints[outpoints.length - 1]);
				} else {
					System.out.print(outpoints[k] + " -> ");
				}
			}
		}
	}
}
