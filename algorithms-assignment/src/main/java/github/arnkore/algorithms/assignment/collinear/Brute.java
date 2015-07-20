package github.arnkore.algorithms.assignment.collinear;

import edu.princeton.cs.algs4.algs.Quick;
import edu.princeton.cs.algs4.stdlib.In;
import edu.princeton.cs.algs4.stdlib.StdDraw;

public class Brute {
	public static void main(String[] args) {
		final int MIN_POINTS = 4;
		In in = new In(args[0]);
		int N = in.readInt();
		Point[] points = new Point[N];
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		for (int i = 0; i < N; i++) {
			points[i] = new Point(in.readInt(), in.readInt());
			points[i].draw();
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				for (int m = j + 1; m < N; m++) {
					for (int n = m + 1; n < N; n++) {
						Point p = points[i], q = points[j], r = points[m], s = points[n];
						double slope1 = p.slopeTo(q), slope2 = p.slopeTo(r), slope3 = p.slopeTo(s);
						
						if (slope1 == slope2 && slope2 == slope3) {
							Point[] ls = new Point[MIN_POINTS];
							ls[0] = p;
							ls[1] = q;
							ls[2] = r;
							ls[3] = s;
							Quick.sort(ls);
							for (int ix = 0; ix < MIN_POINTS; ix++) {
								if (ix == MIN_POINTS - 1) {
									ls[0].drawTo(ls[MIN_POINTS - 1]);
									System.out.println(ls[ix]);
								} else {
									System.out.print(ls[ix] + " -> ");
								}
							}
						}
					}
				}
			}
		}
	}
}
