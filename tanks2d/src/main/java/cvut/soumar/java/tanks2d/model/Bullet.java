package cvut.soumar.java.tanks2d.model;

import cvut.soumar.java.tanks2d.data.Constants;


/**
 * Bullet sprite class
 */
public class Bullet extends Sprite{

    private final double travelSpeed;
    private Point vector = new Point(0,0);
    private Point center;
    private final double radius;
    private int directionX, directionY;
    private long timeShot;
    private final long collisionDelay = 2 * 100_000_000;

    public Bullet(String imageFile, double w, double h, double centerX, double centerY, Point vector, double travelSpeed, long now) {
        super(imageFile, w, h);

        center = new Point(centerX, centerY);

        this.radius = height/2;

        x = this.center.x - radius;
        y = this.center.y - radius;

        this.travelSpeed = travelSpeed;
        this.vector = new Point(vector.x, vector.y);

        timeShot = now;

        setStartDirection();
    }

    /**
     * updates the bullet position depending on its direction and speed
     */
    public void move(){
        double dirX = vector.x* travelSpeed;
        double dirY = vector.y* travelSpeed;

        x += dirX;
        y += dirY;

        center.x +=  dirX;
        center.y += dirY;
    }

    /**
     * set the X vector value to inverse value
     */
    public void changeDirectionX(){
        directionX *= -1;
        vector.x *= -1;
    }

    /**
     * set the Y vector value to inverse value
     */
    public void changeDirectionY(){
        directionY *= -1;
        vector.y *= -1;
    }

    /**
     * determine initial direction
     * direction is used in collision manager in order to create the right bounce
     */
    private void setStartDirection(){
        if (vector.x > 0){
            directionX = Constants.BULLETRIGHT;
        } else {
            directionX = Constants.BULLETLEFT;
        }

        if (vector.y > 0){
            directionY = Constants.BULLETDOWN;
        } else {
            directionY = Constants.BULLETUP;
        }
    }

    /**
     * Get the bullet time - how long since the bullet left the tank
     * @param now current time
     * @return bullet time
     */
    public double getTime(long now){
        return (now - timeShot);
    }

    /**
     * Check if the bullet time is smaller than collision delay in order for a tank
     * not to shoot itself rightaway
     * @param now current time
     * @return true if the bullet time is bigger than collisionDelay
     */
    public boolean collisionDelay(long now){
        return (now - timeShot) > collisionDelay;
    }

    public Point getVector() {
        return vector;
    }

    public double getCenterX() {
        return center.x;
    }

    public double getCenterY() {
        return center.y;
    }

    public double getRadius() {
        return radius;
    }

    public double getVectorX(){
        return vector.x;
    }

    public double getVectorY(){
        return vector.y;
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "vector=" + vector +
                ", center=" + center +
                ", radius=" + radius +
                ", timeShot=" + timeShot +
                '}';
    }
}
