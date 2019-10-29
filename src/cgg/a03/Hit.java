package cgg.a03;

import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;

public class Hit {
	
	protected double t;
	protected Point x; //position des treffers
	protected Direction n;
	protected Color c;
	
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
