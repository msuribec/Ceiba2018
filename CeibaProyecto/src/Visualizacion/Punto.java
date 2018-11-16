package Visualizacion;
/**La clase Punto define un punto que representa una ubicación en el espacio de coordenadas (x, y).*/
public class Punto {
    /**Coordenada X de este punto*/
    private  double x;
    /**Coordenada Y de este punto*/
    private double y;
    /**Construye e inicializa un Punto con las coordenadas especificadas.
     * @param x coordenada X de este punto
     * @param y coordenada Y de este punto*/
    Punto(double x, double y) {
        this.x= x;
        this.y =y;
    }
    /**Retorna la coordenada Y de este punto
     * @return coordenada Y de este punto*/
    double getY() { return y; }
    /**Retorna la coordenada X de este punto
     * @return coordenada X de este punto*/
    double getX() { return x; }
    /**Retorna una cadena que representa el valor de este Punto.*/
    public String toString()
    {
        return x + "," + y;
    }
}
