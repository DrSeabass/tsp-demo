package referenceSols;

import tsp.*;
import java.util.Collections.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.util.Random;

public class HillClimbingGreedyStart extends HillClimbing{

    public HillClimbingGreedyStart(Instance i, Tspdemo d){
        super(i,d);
        DFSChildOrdering primer = new DFSChildOrdering(i, d);
        while(primer.getInc() == null){
            primer.step();
        }
        System.out.println("Got a solution to start from.");
        Solution s = primer.getInc();
        this.incumbent = s;
        for(int j = 0; j < this.numCities; j++){
            this.sol.set(j, new Integer(this.incumbent.order[j]));
        }
    }

    public static void main(String[] argv){
        Instance i = Instance.fromFile(argv[0]);
        Tspdemo demo = new Tspdemo(i);
        HillClimbingGreedyStart search = new HillClimbingGreedyStart(i,demo);
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
