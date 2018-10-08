public class StraightProfileGenerator {

	/**
	 * @param config
	 * @param distance (in inches)
	 * @return {@link WheelTrajectory}
	 */
	public static Trajectory generateTrajectory(Config config, double distance) {
		Trajectory trajectory;
		double time = config.max_velocity / config.max_acceleration;
		double area = time * config.max_velocity;
		if (Math.abs(distance) <= area) {
			trajectory = generateTriangular(config, Math.abs(distance));
		} else {
			trajectory = generateTrapezoidal(config, Math.abs(distance));
		}
		if (distance < 0) {
			for (int i = 0; i < trajectory.length(); i++) {
				trajectory.segments[i].position *= -1;
				trajectory.segments[i].velocity *= -1;
				trajectory.segments[i].acceleration *= -1;
			}
		}

		return trajectory;
	}

	/**
	 * @param config
	 * @param distance (in inches)
	 * @return {@link WheelTrajectory}
	 */
	public static WheelTrajectory generateTrajectory(Config config, final double wheelbase_width, double distance) {
		double time = config.max_velocity / config.max_acceleration;
		double area = time * config.max_velocity;
		Trajectory cent, left, right;

		if (Math.abs(distance) <= area) {
			cent = generateTriangular(config, Math.abs(distance));
		} else {
			cent = generateTrapezoidal(config, Math.abs(distance));
		}

		left = cent.copy();
		right = cent.copy();

		for (int i = 0; i < cent.length(); i++) {
			left.segments[i].horizontal -= (wheelbase_width / 2);
			right.segments[i].horizontal += (wheelbase_width / 2);
			if (distance < 0) {
				left.segments[i].position *= -1;
				left.segments[i].velocity *= -1;
				left.segments[i].acceleration *= -1;
				right.segments[i].position *= -1;
				right.segments[i].velocity *= -1;
				right.segments[i].acceleration *= -1;
			}
		}

		return new WheelTrajectory(left, right);
	}

	/**
	 * @param config
	 * @param distance to travel
	 * @return {@link Trajectory}
	 */
	private static Trajectory generateTriangular(Config config, double distance) {
		Segment[] traj;
		double maxAccel = config.max_acceleration;
		double maxVel = config.max_velocity;
		double totalTime = 0;
		double currMaxVel, currAccel;

		totalTime = 2 * distance / maxVel;
		currAccel = maxVel * maxVel / distance;
		if (currAccel > maxAccel) {
			totalTime = Math.sqrt(4 * distance / maxAccel);
			currMaxVel = Math.sqrt(distance * maxAccel);
			currAccel = maxAccel;
		} else {
			currMaxVel = maxVel;
		}
		traj = new Segment[(int) ((totalTime / config.dt) + 0.5)];
		double dt = 0, prevPosition = 0, prevVel = 0;
		for (int i = 0; i < traj.length; i++) {
			dt = config.dt * i;
			if (dt < totalTime / 2) {
				double vel = currAccel * dt;
				double newPosition = (vel + prevVel) / 2 * (config.dt) + prevPosition;
				traj[i] = new Segment(dt, newPosition, 0, newPosition, vel, currAccel);
				prevPosition = newPosition;
				prevVel = vel;
			} else {
				// y = -currAccel + currMaxVel
				double vel = -currAccel * (dt - totalTime / 2) + currMaxVel;
				double newPosition = ((vel + prevVel) / 2 * (config.dt)) + prevPosition;
				traj[i] = new Segment(dt, newPosition, 0, newPosition, vel, -currAccel);
				prevPosition = newPosition;
				prevVel = vel;
			}
		}

		Trajectory trajectory = new Trajectory(traj);
		return trajectory;
	}

	/**
	 * @param config
	 * @param distance
	 * @return {@link Trajectory}
	 */
	private static Trajectory generateTrapezoidal(Config config, double distance) {
		Segment[] traj;
		double maxAccel = config.max_acceleration;
		double maxVel = config.max_velocity;
		double totalTime = 0;
		double currVel, currAccel;

		double time = maxVel / maxAccel;
		double area = time * maxVel;
		if (area < distance) {

			totalTime = (2 * time) + (distance - area) / maxVel;

			traj = new Segment[(int) ((totalTime / config.dt) + 0.5)];

			double dt = 0, prevPosition = 0, prevVel = 0;
			for (int i = 0; i < traj.length; i++) {
				dt = config.dt * i;
				// Velocity and Accel
				if (dt < time) {
					currAccel = maxAccel;
					double vel = currAccel * dt;
					double newPosition = (vel + prevVel) / 2 * (config.dt) + prevPosition;
					traj[i] = new Segment(dt, newPosition, 0, newPosition, vel, currAccel);
					prevPosition = newPosition;
					prevVel = vel;
				} else if (dt < (totalTime - time)) {
					currAccel = 0.0;
					currVel = maxVel;
					double vel = currVel;
					double newPosition = (vel + prevVel) / 2 * (config.dt) + prevPosition;
					traj[i] = new Segment(dt, newPosition, 0, newPosition, currVel, currAccel);
					prevPosition = newPosition;
					prevVel = vel;
				} else {
					currAccel = maxAccel;
					currVel = maxVel;
					double vel = -currAccel * (dt - (totalTime - time)) + currVel;
					double newPosition = (vel + prevVel) / 2 * (config.dt) + prevPosition;
					traj[i] = new Segment(dt, newPosition, 0, newPosition, vel, -currAccel);
					prevPosition = newPosition;
					prevVel = vel;
				}
			}

			Trajectory trajectory = new Trajectory(traj);
			return trajectory;
		} else {
			return generateTriangular(config, distance);
		}

	}
}
