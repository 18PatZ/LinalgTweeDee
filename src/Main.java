import javafx.application.Application;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static double[][] points = {{1.000, .500, -.500, -1.000, -.500, .500, .840, .315, -.210, -.360,
            -.210, .315}, {-.800, -.800, -.800, -.800, -.800, -.800, -.400, .125,
        .650, .800, .650, .125}, {.000, -.866, -.866, .000, .866, .866, .000,
        -.546, -.364, .000, .364, .546}};

    private static int[][] lineIndices = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 1}, {7, 8}, {8, 9}, {9, 10}, {10, 11}, {11, 12}, {12, 7}, {1, 7}, {2, 8}, {3, 9}, {4, 10}, {5, 11}, {6, 12}};

    private static double[][] scaling = {{1.5, 0, 0}, {0, 0.8, 0}, {0, 0, 3.0}};

    private static double[][] getDisplayed(double[][] points){
        double[][] disp = new double[points[0].length][2];
        for(int i = 0; i < points[0].length; i++){
            double[] col = Util.getColumn(points, i);
            disp[i][0] = col[0] * 100;
            disp[i][1] = col[1] * 100;
        }
        return disp;
    }

    public static List<Line> getLines(double[][] points){
        points = getDisplayed(points);

        List<Line> lines = new ArrayList<>();

        for (int[] lineIndex : lineIndices)
            lines.add(new Line(points[lineIndex[0] - 1], points[lineIndex[1] - 1]));

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
        Screen.lines = getLines(points);

        Application.launch(Screen.class);
    }

}
