package gamecode;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Entity extends Sprite {
    protected List<Image> left_frames;
    protected List<Image> right_frames;
    int dx;
    int dy;
    int screenX;
    int screenY;
    boolean tileCollisionX;
    boolean tileCollisionY;
    int right_offset;
    int left_offset;
    int top_offset;
    int bottom_offset;

    public Entity(int x, int y) {
        super(x, y);
    }

    protected void setLeftFrames(String[] frames) {
        ImageIcon ii;
        Image frame_image;
        left_frames = new ArrayList<>();
        try {
            for (String frame: frames) {
                ii = new ImageIcon(frame);
                frame_image = ii.getImage();
                left_frames.add(frame_image);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void setRightFrames(String[] frames) {
        ImageIcon ii;
        Image frame_image;
        right_frames = new ArrayList<>();
        try {
            for (String frame: frames) {
                ii = new ImageIcon(frame);
                frame_image = ii.getImage();
                right_frames.add(frame_image);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setBoundOffsets(int right_offset, int left_offset, int top_offset, int bottom_offset) {
        this.right_offset = right_offset;
        this.left_offset = left_offset;
        this.top_offset = top_offset;
        this.bottom_offset = bottom_offset;
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+right_offset, y+top_offset, width+left_offset, height+bottom_offset);
    }

    void checkTileCollision(TileManager tm) {
        tileCollisionX = false;
        tileCollisionY = false;
        int rightCol, leftCol, topRow, bottomRow;

        int right = (x+right_offset);
        int left = (x+right_offset) + (width+left_offset);
        int top = (y+top_offset);
        int bottom = (y+top_offset) + (height+bottom_offset);

        topRow    = (top   /GameConstants.TILE_SCALED.get());
        bottomRow = (bottom/GameConstants.TILE_SCALED.get());

        if(dx < 0) {
            rightCol = (right+dx)/GameConstants.TILE_SCALED.get();
            if (tm.tile[tm.mapTileNum[topRow][rightCol]].collision) tileCollisionX = true;
            if (tm.tile[tm.mapTileNum[bottomRow][rightCol]].collision) tileCollisionX = true;
        }
        if(dx > 0) {
            leftCol = (left+dx)/GameConstants.TILE_SCALED.get();
            if (tm.tile[tm.mapTileNum[topRow][leftCol]].collision) tileCollisionX = true;
            if (tm.tile[tm.mapTileNum[bottomRow][leftCol]].collision) tileCollisionX = true;
        }
        
        rightCol  = (right /GameConstants.TILE_SCALED.get());
        leftCol   = (left  /GameConstants.TILE_SCALED.get());

        if(dy < 0) {
            topRow = (top+dy)/GameConstants.TILE_SCALED.get();
            if (tm.tile[tm.mapTileNum[topRow][rightCol]].collision) tileCollisionY = true;
            if (tm.tile[tm.mapTileNum[topRow][leftCol]].collision) tileCollisionY = true;
        }
        if(dy > 0) {
            bottomRow = (bottom+dy)/GameConstants.TILE_SCALED.get();
            if (tm.tile[tm.mapTileNum[bottomRow][rightCol]].collision) tileCollisionY = true;
            if (tm.tile[tm.mapTileNum[bottomRow][leftCol]].collision) tileCollisionY = true;
        }
    }
}
