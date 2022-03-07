package gamecode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Zombie extends Entity {
    enum State {
        LEFT_WALK,
        LEFT_STAND,
        RIGHT_WALK,
        RIGHT_STAND
    }
    private KeyInput key;
    private int dx;
    private int dy;
    private State animation;
    private List<Integer> frames;
    private Iterator<Integer> frame_iterator;
    double angle;

    public Zombie(int x, int y, KeyInput key) {
        super(x, y);
        this.key = key;
        initZombie();
    }

    private void initZombie() {
        String[] left_frames = {
            "resources/zombie/zombie2_left_1.png",
            "resources/zombie/zombie2_left_2.png",
            "resources/zombie/zombie2_left_3.png",
            "resources/zombie/zombie2_left_4.png",
            "resources/zombie/zombie2_left_5.png",
            "resources/zombie/zombie2_left_6.png",
            "resources/zombie/zombie2_left_7.png",
            "resources/zombie/zombie2_left_8.png",
            "resources/zombie/zombie2_left_9.png",
            "resources/zombie/zombie2_left_10.png"
        };
        String[] right_frames = {
            "resources/zombie/zombie2_right_1.png",
            "resources/zombie/zombie2_right_2.png",
            "resources/zombie/zombie2_right_3.png",
            "resources/zombie/zombie2_right_4.png",
            "resources/zombie/zombie2_right_5.png",
            "resources/zombie/zombie2_right_6.png",
            "resources/zombie/zombie2_right_7.png",
            "resources/zombie/zombie2_right_8.png",
            "resources/zombie/zombie2_right_9.png",
            "resources/zombie/zombie2_right_10.png"
        };
        setLeftFrames(left_frames);
        setRightFrames(right_frames);
        frames = new ArrayList<>();
        animation = State.RIGHT_STAND;
        setFrames();
        animate();
        getImageDimensions();
    }

    private void setFrames() {
        frames.clear();
        switch (animation) {
            case LEFT_STAND:
                frames.add(4);
                frames.add(4);
                frames.add(4);
                frames.add(4);
                frames.add(4);
                frames.add(9);
                frames.add(9);
                frames.add(9);
                frames.add(9);
                frames.add(9);
                break;
            case LEFT_WALK:
                frames.add(0);
                frames.add(1);
                frames.add(2);
                frames.add(3);
                frames.add(4);
                frames.add(5);
                frames.add(6);
                frames.add(7);
                frames.add(8);
                frames.add(9);
                break;
            case RIGHT_STAND:
                frames.add(4);
                frames.add(4);
                frames.add(4);
                frames.add(4);
                frames.add(4);
                frames.add(9);
                frames.add(9);
                frames.add(9);
                frames.add(9);
                frames.add(9);
                break;
            case RIGHT_WALK:
                frames.add(0);
                frames.add(1);
                frames.add(2);
                frames.add(3);
                frames.add(4);
                frames.add(5);
                frames.add(6);
                frames.add(7);
                frames.add(8);
                frames.add(9);
                break;
            default:
                break;
        }
        frame_iterator = frames.iterator();
    }

    public void setState() {
        State old_animation = animation;
        if (dx>0)       animation = State.RIGHT_WALK;
        else if (dx<0)  animation = State.LEFT_WALK;
        else if (dy>0 || dy<0) {
            if (animation == State.RIGHT_STAND) animation = State.RIGHT_WALK;
            if (animation == State.LEFT_STAND) animation = State.LEFT_WALK;
        } else {
            if (animation == State.RIGHT_WALK) animation = State.RIGHT_STAND;
            if (animation == State.LEFT_WALK) animation = State.LEFT_STAND;
        }
        if (animation != old_animation) {
            setFrames();
            animate();
        }
    }

    public void animate() {
        if(!frame_iterator.hasNext()) frame_iterator = frames.iterator();
        int frame = frame_iterator.next();
        switch (animation) {
            case LEFT_STAND:
                image = left_frames.get(frame);
                break;
            case LEFT_WALK:
                image = left_frames.get(frame);
                break;
            case RIGHT_STAND:
                image = right_frames.get(frame);
                break;
            case RIGHT_WALK:
                image = right_frames.get(frame);
                break;
            default:
                break;
        }
    }

    public void move() {
        x += dx;
        y += dy;
        
        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
        
        if (x > GameConstants.WINDOW_WIDTH.get() - width - GameConstants.TILE_SCALED.get()) {
            x = GameConstants.WINDOW_WIDTH.get() - width - GameConstants.TILE_SCALED.get();
        }

        if (y > GameConstants.WINDOW_HEIGHT.get() - height - GameConstants.TILE_SCALED.get()) {
            y = GameConstants.WINDOW_HEIGHT.get() - height - GameConstants.TILE_SCALED.get();
        }
    }

    public void setDirection() {

        if (key.up == false && key.down == false) dy = 0;
        else if (key.up == true && key.down == false) dy = -1;
        else if (key.up == false && key.down == true) dy = 1;
        else dy = 0;

        if (key.left == false && key.right == false) dx = 0;
        else if (key.left == true && key.right == false) dx = -1;
        else if (key.left == false && key.right == true) dx = 1;
        else dx = 0;

        if(dx!=0) {
            angle = Math.atan(dy/dx);
            if (dx<0) angle += Math.PI;

            dy = (int)Math.round(Math.sin(angle)*GameConstants.ZOMBIE_SPEED.get());
            dx = (int)Math.round(Math.cos(angle)*GameConstants.ZOMBIE_SPEED.get());
        } else if (dy!=0) {
            dy = dy*GameConstants.ZOMBIE_SPEED.get();
        }
        setState();
    }
}