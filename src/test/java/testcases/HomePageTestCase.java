package testcases;

import base.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.HomePage;
import page.ProductPage;


public class HomePageTestCase extends Base {

    WebDriver driver;
    HomePage homePage;
    ProductPage productPage;
    String searchItemName = testDataProp.getProperty("searchItemName");

    @BeforeMethod
    public void setUp() {
        driver = initBrowserApplication();
        homePage=new HomePage(driver);
        productPage=new ProductPage(driver);
        homePage.searchItem(searchItemName);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void verifySearchMonitor() {
        WebElement searchHeading = productPage.getSearchHeading();
        String actSearchResultText = searchHeading.getText();
        String expSearchResultText = "Search Result for : "+ searchItemName;
        Assert.assertEquals(actSearchResultText,expSearchResultText,"Heading for search text does not match");
    }

    //This method is created to show that screenshots will be taken in case of failed cases.
    //This test is supposed to be failed.
    @Test
    public void verifyFailedCaseToDemonstrateScreenshotCapture() {
        WebElement searchHeading = productPage.getSearchHeading();
        String actSearchResultText = searchHeading.getText();
        String expSearchResultText = "Search Resultt for : "+ searchItemName;
        Assert.assertEquals(actSearchResultText,expSearchResultText,"Heading for search text does not match");
    }


}


