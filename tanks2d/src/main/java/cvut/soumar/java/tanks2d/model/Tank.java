package cvut.soumar.java.tanks2d.model;

import cvut.soumar.java.tanks2d.data.Constants;
import cvut.soumar.java.tanks2d.utils.AppLogger;

import java.util.LinkedList;


/**
 * Tank sprite class
 */
public class Tank extends Sprite{
    private final double moveSpeed, rotateSpeed;
    private Point pivot, shootPoint;
    private Point vector = new Point(0,0);
    private double currentAngle = 0;
    private boolean moving, movingF, movingB, rotating, rotatingL, rotatingR, collision = false;
    private int moveDirection, rotateDirection;
    private final static int firePointOffset = 20;
    private final int capacity;
    private int usedCapacity = 0;
    private double colliderSideLength = 36;
    private double colliderBarrelOffset = 12;

    private double hitboxOffset = -6;
    private boolean destroyed = false;
    private final LinkedList<Bullet> bullets = new LinkedList<>();

    private TankCollider hitbox;

    public Tank(String imageFile, double w, double h, double x, double y, double moveSpeed, double rotateSpeed, int capacity){
        super(imageFile, w, h);
        this.moveSpeed = moveSpeed;
        this.rotateSpeed = rotateSpeed;
        this.capacity = capacity;

        this.x = x;
        this.y = y;

        pivot = new Point(x + width/2, y + height/2);
        shootPoint = new Point(pivot.x, pivot.y);

        vector.x = 0;
        vector.y = -1;
        currentAngle = 0;

        //hitbox pos and size not the tanks
        hitbox = new TankCollider(pivot, colliderSideLength, colliderBarrelOffset, vector, hitboxOffset);
    }

    /**
     * Update the tank position depending on moving speed and direction, rotation speed and direction
     * Calculate direction vector and rotation angle
     * Update the main point such as pivot point, shoot point
     * Update collider
     */
    public void move(){

        if (!destroyed){
            if (rotatingR){
                rotateDirection = Constants.ROTATE_RIGHT;
            }

            if (rotatingL){
                rotateDirection = Constants.ROTATE_LEFT;
            }

            if (!(rotatingR && rotatingL) && (rotatingR || rotatingL)){
                calculateVector();

                shootPoint.x = pivot.x + vector.x*firePointOffset;
                shootPoint.y = pivot.y + vector.y*firePointOffset;

                hitbox.rotateCollider(vector, pivot);

                currentAngle += Math.toDegrees(rotateSpeed) * rotateDirection;
            }
            if (movingF || movingB) {
                double changeX = vector.x * moveDirection * moveSpeed;
                double changeY = vector.y * moveDirection * moveSpeed;

                this.x += changeX;
                this.y += changeY;

                pivot.x += changeX;
                pivot.y += changeY;

                shootPoint.x += changeX;
                shootPoint.y += changeY;

                hitbox.moveCollider(changeX, changeY);
            }
        }
    }

    /**
     * calculate direction vector based on rotation speed, direction and previous vector
     */
    private void calculateVector(){
        double roundOffX = vector.x * Math.cos(rotateSpeed * rotateDirection) - vector.y * Math.sin(rotateSpeed * rotateDirection);
        double roundOffY = vector.x * Math.sin(rotateSpeed* rotateDirection) + vector.y * Math.cos(rotateSpeed * rotateDirection);
        vector.x = Math.round(roundOffX * 100) / 100.0;
        vector.y = Math.round(roundOffY * 100) / 100.0;
    }

    /**
     * Create and shoot a bullet
     * @param bulletSpeed how fast the bulet travels in px per game update
     * @param now time the bullet has been shot
     */
    public void shootBullet(double bulletSpeed, long now){
        if (usedCapacity != capacity && !destroyed){
            usedCapacity++;
            Bullet bullet = new Bullet("bullet12.png", Constants.bulletSize, Constants.bulletSize, shootPoint.x, shootPoint.y, vector, bulletSpeed, now);
            bullets.push(bullet);
            AppLogger.logConfig("Shot bullet at: [" + bullet.getCenterX() + "," + bullet.getCenterY() + "]");
            AppLogger.logFinest(bullet.toString());
        }
    }

    /**
     * destroy bullet
     * removes bullet from the tanks bullet array
     * @param b bullet to be removed
     */
    public void destroyBullet(Bullet b){
        bullets.remove(b);
        usedCapacity--;
    }

    public void setVector(double x, double y) {
        this.vector.x = x;
        this.vector.y = y;
    }

    public boolean isRotatingL() {
        return rotatingL;
    }

    public void setRotatingL(boolean rotatingL) {
        this.rotatingL = rotatingL;
    }

    public boolean isRotatingR() {
        return rotatingR;
    }

    public void setRotatingR(boolean rotatingR) {
        this.rotatingR = rotatingR;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setRotating(boolean rotating) {
        this.rotating = rotating;
    }

    public void setCollision(boolean collision){
        this.collision = collision;
    }

    public void setMoveDirection(int moveDirection) {
        this.moveDirection = moveDirection;
    }

    public void setRotateDirection(int rotateDirection) {
        this.rotateDirection = rotateDirection;
    }

    public boolean isMovingF() {
        return movingF;
    }

    public void setMovingF(boolean movingF) {
        this.movingF = movingF;
    }

    public boolean isMovingB() {
        return movingB;
    }

    public void setMovingB(boolean movingB) {
        this.movingB = movingB;
    }

    public Point getVector() {
        return vector;
    }

    public double getVectorX(){
        return vector.x;
    }

    public double getVectorY(){
        return  vector.y;
    }

    public double getShootX() {
        return shootPoint.x;
    }
    public double getShootY() {
        return shootPoint.y;
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    public double getPivotX() {
        return pivot.x;
    }

    public double getPivotY() {
        return pivot.y;
    }

    public int getMoveDirection() {
        return moveDirection;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }

    public double getRotateSpeed() {
        return rotateSpeed;
    }

    /**
     * get angle in degrees
     * */
    public double getCurrentAngle() {
        return currentAngle;
    }

    public boolean isMoving() {
        return movingF || movingB;
    }

    public boolean isRotating() {
        return rotatingL || rotatingR;
    }

    public TankCollider getHitbox() {
        return hitbox;
    }

    public boolean isCollision() {
        return collision;
    }

    public int getRotateDirection() {
        return rotateDirection;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public int getUsedCapacity() {
        return usedCapacity;
    }

    /**
     * change x and y coordinates using special function changeXY
     * @param newX new x coord
     * @param newY new y coord
     */
    @Override
    public void setXY(double newX, double newY){
        changeXY(newX, newY);
    }

    /**
     * hange the tank coords
     * doesnt directly change them, calculates vector from old pos to new pos
     * and moves all tank points in this vectors diretion
     * @param newX new x coord
     * @param newY new y coord
     */
    void changeXY(double newX, double newY){
        double changeX = newX - x;
        double changeY = newY - y;

        x += changeX;
        y += changeY;

        pivot.x += changeX;
        pivot.y += changeY;

        shootPoint.x += changeX;
        shootPoint.y += changeY;

        hitbox.moveCollider(changeX, changeY);
    }

    @Override
    public String toString() {
        return "Tank{" +
                "center=" + pivot +
                ", vector=" + vector +
                ", currentAngle=" + currentAngle +
                ", moving=" + moving +
                ", rotating=" + rotating +
                ", destroyed=" + destroyed +
                '}';
    }
}
