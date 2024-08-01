package cvut.soumar.java.tanks2d.data;

import javafx.scene.paint.Color;


/**
 * Class with defined constant values used in the app
 */
public class Constants {

    public static final int FORWARDS = 1;
    public static final int BACKWARDS = -1;
    public static final int ROTATE_RIGHT = 1;
    public static final int ROTATE_LEFT = -1;

    public static final int BULLETUP = -1;
    public static final int BULLETDOWN = 1;
    public static final int BULLETRIGHT = 1;
    public static final int BULLETLEFT = -1;


    //tank
    public static final String defTankFile = "grey_tank.png";

    //settings
    public static final String settingsFile = "settings.txt";
    public static final String defaultSettingsFile = "default_settings.txt";

    //explosion
    public static final String boomFile = "boom.png";
    public static final int boomImageW = 140;
    public static final int boomImageH = 105;


    //bullet
    public static final String bulletFile = "bullet12.png";
    public static final double bulletSize = 12;
    public static final double tankSize = 50;

    //background
    public static Color backgroundColor = Color.WHITESMOKE;


    //logger
    public static final int LOGGER_OFF = 0;
    public static final int LOGGER_SEVERE = 4;
    public static final int LOGGER_INFO = 3;
    public static final int LOGGER_CONFIG = 2;

    public static final int LOGGER_FINEST = 1;
}
