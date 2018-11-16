import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Comparativo extends ApplicationFrame {

    private List<Float> Resistencias;
    private List<Float> Temperaturas;
    private ArrayList<Date> Dates;


    Comparativo(Recibir r1) {
        super("Comparación Histórica");
        Resistencias  = r1.getResistencias();
        Temperaturas= r1.getTemperaturas();
        Dates = r1.Fechas;
        setContentPane(new DemoPanel());
        pack();
        setVisible(true);
    }


    private class DemoPanel extends JPanel implements ChangeListener {
        int SLIDER_INITIAL_VALUE = 50;
        JSlider slider;
        DateAxis domainAxis;
        int lastValue = SLIDER_INITIAL_VALUE;
        long delta = 1000 * 60 * 60 * 24 * 30;

        DemoPanel() {
            super(new BorderLayout());
            JFreeChart chart = createChart();
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
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