package cgg;

import java.util.ArrayList;

import cgg.material.BackgroundMaterial;
import cgg.material.Diffuse;
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
import cgtools.Random;
import cgtools.Util;
import cgtools.Vector;

public class TestYouInCar {
	
	public static void main(String[] args) {
		final int width = 1920;
		final int height = 1080;
		
		final String filename = "doc/cgg-competition-ws-19-894204.png";
		
		Image image = new Image(width, height);
		ArrayList<Light> lights = new ArrayList<Light>();

		Matrix far = Matrix.translation(Direction.direction(0, 0, 1));
		
		TransformedTexture ttWand = new TransformedTexture(new Texture("doc/texture/floorPebbles.jpg"), Matrix.scaling(75, 75, 75));
		TransformedTexture roadpic = new TransformedTexture(new Texture("doc/texture/road.jpg"), Matrix.rotation(Direction.zAxis, 90));
		Texture bagr = new Texture("doc/texture/bg/duskSunset1.jpg");
		
		Background bg = new Background(new BackgroundMaterial(bagr)); 
		Shape ground = new Plane(500, Point.point(0.0, -1.1, 0.0), Direction.direction(0, 1, 0), new Diffuse(ttWand));
//----------------------------------------------------------------------------------------------------------------------------------------
		//Boulders
		Group kaktiR = boulderMakerRight(25);
		Group kaktiL = boulderMakerLeft(25);
		
		Group kaktis = new Group(new Shape[] {kaktiR, kaktiL});
//----------------------------------------------------------------------------------------------------------------------------------------
		//Car
		Shape carShape = new ZylinderX(Point.point(-1, -1.5, 0.5), 1, 2, new Metall(ttWand, 0.25));
		Point lightLeft = Point.point(-0.5, -0.75, 0);
		Point lightRight = Point.point(0.5, -0.75, 0);
		
		Shape lightsL = new Kugel(lightLeft, 0.125 ,new BackgroundMaterial(new Constant(Color.white)));
		lights.add(new PointLight(Point.point(-0.5, -0.75, -0.25), new Color(100, 75, 50)));
		
		Shape lightsR = new Kugel(lightRight, 0.125 ,new BackgroundMaterial(new Constant(Color.white)));
		lights.add(new PointLight(Point.point(0.5, -0.75, -0.25), new Color(100, 75, 50)));
		
		Group car = new Group(new Shape[] {carShape, lightsR, lightsL});
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
		
		Group lts1 = new Group(new Shape[] {lts}, new Transformation(Matrix.translation(Direction.direction(0, 0, -5))));

		Group lts2 = new Group(new Shape[] {lts1}, new Transformation(Matrix.translation(Direction.direction(0, 0, -5))));

		Group lts3 = new Group(new Shape[] {lts2}, new Transformation(Matrix.translation(Direction.direction(0, 0, -5))));

		Group lts4 = new Group(new Shape[] {lts3}, new Transformation(Matrix.translation(Direction.direction(0, 0, -5))));

		Group lts5 = new Group(new Shape[] {lts4}, new Transformation(Matrix.translation(Direction.direction(0, 0, -5))));

		Group lts6 = new Group(new Shape[] {lts5}, new Transformation(Matrix.translation(Direction.direction(0, 0, -5))));

		Group lts7 = new Group(new Shape[] {lts6}, new Transformation(Matrix.translation(Direction.direction(0, 0, -5))));

		
		Group laternen = new Group(new Shape[] {lts, lts1, lts2, lts3, lts4, lts5, lts6, lts7});
//----------------------------------------------------------------------------------------------------------------------------------------

		
		//Adding all the lights to the lanterns

		addAllLanternLights(lights, 8);

//----------------------------------------------------------------------------------------------------------------------------------------
	
		Group main = new Group(new Shape[] {bg, road, ground, laternen, car, kaktis});
		LochKamera camera = new LochKamera(width, height, Math.PI/2, far, Util.EPSILON, Double.POSITIVE_INFINITY);
		Raytracer raytracer = new Raytracer(camera, main, 4, lights);
		
		image.sample(new StratifiedSampler(raytracer, 50));
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
		double iR = 50*2;
		double iG = 37.5*2;
		double iB = 12.5*2;
		
		for(int i = 0; i < numLanternCol; i++) {
			lights.add(new PointLight(Point.point(1.9, 0.3, -2 - (i*5)), new Color(iR, iG, iB)));
			lights.add(new PointLight(Point.point(-1.9, 0.3, -2 - (i*5)), new Color(iR, iG, iB)));
		}
	}
	
	public static Group boulderMakerRight(int numBoulder) {
		Group tmp = new Group(new Shape[numBoulder]);
		
		for(int i = 0; i < numBoulder; i++) {
			
			double size = Random.random()*6;
			
			double x = Random.random()*40;
			double z = Random.random()*(-40);
			while(x < 6) {
				x = Random.random()*40;
			}
			
			while(z > 0) {
				z = Random.random()*(-40);
			}
			
			
			tmp.shapes[i] = makeKakti(size, x, z);
		}
		
		return tmp;
	}
	
	public static Group boulderMakerLeft(int numBoulder) {
		
		Group tmp = new Group(new Shape[numBoulder]);
		
		for(int i = 0; i < numBoulder; i++) {
			
			double size = Random.random()*6;
			
			double x = Random.random()*(-40);
			double z = Random.random()*(-40);
			while(x > -6) {
				x = Random.random()*(-40);
			}
			
			while(z > 0) {
				z = Random.random()*(-40);
			}
			
			
			tmp.shapes[i] = makeKakti(size, x, z);
		}
		
		return tmp;
	}
	
	public static Group makeKakti(double size, double x, double z) {
		Diffuse kakti = new Diffuse(new Texture("doc/texture/kakti.jpg"));
		Group tmp = new Group(new Shape[1]);
		
		tmp.shapes[0] = new ZylinderY(Point.point(x, -1, z), 0.5, size, kakti);
		
		return tmp;
	}
	
}
