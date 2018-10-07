import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass mainClass = new MainClass();

    @Test
    public void testGetLocalNumber(){
        Assert.assertEquals("Values are not equals", mainClass.getLocalNumber(), 14);
    }

    @Test
    public void testGetClassNumber(){
        Assert.assertTrue("Method returned value <= 45", mainClass.getLocalNumber() > 45);
    }
}
