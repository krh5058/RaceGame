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
	protected double curX = fWidth/2;
	protected double curY = fHeight/2;
	private Container cp;
	private BufferedImage p1img;
	public Rectangle p1rect;
	private BufferedImage p1crash;
	public BufferedImage p2img;
	public Rectangle p2rect;
	private BufferedImage p2crash;
	public boolean[] keys = new boolean[7];
	private double carSpeed = 0;
	private double carDir = 0;
	private double accelIncrement = .025;
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
		p2rect = new Rectangle(0,0,p1img.getWidth(),p1img.getHeight());
        
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
		
		private static final long serialVersionUID = -1516251819657951269L;
		public CustomPanel(){
			System.out.println("CustomPanel");
		}
		public void paint (Graphics g){
			super.paint(g);
			AffineTransform at = new AffineTransform();
			at.translate((int)Math.ceil(getcurX())+p1img.getWidth()/2, getcurY()+p1img.getHeight()/2);
			at.rotate(carDir);
			at.translate(-p1img.getWidth()/2,-p1img.getHeight()/2);
			Graphics2D g2d = (Graphics2D) g;
//			g2d.drawImage(p1img, at, null);
			
			g.setColor(Color.red);
			g.fillRect((int)p2rect.x,(int)p2rect.y,(int)p2rect.getWidth(),(int)p2rect.getHeight());
			
//			p1rect = new Rectangle((int)Math.ceil(getcurX()),(int)Math.ceil(getcurY()),p1img.getWidth(),p1img.getHeight());
//			g2d.setPaint(Color.red);
//			g2d.fillRect((int)p1rect.x,(int)p1rect.y,(int)p1rect.getWidth(),(int)p1rect.getHeight());
//			AffineTransform at2 = AffineTransform.getTranslateInstance(p1rect.x,p1rect.y);
//			if (keys[4]){
			p1trans = at.createTransformedShape(p1rect);
	    	checkCollision();
//			if (p1trans.intersects(p2rect)) {
//				System.out.println("Collision!");
//			}
			g2d.draw(at.createTransformedShape(p1rect));
//			}
//			System.out.println(p1rect.getBounds());
		}
		
		public Shape getTrans(){
			return p1trans;
		}
		
	    public void checkCollision() {
	    	
//	    	if (getTrans().intersects(p2rect)) {
//	    		System.out.println("Collision!");
//	    	}
//	    	if (p1rect.intersects(p2rect)){
//	    		System.out.println("Collision");
//	    	}
//	    	System.out.println(p1trans.getBounds());
//	    	System.out.println(p2rect.getBounds());
	    	Area areaA = new Area(p2rect);
	    	areaA.intersect(new Area(p1trans));
	    	if(!areaA.isEmpty()) {
	    		System.out.println("Collision!");
	    	}
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
				if (theEvent.getKeyCode()==KeyEvent.VK_NUMPAD0){
					keys[4] = true;
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
		}
	}
	
    public void loadnew(String event)
    {
       if (event == "newload")
        {       
    	   System.out.println("new");
    	   CustomPanel newPanel = new CustomPanel();
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
    		accel(accelIncrement);
    	}
    	if (keys[1]) {
    		accel(-accelIncrement/2);
    	}
    	if (keys[2]) {
    		if (carSpeed < 0) {
    			turn(turnIncrement);
    		} else {
    			turn(-turnIncrement);
    		}
    	}
    	if (keys[3]) {
    		if (carSpeed < 0) {
    			turn(-turnIncrement);
    		} else {
    			turn(turnIncrement);
    		}
    	}
    	
    }
   
    
    public void accel(double v) {
    	carSpeed+=v;
//    	System.out.println(carSpeed);
    }
    
    public void turn(double t) {
    	double turn = carDir+t;
    	if (turn > turnMax) {
    		carDir= turn - turnMax;
    	} else if (turn < -1*turnMax) {
    		carDir= turn + turnMax;
    	} else {
    		carDir=turn;
    	}
    }
    
    public void newCarxy() {
    	double xCalc = getcurX()+(carSpeed*Math.cos(carDir));
    	double yCalc = getcurY()+(carSpeed*Math.sin(carDir));
    	
    	if (xCalc > (fWidth - p1img.getWidth())) {
    		xCalc = (fWidth - p1img.getWidth());
    		carSpeed = 0;
    	}
    	if (xCalc < 0) {
    		xCalc = 0;
    		carSpeed = 0;
    	}
    	if (yCalc > (fHeight - 2*p1img.getWidth())) {
    		yCalc = (fHeight - 2*p1img.getWidth());
    		carSpeed = 0;
    	}
    	if (yCalc < 0) {
    		yCalc = 0;
    		carSpeed = 0;
    	}
    	newX(xCalc);
    	newY(yCalc);
    }
    
    public void newX(double d){
    	curX = d;
    }
    
    public void newY(double d){
    	curY = d;
    }
    
    public double getcurX(){
    	return curX;
    }
    
    public double getcurY(){
    	return curY;
    }
    
	public void run() 
	{
		try {
			while(true) {
				newCarxy();
		    	repaint();
		    	updateCar();
		    	
		    	carSpeed *= .98;
		    	
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
