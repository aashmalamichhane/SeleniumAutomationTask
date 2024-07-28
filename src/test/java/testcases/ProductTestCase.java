package testcases;

import base.Base;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.FilterPage;
import page.HomePage;
import page.ProductPage;

public class ProductTestCase extends Base {
    HomePage homePage;
    ProductPage productPage;
    FilterPage filterPage;

    String searchItemName=testDataProp.getProperty("searchItemName");
    String locationName=testDataProp.getProperty("locationName");
    String exactLocationName=testDataProp.getProperty("exactLocationName");
    String lowToHighSortValue=testDataProp.getProperty("lowToHighSortValue");
    int locationRadius=Integer.parseInt(testDataProp.getProperty("locationRadius"));


    @BeforeMethod
    public void setUp() {
        driver = initBrowserApplication();
        homePage=new HomePage(driver);
        productPage=new ProductPage(driver);
        filterPage=new FilterPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(priority = 1)
    public void fetchTop50Product_SortedByPrice() throws InterruptedException {
        homePage.searchItem(searchItemName);
        filterPage.setLocationName(locationName, exactLocationName);
        filterPage.moveSlider(locationRadius);
        filterPage.clickFilterButton();
        filterPage.setSortValue(lowToHighSortValue);
        productPage.getTop50SortedProducts();
    }

    @Test(priority = 2)
    public void fetchAllFilteredProduct() throws InterruptedException {
        homePage.searchItem(searchItemName);
        filterPage.setLocationName(locationName,exactLocationName);
        filterPage.moveSlider(locationRadius);
        filterPage.clickFilterButton();
        productPage.getAllFilteredItems();
    }

}



