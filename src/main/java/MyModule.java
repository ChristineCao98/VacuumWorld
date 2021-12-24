import astra.core.Module;
import java.util.Arrays;
import astra.core.Agent;

public class MyModule extends Module {
    static int[][] visited=new int[8][16];
    int lastRow = -1;
    int lastCol = -1;
    int lastVal = 0;

    public void setAgent(Agent agent) {
        super.setAgent(agent);
        // initialisation: without perceptions, all elements in the grid are seen as obstacles
        for (int[] array : visited) {
            Arrays.fill(array, -1);
        }
        printGrid();
    }

    @ACTION
    public boolean updateStep(String row, String col, String id){
        //Update the time that the square is visited

        int ROW = Integer.parseInt(row);
        int COL = Integer.parseInt(col);
        int ID = Integer.parseInt(id);

        //First recover the value of the last square
        if(lastRow != -1){
            visited[lastRow][lastCol] = lastVal;
        } 
        
        if (visited[ROW][COL] == -1) {
            visited[ROW][COL] = 0;
        }

        //Update the value of the current square
        lastVal = visited[ROW][COL] + 1;

        //Save the value of the current square and turn it to the negative of its ID value
        lastRow = ROW;
        lastCol = COL;
        visited[ROW][COL] = -ID;
        printGrid();
        return true;
    }

    @ACTION
    public boolean updateNonObstacle(String row, String col, String squareName, String facing){
        //Mark the non bstacle squares with 0
        int ROW = Integer.parseInt(row);
        int COL = Integer.parseInt(col);



        return true;
    }

    @TERM
    public String getDirection(String row, String col){
        int ROW = Integer.parseInt(row);
        int COL = Integer.parseInt(col);

        //Move to the square least visited
        String direction = "";
        int minStep = Integer.MAX_VALUE;

        if (COL != 0) {
            int west = visited[ROW][COL - 1];
            if (west >= 0 && west < minStep) {
                direction = "west";
                minStep = west;
            }
        }

        if (ROW != 0) {
            int north = visited[ROW - 1][COL];
            if (north >= 0 && north < minStep) {
                direction = "north";
                minStep = north;
            }
        }

        if (COL < visited[0].length - 1) {
            int east = visited[ROW][COL + 1];
            if (east >= 0 && east < minStep) {
                direction = "east";
                minStep = east;
            }
    
        }

        if (ROW < visited.length - 1) {
            int south = visited[ROW + 1][COL];
            if (south >= 0 && south < minStep) {
                direction = "south";
                minStep = south;
            }
        }
        // TO DO: use this return, after updateNonObstacle() is done
        // return direction;
        return "back";
    }

    public void printGrid() {
        for (int[] row : visited) {
            System.out.println();
            for (int c : row) {
                System.out.print(Integer.toString(c) + " ");
            }
        }
    }
}

    