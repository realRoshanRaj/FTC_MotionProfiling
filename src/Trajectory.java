
public class Trajectory {

	public Segment[] segments;

	public Trajectory(Segment[] seg) {
		segments = seg;
	}

	public Segment get(int index) {
		return segments[index];
	}

	public int length() {
		return segments.length;
	}

	public void printContent() {
		printContent(this.segments);
	}

	public static void printContent(Segment[] segs) {
		for (Segment i : segs) {
			System.out.print("Dt " + i.dt);
			System.out.print(" Velocity " + i.velocity);
			System.out.print(" Acceleration " + i.acceleration);
			System.out.print(" Postion " + i.position);
			System.out.print(" Heading " + i.heading);
			System.out.println(" Power " + i.velocity / Main.config.max_velocity);
		}

	}

}