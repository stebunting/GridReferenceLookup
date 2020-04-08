import java.util.Locale;

public class GridReferenceLookup {

	// Properties
	// WGS84 Latitude
	private final double latitude;
	
	// WGS84 Latitude
	private final double longitude;

	// WGS84 Cartesian Coordinates
	private final CartesianCoordinates cartesianCoordinates;
	
	private final Locale locale;
	
	// Initialisers
	public GridReferenceLookup(double latitude, double longitude) {
		this.locale = Locale.getDefault();
		this.latitude = latitude;
		this.longitude = longitude;
		this.cartesianCoordinates = geodeticToECEF(EllipsoidName.WGS84);
	}
	
	public GridReferenceLookup(int latDegrees, int latMinutes, double latSeconds, char latDirection,
			int lngDegrees, int lngMinutes, double lngSeconds, char lngDirection) {
		this.locale = Locale.getDefault();	
		this.latitude = toDecimal(latDegrees, latMinutes, latSeconds, latDirection);
		this.longitude = toDecimal(lngDegrees, lngMinutes, lngSeconds, lngDirection);
		this.cartesianCoordinates = geodeticToECEF(EllipsoidName.WGS84);
	}
	
	// Private Transformation Methods
	// Convert Latitude/Longitude to Cartesian Coordinates
    private CartesianCoordinates geodeticToECEF(EllipsoidName ellipsoidName) {
        
        // φ - latitude in radians
        double phi = degreesToRadians(this.latitude);
    
        // λ - longitude in radians
        double lambda = degreesToRadians(this.longitude);
        
        // Height
        double h = 0.0;
        
        // Datum Constants
        Ellipsoid ellipsoid = Constants.ellipsoids.get(ellipsoidName);
        double a = ellipsoid.getEquatorialRadius();
        double b = ellipsoid.getPolarRadius();
        double eSq = ellipsoid.getEccentricitySquared();
        
        // N(φ) Prime vertical radius of curvature
        double N = a / Math.sqrt(1.0 - eSq * Math.pow(Math.sin(phi), 2.0));
        
        // ECEF Coordinates
        double x = (N + h) * Math.cos(phi) * Math.cos(lambda);
        double y = (N + h) * Math.cos(phi) * Math.sin(lambda);
        double z = (N * Math.pow(b, 2.0) / Math.pow(a, 2.0) + h) * Math.sin(phi);
        
        return new CartesianCoordinates(x, y, z);
    }
    
    // Perform Helmert Transformation on Cartesian Coordinates
    private CartesianCoordinates helmertTransformation(DatumName datumName) {
    	
    	// Coordinates
    	double x1 = cartesianCoordinates.getX();
    	double y1 = cartesianCoordinates.getY();
    	double z1 = cartesianCoordinates.getZ();
    	
    	// Transformation
        Datum datum = Constants.datums.get(datumName);
        double cx = datum.getHelmertTransformX();
        double cy = datum.getHelmertTransformY();
        double cz = datum.getHelmertTransformZ();
        
        // Scale Factor
        double s = datum.getHelmertScale() / 1000000;
                
	    // Rotations - converted from seconds to radians
	    double rx = secondsToRadians(datum.getHelmertRotationX());
	    double ry = secondsToRadians(datum.getHelmertRotationY());
	    double rz = secondsToRadians(datum.getHelmertRotationZ());
        
        // Calculations
        double x2 = cx + (1 + s) * (x1 - rz * y1 + ry * z1);
        double y2 = cy + (1 + s) * (y1 + rz * x1 - rx * z1);
        double z2 = cz + (1 + s) * (z1 - ry * x1 + rx * y1);
    	
        return new CartesianCoordinates(x2, y2, z2);
    }
    
    // Convert Cartesian Coordinates to Latitude/Longitude
    private double[] ECEFToGeodetic(CartesianCoordinates transformedCoordinates, DatumName datumName) {
        
        // Cartesian Coordinates      
        double x = transformedCoordinates.getX();
        double y = transformedCoordinates.getY();
        double z = transformedCoordinates.getZ();
        
        // Datum Constants
        Ellipsoid ellipsoid = Constants.datums.get(datumName).getEllipsoid();
        double a = ellipsoid.getEquatorialRadius();
        double eSq = ellipsoid.getEccentricitySquared();
        
        double p = Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0));
        
        // φ calculations
        double oldPhi;
        double N;
        double phi = Math.atan(z / (p * (1.0 - eSq)));
        do {
            oldPhi = phi;
            N = a / Math.sqrt(1.0 - eSq * Math.pow(Math.sin(oldPhi), 2.0));
            phi = Math.atan((z + (eSq * N * Math.sin(oldPhi))) / p);
        } while (Math.abs(oldPhi - phi) < 0.0000000000001);
        
        // Calculations
        double lambda = Math.atan(y / x);
        double H = (p / Math.cos(phi)) - N;
        
        return new double[] {radiansToDegrees(phi), radiansToDegrees(lambda), H};
    }
    
    // Convert Latitude/Longitude to Easting/Northing
    private double[] geodeticToEastingsNorthings(double[] coordinates, DatumName datumName) {
        
    	// Coordinates
        double phi = degreesToRadians(coordinates[0]);
        double lambda = degreesToRadians(coordinates[1]);
     
        // Datum Constants
        Datum datum = Constants.datums.get(datumName);
        Ellipsoid ellipsoid = datum.getEllipsoid();
        double a = ellipsoid.getEquatorialRadius();
        double b = ellipsoid.getPolarRadius();
        double eSq = ellipsoid.getEccentricitySquared();
        double phi0 = degreesToRadians(datum.getTrueOriginPhi());
        double lambda0 = degreesToRadians(datum.getTrueOriginLambda());
        double N0 = datum.getTrueOriginNorthing();
        double E0 = datum.getTrueOriginEasting();
        double f0 = datum.getScaleFactor();
        
        // Intermediate Calculations
        double n = (a - b) / (a + b);
        double v = (a * f0) * Math.pow(1.0 - (eSq * Math.pow(Math.sin(phi), 2)), -0.5);
        double p = (a * f0) * (1.0 - eSq) * Math.pow(1.0 - (eSq * Math.pow(Math.sin(phi), 2.0)), -1.5);
        double nSq = (v / p) - 1.0;
        
        // Northing
        double M = (1 + n + (5 / 4.0 * Math.pow(n, 2.0)) + (5 / 4.0 * Math.pow(n, 3.0))) * (phi - phi0);
        M = M - ((3 * n + 3 * Math.pow(n, 2.0) + 21 / 8.0 * Math.pow(n, 3.0)) * Math.sin(phi - phi0) * Math.cos(phi + phi0));
        M = M + (((15 / 8.0 * Math.pow(n, 2.0)) + (15 / 8.0 * Math.pow(n, 3.0))) * Math.sin(2 * (phi - phi0)) * Math.cos(2 * (phi + phi0)));
        M = M - (35 / 24.0 * Math.pow(n, 3.0) * Math.sin(3 * (phi - phi0)) * Math.cos(3 * (phi + phi0)));
        M = b * f0 * M;
        double I = M + N0;
        double II = v / 2.0 * Math.sin(phi) * Math.cos(phi);
        double III = v / 24.0 * Math.sin(phi) * Math.pow(Math.cos(phi), 3.0) * (5.0 - Math.pow(Math.tan(phi), 2.0) + 9.0 * nSq);
        double IIIA = v / 720.0 * Math.sin(phi) * Math.pow(Math.cos(phi), 5.0) * (61.0 - 58.0 * Math.pow(Math.tan(phi), 2.0) + Math.pow(Math.tan(phi), 4.0));
        double northing = I + II * Math.pow(lambda - lambda0, 2.0) + III * Math.pow(lambda - lambda0, 4.0) + IIIA * Math.pow(lambda - lambda0, 6.0);
     
        // Easting
        double IV = v * Math.cos(phi);
        double V = v / 6.0 * Math.pow(Math.cos(phi), 3.0) * (v / p - Math.pow(Math.tan(phi), 2.0));
        double VI = v / 120.0 * Math.pow(Math.cos(phi), 5.0) * (5.0 - 18.0 * Math.pow(Math.tan(phi), 2.0) + Math.pow(Math.tan(phi), 4.0) + 14.0 * nSq - 58.0 * (Math.pow(Math.tan(phi), 2.0) * nSq));
        double easting = E0 + IV * (lambda - lambda0) + V * Math.pow(lambda - lambda0, 3.0) + VI * Math.pow(lambda - lambda0, 5.0);
        
        return new double[] {easting, northing};
    }
	
	// Helper Methods
    // Convert Latitude/Longitude in Degrees/Minutes/Seconds to Decimal
	private Double toDecimal(int degrees, int minutes, double seconds, char direction) {
		
		// Check direction is valid
		direction = Character.toUpperCase(direction);
		if (direction != 'N' && direction != 'S' && direction != 'W' && direction != 'E') {
			return null;
		}
		
		// Add multiplier if decimal coordinate is negative
        double multiplier = 1.0;
        if (direction == 'W' || direction == 'S') {
            multiplier = -1.0;
        }
        
        return multiplier * (degrees + minutes / 60.0 + seconds / 3600.0);
    }
	
	// Convert Degrees to Radians
    private double degreesToRadians(double degrees) {
    	return degrees * Math.PI / 180;
    }
    
    // Convert Seconds to Radians
    private double secondsToRadians(double seconds) {
    	return degreesToRadians(seconds) / 3600;
    }
    
    // Convert Radians to Degrees
    private double radiansToDegrees(double radians) {
    	return radians * 180 / Math.PI;
    }
    
    // Sequence of transforms to convert Cartesian Coordinates 
    // Returns local easting/northing and grid reference
    private GridReference transform(DatumName projection, GridReference.Country country) {
    	CartesianCoordinates transformedCoordinates = helmertTransformation(projection);
    	double[] transformedLatLon = ECEFToGeodetic(transformedCoordinates, projection);
    	double[] eastingNorthing = geodeticToEastingsNorthings(transformedLatLon, projection);
    	GridReference gridReference = new GridReference(eastingNorthing[0], eastingNorthing[1], country);
    	
    	if (gridReference.isValid()) {
    		return gridReference;
    	} else {
    		return null;
    	}
    }
    
    @Override
    public String toString() {
    	return String.format(locale, "(%.6f, %.6f)", latitude, longitude);
    }
    // Public Methods
    public GridReference getUK() {
    	return transform(DatumName.NATIONAL_GRID, GridReference.Country.GB);
    }
    
    public GridReference getIreland() {
    	return transform(DatumName.IRISH_NATIONAL_GRID, GridReference.Country.IE);
    }
    
    public void getUTM() {
    	int zone = 1 + (int) ((longitude + 180) / 6);
    	
    	String hemisphere = "Northern Hemisphere";
    	DatumName datumName = DatumName.UTM_NORTH;
    	if (latitude < 0) {
    		datumName = DatumName.UTM_SOUTH;
    		hemisphere = "Southern Hemisphere";
    	}
    	
    	double[] latLon = new double[] {latitude, longitude + ((30 - zone) * 6)};
    	double[] eastingNorthing = geodeticToEastingsNorthings(latLon, datumName);
    	
    	System.out.println((int) eastingNorthing[0]);
    	System.out.println((int) eastingNorthing[1]);
    	System.out.println(zone);
    	System.out.println(hemisphere);
    }
    
	// Getters
	public double getLatitude() { return latitude; }
	public double getLongitude() { return longitude; }
	
	// Test
	public static void main(String[] args) {
		double[] thelizard = {49.971641, -5.203509};
		double[] wimbledon = {51.427621, -0.190793};
		double[] enniskillen = {54.344367, -7.633891};
		
		double[] location = {54.344367, -19.633891};
		GridReferenceLookup lookup = new GridReferenceLookup(location[0], location[1]);
		
		System.out.println(lookup);
		lookup.getUTM();
	}

}