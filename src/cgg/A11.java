package cgg;

import java.util.ArrayList;

import cgg.material.BackgroundMaterial;
import cgg.material.Diffuse;
import cgg.material.Glass;
import cgg.material.Metall;
import cgg.sampler.Constant;
import cgg.sampler.Raytracer;
import cgg.sampler.StratifiedSampler;
import cgg.sampler.Texture;
import cgg.sampler.TransformedTexture;
import cgg.scene.LochKamera;
import cgg.scene.light.DirectedLight;
import cgg.scene.light.Light;
import cgg.scene.light.PointLight;
import cgg.scene.rays.Transformation;
import cgg.scene.shapes.Background;
import cgg.scene.shapes.Group;
import cgg.scene.shapes.Kugel;
import cgg.scene.shapes.Plane;
import cgg.scene.shapes.Shape;
import cgg.scene.shapes.ZylinderDeprecated;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Matrix;
import cgtools.Point;
import cgtools.Util;

public class A11 {
	
	public static void main(String[] args) {
		final int width = 500;
		final int height = 500;
		final String filename = "doc/a11-1.png";
		final String filename1 = "doc/a11-2.png";
		
		Image image = new Image(width, height);

		Texture orangeSky = new Texture("doc/texture/bg/sunsetOrange.jpg");
		Texture snow = new Texture("doc/texture/snow.jpg");
		
		Matrix kleinScaliert = Matrix.scaling(100, 100, 100);
		Matrix id = Matrix.identity;
		
		Background bg1 = new Background(new BackgroundMaterial(orangeSky)); 
		Shape globeMetall = new Kugel(Point.point(0, -0.75, -3), 0.75, new Glass(snow));
		Shape globePDD = new Kugel(Point.point(0, 0.25, -3), 0.5, new Glass(snow));
		Shape groundS = new Plane(500, Point.point(0.0, -1.5, 0.0), Direction.direction(0, 1, 0), new Diffuse(new TransformedTexture(snow, kleinScaliert)));
		
		
		Shape rightGl = new Plane(0.1, Point.point(0.2, 0.3, -2.5), Direction.direction(0, 0, 1), new Metall(new Constant(Color.black), 0));
		Shape leftGl = new Plane(0.1, Point.point(-0.2, 0.3, -2.5), Direction.direction(0, 0, 1), new Metall(new Constant(Color.black), 0));
		Shape zyl = new ZylinderDeprecated(Point.point(0, 0, 0), 0.025, 0.4, new Diffuse(new Constant(Color.black)));
		Group a = new Group(new Shape[] {zyl}, new Transformation(Matrix.multiply(Matrix.translation(0.2, 0.33, -2.5), Matrix.rotation(Direction.zAxis, 90))));
		
		Group sunglasses = new Group(new Shape[] {rightGl, leftGl, a});
		
		Group snowMan = new Group(new Shape[] {globeMetall, globePDD, sunglasses});
		Group snowMan1 = new Group(new Shape[] {snowMan}, new Transformation(Matrix.translation(2, 0, -3)));
		Group snowMan2 = new Group(new Shape[] {snowMan}, new Transformation(Matrix.translation(-2, 0, -3)));


		Group main = new Group(new Shape[] {bg1, snowMan, snowMan1, snowMan2, groundS});
		
		LochKamera camera = new LochKamera(width, height, Math.PI / 2, id, Util.EPSILON, Double.POSITIVE_INFINITY);
		
		ArrayList<Light> lights = new ArrayList<Light>();
		lights.add(new PointLight(Point.point(-1, 2, -2), new Color(10,100,100)));

		Raytracer raytracer = new Raytracer(camera, main, 4, lights);
		
		
		image.sample(new StratifiedSampler(raytracer, 10));
		image.write(filename);
		System.out.println("Wrote image: " + filename);
		
		ArrayList<Light> lights2 = new ArrayList<Light>();
		lights2.add(new DirectedLight(Direction.direction(1, 0.5, 1), new Color(8,1,2.5)));
		
		Raytracer rt = new Raytracer(camera, main, 4, lights2);
		
		
		image.sample(new StratifiedSampler(rt, 10));
		image.write(filename1);
		System.out.println("Wrote image: " + filename1);
		
		
		
	}

}
