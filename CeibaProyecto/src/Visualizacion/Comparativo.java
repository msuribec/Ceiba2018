
package Visualizacion;

import Recoleccion.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.*;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Una instancia de la clase Comparativo muestra un marco principal con un gráfico de las variables Temperatura y Fotorresistencia en cada hora.
 * El marco escucha los eventos de cierre de la ventana y responde al cerrar la JVM. para el usuario.
 *
 * Es posible hacer zoom en una hora encerrándola en un rectángulo , arrastrando el mouse.
 * El marco además del gráfico tiene un botón para regresar a la interfaz gráfica de Inicio
 * @see Inicio
 */

class Comparativo extends ApplicationFrame {

    /**Lista de valores de la variable fotorresistencia*/
    private List<Float> Resistencias;
    /**Lista de valores de la variable temepratura*/
    private List<Float> Temperaturas;
    /**Lista de fechas */
    private ArrayList<Date> Dates;
    /** El id para acceder en el servidor */
    private String username;
    /**
     * Construye e inicializa un nuevo ApplicationFrame Comparativo
     * @param  r1   Objeto Recibir que obtiene los datos del servidor
     * @param  username El id para acceder en el servidor
     */
    Comparativo(Recibir r1, String username) {
        super("Comparación Histórica");
        this.username = username;
        Resistencias  = r1.getResistencias();
        Temperaturas= r1.getTemperaturas();
        Dates = r1.getDates();

        setLayout(new BorderLayout());
        add(new PanelGrafico(),BorderLayout.CENTER);
        //setContentPane(new PanelGrafico(username));

        JButton jButton1 = new JButton();
        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Regresar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        InputStream fuente = getClass().getResourceAsStream("/Uni Sans Heavy Italic_0.otf");
        try{
            Font FuenteUniSans = Font.createFont(Font.TRUETYPE_FONT,fuente).deriveFont(Font.PLAIN, 18);

            jButton1.setFont(FuenteUniSans); // NOI18N


        }catch (Exception e){

        }
        add(jButton1, BorderLayout.PAGE_END);

        pack();
        setVisible(true);
    }

    /** AL hacer clic en el botón regresar esconde esta ventana y muestra la interfaz gráfica de Inicio
     * @param evt : objeto ActionEvent
     * @see Inicio
     * */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        new Inicio(this.username).setVisible(true);
        this.setVisible(false);
    }

    /**
     *Una instancia de PanelGrafico representa un componente JPanel que contiene el  gráfico de las variables Temperatura yFotorresistencia en cada hora.
     * Es posible hacer zoom en una hora encerrándola en un rectángulo , arrastrando el mouse.
     */
    private class PanelGrafico extends JPanel implements ChangeListener {
        int SLIDER_INITIAL_VALUE = 50;
        JSlider slider;
        DateAxis domainAxis;
        int lastValue = SLIDER_INITIAL_VALUE;
        long delta = 1000 * 60 * 60 * 24 * 30;

        /**Construye e inicializa un nuevo JPanel PanelGrafico*/
        PanelGrafico() {
            super(new BorderLayout());
            JFreeChart chart = createChart();
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(600, 270));
            chartPanel.setDomainZoomable(true);
            chartPanel.setRangeZoomable(true);
            Border border = BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(4, 4, 4, 4),
                    BorderFactory.createEtchedBorder()
            );
            chartPanel.setBorder(border);
            add(chartPanel);

            JPanel dashboard = new JPanel(new BorderLayout());
            dashboard.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));

            this.slider = new JSlider(0, 100, SLIDER_INITIAL_VALUE);
            this.slider.addChangeListener(this);
            dashboard.add(this.slider);
            add(dashboard, BorderLayout.SOUTH);




        }

        /**Retorna un nuevo JFreeChart con dos TimeSeries para las variables Temperatura y Fotorresistencia
         * @return chart JFreeChart con dos Timeseries para dos variables diferentes
         * */
        private JFreeChart createChart() {

            TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
            TimeSeries series = crearSerie(Temperaturas,"Temperatura");
            TimeSeries series2 = crearSerie(Resistencias,"FotoResistencia");

            timeSeriesCollection.addSeries(series );
            timeSeriesCollection.addSeries(series2 );
            this.domainAxis = new DateAxis("Tiempo");
            NumberAxis rangeAxis = new NumberAxis("Variables");
            XYBarRenderer renderer = new XYBarRenderer();
            renderer.setShadowVisible(false);
            XYPlot plot = new XYPlot(timeSeriesCollection, domainAxis, rangeAxis, renderer);

            JFreeChart chart = new JFreeChart("Comparación Histórica", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            chart.setAntiAlias(false);
            return chart;
        }

        /**Retorna una Timeseries con el nombre especificado y que avanza una hora por dato con la lista de valores específicados
         * @param variable lista de valores a incluir en la TimeSeries
         * @param nombre nombre de la variable a incluir
         * @return Timeseries con el nombre y valores epecificados por la lista
         * */
        private TimeSeries crearSerie(List <Float> variable,String nombre) {
            TimeSeries timeSeries =  new TimeSeries(nombre);
            Date d = Dates.get(0);
            RegularTimePeriod regularTimePeriod = new Hour(d);
            int count =0;
            for (int index = 0; index < variable.size(); index++) {
                double value = variable.get(count);
                timeSeries.add(regularTimePeriod,value);
                regularTimePeriod = regularTimePeriod.next();
                count++;
            }
            return timeSeries;
        }

        /**Invocado cuando el objetivo del oyente ha cambiado su estado.
         *Actualiza el estado de los ejes cuando se hace uso del deslizador de la ventana
         * @param event un objeto ChangeEvent
         * */

        @Override
        public void stateChanged(ChangeEvent event) {
            int value = this.slider.getValue();
            long minimo = domainAxis.getMinimumDate().getTime();
            long maximo = domainAxis.getMaximumDate().getTime();
            if (value<lastValue) { // left
                minimo = minimo - delta;
                maximo = maximo - delta;
            } else { // right
                minimo = minimo + delta;
                maximo = maximo + delta;
            }
            DateRange range = new DateRange(minimo,maximo);
            domainAxis.setRange(range);
            lastValue = value;
        }


    }



    
}