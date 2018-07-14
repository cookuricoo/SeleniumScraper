package tests;
import pages.homePage.HomePage;
import pages.searchResultsPage.SearchResultsPage;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.Utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stefansh on 18/07/09.
 */

public class GitHubScraperTest extends Utils{

//    public static SeleniumScraper scraper = new SeleniumScraper();

    List<Document> repoItems = new ArrayList<Document>();
    int pages = Integer.parseInt(System.getProperty("num_pages_to_scrape","5"));
    SearchResultsPage searchPage;
    HomePage homePage;

    @Test
    public void scraperTest(){
        try {
            homePage = new HomePage(driver);
            searchPage = new SearchResultsPage(driver);

            logger.info("Starting...");
            driver.get("https://github.com");
            Long searchPageStartTime = startTimer();
            homePage.searchPage("selenium");
            logger.info("Search page load time = " + stopTimer(searchPageStartTime) + " milliseconds");

            for (int i = 0; i < pages; i++) {
                logger.info("*****************PAGE: " + i + "*************************************");
                int numElements = searchPage.repo_list.size();
                for (int j = 0; j < numElements; j++) {
                    logger.info("Element: " + j);
                    WebElement current_repo = searchPage.repo_list.get(j);

                    repoItems.add(new Document("title", searchPage.getTitle(current_repo))
                            .append("description", searchPage.getDescription(current_repo))
                            .append("tags", Arrays.asList(searchPage.getTags(current_repo)))
                            .append("time", searchPage.getTime(current_repo))
                            .append("language", searchPage.getLanguage(current_repo))
                            .append("stars", searchPage.getStars(current_repo))
                    );

                }
                Long nextPageStartTime = startTimer();
                searchPage.goToNextPage().waitForPageToLoad();
                logger.info("#############################################################");
                logger.info("Next page load time = " + stopTimer(nextPageStartTime) + " milliseconds");


            }
            repo_collection.insertMany(repoItems);
            logger.info("number of documents: " + repo_collection.countDocuments());
            Assert.assertTrue(repo_collection.countDocuments() == 10*pages);
        }catch (AssertionError e){
            Assert.fail("Bug found");
            logger.error("Not All documents were saved");
        }catch(Exception e){
            logger.error("exception: ",e);
            Assert.fail("Something happened");
        }

    }

     @Test
     public void linksValidityTest(){

           try{
               homePage = new HomePage(driver);
               searchPage = new SearchResultsPage(driver);

               driver.get("https://github.com");
                homePage.searchPage("selenium");
                List<WebElement> allLinks = driver.findElement(By.className("repo-list")).findElements(By.tagName("a"));
                logger.info("All links found on web page are: " + allLinks.size() + " links");
                for (WebElement link : allLinks) {
                    logger.info("Testing link :" + link.getAttribute("href"));
                    HttpURLConnection connection = (HttpURLConnection) new URL(link.getAttribute("href")).openConnection();
                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();
                    if (responseCode != 200) {
                        logger.error("Page is invalid: " + responseCode);
                        Assert.fail("The URL is invalid: "+ link.getAttribute("href").toString());
                    } else {
                        logger.info("Response: " + responseCode);
                    }
                }
                Assert.assertTrue("All URL's are valid",true);
            }catch(Exception e){
            logger.error("exception: ",e);
        }
    }


}
