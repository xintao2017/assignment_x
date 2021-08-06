## Assignment

After reading through the assignment, I draw the structure of the "House" on paper, it clearly showing a Tree structure
to me (with 'Estate' as the root). And what I need to do is to find a shortest PATH of 2 Rooms/Locations in the Tree structure.

As for solution, I was thinking about Breadth First Search (BFS) or I can save the path to the Root for each Location.
For BFS it requires to iterate the Tree structure to find the Path. If I save every Location's path to Root, I can manage
to find the Shortest/Correct path by calculating of the paths. The Two solutions are either use computing resources
or consuming Memory resources.

In the implementation, I did save the Path To Root while reading the Config File, as the Questions states, it would be
a normal size House.


### Bonus #1
There will be multiple ways to get to some of the rooms, so the Solution above might get into circles. Instead, I used
Depth First Search (DFS).

### Bonus #2
"walking up the staircase was more strenuous than walking down the same?"
I plan to give each path a difficulty points, and just pick the path with lowest points.
Dijkstra would be implemented to solve this case.
But being lazy, as it is stated only Stairs that was "more strenuous".

It should be possible to only check on "Stairs" if it is in the resulting PATH.



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

The Config files are located in **assignment_x/config_file/**, feel free to add more rooms/locations as you need.

