import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class RaceGame extends JFrame implements Runnable, ActionListener{
	static RaceGame frame; // Static frame for easy reference
	static int fWidth = 1200;
	static int fHeight = 900;
	protected double curX = fWidth/2;
	protected double curY = fHeight/2;
	protected int xScale = 12;
	protected int yScale = 3;
	Rectangle p1 = new Rectangle ( fWidth/xScale, fHeight/yScale, fWidth/30, fHeight/30 );
	private Container cp;
	private Image carimg;
//	private JLabel carLabel;
//	private double carxSpeed = 0;
//	private double carySpeed = 0;
	private double carSpeed = 0;
	private double carDir = 0;
	private double turnIncrement = Math.PI/180;
	private double turnMax = turnIncrement*180;
	private JPanel newPanel;// = new JPanel();
	//create menu items
	private JMenuBar menuBar;
	private JMenu newMenu;
	private JMenuItem itemExit;
	private JMenuItem newGameItem;
	private JMenuItem openFileItem;
	private JMenuItem itemEnterName;
	private JMenuItem itemHighScore;
	private JMenuItem itemSaveScore;
	//end create menu items
	/**
	 * @param args
	 */
	
	private static final long serialVersionUID = 1L;
	
	public RaceGame() {
		super("Racegame");
		cp=getContentPane();
////		ImageIcon icon = new ImageIcon( getClass().getResource("car.jpeg") );
////        carLabel = new JLabel(icon);//GUI background for initial load
//        cp.add(carLabel);
        pack();
        //Add Exit & New Game Menu Items
        itemExit = new JMenuItem("Exit");
        itemExit.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_X, KeyEvent.CTRL_MASK));//press CTRL+X to exit if you want
        itemSaveScore = new JMenuItem("Save High Score");
        itemSaveScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_S, KeyEvent.CTRL_MASK));//press CTRL+S to save high score if you want
        itemHighScore=new JMenuItem("High Score");
        itemHighScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_H, KeyEvent.CTRL_MASK));//press CTRL+H to view high score if you want
        itemEnterName = new JMenuItem("Enter Player Name");
        itemEnterName.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_N, KeyEvent.CTRL_MASK));//press CTRL+N to enter your name if you want
        newGameItem = new JMenuItem("New Game");
        openFileItem = new JMenuItem("Open Maze File.");
        openFileItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_O, KeyEvent.CTRL_MASK));//press CTRL+O to open a level if you want
        newGameItem.setActionCommand("New Game");
        newGameItem.addActionListener(this);
        itemEnterName.setActionCommand("EnterName");
        itemEnterName.addActionListener(this);
        itemSaveScore.setActionCommand("SaveScore");
        itemSaveScore.addActionListener(this);
        itemHighScore.setActionCommand("HighScore");
        itemHighScore.addActionListener(this);
        itemExit.setActionCommand("Exit");
        itemExit.addActionListener(this);
        openFileItem.setActionCommand("Open");
        openFileItem.addActionListener(this);
        newMenu = new JMenu("File");
        newMenu.add(newGameItem);
        newMenu.add(itemEnterName);
        newMenu.add(openFileItem);
        newMenu.add(itemHighScore);
        newMenu.add(itemSaveScore);
        newMenu.add(itemExit);
        
        //Add Menu Bar
        menuBar = new JMenuBar();
        menuBar.add(newMenu);
        setJMenuBar(menuBar);
        
        ImageIcon icon = new ImageIcon( getClass().getResource("car.jpeg") );
        Image img = icon.getImage();
//        Image newimg = img.getScaledInstance(74, 40,  java.awt.Image.SCALE_SMOOTH);  
        carimg = img.getScaledInstance(74, 40,  java.awt.Image.SCALE_SMOOTH); 
//        ImageIcon newIcon = new ImageIcon(newimg);
//        carLabel = new JLabel(newIcon);
//
        newPanel = new JPanel();
        
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//        if (e.getActionCommand().equals("Exit"))//exit on the menu bar
//        {
//             new Timer(1000, updateCursorAction).stop();
//             System.exit(0); //exit the system.   
//        }
        if (e.getActionCommand().equals("New Game"))//new game on the menu bar
        {
             loadnew("newload");
        }//end New Game Command
	}
	
	public void paint (Graphics g){
		super.paint(g);
        g.fill3DRect(p1.x,p1.y,p1.width,p1.height,true);
        g.drawImage(carimg, p1.x,p1.y,this);

	}
	private class MyKeyHandler extends KeyAdapter //captures arrow keys movement
	{
//	    // Set of currently pressed keys
//	    private final Set<Character> pressed = new HashSet<Character>();
//	    
//	    @Override
//	    public synchronized void keyReleased(KeyEvent e) {
//	        pressed.remove(e.getKeyChar());
//	    }
	    
		public synchronized void keyPressed (KeyEvent theEvent)
		{         
//			pressed.add((char) theEvent.getKeyCode());
//			for (int i = 0; i < pressed.size(); i++){
				System.out.println(theEvent.getKeyCode());
				if (theEvent.getKeyCode()==KeyEvent.VK_UP){
					System.out.println("Accel");
					accel(1);
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_DOWN){
					System.out.println("Decel");
					accel(-1);
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_LEFT){
					System.out.println("Left");
					turn(-turnIncrement);
				}
				if (theEvent.getKeyCode()==KeyEvent.VK_RIGHT){
					System.out.println("Right");
					turn(turnIncrement);
				}
//			}

//		    remove(newPanel);//remove the old game
		}//end method
	}//end inner class
	
    public void loadnew(String event)
    {
       if (event == "newload")
        {       
    	   System.out.println("new");
    	   remove(newPanel);//remove the previous level's game from the screen
    	   newPanel = new JPanel();
    	   newPanel.setLayout(null);
    	   newPanel.addKeyListener( new MyKeyHandler() );
    	   updateIMG(curX,curY);
    	   newPanel.add(carLabel);
    	   cp.add(newPanel);
           System.gc();//force java to clean up memory use.
           pack();
           newPanel.setVisible(true);
           newPanel.grabFocus();  

           Thread t = new Thread( this );
           t.start();
        }
    }
    
    public void updateIMG(double x, double y) {
//	   carLabel.setLocation(x, y);
//	   return carLabel;
    	carLabel.setBounds(new Rectangle(new Point((int)Math.ceil(x),(int)Math.ceil(y)), carLabel.getPreferredSize()));
    }
    
    public void accel(double v) {
//		carxSpeed += Math.sin(direction)*velocity;
//		carySpeed += Math.cos(direction)*velocity;
    	carSpeed+=v;
    	System.out.println(carSpeed);
    }
    
    public void turn(double t) {
    	double turn = carDir+t;
    	if (turn > turnMax) {
    		System.out.println("Turning Maxed.");
    	} else if (turn < -1*turnMax) {
    		System.out.println("Turning Maxed.");
    	} else {
    		carDir=turn;
    	}
    	System.out.println(Math.toDegrees(carDir));
    	System.out.println(Math.toDegrees(turnMax));
    }
    
    public void polarToRect() {
    	double xCalc = getcurX()+(carSpeed*Math.cos(carDir));
    	double yCalc = getcurY()+(carSpeed*Math.sin(carDir));
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
				remove(newPanel);//remove the old game
				newPanel = new JPanel();
				newPanel.setLayout(null);
				newPanel.addKeyListener( new MyKeyHandler() );
				polarToRect();
				updateIMG(getcurX(),getcurY());
				newPanel.add(carLabel);
				cp.add(newPanel);
				//			    System.gc();//force java to clean up memory use.
				pack();
				//			    newPanel.revalidate();
				newPanel.setVisible(true);
				newPanel.grabFocus();

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
