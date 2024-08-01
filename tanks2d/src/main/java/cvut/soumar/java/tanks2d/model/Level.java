package cvut.soumar.java.tanks2d.model;

import cvut.soumar.java.tanks2d.utils.LevelLoader;

/**
 * Class level holds all information about a game level
 * such as player positions, game grid, game window size...
 */
public class Level {

    private Grid grid = new Grid();
    private double tank1X, tank1Y, tank2X, tank2Y;
    private double width = 0, height = 0;
    private final LevelLoader levelLoader;


    public Level(){
        levelLoader = new LevelLoader(this);
    }

    /**
     * loads level from a file
     * @param filename file the level will be loaded from
     */
    public void loadLevelFromFile(String filename){
        levelLoader.loadLevel(filename);
    }

    /**
     * saves level to a file
     * @param filename file the level will be saved to
     */
    public void saveLevelToFile(String filename){
        levelLoader.saveLevel(filename);
    }


    public Grid getGrid() {
        return grid;
    }

    public double getTank1X() {
        return tank1X;
    }

    public double getTank1Y() {
        return tank1Y;
    }

    public double getTank2X() {
        return tank2X;
    }

    public double getTank2Y() {
        return tank2Y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void setTank1X(double tank1X) {
        this.tank1X = tank1X;
    }

    public void setTank1Y(double tank1Y) {
        this.tank1Y = tank1Y;
    }

    public void setTank2X(double tank2X) {
        this.tank2X = tank2X;
    }
    public void setTank2Y(double tank2Y) {
        this.tank2Y = tank2Y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Level{" +
                "grid=" + grid +
                ", tank1X=" + tank1X +
                ", tank1Y=" + tank1Y +
                ", tank2X=" + tank2X +
                ", tank2Y=" + tank2Y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
