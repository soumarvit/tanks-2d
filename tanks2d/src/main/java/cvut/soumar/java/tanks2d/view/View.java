package cvut.soumar.java.tanks2d.view;

import cvut.soumar.java.tanks2d.data.Constants;
import cvut.soumar.java.tanks2d.model.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

/**
 * View class
 * Renders all game objects
 * Connects the game objects together on the graphical side of the game
 */
public class View {

    private final Scene scene;
    private final GameModel gameModel;
    private final Canvas mainCanvas, tank1Canvas, tank2Canvas, boomCanvas;
    private final GraphicsContext mainGC, tank1GC, tank2GC, boomGC;
    private final Rotate tank1Rotate, tank2Rotate;
    private final TankRenderer tank1Renderer, tank2Renderer;
    private final Image bulletImage = new Image(Constants.bulletFile);

    private final boolean renderHitboxes = false;

    private final Image boomImage = new Image(Constants.boomFile);




    public View(GameModel gameModel){
        this.gameModel = gameModel;

        mainCanvas = new Canvas();
        mainCanvas.setWidth(gameModel.getWidth());
        mainCanvas.setHeight(gameModel.getHeight());

        tank1Canvas = new Canvas();
        tank1Canvas.setWidth(gameModel.getWidth());
        tank1Canvas.setHeight(gameModel.getHeight());

        tank2Canvas = new Canvas();
        tank2Canvas.setWidth(gameModel.getWidth());
        tank2Canvas.setHeight(gameModel.getHeight());

        boomCanvas = new Canvas(gameModel.getWidth(), gameModel.getHeight());
        boomGC = boomCanvas.getGraphicsContext2D();

        //gridCanvas = new Canvas();

        tank1Rotate = new Rotate();
        tank2Rotate = new Rotate();

        mainGC = mainCanvas.getGraphicsContext2D();
        tank1GC = tank1Canvas.getGraphicsContext2D();
        tank2GC = tank2Canvas.getGraphicsContext2D();

        tank1Renderer = new TankRenderer(gameModel, gameModel.getTank1(), tank1GC, tank1Rotate);
        tank2Renderer = new TankRenderer(gameModel, gameModel.getTank2(), tank2GC, tank2Rotate);

        Pane root = new Pane(mainCanvas, tank1Canvas, tank2Canvas, boomCanvas);
        scene = new Scene(root, gameModel.getWidth(), gameModel.getHeight());

        //render edges
        ArrayList<Edge> edges = gameModel.getGrid().getEdges();
        for (Edge edge: edges){
            Rectangle rectangle = new Rectangle();
            rectangle.setX(edge.getX());
            rectangle.setY(edge.getY());
            rectangle.setWidth(edge.getWidth());
            rectangle.setHeight(edge.getHeight());
            root.getChildren().add(rectangle);
        }
    }

    /**
     * get game object values from game model and render them accordingly
     */
    public void render(){
        mainGC.setFill(Constants.backgroundColor);
        mainGC.fillRect(0,0, gameModel.getWidth(), gameModel.getHeight());

        for (Bullet bullet: gameModel.getBullets()){
            mainGC.drawImage(bulletImage, bullet.getX(), bullet.getY());
        }

        if (gameModel.getTank1().isDestroyed()){
            boomGC.drawImage(boomImage, gameModel.getTank1().getPivotX() - (double)(Constants.boomImageW/2), gameModel.getTank1().getPivotY()- (double)(Constants.boomImageH/2));
        } else {
            tank1Renderer.render();
        }

        if (gameModel.getTank2().isDestroyed()){
            boomGC.drawImage(boomImage, gameModel.getTank2().getPivotX() - (double)(Constants.boomImageW/2), gameModel.getTank2().getPivotY() - (double)(Constants.boomImageH/2));
        } else {
            tank2Renderer.render();
        }


        //can render tank colliders
        //for debugging purposes only
        if (renderHitboxes){
            int radius = 2;
            mainGC.setFill(Color.RED);
            for (Point p: gameModel.getTank1().getHitbox().getPoints()){
                mainGC.fillOval(p.x - radius, p.y - radius, 2*radius, 2*radius);
            }
            for (Point p: gameModel.getTank2().getHitbox().getPoints()){
                mainGC.fillOval(p.x - radius, p.y - radius, 2*radius, 2*radius);
            }

            int r = 3;
            mainGC.setFill(Color.BLUE);
            mainGC.fillOval(gameModel.getTank1().getPivotX() - r,  gameModel.getTank1().getPivotY() - r, r*2, r*2);

            mainGC.fillOval(gameModel.getTank2().getPivotX() - r,  gameModel.getTank2().getPivotY() - r, r*2, r*2);
        }

    }

    public Scene getScene() {
        return scene;
    }
}
