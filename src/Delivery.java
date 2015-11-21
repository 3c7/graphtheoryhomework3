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

        dfs(xmax - 1, ymax - 1);
        path = backtrackPath(xmax - 1, ymax - 1);
        return path;
    }

    public static void outPath() {
        for (int arr[][] : prev) {
            for (int pos[] : arr) {
                System.out.print(pos[0] + ", " + pos[1] + "\t");
            }
            System.out.println();
        }
    }

    public static void dfs(int goalx, int goaly) {
        // DFS
        for (int i = 0; i < xmax; i++)
            for (int j = 0; j < ymax; j++) {
                col[i][j] = 'w';
                if (map[i][j]) {
                    col[i][j] = 's';
                    prev[i][j][0] = Integer.MAX_VALUE;
                    prev[i][j][1] = Integer.MAX_VALUE;
                }
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
        // If end is not reachable, return null
        // If end is reachable without moving, return empty string
        if (prev[x][y][0] == 0 && prev[x][y][1] == 0 && map.length > 1) {
            return null;
        } else if (prev[x][y][0] == 0 && prev[x][y][1] == 0 && map.length == 1 && !map[x][y]) {
            return "";
        }

        // Backtrace starts on blocked coordinate
        if (map[x][y]) return null;

        while (x > 0 || y > 0) {
            int xprev = prev[x][y][0];
            int yprev = prev[x][y][1];

            if (xprev + yprev == 0 && x + y > 1) return null;

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
}
