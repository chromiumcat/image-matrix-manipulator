import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ResetFileListener implements ActionListener {

	// Attributes
	ImageMatrixGUI guiOperator;

	//Constructor
	public ResetFileListener(ImageMatrixGUI guiOperator) {
		super();
		this.guiOperator = guiOperator;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Gets the current file from the GUI
		File originalFile = guiOperator.getCurrentFile();
		//Gets the image matrix from the file
		ImageMatrix originalMatrix = new ImageMatrix(guiOperator.readFromFile(originalFile));
		// Sets the current matrix to the one just created
		guiOperator.setCurrentMatrix(originalMatrix);
		// Gets the displayable image from the matrix object
		BufferedImage originalImage = guiOperator.buildImageFromMatrix(originalMatrix);
		// Changes the image displayed on the GUI
		guiOperator.changeImage(originalImage);
		//Resets the zoom on the GUI
		guiOperator.setSliderValue(100);
	}

}
