package page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.log4j.Logger;

public class HomePage {
    private final Logger LOG= Logger.getLogger(HomePage.class);

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getSearchBox(){
        return driver.findElement(By.xpath("//input[@name='searchValue']"));
    }


    public void searchItem(String searchItemName){
        WebElement searchBox = this.getSearchBox();
        LOG.info("Searching for: " + searchItemName);
        searchBox.sendKeys(searchItemName);
        searchBox.sendKeys(Keys.ENTER);
    }



}
