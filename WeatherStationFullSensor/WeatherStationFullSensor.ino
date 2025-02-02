#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <Wire.h>
#include <SPI.h>
#include "ReadWriteEeprom.h"
#include "lcd.h"
#include "APServer.h"
#include "WiFiConnect.h"
#include "pushButton.h"
#include "Light.h"
#include "Dht22.h"
#include "Sunet.h"

//const char* serverAddress = "192.168.100.13"; //local
//const int serverPort = 8080; // Portul HTTP este de obicei 80 local
//const char* serverAddress = "europartsvending.ro"; wan
//const int serverPort = 80; // Portul HTTP este de obicei 80 wan
const char* serverAddress = "inovismartapp.ro";
const int serverPort = 80; // Portul HTTP este de obicei 80 wan
WiFiClient client;
elapsedMillis loginContor = 0; //declare global if you don't want it reset every time loop runs
const int loginTime = 30000; //60000millisec=1minut;
elapsedMillis timeElapsedLoop = 0; //declare global if you don't want it reset every time loop runs
elapsedMillis timeElapsedParam = 0; //declare global if you don't want it reset every time loop runs
const int intervalConnectLoop = 600000; //60000millisec=1minut;
#define soundPin 0  //D3
#define qualityAirPin 15 //D8
struct Machine{
  String macAddress;
  String getTemperature;
  String getHumidity;
  String getLight;
  String getpollutionCo;
  String getDecibel;
  String getpAirQuality;
  String getSignalLevel;
};
Machine machine = {"","","","","","","",""};
String compareTemperature;
String compareHumidity;
String compareLight;
String comparePollutionCo;
String compareDecibel;
String compareAirQuality;
bool isConnect = true;
bool isClear = true;
bool isBaleaje = true;
int paramInterval = 2500;  // set time for read parameter

void setup() {
 Wire.begin(14,12); //SDA,SCL
  Serial.begin(115200);
  EEPROM.begin(2048);
  setupLight();
  setupLcd();
  setupDht22();
  setupSunet();
  //MQ135 fasSensor = MQ135(A0);
  setupButton();
  pinMode(soundPin,OUTPUT);
  pinMode(qualityAirPin,OUTPUT);
  digitalWrite(soundPin,LOW);
  digitalWrite(qualityAirPin,LOW);
  Serial.println(digitalRead(BUTTON_PIN_2));

  if(digitalRead(BUTTON_PIN_2) == LOW){
     EEPROMWrite(EEaddress, 0);
       writeWord("aaa",indexEeprom.indexSsid); 
       EEPROM.commit();
         displays.clearDisplay(); 
        delay(100);
        setup();  
}
  displays.clearDisplay();
displayText("Set Pssword",2, 1,1);
 myStringSSID = readWord(indexEeprom.indexSsid);
 myStringPassword = readWord(indexEeprom.indexPassword);
   Serial.print("ssid ");
   Serial.println(myStringSSID );
 Serial.print("password ");
 Serial.println(myStringPassword );
 startWiFi( myStringSSID, myStringPassword); 
displayText(WiFi.softAPIP().toString(),1,0,0);
 // attachInterrupt(coinPin.PIN, isr, FALLING); 
   displays.clearDisplay();
  // loginContor = 70000;
}

void loop() {
    Serial.print("Ssd ");
 Serial.println(myStringSSID);
 Serial.print("Password ");
 Serial.println(myStringPassword);
  if(isConnect){
   //  myStringSSID = readWord(indexEeprom.indexSsid);
 //myStringPassword = readWord(indexEeprom.indexPassword);
  isConnect =false;
 // WiFi.begin(myStringSSID, myStringPassword);
   WiFi.setAutoReconnect(true);
  WiFi.persistent(true);
  }

  while (WiFi.status() != WL_CONNECTED) {
    delay(10);
    displayText(WiFi.softAPIP().toString(),1,0,0);
    displayText("NuexistaconexiuneWIFI",1,0,10);
    displayText("SSID: ",1,0,20);
    displayText(myStringSSID,1,30,20);
    displayText("Pass: ",1,0,40);
    displayText(myStringPassword,1,30,40);
    Serial.print(".");  
   server.handleClient();
   if(isClear){
    displays.clearDisplay();
    isClear = false;
   }
   if(timeElapsedLoop>intervalConnectLoop){
    ESP.restart();
    timeElapsedLoop = 0;
   }
  }
  if(WiFi.status() == WL_CONNECTED){
    if(isClear == false){
      displays.clearDisplay();
      isClear=true;
     
    }
    IPAddress ip = WiFi.localIP();
    String macAddress = String(WiFi.macAddress());
  String textDisplay = "WIFI:"+myStringSSID;
  Serial.print("Adresa IP Locala: ");
  Serial.println(ip);
   displayText(textDisplay,1,0,0);
    displayText(macAddress,1,0,10);
   WiFi.softAPdisconnect (true);
   
 }

if(digitalRead(BUTTON_PIN_2) == LOW){
       writeWord("aaa",indexEeprom.indexSsid); 
       EEPROM.commit();
        displays.clearDisplay();  
        delay(100);
        setup();  
}

   /*
  if((loginContor>loginTime)||(!compareTemperature.equals(machine.getTemperature))||(!compareHumidity.equals(machine.getHumidity))
  ||(!compareLight.equals(machine.getLight))||(!comparePollutionCo.equals(machine.getpollutionCo))||(!compareDecibel.equals(machine.getDecibel))){
    */
    if(timeElapsedParam>paramInterval){

      timeElapsedParam = 0;
     machine.getTemperature= readTemperature();
  machine.getHumidity = readHumidity();
  machine.getLight = readLight();
  machine.getpollutionCo= readQualityAir();
  machine.getDecibel = returnDecibel();
  machine.macAddress = String(WiFi.macAddress());
  machine.getSignalLevel = String(WiFi.RSSI());
    }
    
    String finalTemperature = "T="+machine.getTemperature+",";
  String finalHumidity = "H="+machine.getHumidity;
   String finalLux = "Lux="+machine.getLight;
   String finalDecibel = "DB="+machine.getDecibel;
   String finalAirQuality = " AirQ="+machine.getpollutionCo;
   displayText(finalTemperature+finalHumidity,1,0,20);
   displayText(finalLux,1,0,30);
   displayText(finalDecibel+finalAirQuality,1,0,40);
    if(loginContor>loginTime){
  displays.clearDisplay();
  loginContor = 0;
  delay(50);
 if (client.connect(serverAddress, serverPort)) {

String mac =  "MAC:"+String(WiFi.macAddress());
 displayText(mac,1,0,10);
   Serial.print("loginContor ");
Serial.println(loginContor);
  
 
 //compareTemperature = readTemperature();
 //compareHumidity = readHumidity();
// compareLight = readLight();
 //comparePollutionCo = readQualityAir();
 //compareDecibel = returnDecibel();
 // display.clearDisplay(); 

  //Send data to WAN
 //Serial.println("compareDecibel :" +compareDecibel);
  
    Serial.println("machine.getDecibel :" +machine.getDecibel);
client.println(String("GET /getTcpData.htm?macAddress="+String(machine.macAddress)+"&temperature="+machine.getTemperature+"&humidity="+machine.getHumidity+"&light="+machine.getLight+
       "&pollutionCo="+machine.getpollutionCo+"&pollutionGases="+machine.getDecibel+"&signalLevel="+machine.getSignalLevel+" HTTP/1.1\r\n") +
                 "Host: " + serverAddress + "\r\n" +
                 "Connection: close\r\n\r\n");
  /*
  //Send data to LAN
client.print(String("GET /InoviSmartApp/getTcpData.htm?macAddress="+String(machine.macAddress)+"&temperature="+machine.getTemperature+"&humidity="+machine.getHumidity+"&light="+machine.getLight+
       "&pollutionCo="+machine.getpollutionCo+"&pollutionGases="+machine.getDecibel+"&signalLevel="+machine.getSignalLevel+" HTTP/1.1\r\n") +
                 "Host: " + serverAddress + "\r\n" +
                 "Connection: close\r\n\r\n");
        */ 
  } else {
    Serial.println("Conectare eșuată :(");
      
  }

  }
  delay(200);
  displays.clearDisplay();
}
