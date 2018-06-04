public class Point {

    double x;
    double y;
    double z;

    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Point midpoint(Point p1, Point p2){
        return new Point(m(p1.x, p2.x), m(p1.y, p2.y), m(p1.z, p2.z));
    }

    private static double m(double a, double b){
        return (a + b) / 2.0;
    }

    public static Point fromArray(double[] points){
        return new Point(points[0], points[1], points.length > 2 ? points[2] : 0);
    }

    public String print(){
        return "(" + x + ", " + y + ", " + z + ")";
    }

}
