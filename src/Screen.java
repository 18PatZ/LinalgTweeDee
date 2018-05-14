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
import javafx.stage.Stage;
import java.awt.*;
import java.util.List;

public class Screen extends Application implements EventHandler<KeyEvent> {

    private GraphicsContext context;
    private static int width = 1000;
    private static int height = 1000;

    public static Screen instance;
    public static List<Line> lines;

    @Override
    public void start(Stage stage) throws Exception {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // get screen size wrapper
        width = (int)dim.getWidth();
        height = (int)dim.getHeight();

        instance = this;

        javafx.scene.canvas.Canvas canvas = new Canvas(width, height);                  // object representing onscreen canvas
        context = canvas.getGraphicsContext2D();                    // object that handles canvas manipulation

        Scene scene = new Scene(new Group(canvas));                 // create a scene with the canvas as the only node
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Screen");

        stage.show();

        scene.setOnKeyPressed(this);                                // Register oneself as the key press listener

//        double minX = Double.MAX_VALUE;
//        double maxX = Double.MIN_VALUE;
//        double minY = Double.MAX_VALUE;
//        double maxY = Double.MIN_VALUE;

//        if(points != null){
//            for (Star star : stars.get(0)) {
//                minX = Math.min(minX, star.getPosition().getX());
//                maxX = Math.max(maxX, star.getPosition().getX());
//
//                minY = Math.min(minY, star.getPosition().getY());
//                maxY = Math.max(maxY, star.getPosition().getY());
//            }
//        }

//        double scaleX = (maxX - minX) / width;
//        double scaleY = (maxY - minY) / height;
//
//        double xOff = - (maxX + minX) / 2;
//        double yOff = 0;
//        double rScale = 1;
//
        new AnimationTimer(){

            double theta = 0;

            @Override
            public void handle(long arg) {

                context.clearRect(0, 0, width, height);

                context.setFill(Color.BLACK);
                context.fillRect(0, 0, width, height);

                context.setLineWidth(3);
                context.setStroke(Color.YELLOW);

                lines = Main.getLines(Util.mult(Util.rotationMatrix(theta, 0, 0), Main.points));

                if(lines != null){
                    lines.forEach(l -> {
                        context.moveTo(width / 2.0 + l.p1[0], height / 2.0 - l.p1[1]);
                        context.lineTo(width / 2.0 + l.p2[0], height / 2.0 - l.p2[1]);
                        context.stroke();
                    });

                    System.out.println(lines.size());
                }

                context.setFill(Color.WHITESMOKE);
                context.setFont(Font.font("Verdana", 20));
                context.fillText("Angle: " + (int)Math.toDegrees(theta) + " degrees", 100, 100);

                theta += Math.PI * 2.0 / 120.0;

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

    }


}
