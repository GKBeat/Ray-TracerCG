package cgg.scene.light;

import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgg.scene.shapes.Shape;
import cgtools.Color;

public interface Light {
	
	public Color light(Shape shape, Hit hit, Color albedo);

}
