package Visualizacion;

import Recoleccion.Recibir;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;


/**
 * Una instancia de la clase Resumen muestra un marco principal con Graficas de las últimas 24 horas de cada variable de manera individual,
 * además se muestra un panel con el análisis del comportamiento de la variable.
 * El marco escucha los eventos de cierre de la ventana y responde al cerrar la JVM. para el usuario.
 *
 * En la parte superior del marco se muestra un menú desplegable donde se puede seleccionar la variable que se desea ver
 * El marco además del gráfico tiene un botón para regresar a la interfaz gráfica de Inicio
 * @see Inicio
 */
public class Resumen extends  JFrame implements ItemListener {

    /** JPanel que contiene los gráficos de ambas variables*/
    private JPanel cards;
    /** String del botón Temperatura*/
    private final static String BUTTONPANEL = "Temperatura";
    /** String del botón Resistencia*/
    private final static String TEXTPANEL = "Fotoresistencia";
    private static Color fondo = new Color(255, 255, 255);
    /** El id para acceder en el servidor */
    private String username;

    /**
     * Construye e inicializa un nuevo ApplicationFrame Resumen
     * @param  r1   Objeto Recibir que obtiene los datos del servidor
     * @param  username El id para acceder en el servidor
     */

    Resumen(Recibir r1, String username) {
        this.username=username;
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


        JLabel labEspacio = new JLabel();
        labEspacio.setPreferredSize(new Dimension(20,20));
        labEspacio.setBackground(Color.WHITE);

        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(1,4));
        jp1.add(comboBoxPane);
        jp1.add(labEspacio);
        jp1.add(labEspacio);
        jp1.add(jButton1);
        jp1.setBackground(Color.WHITE);




        add(jp1, BorderLayout.PAGE_START);
        add(cards, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE);

    }

    /**Retorna el contenido del archivo del análisis de la variable.Si no se encuentra el archivo , se retorna el siguiente mensaje: "No hay registros para la variable "
     * @param variable nombre de la variable , cuyo archivo de análisis se quiere leer
     * @return String con el contenido del archivo del análisis de la variable
     *
     * */
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

    /**Se invoca cuando un elemento ha sido seleccionado o deseleccionado por el usuario.
     * Cuando se selecciona , desde el menu desplegable una variable, en la ventana se muestra el resumen y an[alisis de esta
     * */
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }


    /** Al hacer clic en el botón regresar esconde esta ventana y muestra la interfaz gráfica de Inicio
     * @param evt : objeto ActionEvent
     * @see Inicio
     * */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        new Inicio(this.username).setVisible(true);
        this.dispose();
    }




}