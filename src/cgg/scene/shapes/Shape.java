package cgg.scene.shapes;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;

public interface Shape {
	
	public abstract Hit intersect(Ray r);
}
