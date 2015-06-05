
int control_pin = 3;

int state = 0;
int photoPin = A7;
int lightLevel = 0;

int toggle(String command) {
    lightLevel = analogRead(photoPin);
    digitalWrite(control_pin, HIGH); 
    state = 1;
    return lightLevel;
}

int light(String command) {
    lightLevel = analogRead(photoPin);
    return lightLevel;
}

void setup()
{
  pinMode(control_pin, OUTPUT);      // sets the digital pin as output
  Spark.function("toggle", toggle);
  Spark.variable("state", &state, INT);
  Spark.function("light", light);

 	 
  RGB.control(true);
  RGB.brightness(0);
}

void loop()
{
  lightLevel = analogRead(photoPin);
  
  if (state > 0) {
    delay(1000);
    digitalWrite(control_pin, LOW);    
    state = 0;
  }
  delay(50);
}
