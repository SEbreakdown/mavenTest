package de.se.tinf11b3.breakdown.client.gameobjects;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.DirectShapeRenderer;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.math.Vector2;

public class Ball extends Gameobject {

	private int radius;
	private DirectShapeRenderer shapeRenderer;

	/**
	 * Init with Values
	 * 
	 * @param x
	 * @param y
	 * @param color
	 * @param radius
	 */
	public Ball(int x, int y, Color color, int radius, Surface surface) {
		super(x, y, color, surface);
		this.radius = radius;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public void drawObject() {

		shapeRenderer = new DirectShapeRenderer(surface);

		// Kugel
		surface.setFillStyle(color);
		shapeRenderer.drawCircle(new Vector2(x, y), radius).fill();
	}

}
