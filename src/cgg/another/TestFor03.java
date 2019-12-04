package cgg.another;

import cgg.scene.LochKamera;
import cgtools.Matrix;

public class TestFor03 {
	
	public static void two() {
		LochKamera cam = new LochKamera(10, 10, Math.PI/2, Matrix.identity, 0, Double.POSITIVE_INFINITY);
		
		
		//Test for 3.2
		System.out.println("0, 0 " + cam.getRayThroughPoint(0, 0));
		System.out.println("5, 5 " + cam.getRayThroughPoint(5, 5));
		System.out.println("10, 10 " + cam.getRayThroughPoint(10, 10));
	}
	
	public static void three() {
		//Test for 3.3
//		KugelSurface kugel = new KugelSurface(Point.point(0, 0, -2), 1, Color.gray);
//		KugelSurface kugel1 = new KugelSurface(Point.point(0, -1, -2), 1, Color.gray);
//		KugelSurface kugel2 = new KugelSurface(Point.point(0, 0, -4), 1, Color.gray);
		
		
//		Hit hit = kugel.intersect(new Ray(Point.point(0, 0, 0), Direction.direction(0, 0, -1), 0, Double.MAX_VALUE));
//		Hit hit1 = kugel.intersect(new Ray(Point.point(0, 0, 0), Direction.direction(0, 1, -1), 0, Double.MAX_VALUE));
//		
//		Hit hit2 = kugel1.intersect(new Ray(Point.point(0, 0, 0), Direction.direction(0, 0, -1), 0, Double.MAX_VALUE));
//		
//		Hit hit3 = kugel.intersect(new Ray(Point.point(0, 0, -4), Direction.direction(0, 0, -1), 0, Double.MAX_VALUE));
//		
//		Hit hit4 = kugel2.intersect(new Ray(Point.point(0, 0, 0), Direction.direction(0, 0, -1), 0, 2));
		
//		System.out.println(hit);//treffer
//		
//		System.out.println(hit1);
//		
//		System.out.println(hit2); // treffer
//		
//		System.out.println(hit3);
//		System.out.println(hit4);
	}
}
