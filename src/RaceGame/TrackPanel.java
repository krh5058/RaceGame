package RaceGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TrackPanel extends JPanel implements ActionListener{

	CustomPanel racePanel;
	private JButton track1Btn;
	private JButton track2Btn;
	private JButton track3Btn;

	
	private static final long serialVersionUID = 2623765164578488546L;

	public TrackPanel(){
		System.out.println("TrackPanel");
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
        	RaceGame.setMapIndex(1);
        }
        
        if (e.getActionCommand().equals("track2"))//new game on the menu bar
        {
        	RaceGame.setMapIndex(2);
        }
        
        if (e.getActionCommand().equals("track3"))//new game on the menu bar
        {
        	RaceGame.setMapIndex(3);
        }
        
        System.out.println("new");
        RaceGame.stopThread();
        RaceGame.resetCar();
        racePanel = new CustomPanel();
        RaceGame.cards.add(racePanel, "Game");
        RaceGame.cp.add(RaceGame.cards);

        RaceGame.t = new Thread( RaceGame.frame );
        RaceGame.t.start();
		
        RaceGame.cardLayout.show(RaceGame.cards, "Game");
		racePanel.requestFocusInWindow(); // Necessary for any CustomPanel to apply ActionMap actions
	}
}