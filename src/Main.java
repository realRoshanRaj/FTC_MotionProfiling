public class Main {
	public static void main(String[] args) {
		Trajectory traj = new Generator().generateTrajectory(new Config(0.010000000000000, 50, 10), 260);
		traj.printContent();
	}
}
