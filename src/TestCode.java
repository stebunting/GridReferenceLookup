
public class TestCode {

	public static void main(String[] args) {
		
		// UK
		double[] thelizard = {49.971641, -5.203509};
		double[] wimbledon = {51.427621, -0.190793};
		double[] shetlandislands = {60.419666, -1.393972};
		double[] aberdeen = {57.152861, -2.112434};
		double[] isleofman = {54.317965, -4.384772};
		double[] pembroke = {51.696608, -4.940054};
		double[] folkestone = {51.093237, 1.101837};
		double[] isleofwight = {50.659118, -1.254439};
		double[] islesofscilly = {49.918094, -6.298498};
		double[] westernislesofscilly = {49.949275, -6.355056};
		double[] galway = {53.271629, -9.061196};
		
		// Ireland
		double[] enniskillen = {54.344367, -7.633891};
		double[] belfast = {54.574931, -5.983254};
		double[] cork = {51.622467, -8.891433};
		
		// Netherlands
		double[] groningen = {53.21484, 6.569683};
		double[] rotterdam = {51.920239, 4.450462};
		double[] venlo = {51.393114, 6.179330};
		
		double[] location = venlo;
		GridReferenceLookup lookup = new GridReferenceLookup(location[0], location[1]);
		
		System.out.println(lookup.getGridReference(GridSystem.UTM));
		System.out.println(lookup.getUTM(32));
	}
}