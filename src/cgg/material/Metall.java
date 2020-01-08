package cgg.material;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Random;
import cgtools.Sampler;
import cgtools.Util;

public class Metall implements Material{
	
	protected Sampler albedo;
	protected Color emission;
	protected double rauigkeit;
	
	
	public Metall(Sampler albedo, double rauigkeit) {
		this.albedo = albedo;
		emission = new Color(0, 0, 0);
		this.rauigkeit = rauigkeit;
	}

	@Override
	public Ray calculateNewRay(Hit hit, Ray ray) {
		
		Double b = Direction.dotProduct(Direction.negate(ray.d), hit.n);
		Direction bVEC = Direction.multiply(b, hit.n);
		Direction r = Direction.add(Direction.multiply(2, bVEC), ray.d);
		
		if(rauigkeit == 0) {
			return new Ray(hit.x, Direction.normalize(r), Util.EPSILON, Double.POSITIVE_INFINITY); 
		}
		
		double x = Random.random() * 2 - 1;
		double y = Random.random() * 2 - 1;
		double z = Random.random() * 2 - 1;

		while ((x * x) + (y * y) + (z * z) >= 1) {
			x = Random.random() * 2 - 1;
			y = Random.random() * 2 - 1;
			z = Random.random() * 2 - 1;
		}
		
		Direction randomDir = Direction.direction(x, y, z);			
		Direction rs = Direction.add(r, Direction.multiply(rauigkeit, randomDir));
		
		while(Direction.dotProduct(rs, hit.n) <= 0) {
			return null;
		}

		return new Ray(hit.x, Direction.normalize(rs), Util.EPSILON, Double.POSITIVE_INFINITY);
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
