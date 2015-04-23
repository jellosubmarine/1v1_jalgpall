import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;

public class MainGame extends BasicGame
{
    private Image field;

    public MainGame(String gamename)
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        field = new Image("data/field.png");
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {}

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        field.draw(0,0);
        //g.drawString("TESTINTESTINTESTIN", 300, 200);
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Jalka"));
            appgc.setDisplayMode(1000, 500, false);
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}