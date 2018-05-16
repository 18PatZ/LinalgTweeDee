import javafx.scene.paint.Color;

public abstract class Objekt {

    public double[][] points;
    public int[][] lineIndices;

    public double angle;
    public double x = 0;
    public double y = 0;
    public double vertical = 0;

    public Color color;

    public abstract void tick();

}
