import java.awt.image.BufferedImage;
import java.io.File;

public class Assignment {

	public static void main(String[] args) {

		//Makes an object to interact with the GUI class
		ImageMatrixGUI guiOperator = new ImageMatrixGUI();
				
		//Enacts the method to create and display the GUI
		guiOperator.buildGUI();
		
		File chosenFile = guiOperator.chooseFile(); //Gets the user to choose a file
		guiOperator.setCurrentFile(chosenFile);
		
		try {
			//Import an initial file
			ImageMatrix matrix = new ImageMatrix(guiOperator.readFromFile(chosenFile)); //Creates a matrix object from a file
			guiOperator.setCurrentMatrix(matrix); //Sets the matrix object just created to the GUI's current matrix
			BufferedImage importedImage = guiOperator.buildImageFromMatrix(matrix); //Creates the displayable image
			guiOperator.changeImage(importedImage); //Changes the image on the label
		} catch (NullPointerException e) {
			//Do nothing, just continue as normal, the user might not want to open a file just now
		}
		
	}

}