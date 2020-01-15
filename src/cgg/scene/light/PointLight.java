package cgg.scene.light;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgg.scene.shapes.Shape;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Util;

public class PointLight implements Light{
	
	protected Point x0;
	protected Color col;
	
	public PointLight(Point x0, Color col) {
		this.x0 = x0;
		this.col = col;
	}

	@Override
	public Color light(Shape shape, Hit hit, Color albedo) {
		double tMax = Point.length(Point.subtract(x0, hit.x));
		Ray tempR = new Ray(hit.x, Direction.normalize(Direction.subtract(x0, hit.x)), Util.EPSILON, tMax);
		Hit blocked = shape.intersect(tempR);
		double d = Math.max(Point.dotProduct(Point.normalize(Point.subtract(x0, hit.x)),hit.n), 0);
		
		if(blocked != null) {
			return Color.multiply(albedo, Color.multiply(Color.multiply(col, 1/Math.pow(tMax, 2)), d));
		}else {
			return Color.black;
		}
	}

}
