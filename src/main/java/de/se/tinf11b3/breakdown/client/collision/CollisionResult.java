package de.se.tinf11b3.breakdown.client.collision;

import gwt.g2d.client.math.Vector2;

/**
 * Stellt ein Kollisionsergenis dar
 * @author michael
 *
 */
public class CollisionResult {

	private boolean isCollided;
	private Vector2 collisionPoint;
	private double distanceSq;

	public CollisionResult(boolean isCollided, Vector2 collisionPoint, double distanceSq) {
		super();
		this.isCollided = isCollided;
		this.collisionPoint = collisionPoint;
		this.distanceSq = distanceSq;
	}

	public boolean isCollided() {
		return isCollided;
	}

	public Vector2 getCollisionPoint() {
		return collisionPoint;
	}

	public double getDistanceSq() {
		return distanceSq;
	}

}

/*Vector2 PointLineDist(Vector2 point, Vector2 linestart, Vector2 lineend)
{
Vector2 a = lineend - linestart;
Vector2 b = point - linestart;
double t = dot(a, b)/(length(a)²);
if (t < 0) t = 0;
if (t > 1) t = 1;
return linestart + a * t;
}

struct CollisionResult
{
bool isCollided;
Vector2 collisionPoint;
float distanceSq;
}

//Gibt zurück, ob eine Kollision stattfand und wenn ja, wo, und wie lang der (minimale, quadratische) Abstand zum Rechteck ist.
CollisionResult RectangleCircleKollision(Rect rect, Circle circle)
{
float minDistSq = HUGE_VAL;
Vector2 basePoint = Vector2(0,0);
// Seiten durchgehen, Schleife kann (bzw muss, je nachdem wie Rect aussieht) entrollt werden
for(int i=0; i<4 ;i++)
{
      Vector2 base = PointLineDist(circle.mid, rect.points[i], rect.points[(i+1) % 4]);
      if(lengthsq(circle.mid-base)<minDistSq)
      {
            // Kürzerer Abstand, neu zuweisen.
            minDistSq = lengthsq(circle.mid - base);
            basePoint = base;
      }
}
return {minDistSq < circle.radius * circle.radius,
        basePoint,
        minDistSq};
}*/