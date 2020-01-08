package cgg.sampler;

import cgtools.Color;
import cgtools.Sampler;

public class CheckerBoard implements Sampler{
	protected Color backgroundCol;
	protected Color mainCol;
	
	public CheckerBoard(Color backgroundCol, Color mainCol) {
		this.backgroundCol = backgroundCol;
		this.mainCol = mainCol;
	}
	
	public Color getColor(double x ,double y) {
		double ui = (int)((x % 1) * 100);
	    double vi = (int)((y % 1) * 100);
	    if ((ui + vi) % 2 == 0)
	        return backgroundCol;
	    else
	        return mainCol;
	}
}
