package cgg;

import cgtools.Color;
import cgtools.ImageWriter;
import cgtools.Sampler;

public class Image {
	
	private int width, height;
	private double[] pixels;
	
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        
        // width*height, damit alle pixel reinpassen und dann nochmal *3 damit platz f�r jeden pixel seine r, g, b werte passen 
        pixels = new double[(width*height)*3];
    }

    public void setPixel(int x, int y, Color color) {
    	
        int index = 3*((y * width) + x);
        
        pixels[index+0] = Math.pow(color.r, 1/2.2);
        pixels[index+1] = Math.pow(color.g, 1/2.2);
        pixels[index+2] = Math.pow(color.b, 1/2.2);
    }
    
    public void sample(Sampler sampler) {
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                setPixel(x, y, sampler.getColor(x, y));
            }
        }
    }

    public void write(String filename) {
    	ImageWriter.write(filename, pixels, width, height);
    }
}
