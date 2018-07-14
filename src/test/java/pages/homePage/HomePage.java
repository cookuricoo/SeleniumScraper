package pages.homePage;
import pages.searchResultsPage.SearchResultsPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by stefansh on 18/07/11.
 */
public class HomePage {

    RemoteWebDriver driver;
//    public MainPagePO uiObject = new MainPagePO();

    @FindBy(className = "header-search-wrapper")
    public WebElement searchBox;

    public HomePage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SearchResultsPage searchPage(String searchParam){
        searchBox.click();
        searchBox.sendKeys(searchParam);
        searchBox.sendKeys(Keys.RETURN);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        return new SearchResultsPage(driver);
    }



}
