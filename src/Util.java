public class Util {

    public static double[][] mult(double[][] a, double[][] b){
        double[][] ans = new double[a.length][b[0].length];

        for(int x = 0; x < a.length; x++)
            for(int i = 0; i < b[0].length; i++)
                ans[x][i] = dot(a[x], getColumn(b, i));

        return ans;
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
