
public class Segment {
	double dt, position, velocity, acceleration, heading;

	public Segment(double dt, double position, double velocity, double acceleration) {
		this.dt = dt;
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.heading = 0.0;
	}

	public double getPosition(){
		return position;
	}

	public double getVelocity(){
		return velocity;
	}

}
