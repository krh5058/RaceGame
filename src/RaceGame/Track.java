package RaceGame;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * @author WorkHorse
 *
 */
public class Track{
	public ArrayList<Rectangle> wall = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> terrain = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> obstacles = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> lines = new ArrayList<Rectangle>();
	public ArrayList<int[]> dimwall = new ArrayList<int[]>();
	public ArrayList<int[]> dimobst = new ArrayList<int[]>();
	public ArrayList<int[]> dimline = new ArrayList<int[]>();
	public ArrayList<int[]> dimter = new ArrayList<int[]>();
	public ArrayList<Color> terColor = new ArrayList<Color>();
	public ArrayList<BufferedImage> obsimg = new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> lineimg = new ArrayList<BufferedImage>();
	public ArrayList<int[]> imgIndex = new ArrayList<int[]>();
	public BufferedImage junk, start,start3, finish1,finish2,finish3,oil, barrels,pitv,pith;
	public double car1x, car1y, car2x, car2y, p1dir,p2dir;
	double terrainMod;
	public Track(int i){
		
		  try {
			  junk = ImageIO.read(getClass().getResource("resources/junkcar.png"));
				start = ImageIO.read(getClass().getResource("resources/start.png"));
				start3 = ImageIO.read(getClass().getResource("resources/start3.png"));
				finish1 = ImageIO.read(getClass().getResource("resources/finish1.png"));
				finish2 = ImageIO.read(getClass().getResource("resources/finish2.png"));
				finish3 = ImageIO.read(getClass().getResource("resources/finish3.png"));
				oil = ImageIO.read(getClass().getResource("resources/oil.png"));
				barrels = ImageIO.read(getClass().getResource("resources/barrels.png"));
				pitv = ImageIO.read(getClass().getResource("resources/wallv.png"));
				pith = ImageIO.read(getClass().getResource("resources/wallh.png"));				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		switch (i){
		case 1:
			// Terrain Modifier
			terrainMod = 0.64;
			
			// car coords
			car1x = 55; car1y = 605;
			car2x = 125; car2y = 605;
			p1dir = -Math.PI/2; p2dir = -Math.PI/2;
			// terrain (Grass)
			dimter.add(new int[]{0,0,50,700});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{950,0,50,700});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{0,0,1000,50});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{0,650,1000,50});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{180,180,50,470});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{500,10,50,520});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{230,180,140,50});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{300,420,150,150});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{680,150,50,500});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{360,310,140,50});
			terColor.add(Color.GREEN);
			// water
			dimter.add(new int[]{326,446,99,99});
			terColor.add(Color.blue);
			// walls 
			dimwall.add(new int[]{5,5,5,690});
			dimwall.add(new int[]{985,5,5,690});
			dimwall.add(new int[]{5,5,985,5});
			dimwall.add(new int[]{5,690,985,5});
			dimwall.add(new int[]{200,200,5,490});
			dimwall.add(new int[]{520,10,5,500});
			dimwall.add(new int[]{205,200,150,5});
			dimwall.add(new int[]{380,320,140,5});
			dimwall.add(new int[]{700,170,5,520});
//			dimwall.add(new int[]{325,445,100,100});
			// Obstacles
			dimobst.add(new int[]{120,240,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{80,80,40,40});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{350,100,30,30});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{230,445,30,30});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{320,570,30,30});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{580,490,30,30});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{620,180,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{820,180,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{780,360,30,30});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{880,360,30,30});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			// finish and start line
			dimline.add(new int[]{50,580,130,10});
			lineimg.add(start);
			dimline.add(new int[]{730,580,220,10});	
			lineimg.add(finish1);
			break;
		case 2:
			// Terrain Modifier
			terrainMod = 0.33;
			
			// car coords
			car1x = 55; car1y = 605;
			car2x = 125; car2y = 605;
			p1dir = -Math.PI/2; p2dir = -Math.PI/2;
			// Terrain (Snow)
			dimter.add(new int[]{0,0,50,700});
			terColor.add(Color.white);
			dimter.add(new int[]{950,0,50,700});
			terColor.add(Color.white);
			dimter.add(new int[]{0,0,1000,50});
			terColor.add(Color.white);
			dimter.add(new int[]{0,650,1000,50});
			terColor.add(Color.white);
			dimter.add(new int[]{180,180,50,470});
			terColor.add(Color.white);
			dimter.add(new int[]{230,180,380,50});
			terColor.add(Color.white);
			dimter.add(new int[]{750,50,50,470});
			terColor.add(Color.white);
			dimter.add(new int[]{350,360,400,50});
			terColor.add(Color.white);
			dimter.add(new int[]{350,410,50,150});
			terColor.add(Color.white);
			dimter.add(new int[]{500,500,50,150});
			terColor.add(Color.white);
			dimter.add(new int[]{650,410,100,110});
			terColor.add(Color.white);
			// walls 
			dimwall.add(new int[]{5,5,5,690});
			dimwall.add(new int[]{985,5,5,690});
			dimwall.add(new int[]{5,5,985,5});
			dimwall.add(new int[]{5,690,985,5});
			dimwall.add(new int[]{200,200,5,490});
			dimwall.add(new int[]{205,200,385,5});
			dimwall.add(new int[]{770,10,5,480});
			dimwall.add(new int[]{370,380,400,5});
			dimwall.add(new int[]{370,385,5,150});
			dimwall.add(new int[]{520,520,5,170});
			// Obstacles
			dimobst.add(new int[]{120,240,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{100,100,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{230,70,40,40});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{385,100,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{590,120,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{640,70,40,40});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{640,250,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{450,230,39,40});
			obsimg.add(pitv);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{360,310,39,40});
			obsimg.add(pitv);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{270,310,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{290,540,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{420,450,40,40});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{800,420,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{890,340,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{800,260,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{890,180,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			// finish and start line
			dimline.add(new int[]{50,580,130,10});
			lineimg.add(start);
			dimline.add(new int[]{800,130,150,10});	
			lineimg.add(finish2);
			break;
		case 3:
			// Terrain Modifier
			terrainMod = 1;
			
			// car coords
			car1x = 535; car1y = 403;
			car2x = 535; car2y = 438;
			p1dir = -Math.PI; p2dir = -Math.PI;
			// Terrain sand
			dimter.add(new int[]{0,0,80,700});
			terColor.add(Color.YELLOW);
			dimter.add(new int[]{920,0,80,700});
			terColor.add(Color.YELLOW);
			dimter.add(new int[]{0,0,1000,80});
			terColor.add(Color.YELLOW);
			dimter.add(new int[]{0,620,1000,80});
			terColor.add(Color.YELLOW);
			dimter.add(new int[]{150,150,80,400});
			terColor.add(Color.YELLOW);
			dimter.add(new int[]{230,150,610,80});
			terColor.add(Color.YELLOW);
			dimter.add(new int[]{760,150,80,400});
			terColor.add(Color.YELLOW);
			dimter.add(new int[]{230,470,420,80});
			terColor.add(Color.YELLOW);
			dimter.add(new int[]{300,300,350,100});
			terColor.add(Color.YELLOW);
			dimter.add(new int[]{600,400,50,220});
			terColor.add(Color.YELLOW);
			// walls 
			dimwall.add(new int[]{5,5,5,690});
			dimwall.add(new int[]{985,5,5,690});
			dimwall.add(new int[]{5,5,985,5});
			dimwall.add(new int[]{5,690,985,5});
			dimwall.add(new int[]{185,185,5,330});
			dimwall.add(new int[]{190,185,615,5});
			dimwall.add(new int[]{800,190,5,310});
			dimwall.add(new int[]{330,330,5,35});
			dimwall.add(new int[]{335,330,270,5});
			dimwall.add(new int[]{600,335,5,355});
			dimwall.add(new int[]{330,365,270,5});
			dimwall.add(new int[]{190,510,410,5});
			// Obstacles
			dimobst.add(new int[]{240,340,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{380,235,0,0});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{460,250,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{630,250,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{650,385,40,40});
			obsimg.add(pitv);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{700,385,40,40});
			obsimg.add(pitv);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{715,485,40,40});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{650,585,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{750,550,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{850,485,40,40});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{815,385,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{900,315,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{830,285,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{900,200,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{800,100,40,40});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{600,100,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{500,100,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{450,80,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{350,80,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{150,110,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{80,70,40,40});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{100,230,40,40});
			obsimg.add(pitv);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{25,350,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{120,420,40,40});
			obsimg.add(pith);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{100,470,40,40});
			obsimg.add(oil);
			imgIndex.add(new int[]{1});
			dimobst.add(new int[]{80,570,40,40});
			obsimg.add(junk);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{180,600,40,40});
			obsimg.add(barrels);
			imgIndex.add(new int[]{2});
			dimobst.add(new int[]{280,515,40,40});
			obsimg.add(pitv);
			imgIndex.add(new int[]{2});
						
			// finish and start line
			dimline.add(new int[]{520,400,10,70});
			lineimg.add(start3);
			dimline.add(new int[]{500,535,10,100});
			lineimg.add(finish3);
			break;
		}
		
		for (int I=0; I<dimter.size(); I++){
			int[] temp = dimter.get(I);
			terrain.add(makeRect(temp[0],temp[1],temp[2],temp[3]));
		}
		for (int J=0; J<dimwall.size(); J++){
			int[] temp = dimwall.get(J);
			wall.add(makeRect(temp[0],temp[1],temp[2],temp[3]));
		}
		for (int K=0; K<dimobst.size(); K++){
			int[] temp = dimobst.get(K);
			obstacles.add(makeRect(temp[0],temp[1],temp[2],temp[3]));
		}
		for (int L=0; L<dimline.size(); L++){
			int[] temp = dimline.get(L);
			lines.add(makeRect(temp[0],temp[1],temp[2],temp[3]));
		}
	
	}
		public Rectangle makeRect(int x, int y, int x2, int y2) {
			Rectangle newRect = new Rectangle(x,y,x2,y2);
			return newRect;
		}
		
}