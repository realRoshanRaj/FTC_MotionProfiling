public class Generator {

	/**
	 * @param config
	 * @param distance (in inches)
	 * @return
	 */
	public Trajectory generateTrajectory(Config config, double distance) {
		double time = config.max_velocity / config.max_acceleration;
		double area = time * config.max_velocity;
//		double area = time * config.max_velocity * 2;
		if (distance <= area) {
			return generateTriangular(config, distance);
		} else {
			return generateTrapezoidal(config, distance);
		}
	}

	public Trajectory generateTriangular(Config config, double distance) {
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
//			System.out.println("Changed VEL " + currMaxVel);
		} else {
			currMaxVel = maxVel;
		}
		traj = new Segment[(int) (totalTime / config.dt)];
		double dt = 0, prevPosition = 0, prevVel = 0;
		for (int i = 0; i < traj.length; i++) {
			dt = config.dt * i;
			if (dt < totalTime / 2) {
				double vel = currAccel * dt;
				double newPosition = (vel + prevVel) / 2 * (config.dt) + prevPosition;
//				midPosition = currAccel * dt * dt / 2;
				traj[i] = new Segment(dt, newPosition, vel, currAccel);
				prevPosition = newPosition;
				prevVel = vel;
			} else {
				// y = -currAccel + currMaxVel
				double vel = -currAccel * (dt - totalTime / 2) + currMaxVel;
				double newPosition = ((vel + prevVel) / 2 * (config.dt)) + prevPosition;
//				double position = (2 * midPosition)
//						- (currAccel * Math.pow(totalTime / 2 - (dt - totalTime / 2), 2) / 2);
				traj[i] = new Segment(dt, newPosition, vel, -currAccel);
				prevPosition = newPosition;
				prevVel = vel;
			}
		}

		Trajectory trajectory = new Trajectory(traj);
		double[] vel = new double[traj.length], time = new double[traj.length], pos = new double[traj.length],
				zero = new double[traj.length];
		for (int i = 0; i < vel.length; i++) {
			vel[i] = trajectory.get(i).velocity;
			time[i] = trajectory.get(i).dt;
			pos[i] = trajectory.get(i).position;
			zero[i] = 0;
		}

		new Graph("Time", "Position", time, pos).start();
		new Graph("Time", "Velocity", time, vel).start();

		return trajectory;
	}

	public Trajectory generateTrapezoidal(Config config, double distance) {
		Segment[] traj;
		double maxAccel = config.max_acceleration;
		double maxVel = config.max_velocity;
		double totalTime = 0;
		double currVel, currAccel;

		double time = maxVel / maxAccel;
//		double area = time * maxVel * 2;
		double area = time * maxVel;
		if (area < distance) {

			totalTime = (2 * time) + (distance - area) / maxVel;

			traj = new Segment[(int) (totalTime / config.dt)];

			double dt = 0, prevPosition = 0, prevVel = 0;
			for (int i = 0; i < traj.length; i++) {
//				System.out.println(dt);
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
			double[] vel = new double[traj.length], timeGraph = new double[traj.length], pos = new double[traj.length],
					zero = new double[traj.length];
			for (int i = 0; i < vel.length; i++) {
				vel[i] = trajectory.get(i).velocity;
				timeGraph[i] = trajectory.get(i).dt;
				pos[i] = trajectory.get(i).position;
				zero[i] = 0;
			}

			new Graph("Time", "Position", timeGraph, pos).start();
			new Graph("Time", "Velocity", timeGraph, vel).start();

			return trajectory;
		} else {
			return generateTriangular(config, distance);
		}

	}
}
