import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        JFrame framer=new JFrame("Centered Circle");
        Point p1 = new Point(918,218);

        Point zape = new Point(260,280 );
        Circle zap = new Circle(zape,30);
        framer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framer.setSize(1200,1200);
        framer.setVisible(true);
    Circle a = new Circle(p1,300);
    Circle da = new Circle(p1,219);
    Circle daz = new Circle(p1,139);
      a.add(zap);
        zap.add(a);
        zap.add(da);
      zap.add(daz);
        framer.add(zap);

        Point compareP = new Point(265,290);
        zap.ConstainsPointInCircle(compareP,zap);

       System.out.println("Test 2");
        Point comparePE = new Point(26555,29550);
         zap.ConstainsPointInCircle(comparePE,zap);


        zap.CompareCircles(zap,daz);
        zap.CompareCircles(zap,zap);

    }
}
