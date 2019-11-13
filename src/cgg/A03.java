package cgg;

import cgg.material.PerfectDiffuseMaterial;
import cgg.sampler.Raytracer;
import cgg.sampler.StratifiedSampler;
import cgg.scene.LochKamera;
import cgg.scene.shapes.KugelSurface;
import cgtools.Color;
import cgtools.Point;

public class A03 {

	public static void main(String[] args) {
		final int width = 800;
		final int height = 450;
		final String filename = "doc/a03-one-sphere.png";
		
		Image image = new Image(width, height);
		LochKamera cam = new LochKamera(width, height, Math.PI/2, Point.zero, 0, Double.POSITIVE_INFINITY);
		KugelSurface kugel = new KugelSurface(Point.point(0, 0, -3), 1, new PerfectDiffuseMaterial(new Color(0.1, 1, 0.3)));
		
		image.sample(new StratifiedSampler(new Raytracer(cam, kugel, 5), 100));

		image.write(filename);
		System.out.println("Wrote image: " + filename);
	}

}
