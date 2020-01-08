package cgg;

import cgg.material.BackgroundMaterial;
import cgg.material.Diffuse;
import cgg.material.Glass;
import cgg.material.Metall;
import cgg.sampler.Constant;
import cgg.sampler.Raytracer;
import cgg.sampler.StratifiedSampler;
import cgg.scene.LochKamera;
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

public class A07 {
	
	private static Color br= new Color(0.14, 0.09, 0);
	private static Color brI = new Color(0.4, 0.2, 0);
	private static Color ag= new Color(0.85, 0.55, 0);
	
	public static void main(String[] args) {
		final int width = 800;
		final int height = 450;
		final String filename = "doc/a07-1.png";
		final String filename1 = "doc/a07-2.png";
		
		Matrix mId = Matrix.identity;
		Matrix m1 = Matrix.translation(Direction.direction(0, 6, 0));
		Matrix mObenView = Matrix.multiply(m1, Matrix.rotation(Direction.xAxis, -45));
		
		Image image = new Image(width, height);
		LochKamera camera = new LochKamera(width, height, Math.PI/2, mId, 0, Double.POSITIVE_INFINITY);
		Background bg = new Background(new BackgroundMaterial(new Constant(Color.white)));
		
		Plane snow = new Plane(25, Point.point(0, -1, -6), Direction.direction(0, 1, 0), new Diffuse(new Constant(Color.white)));
		Plane darness = new Plane (25, Point.point(0, 0, -10), Direction.direction(0, 0, 1), new Diffuse(new Constant(new Color(0.1, 0.2, 0.3))));
		Plane kranz = new Plane (0.3, Point.point(0, 0.5, -3.75), Direction.direction(0, 0, 1), new Diffuse(new Constant(new Color(0.1, 0.4, 0.2))));
		Plane loch = new Plane(0.1, Point.point(0, 0.5, -3.75), Direction.direction(0, 0, 1), new Diffuse(new Constant(br)));
		Plane see1 = new Plane(0.7, Point.point(-2.5, -1, -2.5), Direction.direction(0, 1, 0), new Metall(new Constant(Color.blue), 0));
		Plane see2 = new Plane(1, Point.point(-2, -1, -1.5), Direction.direction(0, 1, 0), new Metall(new Constant(Color.blue), 0));
		Plane moon = new Plane(2, Point.point(-9, 6, -10), Direction.direction(0, 0, 1), new Diffuse(new Constant(Color.white)));
		
		Shape pfeilerR = new ZylinderY(Point.point(2.7, -1, -4), 0.25, 2, new Diffuse(new Constant(br)));
		Shape pfeilerL = new ZylinderY(Point.point(-2.7, -1, -4), 0.25, 2, new Diffuse(new Constant(br)));
		
		Shape a = new ZylinderY(Point.point(0.5, -1, -2), 0.25, 0.5, new Glass(new Constant(Color.white)));
		Shape b = new ZylinderY(Point.point(-0.5, -1, -2), 0.25, 0.5, new Glass(new Constant(Color.white)));
		
		Group see = new Group(new Shape[] {see1, see2});
		
		Group christSpheres = makeChristSphere(kranz); 
		
		Group logLineL = makeLogWall(-2.5, -0.75);
		Group logLineR = makeLogWall(0.5, -0.75);
		Group logLineO = makeLogTop(-3, 1.25, -4, 6);
		Group logDoor = makeDoor(-0.39, -1);
		
		Group kerze = makeKerzenStand(-0.75);
		Group kerze1 = makeKerzenStand(0.75);
		
		Group walkWay = makeWalkWay(snow, 15);
		Group bushL = makeBushes(-0.75, -3, 15);
		Group bushR = makeBushes(0.75, -3, 15);
		
		
		Group gr1 = new Group(new Shape[] { snow, bg, logLineL, logLineR, pfeilerR, pfeilerL, logLineO, logDoor,
				darness, kranz, christSpheres, loch, kerze, kerze1, walkWay, bushL, bushR, see, moon, a, b});

		Raytracer raytracer = new Raytracer(camera, gr1, 5);
		
		image.sample(new StratifiedSampler(raytracer, 500));
		
		image.write(filename);
		System.out.println("Wrote image: " + filename);
		
		LochKamera camera1 = new LochKamera(width, height, Math.PI/2, mObenView, 0, Double.POSITIVE_INFINITY);
		Raytracer raytracer1 = new Raytracer(camera1, gr1, 5);
		
		image.sample(new StratifiedSampler(raytracer1, 500));
		
		image.write(filename1);
		System.out.println("Wrote image: " + filename1);
	}
	
	public static Group makeLogWall(double x, double y) {
		Group tmp = new Group(new Shape[6]);
		
		for(int i = 0; i < tmp.shapes.length; i++) {
			tmp.shapes[i] = new ZylinderX(Point.point(x, y, -4), 0.25, 2, new Diffuse(new Constant(br)));
			y += 0.5;
		}
		return tmp;
	}
	
	public static Group makeLogTop(double x, double y, double z, double height) {
		Group tmp = new Group(new Shape[6]);
		for(int i = 0; i < tmp.shapes.length; i++) {
			tmp.shapes[i] = new ZylinderX(Point.point(x, y, z), 0.3, height, new Diffuse(new Constant(br)));
			height--;
			if(height==0) {
				height = 1;
			}
			y += 0.25;
			x += 0.5;
			z -= 0.5;
		}
		return tmp;
	}
	public static Group makeLogTop1(double x, double y, double z, double height) {
		Group tmp = new Group(new Shape[6]);
		for(int i = 0; i < tmp.shapes.length; i++) {
			tmp.shapes[i] = new ZylinderX(Point.point(x, y, z), 0.3, height, new Diffuse(new Constant(br)));
			height--;
			if(height==0) {
				height = 1;
			}
			y += 0.25;
			x += 0.5;
			z += 0.5;
		}
		return tmp;
	}
	
	public static Group makeDoor(double x, double y) {
		Group tmp = new Group(new Shape[5]);
		
		for(int i = 0; i < tmp.shapes.length; i++) {
			tmp.shapes[i] = new ZylinderY(Point.point(x, y, -3.95), 0.1, 3, new Diffuse(new Constant(br)));
			x += 0.2;
		}
		return tmp;
	}
	
	public static Group makeChristSphere(Plane plane) {
		Group tmp = new Group(new Shape[8]);
		
		tmp.shapes[0] = new Kugel(Point.point(plane.p.x+0.2, plane.p.y, plane.p.z), 0.1, new Metall(new Constant(Color.red), 0));
		tmp.shapes[1] = new Kugel(Point.point(plane.p.x, plane.p.y+0.2, plane.p.z), 0.1, new Metall(new Constant(Color.red), 1));
		tmp.shapes[2] = new Kugel(Point.point(plane.p.x-0.2, plane.p.y, plane.p.z), 0.1, new Metall(new Constant(Color.red), 0));
		tmp.shapes[3] = new Kugel(Point.point(plane.p.x, plane.p.y-0.2, plane.p.z), 0.1, new Metall(new Constant(Color.red), 1));
		
		tmp.shapes[4] = new Kugel(Point.point(plane.p.x+0.2, plane.p.y+0.2, plane.p.z), 0.05, new Metall(new Constant(ag), 0));
		tmp.shapes[5] = new Kugel(Point.point(plane.p.x-0.2, plane.p.y+0.2, plane.p.z), 0.05, new Metall(new Constant(ag), 1));
		tmp.shapes[6] = new Kugel(Point.point(plane.p.x+0.2, plane.p.y-0.2, plane.p.z), 0.05, new Metall(new Constant(ag), 1));
		tmp.shapes[7] = new Kugel(Point.point(plane.p.x-0.2, plane.p.y-0.2, plane.p.z), 0.05, new Metall(new Constant(ag), 0));
		
		return tmp;
	}
	
	public static Group makeKerzenStand(double x) {
		Group tmp = new Group(new Shape[3]);
		
		tmp.shapes[0] = new Plane(0.2, Point.point(x, -0.50, -3.5), Direction.direction(0, 1, 0), new Diffuse(new Constant(ag)));
		tmp.shapes[1] = new ZylinderY(Point.point(x, -0.5, -3.5), 0.05, 0.25, new Diffuse(new Constant(Color.white)));
		tmp.shapes[2] = new Kugel(Point.point(x, -0.20, -3.5), 0.05, new BackgroundMaterial(new Constant(Color.red)));
		
		return tmp;
	}
	
	public static Group makeWalkWay(Plane plane, int index) {
		Group tmp = new Group(new Shape[index]);
		double z = plane.p.z;
		for(int i = 0; i < tmp.shapes.length; i++) {
			
			tmp.shapes[i] = new Plane(0.7, Point.point(plane.p.x, plane.p.y, z), Direction.direction(0, 1, 0), new Metall(new Constant(brI), 1));
			z += 0.5;
		}
		return tmp;
	}
	
	public static Group makeBushes(double x, double z, int index) {
		Group tmp = new Group(new Shape[index]);
		for(int i = 0; i < tmp.shapes.length; i++) {
			if(i%2 == 0) {
				tmp.shapes[i] = new Kugel(Point.point(x, -0.75, z), 0.25, new Diffuse(new Constant(new Color(0.1, 0.4, 0.2))));
			}else {
				tmp.shapes[i] = new Kugel(Point.point(x, -0.75, z), 0.15, new Diffuse(new Constant(new Color(0.1, 0.4, 0.2))));
			}
			z += 0.25;
		}
		
		return tmp;
	}
	
}
