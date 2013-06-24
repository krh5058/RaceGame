package RaceGame;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RaceGame extends JFrame implements Runnable, ActionListener{
	// General
	static RaceGame frame; // Static frame for easy reference
	protected static CardLayout cardLayout;
	protected static JPanel cards; // Panel that uses CardLayout
	static MainPanel mainPanel;
	static TrackPanel trackPanel;
	static Select selectObj;
	static Container cp;
	static int mapIndex;
	static boolean[] keys = new boolean[8];
	volatile static Thread t;

	// Coordinates
	static int fWidth = 1000;
	static int fHeight = 750;
	private static double curp1X;
	private static double curp1Y;
	private static double curp2X;
	private static double curp2Y;

	// Image/shape stuff
	static BufferedImage p1img;
	static BufferedImage p2img;
	static Rectangle p1rect;
	static Rectangle p2rect;
	private BufferedImage p1crash;
	private BufferedImage p2crash;
	static AffineTransform at;
	static AffineTransform at2;
	static Shape p1trans;
	static Shape p2trans;

	// Car parameters
	static boolean c1;
	static boolean c2;
	static boolean ct1;
	static boolean ct2;
	static double p1Speed;
	static double p1Dir;
	static double p2Speed;
	static double p2Dir;
	static double accelIncrement;
	static double accelVal;
	static double terrainAccel;
	static double turnIncrement;
	static double turnMax;

	// Create UI items
	private JMenuBar menuBar;
	private JMenu newMenu;
	static JMenuItem itemExit;
	static JMenuItem mainMenuItem;
	static JMenuItem scoreMenuItem;

	// Time
	static long stc;
	static long etc1;
	static long etc2;
	static boolean cs1 =true;
	static boolean cs2= true;
	static boolean cf1;
	static boolean cf2;

	private static final long serialVersionUID = 1L;

	public RaceGame(){
		super("Racegame");

		// Panel set-up
		cp=getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);
		mainPanel = new MainPanel();
		trackPanel = new TrackPanel();
        cards.add(mainPanel, "Main");
        cards.add(trackPanel, "Select");
        cp.add(cards);

        // Add menu Items
        itemExit = new JMenuItem("Exit");
        scoreMenuItem=new JMenuItem("High Score");
        mainMenuItem = new JMenuItem("Main Menu");
        mainMenuItem.setEnabled(false);
        mainMenuItem.setActionCommand("Main Menu");
        mainMenuItem.addActionListener(this);
        scoreMenuItem.setActionCommand("HighScore");
        scoreMenuItem.addActionListener(this);
        itemExit.setActionCommand("Exit");
        itemExit.addActionListener(this);
        newMenu = new JMenu("File");
        newMenu.add(mainMenuItem);
        newMenu.add(scoreMenuItem);
        newMenu.add(itemExit);
        
        // Add menu Bar
        menuBar = new JMenuBar();
        menuBar.add(newMenu);
        setJMenuBar(menuBar);
        
        // Load images
        try {
			p1img = ImageIO.read(getClass().getResource("resources/Red_50x30.png"));
			p1crash = ImageIO.read(getClass().getResource("resources/RedCrash_50x30.png"));
			p2img = ImageIO.read(getClass().getResource("resources/Blue_50x30.png"));
			p2crash = ImageIO.read(getClass().getResource("resources/BlueCrash_50x30.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Initialize car rectangles
		p1rect = new Rectangle(0,0,p1img.getWidth(),p1img.getHeight());
		p2rect = new Rectangle(0,0,p2img.getWidth(),p2img.getHeight());

		// Initialize selector object
		selectObj = new Select();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Main Menu"))//new game on the menu bar
        {
        	selectObj.select("mainMenu");
        }
        
        if (e.getActionCommand().equals("HighScore"))//new game on the menu bar
        {
        	selectObj.select("highScore");
        }
        
        if (e.getActionCommand().equals("Exit"))//new game on the menu bar
        {
        	selectObj.select("exit");
        }
	}

	class Select
	{
		void select(String event){
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
				// Prompt
				int result = JOptionPane.showConfirmDialog(
						frame,
						"Are you sure you want to exit the application?",
						"Exit Application",
						JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION){
					frame.setVisible( false ); // Clear old
					frame.dispose(); // Clear old
				}
			}
		}

	}
    
    static void resetCar() {
        c1 = false;
        c2 = false;
        p1Speed = 0;
        p1Dir = 0;
        p2Speed = 0;
        p2Dir = 0;
        accelIncrement = .025;
        accelVal = accelIncrement;
        turnIncrement = Math.PI/90;
    }
    
    static void stopThread() {
        t = null;
    }

    private void updateKeys() {
    	for(int i = 0; i < keys.length; i ++) {
    		keys[i] = CustomPanel.getStateHashMap().get(Integer.toString(i)).reportState();
    	}
    }
    
    private void updateCar() {
    	
    		if (keys[0]) {
    			if (ct1 == true){
    				ct1 = false;
    				accelp1(terrainAccel);        	
        	}
    			else {
    		accelp1(accelVal);
    			}
    		}
    	if (keys[4]) {
    		if (ct2 == true){
    			ct2 = false;
    		accelp2(terrainAccel);
    	}
    		else{
    			accelp2(accelVal);
    		}
    	}
    	if (keys[1]) {
    		if (ct1 == true){
    			ct1 = false;
    			accelp1(-terrainAccel/2);
    		}
    		else{
    		accelp1(-accelVal/2);
    		}
    	}
    	
    	if (keys[5]) {
    		if (ct2 == true){
    			ct2 = false;
    			accelp2(-terrainAccel/2);
    		}
    		else{
    		accelp2(-accelVal/2);
    		}
    		}
    	
    	if (keys[2]) {
    		if (c1 == true){
    			c1 = false;
    			if (p1Speed < 0) {
        			turnp1(turnIncrement*5);
        		} else {
        			turnp1(-turnIncrement*5);
        		}
    		}
    		else{
    		if (p1Speed < 0) {
    			turnp1(turnIncrement);
    		} else {
    			turnp1(-turnIncrement);
    		}
    		}
    	}
    	
    	if (keys[6]) {
    		if (c2 == true){
    			c2 = false;
    			if (p2Speed < 0) {
        			turnp2(turnIncrement*5);
        		} else {
        			turnp2(-turnIncrement*5);
        		}
    		}
    		else {
    		if (p2Speed < 0) {
    			turnp2(turnIncrement);
    		} else {
    			turnp2(-turnIncrement);
    		}
    		}
    	}
    	
    	if (keys[3]) {
    		if (c1 == true){
    			c1 = false;
    			if (p1Speed < 0) {
        			turnp1(turnIncrement*5);
        		} else {
        			turnp1(-turnIncrement*5);
        		}
    		}
    		else{
    		if (p1Speed < 0) {
    			turnp1(-turnIncrement);
    		} else {
    			turnp1(turnIncrement);
    		}
    		}
    	}
    	
    	if (keys[7]) {
    		if (c2 == true){
    			c2 = false;
    			if (p2Speed < 0) {
        			turnp2(-turnIncrement*5);
        		} else {
        			turnp2(turnIncrement*5);
        		}
    		}
    		else {
    		if (p2Speed < 0) {
    			turnp2(-turnIncrement);
    		} else {
    			turnp2(turnIncrement);
    		}
    		}
    	}
    	
    }
    
    private void accelp1(double v) {
    	p1Speed+=v;
    }
    
    private void accelp2(double v) {
    	p2Speed+=v;
    }
    
    private void turnp1(double t) {
    	double turn = p1Dir+t;
    	if (turn > turnMax) {
    		p1Dir= turn - turnMax;
    	} else if (turn < -1*turnMax) {
    		p1Dir= turn + turnMax;
    	} else {
    		p1Dir=turn;
    	}
    }
    
    private void turnp2(double t) {
    	double turn = p2Dir+t;
    	if (turn > turnMax) {
    		p2Dir= turn - turnMax;
    	} else if (turn < -1*turnMax) {
    		p2Dir= turn + turnMax;
    	} else {
    		p2Dir=turn;
    	}
    }
    
    private void friction(){
    	p1Speed *= .98;
    	p2Speed *= .98;
    }
    
    private void newCarxy() {
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
    
	private AffineTransform makeAt(BufferedImage b, int i) {
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
    static void newp1X(double d){
    	curp1X = d;
    }
    
    static void newp1Y(double d){
    	curp1Y = d;
    }
    
    static void newp2X(double d){
    	curp2X = d;
    }
    
    static void newp2Y(double d){
    	curp2Y = d;
    }
    
    static double getCurp1X(){
    	return curp1X;
    }
    
    static double getCurp1Y(){
    	return curp1Y;
    }
    
    static double getCurp2X(){
    	return curp2X;
    }
    
    static double getCurp2Y(){
    	return curp2Y;
    }
    
    static void newp1Dir(double d){
    	p1Dir = d;
    }
    
    static void newp2Dir(double d){
    	p2Dir = d;
    }
    
    boolean getc1(){
		return c1;
	}

	boolean getc2(){

		return c2;
	}

	synchronized void setp1Trans(AffineTransform a, Rectangle r){
		p1trans = a.createTransformedShape(r);
	}

	synchronized void setp2Trans(AffineTransform a, Rectangle r){
		p2trans = a.createTransformedShape(r);
	}

	synchronized static Shape getp1Trans(){
		return p1trans;
	}

	synchronized static Shape getp2Trans(){
		return p2trans;
	}
    // End Accessor & Modifier methods

	public void run() 
	{
		Thread thisThread = Thread.currentThread();
		try {
			while(t == thisThread) {

				updateKeys(); // Modify keys array according to key presses
		    	updateCar(); // Apply car physics 
				newCarxy(); // Update car positions
				friction(); // Apply frictional forces

				// Update rotated images and shapes
				at = makeAt(p1img, 1);
				at2 = makeAt(p2img, 2);
		    	setp1Trans(at,p1rect);
		    	setp2Trans(at2,p2rect);

		    	repaint(); // Draw everything

		    	CustomPanel.checkRoutine(); // Run static routine methods, see CustomPanel

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
		frame.setResizable(false);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int x = (screenSize.width - fWidth) / 2;
		int y = (screenSize.height - fHeight) / 2;
		frame.setLocation(x, y);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setAlwaysOnTop(true);
		frame.setMinimumSize(new Dimension(fWidth,fHeight));
		frame.setVisible( true );

		RaceGame.frame = frame;
	}

}

