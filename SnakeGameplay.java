import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGameplay extends JPanel implements ActionListener, KeyListener {



    private class Tile{
        int x;
        int y;
        Tile(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardwidth ;
    int boardheight;
    int tilesize = 25;

    Tile snakeHead;
    Tile food;

    Timer gameLoop;

    int velocityX ;
    int velocityY ;
     boolean gameOver =  false;
    Random rd ;

    ArrayList<Tile> snakeBody;
    SnakeGameplay(int width,int height){
        boardwidth = width;
        boardheight = height;
        setPreferredSize(new Dimension(boardwidth,boardheight));
        setBackground(Color.black);

        addKeyListener(this);
        setFocusable(true);

        snakeBody = new ArrayList<>();

        snakeHead = new Tile(5,5);
        rd=new Random();
        food = new Tile(10,10);


        placefood();

        gameLoop = new Timer(100,this);
        gameLoop.start();

        velocityX = 1;
        velocityY = 0;
    }

    private void placefood() {
        food.x = rd.nextInt(boardwidth/tilesize - 1);
        food.y = rd.nextInt(boardheight/tilesize - 1);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // Drawing a Line of grid to understand the placement
//        for (int i=0;i<boardwidth/tilesize;i++){
//            g.drawLine(i*tilesize,0,i*tilesize,boardwidth);
//            g.drawLine(0,i*tilesize,boardwidth,i*tilesize);
//        }
        //Head
        g.setColor(Color.green);
//        g.fillRect(snakeHead.x*tilesize,snakeHead.y*tilesize,tilesize,tilesize);
        g.fill3DRect(snakeHead.x*tilesize,snakeHead.y*tilesize,tilesize,tilesize,true);

        //Snake Body
        g.setColor(Color.BLUE);
        for (Tile t : snakeBody){
//            g.fillRect(t.x*tilesize,t.y*tilesize,tilesize,tilesize);
            g.fill3DRect(t.x*tilesize,t.y*tilesize,tilesize,tilesize,true);
        }

        //FOOD
        g.setColor(Color.red);
//        g.fillRect(food.x*tilesize,food.y*tilesize,tilesize,tilesize);
        g.fill3DRect(food.x*tilesize,food.y*tilesize,tilesize,tilesize,true);

        //Score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over :" + String.valueOf(snakeBody.size()) , tilesize - 16,tilesize);
        }
        else{
            g.setColor(Color.green);
            g.drawString("Score :" + String.valueOf(snakeBody.size()) , tilesize - 16,tilesize);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        move();
      repaint();
      if(gameOver) {
          gameLoop.stop();
      }

    }

    private void move() {
        if(snakeHead.x == food.x && snakeHead.y == food.y){
            snakeBody.add(new Tile(food.x,food.y));
            placefood();
        }

        for(int i=snakeBody.size() - 1;i>=0;i--){
            Tile snakepart = snakeBody.get(i);
            if(i==0){
                snakepart.x = snakeHead.x;
                snakepart.y = snakeHead.y;
            }
            else{
                Tile snakeprevBody = snakeBody.get(i-1);
                snakepart.x = snakeprevBody.x;
                snakepart.y = snakeprevBody.y;
            }
        }
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        for(int i=0;i<snakeBody.size();i++){
            Tile snakepart = snakeBody.get(i);
            if(snakepart.x == snakeHead.x && snakepart.y == snakeHead.y) gameOver = true;
        }

        if(snakeHead.x*tilesize < 0 || snakeHead.x*tilesize > boardwidth || snakeHead.y*tilesize > boardheight || snakeHead.y*tilesize < 0){
            gameOver = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       int keycode = e.getKeyCode();
       switch (keycode){
           case KeyEvent.VK_UP :
               if(velocityY == 1) break;
               velocityX = 0;
               velocityY = -1;
               break;
           case KeyEvent.VK_DOWN:
               if(velocityY == -1) break;
               velocityX = 0;
               velocityY = 1;
               break;
           case  KeyEvent.VK_LEFT:
               if(velocityX == 1) break;
               velocityX = -1;
               velocityY = 0;
               break;
           case KeyEvent.VK_RIGHT:
               if(velocityX == -1) break;
               velocityX = 1;
               velocityY = 0;
               break;
           case KeyEvent.VK_ENTER:
               if(gameOver){
                    restart();
                   break;
               }
               break;
           default:
               velocityY = 0;
               velocityX = 0;

       }
    }

    private void restart() {
        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<>();
        placefood();
        gameOver = false;
        gameLoop.restart();
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
