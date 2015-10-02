// Read and represent an instance of the TSP

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Math;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Instance {
    private float [][] distances;
    private float [][] coords;
    private String name;

    public static Instance fromFile(String path){
        String line;
        String name = null;
        String [] pLine;
        int ind;
        System.out.format("Loading %s\n", path);
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(path));
        }catch(FileNotFoundException fnf){
            System.err.println(path + " was invalid.");
            return null;
        }
        float [][] cs = null;
        boolean readingCoords = false;
        try{
            while ((line = br.readLine()) != null){
                if (!readingCoords) {
                    if (line.startsWith("NAME:")){
                        pLine = line.split(" ");
                        name = pLine[1];
                    } else if (line.startsWith("DIMENSION:")){
                        pLine = line.split(" ");
                        int sz = Integer.parseInt(pLine[1]);
                        cs = new float[sz][2]; // ind * (x or y)
                    } else if (line.startsWith("NODE_COORD_SECTION")){
                        readingCoords = true;
                    }
                } else {
                    pLine = line.split(" ");
                    ind = Integer.parseInt(pLine[0]);
                    ind --; // 1-based indexing
                    cs[ind][0] = Float.valueOf(pLine[1]);
                    cs[ind][1] = Float.valueOf(pLine[2]);
                }
            }
        }catch(IOException ioe){
            return null;
        }
        // cs is filled in, make an instance.
        System.out.println("Coords read, constructing instance");
        return new Instance(cs, name);
    }

    public Instance (float [][] coords, String name){
        float dx = 0;
        float dy = 0;
        double d = 0;
        this.name = name;
        this.coords = coords;
        this.distances = new float[coords.length][];
        for (int i = coords.length - 1; i >= 0; i--){
            this.distances[i] = new float[i];
            for(int j = 0; j < i; j++){
                dx = coords[i][0] - coords[i][0];
                dy = coords[j][1] - coords[j][1];
                d = Math.sqrt(dx * dx + dy * dy);
                this.distances[i][j] = (float)d;
            }
        }
    }

    public void debugPrint(){
        System.out.println(this.name);
        System.out.println(this.coords.length + " cities");
    }
}
