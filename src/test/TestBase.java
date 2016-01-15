import HTMLElementsWorkExample.DriverManager;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

/**
 * Created by VKov on 1/14/2016.
 */
public class TestBase {

    private DriverManager manager = new DriverManager();
    protected WebDriver baseDriver;

    @Before
    public void initializeDriver() {
        baseDriver = manager.getDriver();
    }
}
