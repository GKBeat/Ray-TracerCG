package cgg.a02;

import java.util.Comparator;

public class RadCompare implements Comparator<Circle> {

	@Override
	public int compare(Circle c1, Circle c2) {
		if (c1.radius < c2.radius) {
			return 1;
		} else if (c1.radius > c2.radius) {
			return -1;
		} else {
			return 0;
		}
	}

}
