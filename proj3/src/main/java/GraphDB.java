import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    private  Map<Long, Node> nodeList;
    private Map<Long, Way> wayList;

    public class Node {
        long id;
        double lat;
        double lon;
        int version;
        ArrayList<Long> neighbors;
        Map<String, String> tag;
        Node(long pid, double plat, double plon, int pversion) {
            id = pid;
            lat = plat;
            lon = plon;
            version = pversion;
            tag = new HashMap<>();
            neighbors = new ArrayList<>();
        }
        public void addNeighbors(long pid) {
            neighbors.add(pid);
        }
        public void addTag(String k, String v) {
            tag.put(k, v);
        }
        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            }
            if (o == this) {
                return true;
            }
            Node no = (Node) o;
            return no.id == this.id;
        }
        @Override
        public int hashCode() {
            String code = String.valueOf(lat) + String.valueOf(lon);
            return code.hashCode();
        }
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            tmp.append("Node id: ");
            tmp.append(id);
            tmp.append("\n");
            tmp.append("lat: ");
            tmp.append(lat);
            tmp.append("\n");
            tmp.append("lon: ");
            tmp.append(lon);
            tmp.append("\n");
            return tmp.toString();
        }
    }
    public class Way {
        long id;
        int version;
        ArrayList<Long> way;
        Map<String, String> tag;
        public void addNeighbors(Node n, long stop) {
            n.addNeighbors(stop);
        }
        public void constructConnectivity() {
            // construct connection between every node in this Way object
            for (int i = 0; i < way.size(); i++) {
                if (i != 0) {
                    addNeighbors(nodeList.get(way.get(i)), way.get(i - 1));
                }
                if (i != way.size() - 1) {
                    addNeighbors(nodeList.get(way.get(i)), way.get(i + 1));
                }
            }
        }
        Way(long pid, int pversion) {
            id = pid;
            version = pversion;
            way = new ArrayList<>();
            tag = new HashMap<String, String>();
        }
        public void addStops(long pid) {
            way.add(pid);
        }
        public void addTag(String k, String v) {
            tag.put(k, v);
        }
        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            }
            if (this == o) {
                return true;
            }
            Way eo = (Way) o;
            return eo.id == this.id;
        }
        @Override
        public int hashCode() {
            return String.valueOf(id).hashCode();
        }
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            tmp.append("way id: ");
            tmp.append(id);
            tmp.append("\n");
            for (long i : way) {
                tmp.append(i);
                tmp.append(" -> ");
            }
            tmp.append("End");
            return tmp.toString();
        }
    }

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        nodeList = new HashMap<>();
        wayList = new HashMap<>();
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }
    public Node getNode(long pid) {
        return nodeList.get(pid);
    }
    public void addNode(long pid, double plat, double plon, int pversion) {
        nodeList.put(pid, new Node(pid, plat, plon, pversion));
    }
    public Node updateNode(long pid) {
        return nodeList.get(pid);
    }
    public void addWay(long pid, int pversion) {
        wayList.put(pid, new Way(pid, pversion));
    }
    public Way updateWay(long wid) {
        return wayList.get(wid);
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        ArrayList<Long> removeList = new ArrayList<>();
        for (Node i: nodeList.values()) {
            if (i.neighbors.size() == 0) {
                removeList.add(i.id);
            }
        }
        for (long l: removeList) {
            nodeList.remove(l);
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return nodeList.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return nodeList.get(v).neighbors;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        long id = -1;
        double minDistance = Double.MAX_VALUE;
        for (Node i: nodeList.values()) {
            if (distance(lon, lat, i.lon, i.lat) < minDistance) {
                id = i.id;
                minDistance = distance(lon, lat, i.lon, i.lat);
            }
        }
        return id;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodeList.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodeList.get(v).lat;
    }
}
