package cvut.soumar.java.tanks2d.data;

import javafx.scene.paint.Color;


/**
 * Class containing variable option values and also their default values
 */
public class GameSettings {


    // default values
    private final static double defaultTankMoveSpeed = 3.5;
    private final static double defaultTankRotateSpeed = 5;
    private final static double defaultBulletTravelSpeed = 5;
    private final static int defaultTankBulletCapacity = 10;
    private final static int defaultBulletTime = 6; //how long bullet exists in seconds

    private final static String defaultTank1Image = "blue_tank.png";
    private final static Color defaultTank1Color = Color.BLUE;
    private final static String defaultTank2Image = "orange_tank.png";
    private final static Color defaultTank2Color = Color.ORANGE;


    //current values
    private static double tankMoveSpeed = defaultTankMoveSpeed;
    private static double tankRotateSpeed = defaultTankRotateSpeed;
    private static double tankRotateSpeedRad;
    private static double bulletTravelSpeed = defaultBulletTravelSpeed;
    private static int tankBulletCapacity = defaultTankBulletCapacity;
    private static int bulletTime = defaultBulletTime; //how long bullet exists in seconds

    private static String tank1Image = Constants.defTankFile;
    private static Color tank1Color = defaultTank1Color;
    private static String tank2Image = Constants.defTankFile;
    private static Color tank2Color = defaultTank2Color;


    private GameSettings(){
    }

    public static double getTankMoveSpeed() {
        return tankMoveSpeed;
    }

    public static double getTankRotateSpeedRad() {
        return tankRotateSpeedRad;
    }


    public static double getTankRotateSpeed() {
        return tankRotateSpeed;
    }


    public static double getBulletTravelSpeed() {
        return bulletTravelSpeed;
    }

    public static int getTankBulletCapacity() {
        return tankBulletCapacity;
    }

    public static int getBulletTime() {
        return bulletTime;
    }


    public static void setTankMoveSpeed(double tankMoveSpeed) {
        GameSettings.tankMoveSpeed = tankMoveSpeed;
    }

    public static void setTankRotateSpeed(double tankRotateSpeed) {
        GameSettings.tankRotateSpeed = tankRotateSpeed;
        GameSettings.tankRotateSpeedRad = Math.toRadians(tankRotateSpeed);
    }

    public static void setBulletTravelSpeed(double bulletTravelSpeed) {
        GameSettings.bulletTravelSpeed = bulletTravelSpeed;
    }
    public static void setTankBulletCapacity(int tankBulletCapacity) {
        GameSettings.tankBulletCapacity = tankBulletCapacity;
    }

    public static void setBulletTime(int bulletTime) {
        GameSettings.bulletTime = bulletTime;
    }

    public static String getTank1Image() {
        return tank1Image;
    }

    public static void setTank1Image(String tank1Image) {
        GameSettings.tank1Image = new String(tank1Image);
    }

    public static Color getTank1Color() {
        return tank1Color;
    }

    public static void setTank1Color(Color tank1Color) {
        GameSettings.tank1Color = tank1Color;
    }

    public static String getTank2Image() {
        return tank2Image;
    }

    public static void setTank2Image(String tank2Image) {
        GameSettings.tank2Image = String.copyValueOf(tank2Image.toCharArray());
    }

    public static Color getTank2Color() {
        return tank2Color;
    }

    public static void setTank2Color(Color tank2Color) {
        GameSettings.tank2Color = tank2Color;
    }


    // Getter methods for default values
    public static double getDefaultTankMoveSpeed() {
        return defaultTankMoveSpeed;
    }

    public static double getDefaultTankRotateSpeed() {
        return defaultTankRotateSpeed;
    }

    public static double getDefaultBulletTravelSpeed() {
        return defaultBulletTravelSpeed;
    }

    public static int getDefaultTankBulletCapacity() {
        return defaultTankBulletCapacity;
    }

    public static int getDefaultBulletTime() {
        return defaultBulletTime;
    }

    public static String getDefaultTank1Image() {
        return defaultTank1Image;
    }

    public static Color getDefaultTank1Color() {
        return defaultTank1Color;
    }

    public static String getDefaultTank2Image() {
        return defaultTank2Image;
    }

    public static Color getDefaultTank2Color() {
        return defaultTank2Color;
    }




}
