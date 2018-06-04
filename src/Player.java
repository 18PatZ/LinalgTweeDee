import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class Player extends Objekt {

    public double elevator = 0;
    public double offset = Math.toRadians(90);

    public ScreenI screen;

    public Powercube powercube;
    public boolean hasCube = true;

    public boolean compressed = true;

    Scale scale;

    public Player(ScreenI screen, Scale scale){
        this.scale = scale;
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

    MediaPlayer musicPlayer;
    Media media = new Media(getClass().getResource("/elevator.mp3").toExternalForm());

    @Override
    public void tick(){

        double tSpeed = Math.PI * 2.0 / 180.0 * 2.0;
        double speed = Math.PI * 2.0 / 180.0 * 4.0;
        double eSpeed = 1.0 / 40.0;

        speed *= (1.2 - elevator / 2.0);

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
        }

        if(screen.isPressed("Up")) {
            elevator = Math.min(elevator + eSpeed, 1);

            if(musicPlayer == null) {
                musicPlayer = new MediaPlayer(media);
                musicPlayer.play();
            }
        }
        else if (screen.isPressed("Down")) {
            elevator = Math.max(elevator - eSpeed, 0.05);

            if(musicPlayer == null) {
                musicPlayer = new MediaPlayer(media);
                musicPlayer.play();
            }
        }
        else {
            if(musicPlayer != null) {
                musicPlayer.stop();
                musicPlayer = null;
            }
        }

        for(int i = 10; i < 14; i++)
            points[1][i] = elevator * 2.5;
        points[1][14] = elevator * 1 + 1.5;
        points[1][15] = elevator * 1 + 1.5;

        screen.getCubes().forEach(cube -> {

            if(!hasCube && !cube.falling && Math.abs(elevator * 2.5 - .125 - (cube.vertical+.125)) <= .05) {
                double cX = x + Math.cos(-angle) * (0.1 + .125);
                double cY = y + Math.sin(-angle) * (0.1 + .125);
                if (Math.sqrt(Math.pow(cube.x - cX, 2) + Math.pow(cube.y - cY, 2)) <= 0.25) {
                    hasCube = true;
                    compressed = true;
                    scale.arm.left.remove(cube);
                    scale.arm.right.remove(cube);
                    cube.roll = 0;
                    scale.arm.position = 0;

                    powercube = cube;
                }
            }

            if(cube.falling && Math.abs(cube.vertical - (1.8-.125)) <= 0.2){
                double dx = cube.x - scale.x;
                double dy = cube.y - scale.y;

                double mag = Math.sqrt(dx * dx + dy * dy);
                double angle = 0;

                if(dy != 0)
                    angle = Math.toDegrees(Math.atan(dy / dx)) + (dx > 0 ? 180 : 0);
                else if(dx != 0)
                    angle = dx > 0 ? 0 : 180;

                angle = Math.toRadians(angle + 10);

                double xp = Math.cos(angle) * mag;
                double yp = Math.sin(angle) * mag;

                double yM = 0.5;
                double xM = 0.8;

                if(yp <= yM && yp >= -yM) {
                    boolean left = xp >= 0 && xp <= xM;
                    boolean right = xp <= 0 && xp >= -xM;
                    if (left || right) {
                        cube.falling = false;
                        cube.vertical = 2 - 0.125;
                        scale.arm.position = left ? 1 : 2;
                        (left ? scale.arm.left : scale.arm.right).add(cube);
                    }
                }
            }

        });

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
