
public class WheelTrajectory {

	private Trajectory[] trajectories = new Trajectory[2];

	public WheelTrajectory(final Trajectory left, final Trajectory right) {
		trajectories[0] = left;
		trajectories[1] = right;
	}
	
	public WheelTrajectory(final Trajectory[] trajectories) {
		this.trajectories = trajectories;
	}
	
	public Trajectory getLeftTrajectory() {
		return trajectories[0];
	}
	
	public Trajectory getRightTrajectory() {
		return trajectories[1];
	}
	
	public void graphForwardvsHorizontal() {
		double[] forward = new double[getLeftTrajectory().length()];
		double[] horizontal = new double[getLeftTrajectory().length()];
		double[] forward2 = new double[getRightTrajectory().length()];
		double[] horizontal2 = new double[getRightTrajectory().length()];
		for (int i = 0; i < forward.length; i++) {
			forward[i] = getLeftTrajectory().get(i).getForward();
			horizontal[i] = getLeftTrajectory().get(i).getHorizontal();
			forward2[i] = getRightTrajectory().get(i).getForward();
			horizontal2[i] = getRightTrajectory().get(i).getHorizontal();
		}

		new Graph("Horizontal", "Forward", horizontal, forward, horizontal2, forward2).run();
	}
	
	public void graphVelocities() {
		double[] time = new double[getLeftTrajectory().length()];
		double[] velLeft = new double[getLeftTrajectory().length()];
		double[] velRight = new double[getRightTrajectory().length()];
		for (int i = 0; i < time.length; i++) {
			time[i] = getLeftTrajectory().get(i).getTime();
			velLeft[i] = getLeftTrajectory().get(i).getVelocity();
			velRight[i] = getRightTrajectory().get(i).getVelocity();
		}

		new Graph("Velocity", "Time", time, velLeft,time, velRight).run();
	}
}