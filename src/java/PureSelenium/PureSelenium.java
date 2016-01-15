package PureSelenium;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

import java.time.Month;
import java.time.MonthDay;
import java.time.format.TextStyle;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;

/**
 * Created by Superuser on 14.12.2015.
 */
public class PureSelenium{

    private static final int TIMERforThreadSleep = 0;
    private final int TIMERforWebDriverWait = 3;

    @Test
    public void testMain() throws InterruptedException {

        WebDriver driver = new FirefoxDriver();

        //rarely before 4th page of contacts information windows appears with message "Information is old, make new request"
        boolean flag = true;
        while(flag) {
            workingWithFirstPage_PrimaryInformation(driver);
            workingWithSecondAndThirdPages_ChooseVariant(driver);
            try {
                WebElement error = new WebDriverWait(driver,TIMERforWebDriverWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".timeExpiredCntBtn")));
                error.click();
            }
            catch (TimeoutException e)
            {
                flag = false;
            }
        }

        workingWithFourthPage_contactInformation(driver);

        workingWithFifthPage_payment(driver);

        Thread.sleep(2000);
        driver.close();

    }

    private void workingWithFifthPage_payment(WebDriver driver) {
        new WebDriverWait(driver,TIMERforWebDriverWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#paymentDetails\\.creditCardDetails\\.type > option[value='VISA']"))).click();
        driver.findElement(By.id("paymentDetails.creditCardDetails.number")).sendKeys("4554123443214567");
        driver.findElement(By.id("ed2")).sendKeys("2017");
        driver.findElement(By.id("paymentDetails.creditCardDetails.securityCode")).sendKeys("621");
        Assert.assertTrue(driver.findElement(By.cssSelector("#btnBuyTicket .airButton")).isDisplayed());
    }

    private void workingWithFourthPage_contactInformation(WebDriver driver) {
        WebElement sex = new WebDriverWait(driver,TIMERforWebDriverWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#passengerData0\\.passengerTitle")));
        sex.click();
        sex = sex.findElement(By.cssSelector(" option[value='Mr']"));
        sex.click();

        WebElement lastName = driver.findElement(By.cssSelector("#passengerData0\\.lastName"));
        lastName.sendKeys("Ivanov");
        WebElement firstName = driver.findElement(By.cssSelector("#passengerData0\\.firstName"));
        firstName.sendKeys("Ivan");
        //birth date
        driver.findElement(By.cssSelector("#pd0")).findElement(By.cssSelector(" option[value='7']")).click();
        driver.findElement(By.cssSelector("#pm0 > option[value='5']")).click();
        driver.findElement(By.cssSelector("#py0")).sendKeys("1976");
        //other information
        String nationalityOfPassenger = driver.findElement(By.cssSelector("#passengerData0\\.nationality > option[selected='selected']")).getText();
        Assert.assertEquals("Passenger Nationality is RF", "Российская Федерация", nationalityOfPassenger);
        driver.findElement(By.id("passengerData0.documentNumber")).sendKeys("4006 123456");
        driver.findElement(By.cssSelector("#pey0")).sendKeys("2018");
        //contact information, next table
        driver.findElement(By.id("contactDetails.address.city")).sendKeys("Saint-Petersburg");
        driver.findElement(By.id("cdmpn2")).sendKeys("9056968704");
        driver.findElement(By.id("contactDetails.emailAddress")).sendKeys("ivan@gmail.com");
        //button "Next"
        driver.findElement(By.className("airButton")).click();
    }

    private void workingWithSecondAndThirdPages_ChooseVariant(WebDriver driver) throws InterruptedException {
        //sometimes the Service on third page says "The flight unavailable, please ..."
        boolean flag = true;
        while(flag) {
            workingWithSecondPage(driver);
            try
            {
                WebElement error = new WebDriverWait(driver,TIMERforWebDriverWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content > div:nth-child(3) > a:nth-child(1)")));
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
        List<WebElement> listPriceInfo = new WebDriverWait(driver,TIMERforWebDriverWait).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".price-info")));

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

        Thread.sleep(TIMERforThreadSleep);

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
        Assert.assertTrue("Amount of adult is set by default to 1", amountOfAdult == amount);
    }

    private void checkAmountOfChildren(WebDriver driver, int amount) {
        WebElement amountOfChildren = driver.findElement(By.cssSelector("div.mainPageOverride:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        Assert.assertTrue("Amount of Children is set by default to 0", Integer.parseInt(amountOfChildren.getText())==amount);
        WebElement amountOfBabies = driver.findElement(By.xpath(".//*[@id='INF']/div/div"));
        Assert.assertTrue("Amount of Babies is set by default to 0", Integer.parseInt(amountOfBabies.getText())==amount);
    }

    private void checkMonthAndDay(WebDriver driver) {
        //test for current month and correct day of month
        //direction TO
        WebElement dateMonthDirectionTo = driver.findElement(By.cssSelector(".flight_selector_middle_departure > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        Assert.assertTrue(getMonth().equals(dateMonthDirectionTo.getText()));
        WebElement dayOfMonthDirectionTo = driver.findElement(By.cssSelector(".flight_selector_middle_departure > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        Assert.assertTrue(Integer.parseInt(dayOfMonthDirectionTo.getText()) >= getDayOfMonth());
        //direction BACK
        WebElement dateMonthDirectionBack = driver.findElement(By.cssSelector(".flight_selector_middle_return > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        Assert.assertTrue(getMonth().equals(dateMonthDirectionBack.getText()));
        WebElement dayOfMonthDirectionBack = driver.findElement(By.cssSelector(".flight_selector_middle_return > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"));
        Assert.assertTrue(Integer.parseInt(dayOfMonthDirectionBack.getText()) > Integer.parseInt(dayOfMonthDirectionTo.getText()));
    }

    private void setDirectionTo(WebDriver driver, String nameOfAirPort) {
        WebElement directionTo = driver.findElement(By.cssSelector("#to"));
        directionTo.click();
        directionTo.sendKeys(nameOfAirPort);
        WebElement dropListDirectionTo = new WebDriverWait(driver,TIMERforWebDriverWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#autocomplete > ul:nth-child(1) > li:nth-child(1) > b:nth-child(1) > div:nth-child(1)")));
        dropListDirectionTo.click();
        Assert.assertEquals("City destination was set correct", "MOW - Москва - все аэропорты",directionTo.getAttribute("value"));

    }

    private void experimentWIthRadioButtomOnlyOneSideFlightOrToAndBack(WebDriver driver) throws InterruptedException {
        WebElement flightToAsTry = driver.findElement(By.xpath(".//*[@id='1']/div[2]"));
        flightToAsTry.click();

        Thread.sleep(TIMERforThreadSleep);

        WebElement flightToAndBack = driver.findElement(By.cssSelector(".flight_selector_top_contain > div:nth-child(2) > div:nth-child(3)"));
        //.flight_selector_top_contain > div:nth-child(2) > div:nth-child(3)
        //.flight_selector_top_contain > div:nth-child(3) > div:nth-child(2) > div:nth-child(1)
        //.firepath-matching-node
        flightToAndBack.click();
    }

    private void setDirectionFrom(WebDriver driver, String nameOfAirPort) {
        //wait until page is loading
        //WebElement directionFrom = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#from")));
        WebElement directionFrom = (new WebDriverWait(driver, TIMERforWebDriverWait)).until(ExpectedConditions.presenceOfElementLocated(By.id("from")));
        directionFrom.click();
        directionFrom.sendKeys(nameOfAirPort);
        //todo experemnt with independense form of CSS selector
        WebElement dropListDirectionFrom = new WebDriverWait(driver,TIMERforWebDriverWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#autocomplete > ul:nth-child(1) > li:nth-child(1) > b:nth-child(1) > div:nth-child(1)")));
        dropListDirectionFrom.click();
        Assert.assertEquals("City departure was set correct", "LED - Санкт-Петербург  - все аэропорты",directionFrom.getAttribute("value"));
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
