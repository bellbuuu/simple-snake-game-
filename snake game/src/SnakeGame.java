import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random; //random places to place the food on screen
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener,KeyListener{

    private class Tile{
        int x,y;
        Tile(int x, int y){
            this.x=x;
            this.y=y;

        }
    }
    int l, b;
    int tile=25;
    Tile snakehead;

    ArrayList<Tile> snakebody;

    Tile food;
    Random random;

    //game logic
    Timer gameloop;
    int velocityx,velocityy;
    boolean gameover=false;

    SnakeGame(int l, int b){
         
        this.l= l;
        this.b=b;
        setPreferredSize(new Dimension(this.l,this.b));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);


        snakehead= new Tile(5,5);
        food=new Tile(5,5);
        random=new Random();
        placingfood();
        snakebody=new ArrayList<Tile>();

        velocityx=0;
        velocityy=0;

        gameloop=new Timer(100,this); //170 for easy level
        gameloop.start();

    }

    public void paint(Graphics g){
        super.paint(g);
        draw(g);

    }
    public void draw(Graphics g){

        //gridlines
        //for(int i=0;i<l/tile;i++){
        //    g.drawLine(i*tile,0,i*tile,b); //vertical
        //    g.drawLine(0,i*tile,l,i*tile); //horizontal

       // }
        
        //for food
        g.setColor(Color.red);
       // g.fillRect(food.x*tile,food.y*tile,tile,tile);
        g.fill3DRect(food.x*tile,food.y*tile,tile,tile,true);


        //for the snake head
        g.setColor(Color.yellow);
        //g.fillRect(snakehead.x*tile,snakehead.y*tile,tile,tile);
        g.fill3DRect(snakehead.x*tile,snakehead.y*tile,tile,tile,true);
        
        //snake body parts
        for(int i=0;i<snakebody.size();i++){
            Tile snakeparts=snakebody.get(i);
            //g.fillRect(snakeparts.x*tile, snakeparts.y*tile, tile, tile);
            g.fill3DRect(snakeparts.x*tile, snakeparts.y*tile, tile, tile,true);
        }   

        //score track
        g.setFont(new Font("Montserrat",Font.BOLD,18));
        if(gameover==true){
            g.setColor(Color.red);
            g.drawString("GAME OVER :(   your score=" + String.valueOf(snakebody.size()),tile-18,tile);

        }
        else{
            g.drawString("SCORE: "+String.valueOf(snakebody.size()),tile-18,tile);
        }
    }

    public void placingfood(){
        food.x=random.nextInt(l/tile);
        food.y=random.nextInt(b/tile);
        
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;

    }

    public void move(){
        
        //for eating the food:
        if( collision(snakehead,food)==true){
            snakebody.add(new Tile(food.x, food.y));
            placingfood();
        }
        //body tiles
        for(int i=snakebody.size()-1;i>=0;i--){
            Tile snakeparts=snakebody.get(i);
            if(i==0){
                snakeparts.x=snakehead.x;
                snakeparts.y=snakehead.y;
            }
            else{
                Tile previouspart= snakebody.get(i-1);
                snakeparts.x=previouspart.x;
                snakeparts.y=previouspart.y;
            }
        }


        //snakehead
        snakehead.x+=velocityx;
        snakehead.y+=velocityy;

        //gmaeover condition
        for(int i=0;i<snakebody.size();i++){
            Tile snakeparts=snakebody.get(i);
            if(collision(snakehead,snakeparts)){//colliding with snakes head
                gameover=true;

            }
            if(snakehead.x*tile<0 || snakehead.x*tile>l || snakehead.y*tile<0 || snakehead.y*tile>b){ //checking for wall collisions
                gameover=true;

            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameover==true){
            gameloop.stop();
        }

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityy!=1){
            velocityx=0;
            velocityy= -1; //up
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityy!=-1){
            velocityx=0;
            velocityy=1;
        }
        else if(e.getKeyCode()== KeyEvent.VK_LEFT && velocityx!=1){
            velocityx=-1;
            velocityy=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityx!=-1){
            velocityx=1;
            velocityy=0;
        }
    
    }

    @Override
    public void keyTyped(KeyEvent e) {}

     @Override
    public void keyReleased(KeyEvent e) { }
}
