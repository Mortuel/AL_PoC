package AL.PoC;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreateGraph {
	@Test
	public void main() throws IOException{
		ServerModel s = new ServerModel();
		s.main(new String[]{"--createGraph"});
		
		assertTrue(true);
	}
}
