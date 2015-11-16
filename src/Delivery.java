import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Nils on 16.11.2015.
 */
public class Delivery {
    private static int xmax, ymax;
    private static boolean[][] map;
    private static char[][] col;
    private static int[][] disc;
    private static int[][] fin;
    private static int[][][] prev;
    private static int time = 0;
    private static String path = "";

    public static String suche(boolean[][] map) {
        Delivery.map = map;
        xmax = map.length;
        ymax = map[0].length;
        col = new char[xmax][ymax];
        disc = new int[xmax][ymax];
        fin = new int[xmax][ymax];
        prev = new int[xmax][ymax][2];

        for (int i = 0; i < xmax; i++)
            for (int j = 0; j < ymax; j++) {
                col[i][j] = 'w';
                prev[i][j][0] = 0;
                prev[i][j][1] = 0;
            }

        for (int i = 0; i < xmax; i++)
            for (int j = 0; j < ymax; j++)
                if (col[i][j] == 'w')
                    dfsVisit(i, j);
        return "";
    }

    public static void dfsVisit(int x, int y) {
        if (map[x][y]) {
            System.out.println("(" + x + ", " + y + ") Hier liegt ein Hindernis.");
            col[x][y] = 's';
            return;
        }
        if (col[x][y] != 'w') {
            System.out.println("Zyklus verhindert.");
            return;
        }
        col[x][y] = 'g';
        time++;
        disc[x][y] = time;

        // For all white neighbours
        // DOWN
        if (x + 2 < xmax && !map[x + 1][y]) {

            // Recursive call
            if (col[x + 1][y] == 'w') {

                // Predecessor
                prev[x + 1][y][0] = x;
                prev[x + 1][y][1] = y;

                dfsVisit(x + 1, y);
            }
        }
        // RIGHT
        if (y + 2 < ymax && !map[x][y + 1]) {

            // Recursive call
            if (col[x][y + 1] == 'w') {

                // Predecessor
                prev[x][y + 1][0] = x;
                prev[x][y + 1][1] = y;

                dfsVisit(x, y + 1);
            }
        }
        // UP
        if (x > 0 && !map[x - 1][y]) {

            // Recursive call
            if (col[x - 1][y] == 'w') {

                // Predecessor
                prev[x - 1][y][0] = x;
                prev[x - 1][y][1] = y;

                dfsVisit(x - 1, y);
            }
        }
        // LEFT
        if (y > 0 && !map[x][y - 1]) {

            // Recursive call
            if (col[x][y - 1] == 'w') {

                // Predecessor
                prev[x][y - 1][0] = x;
                prev[x][y - 1][1] = y;

                dfsVisit(x, y - 1);
            }
        }

        col[x][y] = 's';
        time++;
        fin[x][y] = time;
    }

   /* public static String backtrackPath(int fromx, int fromy) {
        int x = fromx, y = fromy;
        while (x > 0 && y > 0) {
            int xprev = prev[x][y][0];
            int yprev = prev[x][y][1];



        }
    }*/

    public static void main(String[] args) {
        boolean[][] testmap0 = {
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, true, false},
                {false, false, false, false, false, false, true, true, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {true, false, false, false, false, false, false, false, true, false},
                {false, false, false, false, false, false, false, false, true, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, true, false, false, false, false}
        };
        suche(testmap0);
        for (int i = 0; i<xmax;i++) {
            for(boolean j : testmap0[i]) {
                if(j) System.out.print("X   ");
                else System.out.print("O   ");
            }
            System.out.println();
        }
        for (int i = 0; i<xmax;i++) {
            for(int j[] : prev[i]) {
                System.out.print(j[0]+", ");
                System.out.print(j[1]+ "\t");
            }
            System.out.println();
        }
    }
}
