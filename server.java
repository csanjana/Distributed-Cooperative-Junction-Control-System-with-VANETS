
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.RenderingHints;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.util.*;




public class Server extends JPanel implements Runnable
{
protected Socket clientSocket;
static ArrayList<vehicle> vlist = new ArrayList();
protected void paintComponent(Graphics g) {
	super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setColor(Color.black);
     g.drawLine(470,0,470,470);
	 g.drawLine(0,470,470,470);
	 g.drawLine(0,570,470,570);
	 g.setColor(Color.black);
	 g.fillRect(0, 470, 470,100 );
	 g.setColor(Color.white);
	 g.fillRect(10, 510, 50,15 );
	 g.fillRect(90, 510, 50,15 );
	 g.fillRect(170, 510, 50,15 );
	 g.fillRect(250, 510, 50,15 );
	 g.fillRect(330, 510, 50,15 );
	 g.fillRect(410, 510, 50,15 );
	 
	 g.setColor(Color.black);
	 g.drawLine(470,570,470,760);
	 g.drawLine(570,570,570,760);
	 g.fillRect(470, 570, 100,190 );
	 g.setColor(Color.white);
	 g.fillRect(510, 580, 15,50 );
	 g.fillRect(510, 660, 15,50 );
	 g.setColor(Color.black);
	 g.drawLine(570,570,1350,570);
	 g.drawLine(570,470,1350,470);
	 g.fillRect(570, 470, 780,100 );
	 g.setColor(Color.white);
	 g.fillRect(580, 510, 50,15 );
	 g.fillRect(660, 510, 50,15 );
	 g.fillRect(740, 510, 50,15 );
	 g.fillRect(820, 510, 50,15 );
	 g.fillRect(900, 510, 50,15 );
	 g.fillRect(980, 510, 50,15 );
	 g.fillRect(1060, 510, 50,15 );
	 g.fillRect(1140, 510, 50,15 );
	 g.fillRect(1220, 510, 50,15 );
	 g.fillRect(1300, 510, 50,15 );
	 g.setColor(Color.black);
	 g.drawLine(570,0,570,470);
	 g.fillRect(470, 0, 100,470 );
	 g.setColor(Color.white);
	 g.fillRect(510, 10, 15,50 );
	 g.fillRect(510, 90, 15,50 );
	 g.fillRect(510, 170, 15,50 );
	 g.fillRect(510, 250, 15,50 );
	 g.fillRect(510, 330, 15,50 );
	 g.fillRect(510, 410, 15,50 );
	 g.setColor(Color.black);
	 g.fillRect(470, 470, 100,100 );
	 g.setColor(Color.white);
	 
for (vehicle v : vlist)
{
//System.out.println("here");
	if ( v.tvel == 0)
	 {
		 g.setColor(Color.red);
	 }
	else if ( v.tvel == 2)
	 {
		 g.setColor(Color.green);
	 }
	 else
	 {
	g.setColor(Color.white);
	 }
	g.fillOval(v.x, v.y, 10, 10);
	
   
//g2.drawOval(v.x, v.y, 10, 10);
}
}

public static void main(String[] args) throws IOException
   {
	Server e1 = new Server();
    ServerSocket serverSocket = null;
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(e1);
    f.setSize(1350,760);
    f.setLocation(0, 0);
    f.setVisible(true);
   
    try {
         serverSocket = new ServerSocket(10008);
         System.out.println ("Connection Socket Created");
         try {
              while (true)
                 {
                  System.out.println ("Waiting for Connection");
                  new Server (serverSocket.accept(),f);
                  f.repaint();
                 }
             }
         catch (IOException e)
             {
              System.err.println("Accept failed.");
              System.exit(1);
             }
        }
    catch (IOException e)
        {
         System.err.println("Could not listen on port: 10008.");
         System.exit(1);
        }
    finally
        {
         try {
              serverSocket.close();
         	}
         catch (IOException e)
             {
              System.err.println("Could not close port: 10008.");
              System.exit(1);
             }
        }
   }

private Server (Socket clientSoc, JFrame f)
   {
    clientSocket = clientSoc;
    //Thread t1 = new Thread(this);
    //t1.start();
    Runnable r= new movement(clientSoc,f);
    new Thread(r).start();
   }

public Server() {
// TODO Auto-generated constructor stub
}

public void run()
   {
    System.out.println ("New Communication Thread Started");

    try {
         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                                      true);  // writing to socket
         BufferedReader in = new BufferedReader(
                 new InputStreamReader( clientSocket.getInputStream())); // reading from the socket

         String inputLine = null;
         vehicle v2 = new vehicle();
         while((inputLine = in.readLine())!=null)
         {
         
          System.out.println ("Server: " + inputLine);
         
         // out.println(inputLine);

          /*if (inputLine.equals("Bye."))
              break;*/
         
      //   inputLine = in.readLine();   // msg = "x:y:color"
         String[] temp;
         temp = inputLine.split(":");
         v2.x = Integer.parseInt(temp[0]);
         v2.y = Integer.parseInt(temp[1]);
         
         System.out.println("tvel = " + v2.tvel);
       /* for ( vehicle v : vlist)
        {
        	System.out.println(v.x+ ": " + v.y);
        }*/
        //validate();
         System.out.println("1");
         repaint();
         System.out.println("2");
         /*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
         }
        
         out.close();
         in.close();
         clientSocket.close();
        }
    catch (IOException e)
        {
         System.err.println("Problem with Communication Server");
         System.exit(1);
        }
    }

class movement implements Runnable
{	Socket clientSocket;JFrame f;
	public movement(Socket clientSocket,JFrame f){
		this.clientSocket=clientSocket;
		this.f=f;
	}
	public void run()
	{
		System.out.println ("New Communication Thread Started");

	    try {
	         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
	                                      true);  // writing to socket
	         BufferedReader in = new BufferedReader(
	                 new InputStreamReader( clientSocket.getInputStream())); // reading from the socket

	         String inputLine = null;
	         vehicle v2 = new vehicle();
	         while((inputLine = in.readLine())!=null)
	         {
	         
	          System.out.println ("Server: " + inputLine);
	         
	         // out.println(inputLine);

	          /*if (inputLine.equals("Bye."))
	              break;*/
	         
	      //   inputLine = in.readLine();   // msg = "x:y:color"
	         String[] temp;
	         temp = inputLine.split(":");
	         v2.x = Integer.parseInt(temp[0]);
	         v2.y = Integer.parseInt(temp[1]);
	         v2.tvel = Integer.parseInt(temp[2]);
	         System.out.println("tvel = " +v2.tvel);
	       /* for ( vehicle v : vlist)
	        {
	        	System.out.println(v.x+ ": " + v.y);
	        }*/
	        //validate();
	         System.out.println("1");
	         f.repaint();
	         System.out.println("2");
	         /*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	         }
	        
	         out.close();
	         in.close();
	         clientSocket.close();
	        }
	    catch (IOException e)
	        {
	         System.err.println("Problem with Communication Server");
	         System.exit(1);
	        }
		
	}
}
class vehicle
{
volatile int x, y;
int tvel;
Color vcolor;
public vehicle(String x, String y)
{
this.x = Integer.parseInt(x);
this.y = Integer.parseInt(y);
//this.vcolor = Color.BLACK;
vlist.add(this);
//System.out.println(x + ": " + y + ": " + vcolor);
}
public vehicle() {
	/*this.x = 120;
	this.y = 120;*/
	vlist.add(this);
}
}
}


