import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
    static int width = 600;
    static int height = 600;
    public static void main(String[] args) {
        JFrame j = new JFrame();
        j.setTitle("Snake Game");
        j.setSize(width,height);
        j.setVisible(true);
        j.setResizable(false);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGameplay gameplay = new SnakeGameplay(width,height);
        j.add(gameplay);
        j.pack();
        gameplay.requestFocus();



    }
}