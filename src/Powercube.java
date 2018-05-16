import javafx.scene.paint.Color;

public class Powercube extends Objekt {

    ScreenI screen;

    public boolean falling = true;

    public Powercube(ScreenI screen){
        this.screen = screen;

        angle = Math.toRadians(0);

        color = Color.YELLOW;

        points = new double[][]{
                {.125, -.125,  .125, -.125,   .125, -.125,  .125, -.125},
                {   0,     0,     0,     0,    .25,   .25,   .25,   .25},
                {.125,  .125, -.125, -.125,   .125,  .125, -.125, -.125}};
        //     1       2     3       4      5      6      7      8

        lineIndices = new int[][]
                {{1, 2}, {2, 4}, {3, 4}, {3, 1},
                        {5, 6}, {5, 7}, {7, 8}, {6, 8}, {5, 1}, {6, 2}, {7, 3}, {8, 4}};

    }

    @Override
    public void tick(){
        if(falling)
            vertical = Math.max(-.125, vertical - 1.0 / 20.0);
    }

}

