import java.util.Collections.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;

public class RandomWalk{
    private Instance inst;
    private List<Integer> sol;
    private Solution incumbent;
    private Tspdemo display;
    private int numCities;
    int stepCount = 0;

    public RandomWalk(Instance i, Tspdemo d){
        this.inst = i;
        this.display = d;
        this.numCities = this.inst.getCoords().length;
        this.sol = new ArrayList<Integer>(this.numCities);
        System.out.println("Random walk on " + this.numCities + " cities");
        for(int j = 0; j < this.inst.getCoords().length; j++){
            this.sol.add(j, new Integer(j));
        }
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

    private void step(){
        java.util.Collections.shuffle(this.sol);
        this.updateIncumbent();
        this.stepCount ++;
        if((this.stepCount % 10000) == 0){
            System.out.println("Took " + this.stepCount + " steps.");
        }
    }

    public static void main(String[] argv){
        Instance i = Instance.fromFile(argv[0]);
        Tspdemo demo = new Tspdemo(i);
        RandomWalk search = new RandomWalk(i,demo);
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
