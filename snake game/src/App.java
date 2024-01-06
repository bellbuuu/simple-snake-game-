import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int l=600;
        int b=600;

        JFrame frame=new JFrame("Beas");
        frame.setVisible(true);
        frame.setSize(l,b);
        frame.setLocationRelativeTo(null); //centre screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakegame= new SnakeGame(l, b);
        frame.add(snakegame);
        frame.pack();
        snakegame.requestFocus();

    }
}
