package cvut.soumar.java.tanks2d.model;

import cvut.soumar.java.tanks2d.data.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {

    @Test
    public void testBulletInitialDirection(){
        Bullet bullet1 = new Bullet("bullet.png", 10, 10, 5, 5, new Point(1, 0), 5, 0);

        assertEquals(Constants.BULLETRIGHT, bullet1.getDirectionX());
        assertEquals(Constants.BULLETUP, bullet1.getDirectionY());

        Bullet bullet2 = new Bullet("bullet.png", 10, 10, 5, 5, new Point(0, 1), 5, 0);

        assertEquals(Constants.BULLETLEFT, bullet2.getDirectionX());
        assertEquals(Constants.BULLETDOWN, bullet2.getDirectionY());

        Bullet bullet3 = new Bullet("bullet.png", 10, 10, 5, 5, new Point(-1, 0), 5, 0);

        assertEquals(Constants.BULLETLEFT, bullet3.getDirectionX());
        assertEquals(Constants.BULLETUP, bullet3.getDirectionY());

        Bullet bullet4 = new Bullet("bullet.png", 10, 10, 5, 5, new Point(0, -1), 5, 0);

        assertEquals(Constants.BULLETLEFT, bullet4.getDirectionX());
        assertEquals(Constants.BULLETUP, bullet4.getDirectionY());

    }

    @Test
    public void testBulletTime(){
        Bullet bullet1 = new Bullet("bullet.png", 10, 10, 5, 5, new Point(1, 0), 5, 0);

        long bulletTime = 1;
        long now = 0;

        assertTrue(bullet1.getTime(now) < bulletTime);

        now = 2;

        assertFalse(bullet1.getTime(now) < bulletTime);
    }

    @Test
    public void testChangeDirX(){
        Bullet bullet1 = new Bullet("bullet.png", 10, 10, 5, 5, new Point(1, 0), 5, 0);

        bullet1.changeDirectionX();
        assertEquals(Constants.BULLETLEFT, bullet1.getDirectionX());

        bullet1.changeDirectionX();
        assertEquals(Constants.BULLETRIGHT, bullet1.getDirectionX());

    }

    @Test
    public void testChangeDirY(){
        Bullet bullet1 = new Bullet("bullet.png", 10, 10, 5, 5, new Point(0, -1), 5, 0);

        bullet1.changeDirectionY();
        assertEquals(Constants.BULLETDOWN, bullet1.getDirectionY());

        bullet1.changeDirectionY();
        assertEquals(Constants.BULLETUP, bullet1.getDirectionX());
    }

    @Test
    public void testBulletMove1(){
        double travelSpeed = 10;
        double centerX = 20;
        double centerY = 25;
        double vectorX = 0;
        double vectorY = 1;

        Bullet bullet1 = new Bullet("bullet.png", 10, 10, centerX, centerY, new Point(vectorX, vectorY), travelSpeed, 0);

        assertEquals(centerX, bullet1.getCenterX());
        assertEquals(centerY, bullet1.getCenterY());

        bullet1.move();

        assertEquals(centerX + vectorX*travelSpeed, bullet1.getCenterX());
        assertEquals(centerY + vectorY*travelSpeed, bullet1.getCenterY());
    }

    @Test
    public void testBulletMove2(){
        double travelSpeed = 10;
        double centerX = 20;
        double centerY = 25;
        double vectorX = -1;
        double vectorY = 0;

        Bullet bullet1 = new Bullet("bullet.png", 10, 10, centerX, centerY, new Point(vectorX, vectorY), travelSpeed, 0);

        assertEquals(centerX, bullet1.getCenterX());
        assertEquals(centerY, bullet1.getCenterY());

        bullet1.move();

        assertEquals(centerX + vectorX*travelSpeed, bullet1.getCenterX());
        assertEquals(centerY + vectorY*travelSpeed, bullet1.getCenterY());
    }

    @Test
    public void testBulletMove3(){
        double travelSpeed = 10;
        double centerX = 20;
        double centerY = 25;
        double vectorX = 0;
        double vectorY = 0;

        Bullet bullet1 = new Bullet("bullet.png", 10, 10, centerX, centerY, new Point(vectorX, vectorY), travelSpeed, 0);

        assertEquals(centerX, bullet1.getCenterX());
        assertEquals(centerY, bullet1.getCenterY());

        bullet1.move();

        assertEquals(centerX + vectorX*travelSpeed, bullet1.getCenterX());
        assertEquals(centerY + vectorY*travelSpeed, bullet1.getCenterY());
    }
}