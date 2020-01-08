package cgg.scene.rays;

import cgtools.Direction;
import cgtools.Matrix;
import cgtools.Point;

public class Transformation {
	public final Matrix fromWorld;
	public final Matrix toWorld;
	public final Matrix toWorldN;
	
	public Transformation(Matrix a) {
		toWorld = a;
		fromWorld = Matrix.invert(a);
		toWorldN = Matrix.transpose(Matrix.invert(a));
	}
	
	public Ray transformRayFromWorld(Ray r) {
		Point newX0 = Matrix.multiply(fromWorld, r.x0);
		Direction newd = Matrix.multiply(fromWorld, r.d);
		
		return new Ray(newX0, newd, r.tMin, r.tMax);
	}
	
	public Hit transformHitToWorld(Hit hit) {
		Point newX = Matrix.multiply(toWorld, hit.x);
		Direction newN = Matrix.multiply(toWorldN, hit.n);
		Point newP = Matrix.multiply(toWorld, hit.texturenPoint);
		
		return new Hit(hit.t, newX, newN, newP, hit.material);
	}

}
