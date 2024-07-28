package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import response.ProductResponse;
import utils.CSVUtils;
import utils.HTMLUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductPage {
    private final Logger LOG= LoggerFactory.getLogger(ProductPage.class);
    private final WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    /*
    This method will fetch all products displayed after applying filter
    */
    public void getAllFilteredItems() {
        CSVUtils csvUtils =new CSVUtils("all_filtered_data.csv");

        // keeping track of fetched items
        Set<String> seenProducts = new HashSet<>();
        boolean isEndOfPage = false;
        int height=0;
        while (!isEndOfPage) {
            waitForProductToLoad();
            // all the products displayed in UI will be fetched here
            // in each scroll, it will fetch 3 to 4 products
            List<WebElement> products = getProductList();
            List<ProductResponse> productResponseList=new ArrayList<>();
            boolean newProductsFound=false;
            for (WebElement product : products) {
                // anchor tag attribute is unique for each product
                // so keeping track of this to track new products
                String link = getNewTabAnchor(product);
                if (seenProducts.add(link)) {
                    // converting product WebElement to product response
                    ProductResponse productResponse = fetchProductDetails(product);
                    if (productResponse!=null) {
                        productResponseList.add(productResponse);
                    }
                    newProductsFound = true;
                }
            }
            // writing to CSV in each scroll
            csvUtils.writeToCSV(productResponseList);
            if (!newProductsFound) {
                isEndOfPage = true;
            }

            // scrolling 3 product in each scroll
            height=height+528;

            // scrolling to get new products
            scrollTo(height);
        }
    }

    /*
    * This method will fetch the top 50 products out of all sorted products
    * */
    public void getTop50SortedProducts(){
        final int MAX_PRODUCTS = 50;
        int productCount = 0;
        CSVUtils csvUtils=new CSVUtils("Search_Result.csv");
        HTMLUtils htmlUtils=new HTMLUtils("Search_Result.csv","Search_Result.html");

        // keeping track of fetched items
        Set<String> seenProducts = new HashSet<>();
        // keeping track of scroll
        int height=0;
        boolean isEndOfPage = false;

        // run until products are found and productCount must be less than or equal 50
        // this loop with terminate if there are less than 50 products, it will fetch only found products
        while (!isEndOfPage && productCount < MAX_PRODUCTS) {
            List<ProductResponse> productResponseList = new ArrayList<>();
            waitForProductToLoad();
            // all the products displayed in UI will be fetched here
            // in each scroll, it will fetch 3 to 4 products
            List<WebElement> productList = getProductList();
            for (WebElement product : productList) {
                String link = getNewTabAnchor(product);
                if (seenProducts.add(link)) {
                    if (productCount >= MAX_PRODUCTS) {
                        isEndOfPage = true; // when we reach our limit, set endOfPage to true
                        break;
                    }
                    ProductResponse productResponse = fetchProductDetails(product);
                    productCount++;
                    productResponseList.add(productResponse);
                }
            }
            if (productResponseList.isEmpty()){
                break;
            }
            csvUtils.writeToCSV(productResponseList);

            // scrolling 3 product in each scroll
            height=height+528;
            scrollTo(height);
        }
        htmlUtils.generateHTMLFromCSV();
    }

    /*
    * This method will convert product WebElement data to Product Response Object
    * */
    private ProductResponse fetchProductDetails(WebElement product) {
        try {
            String title = getProductTitle(product);
            String price = getProductRegularPrice(product);
            String description = getProductDescription(product);
            String time = getTime(product);
            String condition = getProductCondition(product);
            String userName = getUserFullName(product);
            ProductResponse productResponse = new ProductResponse(title, description, price, condition, time, userName);
            return productResponse;
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return null;
        }
    }

    private void waitForProductToLoad() {
        LOG.info("Waiting for Product to Load");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading-spinner")));
    }

    public void scrollTo(long scrollHeight){
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, "+scrollHeight+")");
    }

    private  String getUserFullName(WebElement product) {
        return product.findElement(By.cssSelector(".username-fullname")).getText();
    }

    private  String getProductCondition(WebElement product) {
        return product.findElement(By.cssSelector(".condition")).getText();
    }

    private  String getTime(WebElement product) {
        return product.findElement(By.cssSelector(".time")).getText();
    }

    public WebElement getSearchHeading(){
        return driver.findElement(By.xpath("//h3"));
    }

    private  String getProductDescription(WebElement product) {
        return product.findElement(By.cssSelector(".description")).getText();
    }

    private  String getProductRegularPrice(WebElement product) {
        return product.findElement(By.cssSelector(".regularPrice")).getText();
    }

    private  String getProductTitle(WebElement product) {
        return product.findElement(By.cssSelector(".product-title")).getText();
    }

    private  String getNewTabAnchor(WebElement product) {
        return product.findElement(By.cssSelector(".newTabAnchor")).getAttribute("href");
    }

    private List<WebElement> getProductList() {
        return driver.findElements(By.cssSelector(".card-product-linear"));
    }
}
