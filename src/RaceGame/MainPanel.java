package RaceGame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

//import RaceGame.RaceGame.Select;

public class MainPanel extends JPanel implements ActionListener{

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
            RaceGame.cardLayout.show(RaceGame.cards, "Select");
            RaceGame.mainMenuItem.setEnabled(true);
        }
        
        if (e.getActionCommand().equals("HighScore"))//new game on the menu bar
        {
        	RaceGame.selectObj.select("highScore");
        }
        
        if (e.getActionCommand().equals("Exit"))//new game on the menu bar
        {
        	RaceGame.selectObj.select("exit");
        }
	}
}