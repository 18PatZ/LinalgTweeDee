import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ScaleArm extends Objekt {

    ScreenI screen;

    public int position = 0;

    public List<Powercube> left = new ArrayList<>();
    public List<Powercube> right = new ArrayList<>();

    public ScaleArm(ScreenI screen){
        this.screen = screen;

        x = 5;
        y = -3;
        angle = Math.toRadians(10);
        roll = Math.toRadians(0);

        vertical = 1.8;

        color = Color.SADDLEBROWN;

        points = new double[][]{
                {0.4, -0.4, 0.4, -0.4,  0, 0,   0,    0,  -0.8, 0.8, -0.8, 0.8, 0,    0},
                {  0,    0,   0,    0,  0, 2,   0,    2,     0,   0,    0,   0, 1,    1},
                { .3,   .3, -.3,  -.3, .3,.3, -.3,  -.3,    .3,  .3,  -.3, -.3,-.3,  .3}};
        //        1    2     3      4   5  6    7     8     9    10   11    12  13   14   15  16

        lineIndices = new int[][]
                {{9, 10}, {9, 11}, {11, 12}, {10, 12}};

    }

    @Override
    public void tick(){

        double speed = Math.PI / 90.0;

        position = (int) Math.signum(left.size() - right.size());

        if(position == 1)
            roll = Math.min(roll + speed, Math.toRadians(20));
        else if(position == -1)
            roll = Math.max(roll - speed, Math.toRadians(-20));
        else {
            if(roll >= 0)
                roll = Math.max(0, roll - speed);
            else
                roll = Math.min(0, roll + speed);
        }

        left.forEach(c -> check(c, true));
        right.forEach(c -> check(c, false));

    }

    private void check(Powercube cube, boolean left){
        if(cube != null) {
            cube.roll = roll;
//            cube.angle = Math.toRadians(20);

            double dx = cube.x - x;
            double dy = cube.y - y;

            double mag = Math.sqrt(dx * dx + dy * dy);

            cube.vertical = - Math.tan((left ? 1 : -1) * roll) * mag + 2;
        }
    }

}