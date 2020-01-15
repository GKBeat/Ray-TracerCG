package cgg.scene.shapes;

import cgg.material.Material;
import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Direction;
import cgtools.Point;

public class Background implements Shape{
	
	protected Material material;
	
	public Background(Material material) {
		this.material = material;
	}
	
	@Override
	public Hit intersect(Ray r) {
		
		double t = Double.MAX_VALUE;
		
		Point p = r.pointAt(t);
		
		Direction normal = r.d;
		double inclination = Math.acos(normal.y);
	    double azimuth = Math.PI + Math.atan2(normal.x, normal.z);
	    double u = azimuth / (2 * Math.PI);
	    double v = inclination / Math.PI;
		
		return new Hit(t, p, Point.negate(normal), Point.point(u, v, 0), material);
	}

}
