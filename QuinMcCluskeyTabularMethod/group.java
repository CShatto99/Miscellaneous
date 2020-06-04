public class Group {
	public String decimal;
	public String binary;
	public boolean grouped;
	
	public Group(String dec, String bin) {
		decimal = dec;
		binary = bin;
		grouped = false;
	}
	
	public void displayGroup() {
		System.out.println(decimal + " | " + binary);
	}
}
