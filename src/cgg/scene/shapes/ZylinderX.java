package cgg.scene.shapes;

import cgg.material.Material;
import cgg.scene.rays.Hit;
import cgg.scene.rays.Ray;
import cgtools.Direction;
import cgtools.Point;
import cgtools.Vector;

public class ZylinderX implements Shape {

	public final Point center;
	public final double radius;
	public final double height;
	public final Material material;
	
	private double yMin;
	private double yMax;

	public ZylinderX(Point center, double radius, double height, Material material) {
		this.center = center;
		this.radius = radius;
		this.height = height;
		this.material = material;
		yMin = center.x;
		yMax = center.x + height;
	}
	
	/* Zylinder Referencen:
	 * 
	 * https://www.cl.cam.ac.uk/teaching/1999/AGraphHCI/SMAG/node2.html#SECTION00023200000000000000
	 * https://www.math.uni-trier.de//~schulz/prosem0708/Raytracing.pdf
	 * http://woo4.me/wootracer/cylinder-intersection/
	 */
	
	@Override
	public Hit intersect(Ray r) {
		Direction x0 = Point.subtract(r.x0, center);
		Direction rd = r.d;
		
		//Die Formel für einen senkrechten Zylinder
		double a = Math.pow(rd.y, 2) + Math.pow(rd.z, 2);
		double b = 2 * ((x0.y * rd.y) + (x0.z * rd.z));
		double c = Math.pow(x0.y, 2) + Math.pow(x0.z, 2) - Math.pow(radius, 2);

		double d = Math.pow(b, 2) - (4 * a * c);

		double t1, t2;
		
		if(d < 0) {//kein treffer
			return null;
		}		
		
		//t1 und t2 werden berechnet
		t1 = (-b + Math.sqrt(d)) / (2 * a);
		t2 = (-b - Math.sqrt(d)) / (2 * a);

		// t1 und t2 werden sortiert sodas t1 kleiner ist als t2 (vereinfacht einige bedingungen)
		if (t1 > t2) {
			double tmp = t1;
			t1 = t2;
			t2 = tmp;
		}
		
		//der y punkt von t1 und t2 treffer werden aus gerechnet
		Point p1 = r.pointAt(t1);
		Point p2 = r.pointAt(t2);
		double y1 = p1.x;
		double y2 = p2.x;
		
		// Fall: Boden wird getroffen
		if(y1 < yMin) {
			if(y2 < yMin) { // kein reffer wenn beide y treffer kleiner als yMin sind
				return null;
			}else {
				return calcHit(t1, t2, p1, p2, r, true); // wie beim Kreis wird jetzt geschaut welcher treffer genommen wird
			}
		//Fall: der Body wird getroffen
		}else if(y1>=yMin && y1<=yMax) {
			if(t1 <= r.tMin) { //wenn t1 kleiner als 
				return null;
			}
			
			return calcHit(t1, t2, p1, p2, r, false);
			
		//Fall: die Decke wird getroffen
		} else if(y1 > yMax) {
			if(y2 > yMax) { // kein treffer wenn beide y werte größer als yMax sind
				return null;
			}			
			return calcHit(t1, t2, p1, p2, r, true);
		}
		return null;
	}
	
	private Hit calcHit(double t1, double t2, Point p1, Point p2, Ray r, boolean topOrBottom) {
		Direction dir;
		if(topOrBottom) {
			if((t2 <= r.tMin || t1 <= t2) && t1 >= r.tMin && t1 <= r.tMax) {
				dir = Point.subtract(p1,center);
				return new Hit(t1, p1, Vector.normalize(dir), material);
			}else if((t1 <= r.tMin || t2 <= t1) && t2 >= r.tMin && t2 <= r.tMax) {
				dir = Point.subtract(p2, center);
				return new Hit(t2, p2, Vector.normalize(dir), material);
			}
		}else {
			if((t2 <= r.tMin || t1 <= t2) && t1 >= r.tMin && t1 <= r.tMax) {
				dir = Point.subtract(p1, Point.point(p1.x, center.y, center.z));
				return new Hit(t1, p1, Vector.normalize(dir), material);
			}else if((t1 <= r.tMin || t2 <= t1) && t2 >= r.tMin && t2 <= r.tMax) {
				dir = Point.subtract(p2, Point.point(p2.x, center.y, center.z));
				return new Hit(t2, p2, Vector.normalize(dir), material);
			}
		}
		return null;
	}
}
