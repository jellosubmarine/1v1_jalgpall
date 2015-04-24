import org.newdawn.slick.geom.Circle;

public class Player {
    private float x;
    private float y;
    private float r;
    Circle circle;
    private float dx; //kiirus
    private float dy;
    private float inverseMass;

    Player(float x, float y, float r){
        this.circle = new Circle(x,y,r);
    }
    Player(float x, float y, float r, float inverseMass){
        this.circle = new Circle(x,y,r);
        this.inverseMass = inverseMass;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }
    public void translateDxDy(float dx,float dy){
        this.dx += dx;
        this.dy += dy;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public float getInverseMass() {
        return inverseMass;
    }
}
