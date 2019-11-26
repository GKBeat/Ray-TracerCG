package cgg.material;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Random;
import cgtools.Util;

public class Metall implements Material{
	
	protected Color albedo;
	protected Color emission;
	protected double rauigkeit;
	
	public Metall(Color albedo, double rauigkeit) {
		this.albedo = albedo;
		emission = new Color(0, 0, 0);
		this.rauigkeit = rauigkeit;
	}

	@Override
	public Ray calculateNewRay(Hit hit, Ray ray) {
		
		Double b = Direction.dotProduct(Direction.negate(ray.d), hit.n);
		
		Direction bVEC = Direction.multiply(b, hit.n);
		Direction r = Direction.add(Direction.multiply(2, bVEC), ray.d);
		Direction randomDir = Direction.direction(Random.random(), Random.random(), Random.random());
			
		Direction rs = Direction.add(r, Direction.multiply(rauigkeit, randomDir));
//		while(Direction.dotProduct(rs, hit.n) <= 0) {
//			randomDir = Direction.direction(Random.random(), Random.random(), Random.random());
//			rs = Direction.add(r, Direction.multiply(rauigkeit, randomDir));
//		}
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
	public Color getAlbedo() {
		return albedo;
	}

	@Override
	public Color getEmission() {
		return emission;
	}

}
