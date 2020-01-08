package cgg.material;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Color;

public interface Material {
		
	public abstract Ray calculateNewRay(Hit hit, Ray ray);
	
	public abstract boolean isAbsorbt();
	
	public abstract boolean isScattered();
	
	public abstract boolean isRefracted(); 
	
	public abstract Color getAlbedo(double x, double y);
	
	public abstract Color getEmission(double x, double y);
}
