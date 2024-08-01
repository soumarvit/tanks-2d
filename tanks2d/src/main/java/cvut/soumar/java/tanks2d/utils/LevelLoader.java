package cvut.soumar.java.tanks2d.utils;

import cvut.soumar.java.tanks2d.model.Edge;
import cvut.soumar.java.tanks2d.model.Level;

import java.io.*;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Class that loads/saves game levels
 */
public class LevelLoader {

    int ver_x = 0, ver_y = 0;
    int hor_x = 0, hor_y = 0;
    int tiles_x = 0, tiles_y = 0;
    int t1x = 0, t1y = 0, t2x = 0, t2y = 0;
    int tile_size = 0;
    int edge_width = 0;
    private final Level level;
    public LevelLoader(Level level){
        this.level = level;
    }

    /**
     * save level from this.level to a file in a predefined format
     * @param filename file the level gets saved to
     */
    public void saveLevel(String filename){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("TileSize:"+level.getGrid().getTileSize()+"\n");
            writer.write("EdgeSize:"+level.getGrid().getEdgeWidth()+"\n");
            writer.write("GameSize:"+level.getGrid().getTilesY()+","+level.getGrid().getTilesX()+"\n");
            writer.write("Vertical:"+level.getGrid().getTilesY()+","+(level.getGrid().getTilesX()+1)+"\n");
            writer.write("Horizontal:"+(level.getGrid().getTilesY()+1)+","+level.getGrid().getTilesX()+"\n");
            writer.write("P1_pos:"+(int)level.getTank1X()+","+(int)level.getTank1Y()+"\n");
            writer.write("P2_pos:"+(int)level.getTank2X()+","+(int)level.getTank2Y()+"\n");
            writer.write(" : \n");
            writer.write("V:V\n");

            int ts = level.getGrid().getTileSize();
            boolean write1 = false;
            for (int i = 0; i < level.getGrid().getTilesY(); i++){
                for (int j = 0; j < level.getGrid().getTilesX()+1; j++){
                    for (Edge e: level.getGrid().getEdges()){
                        if (e.getX() == j*ts && e.getY() == i*ts && e.getHeight()>e.getWidth()){
                            writer.write("1");
                            write1 = true;
                            break;
                        }
                    }
                    if (write1){
                        write1 = false;
                    } else {
                        writer.write("0");
                    }

                    if (j != level.getGrid().getTilesX()){
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }

            writer.write(" : \n");
            writer.write("H:H\n");

            write1 = false;
            for (int i = 0; i < level.getGrid().getTilesY()+1; i++){
                for (int j = 0; j < level.getGrid().getTilesX(); j++){
                    for (Edge e: level.getGrid().getEdges()){
                        if (e.getX() == j*ts && e.getY() == i*ts && e.getHeight()<e.getWidth()){
                            writer.write("1");
                            write1 = true;
                            break;
                        }
                    }
                    if (write1){
                        write1 = false;
                    } else {
                        writer.write("0");
                    }

                    if (j != level.getGrid().getTilesX()-1){
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }

            AppLogger.logInfo("Saved level to file" + filename);

        } catch (IOException e) {
            AppLogger.logSevere("An error occurred while writing to the file: " + e.getMessage());
        }

        AppLogger.logConfig(level.toString());
    }

    /**
     * load level into this.level from a file in a predefined format
     * @param filename file the level gets loaded from
     */
    public void loadLevel(String filename){
        try (
                FileReader fr = new FileReader(filename);
                BufferedReader br = new BufferedReader(fr);
        ) {
            String s;
            String[] splitS;
            String[] splitS_2;
            String[] split_tiles;
            while ((s = br.readLine()) != null){
                splitS = s.split(":", 2);
                AppLogger.logConfig(splitS[0] + " " + splitS[1]);
                switch (splitS[0]) {
                    case "TileSize" -> tile_size = Integer.parseInt(splitS[1]);
                    case "EdgeSize" -> edge_width = Integer.parseInt(splitS[1]);
                    case "GameSize" ->{
                        splitS_2 = splitS[1].split(",", 2);
                        tiles_y = Integer.parseInt(splitS_2[0]);
                        tiles_x = Integer.parseInt(splitS_2[1]);
                    }
                    case "Vertical" ->{
                        splitS_2 = splitS[1].split(",", 2);
                        ver_y = Integer.parseInt(splitS_2[0]);
                        ver_x = Integer.parseInt(splitS_2[1]);
                    }
                    case "Horizontal" -> {
                        splitS_2 = splitS[1].split(",", 2);
                        hor_y = Integer.parseInt(splitS_2[0]);
                        hor_x = Integer.parseInt(splitS_2[1]);
                    }
                    case "P1_pos" -> {
                        splitS_2 = splitS[1].split(",", 2);
                        t1x = Integer.parseInt(splitS_2[0]);
                        t1y = Integer.parseInt(splitS_2[1]);
                    }
                    case "P2_pos" -> {
                        splitS_2 = splitS[1].split(",", 2);
                        t2x = Integer.parseInt(splitS_2[0]);
                        t2y = Integer.parseInt(splitS_2[1]);
                    }
                    case " " -> {}
                    case "V" -> {
                        for (int i = 0; i < ver_y; i++){
                            s = br.readLine();
                            split_tiles = s.split(",", ver_x);
                            for (int j = 0; j < ver_x; j++){
                                int x = Integer.parseInt(split_tiles[j]);
                                if (x == 1){
                                    level.getGrid().getEdges().add(new Edge(j*tile_size, i*tile_size, edge_width, tile_size + edge_width));
                                }
                            }
                        }
                    }

                    case "H" -> {
                        for (int i = 0; i < hor_y; i++){
                            s = br.readLine();
                            split_tiles = s.split(",", hor_x);
                            for (int j = 0; j < hor_x; j++){
                                int x = Integer.parseInt(split_tiles[j]);
                                if (x == 1){
                                    level.getGrid().getEdges().add(new Edge(j*tile_size, i*tile_size, tile_size + edge_width, edge_width));
                                }
                            }
                        }
                    }

                    default -> throw new Exception("Wrong settings");
                }
            }

            AppLogger.logInfo("Loaded level from file" + filename);

        } catch (FileNotFoundException e) {
            AppLogger.logSevere("file not found " + e.getMessage());
        } catch (Exception e){
            AppLogger.logSevere(e.getMessage());
        }


        level.setWidth((tile_size * tiles_x) + edge_width);
        level.setHeight((tile_size * tiles_y) + edge_width);

        level.getGrid().setTileSize(tile_size);

        level.getGrid().setEdgeWidth(edge_width);

        level.getGrid().setTilesX(tiles_x);
        level.getGrid().setTilesY(tiles_y);

        level.setTank1X(t1x);
        level.setTank1Y(t1y);

        level.setTank2X(t2x);
        level.setTank2Y(t2y);

        level.getGrid().sortList();

        AppLogger.logConfig(level.toString());
    }
}
