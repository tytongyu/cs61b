package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Position implements Serializable {
    private int xPosition;
    private int yPosition;

    public Position(int x, int y) {
        xPosition = x;
        yPosition = y;
    }

    public void setxPosition(int x) {
        xPosition = x;
    }

    public void setyPosition(int y) {
        yPosition = y;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    /**是否在范围内*/
    public boolean inBounds(int[][] worldArray) {
        return xPosition > -1 && xPosition < worldArray.length && yPosition > -1 && yPosition < worldArray[0].length;
    }

    /**是否在范围内*/
    public void ValueSet(int[][] worldArray, int x) {
        worldArray[xPosition][yPosition] = x;
    }

    /**返回四周可连接的点集*/
    public List<Position> ConnectedPointList(int[][] worldArray) {
        List<Position> ConnectedPointList = new ArrayList<>();
        /**上*/
        Position upPoint = new Position(xPosition, yPosition + 2);
        if (upPoint.inBounds(worldArray)) {
            if (worldArray[upPoint.getxPosition()][upPoint.getyPosition()] == 1) {
                ConnectedPointList.add(upPoint);
            }
        }
        /**下*/
        Position downPoint = new Position(xPosition, yPosition - 2);
        if (downPoint.inBounds(worldArray)) {
            if (worldArray[downPoint.getxPosition()][downPoint.getyPosition()] == 1) {
                ConnectedPointList.add(downPoint);
            }
        }
        /**左*/
        Position leftPoint = new Position(xPosition - 2, yPosition);
        if (leftPoint.inBounds(worldArray)) {
            if (worldArray[leftPoint.getxPosition()][leftPoint.getyPosition()] == 1) {
                ConnectedPointList.add(leftPoint);
            }
        }
        /**右*/
        Position rightPoint = new Position(xPosition + 2, yPosition);
        if (rightPoint.inBounds(worldArray)) {
            if (worldArray[rightPoint.getxPosition()][rightPoint.getyPosition()] == 1) {
                ConnectedPointList.add(rightPoint);
            }
        }
        return ConnectedPointList;
        }

    /**是否是死胡同*/
    public boolean isDeadEnd(int[][] worldArray) {
        if (worldArray[xPosition][yPosition] != 3) {
            return false;
        }
        int x = 0;
        if (worldArray[xPosition - 1][yPosition] == 0) {
            x++;
        }
        if (worldArray[xPosition + 1][yPosition] == 0) {
            x++;
        }
        if (worldArray[xPosition][yPosition - 1] == 0) {
            x++;
        }
        if (worldArray[xPosition][yPosition + 1] == 0) {
            x++;
        }
        if (x == 3) {
            return true;
        } else {
            return false;
        }
    }

    /**是否是多余墙*/
    public boolean isSolidWall(int[][] worldArray) {
        if (worldArray[xPosition][yPosition] != 0) {
            return false;
        }
        int x = 0;
        Position upPoint = new Position(xPosition - 1, yPosition);
        if (upPoint.inBounds(worldArray)) {
            if (worldArray[xPosition - 1][yPosition] == 2 || worldArray[xPosition - 1][yPosition] == 3) {
                x++;
            }
        }
        upPoint = new Position(xPosition + 1, yPosition);
        if (upPoint.inBounds(worldArray)) {
            if (worldArray[xPosition + 1][yPosition] == 2 || worldArray[xPosition + 1][yPosition] == 3) {
                x++;
            }
        }
        upPoint = new Position(xPosition, yPosition - 1);
        if (upPoint.inBounds(worldArray)) {
            if (worldArray[xPosition][yPosition - 1] == 2 || worldArray[xPosition][yPosition - 1] == 3) {
                x++;
            }
        }
        upPoint = new Position(xPosition, yPosition + 1);
        if (upPoint.inBounds(worldArray)) {
            if (worldArray[xPosition][yPosition + 1] == 2 || worldArray[xPosition][yPosition + 1] == 3) {
                x++;
            }
        }
        upPoint = new Position(xPosition - 1, yPosition - 1);
        if (upPoint.inBounds(worldArray)) {
            if (worldArray[xPosition - 1][yPosition - 1] == 2 || worldArray[xPosition - 1][yPosition - 1] == 3) {
                x++;
            }
        }
        upPoint = new Position(xPosition + 1, yPosition + 1);
        if (upPoint.inBounds(worldArray)) {
            if (worldArray[xPosition + 1][yPosition + 1] == 2 || worldArray[xPosition + 1][yPosition + 1] == 3) {
                x++;
            }
        }
        upPoint = new Position(xPosition + 1, yPosition - 1);
        if (upPoint.inBounds(worldArray)) {
            if (worldArray[xPosition + 1][yPosition - 1] == 2 || worldArray[xPosition + 1][yPosition - 1] == 3) {
                x++;
            }
        }
        upPoint = new Position(xPosition - 1, yPosition + 1);
        if (upPoint.inBounds(worldArray)) {
            if (worldArray[xPosition - 1][yPosition + 1] == 2 || worldArray[xPosition - 1][yPosition + 1] == 3) {
                x++;
            }
        }
        if (x == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**获得四周点的集合*/
    public List<Position> getAroud(int[][] worldArray) {
        List<Position> aroudPos = new ArrayList<>();
        if (xPosition - 1 > -1) {
            aroudPos.add(new Position(xPosition - 1, yPosition));
        }
        if (xPosition + 1 < worldArray.length) {
            aroudPos.add(new Position(xPosition + 1, yPosition));
        }
        if (yPosition - 1 > -1) {
            aroudPos.add(new Position(xPosition, yPosition - 1));
        }
        if (yPosition + 1 < worldArray[0].length) {
            aroudPos.add(new Position(xPosition, yPosition + 1));
        }
        return aroudPos;
    }

    /**是否为边界点*/
    public boolean isEdge(int[][] worldArray) {
        if (worldArray[xPosition][yPosition] != 0) {
            return false;
        }
        int num = 0;
        for (Position pp : this.getAroud(worldArray)) {
            if (worldArray[pp.xPosition][pp.yPosition] == 2 || worldArray[pp.xPosition][pp.yPosition] == 3) {
                num++;
            }
        }
        if (num == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**点的位置图案set*/
    public void drawTile(TETile[][] world, TETile t) {
        world[xPosition][yPosition] = t;
    }

    /**点的位置图案是否*/
    public boolean isTile(TETile[][] world, TETile t) {
        return world[xPosition][yPosition].equals(t);
    }


    }

