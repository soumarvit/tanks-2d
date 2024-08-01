package cvut.soumar.java.tanks2d.model;

/**
 * 2D Point class
 */
public class Point {
    /**
    * I want this to be public because I will be changing this so often
    * I dont think it would be clever to make it private and use getter and setter functions
     */
    public double x, y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "["+x+ "," + y + "]";
    }
}
