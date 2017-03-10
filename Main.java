import java.util.*;


class Player {
    static List<Directions> moves = new ArrayList<>();

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int WIDTH = in.nextInt();
        int HEIGHT = in.nextInt();
        int NumberOfPlayers = in.nextInt();
        Lab lab = new Lab(HEIGHT, WIDTH, NumberOfPlayers);

        Strategy runAwayStrategy = new KhatStrategy();
        Strategy randomStrategy = new RandomStrategy();

        // game loop
        while (true) {
            String upString = in.next();
            String rightString = in.next();
            String downString = in.next();
            String leftString = in.next();

            // get coords of players
            for (int player = 0; player < NumberOfPlayers; player++) {
                int y = in.nextInt();
                int x = in.nextInt();
                lab.position(y, x, player);
            }

            boolean isUpAllowed = upString.equals("_");
            boolean isDownAllowed = downString.equals("_");
            boolean isLeftAllowed = leftString.equals("_");
            boolean isRigthAllowed = rightString.equals("_");

            lab.setAllowance(Directions.UP, isUpAllowed);
            lab.setAllowance(Directions.DOWN, isDownAllowed);
            lab.setAllowance(Directions.LEFT, isLeftAllowed);
            lab.setAllowance(Directions.RIGHT, isRigthAllowed);


            System.err.println("Visited:" + lab.getVisitedCount());
            System.err.println(lab.toString());

            runAwayStrategy.getAdvice(lab, isUpAllowed, isDownAllowed, isLeftAllowed, isRigthAllowed);
            double runWeight = runAwayStrategy.adviceStrength;

            Strategy strategy;
            if (isStucked() && runWeight > 6) {
                strategy = randomStrategy;
            } else {
                strategy = runAwayStrategy;
            }

            makeMove(strategy
                    .getAdvice(lab, isUpAllowed, isDownAllowed, isLeftAllowed, isRigthAllowed));
            
        }

    }

    public static void makeMove(Directions direction) {
        moves.add(direction);
        System.out.println(direction.letter);
    }

    // find LEFT - RIGHT - LEFT - RIGHT moves
    public static boolean isStucked() {
        for (int i = moves.size()-1; i > 2; i--) {
            if(moves.get(i) == moves.get(i-2) && moves.get(i-1) == moves.get(i-3)) {
                return true;
            }
        }

        return false;
    }

    public static double getD(double x1, double y1, double x2, double y2){
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }
}

class Advice {
    final double adviceStrength;
    final Directions directions;

    public Advice(double adviceStrength, Directions directions) {
        this.adviceStrength = adviceStrength;
        this.directions = directions;
    }
}

abstract class Strategy {
    double adviceStrength;
    abstract Directions getAdvice(Lab lab, boolean isUpAllowed, boolean isDownAllowed, boolean isLeftAllowed, boolean isRigthAllowed);
}

class RandomStrategy extends Strategy {
    private Random random;

    public RandomStrategy() {
        this.random = new Random();
        adviceStrength = 1;
    }


    @Override
    Directions getAdvice(Lab lab, boolean isUpAllowed, boolean isDownAllowed, boolean isLeftAllowed, boolean isRigthAllowed) {
        List<Directions> possibleWays = new ArrayList<>();
        if(isUpAllowed) possibleWays.add(Directions.UP);
        if(isDownAllowed) possibleWays.add(Directions.DOWN);
        if(isLeftAllowed) possibleWays.add(Directions.LEFT);
        if(isRigthAllowed) possibleWays.add(Directions.RIGHT);

        return possibleWays.get(random.nextInt(possibleWays.size()));
    }
}



class KhatStrategy extends Strategy {
    private double lastX = 0;
    private double lastY = 0;


    @Override
    Directions getAdvice(Lab lab, boolean isUpAllowed, boolean isDownAllowed, boolean isLeftAllowed, boolean isRigthAllowed) {
        final int ME = lab.ME;

        double[] x = new double[lab.numberOfPlayers];
        double[] y = new double[lab.numberOfPlayers];
        double [] distance = new double[lab.numberOfPlayers - 1];

        for (int player = 0; player < lab.numberOfPlayers; player++) {
            x[player] = lab.coords[player][0];
            y[player] = lab.coords[player][1];
        }

        double min = 100000000;
        int closiestEnemy = 0;
        for (int enemy = 0; enemy < ME; enemy++) {
            distance[enemy] = Player.getD(x[ME], y [ME], x[enemy], y[enemy]);
            if (distance[enemy] < min){
                min = distance[enemy];
                closiestEnemy = enemy;
            }
        }

        adviceStrength = distance[closiestEnemy];

        double nearX = x[closiestEnemy];
        double nearY = y[closiestEnemy];
        double myX = x[ME];
        double myY = y[ME];

        boolean nearestX = false;
        boolean nearestY = false;

        if (Math.abs(nearX - myX) < Math.abs(nearY - myY)){
            nearestX = true;
        } else if (Math.abs(nearX - myX) > Math.abs(nearY - myY)) {
            nearestY = true;
        } else {
            nearestX = true;
            nearestY = true;
        }

        if (nearestX && myX - nearX >= 0){
            if(isRigthAllowed){
                return Directions.RIGHT;
            } else {
                if (isLeftAllowed && myX - nearX == 0){
                    return Directions.LEFT;
                } else if (myY - nearY >= 0){
                    if(isDownAllowed && myY + 1 != lastY){
                        return Directions.DOWN;
                    } else if(isDownAllowed && myY - nearY <= 10){
                        return Directions.DOWN;
                    } else if (isUpAllowed && myY - 1 != lastY){
                        return Directions.UP;
                    } else if (isUpAllowed && myY - nearY >= -6){
                        return Directions.UP;
                    } else {
                        return Directions.LEFT;
                    }
                } else {
                    if (isUpAllowed && myY - 1 != lastY){
                        return Directions.UP;
                    }  else if (isUpAllowed && myY - nearY <= 10){
                        return Directions.UP;
                    } else if(isDownAllowed && myY + 1 != lastY){
                        return Directions.DOWN;
                    } else if(isDownAllowed && myY - nearY >= -6){
                        return Directions.DOWN;
                    } else {
                        return Directions.LEFT;
                    }
                }
            }
        } else if (nearestX && myX - nearX < 0){
            if (isLeftAllowed) {
                return Directions.LEFT;
            } else {
                if (myY - nearY >= 0){
                    if(isDownAllowed && myY + 1 != lastY){
                        return Directions.DOWN;
                    } else if(isDownAllowed && myY - nearY <= 10){
                        return Directions.DOWN;
                    } else if (isUpAllowed && myY - 1 != lastY){
                        return Directions.UP;
                    }  else if (isUpAllowed && myY - nearY >= -6){
                        return Directions.UP;
                    } else {
                        return Directions.RIGHT;
                    }
                } else {
                    if (isUpAllowed && myY - 1 != lastY){
                        return Directions.UP;
                    }  else if (isUpAllowed && myY - nearY <=10){
                        return Directions.UP;
                    } else if(isDownAllowed && myY + 1 != lastY){
                        return Directions.DOWN;
                    } else if(isDownAllowed && myY - nearY >= -6){
                        return Directions.DOWN;
                    } else {
                        return Directions.RIGHT;
                    }
                }
            }
        } else if (myY - nearY >= 0){
            if(isDownAllowed){
                return Directions.DOWN;
            } else {
                if (isUpAllowed && myY - nearY == 0){
                    return Directions.UP;
                } else if (myX - nearX >= 0){
                    if(isRigthAllowed && myX + 1 != lastX){
                        return Directions.RIGHT;
                    } else  if(isRigthAllowed && myX - nearX <= 10){
                        return Directions.RIGHT;
                    } else if (isLeftAllowed && myX - 1 != lastX){
                        return Directions.LEFT;
                    }  else if (isLeftAllowed && myX - nearX >= -6){
                        return Directions.LEFT;
                    }  else {
                        return Directions.UP;
                    }
                } else {
                    if (isLeftAllowed && myX - 1 != lastX){
                        return Directions.LEFT;
                    } else if (isLeftAllowed && myX - nearX >= -6){
                        return Directions.LEFT;
                    } else  if(isRigthAllowed && myX + 1 != lastX){
                        return Directions.RIGHT;
                    } else  if(isRigthAllowed && myX - nearX <= 10){
                        return Directions.RIGHT;
                    } else {
                        return Directions.UP;
                    }
                }
            }
        } else if (myY - nearY < 0){
            if(isUpAllowed){
                return Directions.UP;
            } else {
                if (myX - nearX >= 0){
                    if(isRigthAllowed && myX + 1 != lastX){
                        return Directions.RIGHT;
                    } else  if(isRigthAllowed && myX - nearX <= 10){
                        return Directions.RIGHT;
                    } else if (isLeftAllowed && myX - 1 != lastX){
                        return Directions.LEFT;
                    } else if (isLeftAllowed && myX - nearX >= -6){
                        return Directions.LEFT;
                    } else {
                        return Directions.DOWN;
                    }
                } else {
                    if (isLeftAllowed && myX - 1 != lastX){
                        return Directions.LEFT;
                    } else if (isLeftAllowed && myX - nearX >= -6){
                        return Directions.LEFT;
                    } else  if(isRigthAllowed && myX + 1 != lastX){
                        return Directions.RIGHT;
                    } else  if(isRigthAllowed && myX - nearX <= 10){
                        return Directions.RIGHT;
                    } else {
                        return Directions.DOWN;
                    }
                }
            }
        }
        lastX = myX;
        lastY = myY;

        return null;
    }
}

enum Directions {
    UP('C'), DOWN('D'), LEFT('E'), RIGHT('A');
    public final char letter;

    Directions(char letter) {
        this.letter = letter;
    }
}

enum CellState {UNKNOWN, ALLOWED, NOT_ALLOWED}


class Lab {

    class Cell{
        private boolean isVisited;
        private CellState cellState;

        public Cell() {
            this(false, CellState.UNKNOWN);
        }

        public Cell(boolean isVisited, CellState allowed) {
            this.isVisited = isVisited;
            this.cellState = allowed;
        }

        public boolean isVisited() {
            return isVisited;
        }

        public void setVisited(boolean visited) {
            isVisited = visited;
        }

        public CellState getCellState() {
            return cellState;
        }

        public void setCellState(CellState cellState) {
            this.cellState = cellState;
        }
    }

    private final int height, width;  // размерность поля
    private int visitedCount, turns;
    int[][] coords;  // координаты персонажей
    final int numberOfPlayers, ME;
    private Cell[][] matrix;

    public Lab(int y, int x, int numberOfPlayers) {
        if (y < 0 || x < 0) {
            throw new IllegalArgumentException();
        }

        this.height = y;
        this.width = x;
        matrix = new Cell[y][x];

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                matrix[i][j] = new Cell();
            }
        }

        this.numberOfPlayers = numberOfPlayers;
        ME = numberOfPlayers - 1;
        coords = new int[numberOfPlayers][2];
    }

    public void position(int y, int x, int player) {
        matrix[y][x].setCellState(CellState.ALLOWED);
        if (player == ME) {
            turns++;
            if (!matrix[y][x].isVisited()) {
                matrix[y][x].setVisited(true);
                visitedCount++;
            }
        }
        coords[player] = new int[]{y, x};
    }

    public void setAllowance(Directions direction, boolean isAllowed) {
        int xOffset = 0;
        int yOffset = 0;
        switch (direction) {
            case UP:
                xOffset = -1;
                break;
            case DOWN:
                xOffset = +1;
                break;
            case LEFT:
                yOffset = -1;
                break;
            case RIGHT:
                yOffset = +1;
                break;
        }

        if (isAllowed) {
            allow(coords[ME][0] + yOffset, coords[ME][1] + xOffset);
        }
        else {
            deny(coords[ME][0] + yOffset, coords[ME][1] + xOffset);
        }
    }

    private void deny(int y, int x) {
        if (y < height && x < width && y >= 0 && x >= 0)
            matrix[y][x].setCellState(CellState.NOT_ALLOWED);
    }

    private void allow(int y, int x) {
        if (y < height && x < width && y >= 0 && x >= 0)
            matrix[y][x].setCellState(CellState.ALLOWED);
    }

    public int getVisitedCount() {
        return visitedCount;
    }

    public int getTurns() {
        return turns;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (matrix[j][i].getCellState() == CellState.NOT_ALLOWED) {
                    b.append(" X");
                } else if (matrix[j][i].getCellState() == CellState.UNKNOWN) {
                    b.append(" ?");
                } else if (matrix[j][i].isVisited()) {
                    b.append("  ");
                } else {
                    b.append(" .");
                }
            }
            b.append(" \n");
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            int start, end;
            start = (height + 1)*2*coords[i][1] + coords[i][0]*2;
            end = start + 2;
            switch (numberOfPlayers - i - 1) {
                case 0 :
                    b.replace(start, end, "P"+i);
                    break;
                default:
                    b.replace(start, end, "E"+i);
                    break;
            }
        }
        return b.toString();
    }
}

