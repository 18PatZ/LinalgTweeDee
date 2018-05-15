public class Player {

    double angle;

    double x = 0;
    double y = 0;

    public void tick(int frame){

        double speed = Math.PI * 2.0 / 180.0 / 5.0;

        if(Screen.isPressed("A"))
            angle -= speed;
        else if(Screen.isPressed("D"))
            angle += speed;

        if(Screen.isPressed("W")){
            x += Math.sin(angle) * speed;
            y += Math.cos(angle) * speed;
        }
        else if(Screen.isPressed("S")) {
            x -= Math.sin(angle) * speed;
            y -= Math.cos(angle) * speed;
        }

    }

}
