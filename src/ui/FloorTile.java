package ui;

/**
 * Method that holds all the information about a tile. 
 * What type of tile it is and location.
 * @author Hauken
 *
 */
public class FloorTile {
	//Variables
	private boolean isObject;
	private boolean hasDust;
	private boolean isBot;
	private boolean wasDust;
	private int tileNr;
	private int x; 
	private int y;
	
	/**
	 * Constructor of the FloorTile class. Sets default tile settings.
	 * @param id position in the tile array
	 * @param x x coordinate 
	 * @param y y coordinate
	 */
	public FloorTile(int id, int x, int y) {
		tileNr = id;
		hasDust = false;
		isObject = false;
		isBot = false;
		wasDust = false;
		this.x = x;
		this.y = y;
	}

	/**
	 * Method that retrieves this tiles position in the array
	 * @return the array number
	 */
	public int getTileNr() {
		return tileNr;
	}
	/**
	 * Methods that is called when this tile is a dust tile
	 */
	public void setDust() {
		hasDust = true;
		wasDust = true;
	}

	/**
	 * Method that "cleans" this tile from dust
	 */
	public void removeDust() {
		hasDust = false;
	}
	
	public boolean wasDustEarlier() {
		return wasDust;
	}
	
	/**
	 * Method that is called when this tile is a object tile
	 */
	public void setObject() { 
		isObject = true;
		
	}
	
	/**
	 * Method that is called to check if this tile is a dust tile
	 * @return true/false depending on if the tile is a dust tile or not
	 */
	public boolean isDust() {
		return hasDust;
	}
	
	/**
	 * Method that is called to check if this tile is a object tile 
	 * @return true/false depending on if the tile is a object tile or not
	 */
	public boolean isObject() {
		return isObject;
	}
	
	/**
	 * Method that returns the x coordinate of the tile
	 * @return the x coordinate
	 */
	public int xCoor() {
		return x;
	}
	
	/**
	 * Method that returns the y coordinate of the tile
	 * @return the y coordinate
	 */
	public int yCoor() {
		return y;
	}
	
	/**
	 * Method that checks if this tile is the robot or not
	 * @return true/false depending on this tile is the robot or not
	 */
	public boolean isBot() {
		return isBot;
	}
	
	/**
	 * Method that makes this tile a robot tile
	 */
	public void setBot() {
		isBot = true;
	}
	
	/**
	 *Method that removes the robot from this tile 
	 */
	public void removebot() {
		isBot = false;
	}
}
