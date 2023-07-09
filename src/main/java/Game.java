
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.net.http.HttpHeaders;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.lang.Math;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SpringLayout;

public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(5, this);

    int SCREEN_WIDTH = 600;
    int SCREEN_HEIGHT = 600;
    int UNIT_SIZE = 25;
    int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    int x[] = new int[GAME_UNITS];
    int y[] = new int[GAME_UNITS];
    int bodyParts = 1;
    int foodLine, foodColumn;
    String dir;

    Random rand = new Random();
    boolean actionAllowed = true;

    public Game() {
        Timer timer = new Timer(1, this);

        setBackground(Color.black);

        spawnFood();

        try {
            addMusic();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        timer.start();
        int j = UNIT_SIZE;
        for (int i = bodyParts; i > 0; i--) {
            x[i] = 0;
            y[i] = j;
            j += 1;

        }

    }

    public void addMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream stream = AudioSystem.getAudioInputStream(new File("rasPutin.wav"));

        Clip clip = AudioSystem.getClip();
        clip.open(stream);
        clip.start();
    }

    public void foodIntersection() {
        if (new Rectangle(x[0], y[0], UNIT_SIZE, UNIT_SIZE).intersects(new Rectangle(foodLine, foodColumn, UNIT_SIZE, UNIT_SIZE))) {
            bodyParts++;
            spawnFood();

        }
    }

    public void updateSnakeLocation() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];

        }
    }

    void gameOver(Graphics g) {
        for (int i = bodyParts; i > 1; i--) {
            if ((new Rectangle(x[0], y[0], UNIT_SIZE, UNIT_SIZE).intersects(new Rectangle(x[i], y[i], UNIT_SIZE, UNIT_SIZE)))) {
                g.setColor(Color.RED);
                g.setFont(new Font("Ink FREE", Font.BOLD, 75));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Game Over Bitch", (SCREEN_WIDTH - metrics.stringWidth("Game Over Bitch")) / 2, SCREEN_HEIGHT / 2);
                actionAllowed = false;

            }

        }

    }

    
    public void spawnFood() {
        Random rand = new Random();

        foodLine = rand.nextInt((((int) SCREEN_WIDTH / (UNIT_SIZE)) * UNIT_SIZE));
        foodColumn = rand.nextInt((((int) SCREEN_HEIGHT / (UNIT_SIZE)) * UNIT_SIZE));

        while (foodLine <= 0 || foodLine >= SCREEN_WIDTH || foodColumn <= 0 || foodColumn >= SCREEN_HEIGHT) {
            foodLine = rand.nextInt((((int) SCREEN_WIDTH / (UNIT_SIZE)) * UNIT_SIZE));
            foodColumn = rand.nextInt((((int) SCREEN_HEIGHT / (UNIT_SIZE)) * UNIT_SIZE));
                
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        if (actionAllowed == true) {
            updateSnakeLocation();

            if (k == KeyEvent.VK_W) {
                if (y[0] == 0) {
                    y[0] = 600;
                } else {
                    y[0] -= UNIT_SIZE;
                }
                dir = "w";
            } else if (k == KeyEvent.VK_S) {
                if (y[0] == 600) {
                    y[0] = 0;
                } else {
                    y[0] += UNIT_SIZE;
                }
                dir = "s";
            } else if (k == KeyEvent.VK_D) {
                if (x[0] == 800) {
                    x[0] = 0;
                } else {
                    x[0] += UNIT_SIZE;
                }
                dir = "d";
            } else if (k == KeyEvent.VK_A) {
                if (x[0] == 0) {
                    x[0] = 800;
                } else {
                    x[0] -= UNIT_SIZE;
                }
                dir = "a";
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        foodIntersection();

        repaint();
        
        
    }

    public void drawSnake(Graphics g) {

        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.YELLOW);
                g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                float r = rand.nextFloat();
                float gr = rand.nextFloat();
                float b = rand.nextFloat();
                g.setColor(new Color(r, gr, b));
                g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

        }

    }

    public void drawFood(Graphics g) {

        g.setColor(Color.GREEN);
        g.fillOval(foodLine, foodColumn, 20, 20);
    }

    public void paint(Graphics g) {
        super.paint(g);

        gameOver(g);

        setBackground(Color.BLACK);

        drawFood(g);
        drawSnake(g);

    }

    @Override
    public void repaint() {
        super.repaint();
    }

}
