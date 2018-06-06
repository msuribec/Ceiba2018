package Visualizacion;

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
/**
 * Representa un Componete Jpanel
 * Este compnente incorpora un Gráfico 2d , en el eje horizontal se muestran la progresión del tiempo en horas y en el eje vertical se muestran
 * los últimos 24 datos recibidos del servidor para una variable determinada.
 *
 * Cada punto está acompañado de una etiqueta con el valor de la variable , para más precisión a la hora de leer datos.
 * @see Inicio
 */

public class ResumenVariable extends JPanel {

    /**lista de valores a incluir en el gráfico*/
    private List<Double> valores;
    /**lista tipo Puntos que contiene el valor de la variable y la hora donde se da ,  de la forma (hora,valor)*/
    private List<Punto> circulos;
    /**lista tipo Puntos con las coordenadas donde se ubicarán los circulos*/
    private List<Punto> puntos;
    /**unidades de la variable (en cualquier sistema de medición)*/
    private String unidades;
    /**nombre de la variable a graficar*/
    private String variable;
    /**Lista de fechas*/
    private List<String> fechas;

    /**valor mínimo*/
    private double minVal ;
    /**valor máximo*/
    private double maxVal ;

    /**Grosor de las lineas */
    private static final Stroke GrosorReglas = new BasicStroke(1f);
    /**Grosor de los gráficos*/
    private static final Stroke GrosorGrafico = new BasicStroke(3f);
    /**Espacio entre componentes del gráfico*/
    private final int espacio = 20;
    /**Margen del gráfico*/
    private final int espacioBordes = 80;
    /**Escala de conversión para el eje x*/
    private double conversorX;
    /**Escala de conversión para el eje y*/
    private double conversorY;


    /**Color para el fondo del gráfico*/
    private Color fondo = new Color(255, 255, 255);
    /**Color para los titulos de ejes*/
    private Color titulosEjes = new Color(255, 43, 190);
    /**Color para los ejes*/
    private Color ejes = new Color(199, 199, 199);
    /**Color para las líneas del gráfico*/
    private Color lineas = new Color(238, 238, 238);
    /**Color para las líneas que conectan los puntos*/
    private Color lineasConectan = new Color(255, 43, 190);
    /**Color para el color de los puntos*/
    private Color colorPuntos = new Color(192, 33, 197);
    /**Color para los números de ls ejes*/
    private Color numerosEjes = new Color(191, 191, 191);
    /**Color para las fechas y horas*/
    private Color fechasHoras = new Color(206, 206, 206);
    /**Color para las etiquetas de los puntos*/
    private Color etiquetasPuntos = new Color(136, 0, 147);


    /**Construye e inicializa un nuevo ApplicationFrame ResumenVariable
     * @param  valores  lista de valores a incluir en el gráfico
     * @param  unidades unidades de la variable (en cualquier sistema de medición)
     * @param  fechas Lista de fechas
     * @param  variable nombre de la variable a graficar
     */
    ResumenVariable(List<Double> valores, String unidades, List<String> fechas, String variable) {
        this.valores = valores;
        this.unidades = unidades;
        this.fechas =fechas;
        this.variable = variable;
        valorMinimo();
        valorMaximo();
    }


    /**Pinta el Gráfico 2d de la variable en función del tiempo
     * @param g el objeto Graphics para proteger
     */
    @Override
    protected void paintComponent(Graphics g) {
        final int anchoPuntos = 5;
        final int divisiones = 20;

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(fondo);

        conversorX = ((double) getWidth() - (2 * espacio) - espacioBordes) / (valores.size() - 1);
        conversorY = ((double) getHeight() - 2 * espacio - espacioBordes) / (maxVal - minVal);
        establecer();

        g2.setStroke(GrosorGrafico);

        int h = getHeight();

        g2.setColor(titulosEjes);
        // Título eje x

        g2.setFont(new Font("arial", Font.PLAIN, 14));
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

            g2.setFont(new Font("arial", Font.PLAIN, 10));
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
            g2.setFont(new Font("arial", Font.PLAIN, 10));
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
    /**Retorna la dimensión de la forma (ancho,alto)
     * @return nueva dimensión con el ancho y alto del componente actual*/
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }


    /**Establece el mínimo de los valores de la lista*/
    private void valorMinimo() {
        double minValor = valores.get(0);
        for(double d : valores){
            if (d< minValor){
                minValor= d;
            }
        }
        this.minVal = minValor;
    }
    /**Establece el máximo de los valores de la lista*/
    private void valorMaximo() {
        double maxValor = valores.get(0);
        for (int i =0;i <valores.size();i++) {
            if(valores.get(i)> maxValor){
                maxValor = valores.get(i);
            }
        }

        this.maxVal=maxValor;
    }

    /**Recorre la lista de valores y estalece las listas Circulos y Puntos*/
    private void establecer() {
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
        }

    }





}