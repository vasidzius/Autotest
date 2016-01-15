package HTMLElementsWorkExample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

/**
 * Created by VKov on 1/14/2016.
 */
public class MainPage {

    private WebDriver driver;

    //@FindBy(id = "text")
    @FindBy(css = ".suggest2-form__node")
    private SearchArrow searchArrow;

    public MainPage (final WebDriver driver)
    {

        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), this);


        this.driver = driver;
    }

    public SearchPage searchFor (String request)
    {
        this.searchArrow.searchFor(request);
        return new SearchPage(driver);

    }
}
