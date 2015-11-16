import java.util.Collections;

/**
 * Created by Nils on 16.11.2015.
 */
public class Delivery {
    private static boolean[][] visited;
    private static boolean[][] complete;
    private static boolean[][] map;
    private static int[][] visitTime;
    private static int[][] completedTime;
    private static int time;
    private static String path = "";

    public static String search(boolean[][] map) {
        Delivery.map = map;
        visited = new boolean[map.length][map.length];
        complete = new boolean[map.length][map.length];
        visitTime = new int[map.length][map.length];
        completedTime = new int[map.length][map.length];
        dfs();
        pathFinder();
        return path;
    }

    private static void dfs() {
        time = 0;
        for (int x = 0; x < map.length; x++)
            for (int y = 0; y < map.length; y++)
                if (!visited[x][y] && !map[x][y])
                    dfsVisit(x, y);
    }

    private static void dfsVisit(int x, int y) {
        visited[x][y] = true;
        time++;
        visitTime[x][y] = time;

        // Case 1: Down
        if (x + 2 < map.length && !visited[x + 1][y] && !map[x + 1][y]) dfsVisit(x + 1, y);
        // Case 2: Right
        if (y + 2 < map.length && !visited[x][y + 1] && !map[x][y + 1]) dfsVisit(x, y + 1);
        // Case 3: Left
        if (y > 0 && !visited[x][y - 1] && !map[x][y - 1]) dfsVisit(x, y - 1);
        // Case 4: Up
        if (x > 0 && !visited[x - 1][y] && !map[x - 1][y]) dfsVisit(x - 1, y);

        complete[x][y] = true;
        time++;
        completedTime[x][y] = time;
    }

    private static void pathFinder() {
        // Reset the "visited" map
        visited = new boolean[map.length][map.length];

        // Where to start
        int x = map.length - 1;
        int y = map.length - 1;

        pathFinder(x, y);
    }

    private static void pathFinder(int x, int y) {
        // Finished on (0, 0)
        if (x == 0 & y == 0) return;

        // set the coordinates as visited
        visited[x][y] = true;

        // Get the next node with the lowest visit time value
        int[] values = new int[4];
        int min;

        // Reverse Cases
        // Case 1: Up
        if (x > 0 && !map[x - 1][y] && !visited[x - 1][y]) values[0] = visitTime[x - 1][y];
        // Case 2: Left
        if (y > 0 && !map[x][y - 1] && !visited[x][y - 1]) values[1] = visitTime[x][y - 1];
        // Case 3: Right
        if (y + 2 < map.length && !map[x][y + 1] && !visited[x][y + 1]) values[2] = visitTime[x][y + 1];
        // Case 4: Down
        if (x + 2 < map.length && !map[x + 1][y] && !visited[x + 1][y]) values[3] = visitTime[x + 1][y];

        switch (indexOf(values, getMin(values))) {
            case 0:
                path = "d" + path;
                pathFinder(x - 1, y);
                break;
            case 1:
                path = "r" + path;
                pathFinder(x, y - 1);
                break;
            case 2:
                path = "l" + path;
                pathFinder(x, y + 1);
                break;
            case 3:
                path = "u" + path;
                pathFinder(x + 1, y);
                break;
            case -1:
                path = "";
                System.out.println("Path deleted at ("+x+", "+y+")");
        }
    }

    private static int getMin(int[] a) {
        int min = Integer.MAX_VALUE;
        for (int i : a) {
            if (i < min && i > 0) {
                min = i;
            }
        }
        return min;
    }

    private static int indexOf(int[] a, int i) {
        int c = 0;
        for (int j : a) {
            if (i == j) return c;
            c++;
        }
        return -1;
    }

    private static void outArray(boolean[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++)
                System.out.print(array[i][j] + "\t");
            System.out.println();
        }
    }

    private static void outArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++)
                System.out.print(array[i][j] + "\t");
            System.out.println();
        }
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
        search(testmap0);
        System.out.println("Path: " + path);
        System.out.println("Visited:");
        outArray(visited);
        System.out.println("\n\n\nComplete:");
        outArray(complete);
        System.out.println("VisitTime: ");
        outArray(visitTime);
        System.out.println("Checkpath sagt " + checkPath(map, path));
    }

}