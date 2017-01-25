package ui;

public class FloorTile {
	
	private boolean isObject;
	private boolean hasDust;
	private boolean isBot;
	private int tileNr;
	private int x; 
	private int y;
	
	public FloorTile(int id, int x, int y) {
		tileNr = id;
		hasDust = false;
		isObject = false;
		isBot = false;
		this.x = x;
		this.y = y;
	}

	public int getTileNr() {
		return tileNr;
	}
	public void setDust() {
		hasDust = true;
		
	}

	public void setObject() {
		isObject = true;
		
	}
	
	public boolean isDust() {
		return hasDust;
	}
	public boolean isObject() {
		return isObject;
	}
	
	public int xCoor() {
		return x;
	}
	
	public int yCoor() {
		return y;
	}
	
	public boolean isBot() {
		return isBot;
	}
	
	public void setBot() {
		System.out.println("tilenr: " + tileNr);
		isBot = true;
	}
	
	public void removeDust() {
		hasDust = false;
	}
	
	public void removebot() {
		isBot = false;
	}
}
