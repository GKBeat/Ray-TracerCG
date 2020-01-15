package cgg.scene.shapes;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgg.scene.rays.Transformation;
import cgtools.Direction;
import cgtools.Matrix;
import cgtools.Point;

public class Group implements Shape{
	public final Shape[] shapes;
	public final Transformation transform;
	
	public Group(Shape[] shapes){
		this.shapes = shapes;
		transform = new Transformation(Matrix.identity); 
	}
	
	public Group(Shape[] shapes, Transformation transform){
		this.shapes = shapes;
		this.transform = transform;
	}

	@Override
	public Hit intersect(Ray r) {
		double t = Double.POSITIVE_INFINITY;
		Point x = Point.point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		Direction n = Direction.direction(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		
		r = transform.transformRayFromWorld(r);
		
		Hit shortestHit = new Hit(t, x, n, x ,null);
		for(Shape s : shapes) {
			Hit tmp = s.intersect(r);
			if(tmp == null) {
				continue;
			}
			if(tmp.t <= shortestHit.t) {
				shortestHit = tmp;
			}
		}
		
		shortestHit = transform.transformHitToWorld(shortestHit);
		return shortestHit;
	}
}
