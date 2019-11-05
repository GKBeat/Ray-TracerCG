package cgg.sampler;

import cgg.scene.LochKamera;
import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgg.scene.shapes.Shape;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Sampler;
import cgtools.Util;

public class Raytracer implements Sampler{
	
	protected LochKamera cam;
	protected Shape shape;
	
	public Raytracer(LochKamera cam, Shape shape) {
		this.cam = cam;
		this.shape = shape;
	}
	
	public Color getColor(double x, double y) {
		
		Direction d = cam.getDirectionThroughPoint(x, y);
		Ray ray = new Ray(Point.zero, d, 0, Double.POSITIVE_INFINITY);
		Hit hit = shape.intersect(ray);
		
		return Util.shade(hit.n, hit.c);
		
	}

}
