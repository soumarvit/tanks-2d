package cvut.soumar.java.tanks2d.scenes;

import cvut.soumar.java.tanks2d.controller.AppController;
import cvut.soumar.java.tanks2d.data.Constants;
import cvut.soumar.java.tanks2d.data.GameSettings;
import cvut.soumar.java.tanks2d.utils.AppLogger;
import cvut.soumar.java.tanks2d.utils.SettingsLoader;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * Options scene class
 */
public class Options {
    private Scene scene;
    private AppController appController;

    private Slider tankMoveSpeedSlider;
    private Slider tankRotateSpeedSlider;
    private Slider bulletTravelSpeedSlider;
    private Slider tankBulletCapacitySlider;
    private Slider bulletTimeSlider;
    private ComboBox<String> tank1ColorComboBox;
    private ComboBox<String> tank2ColorComboBox;

    private Label tankMoveSpeedValueLabel;
    private Label tankRotateSpeedValueLabel;
    private Label bulletTravelSpeedValueLabel;
    private Label tankBulletCapacityValueLabel;
    private Label bulletTimeValueLabel;

    private final SettingsLoader settingLoader = new SettingsLoader();
    public Options(AppController appController){

        this.appController = appController;

        // Create labels
        Label tankMoveSpeedLabel = new Label("Tank Move Speed:");
        Label tankRotateSpeedLabel = new Label("Tank Rotate Speed:");
        Label bulletTravelSpeedLabel = new Label("Bullet Travel Speed:");
        Label tankBulletCapacityLabel = new Label("Tank Bullet Capacity:");
        Label bulletTimeLabel = new Label("Bullet Time:");
        Label tank1ColorLabel = new Label("Tank 1 Color:");
        Label tank2ColorLabel = new Label("Tank 2 Color:");

        // Create sliders
        tankMoveSpeedSlider = new Slider(1, 10, GameSettings.getTankMoveSpeed());
        tankRotateSpeedSlider = new Slider(1, 10, GameSettings.getTankRotateSpeed());
        bulletTravelSpeedSlider = new Slider(1, 10, GameSettings.getBulletTravelSpeed());
        tankBulletCapacitySlider = new Slider(1, 100, GameSettings.getTankBulletCapacity());
        bulletTimeSlider = new Slider(1, 20, GameSettings.getBulletTime());

        // Create value labels
        tankMoveSpeedValueLabel = new Label();
        tankRotateSpeedValueLabel = new Label();
        bulletTravelSpeedValueLabel = new Label();
        tankBulletCapacityValueLabel = new Label();
        bulletTimeValueLabel = new Label();

        // Bind the value labels to the slider values
        bindValueLabelToSlider(tankMoveSpeedSlider, tankMoveSpeedValueLabel);
        bindValueLabelToSlider(tankRotateSpeedSlider, tankRotateSpeedValueLabel);
        bindValueLabelToSlider(bulletTravelSpeedSlider, bulletTravelSpeedValueLabel);
        bindIntegerValueLabelToSlider(tankBulletCapacitySlider, tankBulletCapacityValueLabel);
        bindIntegerValueLabelToSlider(bulletTimeSlider, bulletTimeValueLabel);

        // Create comboboxes
        tank1ColorComboBox = new ComboBox<>();
        tank1ColorComboBox.getItems().addAll("BLUE", "CYAN", "PINK", "RED", "YELLOW", "ORANGE", "GREY", "GREEN");
        tank1ColorComboBox.setValue(settingLoader.getTank1ColorString());

        tank2ColorComboBox = new ComboBox<>();
        tank2ColorComboBox.getItems().addAll("BLUE", "CYAN", "PINK", "RED", "YELLOW", "ORANGE", "GREY", "GREEN");
        tank2ColorComboBox.setValue(settingLoader.getTank2ColorString());

        // Create a GridPane to hold the labels and controls
        GridPane gridPane = new GridPane();
        gridPane.setHgap(12);
        gridPane.setVgap(12);
        gridPane.setPadding(new Insets(23));

        // Add labels, sliders, and value labels to the GridPane
        gridPane.add(tankMoveSpeedLabel, 0, 0);
        gridPane.add(tankMoveSpeedSlider, 1, 0);
        gridPane.add(tankMoveSpeedValueLabel, 2, 0);
        gridPane.add(tankRotateSpeedLabel, 0, 1);
        gridPane.add(tankRotateSpeedSlider, 1, 1);
        gridPane.add(tankRotateSpeedValueLabel, 2, 1);
        gridPane.add(bulletTravelSpeedLabel, 0, 2);
        gridPane.add(bulletTravelSpeedSlider, 1, 2);
        gridPane.add(bulletTravelSpeedValueLabel, 2, 2);
        gridPane.add(tankBulletCapacityLabel, 0, 3);
        gridPane.add(tankBulletCapacitySlider, 1, 3);
        gridPane.add(tankBulletCapacityValueLabel, 2, 3);
        gridPane.add(bulletTimeLabel, 0, 4);
        gridPane.add(bulletTimeSlider, 1, 4);
        gridPane.add(bulletTimeValueLabel, 2, 4);
        gridPane.add(tank1ColorLabel, 0, 5);
        gridPane.add(tank1ColorComboBox, 1, 5);
        gridPane.add(tank2ColorLabel, 0, 6);
        gridPane.add(tank2ColorComboBox, 1, 6);

        // Create bottom pane with buttons
        HBox bottomPane = new HBox(10);
        bottomPane.setPadding(new Insets(15));
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setStyle("-fx-border-color: black; -fx-border-width: 1 0 0 0;");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            saveButtonAction();
            AppLogger.logInfo("Save button clicked");
        });

        Button defaultButton = new Button("Default");
        defaultButton.setOnAction(event -> {
            defaultButtonAction();
            AppLogger.logInfo("Default button clicked");
        });

        bottomPane.getChildren().addAll(defaultButton, saveButton);

        // Create the main layout pane and set the GridPane and bottom pane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setBottom(bottomPane);

        // Create the scene
        scene = new Scene(borderPane, 380, 300);

    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Helper method to bind a value label to a slider
     * Found on internet but dont remember where
     * format double to one decimal place
     */
    private void bindValueLabelToSlider(Slider slider, Label valueLabel) {
        valueLabel.textProperty().bind(
                Bindings.createStringBinding(() -> String.format("%.1f", slider.getValue()), slider.valueProperty())
        );
    }

    /**
     * Helper method to bind a integer value label to a slider
     * Found on internet but dont remember where
     */
    private void bindIntegerValueLabelToSlider(Slider slider, Label valueLabel) {
        valueLabel.textProperty().bind(Bindings.format("%.0f", slider.valueProperty()));
    }

    /**
     * save settings and go to main menu
     */
    private void saveButtonAction(){
        GameSettings.setTankMoveSpeed(tankMoveSpeedSlider.getValue());
        GameSettings.setTankRotateSpeed(tankRotateSpeedSlider.getValue());
        GameSettings.setBulletTravelSpeed(bulletTravelSpeedSlider.getValue());
        GameSettings.setTankBulletCapacity((int)(tankBulletCapacitySlider.getValue()));
        GameSettings.setBulletTime((int)(bulletTimeSlider.getValue()));

        settingLoader.setTank1Color(tank1ColorComboBox.getValue());
        settingLoader.setTank2Color(tank2ColorComboBox.getValue());
        settingLoader.saveSettings(Constants.settingsFile);

        appController.loadMainMenu();
    }

    /**
     * load default settings
     */
    private void defaultButtonAction(){
        settingLoader.loadSettings(Constants.defaultSettingsFile);
        appController.loadOptions();
    }
}
