package byog.Core;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Room {
    private Position leftbottom;
    private Position rightup;

    public Room(Position x, Position y) {
        leftbottom = x;
        rightup = y;
    }

    public double getwidth() {
        return rightup.getxPosition()- leftbottom.getxPosition();
    }

    public double getheight() {
        return rightup.getyPosition() - leftbottom.getyPosition();
    }

    public boolean isLegal() {
        return leftbottom.getxPosition() < rightup.getxPosition() &&
                leftbottom.getyPosition() < rightup.getyPosition();
    }

    public Position middlePosition() {
        return new Position((leftbottom.getxPosition()+rightup.getxPosition())/2,
                (leftbottom.getyPosition()+rightup.getyPosition())/2);
    }

    public boolean isOverlapped(Room a) {
        return Math.abs(a.middlePosition().getxPosition()-this.middlePosition().getxPosition())
                < (a.getwidth() + this.getwidth())/2 &&
                Math.abs(a.middlePosition().getyPosition()-this.middlePosition().getyPosition())
                        < (a.getheight() + this.getheight())/2;
    }

    public boolean isOverlapped(List<Room> roomList) {
        if (roomList.size() == 0) {
            return false;
        }
        for (Room a : roomList) {
            if (this.isOverlapped(a)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNear(Room a) {
        return Math.abs(a.middlePosition().getxPosition()-this.middlePosition().getxPosition())
                < (a.getwidth() + this.getwidth())/2 + 4 &&
                Math.abs(a.middlePosition().getyPosition()-this.middlePosition().getyPosition())
                        < (a.getheight() + this.getheight())/2 + 4;
    }

    public boolean isNear(List<Room> roomList) {
        if (roomList.size() == 0) {
            return false;
        }
        for (Room a : roomList) {
            if (this.isNear(a)) {
                return true;
            }
        }
        return false;
    }

    public void drawRoom(int[][] worldArray) {
        for (int i = leftbottom.getxPosition(); i <= rightup.getxPosition(); i++) {
            for (int j = leftbottom.getyPosition(); j <= rightup.getyPosition(); j++) {
                worldArray[i][j] = 2;
            }
        }
    }

    public Position outerPoint (int x, Random r) {
        Position outerPoint = new Position(0, 0);
        List<Position> outerPointList = new Stack();
        int pointer;
        switch (x) {
            /**左边*/
            case 0:
                for (int i = leftbottom.getyPosition(); i < rightup.getyPosition() + 1; i++) {
                    outerPointList.add(new Position(leftbottom.getxPosition() - 1 , i));
                }
                pointer = r.nextInt(outerPointList.size());
                outerPoint = outerPointList.get(pointer);
                /**右边*/
            case 1:
                for (int i = leftbottom.getyPosition(); i < rightup.getyPosition() + 1; i++) {
                    outerPointList.add(new Position(rightup.getxPosition() + 1 , i));
                }
                pointer = r.nextInt(outerPointList.size());
                outerPoint = outerPointList.get(pointer);
                /**下边*/
            case 2:
                for (int i = leftbottom.getxPosition(); i < rightup.getxPosition() + 1; i++) {
                    outerPointList.add(new Position(i , leftbottom.getyPosition() - 1));
                }
                pointer = r.nextInt(outerPointList.size());
                outerPoint = outerPointList.get(pointer);
                /**上边*/
            case 3:
                for (int i = leftbottom.getxPosition(); i < rightup.getxPosition() + 1; i++) {
                    outerPointList.add(new Position(i , rightup.getyPosition() + 1));
                }
                pointer = r.nextInt(outerPointList.size());
                outerPoint = outerPointList.get(pointer);
        }
        return outerPoint;
    }

}
