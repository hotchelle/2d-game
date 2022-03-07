package gamecode;

public enum GameConstants {
    WINDOW_WIDTH        (1200),
    WINDOW_HEIGHT       (800),
    TILE_SIZE           (32),
    GAME_SCALE          (3),
    TILE_SCALED         (32*3),
    MAX_WORLD_ROW       (50),
    MAX_WORLD_COL       (50),
    WORLD_WIDTH         (50*32*3),
    WORLD_HEIGHT        (50*32*3),
    PLAYER_START_X      (4*32*3),
    PLAYER_START_Y      (5*32*3),
    DELAY               (15),
    PLAYER_SPEED        (6),
    PLAYER_TIMER        (8),
    ZOMBIE_SPEED        (2),
    ZOMBIE_TIMER        (4);

    private final int value;
    GameConstants(int value) {
        this.value = value;
    }
    public final int get() { return value; }
}