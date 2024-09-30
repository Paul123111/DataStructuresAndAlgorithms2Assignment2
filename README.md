# Paris Route Finder
This program's purpose is to find routes between common tourist destinations in Paris. <br> <br> 
The user can configure their own points or use the ones included with the project, <br> 
and then remove the top two lines of the UI once they have configured the map. <br> <br> 
Remove the debug UI by commenting out lines 52-71 and 82-93 in 'main-view.fxml' and line 134 in MainViewController.

# Options for Adding Points to Map
To add points to the map, go to the initialize() method in MainViewController and uncomment addPoints(). <br> <br> 
Run the program and use left click and copy-paste the console output into the <br> ParisLandmarkDatabase.csv file (on the line above 'Links,') to place a node on the map after configuring the following options. <br> <br> 
To add edges, enter the node name and press 'show all possible edges for node', and copy-paste the desired edges from the console output into the ParisLandmarkDatabase.csv file (at the bottom of the file).

- Name - Name of the new node on the map. <br>
- Year - Year the node on the map was constructed <br>
- Type - The node's type - a tourist landmark or a junction. <br> 
- Image Path - The image to represent the node on the map, images/junction.png and images/landmark.png are used for junctions and landmarks, respectively. <br>
---------------------------------------------------------------------------------------------------------------------------------------------------------------
- Node name - The node to show all possible edges for. <br> 
- Show All Possible Edges for Node - Press this to print out every possible connection this node could have with other nodes. <br> 
- Connect All Edges - View all connections between nodes on the map. <br> 

# Options for Users
- Show Route (Depth-First) - Finds a route from the starting route to the destination route (including waypoints) using depth-first search. <br>
- Show All Routes (Depth-First) - Find every route from the starting route to the destination route (including waypoints) using depth-first search, depending on number of routes. <br>
---------------------------------------------------------------------------------------------------------------------------------------------------------------
- Get Shortest Path (Dijkstra's Algorithm) - Finds the shortest path between the starting node and the ending node (accounting for waypoints) with Dijkstra's algorithm. <br>
----------------------------------------------------------------------------------------------------------------------------------------------------------------
- Set Waypoint - Forces routes to visit a specific node on the map. <br>
- Avoid Waypoint - Forces routes to avoid a specific node on the map. <br>
- Clear Waypoints - Removes all waypoints. You can see current waypoints in the list beneath the waypoint buttons. <br>

# Fields for Users
- Start - Choose the starting node. <br>
- Destination - Choose the node to end the route on. <br>
----------------------------------------------------------------------------------------------------------------------------------------------------------------
- Shortest -> Historical Slider - Makes Dijkstra's algorithm priortise visiting old landmarks (earliest year) <br> over shorter distance paths, depending on the position of the slider. <br> The closer to shortest path the slider is, the more the algorithm will prioritise shortest distance, and vice versa.
----------------------------------------------------------------------------------------------------------------------------------------------------------------
- Waypoint - The node used by the waypoints options. There can be multiple waypoints used by routes. <br>
- Number of Routes - 'Show All Routes' uses this field to decide the number of possible routes to show. If total number of routes between two nodes < number of routes, then show every possible route. <br>
- Treeview of Routes - 'Show All Routes' displays the routes it found in this treeview. Click on one of the routes to display it on the map.
