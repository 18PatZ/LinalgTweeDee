import javafx.scene.paint.Color;

public class Player extends Objekt {

    public double elevator = 0;
    public double offset = Math.toRadians(90);

    public ScreenI screen;

    public Powercube powercube;
    public boolean hasCube = true;

    public boolean compressed = true;

    public Player(ScreenI screen){
        this.screen = screen;

        color = Color.CADETBLUE;

        points = new double[][]{
                // [   frame       ]    [  back rails   ]  [vert rail] [    intake       ]   [ elev ]
                {0.3, -0.5, 0.3, -0.5, -0.5, 0.1, -0.5, 0.1, 0.1, 0.1, 0.1, 0.35, 0.1, 0.35, 0.1, 0.1},
                {  0,    0,   0,    0,    0, 1.5,    0, 1.5,   0,   0,   0,   0,   0,   0, 1.5, 1.5},
                {.25,  .25, -.25, -.25, .25, .25, -.25, -.25,.25,-.25, .25, .125,-.25,-.125, .25,-.25}};
        //        1    2     3      4     5    6   7     8     9    10   11   12  13   14   15  16

        lineIndices = new int[][]
                {{1, 2}, {2, 4}, {3, 4}, /*{3, 1},*/ {5, 6}, {7, 8}, {6, 8}, {6, 9}, {8, 10}, {11, 12}, {13, 14}, {15, 9}, {16, 10}, {15, 16}, {9, 10}};
    }

    long lC = 0;

    @Override
    public void tick(){

        double tSpeed = Math.PI * 2.0 / 180.0 * 2.0;
        double speed = Math.PI * 2.0 / 180.0 * 4.0;
        double eSpeed = 1.0 / 40.0;

        if(screen.isPressed("A"))
            angle += tSpeed;
        else if(screen.isPressed("D"))
            angle -= tSpeed;

        if(screen.isPressed("W")){
            x += Math.sin(angle + offset) * speed;
            y += Math.cos(angle + offset) * speed;
        }
        else if(screen.isPressed("S")) {
            x -= Math.sin(angle + offset) * speed;
            y -= Math.cos(angle + offset) * speed;
        }

        if(screen.isPressed("C") && (System.currentTimeMillis() - lC >= 250)){
            lC = System.currentTimeMillis();
            compressed = !compressed;

            if(compressed && !hasCube && elevator < .1)
                if(Math.sqrt(Math.pow(powercube.x - x, 2) + Math.pow(powercube.y - y, 2)) <= 0.25)
                    hasCube = true;
        }

        if(screen.isPressed("Up"))
            elevator = Math.min(elevator + eSpeed, 1);
        else if(screen.isPressed("Down"))
            elevator = Math.max(elevator - eSpeed, 0.05);

        for(int i = 10; i < 14; i++)
            points[1][i] = elevator * 2.5;
        points[1][14] = elevator * 1 + 1.5;
        points[1][15] = elevator * 1 + 1.5;

        if(powercube != null){
            if(hasCube){
                if(compressed) {
                    powercube.vertical = elevator * 2.5 - .125;

                    powercube.angle = angle;

                    powercube.x = x + Math.cos(-angle) * (0.1 + .125);
                    powercube.y = y + Math.sin(-angle) * (0.1 + .125);
                }
                else
                    hasCube = false;

                powercube.falling = !compressed;
            }
        }

        if(compressed){
            points[2][11] = .125;
            points[2][13] = -.125;
        }
        else {
            points[2][11] = .25;
            points[2][13] = -.25;
        }
    }

}
