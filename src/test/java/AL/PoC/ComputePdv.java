package AL.PoC;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComputePdv {
	@Test
	public void main() throws IOException{
		ServerModel s = new ServerModel();
		s.main(new String[]{"--computePDVs"});
		
		assertTrue(new File("/Users/Mortuel/Documents/TMP/DB_PDV/pdv.txt").exists());
	}
}
