/*
  ImageApp: 
    Pseudocode for image manipulation
    1. Recoloring an image by changing the RGB color of each Pixel
    2. Creating a photographic negative of the image  
    3. Creating a grayscale version of the image
    4. Rotating an image 180 degrees
 */
import java.awt.Color;

public class ImageApp {
    // multiply 3x3 matrix by 3x1 vector (int result)

    public static int[] matVec3(double[][] M, int[] v) {
        int[] out = new int[3];
        for (int i = 0; i < 3; i++) {
            double sum = 0;
            for (int j = 0; j < 3; j++) {
                sum += M[i][j] * v[j];
            }
            out[i] = (int) Math.round(sum);
        }
        return out;
    }

    // multiply 4x4 matrix by 4x1 vector (int result)
    public static int[] matVec4(double[][] M, int[] v) {
        int[] out = new int[4];
        for (int i = 0; i < 4; i++) {
            double sum = 0;
            for (int j = 0; j < 4; j++) {
                sum += M[i][j] * v[j];
            }
            out[i] = (int) Math.round(sum);
        }
        return out;
    }

    public static void main(String[] args) {

        // use any file from the lib folder
        String pictureFile = "lib/beach.jpg";

        // Get the original image
        Picture origImg = new Picture(pictureFile);
        Pixel[][] origPixels = origImg.getPixels2D();
        System.out.println(origPixels[0][0].getColor());
        origImg.explore();

        // Image #1 Using the original image and pixels, recolor an image by changing the RGB color of each Pixel
        Picture recoloredImg = new Picture(pictureFile);
        Pixel[][] recoloredPixels = recoloredImg.getPixels2D();

        // changing the values of the colors a littlbe bit
        double[][] RECOLOR = {
            {1.2, 0, 0, 20}, // newG = some redness tint
            {0, 0.85, 0, 0}, // newR = reduced green
            {0, 0, 0.6, 0}, // newB = lower blue tone 
            {0, 0, 0, 1}
        };

        for (Pixel[] row : recoloredPixels) {
            for (Pixel p : row) {
                int[] vec = {p.getRed(), p.getGreen(), p.getBlue(), 1};
                int[] out = matVec4(RECOLOR, vec);

                p.setRed(Math.min(255, out[0]));
                p.setGreen(Math.max(0, out[1]));
                p.setBlue(Math.max(0, out[2]));
            }
        }
        recoloredImg.explore();

        // Image #2 Using the original image and pixels, create a photographic negative of the image
        Picture negImg = new Picture(pictureFile);
        Pixel[][] negPixels = negImg.getPixels2D();

        // negative values for each color
        double[][] NEG = {
            {-1, 0, 0, 255}, // completely red 
            {0, -1, 0, 255}, // complete green aswell
            {0, 0, -1, 255}, // very bklue aswell
            {0, 0, 0, 1}
        };

        for (Pixel[] row : negPixels) {
            for (Pixel p : row) {
                int[] vec = {p.getRed(), p.getGreen(), p.getBlue(), 1};
                int[] out = matVec4(NEG, vec);

                p.setRed(out[0]);
                p.setGreen(out[1]);
                p.setBlue(out[2]);
            }
        }
        negImg.explore();

        // Image #3 Using the original image and pixels, create a grayscale version of the image
        Picture grayscaleImg = new Picture(pictureFile);
        Pixel[][] grayscalePixels = grayscaleImg.getPixels2D();

        // makes all the colors completely gray 
        double[][] GRAY = {
            {1 / 3.0, 1 / 3.0, 1 / 3.0},
            {1 / 3.0, 1 / 3.0, 1 / 3.0},
            {1 / 3.0, 1 / 3.0, 1 / 3.0}
        };

        for (Pixel[] row : grayscalePixels) {
            for (Pixel p : row) {
                int[] rgb = {p.getRed(), p.getGreen(), p.getBlue()};
                int[] out = matVec3(GRAY, rgb);

                p.setRed(out[0]);
                p.setGreen(out[1]);
                p.setBlue(out[2]);
            }
        }
        grayscaleImg.explore();

        // Image #4 Using the original image and pixels, rotate it 180 degrees
        Picture upsidedownImage = new Picture(pictureFile);
        Pixel[][] upsideDownPixels = upsidedownImage.getPixels2D();

        int rows = upsideDownPixels.length;
        int cols = upsideDownPixels[0].length;

        // rotates the columns 180 degrees
        double[][] R180 = {
            {-1, 0, rows - 1},
            {0, -1, cols - 1},
            {0, 0, 1}
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                int[] pos = {r, c, 1};
                int[] xy = {
                    (int) (R180[0][0] * pos[0] + R180[0][1] * pos[1] + R180[0][2]),
                    (int) (R180[1][0] * pos[0] + R180[1][1] * pos[1] + R180[1][2])
                };

                Pixel source = origPixels[xy[0]][xy[1]];
                Pixel target = upsideDownPixels[r][c];
                target.setColor(source.getColor());
            }
        }
        upsidedownImage.explore();

        // Image #5 Using the original image and pixels, rotate image 90
        Picture rotateImg = new Picture(pictureFile);
        Pixel[][] rotatePixels = rotateImg.getPixels2D();

        int origRows = origPixels.length;
        int origCols = origPixels[0].length;

// rotates the image values by 90 degree
        double[][] R90 = {
            {0, 1, 0},
            {-1, 0, origRows - 1},
            {0, 0, 1}
        };

        for (int r = 0; r < rotatePixels.length; r++) {
            for (int c = 0; c < rotatePixels[0].length; c++) {

                // treat (c,r) as (x,y):
                int[] pos = {c, r, 1};

                int x = (int) (R90[0][0] * pos[0] + R90[0][1] * pos[1] + R90[0][2]);
                int y = (int) (R90[1][0] * pos[0] + R90[1][1] * pos[1] + R90[1][2]);

                // copy pixel safely
                Pixel target = rotatePixels[r][c];
                Pixel source = origPixels[y][x];
                target.setColor(source.getColor());
            }
        }

        rotateImg.explore();

// Image #6 Using the original image and pixels, rotate image -90
        Picture rotateImg2 = new Picture(origCols, origRows); // swapped dimensions
        Pixel[][] rotatePixels2 = rotateImg2.getPixels2D();

// Matrix for -90° CCW rotation on (x, y, 1)
        double[][] Rn90 = {
            {0, -1, origCols - 1},
            {1, 0, 0},
            {0, 0, 1}
        };

        for (int r = 0; r < rotatePixels2.length; r++) {
            for (int c = 0; c < rotatePixels2[0].length; c++) {

                int[] pos = {c, r, 1};
                int[] out = matVec3(Rn90, pos);

                int x = out[0];
                int y = out[1];

                Pixel target = rotatePixels2[r][c];
                Pixel source = origPixels[y][x];
                target.setColor(source.getColor());
            }
        }

        rotateImg2.explore();

        // Final Image: Add a small image to a larger one
        Picture largeImg = new Picture(pictureFile);
        Picture smallImg = new Picture("lib2/balloon.png"); // may fail if file missing
        Pixel[][] largePixels = largeImg.getPixels2D();
        Pixel[][] smallPixels = smallImg.getPixels2D();

        int startRow = 20;
        int startCol = 30;

        int whiteThreshold = 250;

        for (int r = 0; r < smallPixels.length; r++) { // iterates through every pixel in smallPixels 2D array
            for (int c = 0; c < smallPixels[0].length; c++) {

                // coressponding position on the image
                int lr = startRow + r;
                int lc = startCol + c;
                // make sure that they do not go outside the boundaries
                if (lr >= 0 && lr < largePixels.length
                        && lc >= 0 && lc < largePixels[0].length) {

                    // get color of the current small pixel
                    Color sc = smallPixels[r][c].getColor();

                    // skip any white pixels to treat white as transparent
                    if (sc.getRed() >= whiteThreshold
                            && sc.getGreen() >= whiteThreshold
                            && sc.getBlue() >= whiteThreshold) {
                        continue;
                    }

                    largePixels[lr][lc].setColor(sc);
                }
            }
        }

        // test arrays
        int[][] test1 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };
        int[][] test2 = new int[4][4];

    }
}
