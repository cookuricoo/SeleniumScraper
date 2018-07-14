package utils;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.GitHubScraperTest;

/**
 * Created by stefansh on 18/07/11.
 */
public class Utils extends DriverManager{

 public static Logger logger = LoggerFactory.getLogger(GitHubScraperTest.class);


     public long startTimer(){
      long startTime = System.currentTimeMillis();
      return startTime;
     }

     public long stopTimer(long startTime){
      new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.className("repo-list")));
      long endTime = System.currentTimeMillis();
      long totalTime = endTime - startTime;
      return totalTime;
     }



 }


