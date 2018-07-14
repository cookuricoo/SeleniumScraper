package pages.searchResultsPage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by stefansh on 18/07/11.
 */
public class SearchPagePO {
    @FindBy (className = "repo-list-item")
    public List<WebElement> repo_list;

    @FindBy (linkText = "Next")
    public WebElement nextPageButton;
}
