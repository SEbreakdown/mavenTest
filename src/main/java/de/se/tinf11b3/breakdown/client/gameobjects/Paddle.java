package de.se.tinf11b3.breakdown.client.gameobjects;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.Shape;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;


public class Paddle extends Gameobject {

	private double size;
	

	/**
	 * Init with Values
	 * 
	 * @param x
	 * @param y
	 * @param size
	 */
	public Paddle(int x, int y, Color color, double size, Surface surface) {
		super(x, y, color, surface);
		this.size = size;
	}

	public double getSize() {
		return size;
	}

	
	@Override
	public void drawObject() {
	
		// Paddle zeichnen
		ShapeBuilder sb = new ShapeBuilder();
			sb.drawLineSegment(new Vector2(x - (size/2), y), new Vector2(x + (size/2), y));
		Shape shape = sb.build();
		surface.setStrokeStyle(color);
		surface.setLineWidth(10);
		surface.strokeShape(shape);
	}
	

	public void setSize(double size) {
		this.size = size;
	}
	
	
	
	
}
