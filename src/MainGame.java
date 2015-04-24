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
    private Shape upperBorder;
    private Shape bottomBorder;
    private Shape leftBorder;
    private Shape rightBorder;
    private Shape middleBorder;
    static int displayX = 1000;
    static int displayY = 500;

    float speed = 0.6f;
    private float x = 250;
    private float y = 250;


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
        player1 = new Player(x,y,playerSize);//x,y,raadius

        //Borders
        upperBorder = new Line(0,0,displayX,0);

        leftBorder = new Line(0,0,0,displayY);
        bottomBorder = new Line(0,displayY, displayX,displayY);
        rightBorder = new Line(displayX, 0, displayX, displayY);
        middleBorder = new Line(displayX/2, 0, displayX/2, displayY);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        //see on suht kole, võiks midagi paremat välja mõelda

        Input input = gc.getInput();
        //control player1
        //up
        if(input.isKeyDown(Input.KEY_W) && !(player1.circle.intersects(upperBorder))){
            player1.circle.setY(y - speed * delta);
            y = y - speed * delta;//delta makes the speed run the same on ANY computer
        }
        //down
        if(input.isKeyDown(Input.KEY_S) && !(player1.circle.intersects(bottomBorder))){
            player1.circle.setY(y + speed * delta);
            y = y + speed * delta;
        }
        //left
        if(input.isKeyDown(Input.KEY_A) && !(player1.circle.intersects(leftBorder))){
            player1.circle.setX(x - speed * delta);
            x = x - speed * delta;
        }
        //right
        if(input.isKeyDown(Input.KEY_D) && !(player1.circle.intersects(middleBorder))){
            player1.circle.setX(x + speed * delta);
            x = x + speed * delta;
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

        gc.setShowFPS(false);
        field.draw(0,0); //siin hoitakse mänguväli ees

        //players
        g.setColor(Color.red);
        g.fill(player1.circle);

        //ball

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