# Game Plan
## NAME

Eric Tay, netid: et102

### Breakout Variant

I felt that 'Warlords' was the most interesting. This is because, unlike the other variants, it
incorporated the idea of playing against other players. By virtue of this, the game has both 
offensive and defensive concepts. I also found it cool that the paddle had a sticky hold that could
be released when the player wanted it to. This was a refreshing change to the normal single-player
mode of Breakout that I was used to. 

### General Level Descriptions

I would build 3 levels each of which has a different starting configuration of bricks. As the levels 
increase, the paddle size will decrease, the bricks would be closer to the bottom and the ball's speed 
may increase. In terms of configuration, level one will have the blocks in a horizontal configuration, 
and these blocks would be easily broken. Level two will have blocks in a more diagonal configuration, 
and these blocks would require more hits to be broken. Level 3 will incorporate diagonal shapes, 
but also have blocks that can never be broken. 

### Bricks Ideas

I will create bricks that take at most three hits to break. Green will take 3 hits, Yellow will take 2, 
and Red will take 1. The color will change as the balls hit the bricks. Purple bricks will break on one 
hit and drop a power up. Grey bricks will be unbreakable. Black bricks are unbreakable and will make the 
player lose a life when hit.

### Power Up Ideas

I will have the following power ups: One that increases paddle size, one that adds a life, one that makes
the ball slower, and one that makes the ball heavy. Heavy balls can break any brick and keep going without
a change in direction until the boundary. If I am unable to implement this, my heavy ball will then simply
be a ball that will be able to break any brick on one hit and rebound back normally. 

### Cheat Key Ideas

I will have the following cheat keys:

'L': add additional lives to the player
'R': resets the ball and paddle to their starting position
'N': next - passes current level
'B': back - goes to previous level
'F': makes the ball faster
'S': makes the ball slower
'E': elongates paddle
'1-9': when the player presses a numeric key, clear the current level and jump to the level corresponding to the number pressed (or the highest one that exists)

### Something Extra

My additional feature will be the added mobility of paddle and bricks. I would like my paddle to be able
to move both vertically and horizontally, within a fixed vertical distance from the bottom of the screen. 
If I have extra time, I would also like to made the bricks descend slowly, such that if a brick descends
below the screen, the player loses. I think it's a substantial addition because the player can now move his
paddle in 2D as compared to 1D. This introduces variability and allwos the player to play with more strategy. 
I feel that the moving bricks would make the game more exciting, and also prevent players from 'playing safe' 
and going for riskier options.