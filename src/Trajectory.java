
public class Trajectory {

	Segment[] segments = null;

	public Trajectory(Segment[] seg) {
		this.segments = seg;
	}

	public Trajectory(int size) {
		this.segments = new Segment[size];
	}

	public Segment get(int index) {
		return segments[index];
	}

	public Trajectory copy() {
		Trajectory cloned = new Trajectory(length());
		Segment[] copied = new Segment[length()];
		for (int i = 0; i < this.segments.length; ++i) {
			copied[i] = new Segment(this.segments[i]);
		}
		cloned.segments = copied;
		return cloned;
	}

	public int length() {
		return segments.length;
	}

	public void printContent() {
		printContent(this.segments);
	}

	public static void printContent(Segment[] segs) {
		for (Segment i : segs) {
			System.out.print("Dt " + i.getTime());
			System.out.print(" Velocity " + i.getVelocity());
			System.out.print(" Acceleration " + i.getAcceleration());
			System.out.print(" Postion " + i.getPosition());
			System.out.print(" Heading " + i.getHeading());
			System.out.println();
		}

	}

	public void graphVelocity() {
		double[] vel = new double[length()];
		double[] time = new double[length()];
		for (int i = 0; i < vel.length; i++) {
			vel[i] = get(i).getVelocity();
			time[i] = get(i).getTime();
		}
		new Graph("Time", "Velocity", time, vel).run();

	}

	public void graphForwardvsHorizontal() {
		double[] forward = new double[length()];
		double[] horizontal = new double[length()];
		for (int i = 0; i < forward.length; i++) {
			forward[i] = get(i).getForward();
			horizontal[i] = get(i).getHorizontal();
		}

		new Graph("Horizontal", "Forward", horizontal, forward).run();
	}

	public void graphPosition() {
		double[] pos = new double[length()];
		double[] time = new double[length()];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = get(i).getPosition();
			time[i] = get(i).getTime();
		}
		new Graph("Time", "Position", time, pos).run();
	}

	public void graphAcceleration() {
		double[] accel = new double[length()];
		double[] time = new double[length()];
		for (int i = 0; i < accel.length; i++) {
			accel[i] = get(i).getAcceleration();
			time[i] = get(i).getTime();
		}
		new Graph("Time", "Acceleration", time, accel).run();
	}

}