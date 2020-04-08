# GridReferenceLookup
Convert Latitude/Longitude to UTM/Local Coordinates or Grid Reference

## Usage
Instantiate the object with latitude and longitude (decimal or degrees):
```
GridReferenceLookup lookup = new GridReferenceLookup(51.093237, 1.101837);
GridReferenceLookup lookup = new GridReferenceLookup(51, 5, 35.7, 'N', 1, 6, 6.6, 'E');
```

Get grid reference:
```
GridReference utm = lookup.getUTM();
GridReference gb = lookup.getGridReference(GridSystem.GB);
GridReference ie = lookup.getGridReference(GridSystem.IE);
```

Properties:
```
utm.getLatitude()        // Latitude (WGS84)
utm.getLongitude()       // Longitude (WGS84)
utm.getEasting()         // Easting 
utm.getNorthing()        // Northing
utm.getHemisphere()      // Hemisphere
utm.getZone()            // UTM Zone (only meaningful on UTM)
gb.getGridReference();   // Grid Reference (not on UTM)
ie.getGridReference();   // Grid Reference
utm.getGridSystem()      // Grid System used
```

Set new coordinates:
```
lookup.setLatitude(51.696608);
lookup.setLatitude(51, 41, 47.8, 'N');
lookup.setLongitude(-4.940054);
lookup.setLongitude(4, 56, 24.2, 'W');
```