import javafx.scene.paint.Color;

public class Scale extends Objekt {

    ScreenI screen;

    public ScaleArm arm;

    public Scale(ScreenI screen, ScaleArm arm){
        this.screen = screen;
        this.arm = arm;

        x = 5;
        y = -3;
        angle = Math.toRadians(10);

        color = Color.SADDLEBROWN;

        points = new double[][]{
                {0.4, -0.4, 0.4, -0.4,  0, 0,   0,    0,  -0.8, 0.8, -0.8, 0.8, 0,    0},
                {  0,    0,   0,    0,  0, 2,   0,    2,     2,   2,    2,   2, 1,    1},
                { .3,   .3, -.3,  -.3, .3,.3, -.3,  -.3,    .3,  .3,  -.3, -.3,-.3,  .3}};
        //        1    2     3      4   5  6    7     8     9    10   11    12  13   14   15  16

        lineIndices = new int[][]
                {{1, 2}, {2, 4}, {3, 4}, {3, 1}, {5, 6}, {7, 8}, /*{9, 10}, {9, 11}, {11, 12}, {10, 12},*/ {6, 8}, {1, 14}, {2, 14}, {3, 13}, {4, 13}};

    }

    @Override
    public void tick(){
    }

}
