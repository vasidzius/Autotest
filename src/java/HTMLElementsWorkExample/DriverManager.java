package HTMLElementsWorkExample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by VKov on 1/15/2016.
 */
public class DriverManager {


    WebDriver driver = new FirefoxDriver();

    public WebDriver getDriver() {
        return driver;
    }


}
