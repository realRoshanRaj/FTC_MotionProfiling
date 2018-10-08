# MOTION PROFILING FOR FTC

Math involves knowing maximum velocity and max acceleration.
Calculates ideal velocity and position to get to desired distance. 

### Which unit should I use?
Unit can be anything as long as it is consistant. <br> 
For example: <br>
If we want the robot/vehicle to move 10 **inches** forward, then the velocity would need to be **inches per second (in/sec)**
and acceleration would be **inches per second per second (inches/second^2)**.

### How do I calculate max velocity and max acceleration?
[Click here for link to KinematicTest](https://github.com/realRoshanRaj/FTC_LinearMotionProfiling/tree/ftcapp/ftc_app-master/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Samples)

#### Using that class you can find both max velocity and max acceleration. 

Before you use kinematic test make sure you configured the [Hardware](https://github.com/realRoshanRaj/FTC_LinearMotionProfiling/tree/ftcapp/ftc_app-master/TeamCode/src/main/java/org/firstinspires/ftc/teamcode) file correctly.
<br> <br> Put the number of encoder ticks per one revolution of your motor as such:

```java
public final int ticks_per_revolution = 1440; // change 1440 to the number of ticks per rev for your motor
```

Do the same for wheel diameter:
```java
public final double wheel_diameter = 4.0; // measure your wheel in the unit that you want to keep constant. EX) inches
```

#### Look at the telemetry data received from the phone then save that to the Hardware file.
![Power Display](https://user-images.githubusercontent.com/34359601/46500921-06820f00-c7f2-11e8-87fa-eb344e2b88c5.gif "Power Display")
