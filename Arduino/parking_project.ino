#include <Servo.h>
Servo i;
byte IR_OUT_BLUE = 9;
byte IR_OUT_ORANGE = 10;
byte LED = 11;
byte PERSON_COUNT = 0;
byte ORANGE_CROSSED = 0;
byte BLUE_CRODDED = 0;

void setup(){
//pinMode(IR_OUT_BLUE,INPUT);
//pinMode(IR_OUT_ORANGE,INPUT);
//pinMode(LED,OUTPUT);
// 7 segment display
    pinMode(2, OUTPUT);
    pinMode(3, OUTPUT);
    pinMode(4, OUTPUT);
    pinMode(5, OUTPUT);
    pinMode(6, OUTPUT);
    pinMode(7, OUTPUT);
    pinMode(8, OUTPUT);
    pinMode(IR_OUT_BLUE,INPUT);
    pinMode(IR_OUT_ORANGE,INPUT);
    pinMode(LED,OUTPUT);
    i.attach(11);
    Serial.begin(9600);
}

void loop(){
    if (digitalRead(IR_OUT_ORANGE)== LOW){
      delay(1000);
      if(PERSON_COUNT == 0){
        digitalWrite(LED,HIGH);i.write(70);
      }
      PERSON_COUNT += 1;
      Serial.println(PERSON_COUNT);
      Num_Write(PERSON_COUNT);
    }
    if(digitalRead(IR_OUT_BLUE)== LOW){
      delay(1000);
      if(PERSON_COUNT == 0){
        Serial.println(PERSON_COUNT);
      }
      else if(PERSON_COUNT == 1){
        digitalWrite(LED,LOW);
        i.write(15);
        PERSON_COUNT -= 1;
        Serial.println(PERSON_COUNT);
        Num_Write(PERSON_COUNT);
      }
      else{
        PERSON_COUNT -= 1;
        Serial.println(PERSON_COUNT);
        Num_Write(PERSON_COUNT);
      }
    }
}

void barrier_up(){
  for(int j=120; j>=40; j--){
      i.write(j);
      delay(50);
  }
}

void barrier_down(){
  for(int j=40; j<120; j++){
    i.write(j);
    delay(50);
  }
}