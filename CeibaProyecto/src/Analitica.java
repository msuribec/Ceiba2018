import java.util.*;

class Analitica {

    private List<String> FechasLatest;
    private List<String> Fechas ;
    private String mensaje = "";
    private float maximo;
    private float maximo10;
    private float minimo;
    private float minimo10;
    private float promedio;
    private float promedio10;
    private List <Float> VariablesTotales;
    private List <Float> VariablesRecientes;
    private String variable;
    private String unidades;

    Analitica(String username, boolean temp) {

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
        this.maximo10= maximo10();
        this.minimo= minimo();
        this.minimo10= minimo10();
        this.promedio= promedio();
        this.promedio10=promedio10();

    }


    private float maximo(){
        Float maxima = VariablesTotales.get(0);
        int indiceMax =0;
        for (int i=0;i< VariablesTotales.size();i++){
            if (VariablesTotales.get(i)> maxima){
                maxima = VariablesTotales.get(i);
                indiceMax  =i;
            }
        }
        String [] fechasHoras = Fechas.get(indiceMax).split("/");
        mensaje+= "\n"+ "Máximo histórico: " + maxima + unidades +"\n"+"Fecha: " +fechasHoras[1] + "/"+
        fechasHoras[0] + "/" + fechasHoras[2] + "\n" ;
        return maxima;
    }

    private float maximo10(){
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

    private float minimo10(){
        Float minimo10 = VariablesRecientes.get(0);
        int indiceMin =0;
        for (int i=0;i< VariablesRecientes.size();i++){
            if (VariablesRecientes.get(i)< minimo10){
                minimo10 = VariablesRecientes.get(i);
                indiceMin  =i;
            }
        }
        String [] fechasHoras = FechasLatest.get(indiceMin).split("//");
        String [] dates = fechasHoras[0].split("/");
        mensaje += "\n"+ "Minimo en las últimas "+ VariablesRecientes.size()+" horas: " + minimo10+unidades + "\n"+"Fecha: " + dates[1] +
                "/" + dates[0] + "/" + fechasHoras[1]  + "\n";

        return  minimo10;
    }

    private float promedio(){
        Float suma = 0.0f;
        for (Float f: VariablesTotales){
            suma += f;
        }
        Float promedio = suma/VariablesTotales.size();
        mensaje += "\n"+"Promedio histórico de " + variable +" es de "+ promedio + unidades  + "\n";
        return promedio;
    }

    private float promedio10(){
        Float suma = 0.0f;
        for (Float f: VariablesRecientes){
            suma += f;
        }
        Float promedio = suma/VariablesRecientes.size();
        mensaje += "\n"+"Promedio de "+ variable +" de las últimas "+ VariablesRecientes.size()+ " horas es " + promedio  + unidades  + "\n";
        return promedio;

    }

    public String getMensaje() {
        return mensaje;
    }
}
