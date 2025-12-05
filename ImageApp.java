/*
  ImageApp: 
    Pseudocode for image manipulation
    1. Recoloring an image by changing the RGB color of each Pixel
    2. Creating a photographic negative of the image  
    3. Creating a grayscale version of the image
    4. Rotating an image 180 degrees
 */
import java.awt.Color;

public class ImageApp
{
  public static void main(String[] args)
  {

    // use any file from the lib folder
    String pictureFile = "lib/beach.jpg";
    String pictureFile2 = "lib/arch.jpg";

    // Get an image, get 2d array of pixels, show a color of a pixel, and display the image
    Picture origImg = new Picture(pictureFile);
    Pixel[][] origPixels = origImg.getPixels2D();
    System.out.println(origPixels[0][0].getColor());
    origImg.explore();

    // Image #1 Using the original image and pixels, recolor an image by changing the RGB color of each Pixel
    Picture recoloredImg = new Picture(pictureFile);
    Pixel[][] recoloredPixels = recoloredImg.getPixels2D();

    /* to be implemented */
    for (int row = 0; row < recoloredPixels.length; row++) {
      for (int col = 0; col < recoloredPixels[0].length; col++) {
        Pixel p = recoloredPixels[row][col];
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();
        int newR = Math.min(255, (int)(r * 1.2) + 20);
        int newG = Math.max(0, (int)(g * 0.85));
        int newB = Math.max(0, (int)(b * 0.6));
        p.setRed(newR);
        p.setGreen(newG);
        p.setBlue(newB);
      }
    }
    
    

    // Image #2 Using the original image and pixels, create a photographic negative of the image
    Picture negImg = new Picture(pictureFile);
    Pixel[][] negPixels = negImg.getPixels2D();

    /* to be implemented */
    for (int row = 0; row < recoloredPixels.length; row++) {
      for (int col = 0; col < recoloredPixels[0].length; col++) {
        Pixel p = recoloredPixels[row][col];
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();
        int newR = Math.min(0, (int)(r -255));
        int newG = Math.max(0, (int)(g -255));
        int newB = Math.max(0, (int)(b -255));
        p.setRed(newR);
        p.setGreen(newG);
        p.setBlue(newB);
      }
    }


    // Image #3 Using the original image and pixels, create a grayscale version of the image
    Picture grayscaleImg = new Picture(pictureFile);
    Pixel[][] grayscalePixels = grayscaleImg.getPixels2D();

    /* to be implemented */ 
    for (int row = 0; row < grayscalePixels.length; row++) {
      for (int col = 0; col < grayscalePixels[0].length; col++) {
        Pixel p = grayscalePixels[row][col];
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();
        int avg = (r + g + b) / 3;
        p.setRed(avg);
        p.setGreen(avg);
        p.setBlue(avg);
      }
    }



    // Image #4 Using the original image and pixels, rotate it 180 degrees
    Picture upsidedownImage = new Picture(pictureFile);
    Pixel[][] upsideDownPixels = upsidedownImage.getPixels2D();

    /* to be implemented */
    // rotate 180: pixel at (r,c) <- source at (rows-1-r, cols-1-c)
    for (int row = 0; row < upsideDownPixels.length; row++) {
      for (int col = 0; col < upsideDownPixels[0].length; col++) {
        Pixel target = upsideDownPixels[row][col];
        Pixel source = origPixels[upsideDownPixels.length - 1 - row][upsideDownPixels[0].length - 1 - col];
        target.setRed(source.getRed());
        target.setGreen(source.getGreen());
        target.setBlue(source.getBlue());
      }
    }



    // Image #5 Using the original image and pixels, rotate image 90
    Picture rotateImg = new Picture(pictureFile);
    Pixel[][] rotatePixels = rotateImg.getPixels2D();

    /* to be implemented */
    // rotate 90 degrees clockwise: target(r,c) <- source(origRows-1-c, r)
    int origRows = origPixels.length;
    int origCols = origPixels[0].length;
    for (int row = 0; row < rotatePixels.length; row++) {
      for (int col = 0; col < rotatePixels[0].length; col++) {
        int srcRow = origRows - 1 - col;
        int srcCol = row;
        // clamp to valid indices to avoid out-of-bounds for non-square images
        srcRow = Math.max(0, Math.min(origRows - 1, srcRow));
        srcCol = Math.max(0, Math.min(origCols - 1, srcCol));
        Pixel target = rotatePixels[row][col];
        Pixel source = origPixels[srcRow][srcCol];
        target.setRed(source.getRed());
        target.setGreen(source.getGreen());
        target.setBlue(source.getBlue());
      }
    }

    // Image #6 Using the original image and pixels, rotate image -90
    Picture rotateImg2 = new Picture(pictureFile);
    Pixel[][] rotatePixels2 = rotateImg2.getPixels2D();
  
    /* to be implemented */
    // rotate -90 degrees (90° counterclockwise): target(r,c) <- source(c, origCols-1-r)
    for (int row = 0; row < rotatePixels2.length; row++) {
      for (int col = 0; col < rotatePixels2[0].length; col++) {
        int srcRow = col;
        int srcCol = origCols - 1 - row;
        // clamp to valid indices to avoid out-of-bounds for non-square images
        srcRow = Math.max(0, Math.min(origRows - 1, srcRow));
        srcCol = Math.max(0, Math.min(origCols - 1, srcCol));
        Pixel target = rotatePixels2[row][col];
        Pixel source = origPixels[srcRow][srcCol];
        target.setRed(source.getRed());
        target.setGreen(source.getGreen());
        target.setBlue(source.getBlue());
      }
    }
    rotateImg2.explore();


    // Final Image: Add a small image to a larger one

    /* to be implemented */
    Picture largeImg = new Picture(pictureFile);
    Picture smallImg = new Picture("lib/arch.jpg"); // change filename if needed
    Pixel[][] largePixels = largeImg.getPixels2D();
    Pixel[][] smallPixels = smallImg.getPixels2D();

    int startRow = 20; // vertical offset in the large image
    int startCol = 30; // horizontal offset in the large image

    for (int r = 0; r < smallPixels.length; r++) {
      for (int c = 0; c < smallPixels[0].length; c++) {
        int lr = startRow + r;
        int lc = startCol + c;
        // ensure we don't write outside the bounds of the large image
        if (lr >= 0 && lr < largePixels.length && lc >= 0 && lc < largePixels[0].length) {
          largePixels[lr][lc].setColor(smallPixels[r][c].getColor());
        }
      }
    }
    largeImg.explore();




    // for testing  2D algorithms
    int[][] test1 = { { 1, 2, 3, 4 },
        { 5, 6, 7, 8 },
        { 9, 10, 11, 12 },
        { 13, 14, 15, 16 } };
    int[][] test2 = new int[4][4];


  }
}
