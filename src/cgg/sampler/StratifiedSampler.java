package cgg.sampler;

import cgtools.Color;
import cgtools.Random;
import cgtools.Sampler;

public class StratifiedSampler implements Sampler{
	protected Sampler sampler;
	protected int samplesPerPixel;
	
	
	public StratifiedSampler(Sampler sampler, int samplesPerPixel) {
		this.sampler = sampler;
		this.samplesPerPixel = samplesPerPixel;		
	}
	

	@Override
	public Color getColor(double x, double y) {
		Color color = new Color(0, 0, 0);
    	int samplesPerRowColumn = (int) Math.sqrt(samplesPerPixel);
    	
    	for(int xPixel = 0; xPixel < samplesPerRowColumn; xPixel++) {
    		for(int yPixel = 0; yPixel < samplesPerRowColumn; yPixel++) {
    		
    			double randomX = Random.random();
    			double randomY = Random.random();
    			
    			double xSample = x + (xPixel + randomX)/samplesPerRowColumn;
    			double ySample = y + (yPixel + randomY)/samplesPerRowColumn;
    			
	    		color = Color.add(color, sampler.getColor(xSample, ySample));
    		}
    	}	
    	return Color.divide(color, samplesPerPixel);
	}

}
