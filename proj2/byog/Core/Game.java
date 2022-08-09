package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 81;
    public static final int HEIGHT = 31;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawStartUI();
        char firstChar = getFirstChar();
        if (firstChar == 'l') {
            loadgame();
        } else if (firstChar == 'n') {
            newgame();
        } else if (firstChar == 'q') {
            System.exit(0);
        }
    }

    /*newgame in playWithKeyboard*/
    private void newgame() {
        TETile[][] finalWorldFrame = new TETile[81][31];
        int[][] world = new int[81][31];
        long seed = getSeed();
        worldgenerate(world, seed);
        fillStuff(finalWorldFrame, world);
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        play(finalWorldFrame);
    }

    /*loadgame in playWithKeyboard*/
    private void loadgame() {
        TETile[][] finalWorldFrame = getSavedGame();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        play(finalWorldFrame);
    }

    /*play in playWithKeyboard*/
    private void play(TETile[][] finalWorldFrame) {
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = Character.toLowerCase(StdDraw.nextKeyTyped());
            switch (c) {
                case 'w':
                    Player.walkup(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                case 'a':
                    Player.walkleft(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                case 's':
                    Player.walkdown(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                case 'd':
                    Player.walkright(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                case ':':
                    while (true) {
                        if (!StdDraw.hasNextKeyTyped()) {
                            continue;
                        }
                        if (Character.toLowerCase(StdDraw.nextKeyTyped()) == 'q') {
                            saveGame(finalWorldFrame);
                            System.exit(0);
                        } else {
                            break;
                        }
                    }
                    break;
                default:
            }
        }
    }

    private void drawStartUI() {
        /**draw background*/
        StdDraw.setCanvasSize(WIDTH*16, (HEIGHT + 1) * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT + 1);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);

        Font font = new Font("Monaco", Font.PLAIN, 60);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "CS61B : The Game");

        Font smallfont = new Font("Monaco", Font.PLAIN, 30);
        StdDraw.setFont(smallfont);
        StdDraw.text(WIDTH / 2, HEIGHT / 4 + 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4 - 2, "Quit (Q)");

        StdDraw.show();
    }

    private char getFirstChar() {
        char c;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            c = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (c == 'n' || c == 'l' || c == 'q') {
                break;
            }
        }
        return c;
    }

    private long getSeed() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 50));
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "Please enter a random seed");
        StdDraw.show();
        String seedString = "";
        while (true) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 50));
            StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "Please enter a random seed");
            char digit;
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            digit = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (digit != 's') {
                if (!Character.isDigit(digit)) {
                    continue;
                }
                seedString += digit;
                StdDraw.setFont(new Font("Monaco", Font.PLAIN, 30));
                StdDraw.text(WIDTH / 2, HEIGHT / 2, seedString);
                StdDraw.show();
            } else {
                break;
            }
        }
        long seed = Long.valueOf(seedString);
        return seed;
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = null;
        input = input.toLowerCase();
        char firstChar = input.toCharArray()[0];
        if (firstChar == 'l') {
            finalWorldFrame = loadgame(input);
        } else if (firstChar == 'n') {
            finalWorldFrame = newgame(input);
        } else if (firstChar == 'q') {
            System.exit(0);
        }
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    /*newgame in playWithInputString*/
    private TETile[][] newgame(String input) {
        TETile[][] finalWorldFrame = new TETile[81][31];
        int[][] world = new int[81][31];
        int indexofS = input.indexOf("s");
        long seed = Long.valueOf(input.substring(1, indexofS));
        worldgenerate(world, seed);
        fillStuff(finalWorldFrame, world);
        play(finalWorldFrame, input.substring(indexofS + 1));
        return finalWorldFrame;
    }

    /*loadgame in playWithInputString*/
    private TETile[][] loadgame(String input) {
        TETile[][] finalWorldFrame = getSavedGame();
        play(finalWorldFrame, input.substring(1));
        return finalWorldFrame;
    }

    private void saveGame(TETile[][] finalWorldFrame) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savefile.txt"));
            out.writeObject(finalWorldFrame);
            out.writeObject(Player.getPlayer());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*play in String*/
    private void play(TETile[][] finalWorldFrame, String playString) {
        char[] chars = playString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case 'w':
                    Player.walkup(finalWorldFrame);
                    break;
                case 'a':
                    Player.walkleft(finalWorldFrame);
                    break;
                case 's':
                    Player.walkdown(finalWorldFrame);
                    break;
                case 'd':
                    Player.walkright(finalWorldFrame);
                    break;
                case ':':
                    if (chars[i+1] == 'q') {
                        saveGame(finalWorldFrame);
                        return;
                    }
                    break;
                default:
            }
        }
    }

    /*getSavedGame*/
    private TETile[][] getSavedGame() {
        TETile[][] finalWorldFrame = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("savefile.txt"));
            finalWorldFrame = (TETile[][]) in.readObject();
            Player.setPlayer((Position) in.readObject());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return finalWorldFrame;
    }

    public static TETile GivenStuff(int x) {
        switch (x) {
            case 0: return Tileset.WALL;
            /*一开始1表示未开发，后来1完全开发后改为noting*/
            case 1: return Tileset.NOTHING;
            case 4: return Tileset.PLAYER;
            /*4表示选手*/
            case 5: return Tileset.LOCKED_DOOR;
            /*5表示门*/
            /*2表示floor*/
            default: return Tileset.FLOOR;
        }
    }

    public static void fillStuff(TETile[][] a, int[][] worldArray) {

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                a[i][j] = GivenStuff(worldArray[i][j]);
            }
        }
    }

    /* generate random world*/
    public static void worldgenerate(int[][] worldArray, long seed) {
        Random r = new Random(seed);
        /*第一步，未开发种子为1，初始墙为0，偶数位为墙*/
        List<Position> UndveSeed = new ArrayList<>();
        for (int i = 1; i < worldArray.length - 1; i = i + 2) {
            for (int j = 1; j <worldArray[0].length - 1; j = j + 2) {
                worldArray[i][j] = 1;
                /*保存未开发种子点，但避免生成room时越界及room在边界边缘，因此收集有限范围*/
                if (i > 1 && i < WIDTH - 8 && j > 1 && j < HEIGHT - 8) {
                    UndveSeed.add(new Position(i, j));
                }
            }
        }
        /*第二步，生成房间*/
        int RoomNum = 10 + r.nextInt(5);
        List<Room> roomList = new ArrayList<>(RoomNum);
        for (int i = 0; i< RoomNum; i++) {
            Room a = generateRoom(UndveSeed, r);
            if (a.isNear(roomList)) {
                i--;
            } else {
                roomList.add(a);
            }
        }
        for (Room a : roomList) {
            a.drawRoom(worldArray);
        }

        /*第三步，生成走廊*/
        /*选取边界起始点*/
        int startPointEdge = r.nextInt(4);
        List<Position> startPointList = new ArrayList<>();
        Position startPoint = new Position(0,0);
        int pointer;
        switch (startPointEdge) {
            /*左边*/
            case 0:
                for (int i = 1; i < HEIGHT - 1; i++) {
                    if (worldArray[1][i] == 1) {
                        startPointList.add(new Position(1,i));
                    }
                }
                pointer = r.nextInt(startPointList.size());
                startPoint = startPointList.get(pointer);
                /*右边*/
            case 1:
                for (int i = 1; i < HEIGHT - 1; i++) {
                    if (worldArray[WIDTH - 2][i] == 1) {
                        startPointList.add(new Position(WIDTH - 2,i));
                    }
                }
                pointer = r.nextInt(startPointList.size());
                startPoint = startPointList.get(pointer);
                /*下边*/
            case 2:
                for (int i = 1; i < WIDTH - 1; i++) {
                    if (worldArray[i][1] == 1) {
                        startPointList.add(new Position(i,1));
                    }
                }
                pointer = r.nextInt(startPointList.size());
                startPoint = startPointList.get(pointer);
                /*上边*/
            case 3:
                for (int i = 1; i < WIDTH - 1; i++) {
                    if (worldArray[i][HEIGHT - 2] == 1) {
                        startPointList.add(new Position(i,HEIGHT - 2));
                    }
                }
                pointer = r.nextInt(startPointList.size());
                startPoint = startPointList.get(pointer);
        }
        List<Position> walkPointList = new ArrayList<>();
        startPoint.ValueSet(worldArray, 3);
        walkPointList.add(startPoint);
        while (!walkPointList.isEmpty()) {
            List<Position> CurrentConnectedPointList =walkPointList.get(walkPointList.size() - 1).ConnectedPointList(worldArray);
            if (!CurrentConnectedPointList.isEmpty()) {
                pointer = r.nextInt(CurrentConnectedPointList.size());
                Position connectedPoint =CurrentConnectedPointList.get(pointer);
                connectedPoint.ValueSet(worldArray, 3);
                Position connectedMiddle =new Position((connectedPoint.getxPosition()+
                        walkPointList.get(walkPointList.size() - 1).getxPosition())/2,
                        (connectedPoint.getyPosition()+
                                walkPointList.get(walkPointList.size() - 1).getyPosition())/2);
                connectedMiddle.ValueSet(worldArray, 3);
                walkPointList.add(CurrentConnectedPointList.get(pointer));
            } else {
                walkPointList.remove(walkPointList.size() - 1);
            }
        }
        /*连通Room和Floor,连通点需再往前延伸一格，以防被墙挡住*/
        for (Room a : roomList) {
            int randomEdge = r.nextInt(4);
            Position RoomConnector = a.outerPoint(randomEdge ,r);
            RoomConnector.ValueSet(worldArray, 2);
            if (randomEdge == 0) {
                new Position(RoomConnector.getxPosition() - 1,RoomConnector.getyPosition()).ValueSet(worldArray, 2);
            } else if (randomEdge == 1) {
                new Position(RoomConnector.getxPosition() + 1,RoomConnector.getyPosition()).ValueSet(worldArray, 2);
            } else if (randomEdge == 2) {
                new Position(RoomConnector.getxPosition(),RoomConnector.getyPosition() - 1).ValueSet(worldArray, 2);
            } else {
                new Position(RoomConnector.getxPosition(),RoomConnector.getyPosition() + 1).ValueSet(worldArray, 2);
            }
            randomEdge = r.nextInt(4);
            RoomConnector = a.outerPoint(randomEdge, r);
            RoomConnector.ValueSet(worldArray, 2);
            if (randomEdge == 0) {
                new Position(RoomConnector.getxPosition() - 1,RoomConnector.getyPosition()).ValueSet(worldArray, 2);
            } else if (randomEdge == 1) {
                new Position(RoomConnector.getxPosition() + 1,RoomConnector.getyPosition()).ValueSet(worldArray, 2);
            } else if (randomEdge == 2) {
                new Position(RoomConnector.getxPosition(),RoomConnector.getyPosition() - 1).ValueSet(worldArray, 2);
            } else {
                new Position(RoomConnector.getxPosition(),RoomConnector.getyPosition() + 1).ValueSet(worldArray, 2);
            }
        }

        /*消灭死胡同*/
        List<Position> FloorList = new Stack<>();
        for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if (worldArray[i][j] == 3) {
                        FloorList.add(new Position(i, j));
                    }
                }
            }
        while (hasDeadEnd(FloorList, worldArray)) {
            for (Position a : FloorList) {
                if (a.isDeadEnd(worldArray)) {
                    a.ValueSet(worldArray,0);
                }
            }
        }

        /*消灭墙堆*/
        List<Position> WallList = new Stack<>();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (worldArray[i][j] == 0) {
                    WallList.add(new Position(i, j));
                }
            }
        }
        while (hasSolidWall(WallList, worldArray)) {
            for (Position a : WallList) {
                if (a.isSolidWall(worldArray)) {
                    a.ValueSet(worldArray,1);
                }
            }
        }

        /*初始化选手及门*/
        List<Position> EdgeList = new ArrayList<>();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Position p = new Position(i,j);
                if (p.isEdge(worldArray)) {
                    EdgeList.add(p);
                }
            }
        }
        Position InitialDoor = EdgeList.get(r.nextInt(EdgeList.size())-1);
        worldArray[InitialDoor.getxPosition()][InitialDoor.getyPosition()] = 5;
        for (Position p : InitialDoor.getAroud(worldArray)) {
            if (worldArray[p.getxPosition()][p.getyPosition()] == 2 || worldArray[p.getxPosition()][p.getyPosition()] == 3) {
                Player.setPlayer(p);
                worldArray[p.getxPosition()][p.getyPosition()] = 4;
                break;
            }
        }

    }


    public static Room generateRoom(List<Position> UndveSeed, Random r) {
        Position x1 = UndveSeed.get(r.nextInt(UndveSeed.size()));
        int[] RoomLength = {2, 4, 6};
        Position x2 = new Position(x1.getxPosition() + RoomLength[r.nextInt(3)], x1.getyPosition() + RoomLength[r.nextInt(3)]);
        Room generateRoom = new Room(x1, x2);
        return generateRoom;
    }

    /*是否存在死胡同*/
    public static boolean hasDeadEnd(List<Position> FloorList, int[][] worldArray) {
        for (Position a : FloorList) {
            if (a.isDeadEnd(worldArray)) {
                return true;
            }
        }
        return false;
    }

    /*是否存在死胡同*/
    public static boolean hasSolidWall(List<Position> WallList, int[][] worldArray) {
        for (Position a : WallList) {
            if (a.isSolidWall(worldArray)) {
                return true;
            }
        }
        return false;
    }

    /*
    public static void main (String[] args) {
        int[][] world = new int[81][31];
        worldgenerate(world, 5);

        for (int j = 0; j < world[0].length; j++) {
            for (int i = world.length - 1; i > -1; i--) {
                System.out.print(world[i][j]);
            }
            System.out.println();
        }

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        fillStuff(randomTiles, world);
        ter.renderFrame(randomTiles);

    }
    */
}