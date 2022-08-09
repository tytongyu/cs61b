package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Player implements Serializable {
    private static Position playerPos;

    public Player(Position p) {
        playerPos = p;
    }

    public static void setPlayer(Position p) {
        playerPos = p;
    }

    public static Position getPlayer() {
        return playerPos;
    }

    public static void walk(TETile[][] world, Position p) {
        if (p.isTile(world, Tileset.FLOOR)) {
            p.drawTile(world, Tileset.PLAYER);
            playerPos.drawTile(world, Tileset.FLOOR);
            Player.setPlayer(p);
        }
    }

    public static void walkleft(TETile[][] world) {
        walk(world, new Position(playerPos.getxPosition() - 1, playerPos.getyPosition()));
    }

    public static void walkright(TETile[][] world) {
        walk(world, new Position(playerPos.getxPosition() + 1, playerPos.getyPosition()));
    }

    public static void walkup(TETile[][] world) {
        walk(world, new Position(playerPos.getxPosition(), playerPos.getyPosition() + 1));
    }

    public static void walkdown(TETile[][] world) {
        walk(world, new Position(playerPos.getxPosition(), playerPos.getyPosition() - 1));
    }

}
