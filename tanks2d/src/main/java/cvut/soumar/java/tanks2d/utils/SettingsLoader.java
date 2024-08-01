package cvut.soumar.java.tanks2d.utils;

import cvut.soumar.java.tanks2d.data.GameSettings;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.logging.Logger;


/**
 * Class that loads/saves game settings
 * helper functions for settings
 */
public class SettingsLoader {


    public SettingsLoader(){
    }

    /**
     * save game settings to a file in a predefined format
     * @param filename file the settings get saved to
     */
    public void saveSettings(String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("tankMoveSpeed:"+GameSettings.getTankMoveSpeed()+"\n");
            writer.write("tankRotateSpeed:"+GameSettings.getTankRotateSpeed()+"\n");
            writer.write("bulletTravelSpeed:"+GameSettings.getBulletTravelSpeed()+"\n");
            writer.write("tankBulletCapacity:"+GameSettings.getTankBulletCapacity()+"\n");
            writer.write("bulletTime:"+GameSettings.getBulletTime()+"\n");

            writer.write("tank1Color:" + getTank1ColorString() + "\n");
            writer.write("tank2Color:" + getTank2ColorString() + "\n");

            AppLogger.logInfo("Saved settings to file: "+ filename);

        } catch (IOException e) {
            AppLogger.logSevere("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    /**
     * load game settings to a file in a predefined format
     * @param filename file the settings get loaded from
     */
    public void loadSettings(String filename){
        try (
                FileReader fr = new FileReader(filename);
                BufferedReader br = new BufferedReader(fr);
        ) {
            String s;
            String[] splitS;
            while ((s = br.readLine()) != null){
                splitS = s.split(":", 2);
                AppLogger.logConfig(splitS[0] + " " + splitS[1]);
                switch (splitS[0]) {
                    case "tankMoveSpeed" -> GameSettings.setTankMoveSpeed(Double.parseDouble(splitS[1]));
                    case "tankRotateSpeed" -> {
                        GameSettings.setTankRotateSpeed(Double.parseDouble(splitS[1]));
                    }
                    case "bulletTravelSpeed" -> GameSettings.setBulletTravelSpeed(Double.parseDouble(splitS[1]));
                    case "tankBulletCapacity" -> GameSettings.setTankBulletCapacity(Integer.parseInt(splitS[1]));
                    case "bulletTime" -> GameSettings.setBulletTime(Integer.parseInt(splitS[1]));
                    case "tank1Color" ->{
                        setTank1Color(splitS[1]);
                    }
                    case "tank2Color" ->{
                        setTank2Color(splitS[1]);
                    }
                    default -> throw new Exception("Wrong settings");
                }
            }

            AppLogger.logInfo("loaded settings from file: "+filename);

        } catch (FileNotFoundException e) {
            AppLogger.logSevere("file not found " + e.getMessage());
        } catch (Exception e){
            AppLogger.logSevere(e.getMessage());
        }
    }

    /**
     * @param color based on this string the tank1 color gets changed
     *              if unknown color - tank1 color is set to default
     */
    public void setTank1Color(String color){
        if (color.equals("BLUE")) {
            GameSettings.setTank1Color(Color.BLUE);
            GameSettings.setTank1Image("blue_tank.png");
        } else if (color.equals("CYAN")) {
            GameSettings.setTank1Color(Color.CYAN);
            GameSettings.setTank1Image("cyan_tank.png");
        } else if (color.equals("PINK")) {
            GameSettings.setTank1Color(Color.PINK);
            GameSettings.setTank1Image("pink_tank.png");
        } else if (color.equals("RED")) {
            GameSettings.setTank1Color(Color.RED);
            GameSettings.setTank1Image("red_tank.png");
        } else if (color.equals("YELLOW")) {
            GameSettings.setTank1Color(Color.YELLOW);
            GameSettings.setTank1Image("yellow_tank.png");
        } else if (color.equals("ORANGE")) {
            GameSettings.setTank1Color(Color.ORANGE);
            GameSettings.setTank1Image("orange_tank.png");
        } else if (color.equals("GREY")) {
            GameSettings.setTank1Color(Color.GREY);
            GameSettings.setTank1Image("grey_tank.png");
        } else if (color.equals("GREEN")) {
            GameSettings.setTank1Color(Color.GREEN);
            GameSettings.setTank1Image("green_tank.png");
        } else {
            //set default
            GameSettings.setTank1Color(Color.GREY);
            GameSettings.setTank1Image("grey_tank.png");
        }
    }

    /**
     * @param color based on this string the tank2 color gets changed
     *              if unknown color - tank2 color is set to default
     */
    public void setTank2Color(String color){
        if (color.equals("BLUE")) {
            GameSettings.setTank2Color(Color.BLUE);
            GameSettings.setTank2Image("blue_tank.png");
        } else if (color.equals("CYAN")) {
            GameSettings.setTank2Color(Color.CYAN);
            GameSettings.setTank2Image("cyan_tank.png");
        } else if (color.equals("PINK")) {
            GameSettings.setTank2Color(Color.PINK);
            GameSettings.setTank2Image("pink_tank.png");
        } else if (color.equals("RED")) {
            GameSettings.setTank2Color(Color.RED);
            GameSettings.setTank2Image("red_tank.png");
        } else if (color.equals("YELLOW")) {
            GameSettings.setTank2Color(Color.YELLOW);
            GameSettings.setTank2Image("yellow_tank.png");
        } else if (color.equals("ORANGE")) {
            GameSettings.setTank2Color(Color.ORANGE);
            GameSettings.setTank2Image("orange_tank.png");
        } else if (color.equals("GREY")) {
            GameSettings.setTank2Color(Color.GREY);
            GameSettings.setTank2Image("grey_tank.png");
        } else if (color.equals("GREEN")) {
            GameSettings.setTank2Color(Color.GREEN);
            GameSettings.setTank2Image("green_tank.png");
        } else {
            //set default
            GameSettings.setTank2Color(Color.GREY);
            GameSettings.setTank2Image("grey_tank.png");
        }
    }

    /**
     * @return String color based on current tank1 color
     */
    public String getTank1ColorString(){
        if (GameSettings.getTank1Image().equals("blue_tank.png")) {
            return "BLUE";
        } else if (GameSettings.getTank1Image().equals("cyan_tank.png")) {
            return "CYAN";
        } else if (GameSettings.getTank1Image().equals("pink_tank.png")) {
            return "PINK";
        } else if (GameSettings.getTank1Image().equals("red_tank.png")) {
            return "RED";
        } else if (GameSettings.getTank1Image().equals("yellow_tank.png")) {
            return "YELLOW";
        } else if (GameSettings.getTank1Image().equals("orange_tank.png")) {
            return "ORANGE";
        } else if (GameSettings.getTank1Image().equals("grey_tank.png")) {
            return "GREY";
        } else if (GameSettings.getTank1Image().equals("green_tank.png")) {
            return "GREEN";
        } else {
            //return default
            return "GREY";
        }
    }

    /**
     * @return String color based on current tank2 color
     */
    public String getTank2ColorString(){
        if (GameSettings.getTank2Image().equals("blue_tank.png")) {
            return "BLUE";
        } else if (GameSettings.getTank2Image().equals("cyan_tank.png")) {
            return "CYAN";
        } else if (GameSettings.getTank2Image().equals("pink_tank.png")) {
            return "PINK";
        } else if (GameSettings.getTank2Image().equals("red_tank.png")) {
            return "RED";
        } else if (GameSettings.getTank2Image().equals("yellow_tank.png")) {
            return "YELLOW";
        } else if (GameSettings.getTank2Image().equals("orange_tank.png")) {
            return "ORANGE";
        } else if (GameSettings.getTank2Image().equals("grey_tank.png")) {
            return "GREY";
        } else if (GameSettings.getTank2Image().equals("green_tank.png")) {
            return "GREEN";
        } else {
            //return default
            return "GREY";
        }
    }
}
