package cgg.sampler;

import java.util.concurrent.Callable;

import cgtools.Color;
import cgtools.Sampler;

public class MThreading implements Callable<Color>{
	public final int x;
	public final int y;
	public final Sampler sampler;
	
	public MThreading(int x, int y, Sampler sampler) {
		this.x = x; 
		this.y = y;
		this.sampler = sampler;
	}

	@Override
	public Color call() throws Exception {
		return sampler.getColor(x,y);
	}

}
