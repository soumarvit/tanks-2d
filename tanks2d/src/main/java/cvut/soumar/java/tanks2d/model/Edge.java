package cvut.soumar.java.tanks2d.model;

/**
 * Edge class
 * Fancy rectangle class with compare method
 */
public class Edge implements Comparable<Edge> {

    private final int x, y, width, height;

    public Edge(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * compare edges by width
     * horizontal edge > vertical edge
     * */
    @Override
    public int compareTo(Edge e){
        int r = this.width - e.getWidth();
        if (r == 0){
            int r2 = this.y - e.getY();
            if (r2 == 0){
                return this.x - e.getX();
            }
            return r2;
        }
        return r;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
