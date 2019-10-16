package cgg;

import cgtools.*;

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
        
        pixels[index+0] = color.r;
        pixels[index+1] = color.g;
        pixels[index+2] = color.b;
        
    }

    public void write(String filename) {
    	ImageWriter.write(filename, pixels, width, height);
    }
}
