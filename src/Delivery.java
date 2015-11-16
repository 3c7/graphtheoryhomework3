/**
 * Created by Nils on 16.11.2015.
 */
public class Delivery {
    private static int xmax, ymax;
    private static boolean[][] map;
    private static char[][] col;
    private static int[][][] prev;
    private static String path;

    public static String search(boolean[][] map) {
        Delivery.map = map;
        xmax = map.length;
        ymax = map[0].length;
        col = new char[xmax][ymax];
        prev = new int[xmax][ymax][2];
        path = "";

        dfs(0, 0, xmax - 1, ymax - 1);

        return backtrackPath(xmax - 1, ymax - 1);
    }

    public static void dfs(int startx, int starty, int goalx, int goaly) {
        // DFS
        for (int i = 0; i < xmax; i++)
            for (int j = 0; j < ymax; j++) {
                col[i][j] = 'w';
            }

        for (int i = 0; i < xmax; i++)
            for (int j = 0; j < ymax; j++)
                if (col[i][j] == 'w') {
                    dfsVisit(i, j, goalx, goaly);
                }
    }

    public static void dfsVisit(int x, int y, int goalx, int goaly) {
        if (col[x][y] != 'w') {
            System.out.println("Zyklus verhindert.");
            return;
        }
        col[x][y] = 'g';

        // This is the end, so return
        if (x == goalx && y == goaly) {
            return;
        }

        // For all white neighbours
        // RIGHT
        if (y + 1 < ymax && !map[x][y + 1]) {

            // Recursive call
            if (col[x][y + 1] == 'w') {

                // Predecessor
                prev[x][y + 1][0] = x;
                prev[x][y + 1][1] = y;

                dfsVisit(x, y + 1, goalx, goaly);
            }
        }
        // DOWN
        if (x + 1 < xmax && !map[x + 1][y]) {

            // Recursive call
            if (col[x + 1][y] == 'w') {

                // Predecessor
                prev[x + 1][y][0] = x;
                prev[x + 1][y][1] = y;

                dfsVisit(x + 1, y, goalx, goaly);
            }
        }
        // UP
        if (x > 0 && !map[x - 1][y]) {

            // Recursive call
            if (col[x - 1][y] == 'w') {

                // Predecessor
                prev[x - 1][y][0] = x;
                prev[x - 1][y][1] = y;

                dfsVisit(x - 1, y, goalx, goaly);
            }
        }
        // LEFT
        if (y > 0 && !map[x][y - 1]) {

            // Recursive call
            if (col[x][y - 1] == 'w') {

                // Predecessor
                prev[x][y - 1][0] = x;
                prev[x][y - 1][1] = y;

                dfsVisit(x, y - 1, goalx, goaly);
            }
        }

        col[x][y] = 's';
    }

    public static String backtrackPath(int fromx, int fromy) {
        int x = fromx, y = fromy;
        while (x > 0 || y > 0) {
            int xprev = prev[x][y][0];
            int yprev = prev[x][y][1];
            if (x - xprev > 0) {
                path = "d" + path;
            }
            if (xprev - x > 0) {
                path = "u" + path;
            }
            if (y - yprev > 0) {
                path = "r" + path;
            }
            if (yprev - y > 0) {
                path = "l" + path;
            }


            x = xprev;
            y = yprev;
        }
        return path;
    }

    public static boolean checkPath(boolean[][] map, String path) {
        int l = path.length();        // Laenge des Weges
        int size = map.length;        // Groesse des Labyrinths sowohl in x- als auch in y-Richtung
        int x = 0;            // aktuelle x-Position des Robots (Zeile)
        int y = 0;            // aktuelle y-Position des Robots (Spalte)
        char move;            // einzelne Bewegung des Roboters

        for (int i = 0; i < l; i++) {    // Laufe den Weg des Roboters ab.
            move = path.charAt(i);    // Bewegung, die der Roboter ausfuehren soll
            switch (move) {
                case 'd':
                    x = x + 1;
                    break;    // nach unten (down) ==> Zeile + 1
                case 'u':
                    x = x - 1;
                    break;    // nach oben (up) ==> Zeile - 1
                case 'r':
                    y = y + 1;
                    break;    // nach rechts (right) ==> Spalte + 1
                case 'l':
                    y = y - 1;
                    break;    // nach links (left) ==> Spalte - 1
                default:
                    return false;        // alle anderen Zeichen duerfen hier nicht auftreten
            }
            // Wenn der Roboter aus dem Labyrinth oder
            // gegen einen Felsen gelaufen ist, dann ist der Weg
            // ungueltig.
            if (x < 0 || x >= size || y < 0 || y >= size || map[x][y]) {
                return false;
            }
        }
        // Wenn man hier angekommen ist, dann ist der Weg gueltig.
        return x == size - 1 && y == size - 1;    // Pruefe nun, ob die Zielposition erreicht ist.
    }

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
        boolean[][] testmap1 = {
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, true, false, false, false, false, false, false, false, false},
                {false, false, true, false, false, false, true, false, false, false},
                {false, false, false, false, true, false, false, false, false, false},
                {false, false, false, true, false, false, false, true, false, false},
                {false, false, false, false, true, false, false, false, false, true},
                {false, false, false, false, false, false, false, false, true, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, true, true, false, true, false, false, false}
        };
        boolean[][] testmap2 = {
                {false, false, false, false, true, true, false, false, true, false},
                {false, false, false, false, false, false, false, true, false, false},
                {false, false, true, false, false, true, false, true, false, false},
                {false, false, true, true, false, false, true, false, true, false},
                {true, true, false, false, false, false, true, false, false, false},
                {false, false, false, false, true, false, true, false, true, false},
                {false, false, true, false, false, false, false, true, false, false},
                {false, false, false, false, false, true, false, true, false, false},
                {false, false, false, false, false, false, true, false, false, false},
                {false, false, false, false, false, false, false, false, false, false}
        };
        System.out.println(checkPath(testmap0, search(testmap0)));
        System.out.println(checkPath(testmap1, search(testmap1)));
        System.out.println(checkPath(testmap2, search(testmap2)));
    }
}
