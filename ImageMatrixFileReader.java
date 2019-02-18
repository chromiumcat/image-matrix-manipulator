import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ImageMatrixFileReader {
		
	private Color[][] readFile (FileReader fr) {
		BufferedReader bfr = null;
		// try to read from the specified file and store (lines of text)
		// with new-line at end) in list and convert list to a MatrixImage for return
		try {
			bfr = new BufferedReader(fr);
			ArrayList<ArrayList<Color>> image = new ArrayList<>();
			//convert each line to an array of colours
			String line = null;
			int cols = 0, count_cols = 0, rows = 0; 
			while ((line = bfr.readLine()) != null) {
				//if the line is blank ignore it
				String clean_line = line.replaceAll("[{}]", "");
				if ( clean_line.trim().equals("") ) { continue; }

				//process the colours in this line
				String[] input_colors = clean_line.split("[,]");
				ArrayList<Color> line_colors = new ArrayList<>();
				
				for (int i = 0; i < input_colors.length; i++) {
					try {
						if ( !input_colors[i].trim().equals("") ) {
							line_colors.add(new Color(Integer.parseInt(input_colors[i].trim(),2)));
						}
					}
					catch (NumberFormatException nfe) {
						try {
							if ( !input_colors[i].trim().equals("") ) {
								line_colors.add(new Color(Integer.parseInt(input_colors[i].trim())));
							}
						}
						catch (NumberFormatException nfe2) {
							System.out.print("Could not generate image from file");
							System.out.println(": Line: "+ line +"; Column: "+ i +"; Value: '"+input_colors[i]+"'");
							return null;
						}
					}
				}
				image.add(line_colors);
				//keep track of how many rows and columns the image has
				rows++;
				//the number of columns in the first row is the number of columns we expect to find in all others
				if ( rows == 1 ) {
					cols = count_cols;
				}
				//make sure we the same number of columns in each subsequent row
				else if ( rows > 1 && cols != count_cols ) {
					System.out.print("Could not generate image from file");
					System.out.println(": Expected " + cols + " columns but found " + count_cols + " on line " + rows);
					return null;
				}
				//reset the count of columns for each row
				else {
					count_cols = 0;
				}
			}
			//convert the arraylists to arrays
			Color[][] image_matrix = new Color[rows][cols];
			for (int y = 0; y < rows; y++) {
				image_matrix[y] = new Color[cols];
				image_matrix[y] = image.get(y).toArray(image_matrix[y]);
			}
			return image_matrix;
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("I/O error: " + e.getMessage());
			return null;
		}
		finally {
			try { bfr.close(); } catch (Exception e) {}
		}
	}
	
	public Color[][] readFile (String filename) {
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
			return readFile(fr);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
			return null;
		}
		finally {
			try { fr.close(); } catch (Exception e) { }
		}
	}
	
	public Color[][] readFile (File file) {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			return readFile(fr);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
			return null;
		}
		finally {
			try { fr.close(); } catch (Exception e) { }
		}
	}
}
