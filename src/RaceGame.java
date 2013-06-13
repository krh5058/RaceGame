import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
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
	static int fWidth = 1200;
	static int fHeight = 900;
	protected double curX = fWidth/2;
	protected double curY = fHeight/2;
	private Container cp;
	private BufferedImage p1img;
	private BufferedImage p1crash;
	private BufferedImage p2img;
	private BufferedImage p2crash;
	public boolean[] keys = new boolean[7];
	private double carSpeed = 0;
	private double carDir = 0;
	private double accelIncrement = .05;
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
			p1img = ImageIO.read(getClass().getResource("Red.png"));
			p1crash = ImageIO.read(getClass().getResource("RedCrash.png"));
			p2img = ImageIO.read(getClass().getResource("Blue.png"));
			p2crash = ImageIO.read(getClass().getResource("BlueCrash.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
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

		private static final long serialVersionUID = 1L;
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
			g2d.drawImage(p1img, at, null);
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
    	if (yCalc > (fHeight - p1img.getWidth())) {
    		yCalc = (fHeight - p1img.getWidth());
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
