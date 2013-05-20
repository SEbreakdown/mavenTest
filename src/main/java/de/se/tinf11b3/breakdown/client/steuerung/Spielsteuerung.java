package de.se.tinf11b3.breakdown.client.steuerung;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

import de.se.tinf11b3.breakdown.client.collision.CollisionResult;
import de.se.tinf11b3.breakdown.client.gameobjects.Ball;
import de.se.tinf11b3.breakdown.client.gameobjects.Block;
import de.se.tinf11b3.breakdown.client.gameobjects.Paddle;
import de.se.tinf11b3.breakdown.client.ui.VCanvas;
import gwt.g2d.client.graphics.KnownColor;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.math.Circle;
import gwt.g2d.client.math.Rectangle;
import gwt.g2d.client.math.Vector2;
import gwt.g2d.client.util.FpsTimer;

public class Spielsteuerung {

	private Surface surface;
	// private ShapeBuilder sb = new ShapeBuilder();

	private boolean gameStarted = false;
	private Paddle paddle;
	private Ball ball;
	// private Block bloecke[] = new Block[15];
	private ArrayList<Block> bloecke = new ArrayList<Block>();
	private int x_direction = 5, y_direction = -5;
	private VCanvas app;
	// private Particle p = new Particle(
	// new Vector2(Random.nextInt(VCanvas.WIDTH),
	// Random.nextInt(VCanvas.HEIGHT)),
	// new Vector2(Math.random(), Math.random()).normalize(),
	// new Color(Random.nextInt(256),
	// Random.nextInt(256),
	// Random.nextInt(256),
	// 1.0));

	public Spielsteuerung(final Surface surface, final VCanvas app) {
		this.surface = surface;
		this.app = app;

		surface.fillBackground(VCanvas.BACKGROUNDCOLOR);

		// Init Paddle
		paddle = new Paddle(250, 480, KnownColor.BLACK, 100, surface);

		// Init Ball
		ball = new Ball(250, 465, KnownColor.GREEN, 10, surface);

		// Init Blocks
		initLevel(surface);

		// Draw them all
		drawAllObjects();

		// Move Paddle along the Canvas
		surface.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				int mouseX = event.getX();

				// Innerhalb des Canvas
				if(event.getX() <= 450 && event.getX() >= 50) {
					paddle.setX(mouseX);

					if(gameStarted == false) {
						ball.setX(mouseX);
						ball.drawObject();
					}
					drawAllObjects();
				}

			}
		});

		surface.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
			}
		});

		surface.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				app.pushToServer("Hello, this is Client :P");
				
				if(gameStarted == false) {

					gameStarted = true;

					FpsTimer timer = new FpsTimer(60) {
						@Override
						public void update() {

							checkCollision();

							ball.setY(ball.getY() + y_direction);
							ball.setX(ball.getX() + x_direction);

							drawAllObjects();
						}

					};

					timer.start();
				}

			}
		});

	}

	private void checkCollision() {
		checkFrameCollision();
		checkPaddleCollision();
		checkBlockCollision();
	}

	private void checkBlockCollision() {
//		for(int i = 0; i < bloecke.size(); i++) {
//			Block tmp = bloecke.get(i);
//			Vector2 position = new Vector2(tmp.getX(), tmp.getY());
//			double width = tmp.getSize().getX();
//			double height = tmp.getSize().getY();
//
//			Rectangle rec = new Rectangle(position, width, height);
////			boolean hit = checkIntersection(rec, new Circle(ball.getX(), ball.getY(), ball.getRadius()));
//			Circle circ = new Circle(ball.getX(), ball.getY(), ball.getRadius());
//			boolean hit = RectangleCircleKollision(rec, circ).isCollided();
//			
//			
//			if(hit) {
//				bloecke.remove(tmp);
//			}
//		}
		
		Vector2 position = new Vector2(bloecke.get(13).getX(), bloecke.get(13).getY());
		double width = bloecke.get(13).getSize().getX();
		double height = bloecke.get(13).getSize().getY();

//		app.pushToServer("x="+String.valueOf(position.getX()));
//		app.pushToServer("y="+String.valueOf(position.getY()));
//		app.pushToServer("w="+String.valueOf(width));
//		app.pushToServer("h="+String.valueOf(height));
		
		Rectangle rec = new Rectangle(position, width, height);
		Circle circ = new Circle(ball.getX(), ball.getY(), ball.getRadius());
		app.pushToServer("x="+String.valueOf(circ.getCenterX()));
		app.pushToServer("y="+String.valueOf(circ.getCenterY()));
		app.pushToServer("r="+String.valueOf(circ.getRadius()));
		boolean hit = RectangleCircleKollision(rec, circ).isCollided();
		
		
		if(hit) {
			bloecke.remove(13);
		}
		
	}
	
	
	/**
	 * Skalarprodukt
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private double dot(Vector2 a, Vector2 b) {
		return a.getX()*b.getX()+a.getY()*b.getY();
	}
	
	/**
	 * Laenge des Vektors im Quadrat (spart Wurzelrechnung ein)
	 * 
	 * @param a
	 * @return
	 */
	private double sqr_length(Vector2 a){
		return dot(a,a);
	}
	
	/**
	 * Vektor mal Skalar
	 * @param a
	 * @param t
	 * @return
	 */
	private Vector2 vec_mal_scalar(Vector2 a, double t){
		return new Vector2(a.getX()*t, a.getY()*t);
	}
	
	private Vector2 vec_plus_vec(Vector2 a, Vector2 b){
		return new Vector2(a.getX()+b.getX(), a.getY()+b.getY());
	}
	private Vector2 vec_minus_vec(Vector2 a, Vector2 b){
		return new Vector2(a.getX()-b.getX(), a.getY()-b.getY());
	}
	
	//TODO CODE UMSCHREIBEN
	
	Vector2 PointLineDist(Vector2 point, Vector2 linestart, Vector2 lineend)
	{
	      Vector2 a = new Vector2(lineend.getX() - linestart.getX(), lineend.getY()-linestart.getY());
	      Vector2 b = new Vector2(point.getX() - linestart.getX(), point.getY()-linestart.getY());
	      double t = dot(a, b)/(sqr_length(a));
	      
	      if (t < 0) t = 0;
	      if (t > 1) t = 1;
	      return vec_plus_vec(linestart, vec_mal_scalar(a,t));
	}
	 
	 
	// Gibt zurück, ob eine Kollision stattfand und wenn ja, wo, und wie lang der (minimale, quadratische) Abstand zum Rechteck ist.
	CollisionResult RectangleCircleKollision(Rectangle rect, Circle circle)
	{
		double x = rect.getX() - circle.getCenterX();
		double y = rect.getY() - circle.getCenterY();
		double h = rect.getHeight();
		double w = rect.getWidth();

		Vector2 p1 = new Vector2(x, y);
		Vector2 p2 = new Vector2(x + w, y);
		Vector2 p3 = new Vector2(x + w, y + h);
		Vector2 p4 = new Vector2(x, y + h);
		
		
	      double minDistSq = 8000;
	      Vector2 basePoint = new Vector2(0,0);
	      
	      // Seiten durchgehen, Schleife kann (bzw muss, je nachdem wie Rect aussieht) entrollt werden
	      Vector2 base = PointLineDist(circle.getCenter(), p1, p2);
	      if(sqr_length(vec_minus_vec(circle.getCenter(), base))<minDistSq)
	      {
	            // Kürzerer Abstand, neu zuweisen.
	             minDistSq = sqr_length(vec_minus_vec(circle.getCenter(),base));
	             basePoint = base;
	      }
	      
	      base = PointLineDist(circle.getCenter(), p2, p3);
	      if(sqr_length(vec_minus_vec(circle.getCenter(), base))<minDistSq)
	      {
	            // Kürzerer Abstand, neu zuweisen.
	             minDistSq = sqr_length(vec_minus_vec(circle.getCenter(),base));
	             basePoint = base;
	      }
	      
	      base = PointLineDist(circle.getCenter(), p3, p4);
	      if(sqr_length(vec_minus_vec(circle.getCenter(), base))<minDistSq)
	      {
	            // Kürzerer Abstand, neu zuweisen.
	             minDistSq = sqr_length(vec_minus_vec(circle.getCenter(),base));
	             basePoint = base;
	      }
	      base = PointLineDist(circle.getCenter(), p4, p1);
	      if(sqr_length(vec_minus_vec(circle.getCenter(), base))<minDistSq)
	      {
	            // Kürzerer Abstand, neu zuweisen.
	             minDistSq = sqr_length(vec_minus_vec(circle.getCenter(),base));
	             basePoint = base;
	      }
	      
	      CollisionResult result = new CollisionResult(minDistSq < circle.getRadius() * circle.getRadius(), basePoint, minDistSq);
	      
	      return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	/**
	 * http://mathworld.wolfram.com/Circle-LineIntersection.html
	 * 
	 * @param rectangle
	 * @param circle
	 * @return
	 */
	private boolean checkIntersection(Rectangle rectangle, Circle circle) {

		double x = rectangle.getX() - circle.getCenterX();
		double y = rectangle.getY() - circle.getCenterY();
		double h = rectangle.getHeight();
		double w = rectangle.getWidth();

		Vector2 p1 = new Vector2(x, y);
		Vector2 p2 = new Vector2(x + w, y);
		Vector2 p3 = new Vector2(x + w, y + h);
		Vector2 p4 = new Vector2(x, y + h);

		boolean s1 = checkIntersection(p1, p2, circle);
		boolean s2 = checkIntersection(p2, p3, circle);
		boolean s3 = checkIntersection(p4, p3, circle);
		boolean s4 = checkIntersection(p1, p4, circle);

		return(s1 || s2 || s3 || s4);
	}

	private boolean checkIntersection(Vector2 p1, Vector2 p2, Circle circle) {
		double dx = p2.getX() - p1.getX();
		double dy = p2.getY() - p1.getY();

		double dr = Math.sqrt(dx * dx + dy * dy);
		double det = p1.getX() * p2.getY() - p2.getX() * p1.getY();

		double delta = (circle.getRadius() * circle.getRadius()) * (dr * dr)
				- (det * det);

		if(delta >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private void checkPaddleCollision() {
		// TODO Auto-generated method stub

	}

	private void checkFrameCollision() {
		// Check Y-Rand Collision
		if((ball.getY() <= 15) || (ball.getY() >= 475)) {
			y_direction *= -1;
		}

		// Check X-Rand Collision
		if((ball.getX() <= 0) || (ball.getX() >= 495)) {
			x_direction *= -1;
		}
	}

	private void drawAllObjects() {
		surface.clear().fillBackground(KnownColor.CORNFLOWER_BLUE);
		ball.drawObject();
		drawBlocks();
		paddle.drawObject();
	}

	private void drawBlocks() {
		for(Block tmp : bloecke) {
			tmp.drawObject();
		}
	}

	/**
	 * Inits Level 1 with the Blogs
	 * 
	 * @param surface
	 */
	private void initLevel(Surface surface) {

		int x = 30;
		int y = 10;

		for(int i = 0; i < 15; i++) {
			bloecke.add(new Block(x, y, KnownColor.RED, new Vector2(80, 30), surface));
			x += 90;
			if((i + 1) % 5 == 0) {
				y += 50;
				x = 30;
			}
		}
	}

	// public void update() {
	// Vector2 pos = p.getPosition();
	// Vector2 vel = p.getVelocity();
	// if (pos.getX() < 0) {
	// vel.setX(Math.abs(vel.getX()));
	// } else if (pos.getX() >= VCanvas.WIDTH) {
	// vel.setX(-Math.abs(vel.getX()));
	// }
	// if (pos.getY() < 0) {
	// vel.setY(Math.abs(vel.getY()));
	// } else if (pos.getY() >= VCanvas.HEIGHT) {
	// vel.setY(-Math.abs(vel.getY()));
	// }
	// pos.mutableAdd(p.getVelocity());
	//
	// draw();
	//
	//
	// //DRAW PADDLE
	// surface.setFillStyle(KnownColor.CORNFLOWER_BLUE);
	// surface.fillShape(new ShapeBuilder().drawRect(new Rectangle(0, 460, 500,
	// 100)).build());
	//
	// // Let the show begin!
	// ShapeBuilder sb = new ShapeBuilder();
	// sb.drawLineSegment(new Vector2(this.mouseX - 50, 480), new
	// Vector2(this.mouseX + 50, 480));
	// Shape shape = sb.build();
	// surface.setStrokeStyle(KnownColor.BLACK);
	// surface.strokeShape(shape);
	//
	//
	//
	//
	// }

	/**
	 * Draws the particles.
	 */
	// private void draw() {
	// surface.clear().fillBackground(KnownColor.CORNFLOWER_BLUE);
	// surface.save()
	// .setFillStyle(p.getColor());
	// shapeRenderer.beginPath()
	// .moveTo(p.getPosition())
	// .drawCircle(p.getPosition(), 10)
	// .closePath()
	// .fill();
	// surface.restore();
	// }

	/**
	 * Simple particle class.
	 * 
	 * @author hao1300@gmail.com
	 */
	// private static class Particle {
	// private final Vector2 position, velocity;
	// private final Color color;
	//
	// public Particle(Vector2 position, Vector2 velocity, Color color) {
	// this.position = position;
	// this.velocity = velocity;
	// this.color = color;
	// }
	//
	// public final Color getColor() {
	// return color;
	// }
	//
	// public final Vector2 getPosition() {
	// return position;
	// }
	//
	// public final Vector2 getVelocity() {
	// return velocity;
	// }
	//
	// }

}
