package cvut.soumar.java.tanks2d.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Simple 2D Sprite class
 */
public class Sprite {

    protected double x, y;
    protected double width, height;
    protected String image;

    public Sprite(String imageFile, double w, double h){
        image = imageFile;

        width = w;
        height = h;
    }

    public void setXY(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setImage(String imageFile) {
        image = imageFile;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getImage() {
        return image;
    }
}

