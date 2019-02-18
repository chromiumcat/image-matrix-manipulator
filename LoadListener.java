import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class LoadListener implements ActionListener {

	//Makes an object to interact with the GUI
	ImageMatrixGUI guiOperator;
	
	//Constructor
	public LoadListener(ImageMatrixGUI guiOperator) {
		super();
		this.guiOperator = guiOperator;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			//Chooses a file
			File chosenFile = guiOperator.chooseFile();
			//Sets the GUI's current file
			guiOperator.setCurrentFile(chosenFile);
			//Makes a matrix object from that file
			ImageMatrix matrix = new ImageMatrix(guiOperator.readFromFile(chosenFile));
			//Sets the current matrix to the one just created
			guiOperator.setCurrentMatrix(matrix);
			//Gets the displayable image from the matrix object
			BufferedImage importedImage = guiOperator.buildImageFromMatrix(matrix);
			//Changes the image displayed on the GUI
			guiOperator.changeImage(importedImage);
			//Resets the zoom
			guiOperator.setSliderValue(100);
		} catch (NullPointerException e) {
			//Do nothing, the user might have clicked the button by accident
		}
	}

}
