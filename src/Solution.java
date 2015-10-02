// represents a solution to the tsp problem

public class Solution{
    public int[] order; // Which city to visit 0th, 1st, snd, etc.
    public float cost = Float.MAX_VALUE;

    public Solution(int[] tour, float cost){
        this.order = tour;
        this.cost = cost;
    }

    public boolean better(Solution s){
        if(s == null){
            return true;
        }else{
            return this.cost < s.cost;
        }
    }

    public boolean better(float c){
        return this.cost < c;
    }
}
