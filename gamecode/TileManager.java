package gamecode;

import java.io.File;
import java.util.Scanner;
import java.awt.Graphics;

class TileManager {

    Tile[] tile;
    int mapTileNum[][];
    int collisionTiles[];

    TileManager() {
        tile = new Tile[10];
        mapTileNum = new int[GameConstants.MAX_WORLD_ROW.get()][GameConstants.MAX_WORLD_COL.get()];
        getTileImage();
        collisionTiles = new int[]{0, 6};
        loadMap();
    }

    void getTileImage() {
        tile[0] = new Tile();
        tile[0].loadTile("resources/tiles/0.png");
        tile[0].collision = true;
        tile[1] = new Tile();
        tile[1].loadTile("resources/tiles/1.png");
        tile[2] = new Tile();
        tile[2].loadTile("resources/tiles/2.png");
        tile[3] = new Tile();
        tile[3].loadTile("resources/tiles/3.png");
        tile[4] = new Tile();
        tile[4].loadTile("resources/tiles/4.png");
        tile[5] = new Tile();
        tile[5].loadTile("resources/tiles/5.png");
        tile[6] = new Tile();
        tile[6].loadTile("resources/tiles/6.png");
        tile[6].collision = true;
        tile[7] = new Tile();
        tile[7].loadTile("resources/tiles/7.png");
        tile[8] = new Tile();
        tile[8].loadTile("resources/tiles/8.png");
        tile[9] = new Tile();
        tile[9].loadTile("resources/tiles/9.png");
    }
    
    public void loadMap() {
        try {
            File fileObj = new File("resources/maps/worldMap.txt");
            Scanner scanFile = new Scanner(fileObj);
            for (int row=0; row<GameConstants.MAX_WORLD_ROW.get(); row++) {
                String line = scanFile.nextLine();
                System.out.println(line);
                for (int col=0; col<GameConstants.MAX_WORLD_COL.get(); col++) {
                    String number[] = line.split(" ");
                    int num = Integer.parseInt(number[col]);
                    mapTileNum[row][col] = num;
                }
            }
            scanFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Player player) {
        int tileNum;
        int worldX;
        int worldY;
        int screenX;
        int screenY;

        for (int row=0; row<GameConstants.MAX_WORLD_ROW.get(); row++) {
            for (int col=0; col<GameConstants.MAX_WORLD_COL.get(); col++) {
                tileNum = mapTileNum[row][col];
                worldX = col * GameConstants.TILE_SCALED.get();
                worldY = row * GameConstants.TILE_SCALED.get();
                screenX = worldX - player.getX() + player.screenX;
                screenY = worldY - player.getY() + player.screenY;
                
                if (player.screenX > player.getX()) {
                    screenX = worldX;
                }

                if (player.screenY > player.getY()) {
                    screenY = worldY;
                }
                        
                if (GameConstants.WORLD_WIDTH.get() - player.getX() < player.rightOffset) {
                    screenX = GameConstants.WINDOW_WIDTH.get() - (GameConstants.WORLD_WIDTH.get() - worldX);
                }

                if (GameConstants.WORLD_HEIGHT.get() - player.getY() < player.bottomOffset) {
                    screenY = GameConstants.WINDOW_HEIGHT.get() - (GameConstants.WORLD_HEIGHT.get() - worldY);
                }

                if (worldX + GameConstants.TILE_SCALED.get() > player.getX() - player.screenX && 
                    worldX - GameConstants.TILE_SCALED.get() < player.getX() + player.rightOffset &&
                    worldY + GameConstants.TILE_SCALED.get() > player.getY() - player.screenY && 
                    worldY - GameConstants.TILE_SCALED.get() < player.getY() + player.bottomOffset) {
                        g.drawImage(tile[tileNum].image, screenX, screenY, GameConstants.TILE_SCALED.get(), GameConstants.TILE_SCALED.get(), null);
                } else if 
                    (player.screenX > player.getX() || 
                    player.screenY > player.getY() || 
                    player.rightOffset > GameConstants.WORLD_WIDTH.get() - player.getX() || 
                    player.bottomOffset > GameConstants.WORLD_HEIGHT.get() - player.getY()) {
                        g.drawImage(tile[tileNum].image, screenX, screenY, GameConstants.TILE_SCALED.get(), GameConstants.TILE_SCALED.get(), null);
                }
            }
        }
    }
}
