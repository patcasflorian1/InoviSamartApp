
 //float db=0;
const int sampleWindow = 50;                              // Sample window width in mS (50 mS = 20Hz)
 float sample;
              // ADS1115  @ +/- 6.144V gain = 0.1875mV/step
 

#define SENSOR_PIN A0  //A0 esp8266
#define MicSamples (1024*2)

 void setupSunet(){
   //analogReference(INTERNAL);
  // ADS.begin();
  
   pinMode (SENSOR_PIN, INPUT); // Set the signal pin as input  
 }

 String returnDecibel(){
 long signalAvg = 0, signalMax = 0, signalMin = 1024, t0 = millis();
    for (int i = 0; i < MicSamples; i++)
    {
        long k = analogRead(SENSOR_PIN);
        signalMin = min(signalMin, k);
        signalMax = max(signalMax, k);
        signalAvg += k;
    }
    signalAvg /= MicSamples;
 float db = ((float)(signalMax - signalAvg)/10)*12;
// if(db>115){
  db =db/10;
 //}
    // print
    Serial.print("Time: " + String(millis() - t0));
    Serial.print(" Min: " + String(signalMin));
    Serial.print(" Max: " + String(signalMax));
    Serial.print(" Avg: " + String(signalAvg));
    Serial.print(" Span: " + String(signalMax - signalMin));
    Serial.print(", " + String(signalMax - signalAvg));
    Serial.print(", " + String(signalAvg - signalMin));
    Serial.println("");
 Serial.println("db "); Serial.println(db );
String dbResult = String(db);
 Serial.print("Db = ");
 Serial.println(dbResult);
 return dbResult;
 }


 /*
 String returnDecibel(){
     unsigned long startMillis= millis();                   // Start of sample window
   float peakToPeak = 0;                                  // peak-to-peak level
   unsigned int signalMax = 0;                            //minimum value
   unsigned int signalMin = 1023;                         //maximum value
 
    while (millis() - startMillis < sampleWindow)
   {
      sample = analogRead(SENSOR_PIN); 
      Serial.print("Sunet: ");
       Serial.println(sample);                    //get reading from microphone
      if (sample < 1024)                                  // toss out spurious readings
      {
         if (sample > signalMax)
         {
            signalMax = sample;                           // save just the max levels
         }
         else if (sample < signalMin)
         {
            signalMin = sample;                           // save just the min levels
         }
      }
   }
 
   peakToPeak = signalMax - signalMin;                    // max - min = peak-peak amplitude
   float db = map(peakToPeak,20,900,49.5,90);             //calibrate for deciBels
   if(db>40){
     db = db-20 ;
    }
  Serial.print("Loudness: ");
  Serial.print(db);
  Serial.println("dB");
              
 String dbResult = String(db);
 Serial.print("Db = ");
 Serial.println(dbResult);
 return dbResult;
 }
 */