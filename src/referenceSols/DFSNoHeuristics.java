package referenceSols;

import tsp.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;


public class DFSNoHeuristics{
    private int numCities;
    private int stepCount;
    private Tspdemo display;
    private Solution incumbent;
    private Instance inst;

    private boolean[] visited;
    private float cost;
    private int depth;
    private Stack<State> sol;


    public DFSNoHeuristics(Instance i, Tspdemo d){
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
        State root = new State(0,0);
        this.sol.push(root);
    }

    private void updateIncumbent(){
        float finalCost = this.cost + this.inst.distance(0, this.sol.peek().city);
        if(this.incumbent == null || !this.incumbent.better(finalCost)){
            this.debugPrint();
            int[] cities = new int[this.numCities];
            int i = 0;
            for(State s : this.sol){
                cities[i] = s.city;
                i++;
            }
            this.incumbent = new Solution(cities, this.cost);
            this.display.updateSolution(this.incumbent);
        }
    }

    private void moveHead(){
        State tip = this.sol.pop();
        int tc = tip.city;
        tip.city = this.nextUnvisited(tc);
        this.visited[tc] = false;
        this.cost -= tip.legCost;
        if(tip.city > 0){
            this.visited[tip.city] = true;
            float c = this.inst.distance(this.sol.peek().city, tip.city);
            tip.legCost = c;
            this.cost += c;
        }
        this.sol.push(tip);
    }

    private boolean back(){
        State head = this.sol.pop();
        if(head.city > 0){
            this.visited[head.city] = false;
            this.cost -= head.legCost;
        }
        this.depth--;
        if(depth > 0){
            // we need to move the tip on to the next possibility.
            this.moveHead();
            return true;
        }else{
            return false;
        }
    }

    private void debugPrint(){
        float c = 0;
        for(State s : this.sol){
            System.out.print(" " + s.city);
            c += s.legCost;
        }
        System.out.println(" of cost " + this.cost + " Vs. " + c);
    }

    private boolean step(){
        this.stepCount ++;
        //        if(this.stepCount % 1000000 == 0){
        //            System.out.println("Took " + this.stepCount + " search steps");
        //        }
        if (this.depth == this.numCities - 1){ // stack is a solution
            this.updateIncumbent();
            return this.back();
        }else{
            State head = this.sol.peek();
            if(head.city >= this.numCities || head.city < 0){
                return this.back();
            }else if (this.incumbent != null && this.incumbent.better(this.cost)){
                //System.out.println("Pruning on incumbent cost: " + this.cost);
                return this.back();
            }else{
                // going forward
                int nextCity = nextUnvisited(0);
                float legCost = this.inst.distance(head.city,nextCity);
                State next = new State(nextCity,legCost);
                this.sol.push(next);
                this.depth++;
                this.visited[nextCity] = true;
                this.cost += legCost;
                return true;
            }
        }
    }

    private int nextUnvisited(int start){
        for(int i = start; i < this.numCities; i++){
            if(!this.visited[i]){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] argv){
        Instance i = Instance.fromFile(argv[0]);
        Tspdemo demo = new Tspdemo(i);
        DFSNoHeuristics search = new DFSNoHeuristics(i,demo);
        JFrame app = new JFrame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(demo);
        app.setSize(800,600);
        app.setVisible(true);
        while(search.step());
        System.out.println("Complete.");
    }
}
