package Persistencia;

import Recoleccion.*;
import Analitica.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;



/**
 * Una instancia de la clase Persistencia guarda los datos del servidor y los análisis de esos datos en dos diferentes archivos por cada variable
 */

public class Persistencia {

/**
 * Construye e inicializa una nueva Persistencia
 * @param  username El id para acceder en el servidor
 */
    public Persistencia(String username) {
        String mensajeTemp = new Analitica(username,true).getMensaje();
        String mensajeResist = new Analitica(username,false).getMensaje();
        guardarAnalisis(mensajeTemp,"Temperatura");
        guardarAnalisis(mensajeResist,"FotoResistencia");
        Recibir r1 = new Recibir(username);
        ArrayList<Float> Temps = r1.getTemperaturas();
        ArrayList<Float> Resist = r1.getResistencias();
        ArrayList <String> Fechas = r1.getFechas();
        guardarDatos(Temps ,"Temperatura","°C",Fechas);
        guardarDatos(Resist ,"FotoResistencia","",Fechas);
    }

    /**
     * Guarda el análisis de una variable en un archivo con el nombre RegistroCeiba[nombreVariable].txt
     * @param  mensaje - mensaje de análisis a guardar
     * @param variable - nombre de la variable uyo análisis se va a guardar
     *
     */
    private void guardarAnalisis(String mensaje,String variable) throws NullPointerException {

        DateFormat archivo = new SimpleDateFormat("dd/MM/yy/HH:mm");
        Date dateobj = new Date();
        BufferedWriter writer = null;
        try {
            File logFile = new File("RegistroCeiba"+variable+".txt");
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("Análisis de "+ variable);
            writer.write("\n");
            writer.write(mensaje);
            writer.write("\n");
            writer.write("Fecha del análisis: " + archivo.format(dateobj));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Guarda una lista de datos en un archivo llamado Datos[NombreVariable].txt
     * @param  datos  lista de valores a incluir en el gráfico
     * @param  variable nombre de la variable a graficar
     * @param  unidades unidades de la variable (en cualquier sistema de medición)
     * @param  Fechas Lista de fechas
     *
     */
    public void guardarDatos(List<Float> datos,String variable,String unidades, List<String> Fechas) throws NullPointerException {

        DateFormat archivo = new SimpleDateFormat("dd/MM/yy/HH:mm");
        Date dateobj = new Date();
        BufferedWriter writer = null;
        try {
            File logFile = new File("Datos"+variable+".txt");
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("Datos de "+ variable);
            writer.write("\n");
            writer.write("Este archivo se creó el : " + archivo.format(dateobj));
            writer.write("\n");
            int count =0;
            for (int i =0 ;i< datos.size();i++){
                writer.write(datos.get(i)+ unidades +" ");
                writer.write(Fechas.get(i));
                writer.write("\n");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }





}
