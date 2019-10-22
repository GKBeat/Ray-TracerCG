package cgg.a01;

import cgtools.*;
import static cgtools.Color.*;

import cgg.Image;

public class A01 {

	public static void main(String[] args) {
		final int width = 160;
		final int height = 90;
		final String filename1_4 = "doc/a01-image.png";
		final String filename1_5 = "doc/a01-square.png";
		final String filename1_6 = "doc/a01-checkered-background.png";

		class ConstantColor {
			Color color;

			ConstantColor(Color color) {
				this.color = color;
			}

			Color getColor(double x, double y) {
				return color;
			}
		}

		Image image = new Image(width, height);

		ConstantColor allGreen = new ConstantColor(green);
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				image.setPixel(x, y, allGreen.getColor(x, y));
			}
		}

		image.write(filename1_4);
		System.out.println("Wrote image: " + filename1_4);

//--------------------------------------------------------------------------------------------------------------------------  

		class ColoredSquare {
			Color color;

			ColoredSquare(Color color) {
				this.color = color;
			}

			Color getColor(int x, int y) {

				boolean xCheck = x > (width / 2) - (width / 8) && x < (width / 2) + (width / 8);
				boolean yCheck = y > (height / 2) - (width / 8) && y < (height / 2) + (width / 8);

				// schaut ob die gegebenen x und y koordinaten in einem bestimmten bereich sind,
				// falls ja, wechselt er die Farbe
				if (xCheck && yCheck) {
					return color;
				} else {
					return black;
				}
			}
		}

		ColoredSquare whiteBox = new ColoredSquare(white);
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {
				image.setPixel(x, y, whiteBox.getColor(x, y));
			}
		}

		image.write(filename1_5);
		System.out.println("Wrote image: " + filename1_5);

//-------------------------------------------------------------------------------------------------------------------------- 

		class ColoredChecker {
			Color colorSquare;
			Color checkerColorA;
			Color checkerColorB;

			ColoredChecker(Color colorSquare, Color checkerColorA, Color checkerColorB) {
				this.colorSquare = colorSquare;
				this.checkerColorA = checkerColorA;
				this.checkerColorB = checkerColorB;
			}

			Color getColor(double checkerPatternX, double checkerPatternY, int squareX, int squareY) {

				boolean xCheckSquare = squareX > (width / 2) - (width / 8) && squareX < (width / 2) + (width / 8);
				boolean yCheckSquare = squareY > (height / 2) - (width / 8) && squareY < (height / 2) + (width / 8);

				if (xCheckSquare && yCheckSquare) {
					return colorSquare;

					// hier wird geschaut ob die Rechnungen von davor, addiert, gerade ist, wenn ja
					// dann wird die Farbe gewechselt
					// sonst wird die andere Farbe gewählt, so kommt dann am Ende das
					// Schachbrettmuster zustande
				} else if (((checkerPatternX + checkerPatternY)) % 2 == 0) {
					return checkerColorA;
				} else {
					return checkerColorB;
				}
			}
		}

		ColoredChecker test = new ColoredChecker(red, black, gray);
		for (int x = 0; x != width; x++) {
			for (int y = 0; y != height; y++) {

				// Rechnung die am Ende für das Schachbrettmuster verantwortlich ist
				double checkerPatternX = x / (width / (width / 10));
				double checkerPatternY = y / (width / (width / 10));

				image.setPixel(x, y, test.getColor(checkerPatternX, checkerPatternY, x, y));
			}
		}

		image.write(filename1_6);
		System.out.println("Wrote image: " + filename1_6);

	}
}
