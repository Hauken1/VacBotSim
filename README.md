# VacBotSim
A vacuum cleaner robot simulation

This is a vacuum cleaner agent set in a configurable simulation environment and is made from scratch using Java. External libraries and dependencies were not used. This simulation is not supposed to be clever or implement any impactful AI concepts, as the goal was to make a simple reflex agent and acquire a larger understanding of how the agent would solve the problems it was presented. With various options included as to explore and clearly see the limits of the design. 

While creating the environment for this simulation the main focus was to create an GUI where the user could change the variables described in the assignment (size, shape, dust placement and some more). This made it simple to change test configurations, giving a better insight in how well it performs, and giving an idea of what it is able to accomplish during simulation with the step limitation in mind.

The last part the environment does is to create the agent. The agent is given a random starting location on the grid (the array list) and starting direction for where it should start moving. The environment will then create a thread which starts the agent’s movement. 
Based on the direction it was given it will first check what type of tile is in that direction:
-	Dust tile: Move to the tile and continue in that direction. 
-	Regular tile: Move to the tile and then chose a random direction. What direction it will chose is dependent on a certain percentage. 
-	Object tile: “Crashes” and tries to move in another random direction, except for the one it just did. If it crashes again it will move   in another random direction except for the last two it did. If the remember object placement checkbox is checked the vacuum cleaner     will remember this location and therefore won’t crash into this object again.

Additional agent related GUI components and functionality:

•	Dust respawning checkbox
Enabling this will make movement have a fixed chance to respawn dust. 

•	Strict movement checkbox
Enabling this will remove the random element from the movement and make it more predictable. Nevertheless, because of the nature of this kind of movement and how it is programmed, it is unstable and will most likely end with the robot being stuck in a corner. 

•	Remember object placement checkbox
Enabling this will make the agent remember objects is has crashed into. This greatly reduces the amount of crashes the agent will have during a simulation. However, this functionality is not making the agent have a better understanding of its environment, as the robot still need to check every tile it encounters.

This agent is to be considered very simple and based on its decision making, un-clever. It is a reflex agent with some random agent elements. Instead of just picking left or right in a very strict manner it will do so randomly, except for when the user checks the strict movement checkbox in the program. 

For more information about classes and methods of the agent or environment, check out the Java Documentation or the code itself.
