package RaceGame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class RaceGame extends JFrame implements Runnable, ActionListener{
	static RaceGame frame; // Static frame for easy reference
	protected CustomPanel newPanel;
	static int fWidth = 1000;
	static int fHeight = 750;
	protected double curp1X = fWidth/2;
	protected double curp1Y = fHeight/2;
	protected double curp2X = fWidth/3;
	protected double curp2Y = fHeight/3;
	private Container cp;
	private int mapIndex = 2;
	private BufferedImage p1img;
	public Rectangle p1rect;
	private BufferedImage p1crash;
	public BufferedImage p2img;
	public Rectangle p2rect;
	private BufferedImage p2crash;
	public boolean[] keys = new boolean[8];
	private double p1Speed = 0;
	private double p1Dir = 0;
	private double p2Speed = 0;
	private double p2Dir = 0;
	private double accelIncrement = .025;
	private double accelVal = accelIncrement;
	private double turnIncrement = Math.PI/90;
	private double turnMax = 360;
	//create menu items
	private JMenuBar menuBar;
	private JMenu newMenu;
	private JMenuItem itemExit;
	private JMenuItem newGameItem;
	private JMenuItem itemEnterName;
	private JMenuItem itemHighScore;
	//end create menu items
	/**
	 * @param args
	 */
	
	private static final long serialVersionUID = 1L;
	
	public RaceGame(){
		super("Racegame");
		cp=getContentPane();
        pack();
        
        //Add Exit & New Game Menu Items
        itemExit = new JMenuItem("Exit");
        itemExit.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_X, KeyEvent.CTRL_MASK));//press CTRL+X to exit if you want
        itemHighScore=new JMenuItem("High Score");
        itemHighScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_H, KeyEvent.CTRL_MASK));//press CTRL+H to view high score if you want
        itemEnterName = new JMenuItem("Enter Player Name");
        itemEnterName.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_N, KeyEvent.CTRL_MASK));//press CTRL+N to enter your name if you want
        newGameItem = new JMenuItem("New Game");
        newGameItem.setActionCommand("New Game");
        newGameItem.addActionListener(this);
        itemEnterName.setActionCommand("EnterName");
        itemEnterName.addActionListener(this);
        itemHighScore.setActionCommand("HighScore");
        itemHighScore.addActionListener(this);
        itemExit.setActionCommand("Exit");
        itemExit.addActionListener(this);
        newMenu = new JMenu("File");
        newMenu.add(newGameItem);
        newMenu.add(itemEnterName);
        newMenu.add(itemHighScore);
        newMenu.add(itemExit);
        
        //Add Menu Bar
        menuBar = new JMenuBar();
        menuBar.add(newMenu);
        setJMenuBar(menuBar);
        
        //Load images
        try {
			p1img = ImageIO.read(getClass().getResource("Red_50x30.png"));
			p1crash = ImageIO.read(getClass().getResource("RedCrash_50x30.png"));
			p2img = ImageIO.read(getClass().getResource("Blue_50x30.png"));
			p2crash = ImageIO.read(getClass().getResource("BlueCrash_50x30.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		p1rect = new Rectangle(0,0,p1img.getWidth(),p1img.getHeight());
		p2rect = new Rectangle(0,0,p2img.getWidth(),p2img.getHeight());
        
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("Exit"))//exit on the menu bar
//        {
//             new Timer(1000, updateCursorAction).stop();
//             System.exit(0); //exit the system.   
//        }
        if (e.getActionCommand().equals("New Game"))//new game on the menu bar
        {
             loadnew("newload");
        }
	}
	
	public class CustomPanel extends JPanel  {

		public Shape p1trans;
		public Shape p2trans;
		public Track track = new Track(mapIndex);
		public boolean c1 = false;
		public boolean c2 = false;
		
		private static final long serialVersionUID = -1516251819657951269L;
		public CustomPanel(){
			System.out.println("CustomPanel");
			curp1X = track.car1x; curp1Y = track.car1y;
			curp2X = track.car2x; curp2Y = track.car2y;
			p1Dir = track.p1dir; p2Dir = track.p2dir;
			setBackground(Color.DARK_GRAY);
			setSize(1000, 750);
			setResizable(false);
		}
		public void paint (Graphics g){
			super.paint(g);
			
			drawTerrain(g);
			drawWall(g);
			drawObstacle(g);
			drawLine(g);
			AffineTransform at = makeAt(p1img, 1);
			AffineTransform at2 = makeAt(p2img, 2);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(p1img, at, null);
			g2d.drawImage(p2img, at2, null);
			setp1Trans(at,p1rect);
			setp2Trans(at2,p2rect);

	    	checkCarCollision();
			checkTerrainCollision();
			checkWallCollision();
		}
		
		public AffineTransform makeAt(BufferedImage b, int i) {
			AffineTransform at = new AffineTransform();
			switch(i){
			case 1:
				at.translate((int)Math.ceil(getCurp1X())+b.getWidth()/2, getCurp1Y()+b.getHeight()/2);
				break;
			case 2:
				at.translate((int)Math.ceil(getCurp2X())+b.getWidth()/2, getCurp2Y()+b.getHeight()/2);
				break;				
			}
			
			switch(i){
			case 1:
				at.rotate(p1Dir);
				break;
			case 2:
				at.rotate(p2Dir);
				break;				
			}
			at.translate(-b.getWidth()/2,-b.getHeight()/2);
			return at;
		}
		
		public void drawTerrain(Graphics g){
			
			for(int j = 0; j < track.terrain.size(); j++){
				Color color = track.terColor.get(j);
				
				g.setColor(color);
				Rectangle temp = track.terrain.get(j);
				
				g.fillRect(temp.x,temp.y, temp.width, temp.height);
			}
		}
		
		public void drawWall(Graphics g){
			g.setColor(Color.red);
			for(int j = 0; j < track.wall.size(); j++){
				Rectangle temp = track.wall.get(j);
				g.fillRect(temp.x,temp.y,temp.width,temp.height);
			}
		}
		public void drawObstacle (Graphics g){
			for (int k = 0; k<track.obstacles.size(); k++){
				BufferedImage img = track.obsimg.get(k);
				Rectangle temp = track.obstacles.get(k);
				g.drawImage(img, temp.x,temp.y,this);
			}
		}
		public void drawLine (Graphics g){
			for (int l=0; l<track.lines.size(); l++){
				BufferedImage limg = track.lineimg.get(l);
				Rectangle temp = track.lines.get(l);
				g.drawImage(limg,temp.x,temp.y,this);
			}
		}
		
	    public void checkCarCollision() {
	    	Area areaA = new Area(getp1Trans());
	    	areaA.intersect(new Area(getp2Trans()));
	    	if(!areaA.isEmpty()) {
	    		newPanel.c1 = true;
	    		newPanel.c2 = true;
	    	} else {
	    		newPanel.c1 = false;
	    		newPanel.c2 = false;
	    	}
	    }
	    
	    public void checkTerrainCollision() {
//	    	for(int j = 0; j < wall.size(); j++){
	    		Area areaA = new Area(getp1Trans());
	    		areaA.intersect(new Area(getp2Trans()));
	    		if(!areaA.isEmpty()) {
	    			newPanel.c1 = true;
	    			newPanel.c2 = true;
	    		} else {
	    			newPanel.c1 = false;
	    			newPanel.c2 = false;
	    		}
//	    	}
	    }
	    
	    public void checkWallCollision() {
	    	Area areaA = new Area(getp1Trans());
	    	areaA.intersect(new Area(getp2Trans()));
	    	if(!areaA.isEmpty()) {
	    		newPanel.c1 = true;
	    		newPanel.c2 = true;
	    	} else {
	    		newPanel.c1 = false;
	    		newPanel.c2 = false;
	    	}
	    }
		
		public void setp1Trans(AffineTransform a, Rectangle r){
			p1trans = a.createTransformedShape(r);
		}
		
		public void setp2Trans(AffineTransform a, Rectangle r){
			p2trans = a.createTransformedShape(r);
		}
		
		public Shape getp1Trans(){
			return p1trans;
		}
		
		public Shape getp2Trans(){
			return p2trans;
		}
	    
		public boolean getc1(){
			return c1;
		}
		
		public boolean getc2(){
			return c2;
		}
	}

	
	private class MyKeyHandler extends KeyAdapter //Captures arrow keys movement
	{
		public synchronized void keyPressed (KeyEvent theEvent)
		{         
				if (theEvent.getKeyCode()==KeyEvent.VK_UP){
					keys[0] = true;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_DOWN){
					keys[1] = true;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_LEFT){
					keys[2] = true;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_RIGHT){
					keys[3] = true;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_W){
					keys[4] = true;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_S){
					keys[5] = true;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_A){
					keys[6] = true;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_D){
					keys[7] = true;
				}
		}
		
		public synchronized void keyReleased (KeyEvent theEvent)
		{         
				if (theEvent.getKeyCode()==KeyEvent.VK_UP){
					keys[0] = false;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_DOWN){
					keys[1] = false;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_LEFT){
					keys[2] = false;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_RIGHT){
					keys[3] = false;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_W){
					keys[4] = false;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_S){
					keys[5] = false;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_A){
					keys[6] = false;
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_D){
					keys[7] = false;
				}
		}
	}
	
    public void loadnew(String event)
    {
       if (event == "newload")
        {       
    	   System.out.println("new");
    	   newPanel = new CustomPanel();
    	   newPanel.addKeyListener(new MyKeyHandler());
    	   repaint();
    	   cp.add(newPanel);
           System.gc();//force java to clean up memory use.
           pack();
           newPanel.setVisible(true);
           newPanel.grabFocus();  

           Thread t = new Thread( this );
           t.start();
        }
    }
    
    public void updateCar() {
    	
    	if (keys[0]) {
    		accelp1(accelVal);
    	}
    	
    	if (keys[4]) {
    		accelp2(accelVal);
    	}
    	
    	if (keys[1]) {
    		accelp1(-accelVal/2);
    	}
    	
    	if (keys[5]) {
    		accelp2(-accelVal/2);
    	}
    	
    	if (keys[2]) {
    		if (p1Speed < 0) {
    			turnp1(turnIncrement);
    		} else {
    			turnp1(-turnIncrement);
    		}
    	}
    	
    	if (keys[6]) {
    		if (p2Speed < 0) {
    			turnp2(turnIncrement);
    		} else {
    			turnp2(-turnIncrement);
    		}
    	}
    	
    	if (keys[3]) {
    		if (p1Speed < 0) {
    			turnp1(-turnIncrement);
    		} else {
    			turnp1(turnIncrement);
    		}
    	}
    	
    	if (keys[7]) {
    		if (p2Speed < 0) {
    			turnp2(-turnIncrement);
    		} else {
    			turnp2(turnIncrement);
    		}
    	}
    	
    }
   
    
    public void accelp1(double v) {
    	p1Speed+=v;
    }
    
    public void accelp2(double v) {
    	p2Speed+=v;
    }
    
    public void turnp1(double t) {
    	double turn = p1Dir+t;
    	if (turn > turnMax) {
    		p1Dir= turn - turnMax;
    	} else if (turn < -1*turnMax) {
    		p1Dir= turn + turnMax;
    	} else {
    		p1Dir=turn;
    	}
    }
    
    public void turnp2(double t) {
    	double turn = p2Dir+t;
    	if (turn > turnMax) {
    		p2Dir= turn - turnMax;
    	} else if (turn < -1*turnMax) {
    		p2Dir= turn + turnMax;
    	} else {
    		p2Dir=turn;
    	}
    }
    
    public void newCarxy() {
    	double xp1Calc = getCurp1X()+(p1Speed*Math.cos(p1Dir));
    	double yp1Calc = getCurp1Y()+(p1Speed*Math.sin(p1Dir));
    	double xp2Calc = getCurp2X()+(p2Speed*Math.cos(p2Dir));
    	double yp2Calc = getCurp2Y()+(p2Speed*Math.sin(p2Dir));
    	
    	if (xp1Calc > (fWidth - p1img.getWidth())) {
    		xp1Calc = (fWidth - p1img.getWidth());
    		p1Speed = 0;
    	}
    	
    	if (xp2Calc > (fWidth - p2img.getWidth())) {
    		xp1Calc = (fWidth - p2img.getWidth());
    		p2Speed = 0;
    	}
    	
    	if (xp1Calc < 0) {
    		xp1Calc = 0;
    		p1Speed = 0;
    	}
    	
    	if (xp2Calc < 0) {
    		xp2Calc = 0;
    		p2Speed = 0;
    	}
    	
    	if (yp1Calc > (fHeight - 2*p1img.getWidth())) {
    		yp1Calc = (fHeight - 2*p1img.getWidth());
    		p1Speed = 0;
    	}
    	
    	if (yp2Calc > (fHeight - 2*p2img.getWidth())) {
    		yp2Calc = (fHeight - 2*p2img.getWidth());
    		p2Speed = 0;
    	}
    	
    	if (yp1Calc < 0) {
    		yp1Calc = 0;
    		p1Speed = 0;
    	}
    	
    	if (yp2Calc < 0) {
    		yp2Calc = 0;
    		p2Speed = 0;
    	}
    	
    	newp1X(xp1Calc);
    	newp1Y(yp1Calc);
    	
    	newp2X(xp2Calc);
    	newp2Y(yp2Calc);
    }
    
    public void newp1X(double d){
    	curp1X = d;
    }
    
    public void newp1Y(double d){
    	curp1Y = d;
    }
    
    public void newp2X(double d){
    	curp2X = d;
    }
    
    public void newp2Y(double d){
    	curp2Y = d;
    }
    
    public double getCurp1X(){
    	return curp1X;
    }
    
    public double getCurp1Y(){
    	return curp1Y;
    }
    
    public double getCurp2X(){
    	return curp2X;
    }
    
    public double getCurp2Y(){
    	return curp2Y;
    }
    
	public void run() 
	{
		try {
			while(true) {
				newCarxy();
		    	repaint();
		    	updateCar();
		    	
		    	p1Speed *= .98;
		    	p2Speed *= .98;
		    	
		    	if (newPanel.getc1()){
		    		System.out.println("P1 Collision On");
		    	} else {
		    		System.out.println("P1 Collision Off");
		    	}
		    	
		    	if (newPanel.getc2()){
		    		System.out.println("P2 Collision On");
		    	} else {
		    		System.out.println("P2 Collision Off");
		    	}
		    	
				Thread.sleep(5);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RaceGame frame = new RaceGame();
		RaceGame.frame= frame;
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setAlwaysOnTop(true);
		frame.setMinimumSize(new Dimension(fWidth,fHeight));
		frame.setVisible( true );
	}

}
