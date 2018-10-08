package motionProfileGenerator.ftc.tools;

/**
 * <p>
 * This class contains all the necesary data about the trajectory that the robot
 * will follow
 * </p>
 * 
 * @see Segment
 * 
 *
 */
public class Trajectory {

	Segment[] segments = null;

	/**
	 * Constructs trajectory with a set of segments
	 * 
	 * @param segment array
	 */
	public Trajectory(Segment[] seg) {
		this.segments = seg;
	}

	/**
	 * Creates empty trajectory of specified size
	 * 
	 * @param size
	 */
	public Trajectory(int size) {
		this.segments = new Segment[size];
	}

	/**
	 * 
	 * @param index
	 * @return segment at specified index
	 */
	public Segment get(int index) {
		return segments[index];
	}

	/**
	 * 
	 * @return length of trajectory
	 */
	public int length() {
		return segments.length;
	}

	/**
	 * 
	 * @return copy of trajectory
	 */
	public Trajectory copy() {
		Trajectory cloned = new Trajectory(length());
		Segment[] copied = new Segment[length()];
		for (int i = 0; i < this.segments.length; ++i) {
			copied[i] = new Segment(this.segments[i]);
		}
		cloned.segments = copied;
		return cloned;
	}

}