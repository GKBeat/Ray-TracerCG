package cgg.material;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Color;

public class BackgroundMaterial implements Material {

	protected Color albedo;
	protected Color emission;

	public BackgroundMaterial(Color emission) {
		albedo = Color.black;
		this.emission = emission;
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
		// TODO Auto-generated method stub
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
