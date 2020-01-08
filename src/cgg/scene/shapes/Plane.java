package cgg.scene.shapes;

import cgg.material.Material;
import cgg.sampler.Texture;
import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Vector;

public class Plane implements Shape {
	
	protected double d; // alles größer als das ist kein hit für die plane
	public final Point p; // ankerpunkt
	protected Direction n;
	protected Material material;

	public Plane(double d, Point p, Direction n, Material material) {
		this.d = d;
		this.p = p;
		this.n = Direction.normalize(n);
		this.material = material;
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
				Direction HitToCenter = Direction.subtract(hit, p);
				double distanzeLenght = Direction.length(HitToCenter);
				if (d <= distanzeLenght) {
					return null;
				}
				
				double u = hit.x/d+0.5;
				double v = hit.z/d+0.5;
				return new Hit(t, hit, n, Point.point(u, v, 0), material);
			} else {
				return null;
			}
		}
	}
}
