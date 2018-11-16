package Visualizacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Una instancia de la clase Creditos  muestra un marco principal con Ventana que muestra la información de derechos de autor de las
 * imágenes que aparecen dentro del proyecto y a las librerías utilizadas dentro de la codificación.
 * El marco escucha los eventos de cierre de la ventana y responde al cerrar la JVM. para el usuario.
 * El marco tiene además un botón para regresar a la interfaz gráfica de Inicio
 * @see Inicio
 */

public class Creditos extends javax.swing.JFrame {

    /** El id para acceder en el servidor */
    String username;


    /**
     * Construye e inicializa un nuevo JFrame Creditos
     * @param  username El id para acceder en el servidor
     */
    public Creditos (String username) {
        this.username = username;
        JLabel link = new JLabel(" Imágenes Datos curiosos y créditos \n");
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI("https://www.freepik.com/");
                            desktop.browse(uri);
                        } catch (IOException ex) {
                            // do nothing
                        } catch (URISyntaxException ex) {
                            //do nothing
                        }
                    } else {
                        //do nothing
                    }
                }
            }
        });

        JLabel link2 = new JLabel(" Speech bubbles        \n");
        link2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI("https://www.freepik.com/winkimages");
                            desktop.browse(uri);
                        } catch (IOException ex) {
                            // do nothing
                        } catch (URISyntaxException ex) {
                            //do nothing
                        }
                    } else {
                        //do nothing
                    }
                }
            }
        });

        JLabel link3 = new JLabel(" Librería JFreeChart  \n");
        link3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI("http://www.jfree.org/jfreechart/");
                            desktop.browse(uri);
                        } catch (IOException ex) {
                            // do nothing
                        } catch (URISyntaxException ex) {
                            //do nothing
                        }
                    } else {
                        //do nothing
                    }
                }
            }
        });

        Color fondo = new Color(252, 255, 194);


        JLabel labtitulo = new JLabel("Créditos");
        labtitulo.setFont(new Font("Bebas Neue", Font.PLAIN, 48)); // NOI18N
        labtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labtitulo.setForeground(new java.awt.Color(242, 120, 83));
        labtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labtitulo.setPreferredSize(new java.awt.Dimension(535, 100));

        JLabel jLabel1 = new javax.swing.JLabel();
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/creditos.png"))); // NOI18N

        JButton jButton1 = new JButton();
        jButton1.setBackground(fondo);
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
        labEspacio.setPreferredSize(new Dimension(20,100));

        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(6,1));

        jp1.add(link);jp1.add(labEspacio);jp1.add(link2);jp1.add(labEspacio);jp1.add(link3);
        jp1.setBackground(fondo);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(labtitulo, BorderLayout.PAGE_START);
        getContentPane().add(jp1, java.awt.BorderLayout.CENTER);
        getContentPane().add(labEspacio, BorderLayout.LINE_START);
        getContentPane().add(jButton1, BorderLayout.PAGE_END);
        getContentPane().add(jLabel1, BorderLayout.LINE_END);
        getContentPane().setPreferredSize(new Dimension(505,370));
        setResizable(false);
        getContentPane().setBackground(fondo);
        pack();

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
