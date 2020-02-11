import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args)
    {
        JFrame framer=new JFrame("Centered Circle");

        Point zape = new Point(260,280 );
        framer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framer.setSize(1200,1200);
        framer.setVisible(true);



        Point a = new Point(33,88);
        Point za = new Point(443,11);
        LineSegment as = new LineSegment(a,za);
        Point da = new Point(0,410);
        Point gab = new Point(32,87);
        LineSegment dabz = new LineSegment(da,gab);
        LineSegment dabz1 = new LineSegment(zape,da);

        dabz.add(dabz);
        as.add(as);
        dabz1.add(dabz1);
        framer.add(dabz);
        dabz.ComputeSlope();
        dabz.CompareSegmentes(as,dabz1);
    }
}
