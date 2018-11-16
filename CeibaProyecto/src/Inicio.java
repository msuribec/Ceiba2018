import org.jfree.ui.RefineryUtilities;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio extends javax.swing.JFrame implements ActionListener {

    private final static String COMPARAR = "Comparación variables";
    private final static String HISTORIA = "Histórico de valores";
    private final static String RESUMEN = "Resumen y análisis";
    private Recibir r1;
    private Inicio(){

        Color integrantes = new Color(171, 168, 255);
        Color fondoBotones = new Color(255, 255, 255);
        Color escritoBotones = new Color(129, 212, 255);
        Color borde = new Color(255, 255, 255);
        Color titulo = new Color(97, 200, 255);
        Color fondo = new Color(255, 255, 255);
        LineBorder bordeBotones = new LineBorder(borde, 2, true);


        r1 = new Recibir("EquipoCeibaSCV");

        Persistencia p1 = new Persistencia(r1.username);

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
        butComparar.setFont(new Font("Uni Sans Heavy Italic CAPS", Font.BOLD, 18));
        butComparar.setForeground(escritoBotones);
        butComparar.setBorder(bordeBotones);
        butComparar.setText("Comparación de las variables");
        butComparar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        //botón Historia de ambas variables
        JButton butHistoria = new JButton();
        butHistoria.setActionCommand( HISTORIA);
        butHistoria.addActionListener( this );
        butHistoria.setBackground(fondoBotones);
        butHistoria.setFont(new Font("Uni Sans Heavy Italic CAPS", Font.BOLD, 18));
        butHistoria.setForeground(escritoBotones);
        butHistoria.setText("Histórico de las variables");
        butHistoria.setBorder(bordeBotones);
        butHistoria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        //botón Resumen de ambas variables
        JButton butResumen = new JButton();
        butResumen.setActionCommand(RESUMEN);
        butResumen.addActionListener( this );
        butResumen.setBackground(fondoBotones);
        butResumen.setFont(new Font("Uni Sans Heavy Italic CAPS", Font.BOLD, 18));
        butResumen.setForeground(escritoBotones);
        butResumen.setText("Resumen y análisis");
        butResumen.setBorder(bordeBotones);
        butResumen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        //Título interfáz gráfica
        JLabel labtitulo = new JLabel();
        labtitulo.setFont(new Font("Uni Sans Heavy Italic CAPS", Font.BOLD, 30)); // NOI18N
        labtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labtitulo.setForeground(titulo);
        labtitulo.setText("PROYECTO CEIBA");
        labtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labtitulo.setPreferredSize(new java.awt.Dimension(535, 100));

        //Etiqueta con nombres de estudiantes
        JLabel labIntegrantes = new JLabel();
        labIntegrantes.setFont(new Font("arial", Font.BOLD, 12)); // NOI18N
        labIntegrantes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labIntegrantes.setForeground(integrantes);
        labIntegrantes.setText("María Sofía Uribe , Valeria Suárez y Camila Barona");
        labIntegrantes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labIntegrantes.setPreferredSize(new java.awt.Dimension(535, 100));

        //Etiqueta imágen
        JLabel labimagen = new JLabel();
        ImageIcon iconLogo = new ImageIcon("main.png");
        labimagen.setIcon(iconLogo);
        labimagen.setBorder(javax.swing.BorderFactory.createLineBorder(fondo, 10));

        //añadir botones al panel
        jPanel1.add(butComparar);
        jPanel1.add(new JLabel());
        jPanel1.add(butHistoria);
        jPanel1.add(new JLabel());
        jPanel1.add(butResumen);

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


    }

    public void actionPerformed( ActionEvent evento ) {
        String comando = evento.getActionCommand( );
        switch (comando){
            case (COMPARAR):
               Comparativo comp = new Comparativo(r1);
               comp.setVisible(true);
                break;
            case (HISTORIA):
                final Historia demo = new Historia(r1.username);
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
                break;
            case (RESUMEN):
                new Resumen(r1).setVisible(true);
                break;

        }
    }

    public static void main( String[] args ){
        Inicio interfazApp = new Inicio( );
        interfazApp.setVisible( true );
    }

}
