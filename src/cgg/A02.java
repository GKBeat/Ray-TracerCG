package cgg;

import java.util.Collections;
import java.util.LinkedList;

import cgg.another.Circle;
import cgg.another.ColorChecker;
import cgg.another.RadCompare;
import cgtools.Color;
import cgtools.Random;

public class A02 {

	public static void main(String[] args) {
		final int width = 800;
		final int height = 450;
		final String filename = "doc/a02-discs.png";
		final String filename1 = "doc/a02-supersampling.png";

		Image image = new Image(width, height);
		LinkedList<Circle> circleList = new LinkedList<Circle>();

		for (int i = 0; i < 250; i++) {

			Color randomColor = new Color(Random.random(), Random.random(), Random.random());
			int randomRadius = (int) (Random.random() * 105);
			int randomX = (int) (Random.random() * width);
			int randomY = (int) (Random.random() * height);

			circleList.add(new Circle(randomColor, randomRadius, randomX, randomY));
		}

		Collections.sort(circleList, new RadCompare());
		
		ColorChecker ccL = new ColorChecker(circleList);
//--------------------------------------------------------------------------------------------------------------------------

		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				image.setPixel(x, y, ccL.pixelColor(x, y));
			}
		}

		image.write(filename);
		System.out.println("Wrote image: " + filename);

//--------------------------------------------------------------------------------------------------------------------------  
		
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				image.setPixel(x, y, stratifiedSampling(x, y, 100, ccL));
			}
		}

		image.write(filename1);
		System.out.println("Wrote image: " + filename1);
	}
	
//--------------------------------------------------------------------------------------------------------------------------
	
    public static Color stratifiedSampling(int x, int y, int samplesPerPixel, ColorChecker cc) {
    	double r = 0;
    	double g = 0;
    	double b = 0;
    	
    	int samplesPerRowColumn = (int) Math.sqrt(samplesPerPixel);
    	
    	for(int xPixel = 0; xPixel < samplesPerRowColumn; xPixel++) {
    		for(int yPixel = 0; yPixel < samplesPerRowColumn; yPixel++) {
    		
    			double randomX = Random.random();
    			double randomY = Random.random();
    			
    			double xSample = x + (xPixel + randomX)/samplesPerRowColumn;
    			double ySample = y + (yPixel + randomY)/samplesPerRowColumn;
    			
    			Color color = cc.pixelColor(xSample, ySample);
    			
    			r = r + color.r;
    			g = g + color.g;
    			b = b + color.b;	 
    		}
    	}	
    	return new Color(r/samplesPerPixel, g/samplesPerPixel, b/samplesPerPixel);
    }
}
