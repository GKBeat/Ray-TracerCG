package cgg.a02;

import cgtools.Color;

public class Circle {

	protected Color color;
	protected int radius;
	protected int x;
	protected int y;

	public Circle(Color color, int radius, int x, int y) {
		this.color = color;
		this.radius = radius;
		this.x = x;
		this.y = y;
	}

	public boolean isPointInCircle(double x, double y) {
		double a_2 = Math.pow((x - this.x), 2);
		double b_2 = Math.pow((y - this.y), 2);
		double c_2 = Math.pow(radius, 2);

		if (a_2 + b_2 < c_2) {
			return true;
		} else {
			return false;
		}
	}
}
