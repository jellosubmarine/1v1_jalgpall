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

    }

}
