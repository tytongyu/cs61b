import java.text.DecimalFormat;
import java.util.*;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private String[][] render_grid;
    private double raster_ul_lon;
    private double raster_ul_lat;
    private double raster_lr_lon;
    private double raster_lr_lat;
    int depth;
    boolean query_success;

    public Rasterer() {
        String[][] render_grid = null;
        raster_ul_lon = -1;
        raster_ul_lat = -1;;
        raster_lr_lon = -1;;
        raster_lr_lat = -1;;
        depth = -1;;
        query_success = false;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();

        double cliLonDPP = (params.get("lrlon") - params.get("ullon")) / params.get("w");
        depth = levDepth(cliLonDPP);

        /**get render_grid*/
        List<int[]> crossNum = crossNum(params.get("ullon"), params.get("lrlon"), params.get("ullat"), params.get("lrlat"), depth);
        int ulX = crossNum.get(0)[0];
        int ulY = crossNum.get(0)[1];
        int lrX = crossNum.get(crossNum.size() - 1)[0];
        int lrY = crossNum.get(crossNum.size() - 1)[1];
        int curX, curY;
        render_grid = new String[lrY - ulY + 1][lrX - ulX + 1];
        for (int i = 0; i < lrY - ulY + 1; i ++) {
            for (int j = 0; j < lrX - ulX + 1; j ++) {
                curX = ulX + j;
                curY = ulY + i;
                render_grid[i][j] = "d" + depth + "_x" + curX + "_y" + curY + ".png";
            }
        }

        /**get raster pos*/
        double lonDis = (122.2998046875 - 122.2119140625) / Math.pow(2, depth);
        double latDis = (37.892195547244356 - 37.82280243352756) / Math.pow(2, depth);
        raster_ul_lon = -122.2998046875 + ulX * lonDis;
        raster_ul_lat = 37.892195547244356 - ulY * latDis;
        raster_lr_lon = -122.2998046875 + (lrX + 1) * lonDis;
        raster_lr_lat = 37.892195547244356 - (lrY + 1) * latDis;

        query_success = query_success(params.get("ullon"), params.get("lrlon"), params.get("ullat"), params.get("lrlat"));

        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", query_success);

        return results;
    }

    private int levDepth(double cliLonDPP) {
        int a = 0;
        double curLonDPP;
        while (a < 8) {
            curLonDPP = (122.2998046875 - 122.2119140625) / (256 * Math.pow(2, a) );
            if (curLonDPP < cliLonDPP) {
                return a;
            }
            a++;
        }
        return 7;
    }

    private boolean query_success(double ullon, double lrlon, double ullat, double lrlat) {
        if (ullon >= lrlon || ullat <= lrlat) {
            return false;
        }
        if (!isCross(ullon, lrlon, ullat, lrlat, -122.2998046875, -122.2119140625, 37.892195547244356, 37.82280243352756)) {
            return false;
        }
        return true;
    }

    private boolean isCross(double ullon1, double lrlon1, double ullat1, double lrlat1,
                            double ullon2, double lrlon2, double ullat2, double lrlat2) {
        boolean a = lrlon1 - ullon1 + lrlon2 - ullon2 > Math.max(Math.abs(lrlon1 - ullon2), Math.abs(lrlon2 - ullon1));
        boolean b = ullat1 - lrlat1 + ullat2 - lrlat2 > Math.max(Math.abs(ullat1 - lrlat2), Math.abs(ullat2 - lrlat1));
        return a && b;
    }

    private List<int[]> crossNum(double ullon, double lrlon, double ullat, double lrlat, int depth) {
        List<int[]> crossNum = new ArrayList<>();
        double ullon1, lrlon1, ullat1, lrlat1;
        double lonDis = (122.2998046875 - 122.2119140625) / Math.pow(2, depth);
        double latDis = (37.892195547244356 - 37.82280243352756) / Math.pow(2, depth);
        for (int i = 0; i <= Math.pow(2, depth) - 1; i++) {
            for (int j = 0; j <= Math.pow(4, depth) - 1; j++) {
                ullon1 = -122.2998046875 + i * lonDis;
                lrlon1 = ullon1 + lonDis;
                ullat1 = 37.892195547244356 - j * latDis;
                lrlat1 = ullat1 - latDis;
                if (isCross(ullon1, lrlon1, ullat1, lrlat1,
                ullon, lrlon, ullat, lrlat)) {
                    crossNum.add(new int[]{i, j});
                }
            }
        }
        return crossNum;
    }

    public static void main(String[] args) {
        Rasterer rasterer = new Rasterer();
        HashMap<String, Double> params = new HashMap<>();
        params.put("ullon", -122.3027284165759);
        params.put("ullat", 37.88708748276975);
        params.put("lrlon", -122.20908713544797);
        params.put("lrlat", 37.848731523430196);
        params.put("w", 305.0);
        params.put("h", 300.0);
        System.out.println(rasterer.mapToString(rasterer.getMapRaster(params)));

    }

    private static DecimalFormat df2 = new DecimalFormat(".#########");
    private String mapToString(Map<String, ?> m) {
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        List<String> keys = new ArrayList<>();
        keys.addAll(m.keySet());
        Collections.sort(keys);
        for (String k : keys) {
            StringBuilder sb = new StringBuilder();
            sb.append(k);
            sb.append("=");
            Object v = m.get(k);
            if (v instanceof String[][]) {
                sb.append(Arrays.deepToString((String[][]) v));
            } else if (v instanceof Double) {
                sb.append(df2.format(v));
            } else {
                sb.append(v.toString());
            }
            String thisEntry = sb.toString();

            sj.add(thisEntry);
        }
        return sj.toString();
    }

}
