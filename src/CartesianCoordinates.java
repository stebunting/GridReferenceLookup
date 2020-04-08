import java.util.Locale;

// Define class to specify Cartesian coordinates
public class CartesianCoordinates {

	// Fields
	private final double x;
	private final double y;
	private final double z;
	
	// Constructor
	public CartesianCoordinates(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		String str = String.format(Locale.getDefault(), "(%f, %f, %f)", x, y, z);
		return str;
	}

	// Getters
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
}
