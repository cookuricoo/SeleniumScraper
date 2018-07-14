package utils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.GenericContainer;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;


/**
 * Created by stefansh on 18/07/11.
 */

public class DriverManager {


    public  RemoteWebDriver driver;
    public static MongoDatabase database;
    public static MongoCollection<Document> repo_collection;


    @ClassRule
    public static GenericContainer mongo = new GenericContainer("mongo:4.0").withExposedPorts(27017);

    @Rule
    public BrowserWebDriverContainer chrome =
            new BrowserWebDriverContainer()
                    .withDesiredCapabilities(DesiredCapabilities.chrome());
    //                    .withRecordingMode(RECORD_ALL, new File("/tmp/recordings/"));

    @BeforeClass
    public static void beforeTests() throws MalformedURLException, InterruptedException {
        String mongoHost = mongo.getContainerIpAddress();
        MongoClient mongoClient = new MongoClient(mongoHost, mongo.getMappedPort(27017));
        database = mongoClient.getDatabase("repository");
        repo_collection = database.getCollection("repo_collection");
    }
    @Before
    public void setup() {
            driver = chrome.getWebDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            PageFactory.initElements(driver, scraper.homePage.uiObject);
//            PageFactory.initElements(driver, scraper.searchResultsPage.uiObject);
        }


    @After
    public void afterTest(){
        driver.quit();
    }

}
