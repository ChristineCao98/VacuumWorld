import astra.core.Module;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import astra.core.Agent;

public class MyModule extends Module {
    static int[][] visited = new int[8][16];
    static int[] row_plus = new int[]{-1, -1, -1, 0, 1, 1, 1, 0};
    static int[] col_plus = new int[]{-1, 0, 1, 1, 1, 0, -1, -1};
    int lastRow = -1;
    int lastCol = -1;
    int lastVal = 0;

    static Map<String, Integer> FACING = new HashMap<String, Integer>(){
        {
            put("north", 1);
            put("east", 3);
            put("south", 5);
            put("west", 7);
        }
    };

    static Map<String, Integer> SQUARENAME = new HashMap<String, Integer>(){
        {
            put("forward", 0);
            put("forwardRight", 1);
            put("right", 2);
            put("left", 6);
            put("forwardLeft", 7);
        }
    };

    //Use the static statement to avoid repetitive initialisation of the grid
    static{
        // initialisation: without perceptions, all elements in the grid are seen as obstacles
        for (int[] array : visited) {
            Arrays.fill(array, -1);
        }
    }

    public void setAgent(Agent agent) {
        super.setAgent(agent);
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
        //Mark the unvisited non-obstacle squares with 0

        int ROW = Integer.parseInt(row);
        int COL = Integer.parseInt(col);

        int index = (SQUARENAME.get(squareName) + FACING.get(facing)) % 8;
        int newROW = ROW + row_plus[index];
        int newCOL = COL + col_plus[index];

        //If the non-obstacle square is within the boundary and is not visited before, change its value to 0
        if(newROW >=0 && newCOL >=0 && newROW < 8 && newCOL <16){
            if(visited[newROW][newCOL] < 0) visited[newROW][newCOL] = 0;
        }

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
        return direction;
    }

    public void printGrid() {
        for (int[] row : visited) {
            System.out.println();
            for (int c : row) {
                if (c >= 0)
                System.out.print(" ");
                System.out.print(Integer.toString(c) + " ");
            }
        }
        System.out.println();
    }
}

    