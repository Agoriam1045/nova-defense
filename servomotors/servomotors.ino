#include <Servo.h>

// Define servo objects
Servo servo1;
Servo servo2;
Servo servo3;

// Pins for the servos
int servo1Pin = 9;
int servo2Pin = 10;
int servo3Pin = 11;

// Variables to store the current angle of servos
int angle1 = 90; // initial angle for servo1
int angle2 = 90; // initial angle for servo2
int angle3 = 30; // initial angle for servo3

// Define the increment for changing the angle
// int angleIncrement = 10;

// Joystick pins
const int VRX = A0;
const int VRY = A1;
const int SW = 7;

// Variables to store joystick values
int joyX, joyY;
bool buttonPressed = false;

unsigned long lastUpdateTime = 0; // variable to store the last update time

void setup() {
  // Attach servos to their pins
  servo1.attach(servo1Pin);
  servo2.attach(servo2Pin);
  servo3.attach(servo3Pin);

  // Initialize servos to their initial position
  servo1.write(angle1);
  servo2.write(angle2);
  servo3.write(angle3);

  // Set joystick button pin as input
  pinMode(SW, INPUT_PULLUP);

  // Begin serial communication at 9600 baud rate
  Serial.begin(9600);
}

void loop() {
  // Read joystick values
  joyX = analogRead(VRX);
  joyY = analogRead(VRY);

  // Map joystick values to servo angles
  int servo1Angle = map(joyX, 0, 1023, 0, 180);
  int servo2Angle = map(joyY, 0, 1023, 0, 180);

  // Control servo1 (left-right movement)
  if (servo1Angle != angle1) {
    angle1 = servo1Angle;
    servo1.write(angle1);
  }

  // Control servo2 (up-down movement)
  if (servo2Angle != angle2) {
    angle2 = servo2Angle;
    servo2.write(angle2);
  }

  // Print servo angles every 2 seconds
  if (millis() - lastUpdateTime >= 2000) {
    Serial.print("Servo 1 angle: ");
    Serial.println(angle1);
    Serial.print("Servo 2 angle: ");
    Serial.println(angle2);
    lastUpdateTime = millis();
  }

  // Check joystick button press
  if (digitalRead(SW) == LOW && !buttonPressed) {
    buttonPressed = true;
    servo3.write(angle3);
    delay(1000);
    servo3.write(180);
    delay(1000);
    Serial.println("Pew! Pew! Pew!");
  } else if (digitalRead(SW) == HIGH) {
    buttonPressed = false;
  }
}
