import java.util.Locale;

public class CartesianCoordinates {

	// Properties
	private final double x;
	private final double y;
	private final double z;
	private final Locale locale;
	
	// Initialiser
	public CartesianCoordinates(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.locale = Locale.getDefault();
	}

	@Override
	public String toString() {
		String str = String.format(locale, "(%f, %f, %f)", x, y, z);
		return str;
	}

	// Getters
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
}
