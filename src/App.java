import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int gamewidth = 400;
        int gameheight = 640;



        JFrame fm = new JFrame("Car Game");
        fm.setSize(gamewidth,gameheight);
        fm.setLocationRelativeTo(null);
        fm.setResizable(false);
        fm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Car_Game cg = new Car_Game();
        fm.add(cg);
        fm.pack();
        cg.requestFocus();
        fm.setVisible(true);
    }
}
