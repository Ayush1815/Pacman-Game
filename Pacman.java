import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.JApplet;
import java.awt.*;
import java.util.*;
import java.lang.*;


public class Pacman extends JApplet implements MouseListener, KeyListener
{ 

  long titleTimer = -1;
  long timer = -1;

  Board b=new Board(); 

  javax.swing.Timer frameTimer;
 

 
  public Pacman()
  {
    b.requestFocus();


    JFrame f=new JFrame(); 
    f.setSize(450,500);

   
    f.add(b,BorderLayout.CENTER);

  
    b.addMouseListener(this);  
    b.addKeyListener(this);  

    f.setVisible(true);
    f.setResizable(false);

    b.New=1;

   
    stepFrame(true);

  
    frameTimer = new javax.swing.Timer(30,new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          stepFrame(false);
        }
      });

 
    frameTimer.start();

    b.requestFocus();
  }

  
  public void repaint()
  {
    if (b.player.teleport)
    {
      b.repaint(b.player.lastX-20,b.player.lastY-20,80,80);
      b.player.teleport=false;
    }
    b.repaint(0,0,600,20);
    b.repaint(0,420,600,40);
    b.repaint(b.player.x-20,b.player.y-20,80,80);
    b.repaint(b.ghost1.x-20,b.ghost1.y-20,80,80);
    b.repaint(b.ghost2.x-20,b.ghost2.y-20,80,80);
    b.repaint(b.ghost3.x-20,b.ghost3.y-20,80,80);
    b.repaint(b.ghost4.x-20,b.ghost4.y-20,80,80);
  }


  public void stepFrame(boolean New)
  {
  
    if (!b.titleScreen && !b.winScreen && !b.overScreen)
    {
      timer = -1;
      titleTimer = -1;
    }

  
    if (b.dying>0)
    {
      b.repaint();
      return;
    }

  
    New = New || (b.New !=0) ;

 
    if (b.titleScreen)
    {
      if (titleTimer == -1)
      {
        titleTimer = System.currentTimeMillis();
      }

      long currTime = System.currentTimeMillis();
      if (currTime - titleTimer >= 5000)
      {
        b.titleScreen = false;
        b.demo = true;
        titleTimer = -1;
      }
      b.repaint();
      return;
    }
 
   
    else if (b.winScreen || b.overScreen)
    {
      if (timer == -1)
      {
        timer = System.currentTimeMillis();
      }

      long currTime = System.currentTimeMillis();
      if (currTime - timer >= 5000)
      {
        b.winScreen = false;
        b.overScreen = false;
        b.titleScreen = true;
        timer = -1;
      }
      b.repaint();
      return;
    }


   
    if (!New)
    {
     
      if (b.demo)
      {
        b.player.demoMove();
      }
      else
      {
        b.player.move();
      }

    
      b.ghost1.move(); 
      b.ghost2.move(); 
      b.ghost3.move(); 
      b.ghost4.move(); 
      b.player.updatePellet();
      b.ghost1.updatePellet();
      b.ghost2.updatePellet();
      b.ghost3.updatePellet();
      b.ghost4.updatePellet();
    }

   
    if (b.stopped || New)
    {
      
      frameTimer.stop();

      
      while (b.dying >0)
      {
       
        stepFrame(false);
      }

     
      b.player.currDirection='L';
      b.player.direction='L';
      b.player.desiredDirection='L';
      b.player.x = 200;
      b.player.y = 300;
      b.ghost1.x = 180;
      b.ghost1.y = 180;
      b.ghost2.x = 200;
      b.ghost2.y = 180;
      b.ghost3.x = 220;
      b.ghost3.y = 180;
      b.ghost4.x = 220;
      b.ghost4.y = 180;

     
      b.repaint(0,0,600,600);

     
      b.stopped=false;
      frameTimer.start();
    }
    
    else
    {
      repaint(); 
    }
  }  

  
  public void keyPressed(KeyEvent e) 
  {
   
    if (b.titleScreen)
    {
      b.titleScreen = false;
      return;
    }
    
    else if (b.winScreen || b.overScreen)
    {
      b.titleScreen = true;
      b.winScreen = false;
      b.overScreen = false;
      return;
    }
   
    else if (b.demo)
    {
      b.demo=false;
     
      b.sounds.nomNomStop();
      b.New=1;
      return;
    }

  
    switch(e.getKeyCode())
    {
      case KeyEvent.VK_LEFT:
       b.player.desiredDirection='L';
       break;     
      case KeyEvent.VK_RIGHT:
       b.player.desiredDirection='R';
       break;     
      case KeyEvent.VK_UP:
       b.player.desiredDirection='U';
       break;     
      case KeyEvent.VK_DOWN:
       b.player.desiredDirection='D';
       break;     
    }

    repaint();
  }

  public void mousePressed(MouseEvent e){
    if (b.titleScreen || b.winScreen || b.overScreen)
    {
     
      return;
    }

  
    int x = e.getX();
    int y = e.getY();
    if ( 400 <= y && y <= 460)
    {
      if ( 100 <= x && x <= 150)
      {
      
        b.New = 1;
      }
      else if (180 <= x && x <= 300)
      {
       
        b.clearHighScores();
      }
      else if (350 <= x && x <= 420)
      {
      
        System.exit(0);
      }
    }
  }
  
 
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseClicked(MouseEvent e){}
  public void keyReleased(KeyEvent e){}
  public void keyTyped(KeyEvent e){}
  

  public static void main(String [] args)
  {
      Pacman c = new Pacman();
  } 
}
