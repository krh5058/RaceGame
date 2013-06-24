package RaceGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class CustomPanel extends JPanel  {

	private static Track track;
	private static Rectangle startLine;
	private static Rectangle finishLine;
	private static HashMap<String,StateObj> stateHash = new HashMap<String,StateObj>(); // Hash map to store StateObj
	static final int [] keyNames = {KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D}; // Key event int array for easier indexing.

	private static final long serialVersionUID = -1516251819657951269L;

	CustomPanel(){
		System.out.println("CustomPanel");
		track = new Track(RaceGame.getMapIndex());
		
		// Initialize car coordinates, and terrain modifier
		RaceGame.newp1X(track.car1x); 
		RaceGame.newp1Y(track.car1y);
		RaceGame.newp2X(track.car2x); 
		RaceGame.newp2Y(track.car2y);
		RaceGame.newp1Dir(track.p1dir); 
		RaceGame.newp2Dir(track.p2dir);
		startLine = track.lines.get(0);
		finishLine = track.lines.get(1);
		RaceGame.terrainMod = track.terrainMod;

		// Add StateObj to stateHash, refer keyName to action key string, associate action key string with StateObj's AbstractAction sub-classes to ActionMap
		for(int i = 0; i < keyNames.length; i++) {
			stateHash.put(Integer.toString(i), new StateObj());
			getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyNames[i],0,false), "pressed" + Integer.toString(i) );
			getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyNames[i],0,true), "released" + Integer.toString(i) );
			getActionMap().put("pressed" + Integer.toString(i),stateHash.get(Integer.toString(i)).onswitch);
			getActionMap().put("released" + Integer.toString(i),stateHash.get(Integer.toString(i)).offswitch);
		}

		setBackground(Color.DARK_GRAY);

	}

	static class StateObj 
	{
		private boolean state = false;
		OnSwitch onswitch; 
		OffSwitch offswitch;

		StateObj() {
			onswitch = new OnSwitch();
			offswitch = new OffSwitch();
		}

		private void stateOn(){
			state = true;
		}

		private void stateOff(){
			state = false;
		}

		boolean reportState(){
			return state;
		}

		private class OnSwitch extends AbstractAction
		{
			private static final long serialVersionUID = 1L;

			@Override public void actionPerformed(ActionEvent e) {
				stateOn();
			}
		}

		private class OffSwitch extends AbstractAction
		{
			private static final long serialVersionUID = 1L;

			@Override public void actionPerformed(ActionEvent e) {
				stateOff();
			}
		}
	}

	static HashMap<String, StateObj> getStateHashMap(){
		return stateHash;
	}

	// Start drawing methods
	public void paint (Graphics g){
		super.paint(g);

		drawTerrain(g);
		drawWall(g);
		drawObstacle(g);
		drawLine(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(RaceGame.p1img, RaceGame.at, null);
		g2d.drawImage(RaceGame.p2img, RaceGame.at2, null);
	}

	private void drawTerrain(Graphics g){

		for(int j = 0; j < track.terrain.size(); j++){
			try{ 
				Color color = track.terColor.get(j);

				g.setColor(color);
				Rectangle temp = track.terrain.get(j);

				g.fillRect(temp.x,temp.y, temp.width, temp.height);
			} catch (Exception e){
				System.out.println("debug");
			}
		}
	}

	private void drawWall(Graphics g){
		g.setColor(Color.red);
		for(int j = 0; j < track.wall.size(); j++){
			Rectangle temp = track.wall.get(j);
			g.fillRect(temp.x,temp.y,temp.width,temp.height);
		}
	}
	private void drawObstacle (Graphics g){
		for (int k = 0; k<track.obstacles.size(); k++){
			BufferedImage img = track.obsimg.get(k);
			Rectangle temp = track.obstacles.get(k);
			g.drawImage(img, temp.x,temp.y,this);
		}
	}

	private void drawLine (Graphics g){
		for (int l=0; l<track.lines.size(); l++){
			BufferedImage limg = track.lineimg.get(l);
			Rectangle temp = track.lines.get(l);
			g.drawImage(limg,temp.x,temp.y,this);
		}
	}
	// End drawing methods

	// Start check routine methods
	static void checkRoutine(){
		startTimer();
    	checkCarCollision();
		checkTerrainCollision();
		checkWallCollision();
		checkObstaclesCollision();
	}

	private static void startTimer(){
		if (RaceGame.getp1Trans().intersects(startLine)&& RaceGame.cs1==true
			||	RaceGame.getp2Trans().intersects(startLine)&& RaceGame.cs2 == true){
			RaceGame.stc = System.nanoTime();
			System.out.println("Start1");
			System.out.println("Start2");
			RaceGame.cs1 = false; RaceGame.cf1 = true;
			RaceGame.cs2 = false; RaceGame.cf2 = true;
		}
		if (RaceGame.getp1Trans().intersects(finishLine)&& RaceGame.cf1 == true ){
			RaceGame.etc1 = System.nanoTime()-RaceGame.stc;
			RaceGame.cf1=false;
			System.out.println("time1:"+(RaceGame.etc1/1e9));
		}
		if (RaceGame.getp2Trans().intersects(finishLine)&& RaceGame.cf2 == true){
			RaceGame.etc2 = System.nanoTime()-RaceGame.stc;
			RaceGame.cf2 = false;
			System.out.println("time2:"+(RaceGame.etc2/1e9));
		}
	}

	private static void checkCarCollision() {
    	Area areaA = new Area(RaceGame.getp1Trans());
    	areaA.intersect(new Area(RaceGame.getp2Trans()));
    	if(!areaA.isEmpty()) {
    		RaceGame.p1Speed =0; RaceGame.p2Speed=0;
    	} 
    }
    
	private static void checkTerrainCollision() {
		
		boolean state1 = false;
		boolean state2 = false;

		for (int J1 = 0; J1<track.terrain.size();J1++){

			if ((RaceGame.getp1Trans().intersects(track.terrain.get(J1)))){
				state1 = true;
				break;
			} 
		}
		
		for (int J2 = 0; J2<track.terrain.size();J2++){

			if ((RaceGame.getp2Trans().intersects(track.terrain.get(J2)))){
				state2 = true;
				break;
			} 
		}
		
		RaceGame.ct1 = state1;
		RaceGame.ct2 = state2;
    }
    
	private static void checkWallCollision() {
    	for (int L = 0; L<track.wall.size(); L++){
    		if (RaceGame.getp1Trans().intersects(track.wall.get(L))){
    			RaceGame.p1Speed = 0;
    		}
    		else if (RaceGame.getp2Trans().intersects(track.wall.get(L))){
    			RaceGame.p2Speed = 0;
    		}
    	}
    }
  
	private static void checkObstaclesCollision(){
		
		boolean state1 = false;
		boolean state2 = false;
		
    	for (int m =0; m< track.obstacles.size(); m++){
    		
    		if (RaceGame.getp1Trans().intersects(track.obstacles.get(m))){
    			int[] Temp = track.imgIndex.get(m);
    			if (Temp[0]== 1){
    				state1 = true;
    				break;
    			}
    			
    			if (Temp[0]== 2){
    				RaceGame.p1Speed = 0;
    			}
    		}

    	}
    	
    	for (int m =0; m< track.obstacles.size(); m++){

    		if (RaceGame.getp2Trans().intersects(track.obstacles.get(m))){
    			int[] Temp = track.imgIndex.get(m);
    			if (Temp[0]== 1){
    				state2 = true;
    				break;
    			}

    			if (Temp[0]== 2){
    				RaceGame.p2Speed = 0;
    			}
    		}
    	}
    	
		RaceGame.c1 = state1;
		RaceGame.c2 = state2;
    	
    }
    // End check routine methods
    
}
