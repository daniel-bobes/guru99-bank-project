import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

@DisplayName("Verify the Login Section")
public class LoginTest {
    private WebDriver driver = null;
    private WebDriverWait wait = null;

    private static final String BASE_URL = "https://demo.guru99.com/V4/";
    private static final String EXPECTED_USERID = "mngr532405";
    private static final String EXPECTED_PASSWORD = "ahYpagy";

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(BASE_URL);
    }

    @Test
    @DisplayName("Enter valid UserId and Password")
    public void enterValidUserIdAndPassword() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("gdpr-consent-notice"));
        driver.findElement(By.id("save")).click();
        driver.switchTo().defaultContent();

        WebElement userIdField = driver.findElement(By.name("uid"));
        userIdField.sendKeys(EXPECTED_USERID);
        assertEquals(EXPECTED_USERID, userIdField.getAttribute("value"), "Invalid userId");

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(EXPECTED_PASSWORD);
        assertEquals(EXPECTED_PASSWORD, passwordField.getAttribute("value"), "Invalid password");

        // Another way: password.submit()
        driver.findElement(By.name("btnLogin")).click();

        // Verify we are on the manager home page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("marquee")));

        String expectedHeadingText = "Manger Id : " + EXPECTED_USERID;
        WebElement heading = driver.findElement(By.cssSelector("tr.heading3 > td"));
        assertEquals(expectedHeadingText, heading.getText(), "Invalid heading text");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
