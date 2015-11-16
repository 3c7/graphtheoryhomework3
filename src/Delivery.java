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


        // DFS
        for (int i = 0; i < xmax; i++)
            for (int j = 0; j < ymax; j++) {
                col[i][j] = 'w';
            }

        for (int i = 0; i < xmax; i++)
            for (int j = 0; j < ymax; j++)
                if (col[i][j] == 'w') {
                    dfsVisit(i, j);
                }
        return backtrackPath(xmax - 1, ymax - 1);
    }

    public static void dfsVisit(int x, int y) {
        //if (map[x][y]) {
        //    System.out.println("(" + x + ", " + y + ") Hier liegt ein Hindernis.");
        //    col[x][y] = 's';
        //    return;
        //}
        if (col[x][y] != 'w') {
            System.out.println("Zyklus verhindert.");
            return;
        }
        if (x == xmax - 1 && y == ymax - 1) {
            System.out.println("Endpunkt erreicht.");
        }
        col[x][y] = 'g';
        time++;
        disc[x][y] = time;

        // For all white neighbours
        // RIGHT
        if (y + 1 < ymax && !map[x][y + 1]) {

            // Recursive call
            if (col[x][y + 1] == 'w') {

                // Predecessor
                prev[x][y + 1][0] = x;
                prev[x][y + 1][1] = y;

                dfsVisit(x, y + 1);
            }
        }
        // DOWN
        if (x + 1 < xmax && !map[x + 1][y]) {

            // Recursive call
            if (col[x + 1][y] == 'w') {

                // Predecessor
                prev[x + 1][y][0] = x;
                prev[x + 1][y][1] = y;

                dfsVisit(x + 1, y);
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
        //System.out.println(suche(testmap0));
        System.out.println(checkPath(testmap2, suche(testmap2)));
        for (int i = 0; i < xmax; i++) {
            for (boolean j : testmap0[i]) {
                if (j) System.out.print("X   ");
                else System.out.print("O   ");
            }
            System.out.println();
        }
        for (int i = 0; i < xmax; i++) {
            for (int j[] : prev[i]) {
                System.out.print(j[0] + ", ");
                System.out.print(j[1] + "\t");
            }
            System.out.println();
        }
    }
}
