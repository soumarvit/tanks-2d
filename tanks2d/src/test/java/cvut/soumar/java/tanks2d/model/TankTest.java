package cvut.soumar.java.tanks2d.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test
    public void testTankMove() {
        // Create a tank with initial position and speed
        double rotateSpeedDeg = 90;
        double rotateSpeedRad = Math.toRadians(rotateSpeedDeg);
        double moveSpeed = 5;
        double tankX = 100;
        double tankY = 100;
        double tankW = 50;
        double tankH = 50;

        Tank tank = new Tank("tank.png", tankW, tankH, tankX, tankY, moveSpeed, rotateSpeedRad, 10);

        tank.setMovingF(true);
        Assertions.assertTrue(tank.isMoving());

        //test position
        assertEquals(100, tank.getX());
        assertEquals(100, tank.getY());

        //set movement forwards
        tank.move();
        assertEquals(100 + moveSpeed*tank.getVectorX()*tank.getMoveDirection(), tank.getX());
        assertEquals(100 + moveSpeed*tank.getVectorY()*tank.getMoveDirection(), tank.getY());

        tank.setMovingF(false);
        Assertions.assertFalse(tank.isMoving());

        //test movement backwards
        tank.setMovingB(true);
        Assertions.assertTrue(tank.isMoving());

        tank.move();
        assertEquals(100, tank.getX());
        assertEquals(100, tank.getY());
    }

    @Test
    public void testTankRotate(){

        double rotateSpeedDeg = 90;
        double rotateSpeedRad = Math.toRadians(rotateSpeedDeg);
        double moveSpeed = 100;
        double tankX = 100;
        double tankY = 100;
        double tankW = 50;
        double tankH = 50;

        Tank tank = new Tank("tank.png", tankW, tankH, tankX, tankY, moveSpeed, rotateSpeedRad, 10);

        //test rotating
        Assertions.assertFalse(tank.isRotating());

        tank.setRotatingL(true);
        Assertions.assertTrue(tank.isRotating());

        tank.setRotatingL(false);
        tank.setRotatingR(true);

        Assertions.assertTrue(tank.isRotating());

        //test if facing upwards
        assertEquals(0, tank.getVectorX());
        assertEquals(-1, tank.getVectorY());

        //test if angle is 0
        assertEquals(0, tank.getCurrentAngle());

        tank.move();

        //test if new vector is correct
        assertEquals(1, tank.getVectorX());
        assertEquals(0, tank.getVectorY());

        //test angle after 90 degree rotate to the right
        assertEquals(90, tank.getCurrentAngle());
    }

    @Test
    public void testTankMoveAndRotate(){
        double rotateSpeedDeg = 180;
        double rotateSpeedRad = Math.toRadians(rotateSpeedDeg);
        double moveSpeed = 5;
        double tankX = 100;
        double tankY = 100;
        double tankW = 50;
        double tankH = 50;

        Tank tank = new Tank("tank.png", tankW, tankH, tankX, tankY, moveSpeed, rotateSpeedRad, 10);

        tank.setMovingF(true);
        tank.setRotatingR(true);
        Assertions.assertTrue(tank.isMoving());
        Assertions.assertTrue(tank.isRotating());

        //test position
        assertEquals(100, tank.getX());
        assertEquals(100, tank.getY());

        //test if facing upwards
        assertEquals(0, tank.getVectorX());
        assertEquals(-1, tank.getVectorY());

        //set movement forwards
        tank.move();

        assertEquals(100 + moveSpeed*tank.getVectorX()*tank.getMoveDirection(), tank.getX());
        assertEquals(100 + moveSpeed*tank.getVectorY()*tank.getMoveDirection(), tank.getY());

        //test if new vector is correct
        assertEquals(0, tank.getVectorX());
        assertEquals(1, tank.getVectorY());

    }

    @Test public void testRotate360(){
        double rotateSpeedDeg = 360;
        double rotateSpeedRad = Math.toRadians(rotateSpeedDeg);
        double moveSpeed = 10;
        double tankX = 100;
        double tankY = 100;
        double tankW = 50;
        double tankH = 50;

        Tank tank = new Tank("tank.png", tankW, tankH, tankX, tankY, moveSpeed, rotateSpeedRad, 10);

        double vectorX = 0;
        double vectorY = -1;

        //test if angle is 0
        assertEquals(0, tank.getCurrentAngle());
        assertEquals(vectorX, tank.getVectorX());
        assertEquals(vectorY, tank.getVectorY());

        tank.move();

        //test if angle is 0 again
        assertEquals(0, tank.getCurrentAngle());
        assertEquals(vectorX, tank.getVectorX());
        assertEquals(vectorY, tank.getVectorY());
    }

    @Test
    public void testTankChangeXY(){
        Tank tank = new Tank("tank.png", 50, 50, 100, 100, 5.0, 3, 10);

        //test if changing the pos works
        tank.changeXY(160, 352);
        assertEquals(160, tank.getX());
        assertEquals(352, tank.getY());

        tank.changeXY(0, 0);
        assertEquals(0, tank.getX());
        assertEquals(0, tank.getY());
    }

    @Test
    public void testShootBullet(){
        Tank tank = new Tank("tank.png", 50, 50, 100, 100, 5.0, 3, 10);


        tank.shootBullet(1, 1);

        //test position of the bullet
        //bullet center should be on shoot point
        assertEquals(tank.getShootX(), tank.getBullets().getFirst().getCenterX());
        assertEquals(tank.getShootY(), tank.getBullets().getFirst().getCenterX());
    }

    @Test
    public void testShootEmpty(){

        int capacity = 0;
        Tank tank = new Tank("tank.png", 50, 50, 100, 100, 5.0, 3, capacity);

        tank.shootBullet(1, 1);

        assertTrue(tank.getBullets().isEmpty());
    }

}