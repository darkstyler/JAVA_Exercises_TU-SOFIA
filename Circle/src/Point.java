public class Point
{
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    private int x;
    private int y;

    public Point(int x,int y){
        this.x = x;
        this.y=y;
    }

    public Point(){
        x=0;
        y=0;
    }
}
