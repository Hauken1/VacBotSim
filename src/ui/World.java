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
import java.util.concurrent.TimeUnit;

import javax.rmi.CORBA.Tie;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import reflexAgent.RAgent;

/**
 * The playground for the vacuum cleaner, extends a JPANEL. This class keeps track of 
 * what tile is what and draws the objects. It also starts the agents and has a run function
 * that makes the bot run until the simulation is complete (the area is cleaned or steps == 0) 
 * @author Hauken
 */
public class World extends JPanel  {
	//Statics
	public static final int TILE_SIZE = 40; //The size of one tile in the grid
	public static final int WORLD_HEIGHT = 800;
	public static final int WORLD_WIDTH = 800;
	private static final int DELAY = 50; //Delay between each action
	//Variables
	private boolean botSet;
	private boolean simSet;
	private boolean dustRespawn;
	private boolean strictMovement;
	private boolean rememberObjectPlacement;
	private int botNr;
	private int rows;
	private int columns;
	private int objects;
	private int dustParticles;
	private int MaxDustParticles;
	private int steps;
	private int crashes;
	private int eastChance;
	private int westChance;
	private int forwardChance;
	//GUI Components
	public JLabel stepLbl;
	public JLabel crashLbl;
	public JLabel dustRmnLabel;
	//Array and objects
	private ArrayList<FloorTile> tiles = new ArrayList<FloorTile>();
	private ArrayList<Integer> wasDustTiles = new ArrayList<Integer>();
	private ArrayList<Integer> crashedHere = new ArrayList<Integer>();
	private RAgent agent;
	
	/**
	 * Constructor. Adds all the tiles corresponding to number of rows/columns
	 * @param rows how many rows in the grid
	 * @param columns how many columns in the grid
	 * @param dustParticles how many dust particales
	 * @param objects how many objects, excluding the border
	 * @param lbl label used to decrement steps in the GUI
	 * @param dustRespawn true/false depending on dust should respawn or not
	 * @param strictMovement true/false depending on there should be no random movement
	 * @param rememberObject true/false depending on if the agent should remember crashlocations
	 * @param crashLbl label to show number of crashes
	 * @param dustRmnLabel label to show number of dust particles remaining
	 * @param eastChance chance for the agent to turn east, chosen by user
	 * @param westChance chance for the agent to turn west, chosen by user
	 * @param forwardChance chance for the agent to turn forward, chosen by uyser
	 * 
	 */
	public World(int rows, int columns, int dustParticles, int objects, JLabel lbl,
					boolean dustRespawn, boolean strictMovement,
							boolean rememberObject, JLabel crashLbl, JLabel dustRmnLabel,
							int eastChance, int westChance, int forwardChance) {
		super();
		
		steps = 1000;
		crashes = 0;
		this.rows = rows;
		this.columns = columns;
		this.objects = objects;
		this.dustParticles = dustParticles;
		MaxDustParticles = dustParticles;
		stepLbl = lbl;
		this.crashLbl = crashLbl;
		this.dustRmnLabel = dustRmnLabel;
		String s = Integer.toString(dustParticles);
		this.dustRmnLabel.setText(s);
		botSet = false;
		botNr = 0;
		this.dustRespawn = dustRespawn;
		this.strictMovement = strictMovement;
		this.rememberObjectPlacement = rememberObject;
		
		this.eastChance = eastChance;
		this.westChance = westChance;
		this.forwardChance = forwardChance;
		
		int nrTiles = rows * columns;
		int tilenr = 0;
		
		
		
		
		//Nested loop for creating all the tiles corresponding to the rows*columns
		for(int i=0; i < rows; i++) {
			for( int j = 0; j < columns; j++) {
			int x = i;
			int y = j;
			FloorTile tile = new FloorTile(tilenr, x, y); 
			tiles.add(tile);
			tilenr++;
			}
		}
		
		//Makes the borders into objects
		tilenr = 0;
		for(int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (i == 0) {
					if(!tiles.get(tilenr).isObject()){
						tiles.get(tilenr).setObject();
					}
				}
				if( i == rows-1 ){
					if(!tiles.get(tilenr).isObject()){
						tiles.get(tilenr).setObject();
					}
				}
				if ( j == 0) {
					if(!tiles.get(tilenr).isObject()){
						tiles.get(tilenr).setObject();
					}
				}
				if( j == columns-1) {
					if(!tiles.get(tilenr).isObject()){
						tiles.get(tilenr).setObject();
					}
				}
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
				wasDustTiles.add(nL);
			}
			else i--;
		}
		
		//Makes a robot tile and creates the agent
		//Dust or object tiles can not be a robot tile
		while(!botSet) {
			Random rng = new Random();
			int nL = rng.nextInt(nrTiles);
			if(!tiles.get(nL).isObject() && !tiles.get(nL).isDust()){
				tiles.get(nL).setBot();
				int x = tiles.get(nL).xCoor();
				int y = tiles.get(nL).yCoor();
				botSet = true;
				botNr = nL;
				agent = new RAgent(nL, x, y, this, this.dustRespawn,
							this.strictMovement, this.rememberObjectPlacement,
							eastChance, westChance, forwardChance);
			}
		}
		
		/*
		 * Decided to not implement this and rather make it "spawn" randomly
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
				
				int increment = 0;
				for(int yTile = 0; yTile < World.this.rows; yTile++) {
		        	 for(int xTile = 0; xTile < World.this.columns; xTile++) {
		        		 if(!tiles.get(increment).isObject() && !tiles.get(increment).isDust()
		        				 								&& tiles.get(increment).getTileNr() == test3 &&!botSet) {
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
		*/
	}
	
	/**
	 * Method sets that the simulation is currently running
	 */
	public void simStarted() {
		simSet = true;
	}
	
	/**
	 * Method that stops the simulation
	 */
	public void simStopped() {
		simSet = false;
	}
	/**
	 * Method that check if the simulation is running
	 * @return true/false depending on if the simulation is running or not
	 */
	public boolean checkSimulationStatus() {
		return simSet;
	}
	
	/**
	 * Method that starts the simulation and keeps it running until the simulation
	 * is complete or the simulation is stopped.
	 */
	public void start() {
		while(dustParticles != 0 && !(steps <= 0)) {
			agent.move();
			
			this.repaint();
			steps--;
			String s = Integer.toString(steps);
			stepLbl.setText(s);
			s = Integer.toString(dustParticles);
			dustRmnLabel.setText(s);
			s = Integer.toString(crashes);
			crashLbl.setText(s);
			if(simSet == false) {
				while(simSet == false) {
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					}
					catch ( Exception e) {
						System.out.println("Timeunit error");
					}
				}
			}
			try {
				TimeUnit.MILLISECONDS.sleep(DELAY);
			}
			catch ( Exception e) {
				System.out.println("Timeunit error");
			}
		}
	}
	
	/**
	 * Method that iterates through each tile until it find the next tile in the direction
	 * of the robot. Depending on what tile it is, it does some actions
	 * @param nX the x coordinate of the next tile
	 * @param nY the y coordinate of the next tile
	 * @param loc the location/position of the tile in the array
	 * @param cDir the direction of the robot
	 * @return a number which decides what the robot should do
	 */
	public int checkTile(int nX, int nY, int loc, int cDir) {
		int nr = 0; 
		boolean crashedHereEarlier = false;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				nr++;				
				if(i == nX && j == nY) {
					if(rememberObjectPlacement){
						for(int c = 0; c < crashedHere.size(); c++) {
							int test = crashedHere.get(c).intValue();
							if(test == nr) { crashedHereEarlier = true;
							}
						}
					}
					if(tiles.get(nr).isDust()) {	//If next tile is dust
						agent.setNextTileIsdust();
						tiles.get(loc).removebot();
						tiles.get(nr).removeDust();
						dustParticles--;
						tiles.get(nr).setBot();
						agent.setLocation(nr);
						return cDir;
					}
					else if(tiles.get(nr).isObject()) {	//Crash. Increment crash counter.
						if(!crashedHereEarlier) {
							crashes++;
							crashedHere.add(nr);
						}
						return 0;
					}
					else  {	//Keeps moving
						agent.setNextTileIsNotDust();
						tiles.get(loc).removebot();
						tiles.get(nr).setBot();
						agent.setLocation(nr);
						return cDir;
					}
				}
			}
		}
		return 0;
	}
	
	/**
	 * Method that respawns dust. Should not spawn on other dust tiles and
	 * object tiles. Dust should only spawn on tiles
	 * that had dust earlier. 
	 */
	public void respawningDust() {
		/*
		 * Respawns a dusttile
		 * Checks if a tile has a object and if it already has dust and was dust before
		 * Needs to be atleast 1 dust
		 */
		if(dustParticles < MaxDustParticles) {
			boolean set = false;
			int n = wasDustTiles.size() - 1;
			while(!set) {
				Random rng = new Random();
				int nL = rng.nextInt(n);
				nL = wasDustTiles.get(nL).intValue();
				if(!tiles.get(nL).isObject() && !tiles.get(nL).isDust() && tiles.get(nL).wasDustEarlier()){
					tiles.get(nL).setDust();
					dustParticles++;
					set = true;
				}
			}
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
	        drawBot(g);
	 }

	 /**
	  * Draws the dust, object and floor tiles. 
	  * @param g Painting component, Graphics g 
	  */
	 public void drawWorld(Graphics g) {
		 
	     // Draw grid lines
         Graphics2D g2d = (Graphics2D) g;
         g2d.setColor(Color.BLACK);
         g2d.setStroke(new BasicStroke(1));
         
         ImageIcon icon = new ImageIcon("src/images/woodFloor40x40.png"); 

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
            		 //g.setColor(Color.ORANGE);
            	     //g.fillRect(x*TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
            	     icon.paintIcon(this, g, x*TILE_SIZE - 1, y*TILE_SIZE -1);
            	 }
        		 increment++;
        	 } 		 
         }
         
         /*
          * Code that draw gridlines on the JPANEL. 
          * Decided that this was not necessary, but will keep the code
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
		 */
	 }
	 
	 /**
	  * Draws the vacuum cleaner robot (R2D2 edition)
	  * @param g painting component
	  */
	 public void drawBot(Graphics g) {
		 if(botSet) {
			ImageIcon icon = new ImageIcon("src/images/woodFloorR2D240x40.png"); 
			int increment = 0;
			for(int x = 0; x < columns; x++) {
				for(int y = 0; y < rows; y++) {
					if(tiles.get(increment).isBot()) {
						//g.setColor(Color.GREEN);
						//g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
						icon.paintIcon(this, g, x*TILE_SIZE - 1, y*TILE_SIZE -1);
					}
					increment++;
				}
			} 
		 }
	 }	 
}
