import javax.swing.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.awt.*;
public class LineSegment  extends JPanel {
    private final Point p;
    private final Point q;
    private Line2D line2D;
    ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

public void add(LineSegment lineSegment){
    lineSegments.add(lineSegment);
}

    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new NullPointerException("argument is null");
        }

        this.p = p;
        this.q = q;
        line2D = new Line2D.Double(p.getX(),p.getY(),q.getX(),q.getY());


    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (LineSegment lineSegment: lineSegments) {

            g2.draw(line2D);
        }
    }

public void ComputeSlope(){
    System.out.println("Slope is: " + (q.getY()-p.getY())/(q.getX()-p.getX()));

}


public void CompareSegmentes(LineSegment first, LineSegment second){
    if(first.getX()==second.getX()&&first.getY()==second.getY())
        System.out.println("Segments are equal: "+first.getX() +" ,"+first.getY() );
     else
         System.out.println("Segments are not equal.");
}


}

