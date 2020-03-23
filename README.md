game
====

This project implements the game of Breakout.

Name: Eric Tay, netid: et102

### Timeline

Start Date: 1/12/19

Finish Date: 1/19/19

Hours Spent: 40

### Resources Used

I used the recommended javafx tutorial, https://www.tutorialspoint.com/javafx/.

I also watched a tutorial on javafx here: https://www.youtube.com/watch?v=Ugj-zEHOsj8, to understand the flow of the 
framework. 

Furthermore, I looked up code on reading files with a scanner at 
https://www.geeksforgeeks.org/different-ways-reading-text-file-java/

Lastly, I used help from the lab code provided, ExampleBounce.java to get my main started.

### Running the Program

Main class: Main.java

Data files needed: All files can be found in the resources folder. In particular, this game uses brick3.gif, 
brick6.gif, brick8.gif, brick9.gif, brick11.gif and brick12.gif amongst the brick gifs. It uses all other files
in the folder excluding pong_beep.wav. 

Key/Mouse inputs:  Press ENTER on the Splash Screen, Win Screen, or Lose Screen to play the game. 

Move the paddle (and the bouncer at the start) using arrow keys. Note that the paddle will not move below the 
screen, nor above a set height. 

Press SPACEBAR to release the bouncer and begin. 

Press I to go back to the Splash Screen to review instructions.

Cheat keys:

'L': add additional lives to the player  
'R': resets the ball and paddle to their starting position  
'N': next - passes current  
'B': back - goes to previous level  
'F': makes the ball faster  
'S': makes the ball slower  
'E': elongates paddle  
'I': return to SPLASH SCREEN  
'1-3': when the player presses a numeric key, clear the current level and jump to the level corresponding to the 
number pressed (or the highest one that exists)  

All cheat keys only work on game levels (1-3). This was designed such that there will not be bugs from going to a 
level that is not constructed.

Known Bugs: The algorithm for bouncing the bouncer is flawed. Due to the step function, and the implementation of 
allowing the bouncer to bounce off the side of bricks, the bouncer gets stuck within a block at times, especially 
when it hits the corner of a brick. This is especially relevant if the ball is sped up significantly using cheat 
keys, but is rarer if the game is played properly. 

Extra credit: My special feature was to allow the paddle to move upwards, but not above a certain point so that 
the paddle would not overlap with bricks. This allows the player to play with 2D directions as compared to 1. I also
feel that my heavy ball power up and my death brick are pretty unique to this game. Another thing unique to my game 
is that the score increment is greater on higher levels, to reflect the higher difficulty. Lastly, I modified my paddle
such that the ball will bounce off at different angles depending on where it hit the paddle. 

### Notes/Assumptions

I managed to implement my heavy ball, such that it would break all bricks, even the unbreakable/death bricks, without
a change in direction. I decided to do so because this would increase more variability in the shape of bricks, which 
would entice players to play the game more. A second reason is that this gave the "heavy-ball" power up an added 
advantage of allowing the player to destroy the death brick at the cost of one life. If this death brick is not 
destroyed, the player runs the risk of continually losing lives on contact. 

Also, another design decision was the decision not to let the paddle move out of screen. This means that if due to a 
paddle elongate power up, the paddle moves off the left of the screen, it can only move to the right. This makes the
assumption that the paddle length will not exceed the screen width (if not the paddle becomes immovable). This 
assumption holds under the game's normal implementation, but the user should be careful if they are using cheat keys.

### Impressions

I felt that implementing this game was extremely difficult due to my inexperience. Having said so, I learnt a ton from
this one assignment, both about javafx and about good coding practices. Maybe more starter code would have been useful 
if design is the focus here, but in general, I enjoyed this assignment immensely. 
