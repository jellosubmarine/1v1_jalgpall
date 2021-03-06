import java.awt.*;
import java.awt.Font;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.text.Text;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

import javax.swing.*;

public class MainGame extends BasicGame
{
    //isendiväljad
    private Image field;
    private Music music;

    float playerSize = 35;
    private Player player1;
    private Player player2;
    private Player ball;

    private Shape upperBorder;
    private Shape bottomBorder;
    private Shape leftBorder;
    private Shape rightBorder;
    private Shape middleBorder;

    private Shape Goal1;
    private Shape Goal2;
    private Shape centerLine;
    private Shape fieldDraw;

    static int displayX = 1000;
    static int displayY = 500;

    float speed = 0.6f;
    float ballspeed = 0.8f; //palli liikumiskiirus
    private float player1x = 250;
    private float player1y = 250;
    private float player2x = 750;
    private float player2y = 250;
    private float ballX = 500;
    private float ballY = 250;
    private float ballSize = 20;
    int timeSinceLastVerticalCollision = 0;
    int timeSinceLastHorizontalCollision = 0;
    int timeSinceLastPlayer1Collision = 0;
    int timeSinceLastPlayer2Collision = 0;
    GamePhysics gamePhysics;

    int player1Score = 0;
    int player2Score = 0;

    TrueTypeFont font;

    int sekundilugeja=3;
    long kulunudaeg = 0;
    long kulunudaeg1 = 0;

    static int maxskoor=2;

    boolean kirjutatud = false;


    public MainGame(String gamename)
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        //MUUSIKA
        music = new Music("data/gamemusic.ogg"); //Frostbite(Original mix) by WarHector
        music.loop(); //testimiseks välja kommitud
        player1 = new Player(player1x,player1y,playerSize);//x,y,raadius
        player2 = new Player(player2x, player2y, playerSize);
        ball = new Player(ballX, ballY, 20);
        ball.direction = new Vector2f(0,1);

        //kunstlikult lisatud failist lugemine
        try {
            BufferedReader sisse = new BufferedReader( new InputStreamReader(new FileInputStream("data/jalkanimed.txt")));
            player1.setNimi(sisse.readLine());
            player2.setNimi(sisse.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Borders
        upperBorder = new Line(0,0,displayX,0);

        leftBorder = new Line(0,0,0,displayY);
        bottomBorder = new Line(0,displayY, displayX,displayY);
        rightBorder = new Line(displayX, 0, displayX, displayY);
        middleBorder = new Line(displayX/2, 0, displayX/2, displayY);
        //shitty physics
        gamePhysics = new GamePhysics();

        //field
        Goal1 = new Rectangle(0, 200, 20, 100);
        Goal2 = new Rectangle(displayX - 20, 200, 20, 100);
        centerLine = new Rectangle(displayX/2-5, 0, 10, displayY);
        fieldDraw = new Rectangle(0,0,displayX, displayY);

        Font awtFont = new Font("Arial", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, false);

        resetPositions();

    }
    public void resetPositions(){
        ballX = 500-ballSize;
        ballY = 250-ballSize;
        player1x = 250-playerSize;
        player1y = 250-playerSize;
        player2x = 750-playerSize;
        player2y = 250-playerSize;
        player1.circle.setX(player1x);
        player1.circle.setY(player1y);
        player2.circle.setX(player2x);
        player2.circle.setY(player2y);
    }
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {

        if (sekundilugeja>-1) {
            if (kulunudaeg<(4-sekundilugeja)*1000){
                kulunudaeg+=delta;
            }
            else {
                sekundilugeja--;
            }
        }

        if (sekundilugeja<0 && player1Score!=maxskoor && player2Score!=maxskoor) {
            if (timeSinceLastHorizontalCollision != 0){
                timeSinceLastHorizontalCollision--;
            }
            if(timeSinceLastVerticalCollision != 0){
                timeSinceLastVerticalCollision--;
            }
            if(timeSinceLastPlayer1Collision != 0){
                timeSinceLastPlayer1Collision--;
            }
            if(timeSinceLastPlayer2Collision != 0){
                timeSinceLastPlayer2Collision--;
            }

            Input input = gc.getInput();
            float x3 = ball.direction.getX();
            float y3 = ball.direction.getY();
            //ball movement
            if(ball.circle.intersects(Goal1)){
                player2Score++;
                ball.direction = new Vector2f(0,1);
                resetPositions();
            }
            else if(ball.circle.intersects((Goal2))){
                player1Score++;
                ball.direction = new Vector2f(0,1);
                resetPositions();
            }

            if(timeSinceLastPlayer1Collision == 0) {
                if (gamePhysics.collisionDetection(player1, ball)) {
                    gamePhysics.collisionDirection(player1, ball);
                    timeSinceLastPlayer1Collision = 15;
                }
            }
            if(timeSinceLastPlayer2Collision == 0) {
                if (gamePhysics.collisionDetection(player2, ball)) {
                    gamePhysics.collisionDirection(player2, ball);
                    timeSinceLastPlayer2Collision = 15;
                }
            }
            if(timeSinceLastVerticalCollision == 0) {
                if (gamePhysics.collisionDetectionWithBorder(ball, "upper") || gamePhysics.collisionDetectionWithBorder(ball, "bottom")) {
                    gamePhysics.collisionDirectionWithWall(ball, 1, -1);
                    timeSinceLastVerticalCollision = 20;
                }
            }
            if(timeSinceLastHorizontalCollision == 0) {
                if (gamePhysics.collisionDetectionWithBorder(ball, "right") || gamePhysics.collisionDetectionWithBorder(ball, "left")) {
                    gamePhysics.collisionDirectionWithWall(ball, -1, 1);
                    timeSinceLastHorizontalCollision = 20;
                }
            }
            double length3 = ball.direction.length();
            if (length3 != 0) {
                x3 /= length3;
                y3 /= length3;
                x3 *= delta * ballspeed;
                y3 *= delta * ballspeed;
                ballX += x3;
                ballY += y3;
                ball.circle.setX(ballX);
                ball.circle.setY(ballY);
            }
            //control player1
            float x1 = 0;
            float y1 = 0;

            /*KeyboardControls nupud = new KeyboardControls();

            int number = nupud.loe();
            StringBuilder sõna = new StringBuilder("KEY_");
            sõna.append("W");
            String minusõna = sõna.toString();

            System.out.println(Input.minusõna);

            int kolm = Input.KEY_W;*/
            //up
            if(input.isKeyDown(17) && !(player1.circle.intersects(upperBorder))){
                y1 -= 1;
            }
            //down
            if(input.isKeyDown(Input.KEY_S) && !player1.circle.intersects(bottomBorder)){
                y1 += 1;
            }
            //left
            if(input.isKeyDown(Input.KEY_A) && !player1.circle.intersects(leftBorder)){
                x1 -= 1;
            }
            //right
            if(input.isKeyDown(Input.KEY_D) && !(player1.circle.intersects(middleBorder))){
                x1 += 1;
            }

            //control player2
            float x2 = 0;
            float y2 = 0;

            //up
            if(input.isKeyDown(Input.KEY_UP) && !(player2.circle.intersects(upperBorder))){
                y2 -= 1;
            }
            //down
            if(input.isKeyDown(Input.KEY_DOWN) && !(player2.circle.intersects(bottomBorder))){
                y2 += 1;
            }
            //left
            if(input.isKeyDown(Input.KEY_LEFT) && !(player2.circle.intersects(middleBorder))){
                x2 -= 1;
            }
            //right
            if(input.isKeyDown(Input.KEY_RIGHT) && !(player2.circle.intersects(rightBorder))){
                x2 += 1;
            }


            //playermovement
            double length1 = Math.sqrt(x1*x1+y1*y1);
            if (length1 != 0){
                x1 /= length1;
                y1 /= length1;
                x1 *= delta*speed;
                y1 *= delta*speed;
                player1x += x1;
                player1y += y1;
                player1.circle.setX(player1x);
                player1.circle.setY(player1y);
            }
            double length2 = Math.sqrt(x2*x2+y2*y2);
            if (length2 != 0){
                x2 /= length2;
                y2 /= length2;
                x2 *= delta*speed;
                y2 *= delta*speed;
                player2x += x2;
                player2y += y2;
                player2.circle.setX(player2x);
                player2.circle.setY(player2y);
            }
        }
        else if (player1Score==maxskoor || player2Score==maxskoor) {
            if (kulunudaeg1<2000){
                kulunudaeg1+=delta;
            }
            else {
                kirjutatud=true;
            }
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
        //field
        g.setColor(Color.green);
        g.fill(fieldDraw);
        g.setColor(Color.white);
        g.fill(centerLine);
        //goals

        g.fill(Goal1);
        g.fill(Goal2);

        //players
        g.setColor(Color.red);
        g.fill(player1.circle);
        g.setColor(Color.cyan);
        g.fill(player2.circle);
        //ball
        g.setColor(Color.darkGray);
        g.fill(ball.circle);

        //skoor
        font.drawString(470,0,String.valueOf(player1Score)+"  :  "+String.valueOf(player2Score),Color.black);




        if (sekundilugeja>-1) {
            font.drawString(470,250,String.valueOf(sekundilugeja));
        }

        if (player1Score==maxskoor || player2Score==maxskoor){
            if (player1Score==maxskoor) {
                font.drawString(470,250,"Võitis " + player1.nimi);
            } else {
                font.drawString(470,250,"Võitis " + player2.nimi);
            }
            if (kirjutatud) {
                try {
                    FileWriter kirjutaja = new FileWriter(new File("data/skoorid.txt"), true);
                    kirjutaja.write(player1Score + ":" + player2Score );
                    kirjutaja.write("\r\n");
                    kirjutaja.close();
                    kirjutatud = false;
                    gc.exit();
                } catch (IOException e) {
                    System.out.println("Failiga jama");
                }
            }
        }
    }

    public static void main(String[] args){
        try
        {
            String input = JOptionPane.showInputDialog("Mitmeni mäng käib?");
            maxskoor = Integer.parseInt(input);
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Airhockey v0.01 Alpha"));
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