import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class ImageMatrixGUI {

	// ATTRIBUTES
	// Components of the GUI
	private JFrame componentFrame = new JFrame(); // Base layer of the GUI
	private JPanel topPanel = new JPanel(); // Top panel to hold the image manipulation controls
	private JPanel bottomPanel = new JPanel(); // Bottom panel to hold the image
	private JScrollPane scrollPane = new JScrollPane(bottomPanel, 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Makes the bottom panel scrollable if the image > the panel
	private JMenuBar menuBar = new JMenuBar(); // Menu bar at the top to hold the menus
	private JTextField inputArea = new JTextField(3); // Text field to get the number of degrees from the user
	private JButton rotateButton = new JButton("Rotate Image"); // Button to rotate the image
	private JButton shearXButton = new JButton("Shear Image on x-axis"); // Button to shear the image horizontally
	private JButton shearYButton = new JButton("Shear Image on y-axis"); // Button to shear the image vertically
	private JSlider zoomSlider; // Fun sliding bar for the zoom
	private JLabel imageLabel = new JLabel(); // Label to hold the image

	// File reader for importing the files
	private ImageMatrixFileReader reader = new ImageMatrixFileReader();
	// Current matrix object
	private ImageMatrix currentMatrix;
	// Current file object
	private File currentFile;

	// Method which builds the GUI
	public void buildGUI() {
		// Set the size of the frame
		componentFrame.setSize(1000, 600);
		// Make sure the program ends when the frame is closed
		componentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sets the size of the panel
		topPanel.setSize(componentFrame.getWidth(), (componentFrame.getHeight() / 3));

		// Sets the size of the panel
		bottomPanel.setSize(componentFrame.getWidth(), (componentFrame.getHeight() / 3) * 2);

		// Sets the scroll speed on the JScrollPane
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);

		// Sets the background colour of the panels etc
		menuBar.setBackground(Color.WHITE);
		topPanel.setBackground(Color.WHITE);
		bottomPanel.setBackground(Color.WHITE);

		// Makes the file menu to go on the bar
		JMenu fileMenu = new JMenu("File");
		// Makes the edit menu
		JMenu editMenu = new JMenu("Edit");

		// Makes the load item for the file menu
		JMenuItem loadItem = new JMenuItem("Load New Image");
		loadItem.addActionListener(new LoadListener(this));
		fileMenu.add(loadItem);

		// Makes the reset image button for the file
		JMenuItem resetItem = new JMenuItem("Reset Image");
		resetItem.addActionListener(new ResetFileListener(this));
		fileMenu.add(resetItem);

		// Creates the menu item zoom buttons
		JMenuItem zoom100Button = new JMenuItem("100%");
		JMenuItem zoom200Button = new JMenuItem("200%");
		JMenuItem zoom400Button = new JMenuItem("400%");
		JMenuItem zoom800Button = new JMenuItem("800%");

		// Adds the zoom buttons' action listeners
		zoom100Button.addActionListener(new ZoomListener(this, 100));
		zoom200Button.addActionListener(new ZoomListener(this, 200));
		zoom400Button.addActionListener(new ZoomListener(this, 400));
		zoom800Button.addActionListener(new ZoomListener(this, 800));

		// Puts the zoom radio buttons on the edit menu
		editMenu.add(new JLabel("Zoom Image:"));
		editMenu.add(zoom100Button);
		editMenu.add(zoom200Button);
		editMenu.add(zoom400Button);
		editMenu.add(zoom800Button);

		// Adds the menus to the menu bar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);

		// Initialises the slider with its max and min values
		zoomSlider = new JSlider();
		zoomSlider.setMaximum(800);
		zoomSlider.setMinimum(100);

		// Sets the sliders tick settings
		zoomSlider.setMajorTickSpacing(100);
		zoomSlider.setPaintTicks(true);
		zoomSlider.setSnapToTicks(true);

		// Adds the change listener to the slider
		zoomSlider.addChangeListener(new ZoomChangeListener(this));

		// Paints labels on the slider
		zoomSlider.setPaintLabels(true);

		// Inaitialises the rotate button's action listener
		rotateButton.addActionListener(new RotateListener(this));

		// Initialises the shear buttons' action listeners
		shearXButton.addActionListener(new ShearListener(this, true));
		shearYButton.addActionListener(new ShearListener(this, false));

		// Add the components to the top panel
		topPanel.add(new JLabel("Zoom (%)"), BorderLayout.WEST);
		topPanel.add(zoomSlider, BorderLayout.WEST);
		topPanel.add(new JLabel("Enter value:"));
		topPanel.add(inputArea, BorderLayout.EAST);
		topPanel.add(rotateButton, BorderLayout.CENTER);
		topPanel.add(shearXButton, BorderLayout.CENTER);
		topPanel.add(shearYButton, BorderLayout.CENTER);

		// Adds the label to the bottom panel
		bottomPanel.add(imageLabel);// BorderLayout.CENTER

		// Adds the menu bar and panels to the frame
		componentFrame.add(topPanel, BorderLayout.NORTH);
		componentFrame.add(scrollPane, BorderLayout.CENTER);
		componentFrame.setJMenuBar(menuBar);

		// Make the frame visible
		componentFrame.setVisible(true);
	}

	// Getter for the current matrix object
	public ImageMatrix getCurrentMatrix() {
		return currentMatrix;
	}

	// Setter fo the current matrix object
	public void setCurrentMatrix(ImageMatrix matrix) {
		this.currentMatrix = matrix;
	}

	// Getter for the current file object
	public File getCurrentFile() {
		return currentFile;
	}

	// Setter for the current file object
	public void setCurrentFile(File newFile) {
		this.currentFile = newFile;
	}

	// Getter for slider vale
	public int getSliderValue() {
		int value = zoomSlider.getValue();
		return value;
	}

	// Setter for slider value
	public void setSliderValue(int value) {
		zoomSlider.setValue(value);
	}

	// Method for changing the image on the label
	public void changeImage(BufferedImage file) {
		imageLabel.setIcon(new ImageIcon(file));
	}

	// Method for getting the text from the text area
	public String getTextFromGUI() {
		String input = inputArea.getText();
		return input;
	}

	// Method for choosing the file
	public File chooseFile() {
		// Creates the file chooser object
		JFileChooser fileChooser = new JFileChooser();
		File chosenFile = null;

		int fileValue = fileChooser.showOpenDialog(componentFrame);

		if (fileValue == JFileChooser.APPROVE_OPTION) {
			chosenFile = fileChooser.getSelectedFile();
			return chosenFile;
		}

		return chosenFile;
	}

	// Method to read the file to get the matrix from it
	public Color[][] readFromFile(File file) {
		Color[][] matrix = reader.readFile(file);
		return matrix;
	}

	// Method to make a BufferedImage out of the colour array in the matrix object
	public BufferedImage buildImageFromMatrix(ImageMatrix matrixObject) {
		// Gets the scale from the matrix object
		int scale = matrixObject.getScale();

		// Creates a BufferedImage to draw the new image on
		BufferedImage newImage = new BufferedImage((matrixObject.getColumns() * scale),
				(matrixObject.getRows() * scale), BufferedImage.TYPE_3BYTE_BGR);

		// Gets the matrix from the object
		Color[][] matrix = matrixObject.getMatrix();

		// Creates a graphics object for displaying the new image
		Graphics2D graphicsObject = newImage.createGraphics();

		// Loops through the matrix to set the colour of each pixel of the new image
		for (int i = 0; i < matrixObject.getRows(); i++) {
			for (int ii = 0; ii < matrixObject.getColumns(); ii++) {
				// Initially sets the graphics so that the background doesn't distort
				graphicsObject.setColor(Color.WHITE);

				// Sets the colour of the graphics to the colour at that point in the array
				graphicsObject.setColor(matrix[i][ii]);

				// Creates the integer values for the new coordinates of the colour on the image
				int newX;
				int newY;

				// Scales the image to the correct zoom level
				newX = i * scale;
				newY = ii * scale;

				// Draws the pixel on the image
				graphicsObject.fillRect(newY, newX, scale, scale);
			}
		}

		return newImage;
	}

	// Method for showing an error message if nothing is put in the text area
	public void nullTextErrorMessage() {
		JOptionPane.showMessageDialog(componentFrame, "Please enter a value in the text area", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	// Method for showing an error message if the input can't be converted into
	// integers
	public void invalidTextErrorMessage() {
		JOptionPane.showMessageDialog(componentFrame, "Invalid value entered, please try again", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
}