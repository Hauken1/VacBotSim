package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.rmi.CORBA.Tie;
import javax.swing.JPanel;

public class World extends JPanel  {
	/*
	 *  
	 */
	public static final int TILE_SIZE = 40; //The size of one tile in the grid
	public static final int WORLD_HEIGHT = 800;
	public static final int WORLD_WIDTH = 800;
	
	private boolean botSet;
	private int botNr;
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
		botSet = false;
		botNr = 0;
		
		int nrTiles = rows * columns;
		
		int tilenr = 0;
		for(int i=0; i < rows; i++) {
			for( int j = 0; j < columns; j++) {
				
			int x = i * TILE_SIZE;
			int y = j * TILE_SIZE;
			
			FloorTile tile = new FloorTile(tilenr, x, y); 
			tiles.add(tile);
			tilenr++;
			}
		}
		
		/*
		 * Makes a tile into a objecttile
		 * Cant make objects on the same tile...
		 * Makes as many as user requested
		 */
		for(int i = 0; i < objects; i++) {
			Random rng = new Random();
			int nL = rng.nextInt(nrTiles);
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
			if(!tiles.get(nL).isObject() && !tiles.get(nL).isDust()){
				tiles.get(nL).setDust();
			}
			else i--;
		}
		
		while(!botSet) {
			Random rng = new Random();
			int nL = rng.nextInt(nrTiles);
			if(!tiles.get(nL).isObject() && !tiles.get(nL).isDust()){
				tiles.get(nL).setBot();
				botSet = true;
				botNr = nL;
			}
		}
		
		//For this to work the col and row needs to be even numbers
		//Places the robot where the user wants
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//Checks if clicked area is inside a tile
				int x = e.getX();
				int y = e.getY();
				
				x = (x/TILE_SIZE);
				y = (y/TILE_SIZE);
				if(x == 0) x++;
				if( y == 0) y++;
				
				int test = 10 * x;
				int test2 = y;
				int test3 = test + test2 - 10;
				System.out.println("x: " + x +", y: " + y);
				System.out.println("test3: " + test3);
				
				int increment = 0;
				for(int yTile = 0; yTile < World.this.rows; yTile++) {
		        	 for(int xTile = 0; xTile < World.this.columns; xTile++) {
		        		 if(!tiles.get(increment).isObject() && !tiles.get(increment).isDust()
		        				 								&& tiles.get(increment).getTileNr() == test3 &&!botSet) {
		        			 System.out.println("incre i mouse: " +increment);
		        			 tiles.get(increment).setBot();
		        			 botSet = true;
		        			 botNr = increment;
		        			 repaint();
		        			 
		        		 }
		        		 increment++;
		        	 }
				}
			}
		});
		
	}
	
	 @Override
	 public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.clearRect(0, 0, getWidth(), getHeight());
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        drawWorld(g);
	        drawBot(g);
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
	 
	 public void drawBot(Graphics g) {
		 if(botSet) {
			int increment = 0;
			for(int x = 0; x < rows; x++) {
				for(int y = 0; y < columns; y++) {
					System.out.println(increment);
					if(tiles.get(increment).isBot()) {
						System.out.println(increment);
						System.out.println("bot" + botNr);
						System.out.println("x: " + x + "y: " + y);
						g.setColor(Color.GREEN);
						g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
					}
					increment++;
				}
			}
			 
		 }
		 
	 }
}
