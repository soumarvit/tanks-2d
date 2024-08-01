package cvut.soumar.java.tanks2d.controller;

import cvut.soumar.java.tanks2d.model.GameModel;
import cvut.soumar.java.tanks2d.view.View;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import cvut.soumar.java.tanks2d.utils.AppLogger;

/**
 * handles user input for game scene
 * handles main game loop
 */
public class GameController extends AnimationTimer {
    private final GameModel gameModel;
    private final View view;

    private AppController appController;
    private double lastUpdate = 0;

    public GameController(GameModel gameModel, View view, AppController appController) {
        this.appController = appController;
        this.gameModel = gameModel;
        this.view = view;

        view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {

                final KeyCode keyCode = keyEvent.getCode();
                AppLogger.logConfig(keyCode.toString());
                switch (keyCode) {
                    case UP:
                        gameModel.moveTankForwards(gameModel.getTank1());
                        AppLogger.logConfig(gameModel.getTank1().toString());
                        break;
                    case DOWN:
                        gameModel.moveTankBackwards(gameModel.getTank1());
                        AppLogger.logConfig(gameModel.getTank1().toString());
                        break;
                    case LEFT:
                        gameModel.rotateTankLeft(gameModel.getTank1());
                        AppLogger.logConfig(gameModel.getTank1().toString());
                        break;
                    case RIGHT:
                        gameModel.rotateTankRight(gameModel.getTank1());
                        AppLogger.logConfig(gameModel.getTank1().toString());
                        break;
                    case W:
                        gameModel.moveTankForwards(gameModel.getTank2());
                        AppLogger.logConfig(gameModel.getTank2().toString());
                        return;
                    case S:
                        gameModel.moveTankBackwards(gameModel.getTank2());
                        AppLogger.logConfig(gameModel.getTank2().toString());
                        return;
                    case D:
                        gameModel.rotateTankRight(gameModel.getTank2());
                        AppLogger.logConfig(gameModel.getTank2().toString());
                        return;
                    case A:
                        gameModel.rotateTankLeft(gameModel.getTank2());
                        AppLogger.logConfig(gameModel.getTank2().toString());
                        return;
                    case R:
                        appController.loadPopUpMenu();
                }
            }
        });

        view.getScene().addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                final KeyCode keyCode = event.getCode();
                AppLogger.logConfig(keyCode.toString());
                switch (keyCode) {
                    case UP:
                        gameModel.stopMovingTankForwards(gameModel.getTank1());
                        break;
                    case DOWN:
                        gameModel.stopMovingTankBackwards(gameModel.getTank1());
                        break;
                    case LEFT:
                        gameModel.stopRotatingTankLeft(gameModel.getTank1());
                        break;
                    case RIGHT:
                        gameModel.stopRotatingTankRight(gameModel.getTank1());
                        break;
                    case W:
                        gameModel.stopMovingTankForwards(gameModel.getTank2());
                        break;
                    case S:
                        gameModel.stopMovingTankBackwards(gameModel.getTank2());
                        break;
                    case A:
                        gameModel.stopRotatingTankLeft(gameModel.getTank2());
                        break;
                    case D:
                        gameModel.stopRotatingTankRight(gameModel.getTank2());
                        break;
                }
            }
        });


        view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                final KeyCode keyCode = keyEvent.getCode();
                AppLogger.logConfig(keyCode.toString());
                switch (keyCode) {
                    case SPACE:
                        gameModel.shoot(gameModel.getTank1());
                        break;
                    case Q:
                        gameModel.shoot(gameModel.getTank2());
                        break;
                }
            }
        });
    }

    @Override
    public void handle(long now) {
        if (now - lastUpdate > 16_000_000) { //60 fps = 16_000_000, 90fps = 11_000_000, unlimited = 0
            gameModel.update();
            view.render();
            lastUpdate = now;
        }
    }
}
