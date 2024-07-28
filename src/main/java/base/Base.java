package base;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.apache.log4j.Logger;
import utils.Utilities;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class Base {
    public WebDriver driver ;
    public Properties prop;
    public Properties testDataProp;

    private Logger LOG= Logger.getLogger(Base.class);

    public  Base() {
        prop = new Properties();
        testDataProp = new Properties();
        try {
            LOG.info("Loading Config Properties");
            InputStream conFile = Base.class.getClassLoader().getResourceAsStream("config/config.properties");
            prop.load(conFile);

            LOG.info("Loading Test Data Properties");
            InputStream testDataFile = Base.class.getClassLoader().getResourceAsStream("testdata/testdata.properties");
            testDataProp.load(testDataFile);

            LOG.info("Loading Properties Completed");
        }
        catch (Exception e) {
            LOG.error("ERROR Loading Config Properties");
            e.printStackTrace();
        }

    }

    public WebDriver initBrowserApplication()
    {
        String browserName = prop.getProperty("browser");
        if(browserName.equalsIgnoreCase("chrome"))
        {
            driver = new ChromeDriver();
            LOG.info("Chrome Driver Initialized");
        }
        else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            LOG.info("Firefox Driver Initialized");
        }
        else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
            LOG.info("Edge Driver Initialized");
        }
        else if (browserName.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
            LOG.info("Safari Driver Initialized");
        }else{
            // when no driver is initialized chrome driver will be used by default
            driver = new ChromeDriver();
            LOG.info("Chrome Driver Initialized By Default");
        }
        Dimension dimension=new Dimension(1920,1080);
        driver.manage().window().setSize(dimension);
//        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Utilities.Implicit_Wait_time));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Utilities.Page_Load_time));
        driver.get(prop.getProperty("url"));

        LOG.info(String.format("Accessing %s ",driver.getTitle()));
        return driver;
    }
}
