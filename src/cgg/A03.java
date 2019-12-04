package cgg;

import cgg.material.Diffuse;
import cgg.sampler.Raytracer;
import cgg.sampler.StratifiedSampler;
import cgg.scene.LochKamera;
import cgg.scene.shapes.Kugel;
import cgtools.Color;
import cgtools.Matrix;
import cgtools.Point;

public class A03 {

	public static void main(String[] args) {
		final int width = 800;
		final int height = 450;
		final String filename = "doc/a03-one-sphere.png";
		
		Image image = new Image(width, height);
		LochKamera cam = new LochKamera(width, height, Math.PI/2, Matrix.identity, 0, Double.POSITIVE_INFINITY);
		Kugel kugel = new Kugel(Point.point(0, 0, -3), 1, new Diffuse(new Color(0.1, 1, 0.3)));
		
		image.sample(new StratifiedSampler(new Raytracer(cam, kugel, 5), 100));

		image.write(filename);
		System.out.println("Wrote image: " + filename);
	}

}
