package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    int WIDTH;
    int HEIGHT;
    Stack<Position> DRAWING = new Stack<>();
    Map<Position, TETile> DRAWED = new HashMap<Position, TETile>();
    public HexWorld(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
    }
    private class Position {
        private int row;
        private int col;
        public int getRow() {
            return row;
        }
        public int getCol() {
            return col;
        }
        public Position(int r, int c) {
            row = r;
            col = c;
        }
        @Override
        public boolean equals(Object o) {
            Position p = (Position) o;
            if (p.row == row && p.col == col) {
                return true;
            }
            return false;
        }
        @Override
        public String toString() {
            return String.valueOf(row) + " " + String.valueOf(col);
        }
        @Override
        public int hashCode() {
            String code = String.valueOf(row) + " " + String.valueOf(col);
            return code.hashCode();
        }
    }
    private TETile index2pattern(int i) throws IllegalArgumentException{
        switch (i) {
            case 0: return Tileset.FLOWER;
            case 1: return Tileset.GRASS;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.TREE;
            case 4: return Tileset.SAND;
            default: throw new IllegalArgumentException("Invalid pattern index!");
        }
    }
    private TETile[][] drawHexagonWorld(TETile[][] w, int sz) {

        int middlerPos = (int) ((HEIGHT - 1) / 2);
        int middlecPos = (int) ((WIDTH - 1) / 2);
        Position origin = new Position(middlerPos -  (int) ((sz - 1) / 2), middlecPos - (int) ((sz - 1) /2));
        if (!checkSpace(w, origin, sz)) {
            return w;
        }
        Random r = new Random();
        DRAWING.add(origin);
        while (!DRAWING.empty()) {
            int patternIndex = r.nextInt(5);
            drawHexagon(w, DRAWING.pop(), sz, index2pattern(patternIndex));
        }
        return w;
    }
    private void stacking(TETile[][] w, Position pos, int sz) {
        // check 6 neighbors
        Position[] neighbors = new Position[6];
        neighbors[0] = new Position(pos.row - sz * 2, pos.col); // UP
        neighbors[1] = new Position(pos.row - sz, pos.col + 2 * sz - 1); // UP RIGHT
        neighbors[2] = new Position(neighbors[1].row + sz * 2, neighbors[1].col); // DOWN RIGHT
        neighbors[3] = new Position(pos.row + sz * 2, pos.col); // DOWN
        neighbors[4] = new Position(pos.row + sz, pos.col - 2 * sz + 1); // DOWN LEFT
        neighbors[5] = new Position(neighbors[4].row - sz * 2, neighbors[4].col); // UP LEFT
        for (Position n: neighbors) {
            if (checkSpace(w, n, sz) && !DRAWED.containsKey(n) && !DRAWING.contains(n)) {
                DRAWING.push(n);
                System.out.println(String.valueOf(n.row) + ' ' + String.valueOf(n.col));
            }
        }
        System.out.println(DRAWED.keySet());
        System.out.println(DRAWING.size());
        System.out.println();
    }

    private boolean checkSpace(TETile[][] w, Position pos, int sz) {
        if (pos.row < 0 || pos.col - sz + 1 < 0) {
            return false;
        }
        if (pos.col + 2 * sz - 2 > WIDTH - 1 ) {
            return false;
        }
        if (pos.row + sz * 2 - 1 > HEIGHT - 1) {
            return false;
        }
        return true;
    }

    /**
     * draw a Hexagon according to below parameters if possible
     * @param cworld: which world to draw hexagon in.
     * @param pos: left top coner of hexagon.
     * @param sz: row and col size of hexagon
     * @param pattern: which tileset pattern to use
     */
    public void drawHexagon(TETile[][] w, Position pos, int sz, TETile pattern) {
        int offset = 0;
        for (int r = pos.row; r < pos.row + sz; r++) {
            for (int c = pos.col - offset; c < pos.col + sz + offset; c++) {
                w[c][r] = pattern;
            }
            offset += 1;
        }
        for (int r = pos.row + sz; r < pos.row + sz * 2; r++) {
            offset -= 1;
            for (int c = pos.col - offset; c < pos.col + sz + offset; c++) {
                w[c][r] = pattern;
            }
        }
        DRAWED.put(pos, pattern);
        stacking(w, pos, sz);
    }

    public static void main(String[] args) {
        // r and w are reversed in this framwork !!!
        int WIDTH = 60;
        int HEIGHT = 60;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        int sz = 4;
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                world[c][r] = Tileset.NOTHING;
            }
        }
        HexWorld drawer = new HexWorld(WIDTH, HEIGHT);
        world = drawer.drawHexagonWorld(world, sz);
        ter.renderFrame(world);
    }
}
