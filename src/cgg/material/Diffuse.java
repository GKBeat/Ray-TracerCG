package cgg.material;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Random;
import cgtools.Util;

public class Diffuse implements Material{
	
	protected Color albedo;
	protected Color emission;
	
	public Diffuse(Color albedo) {
		this.albedo = albedo;
		emission = new Color(0, 0, 0);
	}

	@Override
	public Ray calculateNewRay(Hit hit, Ray ray) {
		double x = Random.random() * 2 - 1;
		double y = Random.random() * 2 - 1;
		double z = Random.random() * 2 - 1;

		while ((x * x) + (y * y) + (z * z) >= 1) {
			x = Random.random() * 2 - 1;
			y = Random.random() * 2 - 1;
			z = Random.random() * 2 - 1;
		}

		Direction randomDir = Direction.direction(x, y, z);
		Direction d = Direction.normalize(Direction.add(hit.n, randomDir));
		return new Ray(hit.x, d, Util.EPSILON, Double.POSITIVE_INFINITY);
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
