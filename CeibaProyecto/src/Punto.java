public class Punto {
    private  double x;
    private double y;

    Punto(double x, double y) {
        this.x= x;
        this.y =y;
    }

    double getY() { return y; }

    double getX() { return x; }

    public String toString()
    {
        return x + "," + y;
    }
}
