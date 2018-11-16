import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Historia extends ApplicationFrame implements ActionListener {

    int count =0;
    Recibir r1;
    List<Float> Resistencias;
    List<Float> Temperaturas;
    ArrayList<Date> DATES;
    private TimeSeries series;
    private TimeSeries series2;

    private double lastValue =0.0;
    private double lastValue2 =0.0;
    Date d ;
    Hour h;
    RegularTimePeriod regularTimePeriod;

    public Historia(final String username) {
        super("Historia");

        this.r1= new Recibir(username);
        this.Resistencias  = r1.getResistencias();
        this.Temperaturas= r1.getTemperaturas();
        this.DATES = r1.Fechas;
        this.d = DATES.get(0);
        this.h = new Hour(d);
        this.regularTimePeriod = h;

        this.series = new TimeSeries("Temperatura");
        this.series2 = new TimeSeries("Resistencia");

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series2);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);

        this.lastValue = Temperaturas.get(0);
        this.lastValue2 = Resistencias.get(0);

        this.series.add(regularTimePeriod, this.lastValue);
        this.series2.add(regularTimePeriod, this.lastValue2);
        count++;

        regularTimePeriod = regularTimePeriod.next();

        final JButton button = new JButton("Siguiente hora");
        button.setActionCommand("ADD_DATA");
        button.addActionListener(this);

        final JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
        content.add(button, BorderLayout.SOUTH);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);

    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                "Histórico de varaibles",
                "Hora",
                "Valor",
                dataset,
                true,
                true,
                false
        );
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        DateFormat targetFormat = new SimpleDateFormat("MM/dd/HH:mm");
        ((DateAxis) axis).setDateFormatOverride(targetFormat);
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(0.0, 2000.0);

        return result;
    }

    public void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals("ADD_DATA")) {
            this.lastValue = Temperaturas.get(count);
            this.series.add(regularTimePeriod, this.lastValue);

            this.lastValue2 = Resistencias.get(count);
            this.series2.add(regularTimePeriod, this.lastValue2);

            count++;
            regularTimePeriod = regularTimePeriod.next();
        }
    }





}










