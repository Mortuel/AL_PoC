package AL.PoC;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class CopyInformations {
	@Test
	public void main() throws IOException{
		ServerModel s = new ServerModel();
		s.main(new String[]{"--copyInformations", "/Users/Mortuel/Documents/TMP/PlanACharger/Couloirs1.txt"});
		
		assertTrue(new File("/Users/Mortuel/Documents/TMP/DB_Couloirs/couloirs.txt").exists());
	}
}
