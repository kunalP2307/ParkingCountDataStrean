#include <Servo.h>
Servo i;
byte IR_OUT_BLUE = 9;
byte IR_IN_ORANGE = 10;
byte VEHICLE_COUNT = 0;
byte BUZZER = 2;
byte SERVO = 11;

void setup(){
    pinMode(IR_OUT_BLUE, INPUT);
    pinMode(IR_IN_ORANGE, INPUT);
    pinMode(BUZZER, OUTPUT);
    i.attach(SERVO);
    Serial.begin(9600);
    init_barrier();
}

void loop(){
    // check for incoming vehicle
    if (digitalRead(IR_IN_ORANGE)== LOW){
      VEHICLE_COUNT += 1;
      Serial.println(VEHICLE_COUNT);
      beep_in();
      delay(1000);
      barrier_up();
      delay(1000);
      barrier_down();
    }
    
    // check for outgoing vehicle
    if(digitalRead(IR_OUT_BLUE)== LOW){
      VEHICLE_COUNT -= 1;
      Serial.println(VEHICLE_COUNT); 
      beep_out();
      delay(1000);
      barrier_up();
      delay(1000);
      barrier_down();
    }
}

//set the barrier to initial position
void init_barrier(){
  i.write(115);
}

void barrier_up(){
  for(int j=115; j>=40; j--){
      i.write(j);
      delay(50);
  }
}

void barrier_down(){
  for(int j=40; j<115; j++){
    i.write(j);
    delay(50);
  }
}

void beep_in(){
   digitalWrite(BUZZER, HIGH);
   delay(500);
   digitalWrite(BUZZER, LOW);
   delay(100);
   digitalWrite(BUZZER, HIGH);
   delay(100);
   digitalWrite(BUZZER, LOW);
}

void beep_out(){
   digitalWrite(BUZZER, HIGH);
   delay(500);
   digitalWrite(BUZZER, LOW);
   delay(100);
   digitalWrite(BUZZER, HIGH);
   delay(100);
   digitalWrite(BUZZER, LOW);
   delay(100);
   digitalWrite(BUZZER, HIGH);
   delay(100);
   digitalWrite(BUZZER, LOW);
}

