package cvut.soumar.java.tanks2d.scenes.levelEditor;


import cvut.soumar.java.tanks2d.controller.AppController;
import cvut.soumar.java.tanks2d.data.Constants;
import cvut.soumar.java.tanks2d.data.GameSettings;
import cvut.soumar.java.tanks2d.model.Edge;
import cvut.soumar.java.tanks2d.model.GameModel;
import cvut.soumar.java.tanks2d.model.Grid;
import cvut.soumar.java.tanks2d.model.Level;
import cvut.soumar.java.tanks2d.utils.AppLogger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Level editor scene class
 */
public class LevelEditor{
    private static final int CANVAS_WIDTH = 350;
    private static final int CANVAS_HEIGHT = 350;

    private Canvas canvas1, canvas2;
    private GraphicsContext gcLeft, gcRight;
    private Scene scene;
    private Grid grid;
    private final int prGridTileSize = 25;
    private AppController appController;
    private Level level;
    private Color backColor = Constants.backgroundColor;
    private Color canvasBackColor = Constants.backgroundColor;
    private Color gridColor = Color.BLACK;
    private ComboBox<Integer> tilesXComboBox;
    private ComboBox<Integer> tilesYComboBox;
    private ComboBox<Integer> tileSizeComboBox;
    private ComboBox<Integer> edgeWidthComboBox;

    private ComboBox<String> levelComboBox;

    private int tilesX, tilesY, tileSize, edgeWidth;


    public LevelEditor(AppController appController, Level level) {
        this.appController = appController;
        this.level = level;
        this.grid = new Grid();

        //create comboboxes for tank positions
        tileSizeComboBox = new ComboBox<>();
        tileSizeComboBox.getItems().addAll(90, 100, 110, 120, 130, 140, 150, 160, 170); // Predefined values
        tileSizeComboBox.setValue(100);

        edgeWidthComboBox = new ComboBox<>();
        edgeWidthComboBox.getItems().addAll(14, 16, 18, 20, 22, 24, 26, 28, 30); // Predefined values
        edgeWidthComboBox.setValue(16);

        tilesXComboBox = new ComboBox<>();
        tilesXComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12); // Predefined values
        tilesXComboBox.setValue(8);

        tilesYComboBox = new ComboBox<>();
        tilesYComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9 ,10, 12); // Predefined values
        tilesYComboBox.setValue(6);


        //create labels
        Label tileSizeLabel = new Label("Tile Size (px):");
        Label edgeWidthLabel = new Label("Wall Width (px):");
        Label tilesXLabel = new Label("Grid size (tiles):");
        Label tilesYLabel = new Label(" x ");

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            backButtonAction();
        });

        //levels combobox
        levelComboBox = new ComboBox<>();
        levelComboBox.getItems().add("default");
        for (int i = 1 ; i < 10; i++){
            String levelS = "level_" + Integer.toString(i);
            levelComboBox.getItems().add(levelS);
        }
        levelComboBox.setValue("default");

        levelComboBox.setOnAction(event -> {
            loadFromFileButtonAction();
            AppLogger.logInfo("Load from file button clicked");
        });

        //buttons
        Button loadDefLevelButton = new Button("Load default");
        loadDefLevelButton.setOnAction(event -> {
            loadDefLevelButtonAction();
            AppLogger.logInfo("Default button clicked");
        });

        Button saveToFileButton = new Button("Save");
        saveToFileButton.setOnAction(event -> {
            saveToFileButtonAction();
            AppLogger.logInfo("Save button clicked");
        });

        Button reloadButton = new Button("Set");
        reloadButton.setOnAction(event -> {
            reloadButtonAction();
            AppLogger.logInfo("Reload button clicked");
        });

        Button editGridButton = new Button("Edit Grid");
        editGridButton.setOnAction(event -> {
            editGridButtonAction();
            AppLogger.logInfo("Edit Grid button clicked");
        });

        Button continueButton = new Button("Play");
        continueButton.setOnAction(event -> {
            continueButtonAction();
            AppLogger.logInfo("Play button clicked");

        });

        HBox topPane = new HBox(10);
        topPane.setPadding(new Insets(10));
        topPane.setAlignment(Pos.CENTER);
        topPane.getChildren().addAll(
                tileSizeLabel, tileSizeComboBox,
                edgeWidthLabel, edgeWidthComboBox,
                tilesXLabel, tilesXComboBox,
                tilesYLabel, tilesYComboBox,
                reloadButton
        );

        canvas1 = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gcLeft = canvas1.getGraphicsContext2D();
        Pane canvasPane1 = new Pane(canvas1);

        Label canvas1Label = new Label("Real size of one tile");
        StackPane.setAlignment(canvas1Label, Pos.BOTTOM_CENTER);
        StackPane.setMargin(canvas1Label, new Insets(0, 0, 20, 0));
        StackPane canvasPane1Wrapper = new StackPane(canvasPane1, canvas1Label);

        canvas2 = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gcRight = canvas2.getGraphicsContext2D();
        Pane canvasPane2 = new Pane(canvas2);

        Label canvas2Label = new Label("Scaled grid preview");
        StackPane.setAlignment(canvas2Label, Pos.BOTTOM_CENTER);
        StackPane.setMargin(canvas2Label, new Insets(0, 0, 20, 0));
        StackPane canvasPane2Wrapper = new StackPane(canvasPane2, canvas2Label);

        HBox canvasPane = new HBox(10);
        canvasPane.setPadding(new Insets(10));
        canvasPane.setAlignment(Pos.CENTER);
        canvasPane.getChildren().addAll(canvasPane1Wrapper, canvasPane2Wrapper);
        canvasPane.setStyle("-fx-border-color: black; -fx-border-width: 1 0 1 0;");


        HBox bottomPane = new HBox(10);
        bottomPane.setPadding(new Insets(10));
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.getChildren().addAll(backButton, loadDefLevelButton, levelComboBox, saveToFileButton, editGridButton, continueButton);

        BorderPane root = new BorderPane();
        root.setTop(topPane);
        root.setCenter(canvasPane);
        root.setBottom(bottomPane);



        setCurrentLevel();
        reloadButtonAction();
        loadFromFileButtonAction();
        scene = new Scene(root);
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * initializes default (border) grid with comboBox tilesX x comboBox tilesY dimensions
     */
    private void initPreviewGrid(){
        loadCurrentValues();

        grid.setEmpty();
        grid.setTilesX(tilesX);
        grid.setTilesY(tilesY);
        grid.setBorder();

        drawPreview();
    }

    /**
     * draw scaled preview grid with level values
     */
    private void drawPreview(){
        gcRight.setFill(canvasBackColor);
        gcRight.fillRect(0,0, CANVAS_WIDTH, CANVAS_HEIGHT);
        double tileScale = (double)prGridTileSize/grid.getTileSize();

        int xs = (CANVAS_WIDTH - (prGridTileSize*tilesX))/2;
        int ys = (CANVAS_HEIGHT - (prGridTileSize*tilesY))/2;

        double t1x = level.getTank1X();
        double t1y = level.getTank1Y();

        double t2x = level.getTank2X();
        double t2y = level.getTank2Y();

        //draw tank 1 pos
        gcRight.setFill(GameSettings.getTank1Color());
        gcRight.fillRect(xs + t1x * tileScale, ys + t1y*tileScale, Constants.tankSize*tileScale, Constants.tankSize*tileScale);

        //draw tank2 pos
        gcRight.setFill(GameSettings.getTank2Color());
        gcRight.fillRect(xs + t2x * tileScale, ys + t2y *tileScale, Constants.tankSize*tileScale, Constants.tankSize*tileScale);

        gcRight.setFill(gridColor);
        for (Edge edge: grid.getEdges()){
            gcRight.fillRect((int)(edge.getX()*tileScale+xs), (int)(edge.getY()*tileScale+ys), (int)(edge.getWidth()*tileScale), (int)(edge.getHeight()*tileScale));
        }
    }

    /**
     * draw tile in real size with current values
     */
    private void drawTileRealSize(){
        int xs = (int)(CANVAS_WIDTH-(tileSize+edgeWidth))/2;
        int ys = (int)(CANVAS_HEIGHT-(tileSize+edgeWidth))/2;

        gcLeft.setFill(gridColor);
        gcLeft.fillRect(xs, ys, tileSize + edgeWidth, edgeWidth);
        gcLeft.fillRect(xs, ys, edgeWidth, tileSize+edgeWidth);

        gcLeft.fillRect(xs+tileSize, ys, edgeWidth, tileSize+edgeWidth);
        gcLeft.fillRect(xs, ys+tileSize, tileSize+edgeWidth, edgeWidth);
    }

    /**
     * draw tank in real size
     */
    private void drawTankRealSize(){
        Image tank = new Image(Constants.defTankFile);
        int xs = (int)(CANVAS_WIDTH-tank.getWidth())/2;
        int ys = (int)(CANVAS_HEIGHT-tank.getHeight())/2;
        gcLeft.drawImage(tank, xs, ys);
    }

    /**
     * loads values from all comboBoxes to current values
     */
    private void loadCurrentValues(){
        tileSize = tileSizeComboBox.getValue();
        edgeWidth = edgeWidthComboBox.getValue();
        tilesX = tilesXComboBox.getValue();
        tilesY = tilesYComboBox.getValue();
    }


    /**
     * set level and grid values to current values
     * opens a grid editor with current values
     */
    private void editGridButtonAction(){
        grid.setEdgeWidth(edgeWidth);
        grid.setTileSize(tileSize);
        grid.setTilesX(tilesX);
        grid.setTilesY(tilesY);
        level.setGrid(grid);

        level.setWidth(tileSize*tilesX+edgeWidth);
        level.setHeight(tileSize*tilesY+edgeWidth);

        appController.loadGridEditor();
    }

    /**
     * loads values from comboBoxes into grid and level
     */
    private void setCurrentLevel(){
        loadCurrentValues();

        grid.setEdgeWidth(edgeWidth);
        grid.setTileSize(tileSize);
        grid.setTilesX(tilesX);
        grid.setTilesY(tilesY);
        level.setGrid(grid);
        level.setWidth(tileSize*tilesX+edgeWidth);
        level.setHeight(tileSize*tilesY+edgeWidth);

        level.setTank1X((tileSize-Constants.tankSize)/2 + (int)((double)edgeWidth/2));
        level.setTank1Y((tileSize-Constants.tankSize)/2 + (int)((double)edgeWidth/2));

        level.setTank2X((tilesX-1) * tileSize + (tileSize-Constants.tankSize)/2 + (int)((double)edgeWidth/2));
        level.setTank2Y((tilesY-1) * tileSize + (tileSize-Constants.tankSize)/2 + (int)((double)edgeWidth/2));
    }


    /*button actions*/

    /**
     * load chosen values and redraw grid with new dimensions
     * redraw preview grid and tile real size preview
     */
    private void reloadButtonAction(){
        gcRight.setFill(canvasBackColor);
        gcRight.fillRect(0,0, CANVAS_WIDTH, CANVAS_HEIGHT);

        gcLeft.setFill(canvasBackColor);
        gcLeft.fillRect(0,0, CANVAS_WIDTH, CANVAS_HEIGHT);

        loadCurrentValues();

        level.setWidth(tileSize*tilesX+edgeWidth);
        level.setHeight(tileSize*tilesY+edgeWidth);

        //draw tank 1 pos
        level.setTank1X((tileSize-Constants.tankSize)/2 + (int)((double)edgeWidth/2));
        level.setTank1Y((tileSize-Constants.tankSize)/2 + (int)((double)edgeWidth/2));

        //draw tank 2 pos
        level.setTank2X((tilesX-1) * tileSize + (tileSize-Constants.tankSize)/2 + (int)((double)edgeWidth/2));
        level.setTank2Y((tilesY-1) * tileSize + (tileSize-Constants.tankSize)/2 + (int)((double)edgeWidth/2));

        grid.setBorder(tileSize, edgeWidth, tilesX, tilesY);

        initPreviewGrid();

        drawTileRealSize();
        drawTankRealSize();
    }


    /**
     * loads level from currently selected levelComboboxValue
     * sets other comboBoxes to this level values
     * sets current grid to this levels grid
     */
    private void loadFromFileButtonAction(){
        grid.setEmpty();

        level.loadLevelFromFile(levelComboBox.getValue() + ".txt");

        tileSizeComboBox.setValue(level.getGrid().getTileSize());
        edgeWidthComboBox.setValue(level.getGrid().getEdgeWidth());
        tilesYComboBox.setValue(level.getGrid().getTilesY());
        tilesXComboBox.setValue(level.getGrid().getTilesX());
        grid = level.getGrid();

        gcRight.setFill(canvasBackColor);
        gcRight.fillRect(0,0, CANVAS_WIDTH, CANVAS_HEIGHT);

        gcLeft.setFill(canvasBackColor);
        gcLeft.fillRect(0,0, CANVAS_WIDTH, CANVAS_HEIGHT);

        loadCurrentValues();

        drawTileRealSize();
        drawTankRealSize();

        drawPreview();
    }

    /**
     * saves the level
     */
    private void saveToFileButtonAction(){
        level.saveLevelToFile(levelComboBox.getValue()+".txt");
    }

    /**
     * reloads the scene with default level
     * sets comboBoxes values to default level values
     * sets current grid to default level grid
     */
    private void loadDefLevelButtonAction(){
        grid.setEmpty();

        level.loadLevelFromFile("default.txt");

        tileSizeComboBox.setValue(level.getGrid().getTileSize());
        edgeWidthComboBox.setValue(level.getGrid().getEdgeWidth());
        tilesYComboBox.setValue(level.getGrid().getTilesY());
        tilesXComboBox.setValue(level.getGrid().getTilesX());
        grid = level.getGrid();

        gcRight.setFill(canvasBackColor);
        gcRight.fillRect(0,0, CANVAS_WIDTH, CANVAS_HEIGHT);

        gcLeft.setFill(canvasBackColor);
        gcLeft.fillRect(0,0, CANVAS_WIDTH, CANVAS_HEIGHT);

        loadCurrentValues();

        drawTileRealSize();
        drawTankRealSize();

        drawPreview();
    }

    /**
     * go to main menu
     */
    private void backButtonAction(){
        appController.loadMainMenu();
    }

    /**
     * go to game scene
     */
    private void continueButtonAction(){
        appController.loadGame();
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level){
        this.level = level;
        this.grid = level.getGrid();
        drawPreview();
    }
}
