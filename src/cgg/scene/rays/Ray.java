package cgg.scene.rays;

import cgtools.Direction;
import cgtools.Point;

public class Ray {
	public final Point x0;
	public final Direction d;
	public final double tMin;
	public final double tMax;
	
	
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
