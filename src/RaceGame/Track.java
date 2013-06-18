package RaceGame;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author WorkHorse
 *
 */
public class Track{
	public ArrayList<Rectangle> wall = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> terrain = new ArrayList<Rectangle>();
	public static ArrayList<int[]> dimwall = new ArrayList<int[]>();
	public static ArrayList<int[]> dimter = new ArrayList<int[]>();
	public ArrayList<Color> terColor = new ArrayList<Color>();
	public Track(int i){
		switch (i){
		case 1:
			// terrain 
			dimter.add(new int[]{0,0,50,700});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{950,0,50,700});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{0,0,1000,50});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{0,650,1000,50});
			terColor.add(Color.GREEN);
			dimter.add(new int[]{180,180,50,650});
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
			break;
		case 2:
			break;
		case 3:
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
	
	}
		public Rectangle makeRect(int x, int y, int x2, int y2) {
			Rectangle newRect = new Rectangle(x,y,x2,y2);
			return newRect;
		}
		
}