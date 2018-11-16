
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.*;
import javax.swing.JPanel;

public class ResumenVariable extends JPanel {

    private List<Double> valores;
    private List<Punto> circulos;
    private List<Punto> puntos;
    private String unidades;
    private String variable;
    private List<String> fechas;
    private List<Double> coordX;

    private double minVal ;
    private double maxVal ;

    private static final Stroke GrosorReglas = new BasicStroke(1f);
    private static final Stroke GrosorGrafico = new BasicStroke(3f);
    private int anchoPuntos = 5;
    private int divisiones = 20;
    private final int espacio = 20;
    private final int espacioBordes = 80;
    double conversorX;
    double conversorY;

    Color fondo = new Color(255, 255, 255);
    Color titulosEjes = new Color(255, 43, 190);
    Color ejes = new Color(199, 199, 199);
    Color lineas = new Color(238, 238, 238);
    Color lineasConectan = new Color(255, 43, 190);
    Color colorPuntos = new Color(192, 33, 197);
    Color numerosEjes = new Color(191, 191, 191);
    Color fechasHoras = new Color(206, 206, 206);
    Color etiquetasPuntos = new Color(204, 42, 34);



    public ResumenVariable(List<Double> valores,String unidades,List<String> fechas,String variable) {
        this.valores = valores;
        this.unidades = unidades;
        this.fechas =fechas;
        this.variable = variable;
        valorMinimo();
        valorMaximo();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(fondo);

        conversorX = ((double) getWidth() - (2 * espacio) - espacioBordes) / (valores.size() - 1);
        conversorY = ((double) getHeight() - 2 * espacio - espacioBordes) / (maxVal - minVal);
        establecer();

        g2.setStroke(GrosorGrafico);


        int w = getWidth();
        int h = getHeight();


        g2.setColor(titulosEjes);
        // Título eje x

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        g.drawString("H O R A ",espacio -10,getHeight() - espacio - espacioBordes+40);

        // Título eje Y
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        float sy = espacio + ((h - 2*espacio) - variable.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < variable.length(); i++) {
            String letter = String.valueOf(variable.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth() - espacio;
            float sx = (espacio - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }


        g2.setStroke(GrosorReglas);

        // lineas horizontales y marcas del eje y
        for (int i = 0; i < divisiones + 1; i++) {
            int y = getHeight() - ((i * (getHeight() - espacio * 2 - espacioBordes)) / divisiones + espacio + espacioBordes);
            if (valores.size() > 0) {

                g2.setColor(lineas);
                //lineas horizontales
                g2.drawLine(espacio + espacioBordes + 1 + anchoPuntos, y, getWidth() - espacio, y);

                g2.setColor(numerosEjes);
                String yLabel = ((int) ((minVal + (maxVal - minVal) * ((i * 1.0) / divisiones)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                //marcas en el eje y
                g2.drawString(yLabel, espacio + espacioBordes - labelWidth - 5, y + (metrics.getHeight() / 2) - 3);
            }
        }





        //puntos y sus etiquetas, marccas de fecha y hora,marcas del eje x y lineas verticales


        for (int i = 0; i < puntos.size(); i++) {
            g2.setStroke(GrosorReglas);
            int x = (int)puntos.get(i).getX() - anchoPuntos / 2;
            int y = (int)puntos.get(i).getY() - anchoPuntos / 2;

            g2.setFont(new Font("TimesRoman", Font.PLAIN, 10));
            g2.setColor(fechasHoras);
            // marcas de  fecha y hora
            String [] fecha = fechas.get(i).split("//");
            g2.drawString(""+ fecha[0],x+2,getHeight() - espacioBordes/2);
            g2.drawString(""+ fecha[1],x+2,getHeight() - espacioBordes/4);

            // marcas en el eje vertical
            g2.setColor(numerosEjes);
            String xLabel = i + "";
            g2.drawString(xLabel, x,getHeight() - espacioBordes);

            //lineas verticales
            g2.setColor(lineas);
            g2.drawLine(x+1, getHeight() - espacio - espacioBordes - 1 - anchoPuntos, x+1, espacio);

            g2.setStroke(GrosorGrafico);

            g2.setColor(etiquetasPuntos);
            //etiquetas puntos
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 10));
            g2.drawString(""+ (int)circulos.get(i).getY()+ unidades,x+2,y+40);

            //puntos
            g2.setColor(colorPuntos);
            g2.fillOval(x, y, anchoPuntos, anchoPuntos);
            if(i == puntos.size() - 1){
                break;
            }
            g2.setColor(lineasConectan);
            g2.setStroke(GrosorGrafico);
            int x1 = (int)puntos.get(i).getX();
            int y1 = (int)puntos.get(i).getY();
            int x2 = (int)puntos.get(i + 1).getX();
            int y2 = (int) puntos.get(i + 1).getY();
            g2.drawLine(x1, y1, x2, y2);
        }


        g2.setColor(ejes);
        //eje x
        g2.drawLine(espacio + espacioBordes, getHeight() - espacio - espacioBordes, espacio + espacioBordes, espacio);
        //eje y
        g2.drawLine(espacio + espacioBordes, getHeight() - espacio - espacioBordes, getWidth() - espacio, getHeight() - espacio - espacioBordes);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }

    private void valorMinimo() {
        double minValor = valores.get(0);
        for (int i =0;i <valores.size();i++) {
            if (valores.get(i)< minValor){
                minValor= valores.get(i);
            }
        }
        this.minVal = minValor;
    }

    private void valorMaximo() {
        double maxValor = valores.get(0);
        for (int i =0;i <valores.size();i++) {
            if(valores.get(i)> maxValor){
                maxValor = valores.get(i);
            }
        }
        this.maxVal=maxValor;
    }


    private void establecer() {
        Set<Double> todasX = new HashSet<>();
        coordX = new ArrayList<>();
        // recorre cada dos valores y los agrega como puntos (x,y)
        circulos = new ArrayList<>();
        for (int i = 0; i < valores.size() - 1; i = i + 2) {
            circulos.add(new Punto((valores.get(i)), valores.get(i + 1)));
        }
        // convierte los puntos agregados en el ciclo anterior según los conversores
        puntos = new ArrayList<>();
        for (int i = 0; i < circulos.size(); i++) {
            int x1 = (int) (i * 1.95 * conversorX + espacio + espacioBordes);
            int y1 = (int) ((maxVal - circulos.get(i).getY()) * conversorY + espacio);
            puntos.add(new Punto(x1, y1));
            todasX.add((double)y1);
        }
        Iterator <Double >iter = todasX.iterator();
        while (iter.hasNext()) {
            coordX.add(iter.next());
        }
    }



}