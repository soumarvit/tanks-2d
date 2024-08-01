package cvut.soumar.java.tanks2d.controller;

import cvut.soumar.java.tanks2d.scenes.PopUpMenu;
import cvut.soumar.java.tanks2d.scenes.levelEditor.GridEditor;
import cvut.soumar.java.tanks2d.scenes.levelEditor.LevelEditor;
import cvut.soumar.java.tanks2d.model.GameModel;
import cvut.soumar.java.tanks2d.model.Level;
import cvut.soumar.java.tanks2d.scenes.MainMenu;
import cvut.soumar.java.tanks2d.scenes.Options;
import cvut.soumar.java.tanks2d.utils.AppLogger;
import cvut.soumar.java.tanks2d.view.View;
import javafx.stage.Stage;

import java.nio.file.ClosedFileSystemException;

/**
 * Acts as a scene controller for this app
 * Connects all the scenes together and make them switch
 */
public class AppController {

    private Level currentLevel;
    private Stage stage;
    private GameController gameController;
    private LevelEditor levelEditor;
    private GridEditor gridEditor;
    private MainMenu mainMenu;
    private Options options;

    private PopUpMenu popUpMenu;


    /**
     * creates app controller and a main stage
     * @param stage - main app stage
     */
    public AppController(Stage stage){
        this.currentLevel = new Level();
        this.stage = stage;
    }

    /***
     * loads main menu
     */
    public void loadMainMenu(){
        mainMenu = new MainMenu(this);
        stage.setScene(mainMenu.getScene());
        stage.setTitle("Main menu");
        stage.show();
        stage.setResizable(false);
        AppLogger.logInfo("Loaded main menu scene");
    }

    /***
     * loads options
     */
    public void loadOptions(){
        options = new Options(this);
        stage.setScene(options.getScene());
        stage.setTitle("Options");
        stage.show();
        AppLogger.logInfo("Loaded options scene");
    }

    /***
     * loads level editor with default level
     */
    public void loadLevelEditor(){
        levelEditor = new LevelEditor(this, currentLevel);
        stage.setScene(levelEditor.getScene());

        stage.setTitle("Level editor");
        stage.show();
        AppLogger.logInfo("Loaded level editor scene");

    }

    /***
     * loads level editor with level created in the grid editor
     */
    public void loadLevelEditorWithGrid(){
        currentLevel = gridEditor.getLevel();
        levelEditor.setLevel(currentLevel);
        stage.setScene(levelEditor.getScene());

        stage.setTitle("Level editor");
        stage.show();
        AppLogger.logInfo("Loaded level editor scene");
    }

    /***
     * loads the grid editor with current level
     */
    public void loadGridEditor(){
        currentLevel = levelEditor.getLevel();
        gridEditor = new GridEditor(this, currentLevel);
        stage.setScene(gridEditor.getScene());

        stage.setTitle("Grid editor");
        stage.show();
        AppLogger.logInfo("Loaded grid editor scene");
    }

    /***
     * loads the game with current level
     */
    public void loadGame(){
        currentLevel.getGrid().sortList();
        currentLevel = levelEditor.getLevel();
        GameModel gameModel = new GameModel(currentLevel, this);

        View view = new View(gameModel);
        gameController = new GameController(gameModel, view, this);
        gameController.start();

        stage.setScene(view.getScene());
        stage.setTitle("TANKS 2D");
        stage.show();
        AppLogger.logInfo("Loaded game scene");
    }

    /**
     * exit app
     */
    public void exitApp(){
        System.exit(0);
    }

    public void loadPopUpMenu(){
        this.stop();
        popUpMenu = new PopUpMenu(this);
    }

    public void stop(){
        gameController.stop();
    }



}
