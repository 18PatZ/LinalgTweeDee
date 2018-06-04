import javafx.scene.paint.Color;

import java.util.List;

public class Line {

//    public double[] p1;
//    public double[] p2;

    public Point p1;
    public Point p2;
    public Color color = Color.CADETBLUE;

    public Line(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
    }

}
