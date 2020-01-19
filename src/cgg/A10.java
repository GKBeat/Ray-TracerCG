package cgg;

import static cgtools.Matrix.multiply;
import static cgtools.Matrix.rotation;
import static cgtools.Matrix.translation;
import static cgtools.Vector.direction;
import static cgtools.Vector.xAxis;
import static cgtools.Vector.yAxis;

import cgg.material.BackgroundMaterial;
import cgg.material.Diffuse;
import cgg.material.Glass;
import cgg.material.Metall;
import cgg.sampler.Constant;
import cgg.sampler.PolkaDot;
import cgg.sampler.Raytracer;
import cgg.sampler.StratifiedSampler;
import cgg.sampler.Texture;
import cgg.sampler.TransformedTexture;
import cgg.scene.LochKamera;
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
import cgtools.Sampler;

public class A10 {

	public static void main(String[] args) {
		final int width = 1080;
		final int height = 720;
		final String filename = "doc/a10-1.png";
		final String filename1 = "doc/a10-2.png";
		
		Texture redSky = new Texture("doc/texture/bg/redDuskSky.jpg");
		Texture redishSky = new Texture("doc/texture/bg/sunsetRedish.jpg");
		Texture orangeSky = new Texture("doc/texture/bg/sunsetOrange.jpg");
		
		Texture sunSet = new Texture("doc/texture/bg/duskSunset.jpg");
		Texture sky = new Texture("doc/texture/bg/sky.jpg");
		
		Texture snow = new Texture("doc/texture/snow.jpg");
		Texture dirt = new Texture("doc/texture/dirt.jpg");
		Texture wand = new Texture("doc/texture/wand.png");
		Texture wood = new Texture("doc/texture/wood.jpg");
		
		Matrix id = Matrix.identity;
		Matrix kleinScaliert = Matrix.scaling(100, 100, 100);
				
		Image image = new Image(width, height);
		
		Sampler pd = new PolkaDot(new Color(0, 0.2, 0.2), new Color(0.9, 0.3, 0.2));
		Sampler pd1 = new PolkaDot(Color.white, Color.blue);
		
		Background bg = new Background(new BackgroundMaterial(sky));
		
		Shape globeWall = new Kugel(Point.point(0.25, -1, -3), 0.5, new Diffuse(wand));
		Shape globePDM = new Kugel(Point.point(0.25, 0, -3), 0.5, new Metall(new TransformedTexture(pd, Matrix.scaling(15, 15, 15)), 0));
		Shape woodstem = new ZylinderDeprecated(Point.point(-0.75, -1.5, -3), 0.5, 2,new Diffuse(wood));
		
		Shape groundD = new Plane(500, Point.point(0.0, -1.5, 0.0), Direction.direction(0, 1, 0), new Diffuse(new TransformedTexture(dirt, kleinScaliert)));

		Group main = new Group(new Shape[] {bg, groundD, globeWall, globePDM, woodstem});
		
		LochKamera camera = new LochKamera(width, height, Math.PI / 2, id, 0, Double.POSITIVE_INFINITY);
		
		Raytracer raytracer = new Raytracer(camera, main, 6);
		
		image.sample(new StratifiedSampler(raytracer, 250));
		
		image.write(filename);
		System.out.println("Wrote image: " + filename);
		
		
		
		
		Background bg1 = new Background(new BackgroundMaterial(orangeSky)); 
		Shape globeMetall = new Kugel(Point.point(0.25, -1, -3), 0.5, new Metall(wand, 0));
		Shape globePDD = new Kugel(Point.point(0.25, 0, -3), 0.5, new Diffuse(new TransformedTexture(pd1, Matrix.scaling(15, 15, 15))));
		Shape woodstemGL = new ZylinderDeprecated(Point.point(-0.75, -1.5, -3), 0.5, 2, new Glass(wood));
		Shape groundS = new Plane(500, Point.point(0.0, -1.5, 0.0), Direction.direction(0, 1, 0), new Diffuse(new TransformedTexture(snow, kleinScaliert)));
		
		Group main1 = new Group(new Shape[] {bg1, globeMetall, globePDD, woodstemGL, groundS});
		
		Raytracer raytracer1 = new Raytracer(camera, main1, 6);
		
		image.sample(new StratifiedSampler(raytracer1, 250));
		image.write(filename1);
		System.out.println("Wrote image: " + filename1);


		
	}

}
