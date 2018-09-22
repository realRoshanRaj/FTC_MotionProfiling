public class Main {
	public static void main(String[] args) {
		Trajectory traj = new Generator().generateTrajectory(new Config(0.050000000000000, 50, 20), 190);
//		Timer time = new Timer(); // Instantiate Timer Object
//
//		time.schedule(new TimerTask() {
//			double dt = 0;
//
//			@Override
//			public void run() {
//		
//				if (dt > 1) {
//					System.out.println("canceled");
//					time.cancel();
//					System.out.println("canceled");
//				} else {
//					dt += 0.0500;
//					System.out.println(dt);
//				}
//			}
//		}, 0, 1000); // Create Repetitively task for every 1 secs
	}
}
