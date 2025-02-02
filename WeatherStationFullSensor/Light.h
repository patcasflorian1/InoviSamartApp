#include <BH1750.h>
BH1750 lightMeter (0x23);
void setupLight(){
  lightMeter.begin();

  Serial.println(F("BH1750 Test begin"));
}

String readLight(){
float lux = lightMeter.readLightLevel();
String resultLight;
  Serial.print("Light: ");
  Serial.print(lux);
  Serial.println(" lx");
  resultLight = String(lux);
 // delay(1000);
return resultLight;
}