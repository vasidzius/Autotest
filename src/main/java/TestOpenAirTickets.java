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


    public static void main(String...args) throws InterruptedException {

        WebDriver driver = new FirefoxDriver();

        driver.get("http://www.airtickets.ru/bileti-aeroflot?gclid=CNzJ_-DQw8UCFcL3cgodeKwA0w");
        //driver.get("https://www.google.ru");
        //wait until page is loaded
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //WebElement element = driver.findElement(By.cssSelector("#from"));
        //WebElement element = driver.findElement(By.id("#googleMethodItem-0-0"));

        //todo why the command "By.id("#from") doesn't work? allways "NoSuchElementException"

        WebElement directionFrom = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#from")));
        directionFrom.click();
        directionFrom.sendKeys("Санкт");
        //todo experemnt with independense form of CSS selector
        WebElement dropListDirectionFrom = new WebDriverWait(driver,2).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#autocomplete > ul:nth-child(1) > li:nth-child(1) > b:nth-child(1) > div:nth-child(1)")));
        dropListDirectionFrom.click();

        WebElement directionTo = driver.findElement(By.cssSelector("#to"));
        directionTo.click();
        directionTo.sendKeys("Москв");
        WebElement dropListDirectionTo = new WebDriverWait(driver,2).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#autocomplete > ul:nth-child(1) > li:nth-child(1) > b:nth-child(1) > div:nth-child(1)")));
        //#autocomplete > ul:nth-child(1) > li:nth-child(1) > b:nth-child(1) > div:nth-child(1)
        dropListDirectionTo.click();

        WebElement flightToAsTry = driver.findElement(By.xpath(".//*[@id='1']/div[2]"));
        //todo what's wrong with this query to class? WebElement flightToAsTry = driver.findElement(By.xpath(".firepath-matching-node"));
        //.flight_selector_top_contain > div:nth-child(3) > div:nth-child(2) > div:nth-child(1)
        //.firepath-matching-node
        flightToAsTry.click();

        Thread.sleep(2000);

        WebElement flightToAndBack = driver.findElement(By.cssSelector(".flight_selector_top_contain > div:nth-child(2) > div:nth-child(3)"));
        //.flight_selector_top_contain > div:nth-child(2) > div:nth-child(3)
        //.flight_selector_top_contain > div:nth-child(3) > div:nth-child(2) > div:nth-child(1)
        //.firepath-matching-node
        flightToAndBack.click();

        WebElement dataMonthDirectionTo = driver.findElement(By.cssSelector(".flight_selector_middle_departure > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));





        //this block holds browser as open after actions
        try {
            driver.wait(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();

    }
}
