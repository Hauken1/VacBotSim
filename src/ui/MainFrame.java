package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

//import vacworld.VacuumWorld;
//import vacworld.ui.Map;

public class MainFrame extends JFrame {


    private static final long serialVersionUID = -7659865770458522413L;
    private static final double DEFAULT_INTERVAL = 2;
    private static final double MAX_INTERVAL = 10;
    private static final double MIN_INTERVAL = 0.01;
    private static final String RUNNING = "RUNNING";
    private static final String STOPPED = "STOPPED";

    //The playground for the vacuum cleaner
    private World world;

    //GUI components
    private JPanel contentPane;
    private JTextField nrObjects;
    private JTextField dustPs;
    private JTextField nrRows;
    private JTextField nrCols;
    private JButton runBtn;
    private JButton btnStep;
    private JButton stopBtn;
    private JButton btnScore;
    private JButton resetButton;

    // Timer for starting/stopping simulation
    private Timer timer;

    private JLabel lblPerceptString;
    private JLabel lblActionString;
    private JLabel lblPositionString;
    private JLabel lblStatus;
    private JCheckBox chckbxShowGridLines;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);

	}
	
	//Create the main frame
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
                //run();
            	int r, c, d, o; 
            	r = Integer.parseInt(nrRows.getText());
            	c = Integer.parseInt(nrCols.getText());
            	d = Integer.parseInt(dustPs.getText());
            	o = Integer.parseInt(nrObjects.getText());
            	world = new World(r, c, d, o);
                world.setBounds(10, 10, 800, 800);
                contentPane.add(world);
                repaint();
                runBtn.setEnabled(false);
            }
        });
        runBtn.setBounds(900, 11, 89, 23);
        contentPane.add(runBtn);

        stopBtn = new JButton("Stop");
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //stop();
            }
        });
        stopBtn.setBounds(1000, 11, 89, 23);
        contentPane.add(stopBtn);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	remove(world);
            	repaint();
            	runBtn.setEnabled(true);
            }
        });
        resetButton.setBounds(950, 45, 89, 23);
        contentPane.add(resetButton);

        /*
        btnScore = new JButton("Score");
        btnScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //showScore();
            }
        });
        
        
        btnScore.setBounds(379, 79, 89, 23);
        contentPane.add(btnScore);
		*/
        //map = new Map(false);
        //map.setBounds(10, 11, 350, 350);
        //contentPane.add(map);
        
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
        
        
        JLabel simInfoLabel = new JLabel("Simulation information");
        simInfoLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        simInfoLabel.setBounds(900, 270, 200, 23);
        contentPane.add(simInfoLabel);
        
        
        
        /*
         * 
        JLabel lblAgentPosition = new JLabel("Sim info");
        lblAgentPosition.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblAgentPosition.setBounds(950, 270, 185, 23);
        contentPane.add(lblAgentPosition);
        
        JLabel lblPercept = new JLabel("Last Percept:");
        lblPercept.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblPercept.setBounds(379, 229, 89, 14);
        contentPane.add(lblPercept);

        JLabel lblAction = new JLabel("Last Action:");
        lblAction.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblAction.setBounds(379, 254, 89, 14);
        contentPane.add(lblAction);

        JLabel lblPosition = new JLabel("Position:");
        lblPosition.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblPosition.setBounds(379, 279, 89, 14);
        contentPane.add(lblPosition);

        JLabel lblSimulationStatus = new JLabel("Simulation Status");
        lblSimulationStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSimulationStatus.setBounds(379, 315, 155, 14);
        contentPane.add(lblSimulationStatus);

        lblStatus = new JLabel(STOPPED);
        lblStatus.setForeground(Color.RED);
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblStatus.setBounds(379, 345, 89, 14);
        contentPane.add(lblStatus);

        lblPerceptString = new JLabel("");
        lblPerceptString.setBounds(488, 229, 86, 14);
        contentPane.add(lblPerceptString);

        lblActionString = new JLabel("");
        lblActionString.setBounds(488, 254, 86, 14);
        contentPane.add(lblActionString);

        lblPositionString = new JLabel("");
        lblPositionString.setBounds(488, 279, 86, 14);
        contentPane.add(lblPositionString);
		
        chckbxShowGridLines = new JCheckBox("Show grid lines");
        chckbxShowGridLines.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
               // map.setShowGridLines(e.getStateChange() == ItemEvent.SELECTED);
                repaint();
            }
        });
        chckbxShowGridLines.setBounds(379, 108, 97, 23);
        contentPane.add(chckbxShowGridLines);
		*/
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
