import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

   
    @Test
    public void trySubstance(){
    	Substance s1 = new Substance("C2H6",30, "Ethane").save();
    	s1.addProtein("Cytoplasm", "Cancer", "RRRRRR", "PGB").save();  
    	
    	assertEquals("C2H6", s1.molecularFormula);
    	assertEquals(30, s1.molecularWeight);

    	assertEquals("Cytoplasm", s1.targets.get(0).cellularLocation);
    	assertEquals("Cancer", s1.targets.get(0).associatedDisease);
    }
    
    @Before
    public void setup(){
    	Fixtures.deleteDatabase();
    }
}
