package cvut.soumar.java.tanks2d.utils;

import cvut.soumar.java.tanks2d.data.Constants;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application logger class
 */
public class AppLogger {

    private static final Logger logger= Logger.getLogger(AppLogger.class.getName());

    private AppLogger(){
    }

    public static void initLogger(int loggerLevel){

        switch (loggerLevel){
            case Constants.LOGGER_FINEST -> logger.setLevel(Level.FINEST);
            case Constants.LOGGER_CONFIG -> logger.setLevel(Level.CONFIG);
            case Constants.LOGGER_INFO -> logger.setLevel(Level.INFO);
            case Constants.LOGGER_SEVERE -> logger.setLevel(Level.SEVERE);
            case Constants.LOGGER_OFF -> logger.setLevel(Level.OFF);
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logger.getLevel());
        logger.addHandler(consoleHandler);

        AppLogger.logConfig("LOGGER LEVEL FINEST");
        AppLogger.logConfig("LOGGER LEVEL CONFIG");
        AppLogger.logInfo("LOGGER LEVEL INFO");
        AppLogger.logSevere("LOGGER LEVEL SEVERE");
    }

    public static void logFinest(String text){
        //white color
        logger.log(Level.FINEST,  "\u001B[37m" + text + "\u001B[0m");
    }

     /**
     * used for logging object.toString()
     * used for logging mouse position
     * used for logging tank, collision positions
     * @param text - logged text
    */
    public static void logConfig(String text){
        //green color
        logger.log(Level.CONFIG,  "\u001B[32m" + text + "\u001B[0m");
    }

     /**
     * used for info such as
     * level loaded from/to file
     * settings loaded from/to file
     * scene loaded
     * button pressed..
     * @param text - logged text
     * */
    public static void logInfo(String text){
        //blue color
        logger.log(Level.INFO,  "\u001B[34m" + text + "\u001B[0m");
    }

     /***
     * used for errors
     * @param text - logged text
     */
    public static void logSevere(String text){
        //red color
        logger.log(Level.SEVERE,  "\u001B[31m" + text + "\u001B[0m");
    }


}
