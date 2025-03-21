import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;


public class Car_Game extends JPanel implements ActionListener,KeyListener{
    int width = 400;
    int height = 640;

    
    Image bgimg;
    Image carimg;
    Image encarimg;
    Image roadimg;
    
    int carX = width/2-15;
    int carY = 500;
    

    int carWidth = 34;
    int carHeight = 60;

    boolean gameover = false;
    double score = 0;

    class Car{
        int x = carX;
        int y = carY;
        int width = carWidth;
        int height = carHeight;
        Image img;

        Car(Image img){
            this.img = img;
        }
    }


    //encars
    int encarX = width/4;
    int encarY = 10;

    int encarWidth = 34;
    int encarHeight = 60;

    class EnCar{
        int x = encarX;
        int y = encarY;
        int width = encarWidth;
        int height = encarHeight;
        Image img;
        boolean passed = false;

        EnCar(Image img){
            this.img = img;
        }
    }

    //game logic
    Car car;

    int velocityYplus = 2;

    int velocityXminus = -5;
    int velocityXplus = 5;


    ArrayList<EnCar> encars;
    Random rndm = new Random();



    Timer gmloop;
    Timer plTimer;


    Car_Game(){

        setPreferredSize(new Dimension(width, height));

        setFocusable(true);
        addKeyListener(this);

        setBackground(Color.green);
        bgimg = new ImageIcon(getClass().getResource("assets/road.png")).getImage();
        carimg = new ImageIcon(getClass().getResource("assets/play_car.png")).getImage();
        encarimg = new ImageIcon(getClass().getResource("assets/car.png")).getImage();

        car = new Car(carimg);

        encars = new ArrayList<EnCar>();

        plTimer = new Timer(1500,  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeCars();
            }
        });

        plTimer.start();

        gmloop = new Timer(1,this);
        gmloop.start();
    }


    int[] lanePositions = {60, 140, 220, 300};


    public void placeCars(){
        

        int rndmCarX = lanePositions[(int)(Math.random() * lanePositions.length)];
        EnCar encar = new EnCar(encarimg);
        encar.x = rndmCarX;
        encars.add(encar);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(bgimg, 0, 0, width, height,null);
        g.drawImage(car.img, carX, carY, carWidth, carHeight,null);

        for (int i = 0; i < encars.size(); i++) {
            EnCar encar = encars.get(i);
            g.drawImage(encar.img, encar.x, encar.y, encar.width, encar.height, null);
        }


        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameover){
            g.drawString("Game Over"+String.valueOf(score), 10, 35);
        }else{
            g.drawString(String.valueOf(score), 10, 35);
        }

    }

    public void leftmove(){
        carX += velocityXminus;
        if(carX<40){
            gameover = true;
        }
    }
    public void rightmove(){
        carX += velocityXplus;
        if(carX>width - (carWidth+40)){
            gameover = true;
        }
    }
    public void movedown(){
        for(int i = 0 ; i < encars.size(); i++){
            EnCar encar = encars.get(i);
            encar.y+=velocityYplus;
            if(collision(car, encar)){
                gameover = true;
                break;
            }
            if (!encar.passed && encar.y + encar.height > carY + carHeight) {
                encar.passed = true;
                score += 1;
            }

        }
    }


    public boolean collision(Car a, EnCar b) {
        return (carY < b.y + b.height &&  // Player car's top is above enemy car's bottom
                carY + a.height > b.y &&  // Player car's bottom is below enemy car's top
                carX < b.x + b.width &&   // Player car's left is before enemy car's right
                carX + a.width > b.x);    // Player car's right is after enemy car's left
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movedown();
        repaint();
        if(gameover){
            plTimer.stop();
            gmloop.stop();
            JOptionPane.showMessageDialog(null, "Game Over");
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            rightmove();
        }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            leftmove();
        }

    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }

}
