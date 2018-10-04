public class Main {
	public static Config config = new Config(0.050000000000000, 50, 10);
	
	public static void main(String[] args) {
		Trajectory traj = Generator.generateTrajectory(config, 260);
		traj.printContent();
	}
}
