package cvut.soumar.java.tanks2d.scenes.levelEditor;

import cvut.soumar.java.tanks2d.controller.AppController;
import cvut.soumar.java.tanks2d.data.Constants;
import cvut.soumar.java.tanks2d.data.GameSettings;
import cvut.soumar.java.tanks2d.model.Edge;
import cvut.soumar.java.tanks2d.model.Grid;
import cvut.soumar.java.tanks2d.model.Level;
import cvut.soumar.java.tanks2d.utils.AppLogger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Grid editor scene class
 * Scene that make it possible to create and edit the game grid
 */
public class GridEditor {

    private Grid tmpGrid = new Grid();
    private Color backColor = Constants.backgroundColor;
    private Color gridColor = Color.BLACK;
    private Color tmpGridColor = Color.LIGHTGRAY;
    private Level level;
    private AppController appController;

    private GraphicsContext gc;
    private Scene scene;
    Image tank1 = new Image(GameSettings.getTank1Image());
    Image tank2 = new Image(GameSettings.getTank2Image());
    private ComboBox<Integer> tank1XComboBox;
    private ComboBox<Integer> tank1YComboBox;
    private ComboBox<Integer> tank2XComboBox;
    private ComboBox<Integer> tank2YComboBox;


    public GridEditor(AppController appController, Level level){
        this.appController = appController;
        this.level = level;

        Canvas canvas = new Canvas(level.getWidth(), level.getHeight());
        gc = canvas.getGraphicsContext2D();
        gc.setFill(backColor);
        gc.fillRect(0,0, level.getWidth(), level.getHeight());

        Pane canvasPane = new Pane(canvas);

        //buttons
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> {
            resetButtonAction();
            AppLogger.logInfo("Reset button clicked");
        });

        Button randomButton = new Button("Random");
        randomButton.setOnAction(event -> {
            randomButtonAction();
            AppLogger.logInfo("Random button clicked");
        });

        Button saveButton = new Button("Confirm");
        saveButton.setOnAction(event -> {
            saveButtonAction();
            AppLogger.logInfo("Save button clicked");
        });

        //comboBoxes
        tank1XComboBox = new ComboBox<>();
        tank2XComboBox = new ComboBox<>();
        for (int i = 0; i < level.getGrid().getTilesX(); i++){
            tank1XComboBox.getItems().add(i);
            tank2XComboBox.getItems().add(i);
        }
        tank1XComboBox.setValue((int)level.getTank1X()/level.getGrid().getTileSize());
        tank2XComboBox.setValue((int)level.getTank2X()/level.getGrid().getTileSize());

        tank1YComboBox = new ComboBox<>();
        tank2YComboBox = new ComboBox<>();
        for (int i = 0; i < level.getGrid().getTilesY(); i++){
            tank1YComboBox.getItems().add(i);
            tank2YComboBox.getItems().add(i);
        }
        tank1YComboBox.setValue((int)level.getTank1Y()/level.getGrid().getTileSize());
        tank2YComboBox.setValue((int)level.getTank2Y()/level.getGrid().getTileSize());

        tank1XComboBox.setOnAction(event -> {
            updateButtonAction();
        });

        tank1YComboBox.setOnAction(event -> {
            updateButtonAction();
        });

        tank2XComboBox.setOnAction(event -> {
            updateButtonAction();
        });

        tank2YComboBox.setOnAction(event -> {
            updateButtonAction();
        });

        //labels
        Label tank1PosLabel = new Label("Tank 1 pos:");
        Label tank2PosLabel = new Label("Tank 2 pos:");
        Label spaceLabel = new Label("     ");

        HBox tankPane = new HBox(10);
        tankPane.setAlignment(Pos.CENTER);
        tankPane.getChildren().addAll(spaceLabel, tank1PosLabel, tank1XComboBox, tank1YComboBox, tank2PosLabel, tank2XComboBox, tank2YComboBox);

        HBox bottomPane = new HBox(10);
        bottomPane.setPadding(new Insets(10));
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.getChildren().addAll( resetButton, randomButton, saveButton, tankPane);

        BorderPane root = new BorderPane();
        root.setCenter(canvasPane);
        root.setBottom(bottomPane);

        initGrid();
        scene = new Scene(root);

        //mouse handlers
        scene.setOnMouseMoved(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();

            updateTmpGrid(mouseX, mouseY);
            AppLogger.logFinest("Mouse position: (" + mouseX + ", " + mouseY + ")");
        });

        scene.setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();

            updateRealGrid(mouseX, mouseY);
            AppLogger.logInfo("Mouse clicked at position: (" + mouseX + ", " + mouseY + ")");
        });
    }



    public Scene getScene() {
        return scene;
    }

    /**
     * render the real grid in grid color
     */
    private void drawRealGrid(){
        gc.setFill(gridColor);
        for (Edge edge: level.getGrid().getEdges()){
            gc.fillRect(edge.getX(), edge.getY(), edge.getWidth(), edge.getHeight());
        }
    }

    /**
     * render the tmp grid int tmpGrid color
     */
    private void drawTmpGrid(){
        gc.setFill(tmpGridColor);
        for (Edge edge: tmpGrid.getEdges()){
            gc.fillRect(edge.getX(), edge.getY(), edge.getWidth(), edge.getHeight());
        }
    }

    /**
     * render the grid editor backgdround
     */
    private void drawCanvasBackground(){
        gc.setFill(backColor);
        gc.fillRect(0,0, level.getWidth(), level.getHeight());
    }

    /**
     * initialize tmp grid to full grid
     * draw tmp grid
     * draw level (real) grid on top of tmp grid
     * draw tanks on top of the grids
     */
    private void initGrid(){
        tmpGrid.setFull(level.getGrid().getTileSize(), level.getGrid().getEdgeWidth(), level.getGrid().getTilesX(), level.getGrid().getTilesY());
        drawTmpGrid();
        drawRealGrid();
        placeTanks();
    }

    /**
     * redraw current state of the editor
     * set tmp grid to full grid and render it to screen
     * set level (real) grid to border grid and render it to screen
     * set comboBox values to default tank position
     * render tanks
     */
    private void resetGrid(){
        drawCanvasBackground();

        tmpGrid.setFull(level.getGrid().getTileSize(), level.getGrid().getEdgeWidth(), level.getGrid().getTilesX(), level.getGrid().getTilesY());
        drawTmpGrid();

        level.getGrid().setEmpty();
        level.getGrid().setBorder();
        drawRealGrid();

        tank1XComboBox.setValue(0);
        tank2XComboBox.setValue(level.getGrid().getTilesX()-1);
        tank1YComboBox.setValue(0);
        tank2YComboBox.setValue(level.getGrid().getTilesY()-1);

        placeTanks();

        AppLogger.logConfig(level.toString());
    }

    /**
     * check if mouse is hovering over some edge that is not in real grid
     * if so, set color of that one edge to grid color
     * @param x mouse x pos
     * @param y mouse y pos
     */
    private void updateTmpGrid(double x, double y){
        drawTmpGrid();

        for (Edge edge: tmpGrid.getEdges()){
            boolean xx = (x >=edge.getX()) && (x <= (edge.getX() + edge.getWidth()));
            boolean yy = (y >=edge.getY()) && (y <= (edge.getY() + edge.getHeight()));

            if (xx && yy){
                gc.setFill(gridColor);
                gc.fillRect(edge.getX(), edge.getY(), edge.getWidth(), edge.getHeight());
            }
        }
        drawRealGrid();
    }

    /**
     * check if mouse didnt click on some edge that is not in real grid yet
     * if so, set color of that one edge to grid color and add it to real grid
     * @param x mouse x pos
     * @param y mouse y pos
     */
    private void updateRealGrid(double x, double y){
        for (Edge edge: tmpGrid.getEdges()){
            boolean xx = (x >=edge.getX()) && (x <= (edge.getX() + edge.getWidth()));
            boolean yy = (y >=edge.getY()) && (y <= (edge.getY() + edge.getHeight()));

            if (xx && yy){
                boolean add = true;
                for (Edge edgeReal: level.getGrid().getEdges()){
                    boolean xy = edgeReal.getX() == edge.getX() && edgeReal.getY() == edge.getY();
                    boolean wh = edgeReal.getWidth() == edge.getWidth() && edgeReal.getHeight() == edge.getHeight();

                    if (xy && wh) {
                        add = false;
                        break;
                    }
                }
                if (add){
                    level.getGrid().addEdge(edge.getX(), edge.getY(), edge.getWidth(), edge.getHeight());
                }
            }
        }
    }

    /**
     * redraw all
     */
    private void updateButtonAction(){
        drawCanvasBackground();
        drawTmpGrid();
        drawRealGrid();
        placeTanks();
    }


    /**
     * calculate real tank position based on tile size, edge size etc
     * draw the tank to the screen
     */
    private void placeTanks(){
        level.setTank1X(tank1XComboBox.getValue() * level.getGrid().getTileSize() + (level.getGrid().getTileSize()-Constants.tankSize)/2 + (int)((double)level.getGrid().getEdgeWidth()/2));
        level.setTank1Y(tank1YComboBox.getValue() * level.getGrid().getTileSize() + (level.getGrid().getTileSize()-Constants.tankSize)/2 + (int)((double)level.getGrid().getEdgeWidth()/2));
        level.setTank2X(tank2XComboBox.getValue() * level.getGrid().getTileSize() + (level.getGrid().getTileSize()-Constants.tankSize)/2 + (int)((double)level.getGrid().getEdgeWidth()/2));
        level.setTank2Y(tank2YComboBox.getValue() * level.getGrid().getTileSize() + (level.getGrid().getTileSize()-Constants.tankSize)/2 + (int)((double)level.getGrid().getEdgeWidth()/2));

        gc.drawImage(tank1, level.getTank1X(), level.getTank1Y());
        gc.drawImage(tank2, level.getTank2X(), level.getTank2Y());
    }


    /**
     * create random grid with random tank positions
     * render to screen
     */
    private void randomButtonAction(){
        drawCanvasBackground();

        Random r = new Random();
        int tx1 = r.nextInt(0, level.getGrid().getTilesX());
        int ty1 = r.nextInt(0, level.getGrid().getTilesY());
        int tx2 = r.nextInt(0, level.getGrid().getTilesX());
        int ty2 = r.nextInt(0, level.getGrid().getTilesY());

        tank1XComboBox.setValue(tx1);
        tank1YComboBox.setValue(ty1);

        tank2XComboBox.setValue(tx2);
        tank2YComboBox.setValue(ty2);
        placeTanks();

        level.getGrid().setEmpty();
        level.getGrid().setRandom(level.getGrid().getTileSize(), level.getGrid().getEdgeWidth(), level.getGrid().getTilesX(), level.getGrid().getTilesY());
        updateTmpGrid(0,0);

        AppLogger.logConfig(level.toString());
    }

    /**
     *reset grid to default and render to screen
     */
    private void resetButtonAction(){
        resetGrid();
    }

    /**
     * confirm new grid and go back to level editor
     */
    private void saveButtonAction(){
        appController.loadLevelEditorWithGrid();
        AppLogger.logConfig(level.toString());
    }

    public Level getLevel(){
        return level;
    }

}
