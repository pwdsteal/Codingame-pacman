

import java.util.*;


class Player {

    static private void goUp() { System.out.println("C"); }
    static private void goDown() { System.out.println("D"); }
    static private void goRight() { System.out.println("A"); }
    static private void goLeft() { System.out.println("E"); }

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int WIDTH = in.nextInt();
        int HEIGHT = in.nextInt();
        int NumberOfPlayers = in.nextInt();
        final int ME = NumberOfPlayers - 1;
        Lab lab = new Lab(HEIGHT, WIDTH, NumberOfPlayers);

        double lastX = 0;
        double lastY = 0;

        // game loop
        while (true) {
            String upString = in.next();
            String rightString = in.next();
            String downString = in.next();
            String leftString = in.next();

            double[] x = new double[NumberOfPlayers];
            double[] y = new double[NumberOfPlayers];
            double [] distance = new double[NumberOfPlayers-1];

            // считываем координаты игроков
            for (int i = 0; i < NumberOfPlayers; i++) {
                int fifthInput = in.nextInt();
                int sixthInput = in.nextInt();
                lab.position(fifthInput, sixthInput, i);
                x[i] = fifthInput;
                y[i] = sixthInput;
            }

            // считает расстояния до противников
            double min = 100000000;
            int nearestEnemy = 0;
            for (int i = 0; i < NumberOfPlayers-1; i++) {
                distance[i] = getD(x[ME], y[ME], x[i], y[i]);
                if (distance[i] < min){
                    min = distance[i];
                    nearestEnemy = i;
                }
            }

            double nearX = x[nearestEnemy];
            double nearY = y[nearestEnemy];
            double myX = x[ME];
            double myY = y[ME];

            boolean isNearestX = false;
            boolean isNearestY = false;

            if (Math.abs(nearX - myX) < Math.abs(nearY - myY)){
                isNearestX = true;
            } else if (Math.abs(nearX - myX) > Math.abs(nearY - myY)) {
                isNearestY = true;
            } else {
                isNearestX = true;
                isNearestY = true;
            }

            System.err.println("Distance:" + Arrays.toString(distance));
            System.err.println(isNearestX + " " + isNearestY + " " + (nearestEnemy+1));
            System.err.println(Arrays.toString(x));
            System.err.println(Arrays.toString(y));
            System.err.println("  " + upString);
            System.err.println(leftString + " O " + rightString );
            System.err.println("  " + downString);

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


            if (isNearestX && myX - nearX >= 0){
                if(isRigthAllowed){
                    goRight();
                    
                } else {
                    if (isLeftAllowed && myX - nearX == 0){
                        goLeft();
                    } else if (myY - nearY >= 0){
                        if(isDownAllowed && myY + 1 != lastY){
                            goDown();
                        } else if(isDownAllowed && myY - nearY <= 10){
                            goDown();
                        } else if (isUpAllowed && myY - 1 != lastY){
                            goUp();
                        } else if (isUpAllowed && myY - nearY >= -6){
                            goUp();
                        } else {
                            goLeft();
                        }

                    } else {
                        if (isUpAllowed && myY - 1 != lastY){
                            goUp();
                        }  else if (isUpAllowed && myY - nearY <= 10){
                            goUp();
                        } else if(isDownAllowed && myY + 1 != lastY){
                            goDown();
                        } else if(isDownAllowed && myY - nearY >= -6){
                            goDown();
                        } else {
                            goLeft();
                        }
                    }
                }
            } else if (isNearestX && myX - nearX < 0){
                if (isLeftAllowed) {
                    goLeft();

                } else {
                    if (myY - nearY >= 0){
                        if(isDownAllowed && myY + 1 != lastY){
                            goDown();
                        } else if(isDownAllowed && myY - nearY <= 10){
                            goDown();
                        } else if (isUpAllowed && myY - 1 != lastY){
                            goUp();
                        }  else if (isUpAllowed && myY - nearY >= -6){
                            goUp();
                        } else {
                            goRight();

                        }
                    } else {
                        if (isUpAllowed && myY - 1 != lastY){
                            goUp();
                        }  else if (isUpAllowed && myY - nearY <=10){
                            goUp();
                        } else if(isDownAllowed && myY + 1 != lastY){
                            goDown();
                        } else if(isDownAllowed && myY - nearY >= -6){
                            goDown();
                        } else {
                            goRight();
                        }
                    }
                }
            } else if (isNearestY && myY - nearY >= 0){
                if(isDownAllowed){
                    goDown();

                } else {
                    if (isUpAllowed && myY - nearY == 0){
                        goUp();
                    } else if (myX - nearX >= 0){
                        if(isRigthAllowed && myX + 1 != lastX){
                            goRight();
                        } else  if(isRigthAllowed && myX - nearX <= 10){
                            goRight();
                        } else if (isLeftAllowed && myX - 1 != lastX){
                            goLeft();
                        }  else if (isLeftAllowed && myX - nearX >= -6){
                            goLeft();
                        }  else {
                            goUp();

                        }
                    } else {
                        if (isLeftAllowed && myX - 1 != lastX){
                            goLeft();
                        } else if (isLeftAllowed && myX - nearX >= -6){
                            goLeft();
                        } else  if(isRigthAllowed && myX + 1 != lastX){
                            goRight();
                        } else  if(isRigthAllowed && myX - nearX <= 10){
                            goRight();
                        } else {
                            goUp();
                        }
                    }
                }
            } else if (isNearestY && myY - nearY < 0){
                if(isUpAllowed){
                    goUp();

                } else {
                    if (myX - nearX >= 0){
                        if(isRigthAllowed && myX + 1 != lastX){
                            goRight();
                        } else  if(isRigthAllowed && myX - nearX <= 10){
                            goRight();
                        } else if (isLeftAllowed && myX - 1 != lastX){
                            goLeft();
                        } else if (isLeftAllowed && myX - nearX >= -6){
                            goLeft();
                        } else {
                            goDown();
                        }

                    } else {
                        if (isLeftAllowed && myX - 1 != lastX){
                            goLeft();
                        } else if (isLeftAllowed && myX - nearX >= -6){
                            goLeft();
                        } else  if(isRigthAllowed && myX + 1 != lastX){
                            goRight();
                        } else  if(isRigthAllowed && myX - nearX <= 10){
                            goRight();
                        } else {
                            goDown();
                        }
                    }
                }
            }
            lastX = myX;
            lastY = myY;
        }

    }
    public static double getD(double x1, double y1, double x2, double y2){
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }
}



class Lab {
    enum CellState {UNKNOWN, ALLOWED, NOT_ALLOWED}

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
    private int players, visitedCount, turns;
    private int[][] coords;  // координаты персонажей
    private Cell[][] matrix;

    public Lab(int y, int x, int players) {
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

        this.players = players;
        coords = new int[players][2];
    }

    public void position(int y, int x, int player) {
        matrix[y][x].setCellState(CellState.ALLOWED);
        if (player == players - 1) {
            turns++;
            if (!matrix[y][x].isVisited()) {
                matrix[y][x].setVisited(true);
                visitedCount++;
            }
        }
        coords[player] = new int[]{y, x};
    }

    public void setAllowance(Directions direction, boolean isAllowed) {
        // TODO разобраться с координатной   системой
        int xOffset = 0;
        int yOffset = 0;
        switch (direction) {
            case UP:
                xOffset = -1;  // TODO почему не y - 1?
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
            allow(coords[players-1][0] + yOffset, coords[players-1][1] + xOffset);
        }
        else {
            deny(coords[players-1][0] + yOffset, coords[players-1][1] + xOffset);
        }
    }

    public void allowLeft() {
        allow(coords[players-1][0] - 1, coords[players-1][1]);
    }
    public void allowRight() {
        allow(coords[players-1][0] + 1, coords[players-1][1]);
    }
    public void allowUp() {
        allow(coords[players-1][0], coords[players-1][1] - 1);
    }
    public void allowDown() {
        allow(coords[players-1][0], coords[players-1][1] + 1);
    }

    public void denyLeft() {
        deny(coords[players-1][0] - 1, coords[players-1][1]);
    }
    public void denyRight() {
        deny(coords[players-1][0] + 1, coords[players-1][1]);
    }
    public void denyUp() {
        deny(coords[players-1][0], coords[players-1][1] - 1);
    }
    public void denyDown() {
        deny(coords[players-1][0], coords[players-1][1] + 1);
    }

    private void deny(int y, int x) {
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
            joter:
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

        for (int i = 0; i < players; i++) {
            int start, end;
            start = (height + 1)*2*coords[i][1] + coords[i][0]*2;
            end = start + 2;
            switch (players - i - 1) {
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

enum Directions {UP, DOWN, LEFT, RIGHT}
