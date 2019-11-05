package cgg.scene.rays;

import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;

public class Hit {
	
	public final double t;
	public final Point x; //position des treffers
	public final Direction n;
	public final Color c;
	
	public Hit(double t, Point x, Direction n, Color c) {
		this.t = t;
		this.x = x;
		this.n = n;
		this.c = c;
	}
	
	@Override
	public String toString() {
		return String.format("%.2f, %s, %s", t, x, n);
	}
	
}
