#include <SPI.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

#define SCREEN_WIDTH 128 // OLED display width, in pixels
#define SCREEN_HEIGHT 64 // OLED display height, in pixels

// Declaration for an SSD1306 display connected to I2C (SDA, SCL pins)
#define OLED_RESET     -1 // Reset pin # (or -1 if sharing Arduino reset pin)
Adafruit_SSD1306 displays(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);

void setupLcd(){
  // Initialising the UI will init the display too.
  // SSD1306_SWITCHCAPVCC = generate display voltage from 3.3V internally
  if(!displays.begin(SSD1306_SWITCHCAPVCC, 0x3C)) { 
    Serial.println(F("SSD1306 allocation failed"));
    for(;;); // Don't proceed, loop forever
  }

  displays.invertDisplay(false);
  displays.setRotation(2);
  displays.display();
 // delay(2000); // Pause for 2 second

}

void displayText(String text,int sizeText,int xPosition,int yPosition) {
 
  displays.setTextSize(sizeText);      // Normal 1:1 pixel scale
  displays.setTextColor(WHITE); // Draw white text
  displays.setCursor(xPosition, yPosition);     // Start at top-left corner
 displays.println(text);
  displays.display();
  //delay(1000);
}
