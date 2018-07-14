package pages.searchResultsPage;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by stefansh on 18/07/11.
 */
public class SearchResultsPage {

    RemoteWebDriver driver;
//    public SearchPagePO uiObject = new SearchPagePO();
    public static Logger searchlogger = LoggerFactory.getLogger(SearchResultsPage.class);
    String pageLoadStatus = null;

    @FindBy(className = "repo-list-item")
    public List<WebElement> repo_list;

    @FindBy (linkText = "Next")
    public WebElement nextPageButton;

    public SearchResultsPage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitle(WebElement repo) {
        String title = repo.findElement(By.className("col-8")).getText().split("\\n")[0];
        searchlogger.info("Title :"+ title);
        return title;

    }

    public String getDescription(WebElement repo) {
        try{
            String description = repo.findElement(By.className("col-9")).getText();
            searchlogger.info("Description :"+description);
            return description;
        }catch(NoSuchElementException e){
            return "";
        }catch (StaleElementReferenceException r) {
            driver.navigate().refresh();
            String description =  repo.findElement(By.className("col-9")).getText();
            searchlogger.info("Description :"+ description);
            return description;
        }

    }

    public String getLanguage(WebElement repo) {
        try{
            String language =  repo.findElement(By.className("d-table-cell")).getText();
            searchlogger.info("Language :"+language);
            return language;
        }catch (NoSuchElementException e){
            return "";
        }catch (StaleElementReferenceException s){
            driver.navigate().refresh();
            String language =  repo.findElement(By.className("d-table-cell")).getText();
            searchlogger.info("Language :"+language);
            return language;
        }
    }

    public String getStars(WebElement repo) {
        try{

            String stars = repo.findElement(By.cssSelector("a[href*='stargazers']")).getText();
            searchlogger.info("Stars :"+stars);
            return stars;

        }catch (NoSuchElementException e){
            return "";
//        }catch (StaleElementReferenceException r) {
//            return "";
        }
    }

    public String getTime(WebElement repo) {
        String time = repo.findElement(By.className("d-flex")).findElement(By.tagName("relative-time")).getAttribute("title");
        searchlogger.info("Time :"+time);
        return time;

    }

    public String[] getTags(WebElement repo) {
        try {
            String[] tags = repo.findElement(By.className("topics-row-container")).getText().split("\\n");
            searchlogger.info("Tags :"+ Arrays.toString(tags));
            return tags;
        }catch (NoSuchElementException e){
            return new String[]{""};
        }catch (StaleElementReferenceException r){
            return new String[]{""};
        }
    }

    public SearchResultsPage goToNextPage(){

        nextPageButton.click();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        return new SearchResultsPage(driver);

    }



    public void waitForPageToLoad() {
        do {
            JavascriptExecutor js = driver;
            pageLoadStatus = (String)js.executeScript("return document.readyState");

        } while (! pageLoadStatus.equals("complete") );

    }




}
