package cgg.scene;

import cgg.scene.rays.Ray;
import cgtools.Direction;
import cgtools.Matrix;
import cgtools.Point;

public class LochKamera {
	public final int width;
	public final int height;
	public final double phi;
	private Point x0;
	public final Matrix transformation;
	public final double tMin;
	public final double tMax;
	
	public LochKamera(int width, int height, double phi, Matrix transformation, double tMin, double tMax) {	
		this.width = width;
		this.height = height;
		this.phi = phi;
		x0 = Matrix.multiply( transformation, Point.zero);
		this.tMin = tMin;
		this.tMax = tMax;
		this.transformation = transformation;
	}
	
	public Ray getRayThroughPoint(double xp, double yp) {
		double a = xp - width/2;
		double b = height/2 - yp;
		double c = -((height/2)/Math.tan(phi/2));
		
		Direction d = Direction.normalize(Direction.direction(a, b, c));
		Direction newD = Matrix.multiply( transformation, d);
		
		return new Ray(x0, newD, tMin, tMax);
	}
}
