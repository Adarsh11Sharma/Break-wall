package suchit_brickBracker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
  private boolean play = false;
  private int score=0;
  private  int totalBricks=21;
  private Timer timer;
  private int delay=8;
  private int playerx=310;
  private int ballposX=120;
  private int ballposy=350;
  private int ballXdir=-1;
  private int ballYdir=-2;
private MapGenerator map;

  public GamePlay(){
 map = new MapGenerator(3,7);
      addKeyListener(this);
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      timer = new Timer(delay,this);
      timer.start();
  }
  public void paint ( Graphics g){
      //background
      g.setColor(Color.black);
      g.fillRect(1,1,692,592);
      //drawing map
      map.draw((Graphics2D)g);
      //broders
      g.setColor(Color.yellow);
      g.fillRect(0,0,3,592); //left vertical
      g.fillRect(0,0,692,3);  //top horizontal
      g.fillRect(691,0,3,592);
      //scores
g.setColor(Color.white);
g.setFont(new Font("serf",Font.BOLD,25 ));
g.drawString(""+score,90,30);
//paddle
      g.setColor(Color.green);
      g.fillRect(playerx,550,100,8 );
      //ball
      g.setColor(Color.yellow);
      g.fillOval(ballposX,ballposy,20,20 );
      if (totalBricks<=0){
          play=false;
          ballXdir=0;
          ballYdir=0;
          g.setColor(Color.RED);
          g.setFont(new Font("serf",Font.BOLD,25));
          g.drawString("You won:",260,300);

          g.setFont(new Font("serf",Font.BOLD,20));
          g.drawString("Press Enter to restart",230,350);

      }
      if(ballposy>570){
          play=false;
          ballXdir=0;
          ballYdir=0;
          g.setColor(Color.RED);
          g.setFont(new Font("serf",Font.BOLD,25));
          g.drawString("Game over scores:",190,300);

          g.setFont(new Font("serf",Font.BOLD,20));
          g.drawString("Press Enter to restart",230,350);
      }
      g.dispose();

  }

    @Override
    public void actionPerformed(ActionEvent e) {
     timer.start();

   //for ball movement
       if(play){
           //detecting at paddle
           if(new Rectangle(ballposX,ballposy,20,20).intersects(new Rectangle(playerx,550,100,8))){
               ballYdir=-ballYdir;
           }
        A:for(int i=0;i<map.map.length;i++){
               for (int j=0;j<map.map[0].length;j++){
                   if(map.map[i][j]>0){
                       int brickX = j* map.brickWidth+80;
                       int brickY=i*map.brickHeight+50;
            /* 77  */    int brickWidth = map.brickWidth;
                   int brickHeight= map.brickHeight;
                   Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                   Rectangle ballRect = new Rectangle(ballposX,ballposy,20,20);
                       Rectangle brickRect = rect;
                   if(ballRect.intersects(brickRect)){
                       map.setBrickValue(0,i,j);
                       totalBricks--;
                       score +=5;

                       if (ballposX+19<=brickRect.x||ballposX+1>=brickRect.x+brickRect.width){
                           ballXdir=-ballXdir;
                       }else{
                           ballYdir=-ballYdir;
                       }
                       break A;
                   }
                   }
               }
           }
        ballposX += ballXdir;
        ballposy+=ballYdir;
     }
       if(ballposX<0){
           ballXdir = -ballXdir;
       }
       if(ballposy<0){
           ballYdir = -ballYdir;
       }  if(ballposX>670){
            ballXdir = -ballXdir;
        }


        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()==KeyEvent.VK_RIGHT){
          if(playerx>=600){
              playerx=600;
          }else{
              moveRight();
          }

      }
if (e.getKeyCode()==KeyEvent.VK_LEFT){
    if(playerx<10){
        playerx=10;
    }else{
        moveLeft();
    }

}
//for enter key working
if(e.getKeyCode()==KeyEvent.VK_ENTER){
    if(!play){

        play =true;
        ballposy=350;
        ballposX=120;
        ballXdir=-1;
        ballYdir=-2;
        playerx=310;
        score=0;
        totalBricks =21;
        map =new MapGenerator(3,7);
        repaint();
    }

}
    }
    public void moveRight(){
        play= true;
        playerx+=20;
    }
    public void moveLeft(){
        play= true;
        playerx-=20;
    }

}

