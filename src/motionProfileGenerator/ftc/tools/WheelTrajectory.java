package motionProfileGenerator.ftc.tools;

/**
 * Stores two trajectories for left and right side of the drivetrain
 *
 */
public class WheelTrajectory {

	private Trajectory[] trajectories = new Trajectory[2];

	/**
	 * 
	 * @param left  trajectory
	 * @param right trajectory
	 */
	public WheelTrajectory(final Trajectory left, final Trajectory right) {
		trajectories[0] = left;
		trajectories[1] = right;
	}

	/**
	 * 
	 * @param trajectories
	 */
	public WheelTrajectory(final Trajectory[] trajectories) {
		this.trajectories = trajectories;
	}

	/**
	 * 
	 * @return left wheel trajectory
	 */
	public Trajectory getLeftTrajectory() {
		return trajectories[0];
	}

	/**
	 * 
	 * @return right wheel trajectory
	 */
	public Trajectory getRightTrajectory() {
		return trajectories[1];
	}
}