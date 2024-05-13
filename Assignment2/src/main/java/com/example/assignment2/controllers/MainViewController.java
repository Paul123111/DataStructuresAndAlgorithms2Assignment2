package com.example.assignment2.controllers;

import com.example.assignment2.Driver;
import com.example.assignment2.utils.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainViewController {

    //-------------------------------------------
    // Image Credits
    //-------------------------------------------
    // none yet

    //-------------------------------------------
    // TODO list - DON'T DELETE COMPLETED TASKS
    //-------------------------------------------

    //custom graph data structure/classes - DONE
    //reduce size of database - DONE
    //points should have two types - landmark and junction, - DONE

    //database will keep edges/links after list of points, with parameters being both points, and its cost (distance) - DONE

    //implement temporary UI for adding points, choosing their type, image, name, and year - DONE
    //implement temporary feature for adding links - DONE

    //add way to generate valid route between two landmarks/points - DONE
    //show route on map - DONE

    //for depth first route permutations, have it generate a fairly large number of routes,
    // and then shuffle and choose first five/however many specified - DONE
    //add tree view with buttons to view route - DONE
    //if there is only 1 or very few possible routes, just show those routes - DONE

    //implement dijkstra's algorithm to find the shortest route - DONE (may need further testing to make sure always finds
    // the shortest route)

    //TODO implement dijkstra's route by culture before slider

    //TODO for dijkstra's cultural/historic route, make it a slider where the user can choose between
    // prioritising distance or culture, eg slider to distance makes it choose shortest path,
    // slider to cultural makes it visit every tourist attraction, in between makes it choose a
    // slight detour from shortest path for extra culture

    //TODO maybe weight culture and distance depending on slider

    //TODO cultural/historic can use only one factor (aka don't need others, can add if I want to), such as
    // the year an attraction was made

    //TODO implement waypoints - a route must go through selected waypoints
    //TODO implement points to avoid - a route must not go through selected points

    //TODO implement breadth-first pixel-by-pixel search, doesn't need to have waypoints/points to avoid etc.

    //TODO tests - probably don't need that many - will probably be writing these as I work on other areas of the assignment
    //TODO benchmark tests - only need 3 or 4 - 2% for setting it up, 2% for making it do something, 1% for doing something useful

    //TODO work on GUI, probably need to use multiple different javaFX components
    //TODO style treeView

    //TODO be able to click on points to select them

    //double demo is possible if done within week 13
    //due 17th may - will be done WAY earlier

    @FXML
    private ImageView mapView;
    @FXML
    private TextField name;
    @FXML
    private TextField year;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private TextField image;
    @FXML
    private Pane imagePane;
    @FXML
    private TextField wantedNode;
    @FXML
    private ChoiceBox<String> start;
    @FXML
    private ChoiceBox<String> destination;
    @FXML
    private ChoiceBox<String> waypointLocations;
    @FXML
    private TextField numRoutes;
    @FXML
    private TreeView<Button> routeTreeView;
    @FXML
    private Slider cultureSlider;
    @FXML
    private Label numberOfWaypoints;
    @FXML
    private ListView<String> waypointsList;
    @FXML
    private TextField maxRoutes;

    private Graph<Node, String> graph;
    private BreadthFirstGraph<Node, String> breadthFirstGraph;
    private int totalCulture = 0;
    private int[] pixels;
    private int pixelStart = 0;
    private int pixelDestination = 0;
    private ArrayList<String> waypoints;
    private ArrayList<String> avoidWaypoints;

    @FXML
    public void initialize() throws FileNotFoundException, URISyntaxException {
        initialImage();
        //addPoints();
        loadCsv();
        editMap();

        populateChoiceBoxes();
        setSliders();
        validateTextFields();

        setPixelPoints();
        waypoints = new ArrayList<>();
        avoidWaypoints = new ArrayList<>();
        //connectAllPoints();
    }

    @FXML
    protected void populateChoiceBoxes() {
        type.getItems().add("Landmark");
        type.getItems().add("Junction");
    }

    @FXML
    protected void setSliders() {
        cultureSlider.setMin(0);
        cultureSlider.setMax(0.9);
    }

    @FXML
    protected void validateTextFields() {
        Utilities.numericText(numRoutes, 5);
    }

    private void initialImage() {
        mapView.setFitWidth(600);
        mapView.setFitHeight(400);
        mapView.setPreserveRatio(false);
        Image image = new Image(Driver.class.getResource("images/ParisLandmarks.png").toString());
        mapView.setImage(image);
        pixels = PixelGraph.getPixels(image);
        PixelGraph.setWidth(600);
    }

    //reads in csv file, then adds nodes to arraylist (add to graph) - DONE
    //junctions 1-9 are from left to right, junction 10 and 11 are between path between library and bastille
    private void loadCsv() throws FileNotFoundException, URISyntaxException {
        List<Node> nodes = new ArrayList<>();

        Scanner sc = new Scanner(new File(Driver.class.getResource("ParisLandmarkDatabase.csv").toURI()));
        sc.useDelimiter(",");
        sc.nextLine();
        String type = sc.next();

        while (sc.hasNext() && !(type.equals("Links"))) {
            //if there were more types of nodes, this could be converted to a switch statement
            if (type.equals("Junction")) {
                nodes.add(new Node("Junction", sc.next(), Utilities.parseInt(sc.next()), Utilities.parseInt(sc.next()),
                        sc.next(), Utilities.parseInt(sc.next()), Utilities.parseInt(sc.next())));
            } else if (type.equals("Landmark")) {
                nodes.add(new Node("Landmark", sc.next(), Utilities.parseInt(sc.next()), Utilities.parseInt(sc.next()), Utilities.parseInt(sc.next()),
                        sc.next(), Utilities.parseInt(sc.next()), Utilities.parseInt(sc.next())));
            }
            sc.nextLine();
            type = sc.next();
        }

        graph = new Graph<>(nodes);
        sc.nextLine();

        //Landmark,Notre Dame,-1,369,233,images/Landmark.png,350,200,
        while (sc.hasNext()) {
            graph.connectNodes(sc.next(), sc.next(), Utilities.parseInt(sc.next()));
            if (sc.hasNextLine())
                sc.nextLine();
        }

//        for (Node node : graph.getNodes()) {
//            System.out.println(node.getName());
//        }
        //System.out.println(AdjacencyMatrix.toString(graph.getAMat(), graph.getNodes().size()));

        sc.close();

        for (Node node : graph.getNodes()) {
            totalCulture += node.getCulture();
            start.getItems().add(node.getName());
            destination.getItems().add(node.getName());
            waypointLocations.getItems().add(node.getName());
        }
    }


    //TODO maybe split into multiple different functions
    private void editMap() {
        WritableImage image = new WritableImage(mapView.getImage().getPixelReader(),
                Utilities.doubleToInt(mapView.getImage().getWidth()), Utilities.doubleToInt(mapView.getImage().getHeight()));

        List<Node> nodes = graph.getNodes();

        //add imageView to add chosen image in imagePane - DONE
        for (Node node : nodes) {
            if (node.getType().equals("Junction")) {
                ImageView landmarkImage = new ImageView(new Image(Driver.class.getResource(node.getImage()).toString()));

                landmarkImage.setFitWidth(6);
                landmarkImage.setFitHeight(6);
                landmarkImage.relocate(node.getImageX() - 3, node.getImageY() - 3);

                imagePane.getChildren().add(landmarkImage);
            } else {
                ImageView landmarkImage = new ImageView(new Image(Driver.class.getResource(node.getImage()).toString()));

                landmarkImage.setFitWidth(16);
                landmarkImage.setFitHeight(20);
                landmarkImage.relocate(node.getImageX() - 8, node.getImageY() - 18);

                imagePane.getChildren().add(landmarkImage);
            }
        }
        mapView.setImage(image);
    }

    //connect points in certain distance - DONE
    private void addPoints() {
        mapView.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            ImageView view = (ImageView) mouseEvent.getSource();
            Bounds bounds = view.getLayoutBounds();
            double xScale = bounds.getWidth() / view.getImage().getWidth();
            double yScale = bounds.getHeight() / view.getImage().getHeight();

            x /= xScale;
            y /= yScale;


            int xCord = (int) x;
            int yCord = (int) y;

            x *= xScale;
            y *= yScale;

            int imageX = (int) x;
            int imageY = (int) y;

            if (mouseEvent.isPrimaryButtonDown()) {
                if (type.getValue().equals("Landmark")) {
                    System.out.println(type.getValue() + "," + name.getText() + "," + year.getText() + "," + xCord + "," + yCord + "," + image.getText()
                            + "," + imageX + "," + imageY + ",");
                } else if (type.getValue().equals("Junction")) {
                    System.out.println(type.getValue() + "," + name.getText() + "," + xCord + "," + yCord + "," + image.getText()
                            + "," + imageX + "," + imageY + ",");
                }

                // testing //-----------------------------------
                ImageView landmarkImage = new ImageView(new Image(Driver.class.getResource("images/Junction.png").toString()));

                landmarkImage.setFitWidth(6);
                landmarkImage.setFitHeight(6);
                landmarkImage.relocate(imageX - 3, imageY - 3);

                imagePane.getChildren().add(landmarkImage);

            }
        });
    }

    private void setPixelPoints() {
        mapView.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            ImageView view = (ImageView) mouseEvent.getSource();
            Bounds bounds = view.getLayoutBounds();
            double xScale = bounds.getWidth() / view.getImage().getWidth();
            double yScale = bounds.getHeight() / view.getImage().getHeight();

            x /= xScale;
            y /= yScale;


            int xCord = (int) x;
            int yCord = (int) y;

            x *= xScale;
            y *= yScale;

            int imageX = (int) x;
            int imageY = (int) y;

            System.out.println(xCord + yCord * 600);
            if (mapView.getImage().getPixelReader().getColor(xCord, yCord).equals(new Color(0, 0, 0, 1))) {
                System.out.println("invalid");
            }

            if (mouseEvent.isPrimaryButtonDown()) {
                pixelStart = xCord + yCord * 600;
            } else if (mouseEvent.isSecondaryButtonDown()) {
                pixelDestination = xCord + yCord * 600;
            }
        });
    }

    @FXML
    protected void connectAllPoints() {
        List<Node> nodes = graph.getNodes();
        int[][] mat = graph.getAMat();

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                if (mat[i][j] != 0)
                    drawLine(nodes.get(i).getImageX(), nodes.get(i).getImageY(),
                            nodes.get(j).getImageX(), nodes.get(j).getImageY());
            }
        }
    }

    @FXML
    protected void connectPoints() {
        System.out.println(graph.displayPossibleLinks(wantedNode.getText()));
    }

    @FXML
    protected void drawLine(int x1, int y1, int x2, int y2) {
        Line line = new Line(x1, y1, x2, y2);
        imagePane.getChildren().add(line);
    }

    @FXML
    protected void drawLine(int x1, int y1, int x2, int y2, Color color) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStyle("-fx-stroke: #" + color.toString().substring(2, 8).toUpperCase() + "; -fx-stroke-width: 2px;");

        imagePane.getChildren().add(line);
    }

    @FXML
    protected void drawPixel(int x, int y) {
        Rectangle rect = new Rectangle(x, y, 1, 1);
        rect.setStroke(Color.BLACK);
        imagePane.getChildren().add(rect);
    }

    @FXML
    protected void drawPixel(int x, int y, Color color) {

    }

    @FXML
    protected void breadthFirstSearchPixelByPixel() {
        int[] path = PixelGraph.breadthFirstSearchWrapper(pixelStart, pixelDestination, pixels);
        Image image = PixelGraph.changePixels(new Image(Driver.class.getResource("images/ParisLandmarks.png").toString()), path);

        for (int i : path) {
            System.out.println(i);
        }

        mapView.setImage(image);
    }

    @FXML
    private void drawRoute(List<Node> route) {
        for (int i = 1; i < route.size(); i++) {
            drawLine(route.get(i - 1).getImageX(), route.get(i - 1).getImageY(),
                    route.get(i).getImageX(), route.get(i).getImageY());
        }
    }

    @FXML
    private void drawRoute(List<Node> route, Color color) {
        for (int i = 1; i < route.size(); i++) {
            drawLine(route.get(i - 1).getImageX(), route.get(i - 1).getImageY(),
                    route.get(i).getImageX(), route.get(i).getImageY(), color);
        }
    }

    private void drawRoutPixels(List<Node> route) {
        //TODO need to create a subrout first

    }


    boolean hasWaypoints(List<Node> rout) {
        for (String waypoint : waypoints) {
            boolean found = false;
            for (Node node : rout) {
                if (node.matchesID(waypoint)) found = true;
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    boolean hasAvoidWaypoints(List<Node> rout) {
        for (String waypoint : avoidWaypoints) {
            for (Node node : rout) {
                if (node.matchesID(waypoint)) return true;
            }
        }
        return false;
    }

    @FXML
    protected void depthFirstRoute() {
        clearLines();
        List<List<Node>> routs = graph.findAllPathsDepthFirstWrapper(start.getValue(), destination.getValue(), Integer.parseInt(maxRoutes.getText()));
        for (List<Node> rout : routs) {
            if (hasWaypoints(rout)) {
                drawRoute(rout);
                break;
            }
        }
    }

    @FXML
    protected void depthFirstRoutes() {
        clearLines();

        List<List<Node>> routes = graph.findAllPathsDepthFirstWrapper(start.getValue(), destination.getValue(), Integer.parseInt(maxRoutes.getText()));
        Collections.shuffle(routes);
//        double hue = 0;

        System.out.println(routes.size());

//        for (int i = 0; i < 5; i++) {
//            drawRoute(routes.get(i), Color.hsb(hue, 1, 1));
//            hue += (360/5);
//        }
        for (int i = routes.size() - 1; i != -1; i--) {
            if (!waypoints.isEmpty() && !hasWaypoints(routes.get(i))) {
                routes.remove(routes.get(i));
                continue;
            }
            if (!avoidWaypoints.isEmpty() && hasAvoidWaypoints(routes.get(i))) {
                routes.remove(routes.get(i));
            }
        }
        addTreeViewRoutes(routes, Utilities.parseInt(numRoutes.getText()));
        if (routes.isEmpty())
            numberOfWaypoints.setText("No routes found with this combination of way points");
        else
            numberOfWaypoints.setText("");
    }

    @FXML
    protected void breadthFirstRout() {

        clearLines();

        //drawRoute(breadthFirstGraph.findPathBreadthFirstWrapper(start.getText(), destination.getText()));
    }


    @FXML
    protected void addTreeViewRoutes(List<List<Node>> routes, int numRoutes) {
        TreeItem<Button> root = new TreeItem<>();
        routeTreeView.setShowRoot(false);
        routeTreeView.setRoot(root);
        double hue = 0;
        double brightness = 1;
        int size = routes.size();

        for (int i = 0; i < numRoutes; i++) {
            if (i >= size) break;

            Button button = new Button("Show Route #" + (i + 1));
            //make final fields to use for lambda expression
            final double lambdaHue = hue;
            final int lambdaI = i;

            if (hue % 360 == 144) brightness = 0.75;
            else brightness = 1;
            final double lambdaBrightness = brightness;

            button.setOnAction(event -> {
                clearLines();
                drawRoute(routes.get(lambdaI), Color.hsb(lambdaHue, 1, lambdaBrightness));
            });

            root.getChildren().add(new TreeItem<>(button));
            hue += (360 / 5);
        }
    }


    @FXML
    protected void shortestPath() {
        clearLines();

        //Disconnect the nodes temporarily
        int[][] toReconnect = new int[graph.getAMat().length][graph.getAMat().length];
        for (String waypoint : avoidWaypoints) {
            for (int i = 0; i < toReconnect.length; i++) {
                int waypointIndex = graph.findIndexByField(waypoint);
                if (AdjacencyMatrix.isConnected(graph.getAMat(), waypointIndex, i)) {
                    toReconnect[waypointIndex][i] = graph.getAMat()[waypointIndex][i];
                    AdjacencyMatrix.connectNodesUndirected(graph.getAMat(), waypointIndex, i, 0);
                }
            }
        }


        //CostedPath<Node> costedPath = new CostedPath<>();//graph.findCheapestPathDijkstraWrapper(start.getValue(), destination.getValue(), cultureSlider.getValue());
        CostedPath<Node> costedPath = insertWayPoints();
        System.out.println(costedPath.getCost());
        System.out.println(costedPath.getCulture());

        drawRoute(costedPath.getCheapestPath());

        //Reconnect them afterward
        for (String waypoint : avoidWaypoints) {
            for (int i = 0; i < toReconnect.length; i++) {
                int waypointIndex = graph.findIndexByField(waypoint);
                AdjacencyMatrix.connectNodesUndirected(graph.getAMat(), waypointIndex, i, toReconnect[waypointIndex][i]);
            }
        }
    }

    private CostedPath<Node> insertWayPoints(){
        ArrayList<Node> waypoints2 = new ArrayList<>();
        for (String waypoint: waypoints){

            waypoints2.add(graph.getNodes().get(graph.findIndexByField(waypoint)));

            //ArrayList<CostedPath<Node>> paths = new ArrayList<>();
//            for(Node node: path){
//                paths.add(graph.findCheapestPathDijkstraWrapper(node.getName(), waypoint, cultureSlider.getValue()));
//            }
//            int lowsetCost = 0;
//            for (int i = 0;i<paths.size();i++){
//               if(paths.get(i).getCost()<paths.get(lowsetCost).getCost()){
//                   lowsetCost = i;
//               }
//            }
//
//            path.addAll(lowsetCost, paths.get(lowsetCost).getCheapestPath());

        }

        return graph.findCheapestPathDijkstraWrapper(start.getValue(), destination.getValue(), waypoints2, cultureSlider.getValue());
    }



    boolean waypointExists(){
        return waypoints.contains(waypointLocations.getValue()) || avoidWaypoints.contains(waypointLocations.getValue());
    }
    @FXML
    protected void setWaypoint(){
        if(waypointExists()){return;}
        waypoints.add(waypointLocations.getValue());
        waypointsList.getItems().add(waypointLocations.getValue() + " (go trough)");
    }
    @FXML
    protected void setAvoidWaypoint(){
        if(waypointExists()){return;}
        avoidWaypoints.add(waypointLocations.getValue());
        waypointsList.getItems().add(waypointLocations.getValue() + " (avoid)");
    }

    @FXML
    protected void clearWaypoints(){
        waypoints.clear();
        avoidWaypoints.clear();
        waypointsList.getItems().clear();

    }

//    @FXML
//    protected void costCulturePath() {
//        CostedPath<Node> costedPath = graph.findCostCulturePathDijkstraWrapper(start.getValue(), destination.getValue(),
//                cultureSlider.getValue(), totalCulture);
//        System.out.println(costedPath.getCost());
//        clearLines();
//        drawRoute(costedPath.getCheapestPath());
//    }

    private void clearLines() {
        imagePane.getChildren().removeIf((e) -> (e instanceof Line));
    }
}