package cvut.soumar.java.tanks2d.model;

import cvut.soumar.java.tanks2d.controller.AppController;
import cvut.soumar.java.tanks2d.data.Constants;
import cvut.soumar.java.tanks2d.data.GameSettings;
import cvut.soumar.java.tanks2d.utils.AppLogger;

import java.util.LinkedList;

/**
 * GameModel class
 * Handles and updates all game object model
 * Connects the game objects together
 */
public class GameModel {

    private double width, height;
    private final Tank tank1, tank2;
    private double t1x, t1y, t2x, t2y;
    private Grid grid;
    private CollisionManager collisions;
    private final long bulletTime;

    private AppController appController;
    private long deathTime = 0;
    private long deathTimeCounter = (long) 3 * 1_000_000_000;

    public GameModel(Level level, AppController appController){
        this.appController = appController;

        loadLevel(level);

        tank1 = new Tank(GameSettings.getTank1Image(), Constants.tankSize, Constants.tankSize, t1x, t1y, GameSettings.getTankMoveSpeed(), GameSettings.getTankRotateSpeedRad(), GameSettings.getTankBulletCapacity());
        tank2 = new Tank(GameSettings.getTank2Image(), Constants.tankSize,Constants.tankSize, t2x, t2y, GameSettings.getTankMoveSpeed(), GameSettings.getTankRotateSpeedRad(), GameSettings.getTankBulletCapacity());

        collisions = new CollisionManager(this);

        collisions.printColliders();
        bulletTime = (long) GameSettings.getBulletTime() * 1_000_000_000;  //converts seconds to nanoseconds
    }


    /**
     * load level into game
     * @param level the level that gets loaded
     */
    private void loadLevel(Level level){
        t1x = level.getTank1X();
        t1y = level.getTank1Y();

        t2x = level.getTank2X();
        t2y = level.getTank2Y();

        width = level.getWidth();
        height = level.getHeight();

        grid = level.getGrid();
    }


    /**
     * @param tank destroy
     */
    public final void destoryTank(Tank tank){
        tank.setDestroyed(true);
    }

    /**
     * @param tank rotate left
     */
    public final void rotateTankLeft(Tank tank){
        tank.setRotatingL(true);
        tank.setRotateDirection(Constants.ROTATE_LEFT);
    }

    /**
     * @param tank rotate right
     */
    public final void rotateTankRight(Tank tank){
        tank.setRotatingR(true);
        tank.setRotateDirection(Constants.ROTATE_RIGHT);
    }

    /**
     * @param tank move forwards
     */
    public final void moveTankForwards(Tank tank){
        tank.setMovingF(true);
        tank.setMoveDirection(Constants.FORWARDS);
    }

    /**
     * @param tank move backwards
     */
    public final void moveTankBackwards(Tank tank){
        tank.setMovingB(true);
        tank.setMoveDirection(Constants.BACKWARDS);
    }

    /**
     * @param tank stop moving forwards
     */
    public void stopMovingTankForwards(Tank tank){
        tank.setMovingF(false);
    }

    /**
     * @param tank stop moving backwards
     */
    public void stopMovingTankBackwards(Tank tank){
        tank.setMovingB(false);
    }

    /**
     * @param tank stop rotating left
     */
    public void stopRotatingTankLeft(Tank tank){
        tank.setRotatingL(false);
    }

    /**
     * @param tank stop rotating right
     */
    public void stopRotatingTankRight(Tank tank){
        tank.setRotatingR(false);
    }

    /**
     * @param tank shoot bullet
     */
    public void shoot(Tank tank){
        tank.shootBullet(GameSettings.getBulletTravelSpeed() ,System.nanoTime());
    }


    /**
     * update all game objects positions, state...
     */
    public void update(){
        if ((tank1.isDestroyed() || tank2.isDestroyed()) && deathTime == 0){
            deathTime = System.nanoTime();
        }

        if (deathCounter(System.nanoTime())){
            appController.loadPopUpMenu();
            return;
        };

        collisions.checkTankWithEdge(tank1);
        collisions.checkTankWithEdge(tank2);

        collisions.checkTankWithTank(tank1, tank2);
        collisions.checkTankWithTank(tank2, tank1);

        tank1.move();
        tank2.move();

        AppLogger.logFinest(tank1.toString());
        AppLogger.logFinest(tank2.toString());

        for (Bullet bullet: tank1.getBullets()) {
            if (bullet.getTime(System.nanoTime()) > bulletTime){
                tank1.destroyBullet(bullet);
                break;
            }
            collisions.checkBulletWithEdge(bullet);
            if (bullet.collisionDelay(System.nanoTime())){
                collisions.checkTankWithBullet(bullet, tank1);
            }
            collisions.checkTankWithBullet(bullet, tank2);
            bullet.move();
        }

        for (Bullet bullet: tank2.getBullets()) {
            if (bullet.getTime(System.nanoTime()) > bulletTime){
                tank2.destroyBullet(bullet);
                break;
            }
            collisions.checkBulletWithEdge(bullet);
            collisions.checkTankWithBullet(bullet, tank1);
            if (bullet.collisionDelay(System.nanoTime())){
                collisions.checkTankWithBullet(bullet, tank2);
            }
            bullet.move();
        }
    }


    /**
     * timer for when player dies
     * 3 seconds from the moment oune tank gets destroyed the pop up menu gets loaded
     * @param now curren time in usec
     * @return true if elapsed time from death > death time counter
     */
    private boolean deathCounter(long now){
        return ((now - deathTime) > deathTimeCounter) && (deathTime != 0);
    }

    public Tank getTank1() {
        return tank1;
    }

    public Tank getTank2() {
        return tank2;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Grid getGrid() {
        return grid;
    }

    public LinkedList<Bullet> getBullets(){
        LinkedList<Bullet> bullets = new LinkedList<>();
        bullets.addAll(tank1.getBullets());
        bullets.addAll(tank2.getBullets());
        return bullets;
    }


}
