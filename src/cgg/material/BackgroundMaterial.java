package cgg.material;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Color;
import cgtools.Sampler;

public class BackgroundMaterial implements Material {

	protected Color albedo;
	protected Sampler emission;

	public BackgroundMaterial(Sampler sampler) {
		this.albedo = Color.black;
		emission = sampler;
	}

	@Override
	public Ray calculateNewRay(Hit hit, Ray ray) {
		return null;
	}

	@Override
	public boolean isAbsorbt() {
		return true;
	}

	@Override
	public boolean isScattered() {
		return false;
	}

	@Override
	public boolean isRefracted() {
		return false;
	}

	@Override
	public Color getAlbedo(double x, double y) {
		return albedo;
	}

	@Override
	public Color getEmission(double x, double y) {
		return emission.getColor(x, y);
	}

}
