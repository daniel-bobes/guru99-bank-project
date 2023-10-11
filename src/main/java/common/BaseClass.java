package common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseClass {
    private WebDriver driver = null;
    private WebDriverWait wait = null;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(Configuration.BASE_URL);
        acceptConsentNotice();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public WebDriverWait getWait() {
        return this.wait;
    }

    private void acceptConsentNotice() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("gdpr-consent-notice"));
        WebElement acceptConsentNoticeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save")));
        acceptConsentNoticeButton.click();
        driver.switchTo().defaultContent();
    }
}
