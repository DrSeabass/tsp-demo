package referenceSols;

import tsp.*;
import java.util.Collections.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.util.Random;

public class HillClimbing{
    private Instance inst;
    private List<Integer> sol;
    private Solution incumbent;
    private Tspdemo display;
    private int numCities;
    private int stepCount = 0;
    private Random entropy;

    public HillClimbing(Instance i, Tspdemo d){
        this.inst = i;
        this.display = d;
        this.numCities = this.inst.getCoords().length;
        this.sol = new ArrayList<Integer>(this.numCities);
        for(int j = 0; j < this.inst.getCoords().length; j++){
            this.sol.add(j, new Integer(j));
        }
        this.entropy = new Random();
        this.restart();
    }

    private void updateIncumbent(){
        float cost = 0;
        cost = 0;
        int sCity = this.sol.get(this.numCities - 1).intValue();
        int nCity = -1;

        for(int i = 0; i < this.numCities; i++){
            nCity = this.sol.get(i).intValue();
            cost += this.inst.distance(sCity, nCity);
            sCity = nCity;
        }

        if (this.incumbent == null || !this.incumbent.better(cost)){
            System.out.println("found new tour of cost " + cost);
            int[] asSol = new int[this.sol.size()];
            for(int i = 0; i < this.numCities; i++){
                asSol[i] = this.sol.get(i).intValue();
            }
            this.incumbent = new Solution(asSol, cost);
            this.display.updateSolution(incumbent);
        }
    }

    private void restart(){
        java.util.Collections.shuffle(this.sol);
        this.updateIncumbent();
    }

    private void swap(int i, int j){
        Integer tmp = this.sol.get(i);
        this.sol.set(i, this.sol.get(j));
        this.sol.set(j, tmp);
    }

    private float getCost(){
        float cost = 0;
        int sCity = this.sol.get(this.numCities - 1).intValue();
        int nCity = -1;

        for(int i = 0; i < this.numCities; i++){
            nCity = this.sol.get(i).intValue();
            cost += this.inst.distance(sCity, nCity);
            sCity = nCity;
        }
        return cost;
    }

    private int prev(int ind){
        if (ind == 0)
            return this.numCities - 1;
        return ind - 1;
    }

    private int next(int ind){
        if (ind == this.numCities - 1)
            return 0;
        return ind + 1;
    }

    private float computeCost(float currentCost, int i, int j){
        float iCost = 0;
        float jCost = 0;
        float delta;
        iCost += this.inst.distance(this.sol.get(i).intValue(), this.sol.get(this.prev(i)).intValue());
        iCost += this.inst.distance(this.sol.get(i).intValue(), this.sol.get(this.next(i)).intValue());
        jCost += this.inst.distance(this.sol.get(j).intValue(), this.sol.get(this.prev(j)).intValue());
        jCost += this.inst.distance(this.sol.get(j).intValue(), this.sol.get(this.next(j)).intValue());
        delta = iCost + jCost;
        this.swap(i,j);
        iCost = 0;
        jCost = 0;
        iCost += this.inst.distance(this.sol.get(i).intValue(), this.sol.get(this.prev(i)).intValue());
        iCost += this.inst.distance(this.sol.get(i).intValue(), this.sol.get(this.next(i)).intValue());
        jCost += this.inst.distance(this.sol.get(j).intValue(), this.sol.get(this.prev(j)).intValue());
        jCost += this.inst.distance(this.sol.get(j).intValue(), this.sol.get(this.next(j)).intValue());
        this.swap(i,j);
        return currentCost + ((iCost + jCost) - delta);
    }

    private void step(){
        int besti = 0;
        int bestj = 0;
        float currentCost = this.getCost();
        float bestCost = currentCost;
        for(int i = 0; i < this.numCities; i++){
            for(int j = i+1; j < this.numCities; j++){
                if(j != i){
                //mutate sol
                    //compute cost
                    float c = this.computeCost(currentCost, i, j);
                    //record
                    if(bestCost - c > 10){
                        bestCost = c;
                        besti = i;
                        bestj = j;
                    }
                }
            }
            if (currentCost - bestCost > 10) break;
        }
        if (currentCost - bestCost > 10){
            // is the next best neighbor an improvement?
            this.swap(besti, bestj);
            this.updateIncumbent();
        }else{
            // nope, local minimum, restart
            System.out.println("Local maximam, restarting");
            this.restart();
        }
        this.stepCount ++;
        if((this.stepCount % 10000) == 0){
            System.out.println("Took " + this.stepCount + " steps.");
        }
    }

    public static void main(String[] argv){
        Instance i = Instance.fromFile(argv[0]);
        Tspdemo demo = new Tspdemo(i);
        HillClimbing search = new HillClimbing(i,demo);
        JFrame app = new JFrame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(demo);
        app.setSize(800,600);
        app.setVisible(true);
        while(true){
            search.step();
        }
    }
}
