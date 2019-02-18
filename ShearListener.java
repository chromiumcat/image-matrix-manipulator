import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ShearListener implements ActionListener {

	// Attributes
	ImageMatrixGUI guiOperator;
	int degrees;
	boolean isX;

	// Constructor
	public ShearListener(ImageMatrixGUI guiOperator, boolean isX) {
		super();
		this.guiOperator = guiOperator;
		this.isX = isX;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			// Gets the degrees for shear from the GUI
			degrees = Integer.valueOf(guiOperator.getTextFromGUI());
		} catch (NullPointerException e) {
			//Gives an error message if nothing is put in the box
			guiOperator.nullTextErrorMessage();
		} catch (NumberFormatException e) {
			//Gives an error message if the contents of the text field are invalid
			guiOperator.invalidTextErrorMessage();
		}

		// Gets the current matrix object
		ImageMatrix currentMatrix = guiOperator.getCurrentMatrix();

		if (isX) {
			// Calls the rotate method to shear the image
			ImageMatrix newMatrix = currentMatrix.shearImageOnX(degrees);
			//Sets the scale so that the zoom level is correct
			newMatrix.setScale(currentMatrix.getPercentageZoom());
			// Sets the current matrix
			guiOperator.setCurrentMatrix(newMatrix);
			// Gets the displayable image from the matrix
			BufferedImage newImage = guiOperator.buildImageFromMatrix(newMatrix);
			// Changes the icon on the label
			guiOperator.changeImage(newImage);
		} else {
			// Calls the rotate method to shear the image
			ImageMatrix newMatrix = currentMatrix.shearImageOnY(degrees);
			//Sets the scale so that the zoom level is correct
			newMatrix.setScale(currentMatrix.getPercentageZoom());
			// Sets the current matrix
			guiOperator.setCurrentMatrix(newMatrix);
			// Gets the displayable image from the matrix
			BufferedImage newImage = guiOperator.buildImageFromMatrix(newMatrix);
			// Changes the icon on the label
			guiOperator.changeImage(newImage);
		}
	}

}
