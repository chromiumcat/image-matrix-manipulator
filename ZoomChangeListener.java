import java.awt.image.BufferedImage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ZoomChangeListener implements ChangeListener {

	//Attributes
	ImageMatrixGUI guiOperator;
	
	//Constructor
	public ZoomChangeListener(ImageMatrixGUI guiOperator) {
		super();
		this.guiOperator = guiOperator;
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		//Gets the matrix currently on the GUI
		ImageMatrix currentMatrix = guiOperator.getCurrentMatrix();
		//Sets the scale so that the zoom level is correct
		currentMatrix.setScale(guiOperator.getSliderValue());
		//Makes the zoomed in/out image
		BufferedImage zoomedImage = guiOperator.buildImageFromMatrix(currentMatrix);
		//Displays the new image on the GUI
		guiOperator.changeImage(zoomedImage);
	}

}
