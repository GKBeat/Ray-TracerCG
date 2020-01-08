package cgg.sampler;

import cgtools.Color;
import cgtools.Matrix;
import cgtools.Point;
import cgtools.Sampler;
import cgtools.Vector;

public class TransformedTexture implements Sampler{
	protected Sampler texture;
    protected Matrix transform;

    public TransformedTexture(Sampler texture, Matrix transform) {
        this.texture = texture;
        this.transform = transform;
    }

    public Color getColor(double x, double y) {
    	Vector transformed = Matrix.multiply(transform, Point.point(x, y, 0));
    	return texture.getColor(transformed.x, transformed.y);
    }

}
