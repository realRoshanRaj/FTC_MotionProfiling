package motionProfileGenerator.ftc.tools;

public class PivotProfileGenerator {
	
	/**
	 * @param config
	 * @param wheelbase_width
	 * @param angle --> Positive if CW; negative CCW
	 * @return
	 */
	public static WheelTrajectory generateTrajectory(Config config, double wheelbase_width, double angle) {
		WheelTrajectory trajectories;
		double r = wheelbase_width / 2;
		double theta = Math.toRadians(boundHalfDegrees(angle));
		double dist = r * theta;
		if (dist < 0) {
			Trajectory right = StraightProfileGenerator.generateTrajectory(config, Math.abs(dist));
			Trajectory left = right.copy();
			for (int i = 0; i < left.length(); i++) {
				double heading = -left.get(i).getPosition() / r;
				left.segments[i].velocity *= -1;
				left.segments[i].acceleration *= -1;
				left.segments[i].position *= -1;
				left.segments[i].heading = Math.toDegrees(heading);
				right.segments[i].heading = Math.toDegrees(heading);
				left.segments[i].horizontal = -r * Math.cos(heading);
				left.segments[i].forward = -r * Math.sin(heading);
				right.segments[i].horizontal = r * Math.cos(heading);
				right.segments[i].forward = r * Math.sin(heading);
			}
			trajectories = new WheelTrajectory(left, right);
		} else {
			Trajectory left = StraightProfileGenerator.generateTrajectory(config, Math.abs(dist));
			Trajectory right = left.copy();
			for (int i = 0; i < right.length(); i++) {
				double heading = left.get(i).getPosition() / r;
				right.segments[i].velocity *= -1;
				right.segments[i].acceleration *= -1;
				right.segments[i].position *= -1;
				left.segments[i].heading = Math.toDegrees(heading);
				right.segments[i].heading = Math.toDegrees(heading);
				left.segments[i].horizontal = -r * Math.cos(heading);
				left.segments[i].forward = r * Math.sin(heading);
				right.segments[i].horizontal = r * Math.cos(heading);
				right.segments[i].forward = -r * Math.sin(heading);
			}
			trajectories = new WheelTrajectory(left, right);
		}
		return trajectories;
	}

	/**
	 * Bound an angle (in degrees) to -180 to 180 degrees.
	 * 
	 * @param angle_degrees an input angle in degrees
	 * @return the bounded angle
	 */
	private static double boundHalfDegrees(double angle_degrees) {
		while (angle_degrees >= 180.0)
			angle_degrees -= 360.0;
		while (angle_degrees < -180.0)
			angle_degrees += 360.0;
		return angle_degrees;
	}

}
