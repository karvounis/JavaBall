package view;

import java.awt.*;
import javax.swing.*;
import controller.*;

/**
 * The main GUI.
 * @author Team C
 */
public final class GUI extends JFrame {

	/**Areas that a JavaBall match can be played.*/
	private static final String[] areaStrings = { "North", "Center", "South" };
	/**Qualification of a referee or a match.*/
	private static final String[] levelOfMatchStrings = {"Junior", "Senior"};
	/**Minimum and maximum week that a JavaBall match can be played.*/
	private static final int MIN_WEEKS = 1, MAX_WEEKS = 52;
	/**Width and Height of the mainGUI*/
	private static final int WIDTH = 1100, HEIGHT = 620;

	public JButton exitButton, barChartButton, allocateButton;
	public JSpinner weekIn;
	public JTextArea display;
	public JMenuItem menuItemNew, menuItemSearch, menuItemAbout;
	public JComboBox<String> areaList, levelOfMatchList;
	private Controller controller;

	public GUI(Controller controller) {
		//We will first save the output and then exit the program using a WindowListener
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(controller);
		setTitle("JavaBall");
		setSize( WIDTH, HEIGHT );
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

		this.controller = controller;

		layoutMenu();
		layoutTop();
		layoutCenter();
		layoutBottom();		
	}

	/**
	 * Layouts the Menu Bar and the Menu Items.
	 */
	private void layoutMenu(){
		// Creates a menubar for a JFrame
		JMenuBar menuBar = new JMenuBar();

		// Add the menubar to the frame
		setJMenuBar(menuBar);

		JMenu helpMenu = new JMenu("Help");
		menuItemAbout = new JMenuItem("About");
		helpMenu.add(menuItemAbout);
		menuItemAbout.addActionListener(controller);

		//JMenu that the user can choose whether to insert, delete or update a referee. In order for a referee to be deleted or updated, the user must search him.
		//If the search is successful, then he can delete or update him. That is the reason that we provide only 2 JMenuItems: New and Search.
		//Search provides the search function and if successful then provides the GUI for deleting or updating a referee. 
		JMenu refereeMenu = new JMenu("Referee");

		menuItemNew = new JMenuItem("New");
		menuItemNew.addActionListener(controller);
		refereeMenu.add(menuItemNew);

		menuItemSearch = new JMenuItem("Search");
		menuItemSearch.addActionListener(controller);
		refereeMenu.add(menuItemSearch);

		menuBar.add(refereeMenu);
		menuBar.add(helpMenu);
	}

	/**
	 * Layouts the components in the Center.
	 */
	private void layoutCenter(){
		display = new JTextArea();
		display.setEditable(false);
		display.setFont(new Font("Courier", Font.BOLD, 14));
		add(display, BorderLayout.CENTER);
	}

	/**
	 * Layouts the components in the North.
	 */
	private void layoutTop() {
		JPanel top = new JPanel();

		JLabel header = new JLabel("List of Referees");
		header.setFont(new Font("Courier", Font.ITALIC, 19));
		top.add(header);

		add(top, BorderLayout.NORTH);
	}

	/**
	 * Adds labels, text fields and buttons to the bottom of the GUI.
	 */
	private void layoutBottom() {
		// Instantiates panel for bottom of display. Uses GridBagLayout.
		JPanel bottom = new JPanel(new GridBagLayout());
		GridBagConstraints constraint = new GridBagConstraints();

		//Adds upper label, text field and button
		JLabel weekNumber = new JLabel("Enter Week Number");
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.weightx = 1.0;
		constraint.weighty = 1.0;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.insets = new Insets(5, 0, 5, 0);

		bottom.add(weekNumber, constraint);

		// add middle label, text field and button
		JLabel areaLabel = new JLabel("Enter the area");
		constraint.gridy = 1;
		bottom.add(areaLabel, constraint);

		// add lower label text field and button
		JLabel levelOftheMatchLabel = new JLabel("Enter Level of the Match");
		constraint.gridy = 2;
		bottom.add(levelOftheMatchLabel, constraint);

		//Spinner component. Has a minimum value of 1 and a maximum of 52.
		weekIn = new JSpinner(new SpinnerNumberModel(MIN_WEEKS, MIN_WEEKS, MAX_WEEKS, 1));
		weekIn.setPreferredSize(new Dimension(90,25));
		constraint.gridx = 1;
		constraint.gridy = 0;
		constraint.insets = new Insets(5, 0, 5, 0);
		bottom.add(weekIn, constraint);

		//ComboBox that allows the user to choose the areas that a match can be played.
		areaList = new JComboBox<String>(areaStrings);
		constraint.gridy = 1;
		bottom.add(areaList, constraint);

		//ComboBox that allows the user to choose the level of the match.
		levelOfMatchList = new JComboBox<String> (levelOfMatchStrings);
		constraint.gridy = 2;
		bottom.add(levelOfMatchList, constraint);

		//Allocate Button that when pressed starts the allocate referees process.
		allocateButton = new JButton("Allocate");
		allocateButton.addActionListener(controller);
		allocateButton.setPreferredSize(new Dimension(90,30));
		constraint.gridx = 2;
		constraint.gridy = 0;
		constraint.fill = GridBagConstraints.NONE;
		bottom.add(allocateButton, constraint);

		//Adds Bar Chart button.
		barChartButton = new JButton("Bar Chart");
		barChartButton.addActionListener(controller);
		barChartButton.setPreferredSize(new Dimension(90,30));
		constraint.gridy = 1;
		bottom.add(barChartButton, constraint);

		//Adds Exit button.
		exitButton = new JButton("Exit");
		exitButton.addActionListener(controller);
		constraint.gridy = 2;
		exitButton.setPreferredSize(new Dimension(90,30));
		bottom.add(exitButton, constraint);

		add(bottom, BorderLayout.SOUTH);
	}
}