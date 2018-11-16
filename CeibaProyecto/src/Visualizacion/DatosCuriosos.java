package Visualizacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.InputStream;

/**
 * Una instancia de la clase DatosCuriosos  muestra un marco principal con un una imágen que contiene información pertinente al porqué se decidió medir estas variables y como esto puede ayudar a mejorar a la ceiba solar.
 * El marco escucha los eventos de cierre de la ventana y responde al cerrar la JVM. para el usuario.
 * El marco tiene además un botón para regresar a la interfaz gráfica de Inicio
 * @see Inicio
 *
 */
public class DatosCuriosos extends JFrame {

    /** El id para acceder en el servidor */
    String username;

    /**
     * Construye e inicializa un nuevo JFrame DatosCuriosos
     * @param  username El id para acceder en el servidor
     */
    public DatosCuriosos(String username) {
        this.username = username;
        JLabel jLabel1 = new JLabel();
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/Datos.png"))); // NOI18N



        JButton jButton1 = new JButton();
        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Regresar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
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
        labEspacio.setPreferredSize(new Dimension(20,100));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(jLabel1, java.awt.BorderLayout.CENTER);
        getContentPane().add(labEspacio, BorderLayout.LINE_START);
        getContentPane().add(jButton1, BorderLayout.PAGE_END);
        getContentPane().setPreferredSize(new Dimension(530,600));
        getContentPane().setBackground(new Color(255, 253, 171));
        pack();

    }


    /** AL hacer clic en el bot+on regresar esconde esta ventana y muestra la interfaz gráfica de Inicio
     * @param evt : objeto ActionEvent
     * @see Inicio
     * */
    private void jButton1ActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        new Inicio(this.username).setVisible(true);
        this.dispose();
    }

}
