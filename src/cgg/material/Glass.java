package cgg.material;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Random;
import cgtools.Sampler;
import cgtools.Util;

public class Glass implements Material {

	private double n1Init;
	private double n2Init;
	protected Sampler albedo;
	protected Color emission;
	
	public Glass(Sampler albedo) {
		n1Init = 1.0;
		n2Init = 1.5;
		this.albedo = albedo;
		emission = new Color(0, 0, 0);
	}
	

	@Override
	public Ray calculateNewRay(Hit hit, Ray ray) {
		Direction n = hit.n;
		Direction d = ray.d;
		double n1 = n1Init;
		double n2 = n2Init;
		
		if (Direction.dotProduct(n, d) > 0) {
			n = Direction.negate(n);
			n1 = n2Init;
			n2 = n1Init;
		}
		
		Hit reflectedHit = new Hit(hit.t, hit.x, n, hit.texturenPoint, hit.material);
		double r = n1 / n2;
		double c = Direction.dotProduct(Direction.negate(n), d);
		
		double unterWurzel = 1 - Math.pow(r, 2) * (1 - Math.pow(c, 2));
		
		if (unterWurzel > 0) {

			double r0 = Math.pow(((n1 - n2) / (n1 + n2)), 2);
			double schlick = r0 + (1 - r0) * Math.pow((1 + Direction.dotProduct(n, d)), 5);
			
			if (Random.random() > schlick) {
				
				Direction dT = Direction.add(Direction.multiply(r, d), Direction.multiply(r*c - Math.sqrt(unterWurzel), n));
				return new Ray(hit.x, Direction.normalize(dT), Util.EPSILON, Double.POSITIVE_INFINITY);
			} else {
				
				Direction reflected = Direction.subtract(d, Direction.multiply(2*Direction.dotProduct(reflectedHit.n, d), reflectedHit.n)); 
				return new Ray(reflectedHit.x, Direction.normalize(reflected), Util.EPSILON, Double.POSITIVE_INFINITY);
			}
		} else {
			
			Direction reflected = Direction.subtract(d, Direction.multiply(2*Direction.dotProduct(reflectedHit.n, d), reflectedHit.n)); 
			return new Ray(reflectedHit.x, Direction.normalize(reflected), Util.EPSILON, Double.POSITIVE_INFINITY);
		}
	}
	
	@Override
	public boolean isAbsorbt() {
		return false;
	}

	@Override
	public boolean isScattered() {
		return true;
	}

	@Override
	public boolean isRefracted() {
		return false;
	}

	@Override
	public Color getAlbedo(double x, double y) {
		return albedo.getColor(x, y);
	}

	@Override
	public Color getEmission(double x, double y) {
		return emission;
	}

}
