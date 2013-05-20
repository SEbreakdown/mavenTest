package de.se.tinf11b3.breakdown.client.gameobjects;


import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.Shape;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

public class Block extends Gameobject {

	private Vector2 size;

	/**
	 * Init with given Values
	 * 
	 * @param x
	 * @param y
	 * @param color
	 * @param size
	 */
	public Block(int x, int y, Color color, Vector2 size, Surface surface) {
		super(x, y, color, surface);
		this.size = size;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	@Override
	public void drawObject() {
		// Block zeichnen
		ShapeBuilder sb = new ShapeBuilder();
			sb.drawRect(x, y, size.getX(), size.getY());
		Shape shape = sb.build();
		surface.setFillStyle(color);
		surface.fillShape(shape);		
	}

}
