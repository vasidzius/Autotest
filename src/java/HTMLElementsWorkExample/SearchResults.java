package HTMLElementsWorkExample;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

/**
 * Created by VKov on 1/14/2016.
 */
public class SearchResults extends HtmlElement {

    @SuppressWarnings("unused")
    @FindBy(className="serp-item__title")
    private List<WebElement> searchItems;

    public List<WebElement> getSearchItems() {
        return searchItems;
    }
}
