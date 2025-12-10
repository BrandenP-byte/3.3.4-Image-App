import java.awt.Color;
import java.util.Scanner;

public class ImageApp {

  // ---- MATRIX HELPERS -----------------------------------------
  public static int[] matVec3(double[][] M, int[] v) {
    int[] out = new int[3];
    for (int i = 0; i < 3; i++) {
      double sum = 0;
      for (int j = 0; j < 3; j++) sum += M[i][j] * v[j];
      out[i] = (int)Math.round(sum);
    }
    return out;
  }

  public static int[] matVec4(double[][] M, int[] v) {
    int[] out = new int[4];
    for (int i = 0; i < 4; i++) {
      double sum = 0;
      for (int j = 0; j < 4; j++) sum += M[i][j] * v[j];
      out[i] = (int)Math.round(sum);
    }
    return out;
  }


  // ---- IMAGE OPERATIONS ----------------------------------------

  public static void recolorImage(Picture origImg, String file) {
    System.out.println("Recoloring image...");
    Picture img = new Picture(file);
    Pixel[][] px = img.getPixels2D();

    double[][] RECOLOR = {
      {1.2, 0,   0,   20},
      {0,   0.85,0,   0},
      {0,   0,   0.6, 0},
      {0,   0,   0,   1}
    };

    for (Pixel[] row : px) {
      for (Pixel p : row) {
        int[] v = {p.getRed(), p.getGreen(), p.getBlue(), 1};
        int[] out = matVec4(RECOLOR, v);

        p.setRed(Math.max(0, Math.min(255, out[0])));
        p.setGreen(Math.max(0, Math.min(255, out[1])));
        p.setBlue(Math.max(0, Math.min(255, out[2])));
      }
    }

    img.explore();
  }

  public static void negative(Picture origImg, String file) {
    System.out.println("Creating negative...");
    Picture img = new Picture(file);
    Pixel[][] px = img.getPixels2D();

    double[][] NEG = {
      {-1, 0,  0, 255},
      {0, -1,  0, 255},
      {0,  0, -1, 255},
      {0,  0,  0,   1}
    };

    for (Pixel[] row : px) {
      for (Pixel p : row) {
        int[] v = {p.getRed(), p.getGreen(), p.getBlue(), 1};
        int[] out = matVec4(NEG, v);

        p.setRed(out[0]);
        p.setGreen(out[1]);
        p.setBlue(out[2]);
      }
    }

    img.explore();
  }

  public static void grayscale(Picture origImg, String file) {
    System.out.println("Converting to grayscale...");
    Picture img = new Picture(file);
    Pixel[][] px = img.getPixels2D();

    double[][] GRAY = {
      {1/3.0, 1/3.0, 1/3.0},
      {1/3.0, 1/3.0, 1/3.0},
      {1/3.0, 1/3.0, 1/3.0}
    };

    for (Pixel[] row : px) {
      for (Pixel p : row) {
        int[] rgb = {p.getRed(), p.getGreen(), p.getBlue()};
        int[] out = matVec3(GRAY, rgb);

        p.setRed(out[0]);
        p.setGreen(out[1]);
        p.setBlue(out[2]);
      }
    }

    img.explore();
  }

  public static void rotate180(Picture origImg) {
    System.out.println("Rotating 180°...");
    Picture img = new Picture(origImg);
    Pixel[][] dst = img.getPixels2D();
    Pixel[][] src = origImg.getPixels2D();

    int rows = src.length;
    int cols = src[0].length;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        dst[r][c].setColor(src[rows - 1 - r][cols - 1 - c].getColor());
      }
    }

    img.explore();
  }

  public static void rotate90(Picture origImg) {
    System.out.println("Rotating +90°...");
    int rows = origImg.getPixels2D().length;
    int cols = origImg.getPixels2D()[0].length;

    Picture img = new Picture(cols, rows);
    Pixel[][] dst = img.getPixels2D();
    Pixel[][] src = origImg.getPixels2D();

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        dst[c][rows - 1 - r].setColor(src[r][c].getColor());
      }
    }

    img.explore();
  }

  public static void rotateNeg90(Picture origImg) {
    System.out.println("Rotating -90°...");
    int rows = origImg.getPixels2D().length;
    int cols = origImg.getPixels2D()[0].length;

    Picture img = new Picture(cols, rows);
    Pixel[][] dst = img.getPixels2D();
    Pixel[][] src = origImg.getPixels2D();

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        dst[cols - 1 - c][r].setColor(src[r][c].getColor());
      }
    }

    img.explore();
  }


  // ---- SMALL IMAGE OVERLAY -------------------------------------

  public static void addSmallImage(Picture origImg) {
    System.out.println("Overlaying small image...");

    Picture largeImg = new Picture(origImg);
    Picture smallImg = new Picture("lib2/balloon.png");

    Pixel[][] L = largeImg.getPixels2D();
    Pixel[][] S = smallImg.getPixels2D();

    int startRow = 20;
    int startCol = 30;
    int whiteThreshold = 250;

    for (int r = 0; r < S.length; r++) {
      for (int c = 0; c < S[0].length; c++) {

        int lr = startRow + r;
        int lc = startCol + c;

        if (lr < 0 || lr >= L.length || lc < 0 || lc >= L[0].length)
          continue;

        Color sc = S[r][c].getColor();

        if (sc.getRed() >= whiteThreshold &&
            sc.getGreen() >= whiteThreshold &&
            sc.getBlue() >= whiteThreshold)
          continue;

        L[lr][lc].setColor(sc);
      }
    }

    largeImg.explore();
  }


  // ---- TERMINAL UI ----------------------------------------------

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    String file = "lib/beach.jpg";
    Picture origImg = new Picture(file);

    while (true) {
      System.out.println("\n=== IMAGE MENU ===");
      System.out.println("1. Recolor Image");
      System.out.println("2. Photographic Negative");
      System.out.println("3. Grayscale");
      System.out.println("4. Rotate 180°");
      System.out.println("5. Rotate +90°");
      System.out.println("6. Rotate -90°");
      System.out.println("7. Add Small Image");
      System.out.println("0. Exit");
      System.out.print("Choose: ");

      int choice = sc.nextInt();

      switch (choice) {
        case 1: recolorImage(origImg, file); break;
        case 2: negative(origImg, file); break;
        case 3: grayscale(origImg, file); break;
        case 4: rotate180(origImg); break;
        case 5: rotate90(origImg); break;
        case 6: rotateNeg90(origImg); break;
        case 7: addSmallImage(origImg); break;
        case 0: System.out.println("Goodbye!"); return;
        default: System.out.println("Invalid option.");
      }
    }
  }
}
