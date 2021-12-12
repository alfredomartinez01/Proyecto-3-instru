/*--------------------INCLUÍMOS LIBRERÍAS Y VARIABLES--------------------*/
#include <16f877a.h>
#device adc=10
#include <stdio.h>
#fuses XT,NOWDT,NOPROTECT,NOLVP,PUT,BROWNOUT
#use DELAY (CLOCK=4MHz)
#use rs232 (baud=9600,parity=N,xmit=pin_c6,rcv=pin_c7,bits=8)

#DEFINE LED PORTC, 0
#BYTE PORTC = 0X07

/*------------------------DECLARACIÓN DE FUNCIONES-----------------------*/
// Leemos la luz
float leerLDR(){
   float luz;
   
   // Configuramos el contador
   setup_adc_ports(ALL_ANALOG);
   setup_adc(adc_clock_div_16);
   
   // Configuramos el canal
   set_adc_channel(1);
   delay_us(50);
   
   // Leemos y calculamos la luz
   luz = ((read_adc()*100.00)/1023);
   return luz;
}

void main(){

   while(true){
         int luz = leerLDR(); // Leemos el sensor para luz
         
         // Enviamos la luz
         putc(253);
         delay_ms(5);
         putc(luz);
         delay_ms(20);
   }
}
