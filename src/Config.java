public class Config {
	public double dt, max_velocity, max_acceleration;	
	
	/**
	 * @param dt				The time delta between points (in seconds)
	 * @param max_velocity		The maximum velocity the body is capable of traveling at (in inches per second)
	 * @param max_acceleration	The maximum acceleration to use (in inches per second per second)
	 */
	public Config(double dt, double max_velocity, double max_acceleration) {
		this.dt = dt;
		this.max_velocity = max_velocity;
		this.max_acceleration = max_acceleration;
	}
}