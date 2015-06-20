//  street 2

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BallwithDVel extends JPanel implements Runnable {
  
Color color;
int diameter;
long delay;
private static int x;
private static int y;
static int vel; 
public static int check; // just to get it working
int nstreete = 0; // to check egn
int nstreets = 0; // to check slv
int StreetId=2;   // its streetid 
int egn=0; // no. of vehicels in its street
int IdIndex[][]= {{0,0,0,0},{0,0,0,0}}; // acts like council
int redstamp = 1; 
int engn=0; // number of vehicles in other street
int slv = 0;
int minslv = 99999, maxslv = -1;
int minstreet, maxstreet;
HashMap<Integer, InetAddress> h1; // to get ip address of leaders of other streets
int LOA = 80;
int LOD = 180;
int LONR1 = 250;
int LONR2 = 320;
static int tvel = 1;  // to modify velocity upon receiving green or red light
public static int flag; // to check if SL msg has been received 
int FinalPort = 5001;// should be taken from db
int greents;
static int already;
private int olength,owidth,oarea;
private int density;

public BallwithDVel(int xvelocity, int yvelocity) {
   color = Color.black;
    diameter = 5;
    delay = 40;
    x = 0;
    y = 490;
}

protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setColor(color);
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
	 if ( tvel == 0)
	 {
		 g.setColor(color.red);
	 }
	 if ( tvel == 2)
	 {
		 g.setColor(color.green);
	 }
    g.fillOval(x,y,10,10); //adds color to circle
   
    g2.drawOval(x,y,10,10); //draws circle
}

public void run() {
    while(isVisible()) {
        try {
            Thread.sleep(delay);
        } catch(InterruptedException e) {
            System.out.println("interrupted");
        }
        move();
        repaint();
    }
}

public void move() {
	if (x >= LOA) // loa
 		{
			x = x + tvel; // keeping the velocity constant
			if ( check == 0)
			{
				TestBroadcast t = new TestBroadcast();
				t.initialise();
			}
			
 		}
	/*if (y > 150)
	{
		y = y + 1;
	}*/
  else if (x < LOA)
	  {
   x = x + vel;
   //System.out.println(x + y);
   repaint();
	  }
}

private void start() {
    while(!isVisible()) {
        try {
            Thread.sleep(25);
        } catch(InterruptedException e) {
            System.exit(1);
        }
    }
    Thread thread = new Thread(this);
    thread.setPriority(Thread.NORM_PRIORITY);
    thread.start();
}

public static void main(String[] args) {
    BallwithDVel ball1 = new BallwithDVel(3,2);
    JFrame f = new JFrame();
    
    TextField textBox = new TextField(" ");
	f.add(textBox);

	textBox.addKeyListener (new KeyAdapter() {
	public void keyPressed(KeyEvent e) {
	int keyCode = e.getKeyCode();
	if (keyCode == 39)
	{
		/*if ( y >= 150)
		{
			vel = 1;
		}*/
		if( vel!= 3)
		vel=vel+1;
	}
	else if (keyCode == 37)
	{
		if((vel-1)==0 || x == 0)
		{
			vel = vel;
		}
		else 
		{
			vel = vel-1;
		}
	}
	}
	
	/* public void keyReleased(KeyEvent e)
     {
        vel = 0;
     } */
	}
	);
    
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(ball1);
    f.setSize(1350,760);
    f.setLocation(0,0);
    f.setVisible(true);
    new Thread(ball1).start();
    new Thread(
    		new Runnable(){
    			public void run(){
    		        String serverHostname = new String ("172.16.30.4");
    		        System.out.println ("Attemping to connect to host " +
    		                serverHostname + " on port 10008.");
    		        Socket echoSocket = null;
    		        PrintWriter out = null;
    		        BufferedReader in = null;
    		        try {
    		            echoSocket = new Socket(serverHostname, 10008);
    		            echoSocket.setReuseAddress(true);
    		            out = new PrintWriter(echoSocket.getOutputStream(), true);
    		            in = new BufferedReader(new InputStreamReader(
    		                                        echoSocket.getInputStream()));
    		        } catch (UnknownHostException e) {
    		            System.err.println("Don't know about host: " + serverHostname);
    		            System.exit(1);
    		        } catch (IOException e) {
    		            System.err.println("Couldn't get I/O for "
    		                               + "the connection to: " + serverHostname);
    		            System.exit(1);
    		        }
    		        while(true)
    		        {
    		        String input = x+":"+y+":"+tvel+":";
    		       // System.out.println("Input " +input);
    		        out.println(input);
    		        try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    		        //System.out.println("sending");
    		        }
    		       
    			}
    		}).start();
}

public class TestBroadcast extends Thread {
  //  String message="1:123";
	int CId=1;
	Date date = new Date();
	long time=(date.getTime()%1000000000);
	String message=""+CId+":"+time+":3:4:";
    int minc=9999;double mint=999999999;
    String[] ipq;
    String[] re;
	public InetSocketAddress addr1;
	

	public void initialise()
	{
		check = 1;
		 TestBroadcast d = new TestBroadcast();
	        d.start();
	        new E(d);
	        // below thread is for receiving traffic light from super leader
	      
	      new Thread (
	        new Runnable(){
	        	public void run(){
	        		try{
	        			while (flag!=1)
	        			{
	        		//System.out.println("new thread");
	        		DatagramSocket receivetl = new DatagramSocket(FinalPort);
	        		byte[] tl = new byte[100];
	        		DatagramPacket tlp = new DatagramPacket(tl, tl.length);
	        		receivetl.receive(tlp);
	        		byte[] tl1 = tlp.getData();
	        		String msg = new String(tl1);    // StreetId:red:yellow:green:time
	        		String[] temp;
	            	temp = msg.split(":");
	            	/*int j;
	        		for (j = 0; j < temp.length; j++)
	            	{
	            	System.out.println("received msg from SL :" + temp[j]);
	            	}*/
	            	if (Integer.parseInt(temp[0])==StreetId)
	            	{
	            		flag = 0;
	            		receivetl.close();
	            		getlight(temp);
	            	}
	            	else
	            	{
	            		flag = 0;
	            	}
	            	Thread.sleep(500);
	        		}
	        		}
	        		catch(Exception e){
	        			
	        		}
	        		
	        	}
	        }).start();
	        
	}
	 public void run() {
	        try {
	        	
	        	while(x < LOD)  // lob
	        	{
	        	//System.out.println("Sending Block");
	        	addr1= new  InetSocketAddress("172.16.31.255", 9876);
			        byte[] senddata=message.getBytes();
			        
			        DatagramSocket tosend=new DatagramSocket();
			        tosend.setBroadcast(true);
			        tosend.setReuseAddress(true);
			        DatagramPacket sendp=new DatagramPacket(senddata, senddata.length, addr1);
			        tosend.send(sendp);
			        System.out.println(" Min car ID and Timestamp: " + minc+ " "+ mint + " ");
			        Thread.sleep(1000);		               	
	        	}
	        	} catch (Exception e) {
	        }
	    }
	 
	    public void changeValue(String val) {
	        
	        try {
	        	String[] temp;
	        	temp = val.split(":");
	        	egn++;
	        	olength =Integer.parseInt(temp[2]);
	        	owidth =Integer.parseInt(temp[3]);
	        	//System.out.println("Here");
	        	oarea+=olength*owidth;
	        	olength = 0;
	        	owidth = 0;
	        	if(Double.valueOf(temp[1])<this.mint){
	            		this.mint=Double.parseDouble(temp[1]);
	            		this.minc=Integer.parseInt(temp[0]);
	            		//System.out.println("Values updated");
	            	}
	            	System.out.println("Min car ID = " +minc);
	               
	                
	            
	        } catch (Exception e) {
	        }
	 
	    }
	}
	 
	class E extends Thread {
	    TestBroadcast cnm;
	    String pmsg;
	   
	 
	    E(TestBroadcast d) {
	    	this.cnm = d;
	        start();
	    }
	 
	    public void run() {
	        try {
	        	HashMap< Integer, InetAddress > IdAddr;
	        	IdAddr = new HashMap<Integer, InetAddress>();
	        	
	        	Queue<InetAddress> myQ=new LinkedList<InetAddress>();
	        	while(x < (LOD - 30))   // some point before lod so that it wont get stuck in receive line
	        	{
	        	//System.out.println("Receiving Block");
	        	byte[] rdata = new byte[100];
	    		DatagramSocket newsocket = new DatagramSocket(9876);
	    		//newsocket.setReuseAddress(true);
	    		DatagramPacket rpacket = new DatagramPacket(rdata, rdata.length);
	    		//System.out.println("Preparing to receive");
	    		newsocket.receive(rpacket);
	    		//System.out.println("Received packets");
	    		String message = new String(rpacket.getData());
	    		System.out.println("Received data: " +message);
	    		//split function and storing of values 
	    		InetAddress ip = rpacket.getAddress();
	    		String[] temp;
	        	temp = message.split(":");
	    		if(! myQ.contains(ip))
	    		{
	    		myQ.add(ip);
	    		IdAddr.put(Integer.parseInt(temp[0]), ip);
	    		cnm.changeValue(message);
	    		}
	    		//System.out.println(String.valueOf(ip));
	            
	            newsocket.close();
	        	}
	        	System.out.println("Done receiving!");
	        	/*for(InetAddress address : myQ ){
	        		   System.out.println(String.valueOf(address));
	        		} */
	        	/*System.out.println(cnm.CId);
	        	System.out.println(cnm.minc);*/
	        	if(cnm.minc == cnm.CId && already == 0)
	        	{
	        		//already = 1;
	        		System.out.println("Leader");
	        		//InetAddress ipl = IdAddr.get(cnm.minc);
	        		call();
	        		
	        		
	        	}
	        	
	        	else
	        	{
	        		// not leader.. receive traffic light
	        		System.out.println("Not a leader");
	        	}
	        } catch (Exception e) {
	        }
	    }
	    
	}
	void call()
	{
		new Thread(
				new Runnable(){
					public void run(){
						System.out.println("First run");
						sendEGN();
					}
				}).start();
		
		new Thread(
				new Runnable(){
					public void run(){
						System.out.println("Second run");
						receiveEGN();
					}
				}).start();
	}
	
	void getlight(String[] temp)
	{
    		/*int j;
    		for (j = 0; j < temp.length; j++)
        	{
        	System.out.println("received msg from SL :" + temp[j]);
        	}*/
    		if (Integer.parseInt(temp[1])==1)    // red light
    		{
    			System.out.println("Red received");
    			redstamp +=1;
    			tvel = 0;
    			flag = 0;
    			/*long time = Long.parseLong(temp[4]);
    			//System.out.println("Time : " +time);
    			try {
					Thread.sleep(time);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
    			
    		}
    		else if(Integer.parseInt(temp[2])==1)
    		{
    			// yellow
    		}
    		else if(Integer.parseInt(temp[3])==1)
    		{
    			// green.. greengap??
    			System.out.println("Green Received");
    			tvel = 2;
    			flag = 1;
    			long time;
    			already = 1;
    			time = Long.parseLong(temp[4]);
    			//System.out.println(" Time: " +time);
    			try {
					Thread.sleep(time);
					// check if it has crossed junction
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    		return;
    	}
    	
	void sendEGN(){
		try {
			//int i=3;
			DatagramSocket egnso = new DatagramSocket();
			String egns = "e:" + String.valueOf(egn)+ ":" + StreetId ;
			byte[] egnb = egns.getBytes();
			egnso.setReuseAddress(true);
			egnso.setBroadcast(true);
			InetSocketAddress addr = new InetSocketAddress("172.16.31.255", 9899);
			DatagramPacket egnp = new DatagramPacket(egnb, egnb.length,addr );
			int i=5;
			while ((x < LONR1 + 100) || i!=0)
			{
				//System.out.println("Sent egn");
			egnso.send(egnp);
			i--;
			Thread.sleep(2000);
			}
			//i = 3;
			while (slv==0){
				Thread.sleep(1000);
			}
			String slvs = "s:" + String.valueOf(slv) + ":" + StreetId;
			byte[] slvb = slvs.getBytes();
			DatagramPacket slvp = new DatagramPacket (slvb, slvb.length, addr);
			int j = 7;
			while(j!=0)
			{
				egnso.send(slvp);
				j--;
				Thread.sleep(1000);
			}
			egnso.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	void receiveEGN(){
		try {
			h1 = new HashMap<Integer, InetAddress>();
			DatagramSocket egnso = new DatagramSocket(9899);
			byte[] egnr = new byte[100];
			DatagramPacket egnp = new DatagramPacket(egnr, egnr.length);
			// LONR condition?
			int z=7;
			while(x < LONR1 || z!=0)
			{
			egnso.receive(egnp);
			z--;
			parse(egnp);
			}
			
			slv = slv();
			// ready to receive slv values
			// timestamp too should be given
			while (nstreets!= nstreete)
			{
				egnso.receive(egnp);
				parse(egnp);
			}
			// check if superleader
			if (minstreet == StreetId)
			{
				// superleader
				System.out.println("SL");
				//long time = 15000;
				//while (time >= 0)
				//{
				// Is this InetAddress really required?
				InetAddress greenstreet = h1.get(maxstreet);
				// send greentimestamp msg to it
				greents = (10 + (2 * IdIndex[0][maxstreet-1]))*1000;
				//System.out.println(greents);
				String gmsg = maxstreet + ":0:0:1:" + greents +":";
				byte[] gmsgb = gmsg.getBytes();
				DatagramSocket ls = new DatagramSocket();
				//System.out.println(maxstreet);
				InetSocketAddress ga = new InetSocketAddress(greenstreet, 9797);
				DatagramPacket gp = new DatagramPacket(gmsgb, gmsgb.length, ga);
				ls.send(gp);
				
				if (maxstreet == StreetId)
				{
					String s1 = maxstreet + ":0:0:1:" + greents + ":";
					ls.setBroadcast(true);
					ls.setReuseAddress(true);
					InetSocketAddress a1 = new InetSocketAddress("172.16.31.255", FinalPort);
					byte[] b1 = s1.getBytes();
					DatagramPacket p1 = new DatagramPacket(b1, b1.length, a1);
					ls.send(p1);
					// after this????
					//System.out.println("Green sent");
					
				}
				//System.out.println("Done sending green");
				
				int i = 1;
				// send red light to other streets
				while (i!=5){  // to cover all 4 streets
					//System.out.println(i);
					if(i==maxstreet){   // street that is given green
						i++;
						//continue;
					}
					else if( i == StreetId)
					{
						String s1 = i + ":1:0:0:" + greents+ ":";
						ls.setBroadcast(true);
						ls.setReuseAddress(true);
						InetSocketAddress a1 = new InetSocketAddress("172.16.31.255", FinalPort);
						byte[] b1 = s1.getBytes();
						DatagramPacket p1 = new DatagramPacket(b1, b1.length, a1);
						ls.send(p1);
						i++;
					}
					else if(IdIndex[0][i-1] != 0)
					{
						//System.out.println("Sending red");
						InetAddress redstreet = h1.get(i); // required?
						String rmsg = i + ":1:0:0:" + greents+ ":";
						InetSocketAddress ra = new InetSocketAddress(redstreet, 9797);
						byte[] rmsgb = rmsg.getBytes();
						DatagramPacket rp = new DatagramPacket(rmsgb, rmsgb.length,ra);
						ls.send(rp);
						//String sentm = new String(rp.getData());
						//System.out.println("SL sent red msg : " + sentm);
						// construct msg and send
						i++;
					}
					else
					{
						i++;
					}
				}
				for ( int k=0;k<2;k++)
				{
					for (int l=0;l<4;l++)
					{
						IdIndex[k][l] = 0;
					}
				}
				maxslv = -1;
				minslv = 99999;
				egnso.close();
				Thread.sleep(greents-5000);
				if (maxstreet != StreetId)
				{
				call();
				}
				/*Thread.sleep(3000);
				time = time - 3000;*/
				// what to do next?
				
			}
			else
			{
				// receive msg from SL
				System.out.println("Not super leader");
				DatagramSocket rtls = new DatagramSocket(9797);
				rtls.setReuseAddress(true);
				byte[] rtlb = new byte[100];
				DatagramPacket rtlp = new DatagramPacket(rtlb, rtlb.length);
				rtls.receive(rtlp);
				byte[] tl = rtlp.getData();
        		String msg = new String(tl);    // StreetId:red:yellow:green:time
        		String[] temp;
            	temp = msg.split(":");
            	System.out.println("Received msg from sl");
            	/*int j;
        		for (j = 0; j < temp.length; j++)
            	{
            	System.out.println("received msg from SL :" + temp[j]);
            	}*/
            	if (Integer.parseInt(temp[0])==StreetId)
            	{
            		// below should be direct broadcast?
            	DatagramSocket ToMemS = new DatagramSocket();
            	ToMemS.setBroadcast(true);
            	ToMemS.setReuseAddress(true);
            	InetSocketAddress addr = new InetSocketAddress("172.16.31.255", FinalPort);
            	DatagramPacket ToMemP = new DatagramPacket(tl, tl.length, addr);
            	ToMemS.send(ToMemP);
            	
            	}
            	byte[] msg1 = rtlp.getData();
            	String msg2 = new String(msg1);
            	String[] temp1;
            	temp1 = msg2.split(":");
            	greents = Integer.parseInt(temp1[4]);
            	if (Integer.parseInt(temp[3])==0)
            	{
            		Thread.sleep(greents-5000);
            		//System.out.println("Sleep done");
            		call();
            	}
			}
			//egnso.close();
			/*System.out.println(greents-5000);
			Thread.sleep(greents-5000);
			call();*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	void parse(DatagramPacket p){
		 try {
			 	byte[] received = p.getData();
				String slvs = new String(received);
			 	InetAddress addr;
	        	String[] temp;
	        	String e = "e";
	        	String s = "s";
	        	temp = slvs.split(":");
	        	if(temp[0].equals(e)){ 
	        		// egn msg
	        		if(temp[2].startsWith("1"))
	        		{
	        		if(IdIndex[0][0]==0){
	        			//System.out.println("inside egn");
	        			nstreete++;
	        			addr = p.getAddress();
	        			h1.put(1, addr);
	        			IdIndex[0][0]= Integer.parseInt(temp[1]);
	        			engn+=IdIndex[0][0];
	        			/*int idind = IdIndex[0][0];
	        			System.out.println(idind);*/
	        		
	        		}
	        		}
	        		else if(temp[2].startsWith("2"))
	        		{
	        			if(IdIndex[0][1]==0){
	        				nstreete++;
	        				addr = p.getAddress();
	        				h1.put(2, addr);
		        			IdIndex[0][1]= Integer.parseInt(temp[1]);
		        			engn+=IdIndex[0][1];
	        		}
	        		}
	        		else if(temp[2].startsWith("3"))
	        		{
	        			if(IdIndex[0][2]==0){
	        				nstreete++;
	        				addr = p.getAddress();
	        				h1.put(3, addr);
		        			IdIndex[0][2]= Integer.parseInt(temp[1]);
		        			engn+=IdIndex[0][2];
	        		}
	        		}
	        		else if(temp[2].startsWith("4"))
	        		{
	        			if(IdIndex[0][3]==0){
	        				nstreete++;
	        				addr = p.getAddress();
	        				h1.put(4, addr);
		        			IdIndex[0][3]= Integer.parseInt(temp[1]);
		        			engn+=IdIndex[0][3];
	        		}
	        		}
	            	}
	        	else if (temp[0].equals(s)) // slv msg
	        	{
	        		if(temp[2].startsWith("1"))
	        		{
	        		if(IdIndex[1][0]==0){
	        			//System.out.println("inside slv");
	        			nstreets++;
	        			IdIndex[1][0]= Integer.parseInt(temp[1]);
	        			if (IdIndex[1][0]<minslv)
	        			{
	        				minslv = IdIndex[1][0];
	        				minstreet = 1;
	        			}
	        			if (IdIndex[1][0]>maxslv)
	        			{
	        				maxslv = IdIndex[1][0];
	        				maxstreet = 1;
	        			}
	        			if (IdIndex[1][0] == minslv)
	        			{
	        				minslv = IdIndex[1][0];
	        				minstreet = 1;
	        			}
	        			if (IdIndex[1][0] == maxslv)
	        			{
	        				if (maxstreet < 2)
	        				{
	        					maxstreet = 1;
	        					maxslv = IdIndex[1][0];
	        				}
	        			}
	        		
	        		}
	        		}
	        		else if(temp[2].startsWith("2"))
	        		{
	        			if(IdIndex[1][1]==0){
	        				nstreets++;
		        			IdIndex[1][1]= Integer.parseInt(temp[1]);
		        			if (IdIndex[1][1]<minslv)
		        			{
		        				minslv = IdIndex[1][1];
		        				minstreet = 2;
		        			}
		        			if (IdIndex[1][1]>maxslv)
		        			{
		        				maxslv = IdIndex[1][1];
		        				maxstreet = 2;
		        			}
		        			if (IdIndex[1][1] == minslv)
		        			{
		        				if (minstreet!=1)
		        				{
		        					minstreet = 2;
		        					minslv = IdIndex[1][1];
		        				}
		        			}
		        			if (IdIndex[1][1] == maxslv)
		        			{
		        				if (maxstreet < 3)
		        				{
		        					maxstreet = 2;
		        					maxslv = IdIndex[1][1];
		        				}
		        			}
	        		}
	        		}
	        		else if(temp[2].startsWith("3"))
	        		{
	        			if(IdIndex[1][2]==0){
	        				nstreets++;
		        			IdIndex[1][2]= Integer.parseInt(temp[1]);
		        			if (IdIndex[1][2]<minslv)
		        			{
		        				minslv = IdIndex[1][2];
		        				minstreet = 3;
		        			}
		        			if (IdIndex[1][2]>maxslv)
		        			{
		        				maxslv = IdIndex[1][2];
		        				maxstreet = 3;
		        			}
		        			if (IdIndex[1][2] == minslv)
		        			{
		        				if (minstreet > 2)
		        				{
		        					minstreet = 3;
		        					minslv = IdIndex[1][2];
		        				}
		        			}
		        			if (IdIndex[1][2] == maxslv)
		        			{
		        				if (maxstreet < 4)
		        				{
		        					maxstreet = 3;
		        					maxslv = IdIndex[1][2];
		        				}
		        			}
	        		}
	        		}
	        		else if(temp[2].startsWith("4"))
	        		{
	        			if(IdIndex[1][3]==0){
	        				nstreets++;
		        			IdIndex[1][3]= Integer.parseInt(temp[1]);
		        			if (IdIndex[1][3]<minslv)
		        			{
		        				minslv = IdIndex[1][3];
		        				minstreet = 4;
		        			}
		        			if (IdIndex[1][3]>maxslv)
		        			{
		        				maxslv = IdIndex[1][3];
		        				maxstreet = 4;
		        			}
		        			// below condition not required
		        			if (IdIndex[1][3] == minslv)
		        			{
		        				if (minstreet > 3)
		        				{
		        					minstreet = 4;
		        					minslv = IdIndex[1][3];
		        				}
		        			}
		        			if (IdIndex[1][3] == maxslv)
		        			{
		        				maxslv = IdIndex[1][3];
		        				maxstreet = 4;
		        			}
	        		}
	        		}
	        	}
		 	} catch (Exception e) {
	        }
	}
	void calcDensity()
	{
		density=oarea;
	}
	
	int slv()
	{ 
		//engn = engn - egn; // receives its own msgs
		System.out.println("here");
		calcDensity();
		slv = this.redstamp * (this.egn + 10/ (this.egn + this.engn+this.density));
		return slv;
	}
}