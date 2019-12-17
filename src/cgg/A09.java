package cgg;

import static cgtools.Color.white;
import static cgtools.Matrix.multiply;
import static cgtools.Matrix.rotation;
import static cgtools.Matrix.translation;
import static cgtools.Vector.direction;
import static cgtools.Vector.yAxis;
import static cgtools.Vector.zero;

import cgg.material.BackgroundMaterial;
import cgg.material.Diffuse;
import cgg.material.Glass;
import cgg.material.Metall;
import cgg.sampler.Raytracer;
import cgg.sampler.StratifiedSampler;
import cgg.scene.LochKamera;
import cgg.scene.rays.Transformation;
import cgg.scene.shapes.Background;
import cgg.scene.shapes.Group;
import cgg.scene.shapes.Kugel;
import cgg.scene.shapes.Plane;
import cgg.scene.shapes.Shape;
import cgg.scene.shapes.ZylinderY;
import cgtools.Color;
import cgtools.Direction;
import cgtools.Matrix;
import cgtools.Point;

public class A09 {
	private static Color br= new Color(0.14, 0.09, 0);
	
	public static void main(String[] args) {
		final int width = 800;
		final int height = 450;
		final String filename = "doc/a09-benchmark-scene.png";
		
		Image image = new Image(width, height);
		Matrix mSeite = multiply(translation(direction(12, 1, 0)), rotation(yAxis, 33.75));

		Background bg = new Background(new BackgroundMaterial(white));
		ZylinderY stamm = new ZylinderY(zero, 0.25, 2, new Glass(br));
		Kugel krone = new Kugel(zero, 2, new Glass(new Color(0.1, 0.4, 0.2)));
		
		ZylinderY stamm1 = new ZylinderY(zero, 0.25, 2, new Glass(br));
		Kugel krone1 = new Kugel(zero, 2, new Glass(new Color(0.1, 0.4, 0.2)));
		
		Group trunk = new Group(new Shape[] {stamm}, new Transformation(translation(direction(0, -1, 0))));
		Group crone = new Group(new Shape[] {krone}, new Transformation(translation(direction(0, 3, 0))));
		
		Group trunk1 = new Group(new Shape[] {stamm1}, new Transformation(translation(direction(0, -1, 0))));
		Group crone1 = new Group(new Shape[] {krone1}, new Transformation(translation(direction(0, 3, 0))));
		
		Group tree = new Group(new Shape[] {trunk, crone}, new Transformation(translation(direction(6, 0, -5))));
		Group tree1 = new Group(new Shape[] {trunk1, crone1}, new Transformation(translation(direction(-6, 0, -5))));
			
		
		Plane snow = new Plane(25, Point.point(0, -1, -6), Direction.direction(0, 1, 0), new Diffuse(Color.white));
		Plane darness = new Plane (30, Point.point(0, 11, -10), Direction.direction(0, 0, 1), new Diffuse(new Color(0.1, 0.2, 0.3)));
		Plane darkness = new Plane (30, Point.point(-25, 11, -10), Direction.direction(1, 0, 0), new Diffuse(new Color(0.1, 0.2, 0.3)));
		
		Group environment = new Group(new Shape[] {darness, snow, tree, tree1, darkness});
//-------------------------------------------------------------------------------------------------------------------------- 		
		
		Shape pfeilerR = new ZylinderY(Point.point(2.7, -1, -4), 0.25, 3, new Diffuse(br));
		Shape pfeilerL = new ZylinderY(Point.point(-2.7, -1, -4), 0.25, 3, new Diffuse(br));
		
//-------------------------------------------------------------------------------------------------------------------------- 		
		Plane see1 = new Plane(0.7, Point.point(-2.5, -1, -2.5), Direction.direction(0, 1, 0), new Metall(Color.blue, 0));
		Plane see2 = new Plane(1, Point.point(-2, -1, -1.5), Direction.direction(0, 1, 0), new Metall(Color.blue, 0));
		
		Group see = new Group(new Shape[] {see1, see2}, new Transformation(translation(direction(-1, 0, 0))));
//-------------------------------------------------------------------------------------------------------------------------- 		
		Plane kranzK = new Plane (0.3, Point.point(0, 0.5, -3.75), Direction.direction(0, 0, 1), new Diffuse(new Color(0.1, 0.4, 0.2)));
		Group christSpheres = A07.makeChristSphere(kranzK); 
		Plane loch = new Plane(0.1, Point.point(0, 0.5, -3.75), Direction.direction(0, 0, 1), new Diffuse(br));

		Group kranz = new Group(new Shape[] {kranzK, christSpheres, loch}, new Transformation(translation(direction(0, 0.75, 0))));
//-------------------------------------------------------------------------------------------------------------------------- 		
		Group logLineL = A07.makeLogWall(-2.5, -0.75);
		Group logLineR = A07.makeLogWall(0.5, -0.75);
		
		Group logLineFrontWall = new Group(new Shape[] {logLineL, logLineR});
//-------------------------------------------------------------------------------------------------------------------------- 
		Group logtop = A07.makeLogTop(0, 0, 0, 6);
		Group logtop1 = A07.makeLogTop1(0, 0, 0, 6);
		
		Group logLineTopV = new Group(new Shape[] {logtop}, new Transformation(translation(direction(-3, 2.25, -4))));
		Group logLineTopH = new Group(new Shape[] {logtop1}, new Transformation(translation(direction(-3, 2.25, -9.5))));
		Group logLineTopR = new Group(new Shape[] {logtop1}, new Transformation(multiply(translation(direction(-3, 2.25, -4)), rotation(yAxis, +90))));
		Group logLineTopL = new Group(new Shape[] {logtop}, new Transformation(multiply(translation(direction(3, 2.25, -4)), rotation(yAxis, +90))));
		
		Group roof = new Group(new Shape[] {logLineTopV, logLineTopH, logLineTopR, logLineTopL});
		
		Group logDoor = A07.makeDoor(-0.39, -1);
		
		Group g = new Group(new Shape[6], new Transformation(translation(direction(0, -1, 0))));
		Group wallback = A08.makeZylinderTreppe(g, 0, 0.25, 0, 0, 5.5, 5, 0);
		
		
		Group kerze = A07.makeKerzenStand(-0.75);
		Group kerze1 = A07.makeKerzenStand(0.75);
		
		Group walkWay = A07.makeWalkWay(snow, 30);
		Group bushL = A07.makeBushes(-0.75, -3.5, 34);
		Group bushR = A07.makeBushes(0.75, -3.5, 34);
		
		Group way = new Group(new Shape[] {walkWay, bushL, bushR, kerze, kerze1});
		Group way1 = new Group(new Shape[] {walkWay, bushL, bushR, kerze, kerze1}, new Transformation(translation(direction(12, 0, 0))));
		Group way2 = new Group(new Shape[] {walkWay, bushL, bushR, kerze, kerze1}, new Transformation(translation(direction(-12, 0, 0))));
		
		Group house = new Group(new Shape[] {wallback, logLineFrontWall, pfeilerR, pfeilerL, roof, logDoor, kranz});
		Group house1 = new Group(new Shape[] {house}, new Transformation(translation(direction(12, 0, 0))));
		Group house2 = new Group(new Shape[] {house}, new Transformation(translation(direction(-12, 0, 0))));
		
		Group main = new Group(new Shape[] {bg, house, house1, house2, environment, way, way1, way2, see});
		
		LochKamera camera = new LochKamera(width, height, Math.PI / 2, mSeite, 0, Double.POSITIVE_INFINITY);
		
		Raytracer raytracer = new Raytracer(camera, main, 6);
		
		image.sample(new StratifiedSampler(raytracer, 50));
		
		image.write(filename);
		System.out.println("Wrote image: " + filename);
	}

}
