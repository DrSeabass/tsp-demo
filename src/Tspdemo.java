public class Tspdemo {

    public static void main(String[] argv){
        System.out.println("Hello World");
        Instance i = Instance.fromFile(argv[0]);
        i.debugPrint();
    }
}
