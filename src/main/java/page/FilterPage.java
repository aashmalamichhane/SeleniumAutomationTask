package page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FilterPage {
    private final Logger LOG = Logger.getLogger(FilterPage.class);

    private final WebDriver driver;

    public FilterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setLocationName(String locationName, String exactLocationName) {
        WebElement location = this.getLocation();
        LOG.info("Setting Location " + locationName);
        location.sendKeys(locationName);

        List<WebElement> locationSuggestions = getLocationSuggestions();
        for (WebElement locationSuggestion : locationSuggestions) {
            String locationSuggestionText = locationSuggestion.getText();
            if (locationSuggestionText.contains(exactLocationName)) {
                locationSuggestion.click();
                LOG.info("Setting exact location " + exactLocationName);
            }
        }
    }

    public void moveSlider(int locationRadius) throws InterruptedException {
        WebElement slider = this.getSlider();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int minValue=500;
        int max=10000;
        //Taken the width of the slider to get the offSet
        float offsetX =((locationRadius - minValue) * 296.75F / (max - minValue));
        //Since custom input is used for slider , JavaScriptExecutor is used to change the attribute value
        //and trigger its corresponding events.
        String script =
                "function simulateDragDrop(sourceNode, delta) {" +
                        "   var rect = sourceNode.getBoundingClientRect();" +
                        "   var startX = rect.left + (rect.width / 2);" +
                        "   var startY = rect.top + (rect.height / 2);" +
                        "   var endX = startX + delta;" +
                        "   var events = ['mousedown', 'mousemove', 'mouseup'];" +
                        "   events.forEach(function(eventType) {" +
                        "       var eventObject = document.createEvent('MouseEvents');" +
                        "       eventObject.initMouseEvent(eventType, true, true, window, 0, 0, 0, " +
                        "           eventType == 'mousedown' ? startX : endX, " +
                        "           startY, false, false, false, false, 0, null);" +
                        "       sourceNode.dispatchEvent(eventObject);" +
                        "   });" +
                        "}" +
                        "simulateDragDrop(arguments[0], arguments[1]);" +
                        "var targetValue = arguments[2];" +
                        "var input = arguments[0].querySelector('input');" +
                        "input.value = targetValue;" +
                        "input.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "input.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "var handle = input.closest('.rs-slider-handle');" +
                        "var percentage = ((targetValue - 500) / (10000 - 500)) * 100;" +
                        "handle.style.left = percentage + '%';" +
                        "var tooltip = handle.querySelector('.rs-slider-tooltip');" +
                        "if (tooltip) { tooltip.textContent = targetValue; }";
        js.executeScript(script, slider, offsetX, locationRadius);
    }


    public WebElement getLocation() {
        return driver.findElement(By.xpath("//div[contains(@class,'sticky--part vh--part')]//div//input[@name='location']"));
    }

    public WebElement getResetButton() {
        return driver.findElement((By.xpath("//div[@class='form-item form-item--filterBtn']//button[@type='submit']")));
    }

    public List<WebElement> getLocationSuggestions() {
        LOG.info("Fetching location suggestions");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(180)); // waiting until location suggestions are loaded
        By locationSuggestionPath = By.xpath("//div[@class='place--suggestions  ']//li");
        wait.until(ExpectedConditions.visibilityOfElementLocated(locationSuggestionPath));
        return driver.findElements(locationSuggestionPath);
    }

    public WebElement getFilterButton() {
        return driver.findElement(By.xpath("//aside[contains(@class,'aside--ad')]//button[1]"));
    }

    public void clickFilterButton() {
        WebElement filterButton = this.getFilterButton();
        filterButton.click();
    }

    public WebElement getSlider() {
        return driver.findElement(By.xpath("//div[@data-testid='slider-handle' and @tabindex='0']"));
    }

    public WebElement getSortOption() {
        return driver.findElement(By.xpath("//div[@class='sticky--part vh--part']//div//select[@name='sortParam']"));
    }

    public void setSortValue(String sortValue) {
        WebElement sortElement = this.getSortOption();
        Select sortFilter = new Select(sortElement);
        sortFilter.selectByVisibleText(sortValue);
    }

}
