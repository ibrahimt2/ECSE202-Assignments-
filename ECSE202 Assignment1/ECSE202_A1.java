import java.awt.Color;

import acm.graphics.*;
import acm.program.*;
import java.awt.color.*;

public class Miraj_260791021 extends GraphicsProgram {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 800;
	private static final double TIME_OUT = 100 ;
	private static final double INTERVAL_TIME = 0.1;

	public void run() {
		;
		this.resize(WIDTH, HEIGHT);
		GOval Ball = new GOval(0, 400, 40, 40);
		GLine Ground = new GLine(0, 600, 600, 600);

		add(Ball);
		add(Ground);

		Ball.setFilled(true);
		Ball.setColor(Color.RED);

		double initialUpPositon = 0;
		double h0 = readDouble("Enter the initial height of the ball: ");
		double loss = readDouble("Enter the ratio of energy lost by the ball (0 < loss < 1): ");
		double t = 0;
		boolean directionUp = false;
		double g = 9.81;
		double vy = Math.pow(2.0 * g * h0, 0.5);
		double lossfactor = Math.pow(1 - loss, 0.5);
		double h; // current position
		double vx = 4;
		double totalTime = 0;
		double xPos = 0;

		while (totalTime < TIME_OUT) {
			double initialUpPosition = 0;
			if (directionUp == false) {
				h = h0 - 0.5 * g * t * t;

				if (h <= 0) {
					h0 = h;
					initialUpPosition = h;
					directionUp = true;
					t = 0;
					vy = vy * lossfactor;

				}
			} else {
				h = initialUpPosition + vy * t - 0.5 * g * t * t;

				if (h > h0) {
					h0 = h;

				} else {
					directionUp = false;
					t = 0;
				}
			}

			println("Time: " + totalTime + " X: " + xPos + " Y: " + h);
			xPos = xPos + vx * INTERVAL_TIME;
			t += INTERVAL_TIME;
			totalTime += INTERVAL_TIME;
			pause(INTERVAL_TIME * 150);

			add(new GOval(xPos + 20, 580 - h, 1, 1));
			Ball.setLocation(xPos, 560 - h);
		}

	}

}
