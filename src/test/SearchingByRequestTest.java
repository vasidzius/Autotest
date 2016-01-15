import HTMLElementsWorkExample.MainPage;
import HTMLElementsWorkExample.SearchPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.Assert;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static ru.yandex.qatools.matchers.webdriver.ExistsMatcher.exists;

/**
 * Created by VKov on 1/14/2016.
 */
public class SearchingByRequestTest extends TestBase{
    private final int DEFAULT_RESULTS_COUNT;

    public SearchingByRequestTest() {
        DEFAULT_RESULTS_COUNT = 14;
    }

    @Before
    public void loadStartPage() {
        baseDriver.get("http://www.yandex.ru");
    }

    @Test
    public void afterSearchingUserShouldSeSearchResults() {
        MainPage mainPage = new MainPage(baseDriver);
        SearchPage page = mainPage.searchFor("Yandex");
        Assert.assertThat(page.getSearchResults(), exists());
        Assert.assertThat(page.getSearchResults().getSearchItems(), hasSize(DEFAULT_RESULTS_COUNT));
    }

    @After
    public void killWebDriver() {
        baseDriver.quit();
    }
}
