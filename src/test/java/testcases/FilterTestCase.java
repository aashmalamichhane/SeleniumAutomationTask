package testcases;

import base.Base;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.FilterPage;
import page.HomePage;

public class FilterTestCase extends Base {
    HomePage homePage;
    FilterPage filterPage;
    String searchItemName=testDataProp.getProperty("searchItemName");
    String locationName=testDataProp.getProperty("locationName");
    String exactLocationName=testDataProp.getProperty("exactLocationName");
    int locationRadius=Integer.parseInt(testDataProp.getProperty("locationRadius"));

    @BeforeMethod
    public void setUp() {
        driver = initBrowserApplication();
        homePage=new HomePage(driver);
        filterPage=new FilterPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void verifyResetFilterButtonVisibility() throws InterruptedException {
        homePage.searchItem(searchItemName);
        filterPage.setLocationName(locationName, exactLocationName);
        filterPage.moveSlider(locationRadius);
        filterPage.clickFilterButton();
        Assert.assertTrue(filterPage.getResetButton().isDisplayed());
        Thread.sleep(10000);
    }
}
