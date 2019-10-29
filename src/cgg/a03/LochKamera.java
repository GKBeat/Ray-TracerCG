package cgg.a03;

import cgtools.Direction;
import cgtools.Point;

public class LochKamera {
	protected Point x0;
	protected int width;
	protected int height;
	protected double phi;
	
	public LochKamera(int width, int height, double phi) {
		x0 = Point.point(0,0,0);
		this.width = width;
		this.height = height;
		this.phi = phi;
	}
	
	public Direction getDirectionThroughPoint(double xp, double yp) {
		
		double a = xp - width/2;
		double b = height/2 - yp;
		double c = -((width/2)/Math.tan(phi/2));
		
		Direction d = Direction.direction(a, b, c);
		
		return Direction.normalize(d);
	}
}
