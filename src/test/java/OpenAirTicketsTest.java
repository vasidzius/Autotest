import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.Assert.assertTrue;

import junit.framework.TestCase;

import java.time.Month;
import java.time.MonthDay;
import java.time.format.TextStyle;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Superuser on 14.12.2015.
 */
public class OpenAirTicketsTest extends TestCase {

    public void testMain() throws InterruptedException {

        WebDriver driver = new FirefoxDriver();

        workingWithFirstPage_PrimaryInformation(driver);
        workingWithSecondAndThirdPages_ChooseVariant(driver);

        //workingWithFourthPage_contactInformation


        WebElement stateEdit = new WebDriverWait(driver,15).until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@class='passengers-form onl_js-mainCont anc_js-mainCont state_edit']")));
        WebElement sex = stateEdit.findElement(By.xpath("//*[@id='passengerData0.passengerTitle']"));
        sex.click();


        //this block holds browser as open after actions
        try {
            driver.wait(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();

    }

    private void workingWithSecondAndThirdPages_ChooseVariant(WebDriver driver) throws InterruptedException {
        //sometimes the Service on third page says "The flight unavailable, plese ..."
        boolean flag = true;
        while(flag) {
            workingWithSecondPage(driver);
            try
            {
                WebElement error = new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content > div:nth-child(3) > a:nth-child(1)")));
                error.click();
            }
            catch(TimeoutException e)
            {
                flag = false;
            }
        }

        //again click on Third page
        WebElement buttonReserve = driver.findElement(By.xpath(".//*[@id='aazone.results']/div/div[1]//*[@class='airButton']"));
        buttonReserve.click();
    }

    private void workingWithSecondPage(WebDriver driver) throws InterruptedException {
        //choose random price-info
        List<WebElement> listPriceInfo = new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".price-info")));

        //driver.findElements(By.cssSelector(".price-info"));
        WebElement priceInfo = listPriceInfo.get((int)(Math.random() * (listPriceInfo.size()-1)));

        //choose random radio direction To
        List<WebElement> listRadioDirectionTo = priceInfo.findElements(By.cssSelector(".price-table:nth-of-type(3) .radio"));
        WebElement radioTo = listRadioDirectionTo.get((int)(Math.random() * (listRadioDirectionTo.size()-1)));
        radioTo.click();

        //choose random radio direction Back
        List<WebElement> listRadioDirectionBack = priceInfo.findElements(By.cssSelector(".price-table:nth-of-type(5) .radio"));
        WebElement radioFrom = listRadioDirectionBack.get((int)(Math.random() * (listRadioDirectionBack.size()-1)));
        radioFrom.click();

        Thread.sleep(2000);

        //click button Reservation
        //todo why next line doesnt work?
        //WebElement buttonRent = priceInfo.findElement(By.xpath("//*[@class='airButton']"));
        WebElement buttonReserve = priceInfo.findElement(By.cssSelector(".airButton"));
        buttonReserve.click();
    }

    private void workingWithFirstPage_PrimaryInformation(WebDriver driver) throws InterruptedException {
        driver.get("http://www.airtickets.ru/bileti-aeroflot?gclid=CNzJ_-DQw8UCFcL3cgodeKwA0w");

        setDirectionFrom(driver, "Санкт");

        experimentWIthRadioButtomOnlyOneSideFlightOrToAndBack(driver);

        setDirectionTo(driver, "Москв");

        checkMonthAndDay(driver);

        checkAmountOfChildren(driver, 0);

        checkAmountOfAdult(driver, 1);

        //go to next page via button "Search"
        WebElement searchButton = driver.findElement(By.cssSelector("div.flight_selector_bottom_right_search > div"));
        searchButton.click();
    }

    private void checkAmountOfAdult(WebDriver driver, int amount) {
        int amountOfAdult = Integer.parseInt(driver.findElement(By.cssSelector("div.mainPageOverride:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)")).getText());
        assertTrue("Amount of adult is set by default to 1", amountOfAdult == amount);
    }

    private void checkAmountOfChildren(WebDriver driver, int amount) {
        WebElement amountOfChildren = driver.findElement(By.cssSelector("div.mainPageOverride:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        assertTrue("Amount of Children is set by default to 0", Integer.parseInt(amountOfChildren.getText())==amount);
        WebElement amountOfBabies = driver.findElement(By.xpath(".//*[@id='INF']/div/div"));
        assertTrue("Amount of Babies is set by default to 0", Integer.parseInt(amountOfBabies.getText())==amount);
    }

    private void checkMonthAndDay(WebDriver driver) {
        //test for current month and correct day of month
        //direction TO
        WebElement dateMonthDirectionTo = driver.findElement(By.cssSelector(".flight_selector_middle_departure > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        //.flight_selector_middle_departure > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)
        assertTrue(getMonth().equals(dateMonthDirectionTo.getText()));
        WebElement dayOfMonthDirectionTo = driver.findElement(By.cssSelector(".flight_selector_middle_departure > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        assertTrue(Integer.parseInt(dayOfMonthDirectionTo.getText()) >= getDayOfMonth());
        //direction BACK
        WebElement dateMonthDirectionBack = driver.findElement(By.cssSelector(".flight_selector_middle_return > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        assertTrue(getMonth().equals(dateMonthDirectionBack.getText()));
        WebElement dayOfMonthDirectionBack = driver.findElement(By.cssSelector(".flight_selector_middle_return > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        assertTrue(Integer.parseInt(dayOfMonthDirectionBack.getText()) > Integer.parseInt(dayOfMonthDirectionTo.getText()));
    }

    private void setDirectionTo(WebDriver driver, String nameOfAirPort) {
        WebElement directionTo = driver.findElement(By.cssSelector("#to"));
        directionTo.click();
        directionTo.sendKeys(nameOfAirPort);
        WebElement dropListDirectionTo = new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#autocomplete > ul:nth-child(1) > li:nth-child(1) > b:nth-child(1) > div:nth-child(1)")));
        //#autocomplete > ul:nth-child(1) > li:nth-child(1) > b:nth-child(1) > div:nth-child(1)
        dropListDirectionTo.click();
        assertEquals("City destination was set correct", "MOW - Москва - все аэропорты",directionTo.getAttribute("value"));

    }

    private void experimentWIthRadioButtomOnlyOneSideFlightOrToAndBack(WebDriver driver) throws InterruptedException {
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
    }

    private void setDirectionFrom(WebDriver driver, String nameOfAirPort) {
        //wait until page is loading
        //WebElement directionFrom = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#from")));
        WebElement directionFrom = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("from")));
        directionFrom.click();
        directionFrom.sendKeys(nameOfAirPort);
        //todo experemnt with independense form of CSS selector
        WebElement dropListDirectionFrom = new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#autocomplete > ul:nth-child(1) > li:nth-child(1) > b:nth-child(1) > div:nth-child(1)")));
        dropListDirectionFrom.click();
        assertEquals("City departure was set correct", "LED - Санкт-Петербург  - все аэропорты",directionFrom.getAttribute("value"));
    }

    private String getMonth()
    {
        Month month = Month.of(new GregorianCalendar().getTime().getMonth() + 1);
        Locale loc = Locale.forLanguageTag("ru");

        return month.getDisplayName(TextStyle.FULL_STANDALONE, loc);
    }

    private int getDayOfMonth()
    {
        return MonthDay.now().getDayOfMonth();
    }
}
