import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

import static org.testng.Assert.assertEquals;

public class BasicTest {

    @Test
    public void simpleTest() {
        WebDriver driver = setupBrowserDriver();
        driver.navigate().to("http://bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("sb_form_q")));
        WebElement element = driver.findElement(By.id("sb_form_q"));
        element.sendKeys("i am working");

        assertEquals(element.getAttribute("value"),"i am working");

        driver.quit();
    }

    public WebDriver setupBrowserDriver() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("test.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (properties.getProperty("browser")) {
            case "firefox" : WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            default: WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                options.addArguments("--disable-gpu")
                        .addArguments("--no-sandbox")
                        .addArguments("--disable-setuid-sandbox");
                return new ChromeDriver(options);
        }
    }
}
