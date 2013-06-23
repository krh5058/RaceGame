package RaceGame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class RaceGame extends JFrame implements Runnable, ActionListener{
	// General
	static RaceGame frame; // Static frame for easy reference
	protected static CardLayout cardLayout;
	protected static JPanel cards; // Panel that uses CardLayout
	protected static MainPanel mainPanel;
	protected static CustomPanel newPanel;
	protected static SelectPanel selPanel;
	public static Container cp;
	private static int mapIndex;
	public static boolean[] keys = new boolean[8];
	private volatile static Thread t;
	
	// Coordinates
	static int fWidth = 1000;
	static int fHeight = 750;
	protected static double curp1X;
	protected static double curp1Y;
	protected static double curp2X;
	protected static double curp2Y;
	
	// Image/shape stuff
	private static BufferedImage p1img;
	public static Rectangle p1rect;
	private BufferedImage p1crash;
	public static BufferedImage p2img;
	public static Rectangle p2rect;
	private BufferedImage p2crash;
	public static AffineTransform at;
	public static AffineTransform at2;
	public static Shape p1trans;
	public static Shape p2trans;
	
	// Car parameters
	private static boolean c1;
	private static boolean c2;
	private static double p1Speed;
	private static double p1Dir;
	private static double p2Speed;
	private static double p2Dir;
	private static double accelIncrement;
	private static double accelVal;
	private static double turnIncrement;
	private static double oilMultiplier;
	private double turnMax;
	
	// Create UI items
	private JMenuBar menuBar;
	private JMenu newMenu;
	private JMenuItem itemExit;
	private JMenuItem mainGameItem;
	private JMenuItem itemHighScore;
	
	// Time
	public static long stc1;
	public static long etc1;
	public static long stc2;
	public static long etc2;
	public static boolean cs1 =true;
	public static boolean cs2= true;
	public static boolean cf1;
	public static boolean cf2;
	
	private static final long serialVersionUID = 1L;
	
	public RaceGame(){
		super("Racegame");
		cp=getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        
		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);

        cards.add(new MainPanel(), "Main");
        cards.add(new SelectPanel(), "Select");
        cp.add(cards);
		
        // Add menu Items
        itemExit = new JMenuItem("Exit");
        itemHighScore=new JMenuItem("High Score");
        mainGameItem = new JMenuItem("Main Menu");
        mainGameItem.setEnabled(false);
        mainGameItem.setActionCommand("Main Menu");
        mainGameItem.addActionListener(this);
        itemHighScore.setActionCommand("HighScore");
        itemHighScore.addActionListener(this);
        itemExit.setActionCommand("Exit");
        itemExit.addActionListener(this);
        newMenu = new JMenu("File");
        newMenu.add(mainGameItem);
        newMenu.add(itemHighScore);
        newMenu.add(itemExit);
        
        // Add menu Bar
        menuBar = new JMenuBar();
        menuBar.add(newMenu);
        setJMenuBar(menuBar);
        
        //Load images
        try {
			p1img = ImageIO.read(getClass().getResource("resources/Red_50x30.png"));
			p1crash = ImageIO.read(getClass().getResource("resources/RedCrash_50x30.png"));
			p2img = ImageIO.read(getClass().getResource("resources/Blue_50x30.png"));
			p2crash = ImageIO.read(getClass().getResource("resources/BlueCrash_50x30.png"));
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
        if (e.getActionCommand().equals("Main Menu"))//new game on the menu bar
        {
             handleSelect("mainMenu");
        }
        
        if (e.getActionCommand().equals("HighScore"))//new game on the menu bar
        {
        	System.out.println("highScore");
        }
        
        if (e.getActionCommand().equals("Exit"))//new game on the menu bar
        {
        	System.out.println("exit");
        }
	}
	
	public static class MainPanel extends JPanel implements ActionListener{

		private JButton startBtn;
		private JButton hiBtn;
		private JButton exitBtn;
		
		private static final long serialVersionUID = 2623765164578488546L;

		public MainPanel(){
			System.out.println("MainPanel");
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			// Add buttons
	        startBtn = new JButton("Start!");
	        startBtn.setActionCommand("pickTrack");
	        startBtn.addActionListener(this);
	        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	        startBtn.setPreferredSize(new Dimension(500,100));
	        startBtn.setMinimumSize(new Dimension(500,100));
	        startBtn.setMaximumSize(new Dimension(new Dimension(500,100)));
//	        startBtn.setAlignmentY(opacity)
	        hiBtn = new JButton("HighScore");
	        hiBtn.setActionCommand("HighScore");
	        hiBtn.addActionListener(this);
	        hiBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	        hiBtn.setPreferredSize(new Dimension(400,100));
	        hiBtn.setMinimumSize(new Dimension(400,100));
	        hiBtn.setMaximumSize(new Dimension(500,100));
	        exitBtn = new JButton("Exit");
	        exitBtn.setActionCommand("Exit");
	        exitBtn.addActionListener(this);
	        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	        exitBtn.setPreferredSize(new Dimension(400,100));
	        exitBtn.setMinimumSize(new Dimension(400,100));
	        exitBtn.setMaximumSize(new Dimension(new Dimension(500,100)));
	        
	        add(Box.createVerticalGlue());
	        add(startBtn);
	        add(Box.createVerticalGlue());
	        add(hiBtn);
	        add(Box.createVerticalGlue());
	        add(exitBtn);
	        add(Box.createVerticalGlue());
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
	        if (e.getActionCommand().equals("pickTrack"))//new game on the menu bar
	        {
	            cardLayout.show(cards, "Select");
	        }
	        
	        if (e.getActionCommand().equals("HighScore"))//new game on the menu bar
	        {
	        	System.out.println("highScore");
	        }
	        
	        if (e.getActionCommand().equals("Exit"))//new game on the menu bar
	        {
				frame.setVisible( false ); // Clear old
				frame.dispose(); // Clear old
	        }
		}
	}

	public static class SelectPanel extends JPanel implements ActionListener{

		private JButton track1Btn;
		private JButton track2Btn;
		private JButton track3Btn;
		
		private static final long serialVersionUID = 2623765164578488546L;

		public SelectPanel(){
			System.out.println("SelectPanel");
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
	        // Add buttons
	        track1Btn = new JButton();
	        track1Btn.setActionCommand("track1");
	        track1Btn.addActionListener(this);
	        track1Btn.setAlignmentX(Component.CENTER_ALIGNMENT);
	        track1Btn.setPreferredSize(new Dimension(500,100));
	        track1Btn.setMinimumSize(new Dimension(500,100));
	        track1Btn.setMaximumSize(new Dimension(new Dimension(500,100)));
	        track1Btn.setBackground(Color.GREEN);
	        track2Btn = new JButton();
	        track2Btn.setActionCommand("track2");
	        track2Btn.addActionListener(this);
	        track2Btn.setAlignmentX(Component.CENTER_ALIGNMENT);
	        track2Btn.setPreferredSize(new Dimension(400,100));
	        track2Btn.setMinimumSize(new Dimension(400,100));
	        track2Btn.setMaximumSize(new Dimension(500,100));
	        track2Btn.setBackground(Color.WHITE);
	        track3Btn = new JButton();
	        track3Btn.setActionCommand("track3");
	        track3Btn.addActionListener(this);
	        track3Btn.setAlignmentX(Component.CENTER_ALIGNMENT);
	        track3Btn.setPreferredSize(new Dimension(400,100));
	        track3Btn.setMinimumSize(new Dimension(400,100));
	        track3Btn.setMaximumSize(new Dimension(new Dimension(500,100)));
	        track3Btn.setBackground(Color.YELLOW);
	        
	        add(Box.createVerticalGlue());
	        add(track1Btn);
	        add(Box.createVerticalGlue());
	        add(track2Btn);
	        add(Box.createVerticalGlue());
	        add(track3Btn);
	        add(Box.createVerticalGlue());
		}
		
		public void actionPerformed(ActionEvent e) {
	        if (e.getActionCommand().equals("track1"))//new game on the menu bar
	        {
	        	mapIndex = 1;
	        }
	        
	        if (e.getActionCommand().equals("track2"))//new game on the menu bar
	        {
	        	mapIndex = 2;
	        }
	        
	        if (e.getActionCommand().equals("track3"))//new game on the menu bar
	        {
	        	mapIndex = 3;
	        }
	        
	        System.out.println("new");
			stopThread();
			resetCar();
	        newPanel = new CustomPanel();
//			newPanel.addKeyListener(new MyKeyHandler());
	        cards.add(newPanel, "Game");
	        cp.add(cards);

			t = new Thread( frame );
			t.start();
			
			cardLayout.show(cards, "Game");
			newPanel.requestFocusInWindow(); // Necessary for any CustomPanel to apply ActionMap actions
		}
	}
	
	public static class CustomPanel extends JPanel  {

		public static Track track = new Track(mapIndex);
		private static Rectangle startLine;
		private static Rectangle finishLine;
		static final int [] keyNames = {KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_W,KeyEvent.VK_A,KeyEvent.VK_S,KeyEvent.VK_D}; // Key event int array for easier indexing.
		private static final long serialVersionUID = -1516251819657951269L;
		public CustomPanel(){
			System.out.println("CustomPanel");
			
			// Initialize car coordinates
			newp1X(track.car1x); 
			newp1Y(track.car1y);
			newp2X(track.car2x); 
			newp2Y(track.car2y);
			newp1Dir(track.p1dir); 
			newp2Dir(track.p2dir);
			startLine = track.lines.get(0);
			finishLine = track.lines.get(1);
			
			for(int i = 0; i < keyNames.length; i++) {
				getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyNames[i],0,false), "pressed" + Integer.toString(i) );
				getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyNames[i],0,true), "released" + Integer.toString(i) );
				getActionMap().put("pressed" + Integer.toString(i) , new AbstractAction() {
					private static final long serialVersionUID = 1L;

					@Override public void actionPerformed(ActionEvent e) {
						System.out.println("test1");
//						keys[i] = true;
					}
				});
				getActionMap().put("released" + Integer.toString(i), new AbstractAction() {
					private static final long serialVersionUID = 1L;

					@Override public void actionPerformed(ActionEvent e) {
						System.out.println("test1");
//						keys[i] = false;
					}
				});
			}
			setBackground(Color.DARK_GRAY);
			
		}
		
		// Start drawing methods
		public void paint (Graphics g){
			super.paint(g);
			
			drawTerrain(g);
			drawWall(g);
			drawObstacle(g);
			drawLine(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(p1img, at, null);
			g2d.drawImage(p2img, at2, null);
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
		// End drawing methods
		
		// Start check routine methods
		public static void checkRoutine(){
			startTimer();
	    	checkCarCollision();
			checkTerrainCollision();
			checkWallCollision();
			checkObstaclesCollision();
		}
		
		public static void startTimer(){
			if (getp1Trans().intersects(startLine)&& cs1==true){
				stc1 = System.nanoTime();
				System.out.println("Start1");
				cs1 = false; cf1 = true;
			}
			if (getp1Trans().intersects(finishLine)&& cf1 == true ){
				etc1 = System.nanoTime()-stc1;
				cf1=false;
				System.out.println("time1:"+(etc1/1e9));
			}
			if (getp2Trans().intersects(startLine)&& cs2 == true){
				stc2 = System.nanoTime();
				System.out.println("Start2");
				cs2 = false; cf2 = true;
			}
			if (getp2Trans().intersects(finishLine)&& cf2 == true){
				etc2 = System.nanoTime()-stc2;
				cf2 = false;
				System.out.println("time2:"+(etc2/1e9));
			}
		}
		
	    public static void checkCarCollision() {
	    	Area areaA = new Area(getp1Trans());
	    	areaA.intersect(new Area(getp2Trans()));
	    	if(!areaA.isEmpty()) {
	    		c1 = true;
	    		c2 = true;
	    	} else {
	    		c1 = false;
	    		c2 = false;
	    	}
	    }
	    
	    public static void checkTerrainCollision() {
//	    	for(int j = 0; j < wall.size(); j++){
	    		Area areaA = new Area(getp1Trans());
	    		areaA.intersect(new Area(getp2Trans()));
	    		if(!areaA.isEmpty()) {
	    			c1 = true;
	    			c2 = true;
	    		} else {
	    			c1 = false;
	    			c2 = false;
	    		}
//	    	}
	    }
	    
	    public static void checkWallCollision() {
	    	Area areaA = new Area(getp1Trans());
	    	areaA.intersect(new Area(getp2Trans()));
	    	if(!areaA.isEmpty()) {
	    		c1 = true;
	    		c2 = true;
	    	} else {
	    		c1 = false;
	    		c2 = false;
	    	}
	    }
	  
	    public static void checkObstaclesCollision(){
	    	for (int m =0; m< track.obstacles.size(); m++){
	    		
	    		if (getp1Trans().intersects(track.obstacles.get(m))){
	    			int[] Temp = track.imgIndex.get(m);
	    			if (Temp[0]== 1){
	    				System.out.println("OIL"+m);
	    			}
	    			else if (Temp[0]== 2){
	    				System.out.println("OTHER"+m);
	    			}
	    		}
	    		if (getp2Trans().intersects(track.obstacles.get(m))){
	    			int[] Temp = track.imgIndex.get(m);
	    			if (Temp[0]== 1){
	    				System.out.println("OIL2"+m);
	    			}
	    			else if (Temp[0]== 2){
	    				System.out.println("OTHER2"+m);
	    			}
	    		}
	    	}
	    }
	    // End check routine methods
	    
	}
	
	private static class MyKeyHandler extends KeyAdapter //Captures arrow keys movement
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
	
	public void handleSelect(String event)
	{
		if (event == "mainMenu")
		{  
			frame.setVisible( false ); // Clear old
			frame.dispose(); // Clear old
			String [] input = {"New"};
			main(input); // Restart
		}
		
		if (event == "highScore")
		{  
			System.out.println("High Scores");
		}

		if (event == "exit")
		{  
			frame.setVisible( false ); // Clear old
			frame.dispose(); // Clear old
		}
	}
    
    public static void resetCar() {
        c1 = false;
        c2 = false;
        p1Speed = 0;
        p1Dir = 0;
        p2Speed = 0;
        p2Dir = 0;
        accelIncrement = .025;
        accelVal = accelIncrement;
        oilMultiplier = 5;
        turnIncrement = Math.PI/90;
        
    }
    
    public static void stopThread() {
        t = null;
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
    
    public void friction(){
    	p1Speed *= .98;
    	p2Speed *= .98;
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
    		xp2Calc = (fWidth - p2img.getWidth());
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
    
    // Accessor & Modifier methods
    public static void newp1X(double d){
    	curp1X = d;
    }
    
    public static void newp1Y(double d){
    	curp1Y = d;
    }
    
    public static void newp2X(double d){
    	curp2X = d;
    }
    
    public static void newp2Y(double d){
    	curp2Y = d;
    }
    
    public static double getCurp1X(){
    	return curp1X;
    }
    
    public static double getCurp1Y(){
    	return curp1Y;
    }
    
    public static double getCurp2X(){
    	return curp2X;
    }
    
    public static double getCurp2Y(){
    	return curp2Y;
    }
    
    public static void newp1Dir(double d){
    	p1Dir = d;
    }
    
    public static void newp2Dir(double d){
    	p2Dir = d;
    }
    
	public boolean getc1(){
		return c1;
	}
	
	public boolean getc2(){
		return c2;
	}
	synchronized public void setp1Trans(AffineTransform a, Rectangle r){
		p1trans = a.createTransformedShape(r);
	}
	
	synchronized public void setp2Trans(AffineTransform a, Rectangle r){
		p2trans = a.createTransformedShape(r);
	}
	
	synchronized public static Shape getp1Trans(){
		return p1trans;
	}
	
	synchronized public static Shape getp2Trans(){
		return p2trans;
	}
    // End Accessor & Modifier methods
	
	public void run() 
	{
		Thread thisThread = Thread.currentThread();
		try {
			while(t == thisThread) {

		    	updateCar(); // Apply car physics for key press
				newCarxy(); // Update car positions
				friction(); // Apply frictional forces
				
				// Update rotated images and shapes
				at = makeAt(p1img, 1);
				at2 = makeAt(p2img, 2);
		    	setp1Trans(at,p1rect);
		    	setp2Trans(at2,p2rect);
		    	
		    	repaint(); // Draw everything

		    	CustomPanel.checkRoutine(); // Run static routine methods, see CustomPanel
		    	
//		    	if (getc1()){
//		    		System.out.println("P1 Collision On");
//		    	} else {
//		    		System.out.println("P1 Collision Off");
//		    	}
//		    	
//		    	if (getc2()){
//		    		System.out.println("P2 Collision On");
//		    	} else {
//		    		System.out.println("P2 Collision Off");
//		    	}
		    	
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
		frame.setSize(1000, 750);
		frame.setResizable(false);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setAlwaysOnTop(true);
		frame.setMinimumSize(new Dimension(fWidth,fHeight));
		frame.setVisible( true );

		RaceGame.frame= frame;
	}

}
