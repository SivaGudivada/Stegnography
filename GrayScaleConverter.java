/**
 * Convert any number of images to a gray scale version by setting all color components of each pixel to the same value.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import java.io.*;

public class GrayScaleConverter {

	private ImageResource makeGray(ImageResource inImage) 
	{
		
		ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
		
		for (Pixel pixel: outImage.pixels()) 
		{
		
			Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());

			int average = (inPixel.getRed() + inPixel.getBlue() + inPixel.getGreen())/3;

			pixel.setRed(average);

			pixel.setGreen(average);

			pixel.setBlue(average);
		}

		return outImage;
	}

	public void selectAndConvert () 
	{
		DirectoryResource dr = new DirectoryResource();
		for (File f : dr.selectedFiles()) {
			ImageResource inImage = new ImageResource(f);
			ImageResource gray = makeGray(inImage);
			gray.draw();
		}
	}

	private void testGray() {
		ImageResource ir = new ImageResource();
		ImageResource gray = makeGray(ir);
		gray.draw();
	}
}
