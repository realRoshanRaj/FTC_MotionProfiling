
public class Segment {
	double dt, forward, horizontal, position, velocity, acceleration, heading;

	/**
	 * @param dt
	 * @param x
	 * @param y
	 * @param position
	 * @param velocity
	 * @param acceleration
	 */
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

	/**
	 * @param segment to be copied
	 */
	public Segment(Segment to_copy) {
		position = to_copy.position;
		velocity = to_copy.velocity;
		acceleration = to_copy.acceleration;
		heading = to_copy.heading;
		dt = to_copy.dt;
		forward = to_copy.forward;
		horizontal = to_copy.horizontal;
	}

	/**
	 * @return position
	 */
	public double getPosition() {
		return position;
	}

	/**
	 * @return velocity
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * @return acceleration
	 */
	public double getAcceleration() {
		return acceleration;
	}

	/**
	 * @return heading
	 */
	public double getHeading() {
		return heading;
	}

	/**
	 * @return time to cycle
	 */
	public double getTime() {
		return dt;
	}

	/**
	 * @return horizontal change
	 */
	public double getHorizontal() {
		return horizontal;
	}

	/**
	 * @return forward change
	 */
	public double getForward() {
		return forward;
	}

}
