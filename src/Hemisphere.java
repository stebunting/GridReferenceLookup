// Enum to define hemispheres
enum Hemisphere {
	NORTH("Northern Hemisphere"),
	SOUTH("Southern Hemisphere");
	
	private final String label;
	
	private Hemisphere(String label) {
		this.label = label;
	}
	
	public String getLabel() { return label; }
}