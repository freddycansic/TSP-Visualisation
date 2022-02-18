# TSP-Visualisation
Node based travelling salesman problem visualisation in Java. Generates a nearest neighbour path then optimises with iterative 2-opt swaps.

## Usage
Download the [TravellingSalesman.jar](https://github.com/freddycansic/TSP-Visualisation/releases/download/v1.0.0/TravellingSalesman.jar) file on the newest release.

Make sure Java 8 is installed on your machine:

    java -version
If not, install java using your local package manager, e.g:

    sudo apt install openjdk-8-jre
If you are running a version of Java later than 8 then you must downgrade your current installation

    sudo update-alternatives --config java
Then select Java 8
 
    There are 2 choices for the alternative java (providing /usr/bin/java).
    
      Selection    Path                                            Priority   Status
    ------------------------------------------------------------
      0            /usr/lib/jvm/java-13-openjdk-amd64/bin/java      1311      auto mode
    * 1            /usr/lib/jvm/java-13-openjdk-amd64/bin/java      1311      manual mode
      2            /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java   1081      manual mode
    
    Press <enter> to keep the current choice[*], or type selection number: 2

