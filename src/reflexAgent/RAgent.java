//Packages
package reflexAgent;

//Imports
import java.util.Random;
import ui.World;

/**
 * Reflex agent class
 * This class makes a simple reflex agent with some random elements 
 * Reflex agent is an agent which acts only on its current "percept".
 * @author Hauken
 *
 */
public class RAgent {
	//Statics
	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int EAST = 3;
	public static final int WEST = 4;
	public static final double EASTCHANCE = 0.1;
	public static final double WESTCHANCE = 0.3;
	public static final double FORWARDCHANCE = 0.80;
	//Variables
	private int nLoc; 
	private boolean learnB;
	private boolean nextTileIsDust;
	private boolean validMove;
	private boolean respawningDust;
	private int lastLastDir;
	private int cDirection;
	private int x;
	private int y; 
	//Other class objects
	private World world;
	
	/**
	 * Constructor of the Reflex agent class. Sets default settings and gives the 
	 * robot a random direction.
	 * @param nLocation the current location in the array the bot is placed
	 * @param x current row position 
	 * @param y current column position
	 * @param world contains the current simulation world
	 */
	public RAgent(int nLocation, int x, int y, World world, boolean respawndust) {
		nLoc = nLocation; 
		validMove = true;
		nextTileIsDust = false;
		this.respawningDust = respawndust;
		this.world = world;
		
		Random rng = new Random();
		int dir = rng.nextInt(4)+1;
		cDirection = dir;
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Method that moves the robot in the correct direction
	 */
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
	
	/**
	 * Method that sets a new location for the robot in the array
	 * @param loc the new location
	 */
	public void setLocation(int loc) {
		nLoc = loc;
	}
	
	/**
	 * Method that is called when the next tile in the array is a dust tile
	 */
	public void setNextTileIsdust() {
		nextTileIsDust = true;
	}
	
	/**
	 * Method that is called when the next tile in the array is not a dust tile
	 */
	public void setNextTileIsNotDust() {
		nextTileIsDust = false;
	}
	
	/**
	 * Method that checks what type of tile the next tile is. If it is a dust tile it, will
	 * move to that tile. If it is a regular tile it will move to it and get a new semi-random
	 * random direction. If it is a object it will try to change direction, if it hits another
	 * object it will try another direction, different from the two prior. 
	 * 
	 * @param nX the next x "coordinate"
	 * @param nY the next y "coordinate"
	 */
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
			else {	//Crashed
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
		        if (respawningDust) {
		        	if ( chance < 0.001) world.respawningDust();
		        }
		        if (chance < EASTCHANCE) {
	                cDirection = EAST;
	            } else if (chance < WESTCHANCE) {
	                cDirection = WEST;
	            } else if (chance < FORWARDCHANCE) {
	                cDirection = n;
	            } 
			}
			
		}
	}
}
