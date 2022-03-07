package gamecode;

class Tile extends Sprite {

    boolean collision;

    Tile() {
        super(0, 0);
        collision = false;
    }

    public void loadTile(String imageName) {
        loadImage(imageName);
        getImageDimensions();
    }
}
