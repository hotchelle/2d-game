package gamecode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    private Timer timer;
    private boolean ingame;
    private TileManager tilemanager;
    private Sound sound;
    private KeyInput keyinput;
    private Player player;
    private int player_timer;
    private Zombie zombie;
    private int zombie_timer;


    public GamePanel() {

        initBoard();
    }

    private void initBoard() {

        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        ingame = true;
        setPreferredSize(new Dimension(GameConstants.WINDOW_WIDTH.get(), GameConstants.WINDOW_HEIGHT.get()));

        tilemanager = new TileManager();
        sound = new Sound();

        keyinput = new KeyInput();
        addKeyListener(keyinput);
        player = new Player(GameConstants.PLAYER_START_X.get(), GameConstants.PLAYER_START_Y.get(), keyinput);
        player_timer = 0;
        zombie = new Zombie(0, 0, keyinput);
        zombie_timer = 0;
        
        timer = new Timer(GameConstants.DELAY.get(), this);
        timer.start();
        playMusic(0);
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.playAudio();
        sound.loopAudio();

    }
    public void stopMusic(){
        sound.stopAudio();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.playAudio();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {
        
        tilemanager.draw(g, player);

        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.screenX, player.screenY, player.getWidth()*GameConstants.GAME_SCALE.get(), player.getHeight()*GameConstants.GAME_SCALE.get(), this);
        }

        if (zombie.isVisible()) {
            g.drawImage(zombie.getImage(), zombie.getX(), zombie.getY(), zombie.getWidth()*GameConstants.GAME_SCALE.get(), zombie.getHeight()*GameConstants.GAME_SCALE.get(), this);
        }

    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (GameConstants.WINDOW_WIDTH.get() - fm.stringWidth(msg)) / 2, GameConstants.WINDOW_HEIGHT.get() / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updatePlayer();
        updateZombie();

        // checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updatePlayer() {
        if (player.isVisible()) {
            player.setDirection();
            player.move(tilemanager);
            player_timer++;
            if (player_timer > GameConstants.PLAYER_TIMER.get()) {
                player.animate();
                player_timer = 0;
            }
        }
    }

    private void updateZombie() {
        if (zombie.isVisible()) {
            zombie.setDirection();
            zombie.move();
            zombie_timer++;
            if (zombie_timer > GameConstants.ZOMBIE_TIMER.get()) {
                zombie.animate();
                zombie_timer = 0;
            }
        }}

    // public void checkCollisions() {

    //     Rectangle r3 = spaceship.getBounds();

    //     for (Alien alien : aliens) {
            
    //         Rectangle r2 = alien.getBounds();

    //         if (r3.intersects(r2)) {
                
    //             spaceship.setVisible(false);
    //             alien.setVisible(false);
    //             ingame = false;
    //         }
    //     }

    //     List<Missile> ms = spaceship.getMissiles();

    //     for (Missile m : ms) {

    //         Rectangle r1 = m.getBounds();

    //         for (Alien alien : aliens) {

    //             Rectangle r2 = alien.getBounds();

    //             if (r1.intersects(r2)) {
                    
    //                 m.setVisible(false);
    //                 alien.setVisible(false);
    //                 aliens_left--;
    //             }
    //         }
    //     }
    // }

    // private class TAdapter extends KeyAdapter {

    //     @Override
    //     public void keyReleased(KeyEvent e) {
    //         player.keyReleased(e);
    //         zombie.keyReleased(e);
    //     }

    //     @Override
    //     public void keyPressed(KeyEvent e) {
    //         player.keyPressed(e);
    //         zombie.keyPressed(e);
    //     }
    // }
}