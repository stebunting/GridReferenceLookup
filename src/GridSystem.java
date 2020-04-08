// Enum for defining available Grid Systems
enum GridSystem {
	UTM("UTM"),
	GB("UK"),
	IE("Ireland");
	
	private final String label;
	
	private GridSystem(String label) {
		this.label = label;
	}
	
	public String getLabel() { return label; }
}