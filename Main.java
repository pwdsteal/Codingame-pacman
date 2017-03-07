import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        
        Scanner in = new Scanner(System.in);
        int firstInitInput = in.nextInt();
        int secondInitInput = in.nextInt();
        int thirdInitInput = in.nextInt();
        Lab lab = new Lab(secondInitInput, firstInitInput, thirdInitInput);
        
        int count = 0;
        double lastX = 0;
        double lastY = 0;

        // game loop
        while (true) {
           
           
            String firstInput = in.next();
            String secondInput = in.next();
            String thirdInput = in.next();
            String fourthInput = in.next();
            double[] x = new double[thirdInitInput];
            double[] y = new double[thirdInitInput];
            double [] distance = new double[thirdInitInput-1];
            for (int i = 0; i < thirdInitInput; i++) {
                int fifthInput = in.nextInt();
                int sixthInput = in.nextInt();
                lab.position(fifthInput, sixthInput, i);
                x[i] = fifthInput;
                y[i] = sixthInput;
            }
            double min = 100000000;
            int index = 0;
             for (int i = 0; i < thirdInitInput-1; i++) {
                distance[i] = getD(x[thirdInitInput-1], y [thirdInitInput-1], x[i], y[i]);
                if (distance[i] < min){
                 min = distance[i];
                 index = i;
                }
            }
            
            double nearX = x[index];
            double nearY = y[index];
            double myX = x[thirdInitInput-1];
            double myY = y[thirdInitInput-1];
            
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
          
             System.err.println(Arrays.toString(distance));
            System.err.println(nearestX + " " + nearestY + " " + (index+1));
             System.err.println(Arrays.toString(x));
             System.err.println(Arrays.toString(y));
             System.err.println("  " + firstInput);
              System.err.println(fourthInput + " O " + secondInput );
                System.err.println("  " + thirdInput);
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            boolean up = false;
            boolean down = false;
            boolean left = false;
            boolean right = false;
            if (firstInput.equals("_")) {
                up = true; 
                lab.allowUp();
            } else {
                lab.denyUp();
            }
            
            if (secondInput.equals("_")){
                right = true; 
                lab.allowRight();
            } else {
                lab.denyRight();
            }
            
            if (fourthInput.equals("_")){
                left = true;
                lab.allowLeft();
            } else {
                lab.denyLeft();
            }
            if (thirdInput.equals("_")){
                down = true;
                lab.allowDown();
            } else {
                lab.denyDown();
            }
             System.err.println(lab.getVisited());
            System.err.println(lab.toString());
            
            
            // if ( Math.abs(myX - nearX) == 0 && Math.abs(myY - nearY) == 1){
            //     System.out.println("B"); 
            // } else if ( Math.abs(myX - nearX) == 1 && Math.abs(myY - nearY) == 0){
            //     System.out.println("B"); 
            // } else
            
           double p = 3;
           double m = -3;
            
            if ((nearestX  || (nearestX && nearestX))&& myX - nearX >= 0){
                if(right){
                System.out.println("A"); 
              
                } else {
                    if (left && myX - nearX == 0){
                         System.out.println("E"); 
                    } else if (myY - nearY >= 0){
                        if(down && myY + 1 != lastY){
                         System.out.println("D");
                        } else if(down && myY - nearY <= 10){
                         System.out.println("D");
                        } else if (up && myY - 1 != lastY){
                             System.out.println("C");
                        } else if (up && myY - nearY >= -6){
                             System.out.println("C");
                        } else {
                            System.out.println("E");
                        }
                        
                    } else {
                       if (up && myY - 1 != lastY){
                        System.out.println("C");
                        }  else if (up && myY - nearY <= 10){
                             System.out.println("C");
                        } else if(down && myY + 1 != lastY){
                         System.out.println("D");   
                        } else if(down && myY - nearY >= -6){
                         System.out.println("D");
                        } else {
                         System.out.println("E");   
                        }
                    }
                }
            } else if ((nearestX  || (nearestX && nearestX)) && myX - nearX < 0){
              if (left) {
                System.out.println("E"); 
                
                } else { 
                     if (myY - nearY >= 0){
                       if(down && myY + 1 != lastY){
                         System.out.println("D");
                        } else if(down && myY - nearY <= 10){
                         System.out.println("D");
                        } else if (up && myY - 1 != lastY){
                             System.out.println("C");
                        }  else if (up && myY - nearY >= -6){
                             System.out.println("C");
                        } else {
                            System.out.println("A");
                        
                        }
                    } else {
                        if (up && myY - 1 != lastY){
                        System.out.println("C");
                        }  else if (up && myY - nearY <=10){
                             System.out.println("C");
                        } else if(down && myY + 1 != lastY){
                         System.out.println("D");   
                        } else if(down && myY - nearY >= -6){
                         System.out.println("D");
                        } else {
                         System.out.println("A");   
                        }
                    }
                }
            } else if ((nearestY  || (nearestX && nearestX)) && myY - nearY >= 0){
              if(down){
                System.out.println("D"); 
                
              } else {
                   if (up && myY - nearY == 0){
                         System.out.println("C"); 
                    } else if (myX - nearX >= 0){
                        if(right && myX + 1 != lastX){
                         System.out.println("A");
                        } else  if(right && myX - nearX <= 10){
                         System.out.println("A");
                        } else if (left && myX - 1 != lastX){
                             System.out.println("E");
                        }  else if (left && myX - nearX >= -6){
                             System.out.println("E");
                        }  else {
                            System.out.println("C");
                        
                        }
                    } else {
                       if (left && myX - 1 != lastX){
                        System.out.println("E");
                        } else if (left && myX - nearX >= -6){
                          System.out.println("E");
                        } else  if(right && myX + 1 != lastX){
                         System.out.println("A");   
                        } else  if(right && myX - nearX <= 10){
                         System.out.println("A");
                        } else {
                         System.out.println("C");   
                        }
                    }
                }
            } else if ((nearestY  || (nearestX && nearestX)) && myY - nearY < 0){
              if(up){
                System.out.println("C"); 
            
              } else {
                    if (myX - nearX >= 0){
                         if(right && myX + 1 != lastX){
                         System.out.println("A");
                        } else  if(right && myX - nearX <= 10){
                         System.out.println("A");
                        } else if (left && myX - 1 != lastX){
                             System.out.println("E");
                        } else if (left && myX - nearX >= -6){
                          System.out.println("E");
                        } else {
                            System.out.println("D");
                        }
                        
                    } else {
                       if (left && myX - 1 != lastX){
                        System.out.println("E");
                        } else if (left && myX - nearX >= -6){
                          System.out.println("E");
                        } else  if(right && myX + 1 != lastX){
                         System.out.println("A");   
                        } else  if(right && myX - nearX <= 10){
                         System.out.println("A");
                        } else {
                         System.out.println("D");   
                        }
                    }
                }
            }
             lastX = myX;
             lastY = myY;
              System.err.println("final");
    }
    
    
    
}
public static double getD(double x1, double y1, double x2, double y2){
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }
}

class Lab {
    class Cell{
        private boolean isVisited;
        private int allowed;

        public Cell(boolean isVisited, int allowed) {
            this.isVisited = isVisited;
            this.allowed = allowed;
        }

        public boolean isVisited() {
            return isVisited;
        }

        public void setVisited(boolean visited) {
            isVisited = visited;
        }

        public int getAllowed() {
            return allowed;
        }

        public void setAllowed(int allowed) {
            this.allowed = allowed;
        }
    }
    private int dimY, dimX;
    private int players, visited, turns;
    private int[][] coords;
    private Cell[][] matrix;

    public Lab(int y, int x, int players) {
        if (y <=0 || x <= 0) {
            throw new IllegalArgumentException();
        }
        dimY = y;
        dimX = x;
        matrix = new Cell[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                matrix[i][j] = new Cell(false, 0);
            }
        }

        this.players = players;
        coords = new int[players][2];
    }

    public void position(int y, int x, int player) {
        matrix[y][x].setAllowed(1);
        if (player == players - 1) {
            turns++;
            if (!matrix[y][x].isVisited()) {
                matrix[y][x].setVisited(true);
                visited++;
            }
        }
        coords[player] = new int[]{y, x};
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
        matrix[y][x].setAllowed(-1);
    }

    private void allow(int y, int x) {
        if (y < dimY && x < dimX && y >= 0 && x >= 0)
        matrix[y][x].setAllowed(1);
    }
    
    public int getVisited() {
        return visited;
    }

    public int getTurns() {
        return turns;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < dimX; i++) {
            joter:
            for (int j = 0; j < dimY; j++) {
                if (matrix[j][i].getAllowed() == -1) {
                    b.append(" X");
                } else if (matrix[j][i].getAllowed() == 0) {
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
            start = (dimY + 1)*2*coords[i][1] + coords[i][0]*2;
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
