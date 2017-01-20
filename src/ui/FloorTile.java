package ui;

public class FloorTile {
	
	private boolean isObject;
	private boolean hasDust;
	private int tileNr;
	
	public FloorTile(int id) {
		tileNr = id;
		hasDust = false;
		isObject = false;
	}

	public void setDust() {
		hasDust = true;
		System.out.println("Tilenr " + tileNr + " is a dusttile" );
	}

	public void setObject() {
		isObject = true;
		System.out.println("Tilenr " + tileNr + " is an object" );
	}
	
	public boolean isDust() {
		return hasDust;
	}
	public boolean isObject() {
		return isObject;
	}
	
	
}
