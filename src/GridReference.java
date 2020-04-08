import java.util.Locale;

public class GridReference {
	
	// Fields
	// Grid System
	private final GridSystem gridSystem;
	
	// Easting / Northing Coordinates
	private final double easting;
	private final double northing;
	
	// Attributes relating to grid system
	private String gridReference = "";
	private final int zone;
	private String hemisphere;
	
	// Locale for String formatting
	private final Locale locale;
	
	// Constructors
	// Country-specific Constructor
	public GridReference(double easting, double northing, GridSystem gridSystem) {
		this.easting = easting;
		this.northing = northing;
		this.gridSystem = gridSystem;
		this.zone = 1;
		this.hemisphere = "";
		this.locale = Locale.getDefault();
		setGridReference();
	}
	
	// UTM Constructor
	public GridReference(double easting, double northing, GridSystem gridSystem, int zone, String hemisphere) {
		this.easting = easting;
		this.northing = northing;
		this.gridSystem = gridSystem;
		this.zone = zone;
		this.hemisphere = hemisphere;
		this.locale = Locale.getDefault();
		setGridReference();
	}
	
	// Private Methods
	// Get Grid Reference
	private void setGridReference() {
		String eastingString = String.format(locale, "%08d", (int) easting);
		String northingString = String.format(locale, "%08d", (int) northing);
		String code = String.format(locale, "%s%s", eastingString.substring(0, 3), northingString.substring(0, 3));
		String square = "";
		
		if (gridSystem == GridSystem.GB) {
			switch (code) {
	        case "000012": square = "HL"; break;
	        case "001012": square = "HM"; break;
	        case "002012": square = "HN"; break;
	        case "003012": square = "HO"; break;
	        case "004012": square = "HP"; break;
	        case "005012": square = "JL"; break;
	        case "006012": square = "JM"; break;
	        case "007012": square = "JN"; break;
	            
	        case "000011": square = "HQ"; break;
	        case "001011": square = "HR"; break;
	        case "002011": square = "HS"; break;
	        case "003011": square = "HT"; break;
	        case "004011": square = "HU"; break;
	        case "005011": square = "JQ"; break;
	        case "006011": square = "JR"; break;
	        case "007011": square = "JS"; break;
	            
	        case "000010": square = "HV"; break;
	        case "001010": square = "HW"; break;
	        case "002010": square = "HX"; break;
	        case "003010": square = "HY"; break;
	        case "004010": square = "HZ"; break;
	        case "005010": square = "JV"; break;
	        case "006010": square = "JW"; break;
	        case "007010": square = "JX"; break;
	            
	        case "000009": square = "NA"; break;
	        case "001009": square = "NB"; break;
	        case "002009": square = "NC"; break;
	        case "003009": square = "ND"; break;
	        case "004009": square = "NE"; break;
	        case "005009": square = "OA"; break;
	        case "006009": square = "OB"; break;
	        case "007009": square = "OC"; break;
	            
	        case "000008": square = "NF"; break;
	        case "001008": square = "NG"; break;
	        case "002008": square = "NH"; break;
	        case "003008": square = "NJ"; break;
	        case "004008": square = "NK"; break;
	        case "005008": square = "OF"; break;
	        case "006008": square = "OG"; break;
	        case "007008": square = "OH"; break;
	            
	        case "000007": square = "NL"; break;
	        case "001007": square = "NM"; break;
	        case "002007": square = "NN"; break;
	        case "003007": square = "NO"; break;
	        case "004007": square = "NP"; break;
	        case "005007": square = "OL"; break;
	        case "006007": square = "OM"; break;
	        case "007007": square = "ON"; break;
	            
	        case "000006": square = "NQ"; break;
	        case "001006": square = "NR"; break;
	        case "002006": square = "NS"; break;
	        case "003006": square = "NT"; break;
	        case "004006": square = "NU"; break;
	        case "005006": square = "OQ"; break;
	        case "006006": square = "OR"; break;
	        case "007006": square = "OS"; break;
	            
	        case "000005": square = "NV"; break;
	        case "001005": square = "NW"; break;
	        case "002005": square = "NX"; break;
	        case "003005": square = "NY"; break;
	        case "004005": square = "NZ"; break;
	        case "005005": square = "OV"; break;
	        case "006005": square = "OW"; break;
	        case "007005": square = "OX"; break;
	            
	        case "000004": square = "SA"; break;
	        case "001004": square = "SB"; break;
	        case "002004": square = "SC"; break;
	        case "003004": square = "SD"; break;
	        case "004004": square = "SE"; break;
	        case "005004": square = "TA"; break;
	        case "006004": square = "TB"; break;
	        case "007004": square = "TC"; break;
	            
	        case "000003": square = "SF"; break;
	        case "001003": square = "SG"; break;
	        case "002003": square = "SH"; break;
	        case "003003": square = "SJ"; break;
	        case "004003": square = "SK"; break;
	        case "005003": square = "TF"; break;
	        case "006003": square = "TG"; break;
	        case "007003": square = "TH"; break;
	            
	        case "000002": square = "SL"; break;
	        case "001002": square = "SM"; break;
	        case "002002": square = "SN"; break;
	        case "003002": square = "SO"; break;
	        case "004002": square = "SP"; break;
	        case "005002": square = "TL"; break;
	        case "006002": square = "TM"; break;
	        case "007002": square = "TN"; break;
	            
	        case "000001": square = "SQ"; break;
	        case "001001": square = "SR"; break;
	        case "002001": square = "SS"; break;
	        case "003001": square = "ST"; break;
	        case "004001": square = "SU"; break;
	        case "005001": square = "TQ"; break;
	        case "006001": square = "TR"; break;
	        case "007001": square = "TS"; break;
	            
	        case "000000": square = "SV"; break;
	        case "001000": square = "SW"; break;
	        case "002000": square = "SX"; break;
	        case "003000": square = "SY"; break;
	        case "004000": square = "SZ"; break;
	        case "005000": square = "TV"; break;
	        case "006000": square = "TW"; break;
	        case "007000": square = "TX"; break;
	            
	        default: square = ""; break;
	        }
		} else if (gridSystem == GridSystem.IE) {
			switch (code) {
			case "000004": square = "A"; break;
	        case "001004": square = "B"; break;
	        case "002004": square = "C"; break;
	        case "003004": square = "D"; break;
	        case "004004": square = "E"; break;
	        case "000003": square = "F"; break;
	        case "001003": square = "G"; break;
	        case "002003": square = "H"; break;
	        case "003003": square = "J"; break;
	        case "004003": square = "K"; break;
	        case "000002": square = "L"; break;
	        case "001002": square = "M"; break;
	        case "002002": square = "N"; break;
	        case "003002": square = "O"; break;
	        case "004002": square = "P"; break;
	        case "000001": square = "Q"; break;
	        case "001001": square = "R"; break;
	        case "002001": square = "S"; break;
	        case "003001": square = "T"; break;
	        case "004001": square = "U"; break;
	        case "000000": square = "V"; break;
	        case "001000": square = "W"; break;
	        case "002000": square = "X"; break;
	        case "003000": square = "Y"; break;
	        case "004000": square = "Z"; break;
	        default: square = ""; break;
			}
		}
		
		gridReference = String.format(locale, "%s%s%s", square, eastingString.substring(3, 6), northingString.substring(3, 6));
	}
	
	@Override
	public String toString() {
		String str = "";
		if (gridSystem == GridSystem.UTM) {
			str = String.format(locale, "%s, Zone: %d, Easting: %.0f, Northing: %.0f, %s", gridSystem.label, zone, easting, northing, hemisphere);
		} else {
			str = String.format(locale, "%s, Easting: %.0f, Northing: %.0f, Grid Reference: %s", gridSystem.label, easting, northing, gridReference);
		}
		return str;
	}
	
	// Public Methods
	public boolean isValid() {
		if (gridSystem == GridSystem.GB) {
			return gridReference != null &&
				   easting >= 0 && easting < 700000 &&
				   northing >= 0 && northing < 1300000;
		} else if (gridSystem == GridSystem.IE) {
			return gridReference != null &&
					   easting >= 0 && easting < 500000 &&
					   northing >= 0 && northing < 500000;
		} else {
			return zone > 0 && zone <= 60 && hemisphere != null &&
					   easting >= 0 && northing >= 0;
		}
	}
}
