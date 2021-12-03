#include <16f877a.h>
#device adc=10
#include <stdio.h>
#fuses XT,NOWDT,NOPROTECT,NOLVP,PUT,BROWNOUT
#use DELAY (CLOCK=4MHz)
#use rs232 (baud=9600,parity=N,xmit=pin_c6,rcv=pin_c7,bits=8)

#DEFINE LED PORTC, 0
#BYTE PORTC = 0X07

float tiempo;
int distancia;
int dato;

int Timer2, Postcaler;
int16 duty;

void encenderLed();
void apagarLed();


#define trigger pin_B1
#define echo pin_B0
#int_rda // Interrupcuón por recepción de un caracter
void rda_isr(){
   dato = getc();
   
   duty = dato*1024/200;
}

void encenderLed(){
   Set_Tris_C(0b11111110); // Se configura el bit 0 del puerto B como salida
   bit_set(LED);
   delay_ms(1000);
   Bit_clear(LED);;
}

void apagarLed(){
   Bit_clear(LED);
}
float leersensor1(){
   float valor;
   setup_adc_ports(ALL_ANALOG);
   setup_adc(adc_clock_div_16);
   
   set_adc_channel(0);
   delay_us(50);
   valor = ((read_adc())*100.0/1023);
   return valor;
}

float leersensor2(){
   float valor;
   setup_adc_ports(ALL_ANALOG);
   setup_adc(adc_clock_div_16);
   
   set_adc_channel(1);  
   delay_us(50);
   valor = ((read_adc()*1.0));
   return valor;   
}

void main(){
   //enable_interrupts(int_rda);
   //enable_interrupts(global);
   setup_timer_1(T1_internal|T1_div_by_1);   

   while(true){
      output_high(trigger);
      delay_us(10);
      output_low(trigger);
      
      float dato_1 = leersensor1();
      float dato_2 = leersensor2();
      
      while(!input(echo));
      set_timer1(0);
      
      while(input(echo));
      tiempo = get_timer1();
      distancia=(tiempo/2)*(0.0343);
      
      //printf("Hola");
      printf("\ %d %f %f\n", distancia, dato_1, dato_2);
      //putc(distancia);
      //putc(dato_1);
      
      delay_ms(500);
   }
}
