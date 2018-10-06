
public class Segment {
	double dt, forward, horizontal, position, velocity, acceleration, heading;

	public Segment(double dt, double x, double y, double position, double velocity, double acceleration) {
		this.dt = dt;
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.forward = x;
		this.heading = 0.0;
		this.horizontal = y;
	}

	public Segment() {

	}

	public Segment(Segment to_copy) {
		position = to_copy.position;
		velocity = to_copy.velocity;
		acceleration = to_copy.acceleration;
		heading = to_copy.heading;
		dt = to_copy.dt;
		forward = to_copy.forward;
		horizontal = to_copy.horizontal;
	}

	public double getPosition() {
		return position;
	}

	public double getVelocity() {
		return velocity;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public double getHeading() {
		return heading;
	}

	public double getTime() {
		return dt;
	}

	public double getHorizontal() {
		return horizontal;
	}

	public double getForward() {
		return forward;
	}

}
