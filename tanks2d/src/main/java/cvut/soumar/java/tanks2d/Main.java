package cvut.soumar.java.tanks2d;

import cvut.soumar.java.tanks2d.controller.AppController;
import cvut.soumar.java.tanks2d.data.Constants;
import cvut.soumar.java.tanks2d.utils.AppLogger;
import cvut.soumar.java.tanks2d.utils.SettingsLoader;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main app
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        AppLogger.initLogger(Constants.LOGGER_INFO);

        SettingsLoader settingsLoader = new SettingsLoader();
        settingsLoader.loadSettings(Constants.settingsFile);

        AppController appController = new AppController(stage);
        appController.loadMainMenu();

    }

    public static void main(String[] args){
        launch();
    }
}
