package cgg;

import cgg.material.BackgroundMaterial;
import cgg.material.Diffuse;
import cgg.sampler.Raytracer;
import cgg.sampler.StratifiedSampler;
import cgg.scene.LochKamera;
import cgg.scene.shapes.Background;
import cgg.scene.shapes.Group;
import cgg.scene.shapes.Kugel;
import cgg.scene.shapes.Plane;
import cgg.scene.shapes.Shape;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Matrix;
import cgtools.Point;
import cgtools.Random;

public class A04 {
	public static void main(String[] args) {
		final int width = 800;
		final int height = 450;
		final String filename = "doc/a04-3-spheres.png";
		final String filename1 = "doc/a04-scene.png";
		
		Image image = new Image(width, height);
		LochKamera camera = new LochKamera(width, height, Math.PI / 3, Matrix.identity, 0, Double.POSITIVE_INFINITY);
		
//--------------------------------------------------------------------------------------------------------------------------
		Shape ground = new Plane(5, Point.point(0.0, -0.5, 0.0), Direction.direction(0, 1, 0), new Diffuse(Color.gray));
		Shape globe1 = new Kugel(Point.point(-1.0, -0.25, -2.5), 0.7, new Diffuse(new Color(1, 0.1, 0.1)));
		Shape globe2 = new Kugel(Point.point(0.0, -0.25, -2.5), 0.5, new Diffuse(new Color(0, 1, 0.4)));
		Shape globe3 = new Kugel(Point.point(1.0, -0.25, -2.5), 0.7, new Diffuse(new Color(0, 0.5, 1)));
		Background bg = new Background(new BackgroundMaterial(Color.black));
		
		Group gr = new Group(new Shape[] {ground, globe1, globe2, globe3, bg});
		Raytracer raytracer = new Raytracer(camera, gr, 5);
		
		image.sample(new StratifiedSampler(raytracer, 100));
		
		image.write(filename);
		System.out.println("Wrote image: " + filename);
//--------------------------------------------------------------------------------------------------------------------------	
		Shape groundND = new Plane(4, Point.point(0.0, -0.5, -2.5), Direction.direction(0, 1, 0), new Diffuse(Color.white));
		Shape legs = new Kugel(Point.point(0.0, -0.5, -2.5), 0.4, new Diffuse(Color.white));
		Shape body = new Kugel(Point.point(0.0, 0, -2.5), 0.25, new Diffuse(Color.white));
		Shape head = new Kugel(Point.point(0.0, 0.4, -2.5), 0.2, new Diffuse(Color.white));
		Shape leftEye = new Kugel(Point.point(-0.04, 0.175, -1), 0.01, new Diffuse(Color.black));
		Shape rightEye = new Kugel(Point.point(0.04, 0.175, -1), 0.01, new Diffuse(Color.black));
		Shape nose = new Kugel(Point.point(0.0, 0.15, -1), 0.01, new Diffuse(Color.red));
		Background bg1 = new Background(new BackgroundMaterial(new Color(0, 0, 0.2)));
		
		Group snowMan = new Group(new Shape[] {legs, body, head, nose, leftEye, rightEye});
		Group snowflakes = new Group(createSnowflakes(125));
		Group gr1 = new Group(new Shape[] {groundND, bg1, snowMan, snowflakes});
				
		Raytracer raytracerND = new Raytracer(camera, gr1, 5);
		
		image.sample(new StratifiedSampler(raytracerND, 100));
		
		image.write(filename1);
		System.out.println("Wrote image: " + filename1);
		
	}
	
	private static Shape[] createSnowflakes(int numSnowflakes) {
		Shape[] tmp = new Shape[numSnowflakes];
		double x = 0;
		double y = 0;
		double z = 0;
		
		for(int i = 0; i < numSnowflakes; i++) {
			
			if(i%2 == 0) {
				x = Random.random()-1;
			} else {
				x = Random.random();
			}
			
			z = -(Random.random()*3);
			if(z > -1) {
				z-=1.5;
			}
			
			y = Random.random()-0.5;
			
			tmp[i] = new Kugel(Point.point(x, y, z), 0.01, new Diffuse(Color.white));
		}
		
		return tmp;
	}
}
