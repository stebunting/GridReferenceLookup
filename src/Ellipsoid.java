// Define available Ellipsoids
enum EllipsoidName {
	WGS84,
	GRS80,
	AIRY1830,
	AIRY1830_MODIFIED
}

// Define an Ellipsoid
public class Ellipsoid {

	// Fields
    private final String name;
    private final double equatorialRadius;
    private final double polarRadius;
    private final double flattening;
    private final double eccentricitySquared;
    
    // Constructor
    public Ellipsoid(String name, double equatorialRadius, double polarRadius) {
        this.name = name;
        this.equatorialRadius = equatorialRadius;
        this.polarRadius = polarRadius;
        this.flattening = 1 - (polarRadius / equatorialRadius);
        this.eccentricitySquared = (Math.pow(equatorialRadius, 2.0) - Math.pow(polarRadius, 2.0)) / Math.pow(equatorialRadius, 2.0);
    }
    
    // Getters
    public String getName() { return name; }
    public double getEquatorialRadius() { return equatorialRadius; }
    public double getPolarRadius() { return polarRadius; }
    public double getFlattening() { return flattening; }
    public double getEccentricitySquared() { return eccentricitySquared; }
}
