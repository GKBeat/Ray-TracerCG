package cgg;

import cgg.material.BackgroundMaterial;
import cgg.material.Glass;
import cgg.material.Metall;
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

public class A06 {
	public static void main(String[] args) {
		final int width = 800;
		final int height = 450;
		final String filename = "doc/a06-mirrors-glass-1.png";
		final String filename1 = "doc/a06-mirrors-glass-2.png";

		Image image = new Image(width, height);
		LochKamera camera = new LochKamera(width, height, Math.PI / 3, Matrix.identity, 0, Double.POSITIVE_INFINITY);
		
		Shape ground = new Plane(7, Point.point(0.0, -0.5, 0.0), Direction.direction(0, 1, 0), new Diffuse(new Color(0.1, 0.8, 0.2)));
		
		Shape globe1 = new Kugel(Point.point(-0.75, -0.2, -1), 0.3, new Diffuse(new Color(0.3, 0.8, 1)));
		Shape globe2 = new Kugel(Point.point(-1, 0, -2.5), 0.5, new Metall(new Color(0.3, 0.4, 1), 0));
		
		Shape globe3 = new Kugel(Point.point(0, 1.5, -5), 2, new Metall(new Color(0.3, 0.2, 1), 0.2));
		
		Shape globe4 = new Kugel(Point.point(1.0, 0, -2.5), 0.5, new Metall(new Color(0.4, 0.2, 1), 0));
		Shape globe5 = new Kugel(Point.point(0.75, -0.2, -1), 0.3, new Diffuse(new Color(0.8, 0.2, 1)));
		
		Shape globe6 = new Kugel(Point.point(0, -0.2, 0.3), 0.2, new Diffuse(Color.red));
		
		Background bg = new Background(new BackgroundMaterial(new Color(0.6, 0.9, 1)));
		
		Group gr = new Group(new Shape[] {ground, globe1, globe2, globe3, globe4, globe5, globe6, bg});
		Raytracer raytracer = new Raytracer(camera, gr, 7);
		
		image.sample(new StratifiedSampler(raytracer, 1000));
		
		image.write(filename);
		System.out.println("Wrote image: " + filename);
		
		Shape globe7 = new Kugel(Point.point(-0.5, 0, -1.5), 0.5, new Diffuse(new Color(0.3, 0.4, 1)));
		Shape globe8 = new Kugel(Point.point(0.5, 0, -1.5), 0.5, new Diffuse(new Color(0.4, 0.2, 1)));
		
		Shape globe9 = new Kugel(Point.point(0, 0.05, -0.5), 0.1, new Glass(Color.white));
		
		Group gr1 = new Group(new Shape[] {ground, globe6, globe7, globe8, globe9, bg});
		Raytracer raytracer1 = new Raytracer(camera, gr1, 7);
		
		image.sample(new StratifiedSampler(raytracer1, 1000));
		
		image.write(filename1);
		System.out.println("Wrote image: " + filename1);
	}

}
