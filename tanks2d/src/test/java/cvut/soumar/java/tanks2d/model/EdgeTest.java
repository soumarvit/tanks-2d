package cvut.soumar.java.tanks2d.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    //compared by width
    // if width is the same - compare y coords
    // if y coords are the same - compare x coords
    @Test
    void testCompareTo() {
        Edge e1 = new Edge(10, 10, 50, 10);
        Edge e2 = new Edge(10, 10, 10, 50);

        assertEquals(40, e1.compareTo(e2));
        assertEquals(-40, e2.compareTo(e1));
    }

    @Test
    void testCompareToSameEdges(){
        Edge e1 = new Edge(10, 10, 10, 10);
        Edge e2 = new Edge(10, 10, 10, 10);

        assertEquals(0, e1.compareTo(e2));
        assertEquals(0, e2.compareTo(e1));
    }

    @Test
    void testCompareToSameDimensions(){
        Edge e1 = new Edge(20, 10, 20, 10);
        Edge e2 = new Edge(10, 10, 20, 10);

        assertEquals(10, e1.compareTo(e2));
        assertEquals(-10, e2.compareTo(e1));
    }

    @Test
    void testCompareToSameDimensions2(){
        Edge e1 = new Edge(20, 10, 20, 10);
        Edge e2 = new Edge(20, 20, 20, 10);

        assertEquals(-10, e1.compareTo(e2));
        assertEquals(10, e2.compareTo(e1));
    }
}