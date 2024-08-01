package cvut.soumar.java.tanks2d.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TankColliderTest {


    @Test
    //test when the collider center is the same as tank center
    public void testMoveColliderNoOffset(){
        double offset = 0;
        double centerX = 10;
        double centerY = 10;

        //facing down
        double vectorX = 0;
        double vectorY = 1;

        double size = 50;
        double barrelOffset = 5;
        double barrelSize = size/2 + barrelOffset;

        TankCollider tc = new TankCollider(new Point(centerX, centerY), size, barrelOffset, new Point(vectorX, vectorY), offset);

        assertEquals(centerX+offset*vectorX, tc.getCenter().x);
        assertEquals(centerY+offset*vectorY, tc.getCenter().y);

        //test barell collider point center + size/2 + barrel offset (tank facing downwards vector 0 1)
        assertEquals(centerX+offset*vectorX + barrelSize*vectorX, tc.getBarrel().x);
        assertEquals(centerY+offset*vectorY + barrelSize*vectorY, tc.getBarrel().y);

        //test move
        tc.moveCollider(10, 10);
        assertEquals(centerX+offset*vectorX + barrelSize*vectorX + 10, tc.getBarrel().x);
        assertEquals(centerY+offset*vectorY + barrelSize*vectorY + 10, tc.getBarrel().y);
    }

    @Test
    //test when the collider center is the same as tank center
    public void testRotateColliderNoOffset(){
        double offset = 0;
        double centerX = 10;
        double centerY = 10;

        //facing down
        double vectorX = 0;
        double vectorY = 1;

        double size = 50;
        double barrelOffset = 5;
        double barrelSize = size/2 + barrelOffset;

        TankCollider tc = new TankCollider(new Point(centerX, centerY), size, barrelOffset, new Point(vectorX, vectorY), offset);

        assertEquals(centerX+offset*vectorX, tc.getCenter().x);
        assertEquals(centerY+offset*vectorY, tc.getCenter().y);

        //rotate 90 degrees to left
        vectorX = -1;
        vectorY = 0;

        tc.rotateCollider(new Point(vectorX,vectorY), new Point(centerX, centerY));
        assertEquals(centerX+offset*vectorX + barrelSize*vectorX, tc.getBarrel().x);
        assertEquals(centerX+offset*vectorY + barrelSize*vectorY, tc.getBarrel().y);
    }

    @Test
    //test when the collider center is not the same as tank center
    public void testMoveColliderWithOffset(){
        double offset = 10;
        double centerX = 10;
        double centerY = 10;

        //facing down
        double vectorX = 0;
        double vectorY = 1;

        double size = 50;
        double barrelOffset = 5;
        double barrelSize = size/2 + barrelOffset;

        TankCollider tc = new TankCollider(new Point(centerX, centerY), size, barrelOffset, new Point(vectorX, vectorY), offset);

        assertEquals(centerX+offset*vectorX, tc.getCenter().x);
        assertEquals(centerY+offset*vectorY, tc.getCenter().y);

        //test barell collider point center + size/2 + barrel offset (tank facing downwards vector 0 1)
        assertEquals(centerX+offset*vectorX + barrelSize*vectorX, tc.getBarrel().x);
        assertEquals(centerY+offset*vectorY + barrelSize*vectorY, tc.getBarrel().y);


        //test move
        tc.moveCollider(10, 10);
        assertEquals(centerX+offset*vectorX + barrelSize*vectorX + 10, tc.getBarrel().x);
        assertEquals(centerY+offset*vectorY + barrelSize*vectorY + 10, tc.getBarrel().y);
    }

    @Test
    //test when the collider center is not the same as tank center
    public void testRotateColliderWithOffset(){
        double offset = 10;
        double centerX = 10;
        double centerY = 10;

        //facing down
        double vectorX = 0;
        double vectorY = 1;

        double size = 50;
        double barrelOffset = 5;
        double barrelSize = size/2 + barrelOffset;

        TankCollider tc = new TankCollider(new Point(centerX, centerY), size, barrelOffset, new Point(vectorX, vectorY), offset);

        assertEquals(centerX+offset*vectorX, tc.getCenter().x);
        assertEquals(centerY+offset*vectorY, tc.getCenter().y);

        //rotate 90 degrees to left
        vectorX = -1;
        vectorY = 0;

        tc.rotateCollider(new Point(vectorX,vectorY), new Point(centerX, centerY));
        assertEquals(centerX+offset*vectorX + barrelSize*vectorX, tc.getBarrel().x);
        assertEquals(centerX+offset*vectorY + barrelSize*vectorY, tc.getBarrel().y);
    }



}