import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Superuser on 14.12.2015.
 */
public class TestOpenAirTickets {

    public static void main(String...args) {

        WebDriver driver = new FirefoxDriver();

        driver.get("http://www.airtickets.ru/bileti-aeroflot?gclid=CNzJ_-DQw8UCFcL3cgodeKwA0w");
        //driver.get("https://www.google.ru");
        //wait until page is loaded
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //todo why the command "By.id("#from") doesn't work? allways "NoSuchElementException"

        WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#from")));
        //WebElement element = driver.findElement(By.cssSelector("#from"));
        //WebElement element = driver.findElement(By.id("#googleMethodItem-0-0"));

        element.click();
        element.sendKeys("Санкт");


        //this block holds browser as open after actions
        try {
            driver.wait(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();

    }
}
