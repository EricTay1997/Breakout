# Design
## NAME

Eric Tay, netid: et102

##### Names of all people who worked on the project

* Eric Tay

##### Each person's role in developing the project

* This was an individual project, so I was responsible for the whole project. 

##### What are the project's design goals, specifically what kinds of new features did you want to make easy to add

* I wanted to make it easy to add new levels, power ups, bricks, cheat codes, or even dimensions to play (such as)
the paddle being able to move up. This was done in a variety of ways. First, with many methods that handled different
attributes of each node, I feel like it was easy to add different features by calling these methods. Also, for power
ups or brick types, I labelled these constants, which might make it easier to add more of these. Lastly, I did the 
same for levels, and also ensured that levels were created from reading a txt file, which would make it easier to edit
brick configurations in a level or even add/delete levels. 

##### Describe the high-level design of your project, focusing on the purpose and interaction of the core classes

* (Disclaimer - upon preparing the ball class for the code masterpiece, a new power up class was created.)
* The main creates a bouncer with the ball class, a paddle with the paddle class, an empty list of bricks and power ups,
and a level with the level class. User input/cheat keys are processed in the main and would activate different method
calls in each of these classes. The ball class creates the bouncer, and handles its movement on the paddle before 
launch, its launch, its movement after launch, and its interaction with the paddle, bricks and walls after. The paddle
class creates the paddle, and handles its width and movement. The brick class creates different kinds of bricks and is
responsible for the effects of these bricks after contact - breaking, changing image, dropping power ups. The Power Up
class handles the dropping of power ups, and the effects it triggers when caught by the paddle. Finally, the level 
class is responsible for tracking and displaying level progress, changing the level when it is cleared, and therefore
created the bricks and powerups on the new level, while resetting the ball and paddle. 

##### What assumptions or decisions were made to simplify your project's design, especially those that affected adding required features

* I first assumed that I was only be playing with one bouncer, which allowed me to only account for the lives of one
ball. I also decided to make only 6 kinds of bricks and 4 kinds of power ups, all of which had effects that I felt 
were pretty easy to implement. In order to implement the features in the project specifications, I made the following
design decisions. I decided to store level configurations in a text file, that would allow me to add levels and edit
brick configurations easily. This simplified design tremendously as I did not have to hardcode each level. I also 
faced issues standardizing the length of the paddle, or the speed of the ball and opted to reset these at the change
of a level and on death, such that power up effects did not carry on. 

##### Describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline.

* To add a new power up, we would create a new constant in the Power Up class, and edit MAX_POWER_UP to account for 
this. Also edit chooseImage() and initiatePowerUpEffects() to account for this. However, perhaps we can look into 
inheritance to reduce these if-statements.
* Similarly, to add a new brick, add a new constant to the Brick class and edit chooseImageFromType() accordingly.
If-statements and the renaming of constants can be altered with inheritance. Add the new brick to other methods it 
belongs to, e.g. multipleLifeBrick(), and edit level txt files to account for this new brick. 
 * To add a new level, add a new text file named appropriately, but you would need to change the constants associated
 with WIN_SCREEN and LOSE_SCREEN, and modify the cheat keys accordingly. To edit the configuration of a game level, 
 simply edit the associated txt file. 
 * To add a new cheat code, add a new key in the handleKeyInput() method in the Main, and call one of the many methods
 available to edit our Ball/Paddle object.
 * Adding a new bouncer would be challenging because it would be hard to keep track of lives. Perhaps, we trigger death
 not when a ball hits the bottom screen, but when all balls hit the bottom screen. This requires us to keep a count of 
 the number of ball objects created at the moment, perhaps in the Main. Functionality for this does not exist in this 
 project. 