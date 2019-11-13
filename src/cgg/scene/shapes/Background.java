package cgg.scene.shapes;

import cgg.material.Material;
import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
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
		
		return new Hit(t, p, r.d, material);
	}

}
