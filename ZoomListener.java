import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ZoomListener implements ActionListener {

	//Attributes
	ImageMatrixGUI guiOperator;
	int percent;
	
	//Constructor
	public ZoomListener(ImageMatrixGUI guiOperator, int percent) {
		this.guiOperator = guiOperator;
		this.percent = percent;
	}
	
	@Override //Method which will run when the 
	public void actionPerformed(ActionEvent arg0) {
		//Gets the matrix currently on the GUI
		ImageMatrix currentMatrix = guiOperator.getCurrentMatrix();
		//Sets the scale so that the zoom level is correct
		currentMatrix.setScale(percent);
		//Makes the zoomed in/out image
		BufferedImage zoomedImage = guiOperator.buildImageFromMatrix(currentMatrix);
		//Displays the new image on the GUI
		guiOperator.changeImage(zoomedImage);
		//Sets the zoom slider value so it is synced with current image zoom
		guiOperator.setSliderValue(percent);
	}

}
