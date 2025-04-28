package src.mchat.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static int[][] convertImageForThinning(BufferedImage ziToTrace) {
		int w = ziToTrace.getWidth();
		int h = ziToTrace.getHeight();
		int blankValue = ziToTrace.getRGB(0, 0);
		
		int[][] image = new int[h][w];
		for (int r=0; r<h; r++) {
			for (int c=0; c<w; c++) {
				image[r][c] = (ziToTrace.getRGB(c, r) == blankValue)?0:1; 
				// -1 or Color(255,255,255,255) is a dot; else is blank
			}
		}
		
		return image;
	}
	
	public static void printTraceLine(int[][] traceImage) {
		for (int r=0; r<traceImage.length; r++) {
			for (int c=0; c<traceImage[0].length; c++) {
				//color = new Color(traceImage[r][c]);
				if (traceImage[r][c] == 1) {
					System.out.print('.');
				}
				else {
					System.out.print(' ');
				}
			}
			System.out.println();		
		}
	}

	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), 
	    		img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

	public static Image clipFromImage(Image srcImage, Point ref, Dimension dim) {
	    BufferedImage bimage = new BufferedImage(dim.width, dim.height,
	    		BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(srcImage, 0, 0, dim.width, dim.height, 
	    		ref.x, ref.y, dim.width, dim.height, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

	public static Image clipFromImage(Image srcImage, Point ref, Dimension dim, int scale) {
	    BufferedImage bimage = new BufferedImage(dim.width/scale, dim.height/scale,
	    		BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(srcImage, 0, 0, dim.width/scale, dim.height/scale, 
	    		ref.x, ref.y, ref.x+dim.width, ref.y+dim.height, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	public static void saveImageInFile(Image image, File file) {
		/*
	    JOptionPane.showConfirmDialog(null, "Test", 
	    		"Save Image in a file...", 0, 0, 
	    		new ImageIcon(image));
	    System.out.println(image.getWidth(null) + "x"
	    		+ image.getHeight(null));
	    */
	    try {
	    	/*
		    System.out.println(toBufferedImage(image).getWidth() + "x"
		    		+ toBufferedImage(image).getHeight());
		    */
			ImageIO.write(toBufferedImage(image), "png", file);
			//System.out.println("Saved in file " + file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}

	public static BufferedImage resizeImage(File chosenFile, int i, int j) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(chosenFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Dimension dim = new Dimension(i, j);
		return (BufferedImage)clipFromImage(img, new Point(0, 0), dim);
	}

}
