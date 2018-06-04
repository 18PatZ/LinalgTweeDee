import java.util.*;

public class Util {

    public static double[][] mult(double[][] a, double[][] b){
        double[][] ans = new double[a.length][b[0].length];

        for(int x = 0; x < a.length; x++)
            for(int i = 0; i < b[0].length; i++)
                ans[x][i] = dot(a[x], getColumn(b, i));

        return ans;
    }

    public static double[][] translate(double[][] a, double dX, double dY, double dZ){
        double[][] b = new double[a.length][a[0].length];

        for(int i = 0; i < a[0].length; i++){
            b[0][i] = a[0][i] + dX;
            b[1][i] = a[1][i] + dY;
            b[2][i] = a[2][i] +dZ;
        }
        return b;
    }

    public static double getAngle(double dX, double dY){
        if(dY != 0)
            return Math.toDegrees(Math.atan(dX / -dY)) + (dY > 0 ? 180 : 0);
        else if(dX != 0)
            return dX > 0 ? 90 : -90;
        return 0;
    }

    public static double dist(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }


    /*public static List<Line> orderByDepth(List<Line> lines){

        List<Line> finalLines = new ArrayList<>();

//        HashMap<Point, List<Line>> segs = new HashMap<>();

        List<Intercept> segments = new ArrayList<>();

        HashMap<Line, List<Intercept>> cepts = new HashMap<>();

        for(int i = 0; i < lines.size(); i++){
            if(!cepts.containsKey(lines.get(i)))
                cepts.put(lines.get(i), new ArrayList<>());

            for(int j = i + 1; j < lines.size(); j++){
                if(!cepts.containsKey(lines.get(j)))
                    cepts.put(lines.get(j), new ArrayList<>());

                Point intercept = getIntercept(lines.get(i), lines.get(j));
                if(intercept != null){
                    cepts.get(lines.get(j)).add(new Intercept(intercept, lines.get(j)));
                    cepts.get(lines.get(i)).add(new Intercept(new Point(intercept.x, intercept.y, getZOnLine(intercept, lines.get(i))), lines.get(i)));
                }
            }
        }

        cepts.keySet().forEach(k -> {

            List<Intercept> list = cepts.get(k);
            Collections.sort(list, Comparator.comparing(c -> ((Double) c.point.x)));

            if(list.size() == 0)
                finalLines.add(k);

            for(int i = 0; i < list.size(); i++){

                Point p1;
                Point p2;

                if(k.p1.x < k.p2.x){
                    p1 = k.p1;
                    p2 = k.p2;
                }
                else {
                    p1 = k.p2;
                    p2 = k.p1;
                }

                Intercept inter = list.get(i);

                Point left;
                Point right;

                if(i == 0) left =p1;
                else
                    left = Point.midpoint(list.get(i - 1).point, inter.point);

                if(i == list.size() - 1) right = p2;
                else
                    right = Point.midpoint(inter.point, list.get(i + 1).point);

                Line line = new Line(left, right);
                line.color = k.color;

                segments.add(new Intercept(inter.point, line));

            }

        });

        Collections.sort(segments, Comparator.comparing(c -> -c.point.z));

        segments.forEach(s -> finalLines.add(s.line));

        return finalLines;
    }*/

    public static List<Line> orderByDepth(List<Line> lines){
        List<Intercept> points = new ArrayList<>();

        for(int i = 0; i < lines.size(); i++){
            Line line = lines.get(i);
            points.add(new Intercept(Point.midpoint(line.p1, line.p2), line));
        }

        Collections.sort(points, Comparator.comparing(c -> c.point.z));

        List<Line> nLines = new ArrayList<>();
        points.forEach(p -> nLines.add(p.line));

        return nLines;
    }

    public static Point getIntercept(Line l1, Line l2){

        double s2 = getSlope(l2);

        double slope = getSlope(l1) - s2;

        double slice = l2.p1.y + s2 * (l1.p1.x - l2.p1.x);

        double diff = -(l1.p1.y - slice);

        double dx = diff / slope;

        if(dx < 0) return null;

        double nx = dx + l1.p1.x;
        if(nx > l1.p2.x || nx > l2.p2.x || nx < l2.p1.x)
            return null;

        return new Point(nx, (nx - l2.p1.x) * s2 + l2.p1.y, (nx - l2.p1.x) * (l2.p2.z - l2.p1.z) / (l2.p2.x - l2.p1.x) + l2.p1.z);
    }

    public static double getZOnLine(Point p, Line l2){
        return (p.x - l2.p1.x) * (l2.p2.z - l2.p1.z) / (l2.p2.x - l2.p1.x) + l2.p1.z;
    }

    public static double getSlope(Line line){
        return (line.p2.y - line.p1.y) / (line.p2.x - line.p1.x);
    }

    public static double[] rotate(double x, double y, double theta){
        double rang = Math.atan(((double)y)/x) + (x < 0 ? Math.PI : 0);
        double ang = rang - theta * Math.PI/180;
        double hyp = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double[] newCoords = {Math.cos(ang) * hyp, Math.sin(ang) * hyp};
        return newCoords;
    }

    // in radians
    public static double[][] rotationMatrix(double a, double b, double c){
        return mult(new double[][]
                {{1, 0, 0}, {0, Math.cos(a), -Math.sin(a)}, {0, Math.sin(a), Math.cos(a)}}, mult(new double[][]
                {{Math.cos(b), 0, Math.sin(b)}, {0, 1, 0}, {-Math.sin(b), 0, Math.cos(b)}}, new double[][]
                {{Math.cos(c), -Math.sin(c), 0}, {Math.sin(c), Math.cos(c), 0}, {0, 0, 1}}));
    }

    public static double[][] transpose(double[][] a){
        double[][] ans = new double[a[0].length][a.length];

        for(int x = 0; x < a[0].length; x++)
            ans[x] = getColumn(a, x);

        return ans;
    }

    public static double[] getColumn(double[][] a, int c){
        double[] column = new double[a.length];
        for(int i = 0; i < a.length; i++)
            column[i] = a[i][c];
        return column;
    }

    public static double dot(double[] a, double[] b){
        double s = 0;
        for(int i = 0; i < a.length; i++)
            s += a[i] * b[i];
        return s;
    }

    public static double getDet(double a, double b, double c, double d){
        return a * d - b * c;
    }



}
