public class Point {
    private int x;
    private  int y;
    Point(int x ,int y){
        this.x = x;
        this.y = y;
    }
    Point(){
        x=0;
        y=0;
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public String toString() {
        System.out.println("Point's toString is called");
        return "("+this.x+","+this.y+")";
    }

}
