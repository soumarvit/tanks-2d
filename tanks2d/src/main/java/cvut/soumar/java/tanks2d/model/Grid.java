package cvut.soumar.java.tanks2d.model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class Grid holds information about the game grid
 * the game grid is created from an array of edges, that can be loaded
 * from file, saved to file
 * grid can also be generated randomly
 */
public class Grid {
    private ArrayList<Edge> edges = new ArrayList<>();;
    private int tilesX, tilesY;
    private int tileSize, edgeWidth;

    public Grid(){
    }

    /**
     * sort list using edge compare method
     * horizontal edges first, then vertial
     */
    public void sortList(){
        Collections.sort(edges);
    }

    /**
     * creates random grid with the given values
     * @param tileSize tileSize in pixels
     * @param edgeWidth edgeWith in pixel
     * @param tilesX number of tiles on the x axis
     * @param tilesY number of tiles on the y axis
     */
    public void setRandom(int tileSize, int edgeWidth, int tilesX, int tilesY){
        this.tileSize = tileSize;
        this.edgeWidth=edgeWidth;
        this.tilesX = tilesX;
        this.tilesY = tilesY;
        Random rand = new Random();

        for (int vy = 0; vy < tilesY; vy++){
            for (int vx = 1; vx < tilesX; vx++){
                if (rand.nextInt(2) * rand.nextInt(2) == 1){
                    edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
                }
            }
        }

        for (int hy = 1; hy < tilesY; hy++) {
            for (int hx = 0; hx < tilesX; hx++) {
                if (rand.nextInt(2) * rand.nextInt(2) == 1){
                    edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize+edgeWidth, edgeWidth));
                }
            }
        }

        for (int vy = 0; vy < tilesY; vy++){
            int vx = 0;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
            vx = tilesX;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
        }

        for (int hx = 0; hx < tilesX; hx++){
            int hy = 0;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize, edgeWidth));
            hy = tilesY;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize, edgeWidth));
        }
        sortList();
    }

    /**
     * creates full grid with the given values
     * @param tileSize tileSize in pixels
     * @param edgeWidth edgeWith in pixel
     * @param tilesX number of tiles on the x axis
     * @param tilesY number of tiles on the y axis
     */
    public void setFull(int tileSize, int edgeWidth, int tilesX, int tilesY){
        this.tileSize = tileSize;
        this.edgeWidth=edgeWidth;
        this.tilesX = tilesX;
        this.tilesY = tilesY;

        for (int vy = 0; vy < tilesY; vy++){
            for (int vx = 1; vx < tilesX; vx++){
                edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
            }
        }

        for (int hy = 1; hy < tilesY; hy++) {
            for (int hx = 0; hx < tilesX; hx++) {
                edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize+edgeWidth, edgeWidth));
            }
        }

        for (int vy = 0; vy < tilesY; vy++){
            int vx = 0;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
            vx = tilesX;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
        }

        for (int hx = 0; hx < tilesX; hx++){
            int hy = 0;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize, edgeWidth));
            hy = tilesY;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize, edgeWidth));
        }
        sortList();
    }

    /**
     * creates full grid with grid values
     */
    public void setFull(){
        for (int vy = 0; vy < tilesY; vy++){
            for (int vx = 1; vx < tilesX; vx++){
                edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
            }
        }

        for (int hy = 1; hy < tilesY; hy++) {
            for (int hx = 0; hx < tilesX; hx++) {
                edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize+edgeWidth, edgeWidth));
            }
        }

        for (int vy = 0; vy < tilesY; vy++){
            int vx = 0;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
            vx = tilesX;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
        }

        for (int hx = 0; hx < tilesX; hx++){
            int hy = 0;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize, edgeWidth));
            hy = tilesY;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize, edgeWidth));
        }
        sortList();
    }

    /**
     * creates grid border with grid values
     */
    public void setBorder(){
        for (int vy = 0; vy < tilesY; vy++){
            int vx = 0;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
            vx = tilesX;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
        }

        for (int hx = 0; hx < tilesX; hx++){
            int hy = 0;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize+edgeWidth , edgeWidth));
            hy = tilesY;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize+edgeWidth, edgeWidth));
        }
        sortList();
    }

    /**
     * creates grid border with the given values
     * @param tileSize tileSize in pixels
     * @param edgeWidth edgeWith in pixel
     * @param tilesX number of tiles on the x axis
     * @param tilesY number of tiles on the y axis
     */
    public void setBorder(int tileSize, int edgeWidth, int tilesX, int tilesY){
        this.tileSize = tileSize;
        this.edgeWidth=edgeWidth;
        this.tilesX = tilesX;
        this.tilesY = tilesY;

        for (int vy = 0; vy < tilesY; vy++){
            int vx = 0;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
            vx = tilesX;
            edges.add(new Edge(vx * tileSize, vy * tileSize, edgeWidth, tileSize + edgeWidth));
        }

        for (int hx = 0; hx < tilesX; hx++){
            int hy = 0;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize+edgeWidth, edgeWidth));
            hy = tilesY;
            edges.add(new Edge(hx * tileSize, hy * tileSize, tileSize+edgeWidth, edgeWidth));
        }
        sortList();
    }

    public void addEdge(int x, int y, int w, int h){
        edges.add(new Edge(x, y, w, h));
    }

    public void removeEdge(Edge edge){
        edges.remove(edge);
    }

    public void setEmpty(){
        edges = new ArrayList<>();
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setTilesX(int tilesX) {
        this.tilesX = tilesX;
    }

    public void setTilesY(int tilesY) {
        this.tilesY = tilesY;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public void setEdgeWidth(int edgeWidth) {
        this.edgeWidth = edgeWidth;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public int getTilesX() {
        return tilesX;
    }

    public int getTilesY() {
        return tilesY;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getEdgeWidth() {
        return edgeWidth;
    }

    @Override
    public String toString() {
        StringBuilder edg = new StringBuilder();

        for (Edge edge: edges){
            edg.append(edge).append("\n");
        }

        return "Grid{" +
                "edges=" + edg +
                ", tilesX=" + tilesX +
                ", tilesY=" + tilesY +
                ", tileSize=" + tileSize +
                ", edgeWidth=" + edgeWidth +
                '}';
    }
}
