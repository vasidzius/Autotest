import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.internal.MouseAction;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

/**
 * Created by VKov on 1/15/2016.
 */
public class MyTest {

    static WebDriver driver = new FirefoxDriver();
    @FindBy(css = ".suggest2-form__button")
    Button button;

    public MyTest(){}

    public MyTest(final WebDriver driver)
    {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), this);
        this.driver = driver;
    }


    public static void main(String...args) {

         driver.get("http://www.yandex.ru");

        MyTest e = new MyTest(driver);

        //WebElement input = driver.findElement(By.className("input__control input__input"));
        //WebElement input = driver.findElement(By.xpath("//*[contains(@class, 'input__control input__input')]"));
        //driver.findElement(By.className("search2__button")).findElement(By.className("button")).click();
        //driver.findElement(By.cssSelector(".button")).click();
        //Assert.assertTrue(input.isDisplayed());

        e.button.click();
    }

}
