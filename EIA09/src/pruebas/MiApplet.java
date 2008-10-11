/**
 * 
 */
package pruebas;

/**
 * @author Luis
 *
 */
import java.applet.*;
import java.awt.*;
import java.util.*;
import java.text.DateFormat;

public class MiApplet extends Applet implements Runnable
{
   private Thread hilo = null;
   private Font fuente;
   private String horaActual = "00:00:00";

   public void init()
   {  
      fuente = new Font("Verdana", Font.BOLD, 24);
   }
   public void start()
   {
      if (hilo == null)
      {
         hilo = new Thread(this, "Reloj");
         hilo.start();
      }
   }  
   public void run()
   {
      Thread hiloActual = Thread.currentThread();
      while (hilo == hiloActual)
      {
         //obtener la hora actual
         Calendar cal = Calendar.getInstance();
         Date hora = cal.getTime();
         DateFormat df = DateFormat.getTimeInstance();
         horaActual = df.format(hora);
         repaint();
         try
         {
            Thread.sleep(1000);
         }
         catch (InterruptedException e){}
      }
   }  
   public void paint(Graphics g)
   {   
      //Dibujar un rectangulo alrededor del contenedor
      g.draw3DRect(1, 1, getSize().width-3, getSize().height-3, false);
      //Establecer la Fuente
      g.setFont(fuente);
      //mostrar la Hora
      g.drawString(horaActual,14,40);
   }   
   public void stop()
   {   
      hilo = null;
   }   
}

