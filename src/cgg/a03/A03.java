package cgg.a03;

import cgg.Image;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Random;
import cgtools.Util;

public class A03 {

	public static void main(String[] args) {
		final int width = 800;
		final int height = 450;
		final String filename = "doc/a03-one-sphere.png";
		
		Image image = new Image(width, height);
		LochKamera cam = new LochKamera(width, height, Math.PI/2);
		KugelSurface kugel = new KugelSurface(Point.point(0, 0, -3), 1, new Color(1, 0., 0.3));
		
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				image.setPixel(x, y, stratifiedSampling(x, y, 100, cam, kugel));
			}
		}

		image.write(filename);
		System.out.println("Wrote image: " + filename);
	}

	public static Color stratifiedSampling(int x, int y, int samplesPerPixel, LochKamera cam, KugelSurface kugel) {
    	double r = 0;
    	double g = 0;
    	double b = 0;
    	Color color;
    	int samplesPerRowColumn = (int) Math.sqrt(samplesPerPixel);
    	
    	for(int xPixel = 0; xPixel < samplesPerRowColumn; xPixel++) {
    		for(int yPixel = 0; yPixel < samplesPerRowColumn; yPixel++) {
    		
    			double randomX = Random.random();
    			double randomY = Random.random();
    			
    			double xSample = x + (xPixel + randomX)/samplesPerRowColumn;
    			double ySample = y + (yPixel + randomY)/samplesPerRowColumn;
    			
				Direction d = cam.getDirectionThroughPoint(xSample, ySample);
				Ray ray = new Ray(Point.point(0, 0, 0), d, 0, Double.MAX_VALUE);
				Hit hit = kugel.intersect(ray);
				
				if (hit == null) {
					color = Color.black;
				}else {
					color = Util.shade(hit.n, hit.c);
				}
	    		
	    		r = r + color.r;
	    		g = g + color.g;
	    		b = b + color.b;	
    		}
    	}	
    	return new Color(r/samplesPerPixel, g/samplesPerPixel, b/samplesPerPixel);
    }

}
