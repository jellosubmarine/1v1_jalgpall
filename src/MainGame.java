import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class MainGame extends BasicGame
{
    //isendiväljad
    private Image field;
    //private Music music;
    private Shape player1;
    float speed = 0.5f;
    float x = 250;
    float y = 250;


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
        player1 = new Circle(x,y,25);//x,y,raadius
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Input input = gc.getInput();
        //control player1
        //up
        if(input.isKeyDown(Input.KEY_UP)){
            player1.setY(y - speed * delta);
            y = y - speed * delta;//delta makes the speed run the same on ANY computer
        }
        //down
        if(input.isKeyDown(Input.KEY_DOWN)){
            player1.setY(y + speed * delta);
            y = y + speed * delta;
        }
        //left
        if(input.isKeyDown(Input.KEY_LEFT)){
            player1.setX(x - speed * delta);
            x = x - speed * delta;
        }
        //right
        if(input.isKeyDown(Input.KEY_RIGHT)){
            player1.setX(x + speed * delta);
            x = x + speed * delta;
        }

    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //gc.setShowFPS(false);
        field.draw(0,0); //siin hoitakse mänguväli ees
        g.setColor(Color.red);
        g.fill(player1);
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Jalka"));
            appgc.setTargetFrameRate(120);
            //appgc.setVSync(true);
            appgc.setDisplayMode(1000, 500, false);
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}