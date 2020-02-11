import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;


public class Circle extends JPanel {
    private Ellipse2D.Double sphere;
 private Point center;
 private int radius;

 ArrayList<Circle> circles = new ArrayList<Circle>();

    public void add(Circle circle) {
        circles.add(circle);
    }
 public Circle(Point center, int radius) {

this.center = center;
 this.radius = radius;

     sphere = new Ellipse2D.Double(getCircleX(center),getCircleY(center),radius,radius);

}
public Circle(){

     Point center = new Point();
     radius =1;
    sphere = new Ellipse2D.Double(0,0,radius,radius);


}

/*
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLUE);
        g2.fill(sphere);
        g2.draw(sphere);

    }
*/



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Circle circle: circles) {
            Ellipse2D circle2D = new Ellipse2D.Double();
            circle2D.setFrameFromCenter(
                    circle.getCircleX(center),
                    circle.getCircleY(center),
                    circle.getCircleX(center) + circle.getRadius(),
                    circle.getCircleY(center) + circle.getRadius());
            g2.draw(circle2D);
        }
    }








    public double getRadius(){
     return radius;
 }

    public Point getCenter() {
        return center;
    }
public  double getCircleX(Point center){
    return center.getX();
}
    public  double getCircleY(Point center){
        return center.getY();
    }

    public void ConstainsPointInCircle(Point p , Circle c){

        if((p.getX() < c.getCircleX(c.getCenter())+(c.getRadius()/2))||p.getX() < (c.getCircleX(c.getCenter())-(c.getRadius()/2))&&(p.getY() < c.getCircleY(c.getCenter())+(c.getRadius()/2))||p.getY() < (c.getCircleY(c.getCenter())-(c.getRadius()/2))){
            System.out.println("Point with cordinates x:" + p.getX() + " y:"+p.getY() + "is contained in circle");

        }
          else
            System.out.println("Point with cordinates x:" + p.getX() + " y:"+p.getY()+ "is out of the circle");
 }

 public void CompareCircles(Circle c1 , Circle c2){
        if(c1.getCircleX(center) == c2.getCircleX(center)&& c1.getCircleY(center)==getCircleY(center) &&c1.getRadius()==c2.getRadius())
            System.out.println("Circles with cordinates: " + c1.getCircleX(center) +", "+c1.getCircleY(center) +" and radius: "+c1.getRadius() + " are equal");

        else
            System.out.println("Circle with cordinates: " + c1.getCircleX(center)+ ", "+c1.getCircleY(center)+" and radius: "+c1.getRadius()+ "is not equal to Circle with cordinates"+c2.getCircleX(center)+ ", "+c2.getCircleY(center)+" and radius: "+c2.getRadius());

 }


}