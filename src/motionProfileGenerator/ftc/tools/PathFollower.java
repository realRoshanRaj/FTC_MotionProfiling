package motionProfileGenerator.ftc.tools;

public class PathFollower {
	WheelTrajectory trajectory;
	int left_encoder_offset, right_encoder_offset, encoder_tick_count;
	double wheel_circumference;

	public PathFollower(WheelTrajectory trajectory) {
		this.trajectory = trajectory;
	}

	double kP = 0, kV = 0;

	/**
	 * Configure the Encoders being used in the follower. initial_position The
	 * initial 'offset' of your encoder. This should be set to the encoder value
	 * just before you start to track
	 *
	 * @param ticks_per_revolution How many ticks per revolution the encoder has
	 * @param wheel_diameter       The diameter of your wheels (or pulleys for track
	 *                             systems) in meters
	 */
	public void configureEncoder(int right_initial_position, int left_initial_position, int ticks_per_revolution,
			double wheel_diameter) {
		left_encoder_offset = left_initial_position;
		right_encoder_offset = right_initial_position;
		encoder_tick_count = ticks_per_revolution;
		wheel_circumference = Math.PI * wheel_diameter;
	}

	public void configurePV(final double kP, final double kV) {
		this.kP = kP;
		this.kV = kV;
	}

	public double calculateLeftPower(int index, int currentPosition) {
		Segment segment = trajectory.getLeftTrajectory().get(index);
		double power = 0;
		if (index < trajectory.getLeftTrajectory().length()) {
			double distance_covered = ((double) (currentPosition - left_encoder_offset) / encoder_tick_count)
					* wheel_circumference;
			double error = segment.getPosition() - distance_covered;

			double currVelocity = segment.getVelocity();

			if (currVelocity > 0) {
				power = (kP * error) + (kV * currVelocity);
			} else {
				power = (kP * error) - (kV * currVelocity);
			}
		}
		return power;

	}

	public double calculateRightPower(int index, int currentPosition) {
		Segment segment = trajectory.getRightTrajectory().get(index);
		double power = 0;
		if (index < trajectory.getRightTrajectory().length()) {
			double distance_covered = ((double) (currentPosition - right_encoder_offset) / encoder_tick_count)
					* wheel_circumference;
			double error = segment.getPosition() - distance_covered;

			double currVelocity = segment.getVelocity();

			if (currVelocity > 0) {
				power = (kP * error) + (kV * currVelocity);
			} else {
				power = (kP * error) - (kV * currVelocity);
			}
		}
		return power;
	}

	public static double boundHalfDegrees(double angle_degrees) {
		while (angle_degrees >= 180.0) {
			angle_degrees -= 360.0;
		}

		while (angle_degrees < -180.0) {
			angle_degrees += 360.0;
		}

		return angle_degrees;
	}

	public double countsToUnit(int position) {
		return (double) (position) / encoder_tick_count * wheel_circumference;
	}

	public int unitToCounts(double unit) {
		return (int) (unit/wheel_circumference * encoder_tick_count);
	}
}
