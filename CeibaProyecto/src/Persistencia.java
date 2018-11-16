
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

class Persistencia {


    public Persistencia(String username) {
        String mensajeTemp = new Analitica(username,true).getMensaje();
        String mensajeResist = new Analitica(username,false).getMensaje();
        guardar(mensajeTemp,"Temperatura");
        guardar(mensajeResist,"FotoResistencia");
    }

    public void guardar(String mensaje,String variable) throws NullPointerException {

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




}
