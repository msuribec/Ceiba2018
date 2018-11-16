import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Recibir {


    private ArrayList<Float> Temperaturas = new ArrayList<>();
    private ArrayList<Float> Resistencias = new ArrayList<>();
    ArrayList<Date> Fechas = new ArrayList<>();
    String username="" ;

    Recibir(String username) {
        recibirTodos(username);
        this.username = username;
    }

    public class Sample {
        float temp;
        float resistencia;
        Date timestamp;
    }

    private void recibirTodos(String username){
        String service1 = "http://iotprojecteafit.herokuapp.com/registers/"+username;
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

    ArrayList<Float> getUltimasTemp() {
        ArrayList<Float> TemperaturasLatest = new ArrayList<>();
        CalcUltimas(Temperaturas,TemperaturasLatest);
        return TemperaturasLatest;
    }

    ArrayList<Float> getUltimasResist() {
        ArrayList<Float> ResistenciasLatest = new ArrayList<>();
        CalcUltimas(Resistencias,ResistenciasLatest);
        return ResistenciasLatest;
    }

    ArrayList<String> getUltimasFechas() {
        ArrayList<String> FechasLatest = new ArrayList<>();
        ArrayList<Date> Fechas2 = new ArrayList<>();
        CalcUltimas(Fechas,Fechas2);
        for (Date d :Fechas2){
            DateFormat targetFormat = new SimpleDateFormat("MM/dd//HH:mm");
            String formattedDate = targetFormat.format(d);
            FechasLatest.add(formattedDate);
        }
        return FechasLatest;
    }

    ArrayList<Double> getPuntosTemp() {
        ArrayList <Float> list = getUltimasTemp();
        ArrayList <Double> nuevalista = new ArrayList<>();
        for(int i = 0;i< list.size();i++){
            Double f = (double)list.get(i);
            nuevalista.add((double)i);
            nuevalista.add(f);
        }
      return nuevalista;
    }

    ArrayList<Double> getPuntosResist() {
        ArrayList <Float> list = getUltimasResist();
        ArrayList <Double> nuevalista = new ArrayList<>();
        for(int i = 0;i< list.size();i++){
            Double f = (double)list.get(i);
            nuevalista.add((double)i);
            nuevalista.add(f);
        }
        return nuevalista;
    }

    ArrayList<Float> getTemperaturas() {
        return Temperaturas;
    }

    ArrayList<Float> getResistencias() {
        return Resistencias;
    }

    ArrayList<String> getFechas() {
        DateFormat targetFormat = new SimpleDateFormat("MM/dd/HH:mm");
        ArrayList<String> FechasTodas = new ArrayList<>();
        for(Date d: Fechas){
            String s = targetFormat.format(d);
            FechasTodas.add(s);
        }
        return FechasTodas;
    }
}