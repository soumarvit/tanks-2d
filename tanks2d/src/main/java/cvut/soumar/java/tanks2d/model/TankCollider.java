package cvut.soumar.java.tanks2d.model;

public class TankCollider {
    private Point s1, s2, s3, s4; //center of sides
    private Point c1, c2, c3, c4; //corners
    private Point center, barrel;
    private double halfSide;
    private double centerOffset;
    private double barrelLength;
    public double x1, y1;
    private final Point[] pointList = new Point[10];
    private final Point[] pointsFront = new Point[4];
    private final Point[] pointsBack = new Point[3];
    private final Point[] pointsLeft = new Point[3];
    private final Point[] pointsRight = new Point[3];

    public TankCollider(Point center, double sideSize, double barrelOffset, Point vector, double centerOffset){
        halfSide = sideSize/2;
        this.barrelLength = halfSide + barrelOffset;

        this.centerOffset = centerOffset;

        this.center = new Point(center.x + centerOffset, center.y);

        //top center
        s1 = new Point(center.x, center.y);

        //bottom center
        s2 = new Point(center.x, center.y);

        //left center
        s3 = new Point(center.x, center.y);

        //right center
        s4 = new Point(center.x, center.y);

        //top left
        c1 = new Point(center.x, center.y);

        //top right
        c2 = new Point(center.x, center.y);

        //bottom left
        c3 = new Point(center.x, center.y);

        //bottom right
        c4 = new Point(center.x, center.y);

        barrel = new Point(center.x, center.y);

        pointList[0] = this.center;
        pointList[1] = s1;
        pointList[2] = s2;
        pointList[3] = s3;
        pointList[4] = s4;
        pointList[5] = c1;
        pointList[6] = c2;
        pointList[7] = c3;
        pointList[8] = c4;
        pointList[9] = barrel;

        pointsFront[0] = c1;
        pointsFront[1] = s1;
        pointsFront[2] = c2;
        pointsFront[3] = barrel;

        pointsBack[0] = c3;
        pointsBack[1] = s2;
        pointsBack[2] = c4;

        pointsLeft[0] = c1;
        pointsLeft[1] = s3;
        pointsLeft[2] = c3;

        pointsRight[0] = c2;
        pointsRight[1] = s4;
        pointsRight[2] = c4;

        this.rotateCollider(vector, center);
    }

    /**
     * rotate tank collider base on the tank direction vector and the tank center point
     * @param vector tank direction vector
     * @param center tank center point
     */
    public void rotateCollider (Point vector, Point center){
        //shootX = pivotX + vector.x*firePointOffset;
        //shootY = pivotY + vector.y*firePointOffset;

        this.center.x = center.x + centerOffset* vector.x;
        this.center.y = center.y + centerOffset* vector.y;

        s1.x = this.center.x + halfSide* vector.x;
        s1.y = this.center.y + halfSide* vector.y;

        barrel.x = this.center.x + barrelLength* vector.x;
        barrel.y = this.center.y + barrelLength* vector.y;

        s2.x = this.center.x - halfSide* vector.x;
        s2.y = this.center.y - halfSide* vector.y;

        s3.x = this.center.x + halfSide* vector.y;
        s3.y = this.center.y + halfSide* (vector.x * -1);

        s4.x = this.center.x - halfSide* vector.y;
        s4.y = this.center.y - halfSide* (vector.x * -1);

        //takes vector right center to top center and adds it to center point -> it creates a top left corner
        c1.x = this.center.x + (s1.x - s4.x);
        c1.y = this.center.y + (s1.y - s4.y);

        //takes vector left center to top center and adds it to center point -> it creates a top right corner
        c2.x = this.center.x + (s1.x - s3.x);
        c2.y = this.center.y + (s1.y - s3.y);

        //takes vector right center to bottom center, adds it to center point -> it creates bottom left corner
        c3.x = this.center.x + (s2.x - s4.x);
        c3.y = this.center.y + (s2.y - s4.y);

        //takes vector right center to bottom center, adds it to center point -> it creates bottom left corner
        c4.x = this.center.x + (s2.x - s3.x);
        c4.y = this.center.y + (s2.y - s3.y);

    }

    /***
     * move the collider
     * @param plusX new X = old X + plus X
     * @param plusY new Y = old Y + plus Y
     */
    public void moveCollider (double plusX, double plusY){
        for (Point p: pointList){
            p.x += plusX;
            p.y += plusY;
        }
    }

    /**
     *
     * @return all collider points
     */
    public Point[] getPoints(){
        return pointList;
    }

    /**
     *
     * @return front collider points including the barrel point
     */
    public Point[] getPointsFront() {
        return pointsFront;
    }

    /**
     *
     * @return back collider points
     */
    public Point[] getPointsBack() {
        return pointsBack;
    }

    /**
     *
     * @return left collider points
     */
    public Point[] getPointsLeft() {
        return pointsLeft;
    }

    /**
     *
     * @return right collider points
     */
    public Point[] getPointsRight() {
        return pointsRight;
    }

    /**
     *
     * @return top center point
     */
    public Point getS1() {
        return s1;
    }

    /**
     *
     * @return bottom center point
     */
    public Point getS2() {
        return s2;
    }

    /**
     *
     * @return left center point
     */
    public Point getS3() {
        return s3;
    }

    /**
     *
     * @return right center point
     */
    public Point getS4() {
        return s4;
    }

    /**
     *
     * @return top left point
     */
    public Point getC1() {
        return c1;
    }

    /**
     *
     * @return top right point
     */
    public Point getC2() {
        return c2;
    }

    /**
     *
     * @return bottom left point
     */
    public Point getC3() {
        return c3;
    }

    /**
     *
     * @return bottom right point
     */
    public Point getC4() {
        return c4;
    }

    /**
     *
     * @return center of the collider (not the tank!) point
     */
    public Point getCenter() {
        return center;
    }

    /**
     *
     * @return barrel point
     */
    public Point getBarrel() {
        return barrel;
    }
}
