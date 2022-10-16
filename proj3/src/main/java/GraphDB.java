import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

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
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    Map<Long, Node> nodeSet = new HashMap<Long, Node>();
    Map<Long, Edge> edgeSet = new HashMap<Long, Edge>();
    Map<Long, NameNode> nameNodes = new LinkedHashMap<>();
    Map<String, List<Long>> locations = new LinkedHashMap<>();
    trie cleanNames = new trie();

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
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

    public Node getNode(Long nodeID) {
        for (GraphDB.Node x : nodeSet.values()) {
            if (x.nodeID.equals(nodeID)) {
                return x;
            }
        }
        return null;
    }

    public Edge getEdge(Long edgeID) {
        for (GraphDB.Edge x : edgeSet.values()) {
            if (x.edgeID.equals(edgeID)) {
                return x;
            }
        }
        return null;
    }

    public void addNode(Node a) {
        nodeSet.put(a.nodeID, a);
    }

    public void addLocation(String a, Long b) {
        if (locations.containsKey(a)) {
            locations.get(a).add(b);
        } else {
            List<Long> c = new ArrayList<>();
            c.add(b);
            locations.put(a, c);
        }
    }

    public void addCleanName(String a) {
        String cleanA = cleanString(a);
        cleanNames.insert(a);
    }

    public void delNode(Node a) {
        nodeSet.remove(a.nodeID);
    }

    public void connectNode(Long a, Long b) {
        for (GraphDB.Node x : nodeSet.values()) {
            if (x.nodeID.equals(a)) {
                x.adjacent.add(getNode(b));
                return;
            }
        }
    }

    public void addEdge(Edge a) {
        edgeSet.put(a.edgeID, a);
    }

    public Edge delEdge(Long edgeID) {
        if (!edgeSet.containsKey(edgeID)) {
            return null;
        }
        return edgeSet.remove(edgeID);
    }

    static class NameNode {
        long id;
        double lon;
        double lat;
        String name;

        public NameNode(long id, double lon, double lat, String name) {
            this.id = id;
            this.lon = lon;
            this.lat = lat;
            this.name = name;
        }
    }

    public void addNameNode(NameNode n) {
        nameNodes.put(n.id, n);
    }

    public static class Node implements Comparable {
        Long nodeID;
        Stack<Node> adjacent;
        double lat;
        double lon;
        double priority;
        double distTo;
        Node prve;
        Set<Long> edgeID;


        public Node(Long nodeID, double lat, double lon) {
            this.nodeID = nodeID;
            this.lat = lat;
            this.lon = lon;
            this.adjacent =  new Stack<>();
            this.edgeID = new HashSet<>();
        }

        @Override
        public int compareTo (Object o) {
            Node a = (Node) o;
            if (this.priority - a.priority > 0) {
                return 1;
            } else if (this.priority - a.priority < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static class Edge {
        Long edgeID;
        String maxSpeed;
        String name;
        String highway;

        public Edge(Long edgeID) {
            this.edgeID = edgeID;
        }
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
        /*for (Node a : nodeSet.values()) {
            if (a.adjacent.isEmpty()) {
                nodeSet.remove(a.nodeID);
            }
        }*/

        Iterator<Long> it = nodeSet.keySet().iterator();
        while (it.hasNext()) {
            Long node = it.next();
            if (nodeSet.get(node).adjacent.isEmpty()) {
                it.remove();
            }
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return nodeSet.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        ArrayList<Long> res = new ArrayList<>();
        if (nodeSet.get(v).adjacent.isEmpty()) {
            return res;
        }
        for (Node a : nodeSet.get(v).adjacent) {
            res.add(a.nodeID);
        }
        return res;
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
        Node res = null;
        for (Node value : nodeSet.values()) {
            res = value;
            break;
        }
        for (Node a : this.nodeSet.values()) {
            if (distance(a.lon, a.lat, lon, lat) < distance(res.lon, res.lat, lon, lat)) {
                res = a;
            }
        }
        return res.nodeID;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        if (!this.nodeSet.containsKey(v)) {
            return 0;
        }
        return this.nodeSet.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        if (!this.nodeSet.containsKey(v)) {
            return 0;
        }
        return this.nodeSet.get(v).lat;
    }
}
