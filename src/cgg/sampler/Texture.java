package cgg.sampler;

import java.io.IOException;

import cgtools.Color;
import cgtools.ImageTexture;
import cgtools.Sampler;

public class Texture implements Sampler{
	
	public final ImageTexture texture;
	
	public Texture(String filename) {
		texture = ioHandlerImageTexture(filename);
	}

	@Override
	public Color getColor(double x, double y) {
		return texture.getColor(x, y);
	}
	
	private ImageTexture ioHandlerImageTexture(String filename) {
		try {
			ImageTexture tmp = new ImageTexture(filename);
			return tmp;
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
