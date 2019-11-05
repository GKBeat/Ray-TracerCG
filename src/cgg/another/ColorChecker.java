package cgg.another;

import java.util.List;

import cgtools.Color;

public class ColorChecker {
	List<Circle> circles;

	public ColorChecker(List<Circle> circles) {
		this.circles = circles;
	}

	public Color pixelColor(double x, double y) {
		Color c = Color.white;

		for (Circle circle : circles) {

			if (circle.isPointInCircle(x, y)) {
				c = circle.color;
			}
		}
		return c;
	}
}
