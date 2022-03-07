package gamecode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player extends Entity {
    enum State {
        LEFT_WALK,
        LEFT_STAND,
        RIGHT_WALK,
        RIGHT_STAND
    }
    private KeyInput key;
    private State animation;
    private List<Integer> frames;
    private Iterator<Integer> frame_iterator;
    int rightOffset;
    int bottomOffset;
    double angle;

    public Player(int x, int y, KeyInput key) {
        super(x, y);
        this.key = key;
        initPlayer();
    }

    private void initPlayer() {
        screenX = GameConstants.WINDOW_WIDTH.get()/2 - GameConstants.TILE_SCALED.get()/2;
        screenY = GameConstants.WINDOW_HEIGHT.get()/2 - GameConstants.TILE_SCALED.get()/2;
        String[] left_frames = {
            "resources/player/player_left_1.png",
            "resources/player/player_left_2.png",
            "resources/player/player_left_3.png"
        };
        String[] right_frames = {
            "resources/player/player_right_1.png",
            "resources/player/player_right_2.png",
            "resources/player/player_right_3.png"
        };
        setLeftFrames(left_frames);
        setRightFrames(right_frames);
        frames = new ArrayList<>();
        animation = State.RIGHT_STAND;
        setFrames();
        animate();
        getImageDimensions();
        // setBoundOffsets(8, 16, 56, -16);  //Offsets for game scale x2
        setBoundOffsets(16, 28, 80, 12);    //Offsets for game scale x3
    }

    private void setFrames() {
        frames.clear();
        switch (animation) {
            case LEFT_STAND:
                frames.add(0);
                break;
            case LEFT_WALK:
                frames.add(0);
                frames.add(1);
                frames.add(0);
                frames.add(2);
                break;
            case RIGHT_STAND:
                frames.add(0);
                break;
            case RIGHT_WALK:
                frames.add(0);
                frames.add(1);
                frames.add(0);
                frames.add(2);
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
        if(!frame_iterator.hasNext()) {
            frame_iterator = frames.iterator();
        }
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

    public void move(TileManager tm) {
        //Player movement & tile collision detection
        checkTileCollision(tm);
        if (!tileCollisionX) x += dx;
        if (!tileCollisionY) y += dy;
        
        //Stops player at worldmap's edge
        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
        
        if (x > GameConstants.WORLD_WIDTH.get() - width - GameConstants.TILE_SCALED.get()) {
            x = GameConstants.WORLD_WIDTH.get() - width - GameConstants.TILE_SCALED.get();
        }

        if (y > GameConstants.WORLD_HEIGHT.get() - height - GameConstants.TILE_SCALED.get()) {
            y = GameConstants.WORLD_HEIGHT.get() - height - GameConstants.TILE_SCALED.get();
        }

        //Set Player to middle of window
        screenX = GameConstants.WINDOW_WIDTH.get()/2 - GameConstants.TILE_SCALED.get()/2;
        screenY = GameConstants.WINDOW_HEIGHT.get()/2 - GameConstants.TILE_SCALED.get()/2;

        //Stops camera tracking at worldmap's edge
        if (x < screenX) {
            screenX = x;
        }

        if (y < screenY) {
            screenY = y;
        }

        rightOffset = GameConstants.WINDOW_WIDTH.get() - screenX;
        bottomOffset = GameConstants.WINDOW_HEIGHT.get() - screenY;

        if (GameConstants.WORLD_WIDTH.get() - x < rightOffset) {
            screenX = GameConstants.WINDOW_WIDTH.get() - (GameConstants.WORLD_WIDTH.get() - x);
        }

        if (GameConstants.WORLD_HEIGHT.get() - y < bottomOffset) {
            screenY = GameConstants.WINDOW_HEIGHT.get() - (GameConstants.WORLD_HEIGHT.get() - y);
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

            dy = (int)Math.round(Math.sin(angle)*GameConstants.PLAYER_SPEED.get());
            dx = (int)Math.round(Math.cos(angle)*GameConstants.PLAYER_SPEED.get());
        } else if (dy!=0) {
            dy = dy*GameConstants.PLAYER_SPEED.get();
        }
        setState();
    }
}