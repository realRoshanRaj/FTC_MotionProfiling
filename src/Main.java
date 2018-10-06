public class Main {
	static Config config = new Config(0.050000000000000, 50, 10);
	static final double wheelbase_width = 24.0; // 24 inches apart

	public static void main(String[] args) {
		WheelTrajectory traj = PivotProfileGenerator.generateTrajectory(config, wheelbase_width, 90);
	}
}
		