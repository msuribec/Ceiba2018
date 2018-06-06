package Visualizacion;


import org.jfree.ui.RefineryUtilities;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import Recoleccion.*;
import Persistencia.*;


/**
 * Una instancia de la clase Inicio muestra un marco principal con el nombre de los autores,el título del proyecto y cinco botones,al hacer en clic en cualquier botón se
 * visualizará una nueva ventana.
 *
 * En el botón de "comparación de variables" muestra un JFrame Comparativo
 * En el botón de "histórico de las variables" muestra un JFrame Historia
 * El botón de "resumen y análisis" muestra un JFrame Resumen
 * El botón de "datos curiosos" muestra  un JFrame DatosCuriosos.
 * El botón de "Créditos" muestra  un JFrame Créditos.
 * @see Inicio
 */
public class Inicio extends JFrame implements ActionListener {

    /** String del botón comparación de variables*/
    private final static String COMPARAR = "Comparación variables";
    /** String del botón histórico de las variables*/
    private final static String HISTORIA = "Histórico de valores";
    /** String del botón resumen y análisis*/
    private final static String RESUMEN = "Resumen y análisis";
    /** String del botón datos curiosos*/
    private final static String DATOS= "Datos curiosos";
    /** String del botón créditos*/
    private final static String CREDITOS = "Créditos";
    /** El id para acceder en el servidor */
    private String username;
    /** Objeto Recibir que obtiene los datos del servidor*/
    private Recibir r1;
    /**
     * Construye e inicializa un nuevo JFrame Inicio
     * @param  username El id para acceder en el servidor
     */

    Inicio(String username){
        this.username= username;
        Color integrantes = new Color(171, 168, 255);
        Color fondoBotones = new Color(255, 255, 255);
        Color escritoBotones = new Color(129, 212, 255);
        Color borde = new Color(255, 255, 255);
        Color titulo = new Color(97, 200, 255);
        Color fondo = new Color(255, 255, 255);
        LineBorder bordeBotones = new LineBorder(borde, 2, true);


        r1 = new Recibir(username);
        Persistencia p1 = new Persistencia(username);

        //panel que contiene los botones
        JPanel jPanel1;
        jPanel1 = new JPanel();
        jPanel1.setLayout( new GridLayout( 5, 1) );
        JPanel jpanel2 = new JPanel();
        jpanel2.setLayout(new BorderLayout());

        jPanel1.setBackground(fondo);
        getContentPane().setBackground(fondo);

        //botón Comparación de ambas variables
        JButton butComparar = new JButton();
        butComparar.setActionCommand(COMPARAR );
        butComparar.addActionListener( this );
        butComparar.setBackground(fondoBotones);
        butComparar.setForeground(escritoBotones);
        butComparar.setBorder(bordeBotones);
        butComparar.setText("Comparación de las variables");
        butComparar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        //botón Historia de ambas variables
        JButton butHistoria = new JButton();
        butHistoria.setActionCommand( HISTORIA);
        butHistoria.addActionListener( this );
        butHistoria.setBackground(fondoBotones);
        butHistoria.setForeground(escritoBotones);
        butHistoria.setText("Histórico de las variables");
        butHistoria.setBorder(bordeBotones);
        butHistoria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        //botón Resumen de ambas variables
        JButton butResumen = new JButton();
        butResumen.setActionCommand(RESUMEN);
        butResumen.addActionListener( this );
        butResumen.setBackground(fondoBotones);
        butResumen.setForeground(escritoBotones);
        butResumen.setText("Resumen y análisis");
        butResumen.setBorder(bordeBotones);
        butResumen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        //botón creditos
        JButton butCred = new JButton();
        butCred.setActionCommand(CREDITOS);
        butCred.addActionListener( this );
        butCred.setBackground(fondoBotones);
        butCred.setForeground(escritoBotones);
        butCred.setText("Créditos");
        butCred.setBorder(bordeBotones);
        butCred.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        //botón datos
        JButton butDatos = new JButton();
        butDatos.setActionCommand(DATOS);
        butDatos.addActionListener( this );
        butDatos.setBackground(fondoBotones);
        butDatos.setForeground(escritoBotones);
        butDatos.setText("Datos curiosos");
        butDatos.setBorder(bordeBotones);
        butDatos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        //Título interfáz gráfica
        JLabel labtitulo = new JLabel();
        labtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labtitulo.setForeground(titulo);
        labtitulo.setText("PROYECTO CEIBA");
        labtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labtitulo.setPreferredSize(new java.awt.Dimension(535, 100));

        //Etiqueta con nombres de estudiantes
        JLabel labIntegrantes = new JLabel();
        labIntegrantes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labIntegrantes.setForeground(integrantes);
        labIntegrantes.setText("María Sofía Uribe , Valeria Suárez y Camila Barona");
        labIntegrantes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labIntegrantes.setPreferredSize(new java.awt.Dimension(535, 100));

        //Etiqueta imágen
        JLabel labimagen = new JLabel();
        ImageIcon iconLogo = new ImageIcon(getClass().getResource("/main.png"));
        labimagen.setIcon(iconLogo);

        labimagen.setBorder(javax.swing.BorderFactory.createLineBorder(fondo, 10));


        InputStream fuente = getClass().getResourceAsStream("/Uni Sans Heavy Italic_0.otf");
        try{
            Font FuenteUniSans = Font.createFont(Font.TRUETYPE_FONT,fuente).deriveFont(Font.PLAIN, 18);

            labIntegrantes.setFont(new Font("arial", Font.PLAIN, 12));
            labtitulo.setFont(FuenteUniSans);
            butDatos.setFont(FuenteUniSans);
            butCred.setFont(FuenteUniSans);
            butHistoria.setFont(FuenteUniSans);
            butResumen.setFont(FuenteUniSans);
            butComparar.setFont(FuenteUniSans);


        }catch (Exception e){

        }

        //añadir botones al panel
        jPanel1.add(butComparar);
        jPanel1.add(butHistoria);
        jPanel1.add(butResumen);
        jPanel1.add(butDatos);
        jPanel1.add(butCred);

        //Configuración Frame principal
        setLayout(new BorderLayout());
        setTitle("App");
        setSize(600,500);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel labEspacio = new JLabel();
        labEspacio.setPreferredSize(new Dimension(20,100));

        //añadir elementos al frame principal
        add(labtitulo, BorderLayout.PAGE_START);
        add(jPanel1,BorderLayout.CENTER);
        add(labEspacio,BorderLayout.LINE_START);
        add(labimagen, BorderLayout.LINE_END);
        add(labIntegrantes, BorderLayout.PAGE_END);
        setVisible(true);

    }


    /**Invocado cuando ocurre una acción.
     * Si el comando de la acción corresponde al botón "comparación de variables" se desecha la ventana actual y se muestra un JFrame Comparativo
     * Si el comando de la acción corresponde al botón "histórico de las variables" se desecha la ventana actual y se muestra un JFrame Historia
     * Si el comando de la acción corresponde al botón "resumen y análisis" se desecha la ventana actual y se muestra un JFrame Resumen
     * Si el comando de la acción corresponde al botón "datos curiosos" se desecha la ventana actual y se muestra un JFrame DatosCuriosos
     *Si el comando de la acción corresponde al botón "Créditos" se desecha la ventana actual y se muestra un JFrame Creditos
     * @param evento un bjeto ActionEvent
     * */
    public void actionPerformed( ActionEvent evento ) {
        String comando = evento.getActionCommand( );
        switch (comando){
            case (COMPARAR):
               Comparativo comp = new Comparativo(r1,this.username);
               comp.setVisible(true);
               this.dispose();
                break;
            case (HISTORIA):
                final Historia demo = new Historia(r1.username);
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
                this.dispose();
                break;
            case (RESUMEN):
                new Resumen(r1,this.username).setVisible(true);
                this.dispose();
                break;
            case (DATOS):
                new DatosCuriosos(this.username).setVisible(true);
                this.dispose();
                break;

            case (CREDITOS):
                new Creditos(this.username).setVisible(true);
                this.dispose();
                break;

        }
    }

    /** Crea un objeto Inicio
     *  @param args : argumentos */
    public static void main( String[] args ){
        Inicio interfazApp = new Inicio("EquipoCeiba7");
    }

}
