import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

public class Resumen extends  JFrame implements ItemListener {

    private JPanel cards;
    private final static String BUTTONPANEL = "Temperatura";
    private final static String TEXTPANEL = "Fotoresistencia";
    private static Color fondo = new Color(255, 255, 255);


    Resumen(Recibir r1) {

        List<String> fechas = r1.getUltimasFechas();

        String mensajeTemp = leerTotal("Temperatura");
        String mensajeResist = leerTotal("FotoResistencia");

        setLayout(new BorderLayout());
        setTitle("Resumen y análisis de variables");
        setSize(1000,700);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Jeditor Temp
        JEditorPane editorTemp = new JEditorPane();
        editorTemp.setEditable(false);
        JScrollPane scrollTemp = new JScrollPane(editorTemp);
        editorTemp.setContentType("text/plain");
        editorTemp.setText(mensajeTemp);

        //Panel Temperaturas
        ResumenVariable panelTemperaturas = new ResumenVariable(r1.getPuntosTemp(), "°C", fechas, "TEMPERATURA");
        panelTemperaturas.setMinimumSize(new Dimension(800, 400));
        scrollTemp.setMinimumSize(new Dimension(400, 400));

        //FotoResistencias panel
        ResumenVariable panelResistencias = new ResumenVariable(r1.getPuntosResist(), "", fechas, "FOTORESISTENCIA");
        panelResistencias.setSize(new Dimension(500,200));
        panelResistencias.setMinimumSize(new Dimension(700, 400));

        //JsplitTemp
        JSplitPane splitTemp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelTemperaturas, scrollTemp);
        splitTemp.setOneTouchExpandable(true);
        splitTemp.setDividerLocation(800);

        //Jeditor FotoResistencias
        JEditorPane editorResist = new JEditorPane();
        editorResist.setEditable(false);
        JScrollPane scrollResist = new JScrollPane(editorResist);
        editorResist.setContentType("text/plain");
        editorResist.setText(mensajeResist);

        //JsplitTemp
        JSplitPane splitResist = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelResistencias, scrollResist);
        splitResist.setOneTouchExpandable(true);
        splitResist.setDividerLocation(800);

        //Provide minimum sizes for the two components in the split pane.
        scrollResist.setSize(new Dimension(400, 400));


        cards = new JPanel(new CardLayout());

        cards.add(splitTemp, BUTTONPANEL);
        cards.add(splitResist, TEXTPANEL);


        //Opciones Cartas
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL };
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        cb.setBackground(fondo);
        comboBoxPane.setBackground(fondo);


        add(comboBoxPane, BorderLayout.PAGE_START);
        add(cards, BorderLayout.CENTER);

    }

    private String  leerTotal(String variable){
        StringBuilder mensaje = new StringBuilder();
        try {
            Scanner input = new Scanner(new File("RegistroCeiba"+variable+".txt"));
            while (input.hasNextLine()) {
                String text = input.nextLine();
                mensaje.append(text);
                mensaje.append("\n");
            }
            return mensaje.toString();

        }catch(Exception e){
            e.printStackTrace();
        }
        return "No hay registros para la variable "+ variable;
    }


    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }




}