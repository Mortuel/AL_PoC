package AL.PoC;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;

public class DeleteFiles {

	@Test
	public void deleteFiles() {
		new File("/Users/Mortuel/Documents/TMP/DB_Couloirs/couloirs.txt").delete();
		new File("/Users/Mortuel/Documents/TMP/DB_PDV/pdv.txt").delete();
		new File("/Users/Mortuel/Documents/TMP/DB_Logs/logs.txt").delete();
		
		assertFalse(new File("/Users/Mortuel/Documents/TMP/DB_Couloirs/couloirs.txt").exists());
		assertFalse(new File("/Users/Mortuel/Documents/TMP/DB_PDV/pdv.txt").exists());
		assertFalse(new File("/Users/Mortuel/Documents/TMP/DB_Logs/logs.txt").exists());
	}
}
