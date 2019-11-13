package cgg.scene;

import cgg.scene.rays.Ray;
import cgtools.Direction;
import cgtools.Point;

public class LochKamera {
	public final Point x0;
	public final int width;
	public final int height;
	public final double phi;
	public final double tMin;
	public final double tMax;
	
	public LochKamera(int width, int height, double phi, Point x0, double tMin, double tMax) {	
		this.width = width;
		this.height = height;
		this.phi = phi;
		this.x0 = x0;
		this.tMin = tMin;
		this.tMax = tMax;
	}
	
	public Ray getRayThroughPoint(double xp, double yp) {
		
		double a = xp - width/2;
		double b = height/2 - yp;
		double c = -((height/2)/Math.tan(phi/2));
		
		Direction d = Direction.normalize(Direction.direction(a, b, c));
		
		return new Ray(x0, d, tMin, tMax);
	}
}
