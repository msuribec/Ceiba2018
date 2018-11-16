package Visualizacion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
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
import Recoleccion.*;

/**
 * Una instancia de la clase Comparativo muestra un marco principal con un gráfico de cada dato de ambas variables que se encuentran en el servidor.
 * El marco escucha los eventos de cierre de la ventana y responde al cerrar la JVM. para el usuario.
 *
 * Es posible hacer zoom en una hora encerrándola en un rectángulo , arrastrando el mouse.
 * Se debe hacer clic en el botón de siguiente hora para avanzar en los datos
 * El marco además del gráfico tiene un botón para regresar a la interfaz gráfica de Inicio
 * @see Inicio
 */

public class Historia extends ApplicationFrame implements ActionListener {


    /**Lista de valores de la variable fotorresistencia*/
    private List<Float> Resistencias;
    /**Lista de valores de la variable temperatura*/
    private List<Float> Temperaturas;
    /**índice del valor que se muestra en la lista de variables*/
    private int count =0;
    /**Timeseries de Temperaturas*/
    private TimeSeries SerieTemp;
    /**Timeseries de fotorresistencias*/
    private TimeSeries SerieResist;
    /**último valor de Temperatura*/
    private double lastTemp =0.0;
    /**último valor de fotorresistencia*/
    private double lastResist =0.0;
    /**Periodo entre los valores de las series*/
    private RegularTimePeriod regularTimePeriod;
    /** El id para acceder en el servidor */
    private String username;
    /** String del botón regresar*/
    private final static String BACK = "Regresar";

    /**
     * Construye e inicializa un nuevo ApplicationFrame Historia
     * @param  username El id para acceder en el servidor
     */
     Historia(final String username) {
        super("Historia");
         Recibir r1 = new Recibir(username);
        this.username= username;
        this.Resistencias  = r1.getResistencias();
        this.Temperaturas= r1.getTemperaturas();
        ArrayList<Date> DATES = r1.getDates();
        Date d = DATES.get(0);
        this.regularTimePeriod = new Hour(d);
        this.SerieTemp = new TimeSeries("Temperatura");
        this.SerieResist = new TimeSeries("Resistencia");

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(SerieTemp);
        dataset.addSeries(SerieResist);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);

        this.lastTemp = Temperaturas.get(0);
        this.lastResist = Resistencias.get(0);

        this.SerieTemp.add(regularTimePeriod, this.lastTemp);
        this.SerieResist.add(regularTimePeriod, this.lastResist);
        count++;

        regularTimePeriod = regularTimePeriod.next();

        final JButton button = new JButton("Siguiente hora");
        button.setActionCommand("ADD_DATA");
        button.setBackground(new java.awt.Color(255, 255, 255));
        button.setFont(new Font("Uni Sans Heavy Italic CAPS", Font.BOLD, 18)); // NOI18N
        button.addActionListener(this);

        final JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        add(content,BorderLayout.CENTER);

        JButton jButton1 = new JButton("Regresar");
        jButton1.setActionCommand(BACK);
        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.addActionListener(this);

         InputStream fuente = getClass().getResourceAsStream("/Uni Sans Heavy Italic_0.otf");
         try{
             Font FuenteUniSans = Font.createFont(Font.TRUETYPE_FONT,fuente).deriveFont(Font.PLAIN, 18);

             jButton1.setFont(FuenteUniSans); // NOI18N


         }catch (Exception e){

         }

        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(1,2));
        jp1.add(button);
        jp1.add(jButton1);

        add(jp1,BorderLayout.PAGE_END);
    }

    /**Retorna un JFreeChart con el eje horizontal avanzando cada hora, establece el nombre de los ejes como "hora" y "valor" y el título como Histórico de variables",
     * @param dataset objeto XYDataset de los datos del gráficos
     * @return result un nuevo JFreeChart con ejes y título establecido
     * */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                "Histórico de variables",
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

    /**Invocado cuando ocurre una acción.
     * <p>Si el comando de la acción corresponde al botón Siguiente hora, se avanza la hora y el dato correspondiente en el gráfico
     * Si el comando de la acción corresponde al botón Regresar esconde esta ventana y muestra la interfaz gráfica de Inicio
     * @see Inicio
     * @param e un bjeto ActionEvent
     * */
    public void actionPerformed(final ActionEvent e) {
        String comando = e.getActionCommand( );
        switch (comando) {
            case ("ADD_DATA"):
                this.lastTemp = Temperaturas.get(count);
                this.SerieTemp.add(regularTimePeriod, this.lastTemp);
                this.lastResist = Resistencias.get(count);
                this.SerieResist.add(regularTimePeriod, this.lastResist);
                count++;
                regularTimePeriod = regularTimePeriod.next();
                break;
            case (BACK):
                new Inicio(this.username).setVisible(true);
                this.dispose();
                break;
        }

    }





}










