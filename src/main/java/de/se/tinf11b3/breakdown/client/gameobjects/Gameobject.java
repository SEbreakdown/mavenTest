package de.se.tinf11b3.breakdown.client.gameobjects;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;


public abstract class Gameobject {
	
	protected int x;
	protected int y;
	protected Color color;
	protected Surface surface;
	
	/**
	 * Init a Gameobject with given Values
	 * @param x
	 * @param y
	 * @param color
	 */
	public Gameobject(int x, int y, Color color, Surface surface) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.surface = surface;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public abstract void drawObject();
	
	
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	}
	
	
}
