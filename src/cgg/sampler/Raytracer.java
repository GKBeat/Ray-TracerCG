package cgg.sampler;

import java.util.ArrayList;

import cgg.material.Material;
import cgg.scene.LochKamera;
import cgg.scene.light.Light;
import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgg.scene.shapes.Shape;
import cgtools.Color;
import cgtools.Sampler;

public class Raytracer implements Sampler{
	
	protected LochKamera cam;
	protected Shape shape;
	protected int depth;
	protected ArrayList<Light> lights;
	
	public Raytracer(LochKamera cam, Shape shape, int depth) {
		this.cam = cam;
		this.shape = shape;
		this.depth = depth;
	}
	
	public Raytracer(LochKamera cam, Shape shape, int depth, ArrayList<Light> lights) {
		this.cam = cam;
		this.shape = shape;
		this.depth = depth;
		this.lights = lights;
	}
	
	public Color getColor(double x, double y) {
		return calculateRadiance(cam.getRayThroughPoint(x, y), depth);
	}
	
	private Color calculateRadiance(Ray ray, int depth) {
		if(depth == 0) {
			return Color.black;
		}
		Hit hit = shape.intersect(ray);
		double x = hit.texturenPoint.x;
		double y = hit.texturenPoint.y;
		Material m = hit.material;
		Ray r = m.calculateNewRay(hit, ray);
		
		if(r != null) {
			Color light = Color.black;
			for(Light l : lights) {
				light = Color.add(light, l.light(shape, hit, m.getAlbedo(x, y)));
			}
			Color c = Color.multiply(Color.add(m.getAlbedo(x, y), light), calculateRadiance(r, depth-1));
			return Color.add(m.getEmission(x, y), c);
//			return Vector.asColor(hit.n);
		}else {
			return m.getEmission(x, y);
		}
	}

}
