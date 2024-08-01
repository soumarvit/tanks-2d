package cvut.soumar.java.tanks2d.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    public void testSetEmpty() {
        Grid grid = new Grid();

        grid.addEdge(0, 0, 10, 10);
        grid.addEdge(10, 10, 20, 20);

        assertFalse(grid.getEdges().isEmpty());

        grid.setEmpty();

        assertTrue(grid.getEdges().isEmpty());
    }

}