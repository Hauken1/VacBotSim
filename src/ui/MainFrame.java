//Packages
package ui;
//Imports
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * Main class that makes all the GUI and creates the simulation world
 * @author Hauken
 */
public class MainFrame extends JFrame {

	//Statics
    private static final long serialVersionUID = -7659865770458522413L;
    private static final double DEFAULT_INTERVAL = 2;
    private static final double MAX_INTERVAL = 10;
    private static final double MIN_INTERVAL = 0.01;
    private static final String RUNNING = "RUNNING";
    private static final String STOPPED = "STOPPED";

    //The playground for the vacuum cleaner
    private World world;

    //Thread for the simulation to run on
    private Thread thread;
    
    //GUI components
    private JPanel contentPane;
    private JTextField nrObjects;
    private JTextField dustPs;
    private JTextField nrRows;
    private JTextField nrCols;
    private JTextField eastChance;
    private JTextField westChance;
    private JTextField forwardChance;
    private JButton runBtn;
    private JButton stopBtn;
    private JButton resetButton;
    private JCheckBox dustRespawnBox;
    private JCheckBox strictMovementBox;
    private JCheckBox rememberObjectsBox;

    // Timer for starting/stopping simulation
    private Timer timer;

    private JLabel stepLbl;
    private JLabel scoreLbl;
    private JLabel dustRmnLbl;
    private JLabel crashLbl;
    private JLabel statusLbl;
    
    /**
     * Main method. Is called when the program is executed
     * Makes the MainFrame JFrame object
     * @param args command line arguments
     */
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
	
	/**
	 * Constructor of the MainFrame class
	 * Creates all the GUI elements
	 */
	public MainFrame() {
		
	    // Generated code for child components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 1000);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        runBtn = new JButton("Run");
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int r, c, d, o, eC, wC, fC; 
            	r = Integer.parseInt(nrRows.getText());
            	c = Integer.parseInt(nrCols.getText());
            	d = Integer.parseInt(dustPs.getText());
            	o = Integer.parseInt(nrObjects.getText());
            	eC = Integer.parseInt(eastChance.getText());
            	wC = Integer.parseInt(westChance.getText());
            	fC = Integer.parseInt(forwardChance.getText());
            	boolean dustRespawn = false;
            	boolean strictMovement = false;
            	boolean rememberObject = false;
            	if (dustRespawnBox.isSelected()) dustRespawn = true;
            	if (strictMovementBox.isSelected()) strictMovement = true;
            	if (rememberObjectsBox.isSelected()) rememberObject = true;
            	world = new World(r, c, d, o, stepLbl, dustRespawn,
            			strictMovement, rememberObject, crashLbl, dustRmnLbl, eC, wC, fC);
                world.setBounds(10, 10, 800, 800);
                contentPane.add(world);
                repaint();
                world.simStarted();
                runBtn.setEnabled(false);
                thread = new Thread(new Runnable() {
                	/**
                	 * Run method for the simulation thread. Starts the simulation
                	 */
					public void run() {
						world.start();
					}
				});
                thread.start();
            }
        });
        runBtn.setBounds(900, 11, 89, 23);
        contentPane.add(runBtn);

        stopBtn = new JButton("Stop");
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(world.checkSimulationStatus()) {
            	   world.simStopped();
               }
               else world.simStarted();
            	
            }
        });
        stopBtn.setBounds(1000, 11, 89, 23);
        contentPane.add(stopBtn);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	world.simStopped();
            	remove(world);
            	repaint();
            	runBtn.setEnabled(true);
            }
        });
        resetButton.setBounds(950, 45, 89, 23);
        contentPane.add(resetButton);
        
        JLabel simVarLabel = new JLabel("Simulation variables");
        simVarLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        simVarLabel.setBounds(900, 110, 185, 23);
        contentPane.add(simVarLabel);

        JLabel dustParticlesLabel = new JLabel("Dust Particles:");
        dustParticlesLabel.setBounds(900, 141, 89, 14);
        contentPane.add(dustParticlesLabel);
        
        dustPs = new JTextField();
        dustPs.setText("10");
        dustPs.setBounds(1000, 141, 86, 20);
        contentPane.add(dustPs);
        
        JLabel nrObjectsLabel = new JLabel("Objects:");
        nrObjectsLabel.setBounds(900, 172, 89, 14);
        contentPane.add(nrObjectsLabel);
        
        nrObjects = new JTextField();
        nrObjects.setText("2");
        nrObjects.setBounds(1000, 172, 86, 20);
        contentPane.add(nrObjects);
        
        JLabel nrRowsLabel = new JLabel("Rows:");
        nrRowsLabel.setBounds(900, 197, 89, 14);
        contentPane.add(nrRowsLabel);
        
        nrRows = new JTextField();
        nrRows.setText("10");
        nrRows.setBounds(1000, 197, 86, 20);
        contentPane.add(nrRows);
        
        JLabel nrColsLabel = new JLabel("Columns:");
        nrColsLabel.setBounds(900, 229, 89, 14);
        contentPane.add(nrColsLabel);
        
        nrCols = new JTextField();
        nrCols.setText("10");
        nrCols.setBounds(1000, 229, 86, 20);
        contentPane.add(nrCols);
        
        JLabel eastText = new JLabel("East chance: ");
        eastText.setBounds(900, 260, 110, 20);
        contentPane.add(eastText);
       
        JLabel westText = new JLabel("West chance: ");
        westText.setBounds(900, 285, 110, 20);
        contentPane.add(westText);
        
        JLabel forwardText = new JLabel("Forward chance: ");
        forwardText.setBounds(900, 310, 110, 20);
        contentPane.add(forwardText);
        
        eastChance = new JTextField();
        eastChance.setText("10");
        eastChance.setBounds(1030, 260, 55, 20);
        contentPane.add(eastChance);
        
        westChance = new JTextField();
        westChance.setText("30");
        westChance.setBounds(1030, 285, 55, 20);
        contentPane.add(westChance);
        
        forwardChance = new JTextField();
        forwardChance.setText("60");
        forwardChance.setBounds(1030, 310, 55, 20);
        contentPane.add(forwardChance);
        
        dustRespawnBox = new JCheckBox("Dust respawn");
        dustRespawnBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dustRespawnBox.setBounds(900, 340, 110, 20);
        contentPane.add(dustRespawnBox);
        
        strictMovementBox = new JCheckBox("Strict movement");
        strictMovementBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        strictMovementBox.setBounds(900, 360, 150, 20);
        contentPane.add(strictMovementBox);
        
        rememberObjectsBox = new JCheckBox("Remember object placement");
        rememberObjectsBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        rememberObjectsBox.setBounds(900, 380, 250, 20);
        contentPane.add(rememberObjectsBox);
        
        JLabel simInfoLabel = new JLabel("Simulation information");
        simInfoLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        simInfoLabel.setBounds(900, 420, 200, 23);
        contentPane.add(simInfoLabel);
         
        JLabel stepText = new JLabel("Steps: ");
        stepText.setBounds(900, 450, 89, 20);
        stepText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(stepText);
        
        stepLbl = new JLabel();
        stepLbl.setText("1000");
        stepLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        stepLbl.setBounds(1000, 450, 89, 14);
        contentPane.add(stepLbl);
        
        JLabel dustReText = new JLabel("Dust: ");
        dustReText.setBounds(900, 470, 89, 20);
        dustReText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(dustReText);
        
        dustRmnLbl = new JLabel();
        dustRmnLbl.setText("");
        dustRmnLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
        dustRmnLbl.setBounds(1000, 470, 89, 20);
        contentPane.add(dustRmnLbl);
        
        JLabel crashText = new JLabel("Crashes: ");
        crashText.setBounds(900, 490, 89, 20);
        crashText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(crashText);
        
        crashLbl = new JLabel();
        crashLbl.setText("0");
        crashLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
        crashLbl.setBounds(1000, 490, 89, 20);
        contentPane.add(crashLbl);
        
        
        // Set properties
        this.setTitle("VacBot Simulator");
        this.setResizable(false);

        // Initialize timer for running simulation automatically
        timer = new Timer((int) (DEFAULT_INTERVAL * 1000),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                       // step();
                    }
                });
        timer.setInitialDelay(0);
	}
}
