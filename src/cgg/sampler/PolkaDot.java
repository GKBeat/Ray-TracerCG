package cgg.sampler;

import cgtools.Color;
import cgtools.Sampler;

public class PolkaDot implements Sampler {
	protected Color backgroundCol;
	protected Color mainCol;
	protected double radius;

	public PolkaDot(Color backgroundCol, Color mainCol) {
		this.backgroundCol = backgroundCol;
		this.mainCol = mainCol;
		radius = 0.25;
	}

	public Color getColor(double x, double y) {
		double ui = x % 1f;
		double vi = y % 1f;
		
		if(isPointInCircle(ui, vi, 0.25)|| isPointInCircle(ui, vi, 0.75)) {
			return mainCol;
		}else {
			return backgroundCol;
		}
	}
	
	public boolean isPointInCircle(double x, double y, double mitte) {
		if(Math.pow(x-mitte, 2)+Math.pow(y-mitte, 2) < Math.pow(radius, 2)) {
			return true;
		}else {
			return false;
		}
	}

}
