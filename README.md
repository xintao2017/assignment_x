## Assignment

After reading through the assignment, I draw the structure of the "House" on paper, it clearly showing a Tree structure
to me (with 'Estate' as the root). And what I need to do is to find a shortest PATH of 2 Rooms/Locations in the Tree structure.

As for solution, I was thinking about Breadth First Search (BFS) or I can save the path to the Root for each Location.
For BFS it requires to iterate the Tree structure, and return the first found PATH, which will be the one we wanted. 
Or I can save every Location's path to the Root, then I can manage to find the Shortest/Correct path by calculating of the paths (Path to Root). 

The two solutions are either use more computing resources or consuming more Memory resources.

In the "assignment" implementation, I did save the Path To Root while reading the Config File, as the Questions states, it would be
a normal size House. Under "alternative1" folder, it is implemented with BFS.


### Bonus #1
There will be multiple ways to get to some of the rooms, so the Solution above might get into circles. Instead, I used
Depth First Search (DFS).

### Bonus #2
"walking up the staircase was more strenuous than walking down the same?"
I plan to give each path a difficulty points, and just pick the path with lowest points.
Dijkstra would be implemented to solve this case.
But being lazy, as it is stated only Stairs that was "more strenuous".

It should be possible to only check on "Stairs" if it is in the resulting PATH.

* * Due to lack of time, did not have much time to do re-factory /write tests . :( * *



## How to Run

There are three different programmes implemented for each question, so there will be three Main classes.

To run the programme, first compile the code:
```
javac assignment/GetPath.java
```
Then run the programme as:
```
java assignment/GetPath bedroom keys
```

For bonus question #1 (#2):
```
javac assignment/bonus1/GetPathToObj.java
```
Then run the programme as:
```
java assignment/bonus1/GetPathToObj bedroom keys
```

The Config files are located in **assignment_x/config_file/**, feel free to add more rooms/locations as you need.

