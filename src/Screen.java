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
import java.util.List;

public class Screen extends Application implements EventHandler<KeyEvent>, ScreenI {

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
        scene.setOnKeyReleased(this);

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

        Player player = new Player(this, null);

        new AnimationTimer(){

            double thetaX = 0;
            double thetaY = 0;
            double thetaZ = 0;

            @Override
            public void handle(long arg) {

                context.setFill(Color.BLACK);
                context.fillRect(0, 0, width, height);

                context.setFill(Color.YELLOW);

                player.tick();

//                double ang = Math.toRadians(Util.getAngle(player.x, player.y));
                thetaY = player.angle;

//                thetaY = Math.toRadians(-45);

//                lines = Main.getLines(Util.mult(new double[][]{{1, 0, 0}, {0, Math.abs(Math.sin(player.angle)) / 2.0 + 1, 0}, {0, 0, 1}}, Util.mult(Util.rotationMatrix(thetaX, thetaY, thetaZ),
//                        Util.translate(Main.points, 0, 0, player.y))));
//
//                double[][] grid = new double[3][40];
//                for(int i = -10; i < 9; i++){
//                    for(int j = 0; j < 2; j++) {
//                        grid[0][i * 2 + j + 20] = i;
//                        grid[1][i * 2 + j + 20] = 3 * (j == 0 ? 1 : -1);
//                        grid[2][i * 2 + j + 20] = 0;
//                    }
//                }
//
//                grid = Main.getDisplayed(Util.mult(new double[][]{{1, 0, 0}, {0, Math.abs(Math.sin(player.angle)) + 1, 0}, {0, 0, 1}}, Util.mult(Util.rotationMatrix(thetaX, thetaY, thetaZ),
//                        Util.translate(grid, 0, 0, player.y))));
//                for (int i = 0; i < grid.length; i += 2)
//                    lines.add(new Line(grid[i], grid[i + 1]));

//                lines.addAll(Main.getGridLines(Util.mult(new double[][]{{1, 0, 0}, {0, Math.abs(Math.sin(player.angle)) + 1, 0}, {0, 0, 1}}, Util.mult(Util.rotationMatrix(thetaX, thetaY, thetaZ),
//                        Util.translate(Main.gridPoints, 0, 0, player.y)))));

                double dX = 0;
//                double dX = player.x + Math.sin(player.angle) * (width / 2.0 + 100);
//                double dY = player.y + Math.sin(ang) * 100;

                lines = Main.getLines(Util.mult(Util.rotationMatrix(thetaX, thetaY, thetaZ),
                        Main.points), Main.lineIndices);

                if(lines != null){// && Math.cos(player.angle) >= 0){
                    lines.forEach(l -> {

                        double p1 = width / 2.0 + l.p1[0];
                        double p1y = height / 2.0 - l.p1[1];

                        double p2 = width / 2.0 + l.p2[0];
                        double p2y = height / 2.0 - l.p2[1];

                        double angle = Util.getAngle(p2 - p1, p2y - p1y) - 90;

                        context.save();
                        context.rotate(angle);

                        double[] p1r = Util.rotate(p1 - dX, p1y, angle);

                        double mag = Util.dist(p1, p1y, p2, p2y);

                        context.fillRect(p1r[0], p1r[1] - 2, mag, 4);

                        context.restore();

                    });
                }

                context.setFill(Color.WHITESMOKE);
                context.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
                context.fillText("Pitch: " + (int)Math.toDegrees(thetaX) % 360 + " degrees", 100, 100);
                context.fillText("Yaw: " + (int)Math.toDegrees(thetaY) % 360 + " degrees", 100, 120);
                context.fillText("Roll: " + (int)Math.toDegrees(thetaZ) % 360 + " degrees", 100, 140);

                double speed = Math.PI * 2.0 / 180.0;

                if(isPressed("W"))
                    thetaX -= speed;
                else if(isPressed("S"))
                    thetaX += speed;

                if(isPressed("A"))
                    thetaY -= speed;
                else if(isPressed("D"))
                    thetaY += speed;

                if(isPressed("Q"))
                    thetaZ -= speed;
                else if(isPressed("E"))
                    thetaZ += speed;


//                theta = (theta + Math.PI * 2.0 / 180.0) % (Math.PI * 2);


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

    }
}
