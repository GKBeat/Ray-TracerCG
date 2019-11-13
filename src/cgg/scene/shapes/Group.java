package cgg.scene.shapes;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Direction;
import cgtools.Point;

public class Group implements Shape{
	protected Shape[] shapes;
	
	public Group(Shape[] shapes){
		this.shapes = shapes;
	}

	@Override
	public Hit intersect(Ray r) {
		double t = Double.POSITIVE_INFINITY;
		Point x = Point.point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		Direction n =  Direction.direction(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		
		Hit shortestHit = new Hit(t, x, n, null);
		for(Shape s : shapes) {
			Hit tmp = s.intersect(r);
			if(tmp == null) {
				continue;
			}
			if(tmp.t <= shortestHit.t) {
				shortestHit = tmp;
			}
		}
		
		return shortestHit;
	}
}
