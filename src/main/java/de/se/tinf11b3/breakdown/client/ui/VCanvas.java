/*
 * Copyright 2010 IT Mill Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package de.se.tinf11b3.breakdown.client.ui;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.DirectShapeRenderer;
import gwt.g2d.client.graphics.KnownColor;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.canvas.CanvasPattern;
import gwt.g2d.client.graphics.shapes.Shape;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Rectangle;
import gwt.g2d.client.math.Vector2;
import gwt.g2d.client.util.FpsTimer;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.Field;

/**
 * This class implements a basic client side rich text editor component.
 * 
 * @author IT Mill Ltd.
 * 
 */
public class VCanvas extends Composite implements Paintable, Field,
		ChangeHandler, BlurHandler {

	/**
	 * The input node CSS classname.
	 */
	public static final String CLASSNAME = "v-richtextarea";
	protected String id;
	protected ApplicationConnection client;
	private final FlowPanel flowPanel = new FlowPanel();
	private boolean aktiviert = false;
	private final Surface surface = new Surface(500, 500);
	private DirectShapeRenderer shapeRenderer = new DirectShapeRenderer(surface);
	private Particle p = new Particle(
			new Vector2(Random.nextInt(WIDTH), Random.nextInt(HEIGHT)),
			new Vector2(Math.random(), Math.random()).normalize(),
			new Color(Random.nextInt(256),
					Random.nextInt(256),
					Random.nextInt(256),
					1.0));
	private static int WIDTH = 500;
	private static int HEIGHT = 500;
	
	
	private int mouseX;
	private int mouseY;
	
	
	public VCanvas() {
		
		surface.fillBackground(KnownColor.CORNFLOWER_BLUE).setFillStyle(KnownColor.ALICE_BLUE);

		
	    	surface.save()
	          .setFillStyle(KnownColor.RED);
	      shapeRenderer.beginPath()
	              .moveTo(new Vector2(100, 100))
	              .drawCircle(new Vector2(10, 10), 20)
	              .closePath()
	              .fill();              
	      surface.restore();
		
		
		
		
		
		// surface.setFillStyle(KnownColor.BLACK).fillShape(new
		// ShapeBuilder().drawCircle(250, 400, 5).build());

		// Stift in Rot
		// surface.setFillStyle(KnownColor.RED);
		surface.setLineWidth(10);
		surface.setStrokeStyle(KnownColor.RED);

		// Let the show begin!
//		ShapeBuilder sb = new ShapeBuilder();
//		sb.drawLineSegment(new Vector2(200, 480), new Vector2(300, 480));
//		Shape shape = sb.build();
//		surface.strokeShape(shape);

		
//		new Cycle zum Warten!
		
		flowPanel.add(surface);

		surface.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				mouseX = event.getX();
				mouseY = event.getY();

				if(event.getX() <= 450 && event.getX() >= 50) {
					surface.setFillStyle(KnownColor.CORNFLOWER_BLUE);
					surface.fillShape(new ShapeBuilder().drawRect(new Rectangle(0, 460, 500, 100)).build());

					// Let the show begin!
					ShapeBuilder sb = new ShapeBuilder();
					sb.drawLineSegment(new Vector2(event.getX() - 50, 480), new Vector2(event.getX() + 50, 480));
					Shape shape = sb.build();
					surface.setStrokeStyle(KnownColor.BLACK);
					surface.strokeShape(shape);
					
				}

			}
		});

		surface.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
			}
		});

		surface.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				
//				for(int i=0; i<100; i++){
//					update();
//				}
				
				FpsTimer timer = new FpsTimer(100) {
					
					@Override
					public void update() {
						Vector2 pos = p.getPosition();
					      Vector2 vel = p.getVelocity();
					      if (pos.getX() < 0) {
					        vel.setX(Math.abs(vel.getX()));
					      } else if (pos.getX() >= WIDTH) {
					        vel.setX(-Math.abs(vel.getX()));
					      }
					      if (pos.getY() < 0) {
					        vel.setY(Math.abs(vel.getY()));
					      } else if (pos.getY() >= HEIGHT) {
					        vel.setY(-Math.abs(vel.getY()));
					      }
					      pos.mutableAdd(p.getVelocity());
					    draw();
					}
				};
				
				timer.start();
				
			}
		});

		initWidget(flowPanel);
		setStyleName(CLASSNAME);
	}

	public void updateFromUIDL(final UIDL uidl, ApplicationConnection client) {
		this.client = client;
		id = uidl.getId();

		if(client.updateComponent(this, uidl, true)) {
			return;
		}
	}

	/**
	 * Method is public to let popupview force synchronization on close.
	 */
	public void synchronizeContentToServer() {
	}

	public void onBlur(BlurEvent event) {
		synchronizeContentToServer();
	}

	public void onChange(ChangeEvent event) {
	}
	
	
	
	
	public void update() {
	      Vector2 pos = p.getPosition();
	      Vector2 vel = p.getVelocity();
	      if (pos.getX() < 0) {
	        vel.setX(Math.abs(vel.getX()));
	      } else if (pos.getX() >= WIDTH) {
	        vel.setX(-Math.abs(vel.getX()));
	      }
	      if (pos.getY() < 0) {
	        vel.setY(Math.abs(vel.getY()));
	      } else if (pos.getY() >= HEIGHT) {
	        vel.setY(-Math.abs(vel.getY()));
	      }
	      pos.mutableAdd(p.getVelocity());
	      
	      draw();
	      
	      
	      //DRAW PADDLE
	      surface.setFillStyle(KnownColor.CORNFLOWER_BLUE);
			surface.fillShape(new ShapeBuilder().drawRect(new Rectangle(0, 460, 500, 100)).build());

			// Let the show begin!
			ShapeBuilder sb = new ShapeBuilder();
			sb.drawLineSegment(new Vector2(this.mouseX - 50, 480), new Vector2(this.mouseX + 50, 480));
			Shape shape = sb.build();
			surface.setStrokeStyle(KnownColor.BLACK);
			surface.strokeShape(shape);
	      
	      
	      
	      
	  }
	  
	  /**
	   * Draws the particles.
	   */
	  private void draw() {
		  surface.clear().fillBackground(KnownColor.CORNFLOWER_BLUE);
		  surface.save()
	          .setFillStyle(p.getColor());
	      shapeRenderer.beginPath()
	              .moveTo(p.getPosition())
	              .drawCircle(p.getPosition(), 10)
	              .closePath()
	              .fill();              
	      surface.restore();
	  }
	  
	  /**
	   * Simple particle class.
	   * 
	   * @author hao1300@gmail.com
	   */
	  private static class Particle {
	    private final Vector2 position, velocity;
	    private final Color color;
	    
	    public Particle(Vector2 position, Vector2 velocity, Color color) {
	      this.position = position;
	      this.velocity = velocity;
	      this.color = color;
	    }
	    
	    public final Color getColor() {
	      return color;
	    }
	    
	    public final Vector2 getPosition() {
	      return position;
	    }
	    
	    public final Vector2 getVelocity() {
	      return velocity;
	    }
	
	  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
