package reflexAgent;

import java.util.Random;

import ui.World;


public class RAgent {
	
	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int EAST = 3;
	public static final int WEST = 4;
	private int nLoc; 
	private boolean learnB;
	private boolean nextTileIsDust;
	private boolean validMove;
	private int lastLastDir;
	private int cDirection;
	private int x;
	private int y; 
	
	private World world;
	
	public RAgent(int nLocation, int x, int y, World world) {
		nLoc = nLocation; 
		validMove = true;
		nextTileIsDust = false;
		this.world = world;
		Random rng = new Random();
		int dir = rng.nextInt(4)+1;
		
		cDirection = dir;
		System.out.println(cDirection);
		
		this.x = x;
		this.y = y;
		
		//System.out.print("x: " + x + ", y :" + y + " ! !");
		
		
	}
	
	public void move() {
		switch(cDirection) {
		case 1 : //North
			int nX1 = x + 1;
			checkNextTile(nX1, y);
			break; 
		case 2: //South
			int nX2 = x - 1;
			checkNextTile(nX2, y);
			break;
		case 3: //East
			int nY1 = y +1;
			checkNextTile(x, nY1);
			break;
		case 4: //West
			int nY2 = y - 1;
			checkNextTile(x, nY2);
			break;
		default:
			checkNextTile(x, y);
			break;
		}
		
	}
	
	public void changeDir() {
		
	}
	
	public void suckDirt() {
		//world.checkForDust(x, y);
	}
	
	public void crash() {
		
	}
	
	public void setLocation(int loc) {
		nLoc = loc;
	}
	
	public void setNextTileIsdust() {
		nextTileIsDust = true;
	}
	
	public void setNextTileIsNotDust() {
		nextTileIsDust = false;
	}
	
	public void checkNextTile(int nX, int nY) {
		int n = world.checkTile(nX, nY, nLoc, cDirection);
		if(n == 0) {	//Cant find the next tile, or the bot crashed
			if(validMove == false) { //Crashed last tile
				int dir = 0;
				while(dir == 0 || dir == lastLastDir) { //Tries to find a new dir
					Random rng = new Random();
					dir = rng.nextInt(4)+1;
				}
				cDirection = dir;
			}
			else {
				validMove = false;
				lastLastDir = cDirection;
				int dir = 0;
				while(dir == 0 || dir == cDirection ) { //Tries to find a new dir
					Random rng = new Random();
					dir = rng.nextInt(4)+1;
				}
				System.out.println("dir :" + dir);
				cDirection = dir;
			}
		}
		else {
			validMove = true;
			x = nX;
			y = nY;
			if(nextTileIsDust) {
			cDirection = n;
			}
			else {
				Random r = new Random();
		        float chance;
		        
		        chance = r.nextFloat();
		        
		        if (chance < 0.1) {
	                cDirection = EAST;
	            } else if (chance < 0.2) {
	                cDirection = WEST;
	            } else if (chance < 0.95) {
	                cDirection = n;
	            } 
			}
			
		}
	}
}
