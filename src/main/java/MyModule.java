import astra.core.Module;
import astra.core.Agent;

public class MyModule extends Module {
    static int[][] visited=new int[16][8];
    int lastx = -1;
    int lasty = -1;
    int lastVal = 0;
    public void setAgent(Agent agent) {
        super.setAgent(agent);
    }

    @ACTION
    public boolean updateStep(String x, String y, String id){
        //Update the time that the square is visited

        //First recover the value of the last square
        if(lastx != -1){
            visited[lastx][lasty] = lastVal;
        }
        int X = Integer.parseInt(x);
        int Y = Integer.parseInt(y);
        int ID = Integer.parseInt(id);

        //Update the value of the current square
        lastVal = visited[X][Y] + 1;

        //Save the value of the current square and turn it to the negative of its ID value
        lastx = X;
        lasty = Y;
        visited[X][Y] = -ID;
        return true;
    }

    @ACTION
    public boolean updateObstacle(String x, String y, String squareName, String facing){
        //Mark the obstacle square with -1
        return true;
    }

    @TERM
    public String getDirection(String x, String y){
        //Calculate the direction that the bot should move to 
        return "back";
    }
}

    