package cgg.scene.shapes;

import cgg.material.Material;
import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Vector;

public class KugelSurface implements Shape {
	protected Point c;
	protected double radius;
	protected Material material;

	public KugelSurface(Point c, double radius, Material material) {
		this.c = c;
		this.radius = radius;
		this.material = material;
	}

	public Hit intersect(Ray r) {
		Point p;

		Direction x0 = Point.subtract(r.x0, c);

		double a = Direction.squaredLength(r.d);
		double btest = (x0.x*r.d.x)+(x0.y*r.d.y)+(x0.z*r.d.z);
//		double b = 2 * (Vector.dotProduct(x0, r.d));
		double b = 2 * btest;
		double c = Point.squaredLength(x0) - Math.pow(radius, 2);

		double d = Math.pow(b, 2) - (4 * a * c);

		double t, t1, t2;

		if (d < 0) { // kein schnittpunkt
			return null;
		} else if (d == 0) { // 1 schnittpunkt
			t = (-b) / (2 * a);

			p = r.pointAt(t);
			return new Hit(t, p, Vector.normalize(Point.subtract(p, this.c)), material);
		} else { // 2 schnittpunkte

			t1 = (-b + Math.sqrt(d)) / (2 * a);
			t2 = (-b - Math.sqrt(d)) / (2 * a);

			if ((t2 < 0 || t1 < t2) && t1 >= r.tMin && t1 <= r.tMax) { //
				p = r.pointAt(t1);
				return new Hit(t1, p, Vector.normalize(Point.subtract(p, this.c)), material);

			} else if ((t1 < 0 || t2 < t1) && t2 >= r.tMin && t2 <= r.tMax) {
				p = r.pointAt(t2);
				return new Hit(t2, p, Vector.normalize(Point.subtract(p, this.c)), material);
			}
		}
		return null;
	}
}