package cgg.scene.rays;

import cgg.material.Material;
import cgtools.Direction;
import cgtools.Point;

public class Hit {
	
	public final double t;
	public final Point x; //position des treffers
	public final Direction n;
	public final Material material;
	
	
	public Hit(double t, Point x, Direction n, Material material) {
		this.t = t;
		this.x = x;
		this.n = n;
		this.material = material;
	}
	
	@Override
	public String toString() {
		return String.format("%.2f, %s, %s", t, x, n);
	}
	
}
