package cvut.soumar.java.tanks2d.scenes;


import cvut.soumar.java.tanks2d.controller.AppController;
import cvut.soumar.java.tanks2d.utils.AppLogger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main menu scene class
 */
public class MainMenu {

    private Scene scene;
    private AppController appController;

    public MainMenu(AppController appController){

        this.appController = appController;

        // Create the play button
        Image backgroundImage = new Image("mm_background.png");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(800);
        backgroundImageView.setFitHeight(600);

        // Create the play button
        Button playButton = createButton("PLAY");
        playButton.setOnAction(event -> {
            playButtonAction();
            AppLogger.logInfo("Play button clicked");
        });


        // Create the options button
        Button optionsButton = createButton("OPTIONS");
        optionsButton.setOnAction(event -> {
            optButtonAction();
            AppLogger.logInfo("Options button clicked");
        });

        // Create the exit button
        Button exitButton = createButton("EXIT");
        exitButton.setOnAction(event -> {
            exitButtonAction();
            AppLogger.logInfo("Exit button clicked");
        });


        // Add the buttons to a VBox layout
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(130, 0, 0, 0));
        menuLayout.getChildren().addAll(playButton, optionsButton, exitButton);

        // Create the stack pane and set the background image as the bottom layer
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, menuLayout);

        // Create the scene
        scene = new Scene(stackPane, 800, 600);

    }

    /**
     * create a button with css styling
     * @param text button label
     * @return button
     */
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(200, 80);

        button.setFocusTraversable(false);
        button.setStyle("-fx-border-color: black; -fx-border-width: 3px; -fx-font-size: 30px;");

        return button;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * go to game scene
     */
    private void playButtonAction(){
        appController.loadLevelEditor();
    }

    /**
     * go to options scene
     */
    private void optButtonAction(){
        appController.loadOptions();
    }

    /**
     * exit app
     */
    private void exitButtonAction(){
        appController.exitApp();
    }
}
