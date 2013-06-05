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