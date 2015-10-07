package referenceSols;

import tsp.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.util.Arrays;


public class DFSChildOrdering{
    private int numCities;
    private int stepCount;
    private Tspdemo display;
    private Solution incumbent = null;
    private Instance inst;

    private boolean[] visited;
    private float cost;
    private int depth;
    private Stack<NearestCityState> sol;


    public DFSChildOrdering(Instance i, Tspdemo d){
        this.inst = i;
        this.display = d;
        this.numCities = this.inst.getCoords().length;
        this.visited = new boolean[this.numCities];
        for(int j = 0; j < this.numCities; j++){
            this.visited[j] = false;
        }

        // set up initial state
        this.cost = 0;
        this.visited[0] = true;
        this.depth = 0;
        this.stepCount = 0;
        this.sol = new Stack();
        NearestCityState root = new NearestCityState(0, Arrays.copyOf(this.visited, this.numCities), this.inst);
        this.sol.push(root);
    }

    public Solution getInc(){
        return this.incumbent;
    }

    protected void debugPrint(){
        float c = 0;
        for(NearestCityState s : this.sol){
            System.out.print(" " + s.city);
            c += s.dist;
        }
        System.out.println(" of cost " + this.cost + " Vs. " + c);
    }

    protected void updateCost(boolean isSol){
        int prevCity = this.sol.peek().city;
        if(isSol)
            prevCity = -1;
        float d = 0;
        for(NearestCityState s : this.sol){
            if(prevCity > 0)
                d += this.inst.distance(s.city, prevCity);
            prevCity = s.city;
        }
        this.cost = d;
    }


    protected void updateIncumbent(){
        this.updateCost(true);
        float finalCost = this.cost + this.inst.distance(0, this.sol.peek().city);
        if(this.incumbent == null || !this.incumbent.better(finalCost)){
            System.err.print("New incumbent: ");
            this.debugPrint();
            int[] cities = new int[this.numCities];
            int i = 0;
            for(NearestCityState s : this.sol){
                cities[i] = s.city;
                i++;
            }
            this.incumbent = new Solution(cities, finalCost);
            this.display.updateSolution(this.incumbent);
        }
    }

    protected boolean back(){
        NearestCityState head = this.sol.pop();
        this.depth --;
        this.visited[head.city] = false;
        if(depth > 0)
            return true;
        else
            return false;
    }

    protected boolean step(){
        this.stepCount ++;
        if (this.depth == this.numCities - 1){ // stack is a solution
            this.updateIncumbent();
            return this.back();
        }else{
            NearestCityState head = this.sol.peek();
            float arcCost = head.dist;
            this.cost -= arcCost;
            if (this.incumbent != null && this.incumbent.better(this.cost)){
                this.updateCost(false);
                return this.back();
            }else{
                int nextCity = head.getNext();
                if(nextCity == -1){
                    return this.back();
                }else{
                    this.visited[nextCity] = true;
                    NearestCityState next = new NearestCityState(nextCity, Arrays.copyOf(this.visited, this.numCities), this.inst);
                    this.cost += head.dist; // new arc cost gets added in.
                    this.depth++;
                    this.sol.push(next);
                    return true;
                }
            }
        }
    }


    public static void main(String[] argv){
        Instance i = Instance.fromFile(argv[0]);
        Tspdemo demo = new Tspdemo(i);
        DFSChildOrdering search = new DFSChildOrdering(i,demo);
        JFrame app = new JFrame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(demo);
        app.setSize(800,600);
        app.setVisible(true);
        while(search.step());
        System.out.println("Complete.");
    }

}
