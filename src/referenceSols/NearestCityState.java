package referenceSols;

import tsp.Instance;

public class NearestCityState{
    public int city;     // me
    public int next;
    public float dist;
    private Instance inst;
    private boolean[] visited;

    public NearestCityState(int me, boolean[] visited, Instance i){
        this.city = me;
        this.inst = i;
        this.dist = Float.MIN_VALUE;
        this.next = 0;
        this.visited = visited;
    }

    public int getNext(){
        if(next >= this.visited.length){
            return -1;
        }else{
            next++;
            float smallestBigger = Float.MAX_VALUE;
            int toRet = -1;
            for(int ind = 0; ind < this.visited.length; ind++){
                if(!this.visited[ind]){
                    float d = this.inst.distance(this.city, ind);
                    if(d > this.dist && d < smallestBigger){
                        smallestBigger = d;
                        toRet = ind;
                    }
                }
            }
            this.dist = smallestBigger;
            return toRet;
        }
    }
}
