package HTMLElements;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

/**
 * Created by VKov on 1/14/2016.
 */
public class SearchArrow extends HtmlElement {
    @FindBy(id = "text")
    public TextInput requestInput;

    @FindBy(className="button suggest2-form__button button_theme_websearch button_size_m i-bem button_js_inited button_focused_yes")
    public Button searchButton;

    public void searchFor (String request)
    {
        requestInput.clear();
        requestInput.sendKeys(request);
        searchButton.click();
        int n;
    }
}
