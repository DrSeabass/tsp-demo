package tsp;
public class Bounds {
    public float maxX = Float.MAX_VALUE;
    public float maxY = Float.MAX_VALUE;
    public float minX = Float.MIN_VALUE;
    public float minY = Float.MIN_VALUE;

    public Bounds(float maxX, float maxY, float minX, float minY){
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;
        System.out.println("maxX: " + this.maxX + " maxY: " + this.maxY);
        System.out.println("minX: " + this.minX + " minY: " + this.minY);
    }
}
