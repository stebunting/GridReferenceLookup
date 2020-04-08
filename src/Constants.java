import java.util.HashMap;
import java.util.Map;

// Define required Constants
public class Constants {
	
	// Constants
	public final static Map<EllipsoidName, Ellipsoid> ellipsoids = new HashMap<EllipsoidName, Ellipsoid>();
	public final static Map<DatumName, Datum> datums = new HashMap<DatumName, Datum>();
	
	static {
		
		// Define Ellipsoids
		ellipsoids.put(EllipsoidName.WGS84, new Ellipsoid("WGS84", 6378137.0, 6356752.314245));
		ellipsoids.put(EllipsoidName.GRS80, new Ellipsoid("GRS80", 6378137.0, 6356752.3141));
		ellipsoids.put(EllipsoidName.AIRY1830, new Ellipsoid("Airy 1830", 6377563.396, 6356256.909));
		ellipsoids.put(EllipsoidName.AIRY1830_MODIFIED, new Ellipsoid("Airy 1830 Modified", 6377340.189, 6356034.447));
		
		// Define Datums
		datums.put(DatumName.NATIONAL_GRID, new Datum(
				"Ordnance Survey National Grid", 0.9996012717,
				49, -2, 400000, -100000,
				-446.448, 125.157, -542.06, 20.4894, -0.1502, -0.247, -0.8421,
				ellipsoids.get(EllipsoidName.AIRY1830)));
		
		datums.put(DatumName.IRISH_NATIONAL_GRID, new Datum(
				"Irish National Grid", 1.000035,
				53.5, -8, 200000, 250000,
				-482.53, 130.596, -564.557, -8.15, 1.042, 0.214, 0.631,
				ellipsoids.get(EllipsoidName.AIRY1830_MODIFIED)));
		
		datums.put(DatumName.UTM_NORTH, new Datum(
				"UTM Northern Hemisphere", 0.9996,
				0, -3, 500000, 0, 0, 0, 0, 0, 0, 0, 0,
				ellipsoids.get(EllipsoidName.WGS84)));
		
		datums.put(DatumName.UTM_SOUTH, new Datum(
				"UTM Southern Hemisphere", 0.9996,
				0, -3, 500000, 10000000, 0, 0, 0, 0, 0, 0, 0,
				ellipsoids.get(EllipsoidName.WGS84)));
	}
}
