package Analitica;

import java.util.*;
import Recoleccion.Recibir;


/**
 * Una instancia de la clase Analitica analiza todos los datos de una variable en un servidor y lanza datos estadísticos.
 *Lanza los siguientes datos
 Promedio: Calcula el promedio de todos los valores de la variable, actualmente almacenados en el servidor
 Promedio Recientes: Calcula el promedio de los últimos 24 valores de la variable, actualmente almacenados en el servidor
 Mínimo: Calcula el mínimo de todos los valores de la variable, actualmente almacenados en el servidor y
 Mínimo Recientes: Calcula el mínimo de los últimos 24 valores de la variable, actualmente almacenados en el servidor y
 Máximo: Calcula el máximo de todos los valores de la variable, actualmente almacenados en el servidor
 Máximo Recientes: Calcula el máximo de los últimos 24 valores de la variable, actualmente almacenados en el servidor
 */
public class Analitica {

    /**lista de las últimas 24 fechas del servidor */
    private List<String> FechasLatest;
    /**lista de las fechas del servidor */
    private List<String> Fechas ;
    /**Representación del mensaje del análisis realizado*/
    private String mensaje = "";
    /**máximo de todos los valores de la variable, actualmente almacenados en el servidor */
    private float maximo;
    /**máximo de los últimos 24 valores de la variable, actualmente almacenados en el servidor*/
    private float maximo24;
    /**mínimo de todos los valores de la variable, actualmente almacenados en el servidor */
    private float minimo;
    /**mínimo de los últimos 24 valores de la variable, actualmente almacenados en el servidor*/
    private float minimo24;
    /**promedio de todos los valores de la variable, actualmente almacenados en el servidor */
    private float promedio;
    /**promedio de los últimos 24 valores de la variable, actualmente almacenados en el servidor*/
    private float promedio24;
    /**lista de todos los valores de una variable del servidor */
    private List <Float> VariablesTotales;
    /**lista de los últimos 24 valores de una variable del servidor */
    private List <Float> VariablesRecientes;
    /**nombre de la variable a analizar*/
    private String variable;
    /**unidades de la variable a graficar (en cualquier sistema de medición)*/
    private String unidades;


    /**
     * Construye e inicializa una nueva Analitica
     * @param  username El id para acceder en el servidor
     * @param temp booleano , si es verdadero se analiza la primera variable , de lo contrario se analiza la segunda
     *
     */
    public Analitica(String username, boolean temp) {

        Recibir r1= new Recibir(username);
        this.Fechas = r1.getFechas();
        this.FechasLatest = r1.getUltimasFechas();

        if (temp){
            this.VariablesTotales = r1.getTemperaturas();
            this.VariablesRecientes = r1.getUltimasTemp();
            this.variable="Temperatura";
            this.unidades= "°C";
        }else{
            this.VariablesTotales = r1.getResistencias();
            this.VariablesRecientes = r1.getUltimasResist();
            this.variable="FotoResistencia";
            this.unidades= "°C";

        }

        this.maximo= maximo();
        this.maximo24= maximo24();
        this.minimo= minimo();
        this.minimo24= minimo24();
        this.promedio= promedio();
        this.promedio24=promedio24();

    }

    /**
     * retorna el máximo de todos los valores de la variable, actualmente almacenados en el servidor
     * @return  máximo de todos los valores de la variable
     *
     */
    private float maximo(){
        Float maxima = VariablesTotales.get(0);
        int indiceMax =0;
        for (int i=0;i< VariablesTotales.size();i++){
            if (VariablesTotales.get(i)> maxima){
                maxima = VariablesTotales.get(i);
                indiceMax  = i ;
            }
        }

        String [] fechasHoras = Fechas.get(indiceMax).split("/");
        mensaje+= "\n"+ "Máximo histórico: " + maxima + unidades +"\n"+"Fecha: " +fechasHoras[1] + "/"+
        fechasHoras[0] + "/" + fechasHoras[2] + "\n" ;
        return maxima;
    }


    /**
     * retorna  el máximo de los últimos 24 valores de la variable, actualmente almacenados en el servidor
     * @return  máximo de los últimos 24 valores de la variable
     *
     */

    private float maximo24(){
        Float maxima = VariablesRecientes.get(0);
        int indiceMax =0;
        for (int i=0;i< VariablesRecientes.size();i++){
            if (VariablesRecientes.get(i)> maxima){
                maxima = VariablesRecientes.get(i);
                indiceMax  = i;
            }
        }
        String [] fechasHoras = FechasLatest.get(indiceMax).split("//");
        String [] dates = fechasHoras[0].split("/");
        mensaje += "\n"+ "Máximo en las últimas "+ VariablesRecientes.size() +" horas : " + maxima + unidades+ "\n"+ "Fecha: " + dates[1] +
                "/" + dates[0] + "/" + fechasHoras[1]  + "\n";
        return maxima;
    }

    /**
     * retorna el mínimo de todos los valores de la variable, actualmente almacenados en el servidor
     * @return  mínimo de todos los valores de la variable
     *
     */
    private float minimo(){
        Float minima = VariablesTotales.get(0);
        int indiceMin =0;
        for (int i=0;i< VariablesTotales.size();i++){
            if (VariablesTotales.get(i)< minima){
                minima = VariablesTotales.get(i);
                indiceMin  =i;
            }
        }
        String [] fechasHoras = Fechas.get(indiceMin).split("/");
        mensaje += "\n"+"Mínimo histórico :" + minima + unidades  + "\n"+ "Fecha:" +fechasHoras[1] + "/"+
                fechasHoras[0] + "/" + fechasHoras[2]  + "\n";
        return minima;

    }

    /**
     * retorna  el mínimo de los últimos 24 valores de la variable, actualmente almacenados en el servidor
     * @return  mínimo de los últimos 24 valores de la variable
     *
     */
    private float minimo24(){
        Float minimo24 = VariablesRecientes.get(0);
        int indiceMin =0;
        for (int i=0;i< VariablesRecientes.size();i++){
            if (VariablesRecientes.get(i)< minimo24){
                minimo24 = VariablesRecientes.get(i);
                indiceMin  =i;
            }
        }
        String [] fechasHoras = FechasLatest.get(indiceMin).split("//");
        String [] dates = fechasHoras[0].split("/");
        mensaje += "\n"+ "Minimo en las últimas "+ VariablesRecientes.size()+" horas: " + minimo24+unidades + "\n"+"Fecha: " + dates[1] +
                "/" + dates[0] + "/" + fechasHoras[1]  + "\n";

        return  minimo24;
    }

    /**
     * retorna el promedio de todos los valores de la variable, actualmente almacenados en el servidor
     * @return  promedio de todos los valores de la variable
     *
     */
    private float promedio(){
        Float suma = 0.0f;
        for (Float f: VariablesTotales){
            suma += f;
        }
        Float promedio = suma/VariablesTotales.size();
        mensaje += "\n"+"Promedio histórico de " + variable +" es de "+ promedio + unidades  + "\n";
        return promedio;
    }

    /*** retorna  el promedio de los últimos 24 valores de la variable, actualmente almacenados en el servidor
     * @return  promedio de los últimos 24 valores de la variable*/

    private float promedio24(){
        Float suma = 0.0f;
        for (Float f: VariablesRecientes){
            suma += f;
        }
        Float promedio = suma/VariablesRecientes.size();
        mensaje += "\n"+"Promedio de "+ variable +" de las últimas "+ VariablesRecientes.size()+ " horas es " + promedio  + unidades  + "\n";
        return promedio;

    }

    /**
     * Retorna una String en representación del análisis realizado
     * @return  mensaje del análisis realizado
     *
     */
    public String getMensaje() {
        return mensaje;
    }

}
