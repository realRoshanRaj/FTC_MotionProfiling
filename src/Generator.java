public class Generator {

	/**
	 * @param config
	 * @param distance (in inches)
	 * @return
	 */
	public static Trajectory generateTrajectory(Config config, double distance) {
		double time = config.max_velocity / config.max_acceleration;
		double area = time * config.max_velocity;
		if (distance <= area) {
			return generateTriangular(config, distance);
		} else {
			return generateTrapezoidal(config, distance);
		}
	}

	public static Trajectory generateTriangular(Config config, double distance) {
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
				traj[i] = new Segment(dt, newPosition, vel, currAccel);
				prevPosition = newPosition;
				prevVel = vel;
			} else {
				// y = -currAccel + currMaxVel
				double vel = -currAccel * (dt - totalTime / 2) + currMaxVel;
				double newPosition = ((vel + prevVel) / 2 * (config.dt)) + prevPosition;
				traj[i] = new Segment(dt, newPosition, vel, -currAccel);
				prevPosition = newPosition;
				prevVel = vel;
			}
		}

		Trajectory trajectory = new Trajectory(traj);
		return trajectory;
	}

	public static Trajectory generateTrapezoidal(Config config, double distance) {
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
					traj[i] = new Segment(dt, newPosition, vel, currAccel);
					prevPosition = newPosition;
					prevVel = vel;
				} else if (dt < (totalTime - time)) {
					currAccel = 0.0;
					currVel = maxVel;
					double vel = currVel;
					double newPosition = (vel + prevVel) / 2 * (config.dt) + prevPosition;
					traj[i] = new Segment(dt, newPosition, currVel, currAccel);
					prevPosition = newPosition;
					prevVel = vel;
				} else {
					currAccel = maxAccel;
					currVel = maxVel;
					double vel = -currAccel * (dt - (totalTime - time)) + currVel;
					double newPosition = (vel + prevVel) / 2 * (config.dt) + prevPosition;
					traj[i] = new Segment(dt, newPosition, vel, -currAccel);
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
