import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import java.util.Random;

public class GameScreen extends JFrame{

    public GameScreen(String string) {

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
    GameScreen MainScreen=new GameScreen("Uzay Oyunu");

    MainScreen.setResizable(false);
    MainScreen.setFocusable(false);
    int SCREEN_WIDTH = 700;
    int SCREEN_HEIGHT = 700;
    int UNIT_SIZE = 25;
    int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    
    MainScreen.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    MainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        
    Game game=new Game();
    game.requestFocus();
    game.addKeyListener(game);
    
    game.setFocusable(true);
    game.setFocusTraversalKeysEnabled(false); 
    MainScreen.add(game);
    MainScreen.setVisible(true);
    }
}