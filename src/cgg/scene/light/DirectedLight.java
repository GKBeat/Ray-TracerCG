package cgg.scene.light;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgg.scene.shapes.Shape;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Util;

public class DirectedLight implements Light{
	
	protected Direction dir;
	protected Color col;
	
	public DirectedLight(Direction dir, Color col) {
		this.dir = dir; 
		this.col = col;
	}

	@Override
	public Color light(Shape shape, Hit hit, Color albedo) {
		Ray tempR = new Ray(hit.x, Direction.normalize(dir), Util.EPSILON, Double.MAX_VALUE);
		Hit blocked = shape.intersect(tempR);
		double d = Math.max(Direction.dotProduct(Direction.normalize(dir), hit.n), 0);

		
		if(blocked != null) {
			return Color.multiply(albedo, Color.multiply(col, d));
		}else {
			return Color.black;
		}
	}

}
