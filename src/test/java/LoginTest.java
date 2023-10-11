import common.BaseClass;
import common.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Verify the Login Section")
public class LoginTest extends BaseClass {

    @Test
    @DisplayName("Enter valid UserId and Password")
    public void enterValidUserIdAndPassword() {
        WebElement userIdField = getDriver().findElement(By.name("uid"));
        userIdField.sendKeys(Configuration.EXPECTED_USERID);
        assertEquals(Configuration.EXPECTED_USERID, userIdField.getAttribute("value"), "Invalid userId");

        WebElement passwordField = getDriver().findElement(By.name("password"));
        passwordField.sendKeys(Configuration.EXPECTED_PASSWORD);
        assertEquals(Configuration.EXPECTED_PASSWORD, passwordField.getAttribute("value"), "Invalid password");

        // Another way: password.submit()
        getDriver().findElement(By.name("btnLogin")).click();

        // Verify we are on the manager home page
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("marquee")));
        String expectedHeadingText = "Manger Id : " + Configuration.EXPECTED_USERID;
        WebElement heading = getDriver().findElement(By.cssSelector("tr.heading3 > td"));
        assertEquals(expectedHeadingText, heading.getText(), "Invalid heading text");
    }

}
