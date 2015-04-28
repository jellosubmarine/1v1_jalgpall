import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class GamePhysics {
    public GamePhysics() {
    }
    //USELESSSSSS
    /*public void elasticCollision(Player player, Player ball){
        Vector2f relativeVelocity = new Vector2f(ball.getDx()-player.getDx(), ball.getDy()-player.getDy());
        Vector2f normal = relativeVelocity.getNormal();
        float velAlongNormal = relativeVelocity.dot(normal);
        System.out.println(velAlongNormal);
        if (velAlongNormal>0){
            return;
        }
        //impulse scalar
        float j = -1*velAlongNormal;
        j /= player.getInverseMass() + ball.getInverseMass();
        Vector2f impulse = normal.scale(j);
        Vector2f playerVelocity = impulse.scale(player.getInverseMass());
        Vector2f ballVelocity = impulse.scale(ball.getInverseMass());

        player.translateDxDy(-playerVelocity.getX(), -playerVelocity.getY());
        ball.translateDxDy(ballVelocity.getX(), ballVelocity.getY());
    }
    */


    public void collisionDirection(Player player, Player ball){
        Vector2f newDirection = new Vector2f(ball.circle.getCenterX()-player.circle.getCenterX(), ball.circle.getCenterY()-player.circle.getCenterY());
        ball.direction  = newDirection.normalise();
    }
    public void collisionDirectionWithWall(Player ball, int x, int y){
        ball.direction = new Vector2f(ball.direction.getX()*x, ball.direction.getY()*y);
    }

    public boolean collisionDetection(Player player, Player ball){
        float x = player.circle.getCenterX()-ball.circle.getCenterX();
        float y = player.circle.getCenterY()-ball.circle.getCenterY();
        double distance = Math.sqrt(x*x+y*y);
        double minimalDistance = player.circle.getBoundingCircleRadius()+ball.circle.getBoundingCircleRadius();
        if(distance <= minimalDistance){
            return true;
        }
        else{
            return false;
        }
    }
    //vajab refactorimist, suht halvasti kirjutatud tuleb see
    public boolean collisionDetectionWithBorder(Player ball, String border){
        float radius = ball.circle.getBoundingCircleRadius();
        float x = ball.circle.getCenterX();
        float y = ball.circle.getCenterY();
        if(border.equals("upper")){
            if((y-radius <= 0)){
                return true;
            }
            else return false;
        }
        else if(border.equals("bottom")){
            if((y+radius >= 500)){
                return true;
            }
            else return false;
        }
        if(border.equals("left")){
            if((x-radius <= 0)){
                return true;
            }
            else return false;
        }
        if(border.equals("right")){
            if((x+radius >= 1000)){
                return true;
            }
            else return false;
        }
        else return false;
    }

}
