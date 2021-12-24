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
        } else {
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
    public boolean updateNonObstacle(String x, String y, String squareName, String facing){
        //Mark the obstacle square with -1
        return true;
    }

    @TERM
    public String getDirection(String x, String y){
        //Calculate the direction that the bot should move to
        
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

    