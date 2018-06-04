import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Screen2 extends Application implements EventHandler<KeyEvent>, ScreenI {

    private GraphicsContext context;
    private static int width = 1000;
    private static int height = 1000;

    public static Screen2 instance;
    public static List<Line> lines;

//    private double[][] points = {
//            {1.000, .500, -.500, -1.000, -.500, .500, .840, .315, -.210, -.360, -.210, .315},
//            {-.800, -.800, -.800, -.800, -.800, -.800, -.400, .125, .650, .800, .650, .125},
//            {.000, -.866, -.866, .000, .866, .866, .000, -.546, -.364, .000, .364, .546}};


    List<Objekt> objekts = new ArrayList<>();

    Player player;

    public void set(){
        ScaleArm arm = new ScaleArm(this);
        Scale scale = new Scale(this, arm);
        objekts.add(scale);
        objekts.add(arm);

        Powercube cube = new Powercube(this, 0, 0);
        player = new Player(this, scale);
        player.powercube = cube;
        cubes.add(cube);

        objekts.add(player);
        objekts.add(cube);

        for(int i = 0; i < 10; i++){
            Powercube c = new Powercube(this, Math.random() * 10 - 5, Math.random() * 10 - 5);
            c.angle = Math.random() * Math.PI * 2;
            cubes.add(c);
            objekts.add(c);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // get screen size wrapper
        width = (int)dim.getWidth();
        height = (int)dim.getHeight();

        instance = this;

        Canvas canvas = new Canvas(width, height);                  // object representing onscreen canvas
        context = canvas.getGraphicsContext2D();                    // object that handles canvas manipulation

        Scene scene = new Scene(new Group(canvas));                 // create a scene with the canvas as the only node
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Screen");

        stage.show();

        scene.setOnKeyPressed(this);                                // Register oneself as the key press listener
        scene.setOnKeyReleased(this);

        set();

        new AnimationTimer(){

            @Override
            public void handle(long arg) {

                lines = new ArrayList<>();

                context.setFill(Color.BLACK);
                context.fillRect(0, 0, width, height);

                List<Line> lines = new ArrayList<>();

                objekts.forEach(o -> {

                    o.tick();

                    double[][] rot = Util.mult(Util.rotationMatrix(0, o.angle, o.roll), o.points);

                    Main.getLines(Util.mult(Util.rotationMatrix(Math.toRadians(30/*Math.abs(Math.sin(System.currentTimeMillis() / 1000.0) * 30)*/), 0, 0),
                            Util.translate(rot, o.x, o.vertical, o.y)), o.lineIndices).forEach(l -> {
                        l.color = o.color;
                        lines.add(l);
                    });

                });

                Util.orderByDepth(lines).forEach(l -> {

                    context.setFill(l.color);

                    double p1 = width / 2.0 + l.p1.x;
                    double p1y = height / 2.0 - l.p1.y;

                    double p2 = width / 2.0 + l.p2.x;
                    double p2y = height / 2.0 - l.p2.y;

                    double angle = Util.getAngle(p2 - p1, p2y - p1y) - 90;

                    context.save();
                    context.rotate(angle);

                    double[] p1r = Util.rotate(p1, p1y, angle);

                    double mag = Util.dist(p1, p1y, p2, p2y);

                    context.fillRect(p1r[0], p1r[1] - 2, mag, 4);

                    context.restore();

                });

                context.setFill(Color.WHITESMOKE);
                context.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
                context.fillText("Pitch: 30 degrees", 100, 100);
                context.fillText("Yaw: " + (int)Math.toDegrees(player.angle) % 360 + " degrees", 100, 120);
                context.fillText("Roll: 0 degrees", 100, 140);

            }

        }.start();

    }

    /**
     * Listens for escape key press and exits
     */
    @Override
    public void handle(KeyEvent event) {

        if(event.getCode().getName().equals("Esc"))
            System.exit(0);

        if(event.getEventType() == KeyEvent.KEY_PRESSED)
            input.add(event.getCode().toString().toLowerCase());
        else if(event.getEventType() == KeyEvent.KEY_RELEASED)
            input.remove(event.getCode().toString().toLowerCase());

        if(event.getCode().getName().equals("R") && System.currentTimeMillis() - l >= 250){
            objekts.clear();
            set();
            l = System.currentTimeMillis();
        }

    }

    long l = 0;
}
