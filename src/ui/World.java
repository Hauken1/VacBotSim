package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class World extends JPanel  {
	/*
	 *  
	 */
	public static final int TILE_SIZE = 40; //The size of one tile in the grid
	public static final int WORLD_HEIGHT = 800;
	public static final int WORLD_WIDTH = 800;
	
	private int rows;
	private int columns;
	private int objects;
	private int dustParticles;
	private ArrayList<FloorTile> tiles = new ArrayList<FloorTile>();
	
	public World(int rows, int columns, int dustParticles, int objects) {
		super();
		
		this.rows = rows;
		this.columns = columns;
		this.objects = objects;
		this.dustParticles = dustParticles;
		
		int nrTiles = rows * columns;
		
		System.out.println(nrTiles);
		
		//Creates tiles requested by user
		for(int i=0; i < nrTiles; i++) {
			FloorTile tile = new FloorTile(i); 
			tiles.add(tile);
		}
		
		/*
		 * Makes a tile into a objecttile
		 * Cant make objects on the same tile...
		 * Makes as many as user requested
		 */
		for(int i = 0; i < objects; i++) {
			Random rng = new Random();
			int nL = rng.nextInt(nrTiles);
			System.out.println(nL);
			if(!tiles.get(nL).isObject()){
				tiles.get(nL).setObject();
			}
			else i--;
		}
		
		/*
		 * Makes a tile into a dusttile
		 * Checks if a tile has a object and if it already has dust
		 */
		for(int i = 0; i < dustParticles; i++) {
			Random rng = new Random();
			int nL = rng.nextInt(nrTiles);
			System.out.println(nL);
			if(!tiles.get(nL).isObject() && !tiles.get(nL).isDust()){
				tiles.get(nL).setDust();
			}
			else i--;
		}
		
	}
	
	
	 @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.clearRect(0, 0, getWidth(), getHeight());
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        drawWorld(g);
	        //drawRobot(g);
	    }

	 public void drawWorld(Graphics g) {
		 
	     // Draw grid lines
         Graphics2D g2d = (Graphics2D) g;
         g2d.setColor(Color.BLACK);
         g2d.setStroke(new BasicStroke(1));

         int increment = 0;
         for(int x = 0; x < columns; x++) {
        	 for(int y = 0; y < rows; y++) {
        		 if(tiles.get(increment).isObject()) {
        			 g.setColor(Color.RED);
            	     g.fillRect(x*TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
            	 }
            	 else if (tiles.get(increment).isDust()) {
            		 g.setColor(Color.GRAY);
            	     g.fillRect(x*TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
            	 }
            	 else {
            		 g.setColor(Color.ORANGE);
            	     g.fillRect(x*TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
            	 }
        		 increment++;
        	 }
        		 
        	
        	 
         }
         
         // Draw vertical lines
         for (int i = 0; i < columns + 1; i++) {
             int x = i * TILE_SIZE;
             int y = rows * TILE_SIZE;

             if (i == columns) { // Last grid line should be on the inside
                 x--;
             }
             g2d.drawLine(x, 0, x, y);
         }

         // Draw horizontal lines
         for (int i = 0; i < rows + 1; i++) {
             int x = columns * TILE_SIZE;
             int y = i * TILE_SIZE;

             if (i == rows) { // Last grid line should be on the inside
                 y--;
             }
             g2d.drawLine(0, y, x, y);
         }
		 
         
		 
	 }
}
