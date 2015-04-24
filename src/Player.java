import org.newdawn.slick.geom.Circle;

public class Player {
    private float x;
    private float y;
    private float r;
    Circle circle;
    private float dx; //kiirus
    private float dy;

    Player(float x, float y, float r){
        this.circle = new Circle(x,y,r);
    }




}
