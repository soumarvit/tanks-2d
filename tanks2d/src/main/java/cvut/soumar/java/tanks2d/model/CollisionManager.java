package cvut.soumar.java.tanks2d.model;
import cvut.soumar.java.tanks2d.data.Constants;
import cvut.soumar.java.tanks2d.utils.AppLogger;

import java.util.ListIterator;
import java.util.logging.Logger;


/**
 * Class that handles all game collisions
 * This class can change game object positions in order to handle the
 * collisions correctly
 */
public class CollisionManager {

    private final GameModel gameModel;

    private int iterationDirection = -1;
    public CollisionManager(GameModel gameModel){
        this.gameModel = gameModel;
    }


    /**
     * handle collisions between two tanks if thisTank is moving
     * otherTank acts as a circle collider
     * @param thisTank tank collider
     * @param otherTank circle collider
     */
    public void checkTankWithTank(Tank thisTank, Tank otherTank){
        if (thisTank.isMoving()){
            movingTankCollision(thisTank, otherTank);
        }
    }

    /**
     * handle collision between tank and all game edges
     * @param tank to check the collisions for
     */
    public void checkTankWithEdge(Tank tank){
        /*
        tank gets stuck sometimes and the direction of iterations solves this problem
        because the list is sorted - first are checked vertical lines then horizontal lines
        on collision it checks if the edge it collided was vertical or horizontal and depending
        on that it changes the iteration direction
         */
        if (iterationDirection == 1){
            for (Edge edge: gameModel.getGrid().getEdges()){
                if (tank.isRotating() && tank.isMoving()){
                    movingTankCollision(tank, edge);
                }
                else if (tank.isRotating() && !tank.isMoving()){
                    rotatingTankCollision(tank, edge);
                }
                else if (tank.isMoving() && !tank.isRotating()){
                    movingTankCollision(tank, edge);
                }
            }
        }
        if (iterationDirection == -1) {
            //iterate backwards
            ListIterator<Edge> iter = gameModel.getGrid().getEdges().listIterator(gameModel.getGrid().getEdges().size());
            while (iter.hasPrevious()){
                Edge edge = iter.previous();
                if (tank.isRotating() && tank.isMoving()){
                    movingTankCollision(tank, edge);
                }
                else if (tank.isRotating() && !tank.isMoving()){
                    rotatingTankCollision(tank, edge);
                }
                else if (tank.isMoving() && !tank.isRotating()){
                    movingTankCollision(tank, edge);
                }
            }
        }

    }


    /**
     * handle collision of rotating tank with one edge
     * @param tank to check the collisions for
     * @param edge to check the collisions for
     */
    private void rotatingTankCollision(Tank tank, Edge edge) {
        if (tank.isRotatingR()) {
            for (Point p : tank.getHitbox().getPointsRight()) {
                boolean cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                boolean cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());

                if (cX && cY) {
                    AppLogger.logConfig("Rotating right tank collision at" + p);
                    tank.setRotatingR(false);
                    tank.setRotatingL(true);
                    while (cX && cY) {
                        tank.move();
                        cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                        cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());
                    }
                    tank.setRotatingL(false);
                    tank.setRotatingR(false);
                    break;
                }
            }

            for (Point p : tank.getHitbox().getPointsLeft()) {
                boolean cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                boolean cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());

                if (cX && cY) {
                    AppLogger.logConfig("Rotating right tank collision at" + p);
                    tank.setRotatingR(false);
                    tank.setRotatingL(true);

                    while (cX && cY) {
                        tank.move();
                        cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                        cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());
                    }
                    tank.setRotatingL(false);
                    tank.setRotatingR(false);
                    break;
                }
            }
        }

        if (tank.isRotatingL()){
            for (Point p : tank.getHitbox().getPointsRight()) {
                boolean cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                boolean cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());

                if (cX && cY) {
                    AppLogger.logConfig("Rotating left tank collision at" + p);
                    tank.setRotatingL(false);
                    tank.setRotatingR(true);
                    while (cX && cY) {
                        tank.move();
                        cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                        cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());
                    }
                    tank.setRotatingR(false);
                    tank.setRotatingL(false);
                    break;
                }
            }

            for (Point p : tank.getHitbox().getPointsLeft()) {
                boolean cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                boolean cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());

                if (cX && cY) {
                    AppLogger.logConfig("Rotating left tank collision at" + p);
                    tank.setRotatingL(false);
                    tank.setRotatingR(true);
                    while (cX && cY) {
                        tank.move();
                        cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                        cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());
                    }
                    tank.move();
                    tank.setRotatingR(false);
                    tank.setRotatingL(false);
                    break;
                }
            }
        }
    }

    /**
     * handle collision of moving tank with one edge
     * @param tank to check the collisions for
     * @param edge to check the collisions for
     */
    private void movingTankCollision(Tank tank, Edge edge){
        double tmpTankX = tank.getX();
        double tmpTankY = tank.getY();

        double tmpTankCenterX = tank.getPivotX();
        double tmpTankCenterY = tank.getPivotY();

        switch (tank.getMoveDirection()){
            case Constants.FORWARDS -> {
                for (Point p: tank.getHitbox().getPointsFront()){
                    boolean cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                    boolean cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());

                    if (cX && cY){
                        AppLogger.logConfig("Moving forwards tank collision at" + p);
                        if (edge.getWidth() > edge.getHeight()){
                            iterationDirection = -1;
                        }

                        if (edge.getWidth() < edge.getHeight()){
                            iterationDirection = 1;
                        }

                        tank.setMoveDirection(Constants.BACKWARDS);
                        tank.setMovingB(true);

                        while (cX && cY){
                            tank.move();
                            for (Point p_new: tank.getHitbox().getPointsFront()){
                                cX = p_new.x >= edge.getX() && p_new.x <= (edge.getX() + edge.getWidth());
                                cY = p_new.y >= edge.getY() && p_new.y <= (edge.getY() + edge.getHeight());
                            }
                        }
                        tank.setMovingF(true);
                        tank.setMovingB(false);
                        tank.setMoveDirection(Constants.FORWARDS);

                        //if it is a horizontal edge
                        if (edge.getHeight() < edge.getWidth()){

                            boolean xx = !(tmpTankCenterX < edge.getX() || tmpTankCenterX > (edge.getX() + edge.getWidth()));
                            boolean yy = true;
                            for (Point pp: tank.getHitbox().getPointsBack()){
                                yy = !(pp.y > edge.getY() && pp.y < (edge.getY()+edge.getHeight()));
                                if (!yy){
                                    break;
                                }
                            }
                            if (yy){
                                Point pp = tank.getHitbox().getS3();
                                yy = !(pp.y > edge.getY() && pp.y < (edge.getY()+edge.getHeight()));
                            }
                            if (yy){
                                Point pp = tank.getHitbox().getS4();
                                yy = !(pp.y > edge.getY() && pp.y < (edge.getY()+edge.getHeight()));
                            }

                            if (xx || yy){
                                tank.changeXY(tmpTankX, tank.getY());
                            }
                            else {
                                tank.changeXY(tank.getX(), tmpTankY);
                            }
                        }

                        //if it is a vertical edge
                        if (edge.getHeight() > edge.getWidth()) {

                            boolean xx = true;
                            boolean yy = !(tmpTankCenterY < edge.getY() || tmpTankCenterY > (edge.getY() + edge.getHeight()));

                            for (Point pp: tank.getHitbox().getPointsBack()){
                                xx = !(pp.x > edge.getX() && pp.x < (edge.getX()+edge.getWidth()));
                                if (!xx){
                                    break;
                                }
                            }
                            if (xx){
                                Point pp = tank.getHitbox().getS3();
                                xx = !(pp.x > edge.getX() && pp.x < (edge.getX()+edge.getWidth()));
                            }
                            if (xx){
                                Point pp = tank.getHitbox().getS4();
                                xx = !(pp.x > edge.getX() && pp.x < (edge.getX()+edge.getWidth()));
                            }

                            if (xx || yy){
                                tank.changeXY(tank.getX(), tmpTankY);
                            } else {
                                tank.changeXY(tmpTankX, tank.getY());
                            }
                        }
                        return;
                    }
                }
            }
            case Constants.BACKWARDS -> {
                for (Point p: tank.getHitbox().getPointsBack()){
                    boolean cX = p.x >= edge.getX() && p.x <= (edge.getX() + edge.getWidth());
                    boolean cY = p.y >= edge.getY() && p.y <= (edge.getY() + edge.getHeight());

                    if (cX && cY){
                        AppLogger.logConfig("Moving backwards tank collision at" + p);
                        if (edge.getWidth() > edge.getHeight()){
                            iterationDirection = -1;
                        }

                        if (edge.getWidth() < edge.getHeight()){
                            iterationDirection = 1;
                        }

                        tank.setMoveDirection(Constants.FORWARDS);
                        tank.setMovingF(true);
                        while (cX && cY){
                            tank.move();
                            for (Point p_new: tank.getHitbox().getPointsBack()){
                                cX = p_new.x >= edge.getX() && p_new.x <= (edge.getX() + edge.getWidth());
                                cY = p_new.y >= edge.getY() && p_new.y <= (edge.getY() + edge.getHeight());
                            }
                        }
                        tank.setMovingF(false);
                        tank.setMovingB(true);
                        tank.setMoveDirection(Constants.BACKWARDS);

                        //simulate tank sliding when hit border
                        //if it is a horizontal
                        if (edge.getHeight() < edge.getWidth()){

                            boolean xx = !(tmpTankCenterX < edge.getX() || tmpTankCenterX > (edge.getX() + edge.getWidth()));
                            boolean yy = true;
                            for (Point pp: tank.getHitbox().getPointsFront()){
                                yy = !(pp.y > edge.getY() && pp.y < (edge.getY()+edge.getHeight()));
                                if (!yy){
                                    break;
                                }
                            }
                            if (yy){
                                Point pp = tank.getHitbox().getS3();
                                yy = !(pp.y > edge.getY() && pp.y < (edge.getY()+edge.getHeight()));
                            }
                            if (yy){
                                Point pp = tank.getHitbox().getS4();
                                yy = !(pp.y > edge.getY() && pp.y < (edge.getY()+edge.getHeight()));
                            }

                            if (xx || yy){
                                tank.changeXY(tmpTankX, tank.getY());
                            }
                            else {
                                tank.changeXY(tank.getX(), tmpTankY);
                            }
                        }

                        //if it is a vertical
                        if (edge.getHeight() > edge.getWidth()) {

                            boolean xx = true;
                            boolean yy = !(tmpTankCenterY < edge.getY() || tmpTankCenterY > (edge.getY() + edge.getHeight()));

                            for (Point pp: tank.getHitbox().getPointsFront()){
                                xx = !(pp.x > edge.getX() && pp.x < (edge.getX()+edge.getWidth()));
                                if (!xx){
                                    break;
                                }
                            }
                            if (xx){
                                Point pp = tank.getHitbox().getS3();
                                xx = !(pp.x > edge.getX() && pp.x < (edge.getX()+edge.getWidth()));
                            }
                            if (xx){
                                Point pp = tank.getHitbox().getS4();
                                xx = !(pp.x > edge.getX() && pp.x < (edge.getX()+edge.getWidth()));
                            }

                            if (xx || yy){
                                tank.changeXY(tank.getX(), tmpTankY);
                            } else {
                                tank.changeXY(tmpTankX, tank.getY());
                            }
                        }
                        return;
                    }
                }
            }
        }
        //iterationDirection = 1;
    }

    /**
     * handle collision of two tanks
     * @param thisTank tank collider
     * @param otherTank circle collider
     */
    private void movingTankCollision(Tank thisTank, Tank otherTank){
        //acts like a circle collider because its the most effective way

        double radius = otherTank.getWidth()/2;
        Point tankCenter = new Point(otherTank.getPivotX(), otherTank.getPivotY());

        switch (thisTank.getMoveDirection()){
            case Constants.FORWARDS -> {
                for (Point p: thisTank.getHitbox().getPointsFront()){
                    boolean cX = p.x >= (tankCenter.x - radius) && p.x <= (tankCenter.x + radius);
                    boolean cY = p.y >= (tankCenter.y - radius) && p.y <= (tankCenter.y + radius);

                    if (cX && cY){
                        AppLogger.logConfig("Tanks collided at" + p);
                        thisTank.setMoveDirection(Constants.BACKWARDS);
                        thisTank.setMovingB(true);

                        while (cX && cY){
                            thisTank.move();
                            for (Point p_new: thisTank.getHitbox().getPointsFront()){
                                cX = p_new.x >= (tankCenter.x - radius) && p_new.x <= (tankCenter.x + radius);
                                cY = p_new.y >= (tankCenter.y - radius) && p_new.y <= (tankCenter.y + radius);
                            }
                        }
                        thisTank.setMovingF(true);
                        thisTank.setMovingB(false);

                        thisTank.setMoveDirection(Constants.FORWARDS);
                        break;
                    }
                }
            }
            case Constants.BACKWARDS -> {
                for (Point p: thisTank.getHitbox().getPointsBack()){
                    boolean cX = p.x >= (tankCenter.x - radius) && p.x <= (tankCenter.x + radius);
                    boolean cY = p.y >= (tankCenter.y - radius) && p.y <= (tankCenter.y + radius);

                    if (cX && cY){
                        AppLogger.logConfig("Tanks collided at" + p);
                        thisTank.setMoveDirection(Constants.FORWARDS);
                        thisTank.setMovingF(true);
                        while (cX && cY){
                            thisTank.move();
                            for (Point p_new: thisTank.getHitbox().getPointsBack()){
                                cX = p_new.x >= (tankCenter.x - radius) && p_new.x <= (tankCenter.x + radius);
                                cY = p_new.y >= (tankCenter.y - radius) && p_new.y <= (tankCenter.y + radius);
                            }
                        }
                        thisTank.setMovingF(false);
                        thisTank.setMovingB(true);
                        thisTank.setMoveDirection(Constants.BACKWARDS);
                        break;

                    }
                }
            }
        }
    }

    /**
     * check if there has been a collision between @param bullet and @param tank
     * @param bullet check collision of this bullet with @tank
     * @param tank check collision of this tank with @bullet
     */
    public void checkTankWithBullet(Bullet bullet, Tank tank){
        double bcx = bullet.getCenterX();
        double bcy = bullet.getCenterY();
        double bcr = bullet.getRadius();

        for (Point p: tank.getHitbox().getPoints()){
            if (p.x > bullet.getX() && (p.x < (bullet.getX() + bcr*2)) && (p.y > bullet.getY()) && (p.y < (bullet.getY() + bcr*2))){
                AppLogger.logInfo("Tank with bullet collision at"+p);
                gameModel.destoryTank(tank);
            }
        }
    }

    /**
     * logs current grid colliders
     * logger level - CONFIG
     */
    public void printColliders(){
        for (Edge edge: gameModel.getGrid().getEdges()) {
            AppLogger.logConfig("("+ edge.getX() + "; " + edge.getY() +"), width: " + edge.getWidth() + " height: " + edge.getHeight() + "\n");
        }
    }

    /**
     * handle collision of bullet with all game edges
     * @param bullet to check the collisions for
     */
    public void checkBulletWithEdge(Bullet bullet){
        double bcx = bullet.getCenterX();
        double bcy = bullet.getCenterY();
        double bcr = bullet.getRadius();

        switch (bullet.getDirectionX()){
            case Constants.BULLETRIGHT -> {
                switch (bullet.getDirectionY()){
                    case Constants.BULLETUP -> {
                        for (Edge edge: gameModel.getGrid().getEdges()) {

                            if (((bcx + bcr) >= edge.getX() && ((bcx + bcr) <= (edge.getX()+edge.getWidth()))) && ((bcy >= edge.getY()) && bcy <= (edge.getY() + edge.getHeight()))){
                                bullet.changeDirectionX();
                                AppLogger.logConfig("bullet collision, changed vector from 1 0 -> -1 0");
                                return;
                            }
                            if (((bcy - bcr) >= edge.getY() && ((bcy - bcr) <= (edge.getY()+edge.getHeight()))) && ((bcx >= edge.getX()) && bcx <= (edge.getX() + edge.getWidth()))){
                                bullet.changeDirectionY();
                                AppLogger.logConfig("bullet collision, changed vector from 0 -1 -> 0 1");
                                return;
                            }
                        }
                    }
                    case Constants.BULLETDOWN -> {
                        for (Edge edge: gameModel.getGrid().getEdges()) {
                            if (((bcy + bcr) >= edge.getY() && ((bcy + bcr) <= (edge.getY()+edge.getHeight()))) && ((bcx >= edge.getX()) && bcx <= (edge.getX() + edge.getWidth()))){
                                bullet.changeDirectionY();
                                AppLogger.logConfig("bullet collision, changed vector from 0 1 -> 0 -1");
                                return;
                            }

                            if (((bcx + bcr) >= edge.getX() && ((bcx + bcr) <= (edge.getX()+edge.getWidth()))) && ((bcy >= edge.getY()) && bcy <= (edge.getY() + edge.getHeight()))){
                                bullet.changeDirectionX();
                                AppLogger.logConfig("bullet collision, changed vector from 1 0 -> -1 0 ");
                                return;
                            }
                        }
                    }
                }
            }
            case Constants.BULLETLEFT -> {
                switch (bullet.getDirectionY()){
                    case Constants.BULLETUP -> {
                        for (Edge edge: gameModel.getGrid().getEdges()) {
                            if (((bcx - bcr) >= edge.getX() && ((bcx - bcr) <= (edge.getX()+edge.getWidth()))) && ((bcy >= edge.getY()) && bcy <= (edge.getY() + edge.getHeight()))){
                                bullet.changeDirectionX();
                                AppLogger.logConfig("bullet collision, changed vector from -1 0 -> 1 0 ");
                                return;
                            }
                            if (((bcy - bcr) >= edge.getY() && ((bcy - bcr) <= (edge.getY()+edge.getHeight()))) && ((bcx >= edge.getX()) && bcx <= (edge.getX() + edge.getWidth()))){
                                bullet.changeDirectionY();
                                AppLogger.logConfig("bullet collision, changed vector from -1 0 -> 1 0 ");
                                return;
                            }
                        }
                    }
                    case Constants.BULLETDOWN -> {
                        for (Edge edge: gameModel.getGrid().getEdges()) {
                            if (((bcy + bcr) >= edge.getY() && ((bcy + bcr) <= (edge.getY()+edge.getHeight()))) && ((bcx >= edge.getX()) && bcx <= (edge.getX() + edge.getWidth()))){
                                bullet.changeDirectionY();
                                AppLogger.logConfig("bullet collision, changed vector from 1 0 -> -1 0 ");
                                return;
                            }

                            if (((bcx - bcr) >= edge.getX() && ((bcx - bcr) <= (edge.getX()+edge.getWidth()))) && ((bcy >= edge.getY()) && bcy <= (edge.getY() + edge.getHeight()))){
                                bullet.changeDirectionX();
                                AppLogger.logConfig("bullet collision, changed vector from -1 0 -> 1 0 ");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
