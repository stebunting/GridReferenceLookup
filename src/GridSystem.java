// Enum for defining available Grid Systems
enum GridSystem {
	UTM("UTM"),
	GB("UK"),
	IE("Ireland");
	
	public final String label;
	private GridSystem(String label) {
		this.label = label;
	}
}