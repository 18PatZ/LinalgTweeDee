import javafx.application.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static double[][] points = {{1.000, .500, -.500, -1.000, -.500, .500, .840, .315, -.210, -.360,
            -.210, .315}, {-.800, -.800, -.800, -.800, -.800, -.800, -.400, .125,
        .650, .800, .650, .125}, {.000, -.866, -.866, .000, .866, .866, .000,
        -.546, -.364, .000, .364, .546}};

    public static int[][] lineIndices =
            {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 1}, {7, 8}, {8, 9}, {9, 10}, {10, 11}, {11, 12}, {12, 7}, {1, 7}, {2, 8}, {3, 9}, {4, 10}, {5, 11}, {6, 12}};

    private static double[][] scaling = {{1.5, 0, 0}, {0, 0.8, 0}, {0, 0, 3.0}};

    public static double[][] gridPoints = {{-1, -1, 0, 0, 1, 1}, {3, -3, 3, -3, 3, -3}, {0, 0, 0, 0, 0, 0}};
    public static int[][] gridLineIndices =
            {{1, 2}, {3, 4}, {5, 6}};

    public static double[][] getDisplayed(double[][] points){
        double[][] disp = new double[points[0].length][3];
        for(int i = 0; i < points[0].length; i++){
            double[] col = Util.getColumn(points, i);
            disp[i][0] = col[0] * 100;
            disp[i][1] = col[1] * 100;
            disp[i][2] = col[2] * 100;
        }
        return disp;
    }

    public static List<Line> getLines(double[][] points, int[][] lineIndices){
        points = getDisplayed(points);

        List<Line> lines = new ArrayList<>();

        for (int[] lineIndex : lineIndices)
            lines.add(new Line(Point.fromArray(points[lineIndex[0] - 1]), Point.fromArray(points[lineIndex[1] - 1])));

        return lines;
    }

    public static List<Line> getGridLines(double[][] points){
        points = getDisplayed(points);

        List<Line> lines = new ArrayList<>();

        for (int[] lineIndex : gridLineIndices)
            lines.add(new Line(Point.fromArray(points[lineIndex[0] - 1]), Point.fromArray(points[lineIndex[1] - 1])));

        return lines;
    }

    public static String print(double[][] p){
        String s = "";
        for (double[] doubles : p) {
            for (double aDouble : doubles)
                s += aDouble + " ";
            s += "\n";
        }
        return s;
    }


    public static void main(String[] args){

        System.out.println(print(points));
        System.out.println(print(scaling));
        System.out.println(print(Util.mult(scaling, points)));

//        Screen.lines = getLines(Util.mult(Util.rotationMatrix(Math.PI / 2, 0, 0), points));
        Screen.lines = getLines(points, lineIndices);

        Line l1 = new Line(new Point(0, 0, 0), new Point(4, 4, 0));
        Line l2 = new Line(new Point(0, 4, 0), new Point(4, 0, 4));
        Line l3 = new Line(new Point(0, 2, 0), new Point(2, 0, 0));
        System.out.println(Util.getIntercept(l1, l2).print());
        System.out.println(Util.getIntercept(l1, l3).print());

        System.out.println("");
        Util.orderByDepth(Arrays.asList(l2, l1, l3)).forEach(l -> System.out.println(l.p1.print() + " " + l.p2.print()));

//        Application.launch(Screen.class);
        Application.launch(Screen2.class);
    }

}
