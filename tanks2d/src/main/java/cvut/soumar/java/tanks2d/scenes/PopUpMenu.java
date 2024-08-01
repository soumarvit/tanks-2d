package cvut.soumar.java.tanks2d.scenes;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Simple quick pop up menu to quit a level / restart a level / exit
 */
public class PopUpMenu {

    private AppController appController;
    private Stage popupStage;

    public PopUpMenu(AppController appController){

        // Create buttons
        Button restartButton = new Button("Restart level");
        Button levelEditorButton = new Button("Level editor");
        Button mainMenuButton = new Button("Main menu");
        Button optionsButton = new Button("Options");
        Button exitButton = new Button("Exit");

        double buttonWidth = 120;
        restartButton.setPrefWidth(buttonWidth);
        mainMenuButton.setPrefWidth(buttonWidth);
        optionsButton.setPrefWidth(buttonWidth);
        levelEditorButton.setPrefWidth(buttonWidth);
        exitButton.setPrefWidth(buttonWidth);


        // Set actions for the buttons
        restartButton.setOnAction(event ->{
            popupStage.close();
            appController.loadGame();

            AppLogger.logInfo("Play button clicked");
        });

        levelEditorButton.setOnAction(event -> {
            popupStage.close();
            appController.loadLevelEditor();

            AppLogger.logInfo("Level Editor button clicked");
        });

        mainMenuButton.setOnAction(event ->{
            popupStage.close();
            appController.loadMainMenu();

            AppLogger.logInfo("Main Menu button clicked");
        });

        optionsButton.setOnAction(event ->{
            popupStage.close();
            appController.loadOptions();

            AppLogger.logInfo("Options button clicked");
        });

        exitButton.setOnAction(event ->{
            popupStage.close();
            appController.exitApp();

            AppLogger.logInfo("Exit Menu button clicked");
        });


        // Create a vertical layout for the pop-up window
        VBox vbox = new VBox(10);  // Set spacing between elements
        vbox.setAlignment(Pos.CENTER);  // Center align the elements

        // Set padding between label and buttons
        double labelButtonPadding = 30;
        vbox.setPadding(new Insets(labelButtonPadding, 20, labelButtonPadding, 20));
        vbox.getChildren().addAll(restartButton, levelEditorButton, mainMenuButton, optionsButton, exitButton);


        // Create the pop-up window
        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);  // Prevent interaction with other windows
        popupStage.setTitle("Pop up menu");
        popupStage.setScene(new Scene(vbox, 300, 200));

        // Show the pop-up window
         Platform.runLater(() -> popupStage.showAndWait());
    }
}
