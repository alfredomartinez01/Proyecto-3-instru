/*--------------------INCLUÍMOS LIBRERÍAS Y VARIABLES--------------------*/
#include <16f877a.h>
#device adc=10
#include <stdio.h>
#fuses XT,NOWDT,NOPROTECT,NOLVP,PUT,BROWNOUT
#use DELAY (CLOCK=4MHz)
#use rs232 (baud=9600,parity=N,xmit=pin_c6,rcv=pin_c7,bits=8)

/*------------------------DECLARACIÓN DE VARIABLES-----------------------*/
#define trigger pin_B1 // Variable del pin de trigger en el sensor
#define echo pin_B0 // Variable del pin de echo en el sesnsor

int Timer2, Postcaler; // Variables de la carga del timer2 y su poscaler
int16 duty; 

/*------------------------DECLARACIÓN DE FUNCIONES-----------------------*/
// Leemos la temperatura
float leerRTD(){
   float temperatura;
   
   // Configuramos el contador
   setup_adc_ports(ALL_ANALOG);
   setup_adc(adc_clock_div_16);
   
   // Configuramos el canal
   set_adc_channel(0);
   delay_us(50);
   
   // Leemos y calculamos la temperatura
   temperatura = ((read_adc())*100.0/1023);
   return temperatura;
}

// Leemos el tiempo y calculamos distancia
float leerUTSC(){
   float tiempo;
   float distancia;
   
   // Enviamos el trigger
   output_high(trigger);
   delay_us(10);
   output_low(trigger);
   
   // Medimos el tiempo
   while(!input(echo));
   set_timer1(0);
   
   while(input(echo));
   tiempo = get_timer1();
   
   // Convertimos a distancia
   distancia=(tiempo/2)*(0.0343);
   return distancia;
}


void main(){
   setup_timer_1(T1_internal|T1_div_by_1);
   
   Timer2=249;
   Postcaler=1;   
   setup_timer_2(t2_div_by_4,Timer2,Postcaler);
   setup_ccp1(ccp_pwm);
   
   while(true){
      int distancia = leerUTSC(); // Leemos el sensor para distancia
      int temperatura = leerRTD(); // Leemos el sensor para la emperatura
      
      // Activamos/desactivamos el motor con base en la distancia
      if(distancia < 8)
         set_pwm1_duty(0);
      else
         set_pwm1_duty(1000);
      
      // Enviamos la distancia
      putc(255);
      delay_ms(10);
      putc(distancia);
      
      delay_ms(100);
      
      // Enviamos la temperatura
      putc(254);
      delay_ms(10);
      putc(temperatura);
      
      delay_ms(100);
   }
}
