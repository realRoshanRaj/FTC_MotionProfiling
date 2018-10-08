
public class Segment {
	double time, forward, horizontal, position, velocity, acceleration, heading;

	/**
	 * @param time
	 * @param x
	 * @param y
	 * @param position
	 * @param velocity
	 * @param acceleration
	 */
	public Segment(double time, double x, double y, double position, double velocity, double acceleration) {
		this.time = time;
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
		time = to_copy.time;
		forward = to_copy.forward;
		horizontal = to_copy.horizontal;
	}

	/**
	 * @return position of segment
	 */
	public double getPosition() {
		return position;
	}

	/**
	 * @return velocity of segment
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * @return acceleration of segment
	 */
	public double getAcceleration() {
		return acceleration;
	}

	/**
	 * @return heading of segment
	 */
	public double getHeading() {
		return heading;
	}

	/**
	 * @return time of segment
	 */
	public double getTime() {
		return time;
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
