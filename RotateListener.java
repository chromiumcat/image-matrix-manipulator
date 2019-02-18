import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class RotateListener implements ActionListener {

	ImageMatrixGUI guiOperator;
	
	int degrees;
	
	public RotateListener(ImageMatrixGUI guiOperator) {
		super();
		this.guiOperator = guiOperator;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			//Gets the degrees for rotation from the GUI
			degrees = Integer.valueOf(guiOperator.getTextFromGUI());
		} catch(NullPointerException e) {
			guiOperator.nullTextErrorMessage();
		} catch(NumberFormatException e) {
			guiOperator.invalidTextErrorMessage();
		}
		
		// Gets the current matrix object
		ImageMatrix currentMatrix = guiOperator.getCurrentMatrix();
		// Calls the rotate method to rotate the image
		ImageMatrix newMatrix = currentMatrix.rotateImage(degrees);
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
