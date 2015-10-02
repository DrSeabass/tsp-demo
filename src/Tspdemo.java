import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.lang.Math;

public class Tspdemo extends JPanel{
    private Solution sol;
    private Instance cities;
    private Bounds bbox;

    public Tspdemo(Instance i){
        super();
        this.setBackground(Color.WHITE);
        this.cities = i;
        this.bbox = this.cities.getBounds();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        float sWidth = (float)(this.getWidth() - 10);
        float sHeight = (float)(this.getHeight() - 10);
        float mWidth = bbox.maxX - bbox.minX;
        float mHeight = bbox.maxY - bbox.minY;
        float scaleX = sWidth / mWidth;
        float scaleY = sHeight / mHeight;
        System.out.println("ScaleX: " + scaleX);
        System.out.println("ScaleY: " + scaleY) ;
        int x;
        int y;

        float[][] coords = this.cities.getCoords();

        System.out.println("Painting cities...");
        System.out.println("sWidth: " + sWidth + " sHeight: " + sHeight);
        g.setColor(Color.RED);
        for(int i = 0; i < coords.length; i++){
            x = (int)(sWidth - Math.floor((coords[i][0] - this.bbox.minX) * scaleX));
            y = (int)(sHeight - Math.floor((coords[i][1] - this.bbox.minY) * scaleY));
            g.fillRect(x,y,2,2);
        }
        if (this.sol != null){
            g.drawString("Tour Cost: " + this.sol.cost, 0, (int) (sHeight - 10));
            // paint the tour here as well.
            int j = 0;
            int sx = -1;
            int sy = -1;
            int ex = -1;
            int ey = -1;
            g.setColor(Color.BLUE);
            for(int i = 0; i < coords.length - 1; i++){
                if (sx < 0){
                    sx = (int)(sWidth - Math.floor((coords[i][0] - this.bbox.minX) * scaleX));
                    sy = (int)(sHeight - Math.floor((coords[i][1] - this.bbox.minY) * scaleY));
                }else{
                    sx = ex;
                    sy = ey;
                    ex = (int)(sWidth - Math.floor((coords[i][0] - this.bbox.minX) * scaleX));
                    ey = (int)(sHeight - Math.floor((coords[i][1] - this.bbox.minY) * scaleY));
                }
                if (sx >= 0 && ex >= 0){
                    g.drawLine(sx,sy,ex,ey);
                }
            }
        }else{
            g.drawString("No Tour Found.",0, (int) (sHeight - 10));
        }
    }

    public void updateSolution(Solution s){
        if (s.better(this.sol)){
            this.sol = s;
            this.repaint();
        }
    }

    public static void main(String[] argv){
        Instance i = Instance.fromFile(argv[0]);
        Tspdemo demo = new Tspdemo(i);
        JFrame app = new JFrame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(demo);
        app.setSize(800,600);
        app.setVisible(true);
    }
}
