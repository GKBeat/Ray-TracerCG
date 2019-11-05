package cgg.scene.shapes;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Vector;

public class Plane implements Shape {
	protected double d; // alles größer als das ist kein hit für die plane
	protected Point p; // ankerpunkt
	protected Direction n;
	protected Color color;

	public Plane(double d, Point p, Direction n, Color color) {
		this.d = d;
		this.p = p;
		this.n = n;
		this.color = color;
	}

	@Override
	public Hit intersect(Ray r) {
		double hitOrNoHit = Direction.dotProduct(r.d, n);

		if (hitOrNoHit == 0) {
			return null;
		} else {
			double a = Vector.dotProduct(p, n);
			double b = Vector.dotProduct(r.x0, n);

			double t = (a - b) / hitOrNoHit;

			if (t > r.tMin && t < r.tMax) {
				Point hit = r.pointAt(t);
				if(Point.length(hit) > d) {
					return null;
				}
				return new Hit(t, hit, Vector.normalize(Direction.subtract(hit, p)), color);
			} else {
				return null;
			}
		}
	}

}
