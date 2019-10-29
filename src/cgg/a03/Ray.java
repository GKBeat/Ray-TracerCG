package cgg.a03;

import cgtools.Direction;
import cgtools.Point;

public class Ray {
	protected Point x0;
	protected Direction d;
	protected double tMin;
	protected double tMax;
	
	
	public Ray(Point x0, Direction d, double tMin, double tMax) {
		this.x0 = x0;
		this.d = d;
		this.tMin = tMin;
		this.tMax = tMax;
		
	}
	
	public Point pointAt(double t) {
		
		Direction tD = Point.multiply(d, t); // t*d
		Point x = Point.add(x0, tD); // x0 + (t*d)
		
		return x;
	}

}
