# SeleniumScraper
Github scraper tool
===================

Setup instructions and details.
------------------------------
***************
1.Prerequisites:
***************

  1.1 Docker installed (https://docs.docker.com/install/)
  
  1.2 Maven installed (https://maven.apache.org/install.html)
  
***********************************  
2.Download the test git repository:
***********************************
  
  > git clone -b final https://github.com/cookuricoo/SeleniumScraper.git
  
*********************  
3. Running the tests:
*********************
 
  > cd SeleniumScraper
  
  For running spesific test:
  
  Test1 
  
  > mvn test -Dtest=tests.GitHubScraperTest#scraperTest -Dnum_pages_to_scrape={number of pages}
  
  Example:
  > mvn test -Dtest=tests.GitHubScraperTest#scraperTest -Dnum_pages_to_scrape=1
  
  Test2
  
  > mvn test -Dtest=tests.GitHubScraperTest#linksValidityTest
  
   for running both tests:
   
   > mvn test
   
*********************  
4. To view the code just load the project by selecting pom.xml in intelliJ or Eclipse.
*********************

