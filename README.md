<p align="center">
    <img src="https://user-images.githubusercontent.com/93549743/154691156-e434da42-c318-4b8b-a14e-daa0ff3ffb98.png" alt="Application Icon" width=256 height=256/>
</p>


# TSP-Visualisation
Node based travelling salesman problem visualisation in Java. Generates a nearest neighbour path then optimises with iterative 2-opt swaps.

## Usage
Download the [TravellingSalesman.jar](https://github.com/freddycansic/TSP-Visualisation/releases/download/v1.0.0/TravellingSalesman.jar) file on the newest release.

Execute the jar file:
    
    java -jar /path/to/TravellingSalesman.jar
If Java 8 is not installed, install it using your local package manager, e.g:

    sudo apt install openjdk-8-jre
If you are running a version of Java later than 8 then you must downgrade your current installation:

    sudo update-alternatives --config java
Then select Java 8:
 
    There are 2 choices for the alternative java (providing /usr/bin/java).
    
      Selection    Path                                            Priority   Status
    ------------------------------------------------------------
      0            /usr/lib/jvm/java-13-openjdk-amd64/bin/java      1311      auto mode
    * 1            /usr/lib/jvm/java-13-openjdk-amd64/bin/java      1311      manual mode
      2            /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java   1081      manual mode
    
    Press <enter> to keep the current choice[*], or type selection number: 2

Done!

## How it works
- Get user input numNodes, proximity (Example 10, 2)
##### Nearest neighbour algorithm
- Populate a list with 10 randomly placed nodes
- For every node 
    - find the distance to every other node
    - Randomly select from the 2 nearest other nodes and add it to a base route
##### 2-Opt swap
- Find 2 edges
- Swap them
- If the resulting route is shorter than the previous route
    - Keep the swap
- Otherwise find the next 2 edges
- Repeat until every edge has been checked with every other edge without any swaps occurring
- A local minumum has now been found and the program will stop
