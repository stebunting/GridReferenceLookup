// Define available Datums
enum DatumName {
	NATIONAL_GRID,
	IRISH_NATIONAL_GRID,
	UTM_NORTH,
	UTM_SOUTH
}

// Define a Datum
public class Datum {

	// Fields
    private final String name;
    private final double scaleFactor;
    private final double trueOriginPhi;
    private final int trueOriginLambda;
    private final int trueOriginEasting;
    private final int trueOriginNorthing;
    private final double[] helmertTransform;
    private final double helmertScale;
    private final double[] helmertRotation;
    private final Ellipsoid ellipsoid;
    
    // Constructor
    public Datum(String name, double scaleFactor, double trueOriginPhi,
    		int trueOriginLambda, int trueOriginEasting, int trueOriginNorthing,
    		double helmertTransformX, double helmertTransformY, double helmertTransformZ,
    		double helmertScale, double helmertRotationX, double helmertRotationY,
    		double helmertRotationZ, Ellipsoid ellipsoid) {
        this.name = name;
        this.scaleFactor = scaleFactor;
        this.trueOriginPhi = trueOriginPhi;
        this.trueOriginLambda = trueOriginLambda;
        this.trueOriginEasting = trueOriginEasting;
        this.trueOriginNorthing = trueOriginNorthing;
        this.helmertTransform = new double[]
        		{helmertTransformX, helmertTransformY, helmertTransformZ};
        this.helmertScale = helmertScale;
        this.helmertRotation = new double[]
        		{helmertRotationX, helmertRotationY, helmertRotationZ};
        this.ellipsoid = ellipsoid;
    }
    
    // Getters
    public String getName() { return name; }
	public double getScaleFactor() { return scaleFactor; }
	public double getTrueOriginPhi() { return trueOriginPhi; }
	public double getTrueOriginLambda() { return trueOriginLambda; }
	public double getTrueOriginEasting() { return trueOriginEasting; }
	public double getTrueOriginNorthing() { return trueOriginNorthing; }
	public double getHelmertTransformX() { return helmertTransform[0]; }
	public double getHelmertTransformY() { return helmertTransform[1]; }
	public double getHelmertTransformZ() { return helmertTransform[2]; }
	public double getHelmertScale() { return helmertScale; }
	public double getHelmertRotationX() { return helmertRotation[0]; }
	public double getHelmertRotationY() { return helmertRotation[1]; }
	public double getHelmertRotationZ() { return helmertRotation[2]; }
	public Ellipsoid getEllipsoid() { return ellipsoid; }
}
