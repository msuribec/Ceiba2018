 //LIBRERIA: DHT11 SENSOR (TEMPERATURA)
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <SimpleDHT.h>


// VARIABLES
int SENSOR = 2; //PIN ANALOGO 5 Sensor Temperatura
float temperatura;
float resistencia; //Sensor de fotoresistencia
DHT dht (SENSOR, DHT11); //(DHT(Tipo de dato) Existente en la libreria DHT
// Se le indica en que pin analogo se encuentra el sensor, y que tipo de sensor es (DHT11)

void setup() {
  Serial.begin (9600); //INICIA EL ARDUINO EN EL MONITOR SERIAL
  //Inicializar sensor
  dht.begin();
}

void loop() {
  //SENSOR TEMPERATURA
  temperatura = dht.readTemperature(); //Función implementada en la libreria DHT
  //Imprime los datos
  Serial.print ("Temperatura: ");
  Serial.print (temperatura);
  Serial.print ("°C");
  Serial.println(" ");
  Serial.println();
resistencia=analogRead(A0);
Serial.print ("Resistencia de LDR");
Serial.println(" ");
Serial.print (resistencia);
Serial.println();
Serial.println("");
delay(5000); //Tiempo de espera de impresión
}
