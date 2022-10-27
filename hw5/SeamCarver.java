import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private edu.princeton.cs.algs4.Picture picture;
    public int width;
    public int height;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
    }

    public Picture picture() {
        return this.picture;
    }

    public int width() {
        return this.width;
    }
    public int height() {
        return this.width;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >=width || y < 0 || y >=height) {
            throw new IndexOutOfBoundsException();
        }
        double dx21 = Math.pow(picture.get((x - 1 + width) % width, y).getRed() - picture.get((x + 1) % width, y).getRed(), 2);
        double dx22 = Math.pow(picture.get((x - 1 + width) % width, y).getGreen() - picture.get((x + 1) % width, y).getGreen(), 2);
        double dx23 = Math.pow(picture.get((x - 1 + width) % width, y).getBlue() - picture.get((x + 1) % width, y).getBlue(), 2);
        double dx2 = dx21 + dx22 + dx23;
        double dy21 = Math.pow(picture.get(x, (y - 1 + height) % height).getRed() - picture.get(x, (y + 1) % height).getRed(), 2);
        double dy22 = Math.pow(picture.get(x, (y - 1 + height) % height).getGreen() - picture.get(x, (y + 1) % height).getGreen(), 2);
        double dy23 = Math.pow(picture.get(x, (y - 1 + height) % height).getBlue() - picture.get(x, (y + 1) % height).getBlue(), 2);
        double dy2 = dy21 + dy22 + dy23;
        return dx2 + dy2;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Picture rotate = new Picture(picture.height(), picture.width());
        for (int i = 0; i < rotate.width(); i++) {
            for (int j = 0; j < rotate.height(); j++) {
                rotate.set(i, j, picture.get(j, i));
            }
        }
        SeamCarver a = new SeamCarver(rotate);
        return a.findVerticalSeam();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int [][] paths = new int[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int a = (int) energy(i, j);
                if (j == 0) {
                    paths[i][j] = a;
                } else {
                    paths[i][j] = paths[i][j - 1] + a;
                    if (i - 1 >= 0) {
                        paths[i][j] = Math.min(paths[i - 1][j - 1] + a, paths[i][j]);
                    }
                    if (i + 1 < width) {
                        paths[i][j] = Math.min(paths[i + 1][j - 1] + a, paths[i][j]);
                    }
                }
            }
        }
        int bottomx = 0;
        for (int i = 0; i < width; i++) {
            if (paths[i][height - 1] < paths[bottomx][height - 1]) {
                bottomx = i;
            }
        }
        int shortestPath = paths[bottomx][height - 1];
        int[] res = new int[height];
        res[height - 1] =bottomx;
        for (int i = height -2; i > -1; i--) {
            shortestPath -= energy(bottomx, i + 1);
            if (bottomx - 1 >= 0) {
                if (paths[bottomx - 1][i] == shortestPath) {
                    bottomx--;
                }
            }
            if (bottomx + 1 < width) {
                if (paths[bottomx + 1][i] == shortestPath) {
                    bottomx++;
                }
            }
            res[i] =bottomx;
        }
        return res;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(this.picture, seam);
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(this.picture, seam);
    }
}
