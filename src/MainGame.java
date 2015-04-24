import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

public class MainGame extends BasicGame
{
    //isendiväljad
    private Image field;
    //private Music music;

    float playerSize = 35;
    private Player player1;
    //private Player player2;
    private Player ball;

    private Shape upperBorder;
    private Shape bottomBorder;
    private Shape leftBorder;
    private Shape rightBorder;
    private Shape middleBorder;
    static int displayX = 1000;
    static int displayY = 500;

    float speed = 0.6f;
    private float player1x = 250;
    private float player1y = 250;
    private float ballX = 400;
    private float ballY = 250;

    GamePhysics gamePhysics;

    public MainGame(String gamename)
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        field = new Image("data/field.png");
        //MUUSIKA
        /*music = new Music("data/gamemusic.ogg"); //Frostbite(Original mix) by WarHector
        music.loop();*/
        player1 = new Player(player1x,player1y,playerSize, 11);//x,y,raadius, (inverted mass)
        ball = new Player(ballX, ballY, 20, 10);
        ball.setDx(0);
        ball.setDy(0);
        //Borders
        upperBorder = new Line(0,0,displayX,0);

        leftBorder = new Line(0,0,0,displayY);
        bottomBorder = new Line(0,displayY, displayX,displayY);
        rightBorder = new Line(displayX, 0, displayX, displayY);
        middleBorder = new Line(displayX/2, 0, displayX/2, displayY);

        gamePhysics = new GamePhysics();
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Input input = gc.getInput();

        //ball movement


        //control player1
        float x1 = 0;
        float y1 = 0;

        //up
        if(input.isKeyDown(Input.KEY_W) && !(player1.circle.intersects(upperBorder))){
            y1 -= 1;
        }
        //down
        if(input.isKeyDown(Input.KEY_S) && !(player1.circle.intersects(bottomBorder))){
            y1 += 1;
        }
        //left
        if(input.isKeyDown(Input.KEY_A) && !(player1.circle.intersects(leftBorder))){
            x1 -= 1;
        }
        //right
        if(input.isKeyDown(Input.KEY_D) && !(player1.circle.intersects(middleBorder))){
            x1 += 1;
        }
        //playermovement
        double length = Math.sqrt(x1*x1+y1*y1);
        if (length != 0){
            x1 /= length;
            y1 /= length;
            x1 *= delta*speed;
            y1 *= delta*speed;
            player1x += x1;
            player1y += y1;
            player1.circle.setX(player1x);
            player1.circle.setY(player1y);
        }




    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //borders
        g.setColor(Color.transparent);
        g.draw(upperBorder);
        g.draw(leftBorder);
        g.draw(rightBorder);
        g.draw(bottomBorder);
        g.draw(middleBorder);

        //gc.setShowFPS(false);
        field.draw(0,0); //siin hoitakse mänguväli ees

        //players
        g.setColor(Color.red);
        g.fill(player1.circle);

        //ball
        g.setColor(Color.magenta);
        g.fill(ball.circle);
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Jalka"));
            appgc.setTargetFrameRate(120);
            //appgc.setVSync(true);
            appgc.setDisplayMode(displayX, displayY, false);
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}