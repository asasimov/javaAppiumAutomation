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
        Assert.assertTrue("Method returned value <= 45", mainClass.getClassNumber() > 45);
    }

    @Test
    public void testGetClassString(){
        String actual = mainClass.getClassString();
        Assert.assertTrue("Substring not found", actual.contains("hello") || actual.contains("Hello"));
    }
}
