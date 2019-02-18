import java.awt.Color;

public class ImageMatrix {

	// Attribute
	private Color[][] matrix;
	private int rows;
	private int columns;
	private int scale = 1;

	// Constructor
	public ImageMatrix(Color[][] matrix) {
		this.matrix = matrix;
	}

	// Getter for the matrix
	public Color[][] getMatrix() {
		return matrix;
	}

	// Getter for the rows
	public int getRows() {
		rows = matrix.length;
		return rows;
	}

	// Getter for the columns
	public int getColumns() {
		columns = matrix[0].length;
		return columns;
	}

	// Getter for the scale
	public int getScale() {
		return scale;
	}

	//Getter for the percentage zoom it has
	public int getPercentageZoom() {
		int percent = scale * 100;
		return percent;
	}

	// Setter for the scale (zooming)
	public void setScale(int percent) {
		scale = percent / 100;
	}

	//Method which rotates the image
	public ImageMatrix rotateImage(int degrees) {
		// Find the coordinates of the centre of the image
		double centreX = rows / 2;
		double centreY = columns / 2;
		
		//Declares the new coordinate values
		double newX;
		double newY;

		// Converts the degrees into radians
		double radians = degrees * (Math.PI / 180);

		// Creates the new colour array
		Color[][] newMatrix = new Color[rows][columns];

		// Loops through the original image
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				//Uses the radians to calculate the new coordinates of a pixel with trigonometry
				newX = ((row - centreX) * Math.cos(radians)) - ((column - centreY) * Math.sin(radians)) + centreX;
				newY = ((row - centreX) * Math.sin(radians)) + ((column - centreY) * Math.cos(radians)) + centreY;
				//Ignores all pixels outside of the image boundaries
				if (Math.round(newX) < rows && Math.round(newX) >= 0 && 
						Math.round(newY) < columns && Math.round(newY) >= 0) {
					//Sets the new coordinate to the colour of the pixel in the original image
					newMatrix[(int)Math.round(newX)][(int)Math.round(newY)] = matrix[row][column];
				}
			}
		}

		//Makes and returns a new matrix object with the new matrix
		ImageMatrix newImage = new ImageMatrix(newMatrix);
		return newImage;
	}

	// Method for shearing an image along the x-axis
	public ImageMatrix shearImageOnX(int degrees) {
		// Find the relevant coordinates of the centre of the image
		double centreY = columns / 2;
		double newX;

		// Converts the degrees into radians
		double radians = degrees * (Math.PI / 180);

		// Creates the new colour array
		Color[][] newMatrix = new Color[rows][columns];
		
		// Loops through the original image
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				//Uses trig again but in a different way to shear the image horizontally
				newX = row - (column - centreY) * Math.tan(radians / 2);
				
				//Ignores the out of bonds coordinates
				if (Math.round(newX) < rows && Math.round(newX) >= 0) {
					//Sets the new coords with their relevant colour
					newMatrix[(int) Math.round(newX)][column] = matrix[row][column];
				}
			}
		}
		
		//Makes and returns a new matrix object with the new matrix
		ImageMatrix newImage = new ImageMatrix(newMatrix);
		return newImage;
	}
	
	// Method for shearing an image along the x-axis
		public ImageMatrix shearImageOnY(int degrees) {
			// Find the coordinates of the centre of the image
			double centreX = rows / 2;
			double newY;

			// Converts the degrees into radians
			double radians = degrees * (Math.PI / 180);

			// Creates the new colour array
			Color[][] newMatrix = new Color[rows][columns];
			
			// Loops through the original image
			for (int row = 0; row < rows; row++) {
				for (int column = 0; column < columns; column++) {
					//Does the maths again but for vertical shearing
					newY = (row - centreX) * Math.sin(radians) + column;
					
					//Ignores the out of bounds pixels
					if (Math.round(newY) < columns && Math.round(newY) >= 0) {
						//Sets the new coordinates with their colour
						newMatrix[row][(int) Math.round(newY)] = matrix[row][column];
					}
				}
			}
			
			//Makes and returns a new matrix object with the new matrix
			ImageMatrix newImage = new ImageMatrix(newMatrix);
			return newImage;
		}

}
