import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Player {
    private float x;
    private float y;
    private float r;
    Circle circle;
    //suunavektor
    Vector2f direction = new Vector2f();
    private float inverseMass;

    Player(float x, float y, float r){
        this.circle = new Circle(x,y,r);
    }
    Player(float x, float y, float r, float inverseMass){
        this.circle = new Circle(x,y,r);
        this.inverseMass = inverseMass;
    }

    public float getInverseMass() {
        return inverseMass;
    }

}
