package HTMLElementsWorkExample;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

/**
 * Created by VKov on 1/14/2016.
 */
public class SearchArrow extends HtmlElement {

    @FindBy(className = "suggest2-form__button") /*not works*/
    public Button searchButton;

    //@FindBy(className="input__control") /*doesnt work*/
    //@FindBys({@FindBy(xpath = "//input[@class='input__control']"),@FindBy(xpath = "//input[@class='input__input']") /*not works*/})
    //@FindBys({@FindBy(className = "input__control"),@FindBy(className = "input__input") /*not works*/})
    //@FindBys({@FindBy(xpath = "//*[contains(@class, 'input__control')]"),@FindBy(xpath = "//*[contains(@class, 'input__input')]")}) /*not works*/
    @FindBy(xpath = "//input[@id='text']") /*works*/
    //@FindBy(xpath = "//*[contains(@class, 'input__control')]") /*not works*/
    //@FindBy(xpath = "//*[@id='text']") /*works*/
    //@FindBy(id = "text")/*works too*/
    //class="input__control input__input firepath-matching-node"

    public TextInput requestInput;



    public void searchFor (String request)
    {
        requestInput.clear();
        requestInput.sendKeys(request);
        searchButton.click();
        searchButton.click();
        int n;
    }
}
