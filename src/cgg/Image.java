package cgg;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cgg.sampler.MThreading;
import cgtools.Color;
import cgtools.ImageWriter;
import cgtools.Sampler;

public class Image {
	
	private int width, height;
	private double[] pixels;
	
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        
        // width*height, damit alle pixel reinpassen und dann nochmal *3 damit platz für jeden pixel seine r, g, b werte passen 
        pixels = new double[(width*height)*3];
    }

    public void setPixel(int x, int y, Color color) {
    	
        int index = 3*((y * width) + x);
        
        pixels[index+0] = Math.pow(color.r, 1/2.2);
        pixels[index+1] = Math.pow(color.g, 1/2.2);
        pixels[index+2] = Math.pow(color.b, 1/2.2);
    }
    
    public void sample(Sampler sampler) {
    	long startTime = System.currentTimeMillis();
    	ExecutorService pool = Executors.newFixedThreadPool(3);
    	ArrayList<Future<Color>> pixels = new ArrayList<Future<Color>>(width*height);
    	
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	pixels.add(pool.submit(new MThreading(x, y, sampler)));
            }
        }
        
        int index = 0;
        for(int x = 0; x < width; x++) {
        	for(int y = 0; y < height; y++) {
        		try {
        			Color c = pixels.get(index).get();
        			if(c == null) {
        				continue;
        			}
					setPixel(x, y, c);
				} catch (InterruptedException | ExecutionException e) {
					System.err.println("tbh idk");
					e.printStackTrace();
				}
        		index++;
        	}
        }
        pool.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("Took " + (endTime - startTime)/ 1_000 + " s");
    }
    
//	public void sample(Sampler sampler) {
//		for (int x = 0; x != width; x++) {
//			for (int y = 0; y != height; y++) {
//				setPixel(x, y, sampler.getColor(x, y));
//			}
//		}
//	}

    public void write(String filename) {
    	ImageWriter.write(filename, pixels, width, height);
    }
}
