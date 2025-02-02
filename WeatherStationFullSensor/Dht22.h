//#include <MQ135.h>
#include <DHT.h>
//#include "ADS1X15.h" 
 #include <Adafruit_ADS1X15.h>
//ADS1115 ADS(0x48);
 Adafruit_ADS1115 anaDigConverter;

#define DHTPIN 5     // Digital pin connected to the DHT sensor D1
//#define PIN_MQ135 A0 // MQ135 Analog Input Pin
// Uncomment the type of sensor in use:
//#define DHTTYPE    DHT11     // DHT 11
#define DHTTYPE    DHT22     // DHT 22 (AM2302)
//#define DHTTYPE    DHT21     // DHT 21 (AM2301)
//MQ135 mq135_sensor(PIN_MQ135);
DHT dht(DHTPIN, DHTTYPE);
// current temperature & humidity, updated in loop()
float t = 0.0;
float h = 0.0;
float newT = 0;
 float newH = 0;
float temperature, humidity; // Temp and Humid floats, will be measured by the DHT
float multiplier = 0.00845F;  

void setupDht22(){
  pinMode(DHTPIN, INPUT);
dht.begin();
anaDigConverter.begin();
}

String readTemperature(){
    newT = dht.readTemperature();

        // newT = newT-2.3 ;
    // Read temperature as Fahrenheit (isFahrenheit = true)
    //float newT = dht.readTemperature(true);
    // if temperature read failed, don't change t value
    if (isnan(newT)) {
      Serial.println("Failed to read from DHT sensor!");
      t = 0;
    }
    else {
      t = newT;
     // temperature = map(t,-30.5,60.5,-30.5,60.5);
      Serial.println(t);
    }
    return String(t);
}

String readHumidity(){

    newH = map(dht.readHumidity(),20.6,69.5,40,75);
   // newH = dht.readHumidity();
    // if humidity read failed, don't change h value 
    if (isnan(newH)) {
      Serial.println("Failed to read from DHT sensor!");
      h = 0;
    }
    else {
      h = newH;
      Serial.println(h);
    }
    return String(h);
}

String readQualityAir(){
//humidity =  map(dht.readHumidity(),20.6,69.5,40,75);
  //temperature = dht.readTemperature();
  //temperature = temperature ;
//String resultQualityAir;
float qualityAir = 0;
  // Check if any reads failed and exit early (to try again).
  /*
  if (isnan(humidity) || isnan(temperature)) {
    Serial.println(F("Failed to read from DHT sensor!"));
    return String(qualityAir);
  }
else{
  float rzero = mq135_sensor.getRZero();
  float correctedRZero = mq135_sensor.getCorrectedRZero(temperature, humidity);
  float resistance = mq135_sensor.getResistance();
  float ppm = mq135_sensor.getPPM();
  float correctedPPM = mq135_sensor.getCorrectedPPM(temperature, humidity);
  
  Serial.print("MQ135 RZero: ");
  Serial.print(rzero);
  Serial.print("\t Corrected RZero: ");
  Serial.print(correctedRZero);
  Serial.print("\t Resistance: ");
  Serial.print(resistance);
  Serial.print("\t PPM: ");
  Serial.print(ppm);
  Serial.print("ppm");
  Serial.print("\t Corrected PPM: ");
  Serial.print(correctedPPM);
  Serial.println("ppm");
 Serial.print("\t Calitate aer: ");
  Serial.print((correctedPPM*44.01*24.45)/(22.4*1000000));
  Serial.println("ppm");
   //qualityAir = (((correctedPPM*28.01*24.45)/(22.4*1000000))*100);
   */
 
      qualityAir = anaDigConverter.readADC_Differential_0_1();
       Serial.print("Air "); Serial.println(qualityAir);
          qualityAir = qualityAir*multiplier;         
     

  return String(qualityAir);

}