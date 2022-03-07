package gamecode;

import javax.swing.JFrame;

public class Window extends JFrame {

    public Window() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new GamePanel());
        
        setResizable(false);
        pack();
        
        setTitle("CSE 1325 Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}