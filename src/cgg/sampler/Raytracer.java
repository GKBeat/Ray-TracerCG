package cgg.sampler;

import cgg.material.Material;
import cgg.scene.LochKamera;
import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgg.scene.shapes.Shape;
import cgtools.Color;
import cgtools.Sampler;
import cgtools.Vector;

public class Raytracer implements Sampler{
	
	protected LochKamera cam;
	protected Shape shape;
	protected int depth;
	
	public Raytracer(LochKamera cam, Shape shape, int depth) {
		this.cam = cam;
		this.shape = shape;
		this.depth = depth;
	}
	
	public Color getColor(double x, double y) {
		
		return calculateRadiance(cam.getRayThroughPoint(x, y), depth);
	}
	
	private Color calculateRadiance(Ray ray, int depth) {
		if(depth == 0) {
			return Color.black;
		}
		Hit hit = shape.intersect(ray);
		
		Material m = hit.material;
		Ray r = m.calculateNewRay(hit, ray);
		
		if(r != null) {
			Color c = Color.multiply(m.getAlbedo(), calculateRadiance(r, depth-1));
			return Color.add(m.getEmission(), c);
//			return Vector.asColor(hit.n);
		}else {
			return m.getEmission();
		}
	}

}
