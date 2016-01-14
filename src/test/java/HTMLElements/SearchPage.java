package HTMLElements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.pagefactory.CustomElementLocatorFactory;

/**
 * Created by VKov on 1/14/2016.
 */
public class SearchPage {

    private WebDriver driver;

    @FindBy(className="content__left")
    private SearchResults searchResults;

    @FindBy(id = "text")
    private SearchArrow searchArrow;

    public SearchPage(WebDriver driver)
    {
        PageFactory.initElements(new HtmlElementDecorator((CustomElementLocatorFactory) driver), this);
        this.driver = driver;
    }

    public SearchPage searchFor(String request) {
        this.searchArrow.searchFor(request);
        return this;
    }

    public SearchResults getSearchResults() {
        return this.searchResults;
    }


}
