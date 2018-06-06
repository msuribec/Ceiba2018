package Recoleccion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Una instancia de la clase Recibir obtiene los datos de las dos primeras variables guardadas en el servidor seg[un el id especificado
 */


public class Recibir {

    /**lista de valores almacenados en el servidor de la variable Temperatura */
    private ArrayList<Float> Temperaturas = new ArrayList<>();
    /**lista de valores almacenados en el servidor de la variable Fotorresistencia */
    private ArrayList<Float> Resistencias = new ArrayList<>();
    /**lista de Fechas */
    private ArrayList<Date> Fechas = new ArrayList<>();
    /** El id para acceder en el servidor */
    public String username="" ;

    /**
     * Construye e inicializa un nuevo Recibir
     * @param  username El id para acceder en el servidor
     */
    public Recibir(String username) {
        this.username = username;
        recibirTodos();
    }

    /**
     * La clase sample representa una muestra que tiene un valor de temperatura, uno de fotorresistencia y un fecha
     */
    public class Sample {
        /**Valor de la temperatura*/
        float temp;
        /**Valor de la resistencia*/
        float resistencia;
        /**Fecha en la cual se dan los valores*/
        Date timestamp;
    }

    /**
     * Obtiene todos los datos de las dos primeras variables del servidor  y establece las listas de temperaturas, fotrresistencias y fechas
     */
    private void recibirTodos(){
        String service1 = "http://iotprojecteafit.herokuapp.com/registers/"+this.username;
        try {
            URL urlservice = new URL(service1);
            URLConnection urlserviceconn = urlservice.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlserviceconn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                inputLine=inputLine.replace("{","").replace("[","").replace("]","");
                String []lineas = inputLine.split("}");
                for(String a:lineas){
                    if (a.charAt(0)==','){ a = a.substring(1); }
                    cargarMuestra(a);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Añade a las listas de temperaturas, fotrresistencias y fechas los datos contenidos en una string cuya forma sea la determinada por el servidor
     * @param line Linea de datos que contiene el servidor*/
    private void cargarMuestra(String line) {
        String[] tokens = line.split(",");
        Sample sample = new Sample();
        String [] variable1 = tokens[2].split(":");
        sample.temp = Float.parseFloat(variable1[1]);
        String [] variable2 = tokens[3].split(":");
        sample.resistencia = Float.parseFloat(variable2[1]);
        String[] fecha = tokens[5].split(":");
        String Fecha1 = fecha[1]+":"+fecha[2]+":"+fecha[3];
        Fecha1=Fecha1.replaceAll("\"","");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Temperaturas.add(sample.temp);
        Resistencias.add(sample.resistencia);
        try {
            sample.timestamp =  dateFormat.parse(Fecha1.replaceAll("Z$", "+0000"));
            Fechas.add(sample.timestamp);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**Añade a las listaReciente los últimos 24 datos de la lista
     * @param lista de la que se leen los últimos 2 valores
     * @param listaReciente lista a la que se agregan los últimos 24 valores
     * */
    private void CalcUltimas(ArrayList lista , ArrayList listaReciente) {
        if (lista.size() >=24){
            int hasta= lista.size()-1;
            int desde = lista.size()- 24;
            for (int i= desde;i <= hasta;i++){
                listaReciente.add(lista.get(i));
            }
        }else{
            listaReciente.addAll(lista);
        }
    }


    /**Retorna un ArrayList con las últimas 24 temperaturas del servidor
     * @return ArrayList con las últimas 24 temperaturas del servidor
     * */
    public ArrayList<Float> getUltimasTemp() {
        ArrayList<Float> TemperaturasLatest = new ArrayList<>();
        CalcUltimas(Temperaturas,TemperaturasLatest);
        return TemperaturasLatest;
    }

    /**Retorna un ArrayList tipo Float con las últimas 24 Fotorresistencias del servidor
     * @return ArrayList tipo Float con las últimas 24 Fotorresistencias del servidor
     * */
    public ArrayList<Float> getUltimasResist() {
        ArrayList<Float> ResistenciasLatest = new ArrayList<>();
        CalcUltimas(Resistencias,ResistenciasLatest);
        return ResistenciasLatest;
    }
    /**Retorna un ArrayList de strings con las últimas 24 fechas del servidor
     * @return ArrayList de strings con las últimas 24 fechas del servidor
     * */
    public ArrayList<String> getUltimasFechas() {
        ArrayList<String> FechasLatest = new ArrayList<>();
        ArrayList<String> Fechas2 = new ArrayList<>();
        ArrayList<String> Fechas3 = getFechas();
        CalcUltimas(Fechas3,Fechas2);
        for (String d :Fechas2){
            String[] DATES = d.split("/");
            FechasLatest.add(DATES[0]+"/"+DATES[1]+"//"+DATES[2]);
        }

        return FechasLatest;
    }


    /**Retorna un ArrayList con las Temperaturas seguidas del número de dato que ocupan dentro de la lista
     * @return ArrayList con las Temperaturas seguidas del número de dato que ocupan dentro de la lista
     * */
    public ArrayList<Double> getPuntosTemp() {
        ArrayList <Float> list = getUltimasTemp();
        ArrayList <Double> nuevalista = new ArrayList<>();
        for(int i = 0;i< list.size();i++){
            Double f = (double)list.get(i);
            nuevalista.add((double)i);
            nuevalista.add(f);
        }
      return nuevalista;
    }

    /**Retorna un ArrayList con las Fotorresistencias seguidas del número de dato que ocupan dentro de la lista
     * @return ArrayList con las Fotorresistencias seguidas del número de dato que ocupan dentro de la lista
     * */
    public ArrayList<Double> getPuntosResist() {
        ArrayList <Float> list = getUltimasResist();
        ArrayList <Double> nuevalista = new ArrayList<>();
        for(int i = 0;i< list.size();i++){
            Double f = (double)list.get(i);
            nuevalista.add((double)i);
            nuevalista.add(f);
        }
        return nuevalista;
    }

    /**Retorna un ArrayList con las temperaturas en que se han tomado los valores del servidor
     * @return ArrayList con las temperaturas en que se han tomado los valores del servidor
     * */
    public ArrayList<Float> getTemperaturas() {
        return Temperaturas;
    }

    /**Retorna un ArrayList con las fotorresistencias en que se han tomado los valores del servidor
     * @return ArrayList con las fotorresistencias en que se han tomado los valores del servidor
     * */
    public ArrayList<Float> getResistencias() {
        return Resistencias;
    }

    /**Retorna un ArrayList con la representación en String de las Fechas en que se han tomado los valores del servidor
     * @return ArrayList con la representación en String de las Fechas en que se han tomado los valores del servidor
     * */
    public ArrayList<String> getFechas() {
        Calendar cal = Calendar.getInstance(); // creates calendar
        ArrayList<String> FechasTodas = new ArrayList<>();
        cal.setTime(Fechas.get(0));
        for(int i =0;i< Fechas.size();i++){
            DateFormat targetFormat = new SimpleDateFormat("MM/dd/HH:mm");
            String s = targetFormat.format(cal.getTime());
            FechasTodas.add(s);
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
        return FechasTodas;
    }

    /**Retorna un ArrayList con las Fechas en que se han tomado los valores del servidor
     * @return ArrayList con las Fechas en que se han tomado los valores del servidor
     * */
    public ArrayList<Date> getDates() {
        ArrayList<String> FechasTotales = getFechas();
        ArrayList<Date> DatesTotales = new ArrayList<>();
        for (String s : FechasTotales){
            DateFormat targetFormat = new SimpleDateFormat("MM/dd/HH:mm");
            try{
                Date a = targetFormat.parse(s);
                DatesTotales.add(a);

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return  DatesTotales;
    }





}