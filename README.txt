6/21/2013
/*author mohanish
Timer 
- createa timer for the track 
- the timer keeps track of two cars independtly
*/
6/17/13

Architecture:
- Added multiplayer
- Added Track class
- Added drawing of environment

Environment:
- Track class holds wall, terrain, and terrain color array lists

6/13/13

Architecture:
- Created rectangles for collision detection
- Using area for all collision detection
- Shape collision detection -- in CustomPanel
- Working with race track that’s not a loop

6/13/13

Car Movement:
- Removed maximum turning radius.  Resets by 360 if full circle.
- Added .98 frictional forces.
- Fixed turning when in reverse

Architecture:
- Using boolean array to track key changes.  Much smoother movement.
- Implemented Thread.
- Removed Rectangle for car XY.  Just using accessor methods instead.
- Added updateCar(), for key check and accel/turning
- Loading Red, RedCrash, Blue, BlueCrash for later.
- Cleaned up useless code.

6/7/13

Car Movement:
- Issue with turning maximum radius.
- Issue with turning relative to car’s current location or start of turning angle. (If holding key press maintain turning radius, on release and new press begin new turning angle?  However, what about accelerated turning vs. maintained turning radius?)
- Issue with turning direction when car is in reverse.
- Need to reposition car label according to car direction.
- Need runnable thread asap.

6/5/13

Car Movement:
-Rotation matrix: [cos(dt) -sin(dt);sin(dt) cos(dt)], transposte for opposite.
-Make Up accelerate, Down to deccelerate, and Left/Right to turn.
-Direction and speed relative to car >> Need method to convert car direction to frame XY coordinates.
-Separate handling of Up (accel), Down (decel), Left (CCW), and Right (CW).
-Figure out way to reset turning. So, it does not compound indefinitely.
-Add friction forces.

Environment:
-Changed label movement on panel.
-Make boundaries attach to panel.
-Feed boundaries to a receiver in infrastructure.

Architecture:
-Thread that repaints car based on carSpeed and carDir (direction). Applies motion methods and repaints on background.
-Key pressing only effects carSpeed and carDir, but is not the only force
-Figure out multiple key presses
-Friction, environment, and obstacles will also effect carSpeed.
-Collision detection

6/3/13

1) Car movement
- Acceleration
- Update car position during movement
- Top acceleration, speed
- Same car mass,
- Make a single lane track, with a box... Make it move forward. 
- Make a turning lane track.  Make box turn left and right.
- Start making more complex tracks
2) Environment physics
- Obstacle physics
- Track physics, road physics vs. off-road physics
	- Multiple types of off-road: grass (green), desert (yellow), and snow (white)
- Two methods:
	1) Detect colors used, and apply towards motion physics. Difficulty lies in color detection of images -- How does Java do this??
2) Pre-defined borders in track.  Specified x,y coordinates.  When car crosses apply slowing movement.  Difficulty lies in track design.

3) Multiplayer
- WASD and LRDU arrow keys
- Controlling two cars
- Gameplay, how do you win?