class Solution {
    public static int[] countRectangles(int[][] rectangles, int[][] points) {
        int[] result = new int[points.length];
        for (int i=0;i<points.length;i++)
        {
            result[i] = 0;
            for (int j=0;j<rectangles.length;j++){
                if (rectangles[j][0]>=points[i][0] && rectangles[j][1]>=points[i][1]){
                    result[i]++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] rectangles = {{1,1},{2,2},{3,3}};
        int[][] points = {{1,3},{1,1}};
        int [] result = countRectangles(rectangles , points);
        for (int i=0;i<points.length;i++)
        {
            System.out.print(result[i]);
        }
    }
}