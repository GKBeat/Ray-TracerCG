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
import cgg.scene.light.Light;
import cgg.scene.light.PointLight;
import cgg.scene.rays.Transformation;
import cgg.scene.shapes.Background;
import cgg.scene.shapes.Group;
import cgg.scene.shapes.Kugel;
import cgg.scene.shapes.Plane;
import cgg.scene.shapes.Shape;
import cgg.scene.shapes.ZylinderX;
import cgg.scene.shapes.ZylinderY;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Matrix;
import cgtools.Point;
import cgtools.Util;
import cgtools.Vector;

public class TestCarInfront {
	
	public static void main(String[] args) {
		final int width = 500;
		final int height = 500;
//		final int width = 1920;
//		final int height = 1080;
		
		final String filename = "doc/testCarInFront.png";
		
		Image image = new Image(width, height);
		ArrayList<Light> lights = new ArrayList<Light>();

		Matrix id = Matrix.identity;
		Matrix far = Matrix.translation(Direction.direction(0, 0, 1));
		TransformedTexture ttWand = new TransformedTexture(new Texture("doc/texture/floorPebbles.jpg"), Matrix.scaling(75, 75, 75));
		TransformedTexture roadpic = new TransformedTexture(new Texture("doc/texture/road.jpg"), Matrix.rotation(Direction.zAxis, 90));
		
		Background bg = new Background(new BackgroundMaterial(new Texture("doc/texture/bg/duskSunset1.jpg"))); 
		Shape ground = new Plane(500, Point.point(0.0, -1.5, 0.0), Direction.direction(0, 1, 0), new Diffuse(ttWand));
		
		Shape carShape = new ZylinderX(Point.point(-1, -1, -4), 1, 2, new Metall(ttWand, 0.5));
		Point lightLeft = Point.point(-0.5, -0.5, -2);
		Point lightRight = Point.point(0.5, -0.5, -2);
		
		Shape lightsL = new Kugel(lightLeft, 0.125 ,new BackgroundMaterial(new Constant(Color.white)));
		lights.add(new PointLight(lightLeft, new Color(100,50,25)));
		
		Shape lightsR = new Kugel(lightRight, 0.125 ,new BackgroundMaterial(new Constant(Color.white)));

		lights.add(new PointLight(lightRight, new Color(100,50,25)));

		
		Shape windschutz = new ZylinderX(Point.point(-0.75, 0, -4), 0.5, 1.5, new Diffuse(ttWand));
		
		Group car = new Group(new Shape[] {carShape, lightsR, lightsL, windschutz});
//----------------------------------------------------------------------------------------------------------------------------------------
		//The Road
		Shape road1 = new Plane(5, Point.point(0, -1, -4), Direction.direction(0, 1, 0), new Diffuse(roadpic));
		
		Group uno = new Group(new Shape[] {road1}, new Transformation(Matrix.translation(Direction.direction(0, 0, -5))));
		Group dos = new Group(new Shape[] {uno}, new Transformation(Matrix.translation(Direction.direction(0,  0, -5))));
		Group tres = new Group(new Shape[] {dos}, new Transformation(Matrix.translation(Direction.direction(0,  0, -5))));
		Group quatro = new Group(new Shape[] {tres}, new Transformation(Matrix.translation(Direction.direction(0,  0, -5))));
		Group cinco = new Group(new Shape[] {quatro}, new Transformation(Matrix.translation(Direction.direction(0,  0, -5))));

		Group road = new Group(new Shape[] {road1, uno, dos, tres, quatro, cinco});
//----------------------------------------------------------------------------------------------------------------------------------------
		//Laternen
		Group laterne1 = makeLantern(Matrix.translation(3, -1, -2));
		Group laterne2 = makeLantern(Matrix.multiply(Matrix.translation(-3, -1, -2), Matrix.rotation(Vector.yAxis, 180)));
		
		Group lts = new Group(new Shape[] {laterne1, laterne2});
		
		Group lts1 = new Group(new Shape[] {lts}, new Transformation(Matrix.translation(Direction.direction(0, 0, -3))));

		Group lts2 = new Group(new Shape[] {lts1}, new Transformation(Matrix.translation(Direction.direction(0, 0, -3))));

		Group lts3 = new Group(new Shape[] {lts2}, new Transformation(Matrix.translation(Direction.direction(0, 0, -3))));

		Group lts4 = new Group(new Shape[] {lts3}, new Transformation(Matrix.translation(Direction.direction(0, 0, -3))));

		Group lts5 = new Group(new Shape[] {lts4}, new Transformation(Matrix.translation(Direction.direction(0, 0, -3))));

		Group lts6 = new Group(new Shape[] {lts5}, new Transformation(Matrix.translation(Direction.direction(0, 0, -3))));

		Group lts7 = new Group(new Shape[] {lts6}, new Transformation(Matrix.translation(Direction.direction(0, 0, -3))));

		
		Group laternen = new Group(new Shape[] {lts, lts1, lts2, lts3, lts4, lts5, lts6, lts7});
//----------------------------------------------------------------------------------------------------------------------------------------

		
		//Adding all the lights to the lanterns
		addAllLanternLights(lights, 1);
		
//----------------------------------------------------------------------------------------------------------------------------------------
	
		Group main = new Group(new Shape[] {bg, road, ground, lts, car});
		LochKamera camera = new LochKamera(width, height, Math.PI/2, far, Util.EPSILON, Double.POSITIVE_INFINITY);
		Raytracer raytracer = new Raytracer(camera, main, 4, lights);
		
		image.sample(new StratifiedSampler(raytracer, 5));
		image.write(filename);
		System.out.println("Wrote image: " + filename);
	}
	
	public static Group makeLantern(Matrix xyz) {
		Texture torch = new Texture("doc/texture/greenMetall.jpg");
		
		Group tmp = new Group(new Shape[] {new ZylinderY(Point.point(0, 0, 0), 0.125/2, 2, new Metall(torch, 0.7))});
		
		Group tmp1 = new Group(new Shape[] {new ZylinderY(Point.point(2, 0, 0), 0.125/2, 0.75, new Metall(torch, 0.7))}, new Transformation(Matrix.rotation(Vector.zAxis, 90)));
		
		Group tmp2 = new Group(new Shape[] {new ZylinderY(Point.point(1.9, 0, 0), 0.125/2, 0.25, new BackgroundMaterial(new Constant (Color.white)))}, new Transformation(Matrix.multiply(Matrix.rotation(Vector.zAxis, 90), Matrix.translation(0,0.4,0))));
		
		return new Group(new Shape[] {tmp, tmp1, tmp2}, new Transformation(xyz));

	}
	
	public static void addAllLanternLights(ArrayList<Light> lights, int numLanternCol) {
		double iR = 50;
		double iG = 37.5;
		double iB = 12.5;
		
		for(int i = 0; i < numLanternCol; i++) {
			lights.add(new PointLight(Point.point(1.9, 0.3, -2 - (i*3)), new Color(iR, iG, iB)));
			lights.add(new PointLight(Point.point(-1.9, 0.3, -2 - (i*3)), new Color(iR, iG, iB)));
		}
	}
}
