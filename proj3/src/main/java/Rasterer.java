import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double[] longDPPList;
    private double[] latDPPList;

    private void calculateLongDPPlist() {
        longDPPList = new double[8];
        latDPPList = new double[8];
        double distanceLong = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        double distanceLat = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        for (int i = 0; i < 8; i++) {
            longDPPList[i] = distanceLong / (Math.pow(2, i) * 256);
            latDPPList[i] = distanceLat / (Math.pow(2, i) * 256);
        }
    }
    private boolean checkSuccess(double ullong, double lrlong, double ullat, double lrlat) {
        return !(ullong >= MapServer.ROOT_LRLON) && !(lrlong <= MapServer.ROOT_ULLON)
                && !(ullat <= MapServer.ROOT_LRLAT) && !(lrlat >= MapServer.ROOT_ULLAT);
    }
    private int resolutionSolver(double threshold) {
        for (int i = 0; i < longDPPList.length; i++) {
            if (threshold >= longDPPList[i]) {
                return i;
            }
        }
        return 7;
    }
    private int[] gridParamProvider(double ullong, double lrlong, double ullat, double lrlat,
                                    int depth) {
        double stepLong = longDPPList[depth] * 256;
        double stepLat = latDPPList[depth] * 256;
        int[] param = new int[4];
        param[0] = (int) Math.floor(-(MapServer.ROOT_ULLON - ullong) / stepLong);
        param[1] = (int) Math.ceil(-(MapServer.ROOT_ULLON - lrlong) / stepLong);
        param[2] = (int) Math.floor((MapServer.ROOT_ULLAT - ullat) / stepLat);
        param[3] = (int) Math.ceil((MapServer.ROOT_ULLAT - lrlat) / stepLat);
        return param;
    }
    private String[][] synthesisGrid(int lonb, int lone, int latb, int late, int depth) {
        String[][] result = new String[late - latb][lone - lonb];
        for (int row = latb; row < late; row++) {
            for (int col = lonb; col < lone; col++) {
                result[row - latb][col - lonb] = String.format("d%d_x%d_y%d.png", depth, col, row);
            }
        }
        return result;
    }
    private Map<String, Double> calculateUL(int x, int y, int depth) {
        double uLLon = MapServer.ROOT_ULLON + x * longDPPList[depth] * 256;
        double uLLat = MapServer.ROOT_ULLAT - y * latDPPList[depth] * 256;
        Map<String, Double> result = new HashMap<>();
        result.put("Lon", uLLon);
        result.put("Lat", uLLat);
        return result;
    }
    private Map<String, Double> calculateLR(int x, int y, int depth) {
        double lRLon = MapServer.ROOT_ULLON + (x + 1) * longDPPList[depth] * 256;
        double lRLat = MapServer.ROOT_ULLAT - (y + 1) * latDPPList[depth] * 256;
        Map<String, Double> result = new HashMap<>();
        result.put("Lon", lRLon);
        result.put("Lat", lRLat);
        return result;
    }

    public Rasterer() {
        calculateLongDPPlist();
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
        Map<String, Object> results = new HashMap<>();
        int depth = resolutionSolver(-(params.get("ullon") - params.get("lrlon"))
                / params.get("w"));
        boolean querySuccess = checkSuccess(params.get("ullon"), params.get("lrlon"),
                params.get("ullat"), params.get("lrlat"));
        int[] p = gridParamProvider(params.get("ullon"), params.get("lrlon"),
                params.get("ullat"), params.get("lrlat"), depth);
        String[][] renderGrid = synthesisGrid(p[0], p[1], p[2], p[3], depth);
        Map<String, Double> rasterUl = calculateUL(p[0], p[2], depth);
        Map<String, Double> rasterLr = calculateLR(p[1] - 1, p[3] - 1, depth);
        results.put("query_success", querySuccess);
        results.put("depth", depth);
        results.put("raster_ul_lon", rasterUl.get("Lon"));
        results.put("raster_ul_lat", rasterUl.get("Lat"));
        results.put("raster_lr_lon", rasterLr.get("Lon"));
        results.put("raster_lr_lat", rasterLr.get("Lat"));
        results.put("render_grid", renderGrid);
        return results;
    }
}
